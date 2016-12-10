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
	
	
	private boolean centered;
	double center;
	
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
    double lastShooterCenter;
    
	public TurretSubsystem() {
		tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);
		tiltMinLimit = new DigitalInput(RobotMap.TILT_MIN_DIO);
		tiltMaxLimit = new DigitalInput(RobotMap.TILT_MAX_DIO);

		rotateMotor = new CANTalon(RobotMap.ROTATE_SHOOTER_MOTOR);
		rotatePot = new AnalogPotentiometer(RobotMap.ROTATE_ANALOG, 360);
		
    	if (Robot.IS_COMPETITION_ROBOT) {
    		center = 170;
    	} else {
    		center = 200;
    	}
		
    	// TODO - Always check before deploying
		if (Robot.IS_COMPETITION_ROBOT) {
			tiltMotor.setInverted(false);
			rotateMotor.setInverted(true);
		} else {
			tiltMotor.setInverted(false);
			rotateMotor.setInverted(false);
		}
		rotateGyro.reset();
		resetTiltEncoder();
		centered = true;
	}
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new TurretMovementCommand());
	}
	
	public void displayData() {
		SmartDashboard.putNumber("Sensor - Rotate Pot", rotatePot.get());
		SmartDashboard.putNumber("Output - Rotate Motor", rotateMotor.get());
		SmartDashboard.putNumber("Output - Tilt Motor", tiltMotor.get());
		SmartDashboard.putNumber("Encoder - Tilt Motor", tiltMotor.getEncPosition());
		SmartDashboard.putNumber("Angle - Tilt Motor", getTiltAngle());
		SmartDashboard.putNumber("Encoder Inches - Tilt Motor", getInchesFromTicks());
		SmartDashboard.putNumber("Joystick - Rotate Value", Robot.oi.shootJoystick.getZ());
		SmartDashboard.putNumber("Gyro - Rotate Gyro", rotateGyro.getAngle());
		SmartDashboard.putBoolean("Centered Turret", centered);
		SmartDashboard.putNumber("Rotate Angle", getRotateAngle());
		SmartDashboard.putNumber("Angle From Center", getAngleFromCenter());
		SmartDashboard.putNumber("Last Centered Angle", lastShooterCenter);
		SmartDashboard.putBoolean("Override Rotate", overrideRotate);
		SmartDashboard.putBoolean("Override Tilt", overrideTilt);
		if (rotatePot.get() < (center + 10) && rotatePot.get() > (center - 10)) {
			SmartDashboard.putBoolean("Feedback - Centered Turret", true);
		} else {
			SmartDashboard.putBoolean("Feedback - Centered Turret", true);
		}
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
//		    			output = 0;
//		    			resetTiltEncoder();
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
    	
    	if (output > 1) {
    		output = 1;
    	} else if (output < -1) {
    		output = -1;
    	}
    	
    	tiltMotor.set(output);
    }
    
    public void rotateTurret(double output) {
    	if (Robot.IS_COMPETITION_ROBOT) {
	    	if (!overrideRotate) {
		    	if (output < 0) {
		    		// Left
		    		if (rotatePot.get() >= center + 100) {
		    			output = 0;
		    		}
		    	} else if (output > 0) {
		    		// Right
		    		if (rotatePot.get() <= center - 150) {
		    			output = 0;
		    		}
		    	}
	    	}
    	}
    	
//    	if (output > .7) {
//    		System.out.println("PREVIOUS OUTPUT: " + output);
//    		output = .7;
//    	} else if (output < -.7) {
//    		System.out.println("PREVIOUS OUTPUT: " + output);
//    		output = -.7;
//    	}
    	
    	rotateMotor.set(output);
    	
    	final double DEGREES_BEFORE_BREAKING = 3.5;
    	if (Math.abs(getAngleFromCenter()) <= DEGREES_BEFORE_BREAKING && !centered) {
    		centered = true;
    	} else if (Math.abs(getAngleFromCenter()) > DEGREES_BEFORE_BREAKING) {
    		centered = false;
    	}
    }
    
    public boolean tiltToDegree(double degree) {
    	double angleDifference = degree - getTiltAngle();
    	double direction = angleDifference / Math.abs(angleDifference);
    	if (Math.abs(angleDifference) > 3) {
    		double output = angleDifference / 5;
    		tiltTurret(output);
    		return false;
    	} else {
    		tiltTurret(0);
    		return true;
    	}
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
    
    public double getCenterPotVal() {
    	if (Robot.IS_COMPETITION_ROBOT) {
    		return 200.0;
    	} else {
    		// TODO - Configure value for practice bot
    		return 200.0;
    	}
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
    
    public double getTiltPos() {
    	return -tiltMotor.getEncPosition();
    }
    
    public double getRotateAngle() {
    	return rotateGyro.getAngle();
    }
    
    public double getTiltAngle() {
    	double a;
    	double b;
    	double c;
    	double angle;
    	
    	// TODO - Configure values for practice robot
    	if (Robot.IS_COMPETITION_ROBOT) {
    		a = -5.4012120941422 * Math.pow(10, -9);
        	b = .0012074873980199;
        	c = 5.683275712955;
    	} else {
    		a = -5.4012120941422 * Math.pow(10, -9);
        	b = .0012074873980199;
        	c = 5.683275712955;
    	}
    	angle = ((a * Math.pow(getTiltPos(), 2)) + (b * getTiltPos()) + c);
    			
    	return angle;
    }
    
    public void resetGyro() {
    	rotateGyro.reset();
    }
    
    public double getAngleFromCenter() {
    	return rotatePot.get() - center;
    }
    
    public void centerTurret() {
    	final double ratio = 35;
    	double power = getAngleFromCenter() / ratio;
//    	if (centered) {
//    		power = 0;
//    	}
    	rotateTurret(power);
    }
}
