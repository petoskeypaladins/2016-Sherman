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
	
	public ArmLift() {
		intakeArmMotor.setInverted(true);
		
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new ArmLiftCommand());
    }
    
    
    public void liftArm(double output) {
			intakeArmMotor.set(output);
	}
}

