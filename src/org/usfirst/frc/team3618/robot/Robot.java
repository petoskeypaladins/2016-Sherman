package org.usfirst.frc.team3618.robot;

import org.usfirst.frc.team3618.robot.commands.HoldBallCommand;
import org.usfirst.frc.team3618.robot.commands.IntakeStartCommand;
import org.usfirst.frc.team3618.robot.commands.IntakeStopCommand;
import org.usfirst.frc.team3618.robot.commands.ReleaseBallCommand;
import org.usfirst.frc.team3618.robot.subsystems.ArmsSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.RollerSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team3618.robot.subsystems.TurretSubsystem;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Robot extends IterativeRobot {

	  public static DriveSubsystem driveSubsystem = new DriveSubsystem();
	  public static ArmsSubsystem armsSubsystem = new ArmsSubsystem();
	  public static TurretSubsystem turretSubsystem = new TurretSubsystem();
	  public static RollerSubsystem rollerSubsystem = new RollerSubsystem();	
	  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

	  public static OI oi;
	
	  public static boolean IS_USING_OPENCV = true;
	
	  private DigitalInput frontSensor = new DigitalInput(RobotMap.FRONT_BALL_SENSOR);
	  private DigitalInput backSensor = new DigitalInput(RobotMap.BACK_BALL_SENSOR);
	
	  private boolean lastRunBackSensor = false; 
    private boolean lastRunFrontSensor = false;
    private SendableChooser autoBallChooser, autoDefenseChooser;
    
    CameraServer camServer;
    USBCamera lifecam;
    
    int CAM_WIDTH = 640;
    int CAM_HEIGHT = 480;
    
    Command autonomousCommand;

    public void robotInit() {
		oi = new OI();
       SmartDashboard.putData("Auto mode", chooser);
        
        if (!IS_USING_OPENCV) {
        	camServer = CameraServer.getInstance();
	        lifecam = new USBCamera("cam0");
	        frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
        }

        autoBallChooser = new SendableChooser();
        autoDefenseChooser = new SendableChooser();
        String[] defenses = {"Low Bar", "Portcullis", "Cheval de Frise", "Rock Wall", "Rough Terrain", "Ramparts", "Moat", "DrawBridge", "Sally Port"};
        
        autoBallChooser.addDefault("One", 1);
        autoBallChooser.addObject("Two", 2);
        autoBallChooser.addObject("Zero", 3);

        autoDefenseChooser.addDefault(defenses[0], 1);
        for (int i = 2; i < defenses.length + 1; i++) {
            autoDefenseChooser.addObject(defenses[i - 1], i);
        }
    }
	
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        lastRunBackSensor = false; 
        lastRunFrontSensor = false;
        
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
        
        Robot.turretSubsystem.displayData();
        Robot.driveSubsystem.displayData();
        Robot.armsSubsystem.displayData();
        
        SmartDashboard.putBoolean("Back Sensor", backSensor.get());
        SmartDashboard.putBoolean("Front Sensor", frontSensor.get());
        
        if (!IS_USING_OPENCV) {
	        lifecam.getImage(frame);
	        
	        // The cross will be 20px in each direction from the center of the image
	        int radius = 20;
	        
	        NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new NIVision.Point(CAM_WIDTH / 2, (CAM_HEIGHT / 2) + radius), new NIVision.Point(CAM_WIDTH / 2, (CAM_HEIGHT / 2) - radius), 0);
	        NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new NIVision.Point((CAM_WIDTH / 2) - radius, CAM_HEIGHT / 2), new NIVision.Point((CAM_WIDTH / 2) + radius, CAM_HEIGHT / 2), 0);
	        
	        camServer.setImage(frame);
        }
	        
        boolean thisRunBackSensor = backSensor.get();
        boolean thisRunFrontSensor = frontSensor.get();
        
        if (!thisRunBackSensor && lastRunBackSensor) {
        	Scheduler.getInstance().add(new IntakeStopCommand());
			Scheduler.getInstance().add(new HoldBallCommand());
			System.out.println("Hold ball");
		} else if (thisRunBackSensor && !lastRunBackSensor) {
			Scheduler.getInstance().add(new ReleaseBallCommand());
		}
        
		if (!thisRunFrontSensor && lastRunFrontSensor) {
			Scheduler.getInstance().add(new IntakeStartCommand());		
		} 
		
		lastRunBackSensor = thisRunBackSensor;
		lastRunFrontSensor = thisRunFrontSensor;
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
