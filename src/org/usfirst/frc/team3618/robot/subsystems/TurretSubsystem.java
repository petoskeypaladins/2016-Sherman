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
    Gyro rotateGyro = new AnalogGyro(RobotMap.ROTATE_GYRO);
    
    
    boolean overrideRotate = false, overrideTilt = false;

    //Rotate Declarations
    CANTalon rotateMotor;
    public Potentiometer rotatePot;
    
	public TurretSubsystem() {
		leftServo = new Servo(RobotMap.LEFT_BALL_SERVO);
		rightServo = new Servo(RobotMap.RIGHT_BALL_SERVO);
		tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);
		tiltMinLimit = new DigitalInput(RobotMap.TILT_MIN_DIO);
		tiltMaxLimit = new DigitalInput(RobotMap.TILT_MAX_DIO);

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
		rotateGyro.reset();
		resetTiltEncoder();
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
		SmartDashboard.putNumber("Sensor - Left Servo", leftServo.getAngle());
		SmartDashboard.putNumber("Sensor - Right Servo", rightServo.getAngle());
		SmartDashboard.putNumber("Output - Rotate Motor", rotateMotor.get());
		SmartDashboard.putNumber("Output - Tilt Motor", tiltMotor.get());
		SmartDashboard.putNumber("Encoder - Tilt Motor", tiltMotor.getEncPosition());
		SmartDashboard.putNumber("Angle - Tilt Motor", getTiltAngle());
		SmartDashboard.putNumber("Encoder Inches - Tilt Motor", getInchesFromTicks());
		SmartDashboard.putNumber("Joystick - Rotate Value", Robot.oi.shootJoystick.getZ());
		SmartDashboard.putNumber("Gyro - Rotate Gyro", rotateGyro.getAngle());
		if (Robot.IS_COMPETITION_ROBOT) {
			SmartDashboard.putBoolean("Sensor - Tilt Min Limit", tiltMinLimit.get());
			SmartDashboard.putBoolean("Sensor - Tilt Max Limit", tiltMaxLimit.get());
		}
	}
    
    public void tiltTurret(double output) {
    	if (Robot.IS_COMPETITION_ROBOT) {
	    	if (!overrideTilt) {
		    	if (output < 0) {
		    		if (tiltMinLimit.get()) {
		    			output = 0;
		    			resetTiltEncoder();
		    		}
		    	} else if (output > 0) {
		    		if (tiltMaxLimit.get()) {
		    			output = 0;
		    		}
		    	}
	    	}
    	}
    	
    	if (tiltMotor.getEncPosition() > 0) {
    		resetTiltEncoder();
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
    	if (Robot.IS_COMPETITION_ROBOT) {
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
    	}
    	
    	if (output > .4) {
    		output = .4;
    	}
    	
    	rotateMotor.set(output);
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
    
    public void resetTiltEncoder() {
    	tiltMotor.setEncPosition(0);
    }
    
    public double getInchesFromTicks() {
    	int TICKS_IN_INCH = 30621;
    	return -tiltMotor.getEncPosition() / ((double) TICKS_IN_INCH);
    }
    
    public double getRotateAngle() {
    	return rotateGyro.getAngle();
    }
    
    public double getTiltAngle() {
    	double a;
    	double b;
    	double c;
    	double angle;
    	
    	// TODO - Configure values for real robot
    	if (Robot.IS_COMPETITION_ROBOT) {
    		a = 3.875;
        	b = 2.25;
        	c = 6.34 - getInchesFromTicks();
    	} else {
    		a = 4;
        	b = 2.75;
        	c = 6.75 - getInchesFromTicks();
    	}
    	angle = (double) Math.toDegrees(Math.acos((Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2)) / (-2 * b * c)));
    	
    	return angle;
    }
    
    public void resetGyros() {
    	rotateGyro.reset();
    }
    
}

