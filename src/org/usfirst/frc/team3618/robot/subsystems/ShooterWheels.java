package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.ReadyCommand;
import org.usfirst.frc.team3618.robot.commands.ShootCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterWheels extends Subsystem {
	CANTalon leftShootWheel = new CANTalon(RobotMap.LEFT_SHOOT_WHEEL_MOTOR);
	CANTalon rightShootWheel = new CANTalon(RobotMap.RIGHT_SHOOT_WHEEL_MOTOR);
	
	Servo leftBallServo = new Servo(RobotMap.LEFT_BALL_SERVO);
	Servo rightBallServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
	
	private final double FULLSPEED = 1.0;
	private final double OFF = 0;
	private final double LEFT_SHOOT_POSITION = 0;
	private final double LEFT_OPEN_POSITION = 360;
	private final double LEFT_HOLD_POSITION = 90;
	private final double RIGHT_SHOOT_POSITION = 360;
	private final double RIGHT_OPEN_POSITION = 0;
	private final double RIGHT_HOLD_POSITION = 90;

	public ShooterWheels() {
		leftShootWheel.setInverted(true);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ShootCommand());
    }
    
    public void readyShoot() {
    	leftBallServo.setAngle(LEFT_OPEN_POSITION);
		rightBallServo.setAngle(RIGHT_OPEN_POSITION);
    }
    
    public void wheelSpeed(double output) {
    	leftShootWheel.set(output);
    	rightShootWheel.set(output);
    }
    
    public boolean testReady() {
    	return (leftShootWheel.get() == FULLSPEED && rightShootWheel.get() == FULLSPEED);
    }
    
    public void shoot() {
    	leftBallServo.set(LEFT_SHOOT_POSITION);
    	rightBallServo.set(RIGHT_SHOOT_POSITION);
    	DriverStation.reportError("Triggered. \n", false);
    	
    }
    
    public void stopShoot() {
    	leftShootWheel.set(OFF);
    	rightShootWheel.set(OFF);
    	leftBallServo.setAngle(LEFT_OPEN_POSITION);
		rightBallServo.setAngle(RIGHT_OPEN_POSITION);
    }
    
    public void intake(double output) {
    	reverseWheels();
    	wheelSpeed(output);
    	reverseWheels();
    	//fix this later, just tests the angle right now
    	leftBallServo.setAngle(LEFT_HOLD_POSITION);
		rightBallServo.setAngle(RIGHT_HOLD_POSITION);
    }
    
    public void reverseWheels() {
    	leftShootWheel.setInverted(!leftShootWheel.getInverted());
    	rightShootWheel.setInverted(!rightShootWheel.getInverted());
    	
    }
}

