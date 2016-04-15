package org.usfirst.frc.team3618.robot;

import org.usfirst.frc.team3618.robot.commands.HoldBallCommand;
import org.usfirst.frc.team3618.robot.commands.IntakeStopCommand;
import org.usfirst.frc.team3618.robot.commands.ReleaseBallCommand;
import org.usfirst.frc.team3618.robot.commands.autonomous.AutonomousCommandManager;
import org.usfirst.frc.team3618.robot.subsystems.ArmsSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.RollerSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.ServoSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.TurretSubsystem;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Robot extends IterativeRobot {

	public static boolean IS_USING_OPENCV = true;
	public static boolean IS_COMPETITION_ROBOT = true;
	
	public static DriveSubsystem driveSubsystem = new DriveSubsystem();
	public static ArmsSubsystem armsSubsystem = new ArmsSubsystem();
	public static TurretSubsystem turretSubsystem = new TurretSubsystem();
	public static RollerSubsystem rollerSubsystem = new RollerSubsystem();	
	public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	public static ServoSubsystem servoSubsystem = new ServoSubsystem();
//	public static LawrenceMk7 lawrenceMk7 = new LawrenceMk7();

	public static OI oi;
	
	private DigitalInput backSensor = new DigitalInput(RobotMap.BACK_BALL_SENSOR);
	
	private boolean lastRunBackSensor = false; 
	private SendableChooser autoBallChooser, autoDefenseChooser, autoPositionChooser;
	
	private PowerDistributionPanel pdp;
	
	public String[] defenses;
	  
	CameraServer camServer;
	USBCamera lifecam;
    
    int CAM_WIDTH = 640;
    int CAM_HEIGHT = 480;
    
    Command autonomousCommand;

    public void robotInit() {
    	oi = new OI();
    	
    	clearVisionData();
    	
    	autoBallChooser = new SendableChooser();
        autoDefenseChooser = new SendableChooser();
        autoPositionChooser = new SendableChooser();
    	
        pdp = new PowerDistributionPanel(0);
        
    	SmartDashboard.putData("Autonomous Type", autoBallChooser);
    	SmartDashboard.putData("Autonomous Defense Type", autoDefenseChooser);
    	SmartDashboard.putData("Autonomous Position", autoPositionChooser);

        defenses = new String[]{"Rock Wall", "Rough Terrain", "Ramparts", "Moat", "Low Bar", "Portcullis", "Cheval de Frise", "DrawBridge", "Sally Port"};
        
        autoBallChooser.addDefault("One Ball", 1);
        autoBallChooser.addObject("Two Ball", 2);
        autoBallChooser.addObject("No Ball", 3);
        autoBallChooser.addObject("Sit There and Cry", 4);

        autoDefenseChooser.addDefault(defenses[0], 1);
        for (int i = 2; i < defenses.length + 1; i++) {
            autoDefenseChooser.addObject(defenses[i - 1], i);
        }
        
        autoPositionChooser.addDefault("Position 1", 1);
        autoPositionChooser.addObject("Position 2", 2);
        autoPositionChooser.addObject("Position 3", 3);
        autoPositionChooser.addObject("Position 4", 4);
        autoPositionChooser.addObject("Position 5", 5);
        autoPositionChooser.addObject("NONE", 6);
        
        if (!IS_USING_OPENCV) {
        	camServer = CameraServer.getInstance();
	        lifecam = new USBCamera("cam1");
	        frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
        }
        
    }
	
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    	Robot.driveSubsystem.resetGyros();
    
    	Robot.turretSubsystem.resetGyro();
    	Robot.clearVisionData();
        try {
        	System.out.println("Trying autonomous");
        	//one ball: 1
        	//
        	//rock wall 1
        	//rough terrain 2
        	//ramparts 3
        	//moat 4
        	//
        	//position matches
        	int mode = (int) autoBallChooser.getSelected();
        	int defense = (int) autoDefenseChooser.getSelected();
        	int position = (int) autoPositionChooser.getSelected();
        	System.out.println(mode + " | " + defense + " | " + position);
        	autonomousCommand = new AutonomousCommandManager(mode, defense, position);
    		autonomousCommand.start();
        } catch(Exception e) {
        	System.out.println("Unable to read chooser data!");
        }
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
        Robot.rollerSubsystem.displayData();
        Robot.shooterSubsystem.displayData();
        Robot.turretSubsystem.displayData();
        Robot.driveSubsystem.displayData();
        Robot.armsSubsystem.displayData();
        this.displayData();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        Robot.driveSubsystem.resetGyros();
        Robot.turretSubsystem.resetGyro();
        Robot.driveSubsystem.resetEncoders();
        Robot.clearVisionData();
        
        lastRunBackSensor = false; 
        
        if (!IS_USING_OPENCV) {
        	lifecam.setFPS(30);
            lifecam.openCamera();
            lifecam.setSize(CAM_WIDTH, CAM_HEIGHT);
            lifecam.updateSettings();
            lifecam.startCapture();
            camServer.setQuality(100);
        }
    }
    
    Image frame;
    
    NIVision.Line yCross;
    NIVision.Line xCross;
    
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        Robot.rollerSubsystem.displayData();
        Robot.shooterSubsystem.displayData();
        Robot.turretSubsystem.displayData();
        Robot.driveSubsystem.displayData();
        Robot.armsSubsystem.displayData();
        this.displayData();
	        
        boolean thisRunBackSensor = backSensor.get();
        
        if (!thisRunBackSensor && lastRunBackSensor) {
        	Scheduler.getInstance().add(new IntakeStopCommand());
			Scheduler.getInstance().add(new HoldBallCommand());
			System.out.println("Hold ball");
		} else if (thisRunBackSensor && !lastRunBackSensor) {
			Scheduler.getInstance().add(new ReleaseBallCommand());
		} 
		
		lastRunBackSensor = thisRunBackSensor;
		
		if (!IS_USING_OPENCV) {
	        lifecam.getImage(frame);
	        
	        // The cross will be 20px in each direction from the center of the image
	        int radius = 20;
	        
	        NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new NIVision.Point(CAM_WIDTH / 2, (CAM_HEIGHT / 2) + radius), new NIVision.Point(CAM_WIDTH / 2, (CAM_HEIGHT / 2) - radius), 0);
	        NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new NIVision.Point((CAM_WIDTH / 2) - radius, CAM_HEIGHT / 2), new NIVision.Point((CAM_WIDTH / 2) + radius, CAM_HEIGHT / 2), 0);
	        
	        camServer.setImage(frame);
        }
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void displayData() {
    	SmartDashboard.putNumber("Current 0", pdp.getCurrent(0));
    	SmartDashboard.putNumber("Current 1", pdp.getCurrent(1));
    	SmartDashboard.putNumber("Current 2", pdp.getCurrent(2));
    	SmartDashboard.putNumber("Current 3", pdp.getCurrent(3));
    	SmartDashboard.putNumber("Current 4", pdp.getCurrent(4));
    	SmartDashboard.putNumber("Current 5", pdp.getCurrent(5));
    	SmartDashboard.putNumber("Current 6", pdp.getCurrent(6));
    	SmartDashboard.putNumber("Current 7", pdp.getCurrent(7));
    	SmartDashboard.putNumber("Current 8", pdp.getCurrent(8));
    	SmartDashboard.putNumber("Current 9", pdp.getCurrent(9));
    	SmartDashboard.putNumber("Current 10", pdp.getCurrent(10));
    	SmartDashboard.putNumber("Current 11", pdp.getCurrent(11));
    	SmartDashboard.putNumber("Total Robot Current", pdp.getTotalCurrent());
    	SmartDashboard.putNumber("Robot Battery Voltage", pdp.getVoltage());
    	SmartDashboard.putNumber("Total Robot Power", pdp.getTotalPower());
    	
    	SmartDashboard.putBoolean("Sensor - Hold Photosensor", backSensor.get());
    }
    
    public static void clearVisionData() {
    	SmartDashboard.putNumber("Center X", -1);
    	SmartDashboard.putNumber("Center Y", -1);
    	SmartDashboard.putNumber("Goal Width", -1);
    	SmartDashboard.putBoolean("Centered Shooter (x)", false);
    	SmartDashboard.putBoolean("Centered Shooter (y)", false);
    }
}
