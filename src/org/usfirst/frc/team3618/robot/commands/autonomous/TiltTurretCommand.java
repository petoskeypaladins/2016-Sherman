package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TiltTurretCommand extends Command {

	boolean atAngle;
	final int TARGET_ANGLE = 25000;
	
    public TiltTurretCommand() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	atAngle = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.turretSubsystem.tiltTurret(0.3);
    	atAngle = Robot.turretSubsystem.getTiltTicks() >= TARGET_ANGLE;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return atAngle;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.turretSubsystem.tiltTurret(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
