package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAlignShooterCommand extends Command {

	double cenX, cenY;
	int vCamWidth, vCamHeight;
    public AutoAlignShooterCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterRotate);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// These are as interpreted by the openCV program
    	vCamWidth = 320;
    	vCamHeight = 240;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cenX = SmartDashboard.getNumber("Center X");
    	cenY = SmartDashboard.getNumber("Center Y");
	    	double xError = cenX - (vCamWidth / 2) + 15;
	    	double mvmtRatioX = (xError / 360);
	    	
	    	Robot.shooterRotate.rotate(mvmtRatioX);
			
	    	SmartDashboard.putBoolean("Centered Shooter", (Math.abs(xError) < 5));
	    	
	    	double yError = -(cenY - (vCamHeight / 2) - 100);
	    	double mvmtRatioY = (yError / 180);
	    	
	    	Robot.shooterTilt.tilt(mvmtRatioY);
    	
    	
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// Correct the turret horizontally
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterRotate.rotate(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
