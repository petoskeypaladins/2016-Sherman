package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.Robot;
import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

	//Shooter declarations 
	CANTalon leftMotor;
	CANTalon rightMotor;	
	
	public ShooterSubsystem() {
		leftMotor = new CANTalon(RobotMap.LEFT_SHOOT_WHEEL_MOTOR);
		rightMotor = new CANTalon(RobotMap.RIGHT_SHOOT_WHEEL_MOTOR);
		
    	// TODO - Always check before deploying
		if (Robot.IS_COMPETITION_ROBOT) {
			rightMotor.setInverted(false);
		} else {
			rightMotor.setInverted(false);
		}
	}
	
	public void displayData() {
//		SmartDashboard.putNumber("Left Turret Wheel", leftMotor.getEncVelocity());
//		SmartDashboard.putNumber("Right Turret Wheel", rightMotor.getEncVelocity());
		SmartDashboard.putNumber("Output - Left Turret Wheel", leftMotor.get());
		SmartDashboard.putNumber("Output - Right Turret Wheel", rightMotor.get());
	}
	
    public void initDefaultCommand() {
    }
    
    public void spinShooter(double power) {
		leftMotor.set(power);
		rightMotor.set(power);
	}
    
}

