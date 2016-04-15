package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurretMovementCommand extends Command {
	
    public TurretMovementCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.turretSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Code for tilt movement
    	double tilt = Robot.oi.shootJoystick.getY();
    	double tLimit = 1.;
    	
    	if (Robot.oi.setLowTurretSpeed.get()) {
    		Robot.turretSubsystem.setTiltLimit(0.4);
    	} else {
    		Robot.turretSubsystem.setTiltLimit(1.0);
    	}
    	
    	if(Math.abs(tilt) < 0.2){
    		tilt = 0;
    	}
    	
    	Robot.turretSubsystem.tiltTurret(tilt*tLimit);
    	
    	//Code for rotation movement
    	double rotate = Robot.oi.shootJoystick.getZ();
    	double rLimit = 0.4;
    	
    	if (Robot.oi.setLowTurretSpeed.get()) {
    		Robot.turretSubsystem.setRotateLimit(0.5);
    	} else {
    		Robot.turretSubsystem.setRotateLimit(1.0);
    	}
    	
    	Robot.turretSubsystem.rotateTurret(rotate*rLimit);
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
