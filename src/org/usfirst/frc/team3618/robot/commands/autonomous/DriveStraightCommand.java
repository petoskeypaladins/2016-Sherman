package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightCommand extends Command {

	Timer timer;
	double timeUntilStop;
	
    public DriveStraightCommand(double timeUntilStop) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	this.timeUntilStop = timeUntilStop;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = new Timer();
    	
    	timer.start();
    	timer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.autonDrive(-1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= timeUntilStop;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.stopMe();
    	System.out.println("end drive called");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
