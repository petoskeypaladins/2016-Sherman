package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem extends Subsystem {
	
	CANTalon leftFrontMotor;
	CANTalon leftRearMotor;
	CANTalon rightFrontMotor;
	CANTalon rightRearMotor;
	
	RobotDrive myRobotDrive;

	Encoder rightEncoder;
	Encoder leftEncoder;
	
	public DriveSubsystem() {
		leftFrontMotor = new CANTalon(RobotMap.LEFT_FRONT_MOTOR);
		leftRearMotor = new CANTalon(RobotMap.LEFT_REAR_MOTOR);
		rightFrontMotor = new CANTalon(RobotMap.RIGHT_FRONT_MOTOR);
		rightRearMotor = new CANTalon(RobotMap.RIGHT_REAR_MOTOR);
		
		myRobotDrive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
		
		rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A, RobotMap.RIGHT_DRIVE_ENCODER_B, false, Encoder.EncodingType.k4X);
		leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A, RobotMap.LEFT_DRIVE_ENCODER_B, false, Encoder.EncodingType.k4X);
		
		leftFrontMotor.setInverted(true);
		rightFrontMotor.setInverted(true);
	}
    
	public void displayData() {
		SmartDashboard.putNumber("Left Encoder", leftEncoder.get());
		SmartDashboard.putNumber("Right Encoder", rightEncoder.get());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new DriveCommand());
    }
    
    public void DriveMe (double left, double right) {
    	myRobotDrive.tankDrive(left, right);
    }
}

