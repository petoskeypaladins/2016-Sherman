
package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 *
 */
public class Drive extends Subsystem {
	CANTalon leftFrontMotor = new CANTalon(RobotMap.LEFT_FRONT_MOTOR);
	CANTalon leftRearMotor = new CANTalon(RobotMap.LEFT_REAR_MOTOR);
	CANTalon rightFrontMotor = new CANTalon(RobotMap.RIGHT_FRONT_MOTOR);
	CANTalon rightRearMotor = new CANTalon(RobotMap.RIGHT_REAR_MOTOR);
	
	RobotDrive myRobotDrive =new RobotDrive (leftFrontMotor,
			leftRearMotor,
			rightFrontMotor,
			rightRearMotor);
	
	public Gyro firstGyro = new AnalogGyro(0);


	public Drive() {
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
}

