package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

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
        
    public void intake(double output) {
    	reverseWheels();
    	wheelSpeed(output);
    	reverseWheels();
    	//fix this later, just tests the angle right now
    }
    
    public void stopIntake() {
    	wheelSpeed(OFF);
    }
    
    public void reverseWheels() {
    	leftShootWheel.setInverted(!leftShootWheel.getInverted());
    	rightShootWheel.setInverted(!rightShootWheel.getInverted());
    }
    
    
}

