package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LawrenceMk7 extends Subsystem {
	CANTalon lawrence;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public LawrenceMk7() {
    	lawrence = new CANTalon(RobotMap.LAWRENCE);
    }
    
    public void move(double direction) {
    	lawrence.set(direction);
    }
}

