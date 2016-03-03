package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.DriveCommand;
import org.usfirst.frc.team3618.sensorlib.ADIS16448_IMU;
import org.usfirst.frc.team3618.sensorlib.ADXRS453Gyro;

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
	
	ADIS16448_IMU imuGyro;
	ADXRS453Gyro spiGyro;
	
	public static final double TICKS_PER_FOOT = 415.25;
	
	private double kP = 0.3;
	
	public DriveSubsystem() {
		leftFrontMotor = new CANTalon(RobotMap.LEFT_FRONT_MOTOR);
		leftRearMotor = new CANTalon(RobotMap.LEFT_REAR_MOTOR);
		rightFrontMotor = new CANTalon(RobotMap.RIGHT_FRONT_MOTOR);
		rightRearMotor = new CANTalon(RobotMap.RIGHT_REAR_MOTOR);
		
		myRobotDrive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
		
		imuGyro = new ADIS16448_IMU();
		spiGyro = new ADXRS453Gyro();
		
		imuGyro.calibrate();
		imuGyro.reset();
		
		spiGyro.startThread();
		
		rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A, RobotMap.RIGHT_DRIVE_ENCODER_B, false);
		leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A, RobotMap.LEFT_DRIVE_ENCODER_B, true);
		
		leftEncoder.reset();
		rightEncoder.reset();
		
		leftFrontMotor.setInverted(true);
		rightFrontMotor.setInverted(true);
		
		leftRearMotor.setSafetyEnabled(false);
		leftFrontMotor.setSafetyEnabled(false);
		rightFrontMotor.setSafetyEnabled(false);
		rightRearMotor.setSafetyEnabled(false);
		myRobotDrive.setSafetyEnabled(false);
	}
    
	public void displayData() {
		SmartDashboard.putNumber("Encoder - Left Drive", leftEncoder.get());
		SmartDashboard.putNumber("Encoder - Right Drive", rightEncoder.get());
		SmartDashboard.putNumber("Encoder - Drive Train", getEncoders());
		SmartDashboard.putNumber("Output - LF Drive", leftFrontMotor.get());
		SmartDashboard.putNumber("Output - LR Drive", leftRearMotor.get());
		SmartDashboard.putNumber("Output - RF Drive", rightFrontMotor.get());
		SmartDashboard.putNumber("Output - RR Drive", rightRearMotor.get());
		SmartDashboard.putNumber("IMU Gyro Angle", imuGyro.getAngleX());
		SmartDashboard.putNumber("SPI Gyro Angle", spiGyro.getAngle());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new DriveCommand());
    }
    
    public void stopMe() {
    	myRobotDrive.drive(0.0, 0.0);
    }
    
    public void DriveMe(double left, double right) {
    	myRobotDrive.tankDrive(left, right);
    }
    
    public void AutonDrive(double power) {
    	myRobotDrive.tankDrive(-power, -power);
    }
    
    public void AutonStraightDrive(double power) {
    	myRobotDrive.drive(power, -spiGyro.getAngle()*this.kP);
    }
    
    public double getFeetFromTicks(double ticks) {
    	return ticks / TICKS_PER_FOOT;
    }
    
    public double getTicksFromFeet(double distance) {
    	return distance * TICKS_PER_FOOT;
    }
    
    public void resetEncoders() {
    	leftEncoder.reset();
		rightEncoder.reset();
    }
    
    public double accel(double speed, double time, double ramp) {
    	double curSpeed = (speed*time)/ramp;
    	if(curSpeed >= speed) {
    		curSpeed = speed;
    	}
		return curSpeed;   
    }
    
    public double decel(double speed, double time, double ramp) {
    	double curSpeed = (speed*time)/ramp;
    	if(curSpeed >= speed) {
    		curSpeed = speed;
    	}
		return curSpeed;   
    }
    
    public double getEncoders() {
    	return rightEncoder.get();
    }
    
}

