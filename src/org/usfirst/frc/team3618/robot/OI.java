package org.usfirst.frc.team3618.robot;

import org.usfirst.frc.team3618.robot.commands.IntakeCommand;
import org.usfirst.frc.team3618.robot.commands.ReadyCommand;
import org.usfirst.frc.team3618.robot.commands.ShootCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public Joystick driveJoystick = new Joystick(1);
	public Joystick shootJoystick = new Joystick(0);
	
	public Button shootButton;
	public Button readyButton;
	public Button startIntakeButton;
	
	public OI() {
		
		shootButton = new JoystickButton(shootJoystick, 1);
		shootButton.whenPressed(new ShootCommand());
		
		readyButton = new JoystickButton(shootJoystick, 2);
		readyButton.whenPressed(new ReadyCommand());
		
		startIntakeButton = new JoystickButton(shootJoystick, 3);
		readyButton.whenPressed(new IntakeCommand());
	}
	
}



