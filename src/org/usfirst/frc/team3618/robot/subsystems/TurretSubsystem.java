package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TurretSubsystem extends Subsystem {
	
	private final double LEFT_SHOOT_POSITION = 0;
	private final double LEFT_OPEN_POSITION = 90;
	
	private final double LEFT_HOLD_POSITION = 135;
	
	private final double RIGHT_SHOOT_POSITION = 180;
	private final double RIGHT_OPEN_POSITION = 90;
	
	private final double RIGHT_HOLD_POSITION = 45;
	
	//Shooter declarations 
	
	CANTalon leftMotor;
	CANTalon rightMotor;
	
	//Servo Declarations
	
	Servo leftServo;
	Servo rightServo;	

	//Tilt Declarations
	
    CANTalon tiltMotor;
    
    //Rotate Declarations
    
    CANTalon rotateMotor;
    
	public TurretSubsystem() {
		leftMotor = new CANTalon(RobotMap.LEFT_SHOOT_WHEEL_MOTOR);
		rightMotor = new CANTalon(RobotMap.RIGHT_SHOOT_WHEEL_MOTOR);
		
		leftServo = new Servo(RobotMap.LEFT_BALL_SERVO);
		rightServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
		
		rotateMotor = new CANTalon(RobotMap.ROTATE_SHOOTER_MOTOR);

		tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);

		rightMotor.setInverted(true);
		
		tiltMotor.setInverted(true);
	    
	}
    
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
	}
	
	public void spinShooter(double power) {
		leftMotor.set(power);
		rightMotor.set(power);
	}
	
	public void shoot() {
		leftServo.set(LEFT_SHOOT_POSITION);
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
    
    public void tiltTurret(double output) {
    	tiltMotor.set(output);
    }
    
    public void rotateTurret(double output) {
    	rotateMotor.set(output);
    }
    
}

