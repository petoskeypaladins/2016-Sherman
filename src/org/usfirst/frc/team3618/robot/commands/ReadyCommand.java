package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReadyCommand extends Command {
	
	
	
    public ReadyCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterWheels);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooterWheels.readyShoot();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = 1 - (Robot.oi.shootJoystick.getThrottle() * .5);
    	double limit = 1.0;
    	 
    	System.out.println(Double.toString(speed));
    	
    	Robot.shooterWheels.wheelSpeed(speed*limit);
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
