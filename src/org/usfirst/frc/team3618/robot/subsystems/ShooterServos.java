package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.UnReadyCommand;

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
	
	private final double LEFT_SHOOT_POSITION = 0;
	private final double LEFT_OPEN_POSITION = 90;
	
	private final double LEFT_HOLD_POSITION = 135;
	
	private final double RIGHT_SHOOT_POSITION = 180;
	private final double RIGHT_OPEN_POSITION = 90;
	
	private final double RIGHT_HOLD_POSITION = 45;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
public void shoot() {
    	
		leftBallServo.set(LEFT_SHOOT_POSITION);
    	rightBallServo.set(RIGHT_SHOOT_POSITION);
    	DriverStation.reportError("Triggered. \n", false);
    	
    }
    
    public void stopShoot() {
    	SmartDashboard.putString(null, "Wheels Stopping");
    	Scheduler.getInstance().add(new UnReadyCommand());
    	leftBallServo.setAngle(LEFT_OPEN_POSITION);
		rightBallServo.setAngle(RIGHT_OPEN_POSITION);
    }
    
    public void holdBall() {
    	leftBallServo.setAngle(LEFT_HOLD_POSITION);
    	rightBallServo.setAngle(RIGHT_HOLD_POSITION);
    	System.out.println("the servos should have moved");
    }
    
    public void releaseBall() {
    	leftBallServo.setAngle(LEFT_OPEN_POSITION);
    	rightBallServo.setAngle(RIGHT_OPEN_POSITION);
    }
}

