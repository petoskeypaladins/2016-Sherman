package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.MoveLawrenceCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LawrenceMk7 extends Subsystem {
    CANTalon lawrence;

    public LawrenceMk7() {
    	lawrence = new CANTalon(RobotMap.LAWRENCE);
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new MoveLawrenceCommand());
    }
    
    public void move(double power) {
    	lawrence.set(power);
    }
}

