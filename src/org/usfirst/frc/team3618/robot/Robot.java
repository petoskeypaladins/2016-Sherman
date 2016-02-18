
package org.usfirst.frc.team3618.robot;

import org.usfirst.frc.team3618.robot.commands.HoldBallCommand;
import org.usfirst.frc.team3618.robot.commands.IntakeStartCommand;
import org.usfirst.frc.team3618.robot.commands.IntakeStopCommand;
import org.usfirst.frc.team3618.robot.commands.ReleaseBallCommand;
import org.usfirst.frc.team3618.robot.subsystems.ArmLift;
import org.usfirst.frc.team3618.robot.subsystems.ArmRoller;
import org.usfirst.frc.team3618.robot.subsystems.Drive;
import org.usfirst.frc.team3618.robot.subsystems.ShooterRotate;
import org.usfirst.frc.team3618.robot.subsystems.ShooterServos;
import org.usfirst.frc.team3618.robot.subsystems.ShooterTilt;
import org.usfirst.frc.team3618.robot.subsystems.ShooterWheels;

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

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {


	public static final Drive drive = new org.usfirst.frc.team3618.robot.subsystems.Drive() ;

	public static OI oi;
	
	public static ShooterRotate shooterRotate = new ShooterRotate();
	public static ShooterTilt shooterTilt = new ShooterTilt();
	public static ShooterWheels shooterWheels = new ShooterWheels();
	public static ArmLift armLift = new ArmLift();
	public static ShooterServos shooterServos = new ShooterServos();
	public static ArmRoller armRoller = new ArmRoller();

	public static boolean IS_USING_OPENCV = false;
	
	private DigitalInput frontSensor = new DigitalInput(RobotMap.FRONT_BALL_SENSOR);
	private DigitalInput backSensor = new DigitalInput(RobotMap.BACK_BALL_SENSOR);
	
	private boolean lastRunBackSensor = false; 
    private boolean lastRunFrontSensor = false;
	
    CameraServer camServer;
    USBCamera lifecam;
    
    int CAM_WIDTH = 640;
    int CAM_HEIGHT = 480;
    
    Command autonomousCommand;
    SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        chooser = new SendableChooser();
        SmartDashboard.putData("Auto mode", chooser);
        System.out.println("Robot on.");

        if (!IS_USING_OPENCV) {
        	camServer = CameraServer.getInstance();
	        lifecam = new USBCamera("cam0");
	        frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
        }
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
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

    
    
    /**
     * This function is called periodically during operator control
     */
    
    Image frame;
    
    NIVision.Line yCross;
    NIVision.Line xCross;
    
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
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
        
//        System.out.println(Boolean.toString(thisRunBackSensor));
        
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
		
		shooterWheels.displayRPMS();
//		if(cycles >= 20) {
//			cycles = 0;
//			int leftThisPosition = leftMotor.getEncPosition();
//			int rightThisPosition = rightMotor.getEncPosition();
//			
//			double leftRps = ((double) leftThisPosition - leftLastPosition);
//			double rightRps = ((double) rightThisPosition - rightLastPosition);
//			
//			leftLastPosition = leftThisPosition;
//			rightLastPosition = rightThisPosition;
//			
//			System.out.println("Left Wheel sec: " + Double.toString(leftRps));
//	    	System.out.println("Right Wheel sec: " + Double.toString(rightRps));
//			
//			System.out.println("Left Wheel min: " + Double.toString(leftRps * 60));
//	    	System.out.println("Right Wheel min: " + Double.toString(rightRps * 60));
//		} else {
//			cycles++;
//		}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
