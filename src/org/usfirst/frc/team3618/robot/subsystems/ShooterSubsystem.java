package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
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
		
		rightMotor.setInverted(false); //false = competition true = practice
	}
	
	public void displayData() {
		SmartDashboard.putNumber("Left Turret Wheel", leftMotor.getEncVelocity());
		SmartDashboard.putNumber("Right Turret Wheel", rightMotor.getEncVelocity());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void spinShooter(double power) {
		leftMotor.set(power);
		rightMotor.set(power);
	}
    
}

