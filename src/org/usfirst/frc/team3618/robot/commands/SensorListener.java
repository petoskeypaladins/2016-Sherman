package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SensorListener extends Command {
	public DigitalInput frontSensor;
	public DigitalInput backSensor;
	
    public SensorListener() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	frontSensor = new DigitalInput(RobotMap.FRONT_BALL_SENSOR);
    	backSensor = new DigitalInput(RobotMap.BACK_BALL_SENSOR);
    	System.out.println("initailized sensors");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (backSensor.get()) {
			new HoldBallCommand();
		}
		
		if (frontSensor.get()) {
			new IntakeStartCommand();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
