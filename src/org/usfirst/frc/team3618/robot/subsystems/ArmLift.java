package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.ArmLiftCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmLift extends Subsystem {
    CANTalon intakeArmMotor = new CANTalon(RobotMap.TILT_INTAKE_ARM_MOTOR);
	
	

    public void initDefaultCommand() {
    	setDefaultCommand(new ArmLiftCommand());
    	
    }
    
    
    public void armLift(double lift){
		intakeArmMotor.set(lift);
		
	}
}

