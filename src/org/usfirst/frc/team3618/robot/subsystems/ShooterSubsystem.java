package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.Robot;
import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

	//Shooter declarations 
	CANTalon leftMotor;
	CANTalon rightMotor;	
	
	private final double LEFT_SHOOT_POSITION = 0;
	private final double LEFT_OPEN_POSITION = 90;
	
	private final double LEFT_HOLD_POSITION = 135;
	
	private final double RIGHT_SHOOT_POSITION = 180;
	private final double RIGHT_OPEN_POSITION = 90;
	
	private final double RIGHT_HOLD_POSITION = 45;
	
	//Servo Declarations
		Servo leftServo;
		Servo rightServo;
		
		
	
	public ShooterSubsystem() {
		leftMotor = new CANTalon(RobotMap.LEFT_SHOOT_WHEEL_MOTOR);
		rightMotor = new CANTalon(RobotMap.RIGHT_SHOOT_WHEEL_MOTOR);
		
		leftServo = new Servo(RobotMap.LEFT_BALL_SERVO);
		rightServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
		
    	// TODO - Always check before deploying
		if (Robot.IS_COMPETITION_ROBOT) {
			rightMotor.setInverted(true);
		} else {
			rightMotor.setInverted(false);
		}
	}
	
	public void displayData() {
//		SmartDashboard.putNumber("Left Turret Wheel", leftMotor.getEncVelocity());
//		SmartDashboard.putNumber("Right Turret Wheel", rightMotor.getEncVelocity());
		SmartDashboard.putNumber("Output - Left Turret Wheel", leftMotor.get());
		SmartDashboard.putNumber("Output - Right Turret Wheel", rightMotor.get());
		
		SmartDashboard.putNumber("Sensor - Left Servo", leftServo.getAngle());
		SmartDashboard.putNumber("Sensor - Right Servo", rightServo.getAngle());
	}
	
    public void initDefaultCommand() {
    }
    
    public void spinShooter(double power) {
		leftMotor.set(power);
		rightMotor.set(power);
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

