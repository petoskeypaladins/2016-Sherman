package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.Robot;
import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ServoSubsystem extends Subsystem {
	private final double LEFT_SHOOT_POSITION = 0;
	private final double LEFT_OPEN_POSITION = 90;
	
	private final double LEFT_HOLD_POSITION = 135;
	
	private final double RIGHT_SHOOT_POSITION = 180;
	private final double RIGHT_OPEN_POSITION = 90;
	
	private final double RIGHT_HOLD_POSITION = 45;
	
	
	//Servo Declarations
	Servo leftServo;
	Servo rightServo;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public ServoSubsystem() {
		leftServo = new Servo(RobotMap.LEFT_BALL_SERVO);
		rightServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
    }

    public void shoot() {
		if (Robot.IS_COMPETITION_ROBOT) {
			leftServo.set(LEFT_SHOOT_POSITION);
		} else {
			leftServo.set(LEFT_SHOOT_POSITION - 30);
		}
    	rightServo.set(RIGHT_SHOOT_POSITION);	
    }
    
    public void stopShoot() {
    	leftServo.setAngle(LEFT_OPEN_POSITION);
		rightServo.setAngle(RIGHT_OPEN_POSITION);
    }
    
    public void holdBall() {
    	leftServo.setAngle(LEFT_HOLD_POSITION);
    	rightServo.setAngle(RIGHT_HOLD_POSITION);
    }
    
    public void releaseBall() {
    	leftServo.setAngle(LEFT_OPEN_POSITION);
    	rightServo.setAngle(RIGHT_OPEN_POSITION);
    }
}

