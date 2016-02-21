package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {
	
	CANTalon leftFrontMotor;
	CANTalon leftRearMotor;
	CANTalon rightFrontMotor;
	CANTalon rightRearMotor;
	
	RobotDrive myRobotDrive;

	public DriveSubsystem() {
		leftFrontMotor = new CANTalon(RobotMap.LEFT_FRONT_MOTOR);
		leftRearMotor = new CANTalon(RobotMap.LEFT_REAR_MOTOR);
		rightFrontMotor = new CANTalon(RobotMap.RIGHT_FRONT_MOTOR);
		rightRearMotor = new CANTalon(RobotMap.RIGHT_REAR_MOTOR);
		
		myRobotDrive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
		
		leftFrontMotor.setInverted(true);
		rightFrontMotor.setInverted(true);
	}
    
    public void initDefaultCommand() {
    	setDefaultCommand(new DriveCommand());
    }
    
    public void DriveMe (double left, double right) {
    	myRobotDrive.tankDrive(left, right);
    }
}

