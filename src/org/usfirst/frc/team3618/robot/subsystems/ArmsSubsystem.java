package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.ArmLiftCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmsSubsystem extends Subsystem {
    
	CANTalon armMotor;

	public ArmsSubsystem() {
		armMotor = new CANTalon(RobotMap.TILT_INTAKE_ARM_MOTOR);

		armMotor.setInverted(true);
	}
	
	public void displayData() {
		SmartDashboard.putNumber("Encoder - Arms", armMotor.getEncPosition());
		SmartDashboard.putNumber("Output - Arms", armMotor.get());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArmLiftCommand());
    }
    
    public void liftArm(double power) {
		armMotor.set(power);
    }
    
}

