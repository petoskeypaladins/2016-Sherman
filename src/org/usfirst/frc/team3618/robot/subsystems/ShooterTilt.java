package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.TiltCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterTilt extends Subsystem {
    CANTalon tiltMotor = new CANTalon(RobotMap.TILT_SHOOTER_MOTOR);
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        setDefaultCommand(new TiltCommand());
    }
    
    
    public void tilt(double output) {
    	tiltMotor.set(output);
    }
}

