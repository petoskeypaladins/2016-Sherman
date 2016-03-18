package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CenterTurretCommand extends Command {

    public CenterTurretCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.turretSubsystem.rotatePot.get() > Robot.turretSubsystem.getCenterPotVal()) {
    		Robot.turretSubsystem.rotateTurret(0.25);
    	} else {
    		Robot.turretSubsystem.rotateTurret(-0.25);
    	}
    	Robot.turretSubsystem.tiltTurret(-0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.turretSubsystem.rotatePot.get() - Robot.turretSubsystem.getCenterPotVal()) <= 3.0 && Robot.turretSubsystem.isMinLimitSet();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.turretSubsystem.rotateTurret(0.0);
    	Robot.turretSubsystem.tiltTurret(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

