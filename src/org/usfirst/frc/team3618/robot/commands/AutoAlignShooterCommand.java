package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAlignShooterCommand extends Command {

	double cenX, cenY, targetWidth;
	int vCamWidth, vCamHeight;
	boolean centeredX = false, centeredY = false;
	boolean finished;
	boolean isAuto;
	
	private Timer timer;
	private double timeout;
	
	public AutoAlignShooterCommand(double timeout) {
		requires(Robot.turretSubsystem);
		finished = false;
		this.timeout = timeout;
	}
	
    public AutoAlignShooterCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.turretSubsystem);
    	finished = false;
    	timeout = -99;
    }
    
    public AutoAlignShooterCommand(boolean isAuto) {
    	requires(Robot.turretSubsystem);
    	this.isAuto = isAuto;
    	this.timeout = -99;
    	finished = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// These are as interpreted by the openCV program
    	vCamWidth = 320;
    	vCamHeight = 240;
    	if (timeout != -99) {
    		timer = new Timer();
        	timer.reset();
        	timer.start();
    	}
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
		    double camOffsetAngle;
		    if (Robot.IS_COMPETITION_ROBOT) {
		    	camOffsetAngle = 3.25;
		    } else {
		    	camOffsetAngle = 5.0;
		    }
		    final double FOV = 25; //half of real FOV
    		double xError = ((double) cenX - (vCamWidth / 2)) * ((double) TARGET_FEET / targetWidth); //feet
		    double frameAngle = SmartDashboard.getNumber("Frame Gyro", 0);
		    double distanceFromGoal = ((double) TARGET_FEET * vCamWidth) / 
		    				   (2 * targetWidth * Math.tan(Math.toRadians(FOV)));
		    
		    double angleErrorX = Math.toDegrees(Math.atan(xError / distanceFromGoal)) + camOffsetAngle;
		    double targetAngleX = frameAngle + angleErrorX;
		    double rotateOutput = (targetAngleX - Robot.turretSubsystem.getRotateAngle()) / 63;
		    
		    double direction = rotateOutput / Math.abs(rotateOutput);
		    
		    rotateOutput = rotateOutput + (.06 * direction);
//		    
//		    if (Math.abs(angleErrorX) <= 5) {
//		    	rotateOutput = .08 * direction;
//		    }
		    
		    SmartDashboard.putNumber("X Error", angleErrorX);
		    
//		    if (Math.abs(angleErrorX) <= 4) {
//		    	rotateOutput = (targetAngleX - Robot.turretSubsystem.getRotateAngle()) / 25;
//		    }
		    
//		    if (Math.abs((int) angleErrorX) == 8) {
//		    	Robot.turretSubsystem.rotateTurret(0);
//		    }
		    
		    if ((Math.abs(angleErrorX) <= 1.25)) {
		    	Robot.turretSubsystem.rotateTurret(0.0);
			    SmartDashboard.putBoolean("Centered Shooter (x)", true);
		    } else {
		    	Robot.turretSubsystem.rotateTurret(rotateOutput);
			    SmartDashboard.putBoolean("Centered Shooter (x)", false);
		    }
		    
		    double farHeight = .5025;
	    	double nearHeight = .6166;
	    	double farPixels = 86;
	    	double nearPixels = 43;
	    	
	    	if (!Robot.IS_COMPETITION_ROBOT) {
	    		farHeight = .45;
	    		nearHeight = .58;
	    		farPixels = 81;
	    		nearPixels = 40;
	    	}
	    	
//	    	final double HEIGHT_TO_GOAL_BOTTOM = 5.24; // feet
//	    	final double HEIGHT_TO_GOAL = HEIGHT_TO_GOAL_BOTTOM; //feet
//	    	final double g = 32.174; //ft/s^2
//	    	final double VELOCITY = 80; //feet per second
//	    	double lateralDistance = Math.max(Math.sqrt(Math.pow(distanceFromGoal, 2) - Math.pow(HEIGHT_TO_GOAL_BOTTOM, 2)), .00001); //feet
//	    	double targetAngleY = Math.toDegrees(Math.atan(
//	    			(Math.pow(VELOCITY, 2) - 
//	    					Math.sqrt(Math.pow(VELOCITY, 4) - g *(g * Math.pow(lateralDistance, 2) + 2 * HEIGHT_TO_GOAL * Math.pow(VELOCITY, 2))))
//	    			/ (g * lateralDistance)));
//	    	double angleErrorY = targetAngleY - Robot.turretSubsystem.getTiltAngle();
//		    double tiltOutput = angleErrorY / 10;
//		    
//		    SmartDashboard.putNumber("lateral distance", lateralDistance);
//		    SmartDashboard.putNumber("target angle y", targetAngleY);
//		    SmartDashboard.putNumber("angle error", angleErrorY);
//		    SmartDashboard.putNumber("hypotenuse", distanceFromGoal);
//	    	
//	    	if (Math.abs(angleErrorY) <= 1.25) {
//	    		SmartDashboard.putBoolean("Centered Shooter (y)", true);
//			    Robot.turretSubsystem.tiltTurret(0);
//	    	} else {
//	    		SmartDashboard.putBoolean("Centered Shooter (y)", false);
//	    		Robot.turretSubsystem.tiltTurret(tiltOutput);
//	    	}
	    	
	    	double slope = (farHeight - nearHeight) / (farPixels - nearPixels); // -0.003125
	    	double intercept = (slope*(-farPixels)) + farHeight;
	    	double yAlignRatio = slope * targetWidth + intercept;
		    double yOffset = 15.0;
		    double yError = -(cenY - ((vCamHeight*yAlignRatio)) + yOffset);
		    double mvmtRatioY = (yError / 240);
		    
		    
    	
    	// !TiltMaxLimit
		    if (Math.abs(yError) >= 6 && !Robot.turretSubsystem.isMaxLimitSet()) {
		    	if (yError < 30 && yError > 0) {
			    	// The value is too small for the motor to do anything
			    	mvmtRatioY = 0.25;
			    	if (yError < 15 && yError > 0) {
			    		mvmtRatioY = 0.15;
			    	}
			    } else if (yError > -30 && yError < 0) {
		    		mvmtRatioY = -0.25;
			    	if (yError > -15 && yError < 0) {
			    		mvmtRatioY = -0.15;
			    	}
			    }
		    	SmartDashboard.putBoolean("Centered Shooter (y)", false);
			    Robot.turretSubsystem.tiltTurret(mvmtRatioY);
		    } else {
		    	SmartDashboard.putBoolean("Centered Shooter (y)", true);
			    Robot.turretSubsystem.tiltTurret(0);
		    }
		    
		    SmartDashboard.putNumber("Y Align Ratio", yAlignRatio);
	    	SmartDashboard.putNumber("Y Error", yError);
	    	SmartDashboard.putNumber("Y Ratio", mvmtRatioY);
		    
	    	if (isAuto) {
	    		if (SmartDashboard.getBoolean("Centered Shooter (x)") &&
	    				SmartDashboard.getBoolean("Centered Shooter (y)")) {
	    			finished = true;
	    		}
	    	}
	    	
	    	if (timeout != -99) {
	        	finished = timer.get() >= timeout;
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
    	return finished;
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
