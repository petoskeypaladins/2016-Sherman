package org.usfirst.frc.team3618.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3618.robot.Robot;
/**
 *
 */
public class DriveCommand extends Command {
	private int drivePower = 2;
	
    public DriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSubsystem);
    }
    
    public DriveCommand(int drivePower) {
    	requires(Robot.driveSubsystem);
    	this.drivePower = drivePower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double left = Robot.oi.driveJoystick.getRawAxis(1);
    	double right = Robot.oi.driveJoystick.getRawAxis(5);
    	double limit = 1.0;
    	if (drivePower > 1) {
    		Robot.driveSubsystem.driveMe(left*limit, right*limit);
    	} else {
    		Robot.driveSubsystem.autonStraightDrive(Robot.driveSubsystem.accel(drivePower, .05));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.deccel(0, .15);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
