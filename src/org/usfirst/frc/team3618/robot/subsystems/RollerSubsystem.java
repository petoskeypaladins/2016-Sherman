package org.usfirst.frc.team3618.robot.subsystems;

import org.usfirst.frc.team3618.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RollerSubsystem extends Subsystem {
    
	CANTalon rollerMotor;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public RollerSubsystem() {
		rollerMotor = new CANTalon(RobotMap.INTAKE_ROLLER_MOTOR);
	}
	
	public void displayData() {
		SmartDashboard.putNumber("Output - Roller Motor", rollerMotor.get());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void spinRoller(double power) {
    	rollerMotor.set(power);
    }
    
}

