package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateBotCommand extends Command {
	double degrees;
	int rotateDirection;
	
    public RotateBotCommand(double degrees) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	this.degrees = degrees;
    	rotateDirection = (int) (degrees / Math.abs(degrees)); //returns 1 or -1
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.rotate(rotateDirection);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.driveSubsystem.getRobotAngle() >= Math.abs(degrees));
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
