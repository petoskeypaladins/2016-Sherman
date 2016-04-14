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
	public Button chevalMacro;
	
	public Button autoAlignShooter;
	public Button centerTurret;
	
	public Button overrideRotate;
	public Button overrideTilt;
	public Button lowSpeedShoot;
	
	public Button armUp;
	public Button armDown;
	
	public OI() {
		
		shootButton = new JoystickButton(shootJoystick, 1);
		shootButton.whenPressed(new ShootCommand());
		
		spinShooterButton = new JoystickButton(shootJoystick, 2);
		spinShooterButton.whileHeld(new SpinShooterCommand());
		
//		stopShooterButton = new JoystickButton(shootJoystick, 7);
//		stopShooterButton.whenPressed(new StopShooterCommand());
		
		startIntakeButton = new JoystickButton(shootJoystick, 4);
		startIntakeButton.whileHeld(new IntakeStartCommand());
				
		holdBallButton = new JoystickButton(shootJoystick, 6);
		holdBallButton.whileHeld(new HoldBallCommand());
		
		rollerIn = new JoystickButton(driveJoystick, 6);
		rollerIn.whileHeld(new RollerInCommand());
		
		rollerOut = new JoystickButton(driveJoystick, 5);
		rollerOut.whileHeld(new RollerOutCommand());
		
		chevalMacro = new JoystickButton(driveJoystick, 1);
		chevalMacro.whileHeld(new ChevalCommand());
	
		autoAlignShooter = new JoystickButton(shootJoystick, 12);
		autoAlignShooter.whileHeld(new AutoAlignShooterCommand());
		
		overrideRotate = new JoystickButton(shootJoystick, 7);
		overrideRotate.whileHeld(new OverrideRotateCommand());
		
		overrideTilt = new JoystickButton(shootJoystick, 8);
		overrideRotate.whileHeld(new OverrideTiltCommand());
		
		centerTurret = new JoystickButton(shootJoystick, 11);
		centerTurret.whileHeld(new CenterTurretCommand());
		
		armUp = new JoystickButton(shootJoystick, 9);
		armUp.whileHeld(new ArmLiftCommand(1));
		
		armDown = new JoystickButton(shootJoystick, 10);
		armDown.whileHeld(new ArmLiftCommand(-1));
		
//		lowSpeedShoot = new JoystickButton(shootJoystick, 12);
//		lowSpeedShoot.whileHeld(new SpinShooterLowCommand());
	}
	
}



