package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmRoller extends Subsystem {
    CANTalon rollerMotor = new CANTalon(RobotMap.INTAKE_ROLLER_MOTOR);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    }
    
    public void spinIn() {
    	rollerMotor.set(1.0);
    }
    
    public void spinOut() {
    	rollerMotor.set(-1.0);
    }
    
    public void stop() {
    	rollerMotor.set(0.0);
    }
}

