package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.Robot;
import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.ArmLiftCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmsSubsystem extends Subsystem {
    
	CANTalon armMotor;
	DigitalInput minLimitSwitch;
	DigitalInput maxLimitSwitch;

	public ArmsSubsystem() {
		armMotor = new CANTalon(RobotMap.TILT_INTAKE_ARM_MOTOR);
		minLimitSwitch = new DigitalInput(RobotMap.ARM_MIN_LIMIT);
		maxLimitSwitch = new DigitalInput(RobotMap.ARM_MAX_LIMIT);
		
		armMotor.setInverted(true);
	}
	
	public void displayData() {
		SmartDashboard.putNumber("Encoder - Arms", armMotor.getEncPosition());
		SmartDashboard.putNumber("Output - Arms", armMotor.get());
		SmartDashboard.putBoolean("Arm Max Limit", maxLimitSwitch.get());
		SmartDashboard.putBoolean("Arm Min Limit", minLimitSwitch.get());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArmLiftCommand());
    }
    
    public void liftArm(double power) {
		if (power > 0) {
			if (!maxLimitSwitch.get()) {
				power = 0;
			}
		} else if (power < 0) {
			if (!minLimitSwitch.get()) {
				power = 0;
			}
		}
		
		if (!Robot.turretSubsystem.getCentered()) {
			power = 0;
		}
		
		armMotor.set(power);
    }
    
    public void armDown() {
    	armMotor.set(-1);
    }
    
    public boolean getLowerLimitState() {
    	return !minLimitSwitch.get();
    }
}

