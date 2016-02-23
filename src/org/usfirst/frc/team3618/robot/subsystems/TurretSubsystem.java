package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.TurretMovementCommand;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    Potentiometer tiltPot;
    DigitalInput tiltMinLimit;
    DigitalInput tiltMaxLimit;
    
    //Rotate Declarations
    CANTalon rotateMotor;
    Potentiometer rotatePot;
    
	public TurretSubsystem() {
		leftMotor = new CANTalon(RobotMap.LEFT_SHOOT_WHEEL_MOTOR);
		rightMotor = new CANTalon(RobotMap.RIGHT_SHOOT_WHEEL_MOTOR);
		leftServo = new Servo(RobotMap.LEFT_BALL_SERVO);
		rightServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
		tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);
		tiltPot = new AnalogPotentiometer(RobotMap.TILT_ANALOG);
		tiltMinLimit = new DigitalInput(RobotMap.TILT_MIN_DIO);
		tiltMaxLimit = new DigitalInput(RobotMap.TILT_MAX_DIO);
		
		rotateMotor = new CANTalon(RobotMap.ROTATE_SHOOTER_MOTOR);
		rotatePot = new AnalogPotentiometer(RobotMap.ROTATE_ANALOG, 360);
		
		tiltMotor.setInverted(true);
		rotateMotor.setInverted(true);
		
		rightMotor.setInverted(true);
	}
    
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new TurretMovementCommand());
	}
	
	public void displayData() {
		SmartDashboard.putNumber("Rotate Pot", rotatePot.get());
		SmartDashboard.putNumber("Angle", tiltPot.get());
		SmartDashboard.putBoolean("Tilt Min Limit", tiltMinLimit.get());
		SmartDashboard.putBoolean("Tilt Max Limit", tiltMaxLimit.get());
		SmartDashboard.putNumber("Tilt Encoder", tiltMotor.getEncPosition());
		SmartDashboard.putNumber("Rotate Encoder", rotateMotor.getEncPosition());
	}
    
    public void tiltTurret(double output) {
    	if (output < 0) {
    		if (tiltMinLimit.get() == true) {
    			output = 0;
    		}
    	} else if (output > 0) {
    		if (tiltMaxLimit.get() == true) {
    			output = 0;
    		}
    	}
    	SmartDashboard.putNumber("Tilt Value", output);
    	tiltMotor.set(output);
    }
    
    public void rotateTurret(double output) {
    	rotateMotor.set(output);
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
}

