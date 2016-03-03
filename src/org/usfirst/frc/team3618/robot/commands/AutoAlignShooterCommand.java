package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAlignShooterCommand extends Command {

	double cenX, cenY, targetWidth, camOffset;
	int vCamWidth, vCamHeight;
	boolean centeredX = false, centeredY = false;
	
    public AutoAlignShooterCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.turretSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// These are as interpreted by the openCV program
    	vCamWidth = 320;
    	vCamHeight = 240;
    	if (Robot.IS_COMPETITION_ROBOT) {
    		camOffset = 8.125;
    	} else {
    		camOffset = 12.125;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cenX = SmartDashboard.getNumber("Center X");
    	cenY = SmartDashboard.getNumber("Center Y");
    	targetWidth = SmartDashboard.getNumber("Goal Width");
    	
    	if (cenX > 0 && cenY > 0  && targetWidth > 0) {
    		
    		double xOff = (targetWidth / 20) * camOffset; //TargetWidth/20 gives pixels/inch. Times camOffset in inches gives pixel offset.
		    double xError = cenX - (vCamWidth / 2) + xOff;
		    double mvmtRatioX = (xError / 400);
		    
		    if ((Math.abs(xError) >= 3)) {
		    	if (xError < 40 && xError > 0) {
			    	// The value is too small for the motor to do anything
			    	mvmtRatioX = 0.06;
			    	if (xError < 20 && xError > 0) {
			    		mvmtRatioX = 0.0525;
			    	}
			    } else if (xError > -40 && xError < 0) {
			    	mvmtRatioX = -0.06;
			    	if (xError > -20 && xError < 0) {
			    		mvmtRatioX = -0.0525;
			    	}
			    }
			    
			    Robot.turretSubsystem.rotateTurret(mvmtRatioX);
				
			    SmartDashboard.putBoolean("Centered Shooter (x)", false);
			    
		    } else {
		    	SmartDashboard.putBoolean("Centered Shooter (x)", true);
			    Robot.turretSubsystem.rotateTurret(0);
		    }
			    
		    SmartDashboard.putNumber("X Error", xError);
		    SmartDashboard.putNumber("X Ratio", mvmtRatioX);
		    
		    double farHeight = .82;
	    	double nearHeight = .75;
	    	double farPixels = 40;
	    	double nearPixels = 80;
	    	
	    	double slope = (farHeight - nearHeight) / (farPixels - nearPixels); // -0.003125
	    	double intercept = farHeight - (slope*farPixels); // 1 
	    	double yAlignRatio = slope * targetWidth + intercept;
		    
		    double yError = -(cenY - ((vCamHeight*yAlignRatio)));
		    double mvmtRatioY = (yError / 240);
		    	
		    SmartDashboard.putNumber("Y Align Ratio", yAlignRatio);
	    	SmartDashboard.putNumber("Y Error", yError);
	    	SmartDashboard.putNumber("Y Ratio", mvmtRatioY);
		    
		    if ((yError >= 3 && !Robot.turretSubsystem.isMaxLimitSet()) || 
		    	 yError <= -3) {
		    	if (yError < 30 && yError > 0) {
			    	// The value is too small for the motor to do anything
			    	mvmtRatioY = 0.08;
			    	if (yError < 15 && yError > 0) {
			    		mvmtRatioY = 0.075;
			    	}
			    } else if (yError > -30 && yError < 0) {
			    	mvmtRatioY = -0.08;
			    	if (yError > -20 && yError < 0) {
			    		mvmtRatioY = -0.075;
			    	}
			    }
		    	SmartDashboard.putBoolean("Centered Shooter (y)", false);
			    Robot.turretSubsystem.tiltTurret(mvmtRatioY);
		    } else {
		    	SmartDashboard.putBoolean("Centered Shooter (y)", true);
			    Robot.turretSubsystem.tiltTurret(0);
		    }
    	}
    }
    
    public boolean getCentered() {
    	return (SmartDashboard.getBoolean("Centered Shooter (x)") && 
    			SmartDashboard.getBoolean("Centered Shooter (y)"));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// Correct the turret horizontally
        return getCentered();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.turretSubsystem.rotateTurret(0.0);
    	Robot.turretSubsystem.tiltTurret(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
