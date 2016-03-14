package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAlignShooterCommand extends Command {

	double cenX, cenY, targetWidth;
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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
    		cenX = SmartDashboard.getNumber("Center X");
    	} catch(Exception e) {
    		cenX = -1;
    		System.out.println("Can't acquire Center X");
    	}
    	try {
    		cenY = SmartDashboard.getNumber("Center Y");
    	} catch(Exception e) {
    		cenY = -1;
    		System.out.println("Can't acquire Center Y");
    	}
    	targetWidth = SmartDashboard.getNumber("Goal Width", 0);
    	
    	if (cenX > 0 && cenY > 0  && targetWidth > 0) {
		    final double TARGET_FEET = ((double) 20/12);
		    // TODO - CONFIGURE FOR REAL ROBOT
		    double camOffsetAngle = 5.0;
		    final double FOV = 25; //half of real FOV
    		double xError = ((double) cenX - (vCamWidth / 2)) * ((double) TARGET_FEET / targetWidth); //feet
		    double frameAngle = SmartDashboard.getNumber("Frame Gyro", 0);
		    double distanceFromGoal = ((double) TARGET_FEET * vCamWidth) / 
		    				   (2 * targetWidth * Math.tan(Math.toRadians(FOV)));
		    double angleError = Math.toDegrees(Math.atan(xError / distanceFromGoal)) + camOffsetAngle;
		    double targetAngle = frameAngle + angleError;
		    double output = (targetAngle - Robot.turretSubsystem.getRotateAngle()) / 20;
		    
		    SmartDashboard.putNumber("X Error", angleError);
		    
		    if ((Math.abs(angleError) <= 0.95)) {
		    	Robot.turretSubsystem.rotateTurret(0.0);
			    SmartDashboard.putBoolean("Centered Shooter (x)", true);
		    } else {
		    	Robot.turretSubsystem.rotateTurret(output);
			    SmartDashboard.putBoolean("Centered Shooter (x)", false);
		    }
		    
		    double farHeight = .45;
	    	double nearHeight = .50;
	    	double farPixels = 51;
	    	double nearPixels = 75;
	    	
	    	if (!Robot.IS_COMPETITION_ROBOT) {
	    		farHeight = .45;
	    		nearHeight = .58;
	    		farPixels = 81;
	    		nearPixels = 40;
	    	}
	    	
	    	double slope = (farHeight - nearHeight) / (farPixels - nearPixels); // -0.003125
	    	double intercept = (slope*(-farPixels)) + farHeight;
//	    	double intercept = farHeight - (slope*farPixels) + .05; 
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
			    	mvmtRatioY = 0.115;
			    	if (yError < 15 && yError > 0) {
			    		mvmtRatioY = 0.095;
			    	}
			    } else if (yError > -30 && yError < 0) {
			    	mvmtRatioY = -0.115;
			    	if (yError > -20 && yError < 0) {
			    		mvmtRatioY = -0.095;
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
