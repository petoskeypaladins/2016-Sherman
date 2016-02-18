package org.usfirst.frc.team3618.robot;

import org.usfirst.frc.team3618.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public Joystick shootJoystick = new Joystick(0);
	public Joystick driveJoystick = new Joystick(1);
	
	
	public Button shootButton;
	public Button readyButton;
	public Button unReadyButton;
	public Button startIntakeButton;
	public Button stopIntakeButton;
	public Button holdBallButton;
	
	public Button rollerIn;
	public Button rollerOut;
	public Button rollerIn2;
	public Button rollerOut2;
	
	public OI() {
		
		shootButton = new JoystickButton(shootJoystick, 1);
		shootButton.whenPressed(new ShootCommand());
		
		readyButton = new JoystickButton(shootJoystick, 2);
		readyButton.whenPressed(new ReadyCommand());
		
		unReadyButton = new JoystickButton(shootJoystick, 6);
		unReadyButton.whenPressed(new UnReadyCommand());
		
		startIntakeButton = new JoystickButton(shootJoystick, 3);
		startIntakeButton.whenPressed(new IntakeStartCommand());
		
		stopIntakeButton = new JoystickButton(shootJoystick, 4);
		stopIntakeButton.whenPressed(new IntakeStopCommand());
		
		holdBallButton = new JoystickButton(shootJoystick, 5);
		holdBallButton.whenPressed(new HoldBallCommand());
		
		rollerIn = new JoystickButton(driveJoystick, 5);
		rollerOut = new JoystickButton(driveJoystick, 6);
		rollerIn2 = new JoystickButton(shootJoystick, 10);
		rollerOut2 = new JoystickButton(shootJoystick, 8);
		
		
	}
	
}



