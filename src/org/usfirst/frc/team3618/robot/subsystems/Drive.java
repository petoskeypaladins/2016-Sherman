
package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Subsystem {
	CANTalon leftFrontMotor = new CANTalon(RobotMap.LEFT_FRONT_MOTOR);
	CANTalon leftRearMotor = new CANTalon(RobotMap.LEFT_REAR_MOTOR);
	CANTalon rightFrontMotor = new CANTalon(RobotMap.RIGHT_FRONT_MOTOR);
	CANTalon rightRearMotor = new CANTalon(RobotMap.RIGHT_REAR_MOTOR);
	
	Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	
	RobotDrive myRobotDrive =new RobotDrive (leftFrontMotor,
			leftRearMotor,
			rightFrontMotor,
			rightRearMotor);
	
	public Gyro firstGyro = new AnalogGyro(0);


	public Drive() {
		leftEncoder.reset();
		rightEncoder.reset();
		
		leftFrontMotor.setInverted(true);
		rightFrontMotor.setInverted(true);
	}
	
	public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
		setDefaultCommand(new DriveCommand());
	}
	    
	public void DriveMe (double left, double right) {
		myRobotDrive.tankDrive(left, right);
	}
	
	public void displayEncoderData() {
		SmartDashboard.putNumber("Left Encoder", leftEncoder.get());
		SmartDashboard.putNumber("Right Encoder", rightEncoder.get());
	}
	
}

