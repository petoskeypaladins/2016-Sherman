package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.Robot;
import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.TurretMovementCommand;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
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
	
	private boolean centered;
	
	//Servo Declarations
	Servo leftServo;
	Servo rightServo;
	
	//Tilt Declarations
	DigitalInput tiltMinLimit;
    CANTalon tiltMotor;
    Potentiometer tiltPot;
    DigitalInput tiltMaxLimit;
    Gyro tiltGyro;
    
    boolean overrideRotate = false, overrideTilt = false;

    //Rotate Declarations
    CANTalon rotateMotor;
    public Potentiometer rotatePot;
    
	public TurretSubsystem() {
		leftServo = new Servo(RobotMap.LEFT_BALL_SERVO);
		rightServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
		tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);
		tiltPot = new AnalogPotentiometer(RobotMap.TILT_ANALOG);
		tiltMinLimit = new DigitalInput(RobotMap.TILT_MIN_DIO);
		tiltMaxLimit = new DigitalInput(RobotMap.TILT_MAX_DIO);
		tiltGyro = new AnalogGyro(RobotMap.TILT_GYRO);

		rotateMotor = new CANTalon(RobotMap.ROTATE_SHOOTER_MOTOR);
		rotatePot = new AnalogPotentiometer(RobotMap.ROTATE_ANALOG, 360);
		
    	// TODO - Always check before deploying
		if (Robot.IS_COMPETITION_ROBOT) {
			tiltMotor.setInverted(true);
			rotateMotor.setInverted(true);
		} else {
			tiltMotor.setInverted(false);
			rotateMotor.setInverted(false);
		}
		
		tiltGyro.calibrate();
	}
    
	public void resetGyro() {
		tiltGyro.reset();
	}
	
	public void centerTurret() {
		double error = rotatePot.get();
		double mvmtRatio = 0;
		if ((Math.abs(error) >= 3)) {
	    	if (error < 40 && error > 0) {
		    	// The value is too small for the motor to do anything
		    	mvmtRatio = 0.06;
		    	if (error < 20 && error > 0) {
		    		mvmtRatio = 0.0525;
		    	}
		    } else if (error > -40 && error < 0) {
		    	mvmtRatio = -0.06;
		    	if (error > -20 && error < 0) {
		    		mvmtRatio = -0.0525;
		    	}
		    }
		    
		    Robot.turretSubsystem.rotateTurret(mvmtRatio);
			
		    SmartDashboard.putBoolean("Centered Shooter (x)", false);
		    
	    } else {
	    	SmartDashboard.putBoolean("Centered Shooter (x)", true);
		    Robot.turretSubsystem.rotateTurret(0);
		    centered = true;
	    }
	}
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new TurretMovementCommand());
	}
	
	public void displayData() {
		SmartDashboard.putNumber("Sensor - Rotate Pot", rotatePot.get());
		SmartDashboard.putNumber("Sensor - Tilt Pot", tiltPot.get());
		SmartDashboard.putNumber("Sensor - Left Servo", leftServo.getAngle());
		SmartDashboard.putNumber("Sensor - Right Servo", rightServo.getAngle());
		SmartDashboard.putNumber("Output - Rotate Motor", rotateMotor.get());
		SmartDashboard.putNumber("Output - Tilt Motor", tiltMotor.get());
		SmartDashboard.putNumber("Sensor - Tilt Gyro", tiltGyro.getAngle());
		SmartDashboard.putBoolean("Sensor - Tilt Min Limit", tiltMinLimit.get());
		SmartDashboard.putBoolean("Sensor - Tilt Max Limit", tiltMaxLimit.get());
	}
    
    public void tiltTurret(double output) {
    	if (!overrideTilt) {
	    	if (output < 0) {
	    		if (tiltMinLimit.get()) {
	    			output = 0;
	    		}
	    	} else if (output > 0) {
	    		if (tiltMaxLimit.get()) {
	    			output = 0;
	    		}
	    	}
    	}
    	tiltMotor.set(output);
    }
    
    public void rotateTurret(double output) {
    	double offset;
    	
    	// TODO - Always check before deploying
    	if (Robot.IS_COMPETITION_ROBOT) {
    		offset = 0;
    	} else {
    		offset = 200;
    	}
    	if (!overrideRotate) {
	    	if (output < 0) {
	    		// Left
	    		if (rotatePot.get() >= 216 + offset) {
	    			output = 0;
	    		}
	    	} else if (output > 0) {
	    		// Right
	    		if (rotatePot.get() <= 37 + offset) {
	    			output = 0;
	    		}
	    	}
    	}
    	
    	double error = rotatePot.get();
		double mvmtRatio = 0;
		if ((Math.abs(error) >= 3)) {
			centered = true;
		} else {
			centered = false;
		}
    	
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
    
    public boolean isMinLimitSet() {
    	return tiltMinLimit.get();
    }
    
    public boolean isMaxLimitSet() {
    	return tiltMaxLimit.get();
    }
    
    public void setOverrideRotate(boolean overrideRotate) {
    	this.overrideRotate = overrideRotate;
    }
    
    public void setOverrideTilt(boolean overrideTilt) {
    	this.overrideTilt = overrideTilt;
    }
    
    public boolean getCentered() {
    	return centered;
    }
}

