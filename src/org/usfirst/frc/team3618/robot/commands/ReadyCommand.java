package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReadyCommand extends Command {
	
	
    public ReadyCommand() {
        requires(Robot.shooterWheels);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double speed = 1 - (Robot.oi.shootJoystick.getThrottle() * .5);
    	System.out.println("SHOOTER READY");
    	Robot.shooterWheels.readyShoot(speed);
    	Robot.shooterServos.cameraUp();
    	// Brendon is abusing his power
    	// so is James
    	//except I have no power
    	//that is true
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    
}
