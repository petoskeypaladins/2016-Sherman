package org.usfirst.frc.team3618.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3618.robot.Robot;
/**
 *
 */
public class DriveCommand extends Command {
	
    public DriveCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSubsystem);
    }
    
    public DriveCommand(double drivePower) {
    	requires(Robot.driveSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
    	double left = Robot.oi.driveJoystick.getRawAxis(1);
    	double right = Robot.oi.driveJoystick.getRawAxis(5);
    	Robot.driveSubsystem.driveMe(left, right);
    	
    }

    protected boolean isFinished() {
    	return false;
    }
    
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
