package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EncoderDriveCommand extends Command {

	double distance, power;
	
    public EncoderDriveCommand(double distance, double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	
    	this.distance = distance; //feet
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.autonDrive(Robot.driveSubsystem.accel(power, 0.03, 0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveSubsystem.getEncoders() >= Robot.driveSubsystem.getTicksFromFeet(distance);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.stopMe();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
