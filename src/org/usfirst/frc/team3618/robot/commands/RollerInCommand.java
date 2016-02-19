package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RollerInCommand extends Command {

    public RollerInCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.armRoller);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initialized roller out");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.armRoller.spinIn();
    	System.out.println("Running roller out");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armRoller.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Interrupted");
    	end();
    }
}
