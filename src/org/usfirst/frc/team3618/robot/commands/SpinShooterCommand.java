package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpinShooterCommand extends Command {
	
	
    public SpinShooterCommand() {
        requires(Robot.shooterWheels);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("SHOOTER READY");
    	// Brendon is abusing his power
    	// so is James
    	//except I have no power
    	//that is true
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = 1 - (Robot.oi.shootJoystick.getThrottle() * .5);
    	Robot.shooterWheels.readyShoot(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterWheels.stopIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    
}
