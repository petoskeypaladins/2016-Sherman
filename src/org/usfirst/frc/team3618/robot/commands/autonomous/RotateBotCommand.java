package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateBotCommand extends Command {
	int rotateDirection;
	
	boolean isCentered;
	
    public RotateBotCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	rotateDirection = (int) -(Robot.driveSubsystem.getRobotAngle() / 
    			Math.abs(Robot.driveSubsystem.getRobotAngle())); //returns 1 or -1 based ono direction
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isCentered = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.rotate(rotateDirection);
    	isCentered = ((int) (Math.abs(Robot.driveSubsystem.getRobotAngle()) / 3)) == 0; //stops turning within 3 degrees
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isCentered;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.rotate(0);
    }

    // Called when another command 0111which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
