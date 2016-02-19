package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.StopShooterCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterServos extends Subsystem {
	Servo leftBallServo = new Servo(RobotMap.LEFT_BALL_SERVO);
	Servo rightBallServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
	Servo cameraArm = new Servo(RobotMap.CAMERA_ARM_SERVO);
	
	private final double LEFT_SHOOT_POSITION = 0;
	private final double LEFT_OPEN_POSITION = 90;
	
	private final double LEFT_HOLD_POSITION = 135;
	
	private final double RIGHT_SHOOT_POSITION = 180;
	private final double RIGHT_OPEN_POSITION = 90;
	
	private final double RIGHT_HOLD_POSITION = 45;
	
	private final double CAMERA_UP = 66;
	private final double CAMERA_DOWN = 0;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
public void shoot() {
    	
		leftBallServo.set(LEFT_SHOOT_POSITION);
    	rightBallServo.set(RIGHT_SHOOT_POSITION);
    	
    }
    
    public void stopShoot() {
    	Scheduler.getInstance().add(new StopShooterCommand());
    	leftBallServo.setAngle(LEFT_OPEN_POSITION);
		rightBallServo.setAngle(RIGHT_OPEN_POSITION);
    }
    
    public void holdBall() {
    	leftBallServo.setAngle(LEFT_HOLD_POSITION);
    	rightBallServo.setAngle(RIGHT_HOLD_POSITION);
    }
    
    public void releaseBall() {
    	leftBallServo.setAngle(LEFT_OPEN_POSITION);
    	rightBallServo.setAngle(RIGHT_OPEN_POSITION);
    }
    
    public void cameraUp() {
    	cameraArm.setAngle(CAMERA_UP);
    }
    
    public void cameraDown() {
    	cameraArm.setAngle(CAMERA_DOWN);
    }
}

