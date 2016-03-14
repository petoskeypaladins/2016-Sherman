package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateTurretToAngleCommand extends Command {

	private double goalAngle;
	
    public RotateTurretToAngleCommand(double goalAngle) {
    	this.goalAngle = goalAngle;
    	
    	requires(Robot.turretSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
    	if (goalAngle > 0) {
    		Robot.turretSubsystem.rotateTurret(0.3);
    	} else if (goalAngle < 0) {
    		Robot.turretSubsystem.rotateTurret(-0.3);
    	}
    }

    protected boolean isFinished() {
        return Math.abs(Robot.turretSubsystem.getRotateAngle()) >= Math.abs(goalAngle);
    }

    protected void end() {
    	Robot.turretSubsystem.rotateTurret(0.0);
    }

    protected void interrupted() {
    	end();
    }
}
