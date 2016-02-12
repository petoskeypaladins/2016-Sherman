package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;
import org.usfirst.frc.team3618.robot.commands.RotateCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterRotate extends Subsystem {
    CANTalon rotateMotor = new CANTalon(RobotMap.ROTATE_SHOOTER_MOTOR);
    
    
    
    public void initDefaultCommand() {
    	setDefaultCommand(new RotateCommand());
    }
    
    public void rotate(double output) {
    	rotateMotor.set(output);
    }
    
    
}

