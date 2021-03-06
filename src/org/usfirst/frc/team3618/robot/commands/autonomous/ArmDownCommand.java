package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmDownCommand extends Command {

    public ArmDownCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.armsSubsystem.armDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || Robot.armsSubsystem.getLowerLimitState();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armsSubsystem.liftArm(0);
    	System.out.println("arms down ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("arm down interrupted");
    }
}
