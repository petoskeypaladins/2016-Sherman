package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterWheels extends Subsystem {
	CANTalon leftShootWheel = new CANTalon(RobotMap.LEFT_SHOOT_WHEEL_MOTOR);
	CANTalon rightShootWheel = new CANTalon(RobotMap.RIGHT_SHOOT_WHEEL_MOTOR);
	
	
	private double fullSpeed = 1.0;
	private final double OFF = 0;
	
	

	public ShooterWheels() {
		rightShootWheel.setInverted(true);
		
		leftShootWheel.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightShootWheel.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		

		leftShootWheel.configEncoderCodesPerRev(40);
		rightShootWheel.configEncoderCodesPerRev(40);
		
		
	}
	
    public void initDefaultCommand() {
    }
    
    public void readyShoot(double output) {
//    	while (leftShootWheel.get() < output && rightShootWheel.get() < output) {
//    		if (leftShootWheel.get() < output - .2 && rightShootWheel.get() < output - .2) {
//    			leftShootWheel.set(leftShootWheel.get() + .2);
//    	    	rightShootWheel.set(rightShootWheel.get() + .2);
//    		} else {
//    			leftShootWheel.set(output);
//    	    	rightShootWheel.set(output);
//    		}
//    	}
    	
    	fullSpeed = output;
    	
    	wheelSpeed(output);
    	
    }
    
    public void wheelSpeed(double output) {
    	leftShootWheel.set(output);
    	rightShootWheel.set(output);
    	
    }
    
    public boolean testReady() {
    	return (leftShootWheel.get() == fullSpeed && rightShootWheel.get() == fullSpeed);
    }
        
    public void startIntake() {
    	reverseWheels();
    	wheelSpeed(.5);
    	reverseWheels();
    	//fix this later, just tests the angle right now
    }
    
//    public void printWheelSpeed() {
//    	  	
//    	System.out.println("Left Wheel: " + Double.toString(leftShootWheel.getSpeed()));
//    	System.out.println("Right Wheel: " + Double.toString(rightShootWheel.getSpeed()));
//    }
    
    double leftSpeed, rightSpeed;
    
    public void getSpeed() {
    	leftSpeed = (leftShootWheel.getSpeed() / ((double) 8.)) * ((double) 60.);
    	rightSpeed = (rightShootWheel.getSpeed() / ((double) 8.)) * ((double) 60.);
    	
//    	leftSpeed = leftShootWheel.getSpeed();
//    	rightSpeed = rightShootWheel.getSpeed();
    	
    }
     
    public void stopIntake() {
    	wheelSpeed(OFF);
    }
    
    public void reverseWheels() {
    	leftShootWheel.setInverted(!leftShootWheel.getInverted());
    	rightShootWheel.setInverted(!rightShootWheel.getInverted());
    }
    
    public void displayRPMS() {
    	SmartDashboard.putNumber("Left Shooter RPM", leftSpeed);
    	SmartDashboard.putNumber("Right Shooter RPM", rightSpeed);
    	
    	System.out.println("Left Shooter Speed : " + Double.toString(leftSpeed));
    	System.out.println("Right Shooter Speed: " + Double.toString(rightSpeed));
    	
    }
    
}

