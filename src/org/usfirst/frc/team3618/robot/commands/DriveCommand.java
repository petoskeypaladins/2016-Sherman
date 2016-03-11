package org.usfirst.frc.team3618.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3618.robot.Robot;
/**
 *
 */
public class DriveCommand extends Command {
	private double drivePower = 2;
	
    public DriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSubsystem);
    }
    
    public DriveCommand(double drivePower) {
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
    	
    	Robot.driveSubsystem.driveMe(Robot.driveSubsystem.accel(left*limit, .03, "l"), 
    			Robot.driveSubsystem.accel(right*limit, .03, "r"));
    	
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
