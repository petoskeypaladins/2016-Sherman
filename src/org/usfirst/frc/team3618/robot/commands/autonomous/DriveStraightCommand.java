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
	boolean accel;
	boolean straightDrive;
	
    public DriveStraightCommand(double timeUntilStop) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	this.accel = false;
    	this.straightDrive = false;
    	this.timeUntilStop = timeUntilStop;
    }

    public DriveStraightCommand(double timeUntilStop, boolean accel) {
    	requires(Robot.driveSubsystem);
    	this.accel = accel;
    	this.straightDrive = false;
    	this.timeUntilStop = timeUntilStop;
    }
    
    public DriveStraightCommand(double timeUntilStop, boolean accel, boolean straightDrive) {
    	requires(Robot.driveSubsystem);
    	this.accel = accel;
    	this.straightDrive = straightDrive;
    	this.timeUntilStop = timeUntilStop;
    }
    
    protected void initialize() {
    	System.out.println("Initialize: " + timeUntilStop);
    	timer = new Timer();
    	
    	timer.start();
    	timer.reset();
    }

    protected void execute() {
    	if (!accel) {
    		if (straightDrive) {
    			Robot.driveSubsystem.autonStraightDrive(1.0);
    		} else {
    			Robot.driveSubsystem.autonDrive(1.0);
    		}
    	} else {
    		if (straightDrive) {
    			Robot.driveSubsystem.autonStraightDrive(Robot.driveSubsystem.accel(1.0, timeSinceInitialized(), 0.75));
    		} else {
    			Robot.driveSubsystem.autonDrive(Robot.driveSubsystem.accel(1.0, timeSinceInitialized(), 0.75));
    		}
    	}
    }

    protected boolean isFinished() {
        return timer.get() >= timeUntilStop;
    }

    protected void end() {
    	Robot.driveSubsystem.stopMe();
    }

    protected void interrupted() {
    }
}
