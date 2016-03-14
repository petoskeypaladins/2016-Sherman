package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TiltTurretToAngleCommand extends Command {

	Timer timer;
	double goalAngle;
	
    public TiltTurretToAngleCommand(double goalAngle) {
    	this.goalAngle = goalAngle;
    	
        requires(Robot.turretSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = new Timer();
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.turretSubsystem.tiltTurret(0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.turretSubsystem.getTiltAngle() >= goalAngle) || timer.get() >= 1.0;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	Robot.turretSubsystem.tiltTurret(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
