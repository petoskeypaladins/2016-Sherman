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
	public Button spinShooterButton;
	public Button stopShooterButton;
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
		
		spinShooterButton = new JoystickButton(shootJoystick, 2);
		spinShooterButton.whileHeld(new SpinShooterCommand());
		
		stopShooterButton = new JoystickButton(shootJoystick, 7);
		stopShooterButton.whenPressed(new StopShooterCommand());
		
		startIntakeButton = new JoystickButton(shootJoystick, 4);
		startIntakeButton.whileHeld(new IntakeStartCommand());
		
		stopIntakeButton = new JoystickButton(shootJoystick, 9);
		stopIntakeButton.whenPressed(new IntakeStopCommand());
		
		holdBallButton = new JoystickButton(shootJoystick, 6);
		holdBallButton.whileHeld(new HoldBallCommand());
		
		rollerIn = new JoystickButton(driveJoystick, 5);
		rollerIn.whileHeld(new RollerInCommand());
		
		rollerOut = new JoystickButton(driveJoystick, 6);
		rollerOut.whileHeld(new RollerOutCommand());
		
		rollerIn2 = new JoystickButton(shootJoystick, 3);
		rollerIn.whileHeld(new RollerInCommand());
		
		rollerOut2 = new JoystickButton(shootJoystick, 5);
		rollerOut.whileHeld(new RollerOutCommand());
		
	}
	
}



