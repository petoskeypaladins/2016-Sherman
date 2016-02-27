package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAlignShooterYCommand extends Command {

	double cenY, targetWidth, camOffset;
	int vCamWidth, vCamHeight;
	
    public AutoAlignShooterYCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	vCamWidth = 320;
    	vCamHeight = 240;
    	camOffset = 6.5;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cenY = SmartDashboard.getNumber("Center Y");
    	targetWidth = SmartDashboard.getNumber("Goal Width");
    	
    	double yError = -(cenY - (vCamHeight / 2) - (targetWidth*1.25));
	    double mvmtRatioY = (yError / 240);
	    	
	    Robot.turretSubsystem.tiltTurret(mvmtRatioY);
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
