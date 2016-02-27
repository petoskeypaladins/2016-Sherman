package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import com.ni.vision.NIVision.RotatedRect;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetTurretCommand extends Command {

	boolean hasReachedOnce;
	
    public ResetTurretCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.turretSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	hasReachedOnce = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (Robot.turretSubsystem.rotatePot.get() < 216 && Robot.turretSubsystem.rotatePot.get() > 127) {
    		Robot.turretSubsystem.rotateTurret(0.35);
    	} else {
    		Robot.turretSubsystem.rotateTurret(-0.35);
    	}
    	
    	Robot.turretSubsystem.tiltTurret(-0.5);
    	
    	if (Robot.turretSubsystem.rotatePot.get() == 127) {
    		hasReachedOnce = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.turretSubsystem.tiltMinLimit.get() && hasReachedOnce);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
