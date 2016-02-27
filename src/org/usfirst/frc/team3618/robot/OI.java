package org.usfirst.frc.team3618.robot;

import org.usfirst.frc.team3618.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	
	public Button autoAlignShooterX;
	public Button autoAlignShooterY;
	public Button centerTurret;
	
	public OI() {
		
		shootButton = new JoystickButton(shootJoystick, 1);
		shootButton.whenPressed(new ShootCommand());
		
		spinShooterButton = new JoystickButton(shootJoystick, 2);
		spinShooterButton.whileHeld(new SpinShooterCommand());
		
//		stopShooterButton = new JoystickButton(shootJoystick, 7);
//		stopShooterButton.whenPressed(new StopShooterCommand());
		
		startIntakeButton = new JoystickButton(shootJoystick, 4);
		startIntakeButton.whileHeld(new IntakeStartCommand());
		
		stopIntakeButton = new JoystickButton(shootJoystick, 9);
		stopIntakeButton.whenPressed(new IntakeStopCommand());
		
		holdBallButton = new JoystickButton(shootJoystick, 6);
		holdBallButton.whileHeld(new HoldBallCommand());
		
		rollerIn = new JoystickButton(driveJoystick, 6);
		rollerIn.whileHeld(new RollerInCommand());
		
		rollerOut = new JoystickButton(driveJoystick, 5);
		rollerOut.whileHeld(new RollerOutCommand());
	
		autoAlignShooterX = new JoystickButton(shootJoystick, 11);
		autoAlignShooterX.whileHeld(new AutoAlignShooterXCommand());
		
		autoAlignShooterY = new JoystickButton(shootJoystick, 12);
		autoAlignShooterY.whileHeld(new AutoAlignShooterYCommand());
		
		centerTurret = new JoystickButton(shootJoystick, 7);
		centerTurret.whenPressed(new ResetTurretCommand());
		
	}
	
}



