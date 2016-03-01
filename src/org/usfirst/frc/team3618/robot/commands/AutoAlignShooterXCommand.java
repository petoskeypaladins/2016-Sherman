package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAlignShooterXCommand extends Command {

	double cenX, cenY, targetWidth, camOffset;
	int vCamWidth, vCamHeight;
	
    public AutoAlignShooterXCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.turretSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// These are as interpreted by the openCV program
    	vCamWidth = 320;
    	vCamHeight = 240;
    	camOffset = 8.125;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cenX = SmartDashboard.getNumber("Center X");
    	cenY = SmartDashboard.getNumber("Center Y");
    	targetWidth = SmartDashboard.getNumber("Goal Width");
    	
    	if (cenX > 0 && cenY > 0  && targetWidth > 0) {
    		
    		double xOff = (targetWidth / 20) * camOffset; //TargetWidth/20 gives pixels/inch. Times camOffset in inches gives pixel offset.
		    double xError = cenX - (vCamWidth / 2) + xOff;
		    double mvmtRatioX = (xError / 360);
		    
		    if ((Math.abs(xError) >= 3)) {
		    	if (xError < 40 && xError > 0) {
			    	// The value is too small for the motor to do anything
			    	mvmtRatioX = 0.06;
			    	if (xError < 20 && xError > 0) {
			    		mvmtRatioX = 0.055;
			    	}
			    } else if (xError > -40 && xError < 0) {
			    	mvmtRatioX = -0.06;
			    	if (xError > -20 && xError < 0) {
			    		mvmtRatioX = -0.055;
			    	}
			    }
			    
			    Robot.turretSubsystem.rotateTurret(mvmtRatioX);
				
			    SmartDashboard.putBoolean("Centered Shooter", false);
		    } else {
		    	SmartDashboard.putBoolean("Centered Shooter", true);
			    Robot.turretSubsystem.rotateTurret(0);
	    }
			    
		    SmartDashboard.putNumber("X Error", xError);
		    SmartDashboard.putNumber("X Ratio", mvmtRatioX);
    	}
    	
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// Correct the turret horizontally
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.turretSubsystem.rotateTurret(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
