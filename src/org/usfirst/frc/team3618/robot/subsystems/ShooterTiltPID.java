package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class ShooterTiltPID extends PIDSubsystem {

	   CANTalon tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);
	   
    // Initialize your subsystem here
	   
	   
    public ShooterTiltPID() {
    	
    	super("ShooterTilt", 2.0, 0.0, 0.0);
    	
    	setAbsoluteTolerance(0.05);
    	
    	getPIDController().setContinuous(false);
    	
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
    	
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
    	
    	tiltMotor.pidWrite(output);
    	
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	
    	
    }
}
