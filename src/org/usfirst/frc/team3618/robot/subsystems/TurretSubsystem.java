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
	
	//Tilt Declarations
	DigitalInput tiltMinLimit;
    CANTalon tiltMotor;
    Potentiometer tiltPot;
    DigitalInput tiltMaxLimit;
    Gyro rotateGyro = new AnalogGyro(RobotMap.ROTATE_GYRO);
    
	private boolean centered;
    
    boolean overrideRotate = false, overrideTilt = false;

    //Rotate Declarations
    CANTalon rotateMotor;
    public Potentiometer rotatePot;
    
	public TurretSubsystem() {
		tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);
		tiltMinLimit = new DigitalInput(RobotMap.TILT_MIN_DIO);
		tiltMaxLimit = new DigitalInput(RobotMap.TILT_MAX_DIO);

		rotateMotor = new CANTalon(RobotMap.ROTATE_SHOOTER_MOTOR);
		rotatePot = new AnalogPotentiometer(RobotMap.ROTATE_ANALOG, 360);
		
		
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
		double center = 200.0;
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
    	double center = 200.0;
    	
    	// TODO - Always check before deploying
    	if (Robot.IS_COMPETITION_ROBOT) {
    		center = 200;
    	} else {
    		center = 200;
    	}
    	if (Robot.IS_COMPETITION_ROBOT) {
	    	if (!overrideRotate) {
		    	if (output < 0) {
		    		// Left
		    		if (rotatePot.get() >= center + 90) {
		    			System.out.println("LEFT LIMIT REACHED");
		    			output = 0;
		    		}
		    	} else if (output > 0) {
		    		// Right
		    		if (rotatePot.get() <= center - 90) {
		    			System.out.println("RIGHT LIMIT REACHED");
		    			output = 0;
		    		}
		    	}
	    	}
    	}
    	
    	if (output > .4) {
    		System.out.println("Previouos output: " + output);
    		output = .4;
    	} else if (output < -.4) {
    		System.out.println("Previouos output: " + output);
    		output = -.4;
    	}
    	
    	rotateMotor.set(output);
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
    
    public double getTiltTicks() {
    	return tiltMotor.getEncPosition();
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

