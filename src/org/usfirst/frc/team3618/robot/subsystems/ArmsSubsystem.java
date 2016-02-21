package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmsSubsystem extends Subsystem {
    
	CANTalon armMotor;
	CANTalon rollerMotor;

	public ArmsSubsystem() {
		armMotor = new CANTalon(RobotMap.TILT_INTAKE_ARM_MOTOR);
		rollerMotor = new CANTalon(RobotMap.INTAKE_ROLLER_MOTOR);
	}
	
    public void initDefaultCommand() {
    }
    
    public void liftArm(double power) {
		armMotor.set(power);
    }
    
    public void spinRoller(double power) {
    	rollerMotor.set(power);
    }
    
}

