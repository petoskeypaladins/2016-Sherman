package org.usfirst.frc.team3618.robot;

public class RobotMap {
	
	//CAN Motors
	public static int ROTATE_SHOOTER_MOTOR = 1;
	public static int TILT_SHOOTER_MOTOR = 2;
	
	public static int TILT_INTAKE_ARM_MOTOR = 4;
	public static int LEFT_FRONT_MOTOR = 5;
	public static int LEFT_REAR_MOTOR = 6;
	public static int RIGHT_FRONT_MOTOR = 7;
	public static int RIGHT_REAR_MOTOR = 8;
	public static int INTAKE_ROLLER_MOTOR = 9;
	public static int RIGHT_SHOOT_WHEEL_MOTOR = 10;
	public static int LEFT_SHOOT_WHEEL_MOTOR = 11;
	
	//PWM
	public static int LEFT_BALL_SERVO = 0;
	public static int RIGHT_BALL_SERVO = 1;
	
	//ANALOG
	public static int ROTATE_GYRO = 0; //gyro on the shooter
	public static int ROTATE_ANALOG = 1; //rotate potentiometer
	
	//DIGITAL
	public static int LEFT_DRIVE_ENCODER_A = 0; //TALON ENCODERS
	public static int LEFT_DRIVE_ENCODER_B = 1; //TALON ENCODERS
	public static int RIGHT_DRIVE_ENCODER_A = 2; //TALON ENCODERS
	public static int RIGHT_DRIVE_ENCODER_B = 3; //TALON ENCODERS
	public static int BACK_BALL_SENSOR = 4; //optical
	public static int TILT_MIN_DIO = 5; //comp only
	public static int TILT_MAX_DIO = 6; //comp only
	public static int ARM_MAX_LIMIT = 7; //comp only
	public static int ARM_MIN_LIMIT = 8; //comp only
	
	
}
