package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmLiftCommand extends Command {

    public ArmLiftCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.armsSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = Robot.oi.driveJoystick.getRawAxis(2) - Robot.oi.driveJoystick.getRawAxis(3);
    	double limit = 1.0;
    	SmartDashboard.putNumber("Left trigger", Robot.oi.driveJoystick.getRawAxis(2));
    	SmartDashboard.putNumber("Right trigger", Robot.oi.driveJoystick.getRawAxis(3));
    	
    	Robot.armsSubsystem.liftArm(speed * limit);    	
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
