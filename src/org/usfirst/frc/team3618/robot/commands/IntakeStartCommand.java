package org.usfirst.frc.team3618.robot.commands;

//import org.usfirst.frc.team3618.robot.OI;
import org.usfirst.frc.team3618.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeStartCommand extends Command {
	
	
    public IntakeStartCommand() {
        requires(Robot.shooterWheels);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
//    	double intakeSpeed = .75 - (Robot.oi.shootJoystick.getThrottle() * .5);
    	double intakeSpeed = .31;
    	
    	System.out.println(Double.toString(intakeSpeed));
    	Robot.shooterWheels.intake(intakeSpeed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
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
