package org.usfirst.frc.team3618.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	
	
	//CAN Motors
	//PD Board for Drive is 0, 1, 14, 15
	public static int ROTATE_SHOOTER_MOTOR = 1;
	public static int TILT_SHOOTER_MOTOR = 2;
//	public static int 
	public static int TILT_INTAKE_ARM_MOTOR = 4;
	public static int LEFT_FRONT_MOTOR = 5;
	public static int LEFT_REAR_MOTOR = 6;
	public static int RIGHT_FRONT_MOTOR = 7;
	public static int RIGHT_REAR_MOTOR = 8;
	public static int INTAKE_ROLLER_MOTOR = 9;
	public static int RIGHT_SHOOT_WHEEL_MOTOR = 10;
	public static int LEFT_SHOOT_WHEEL_MOTOR = 11;
	
	public static int LEFT_BALL_SERVO = 0;
	public static int RIGHT_BALL_SERVO = 1;
		
}
