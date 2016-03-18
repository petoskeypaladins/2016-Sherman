package org.usfirst.frc.team3618.robot.commands.autonomous;

import java.util.HashMap;

import org.usfirst.frc.team3618.robot.Robot;
import org.usfirst.frc.team3618.robot.commands.AutoAlignShooterCommand;
import org.usfirst.frc.team3618.robot.commands.HoldBallCommand;
import org.usfirst.frc.team3618.robot.commands.ShootCommand;
import org.usfirst.frc.team3618.robot.commands.SpinShooterCommand;
import org.usfirst.frc.team3618.robot.commands.StopShooterCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 *
 */
public class AutonomousCommandManager extends CommandGroup {
    
	private HashMap<String, Double> defenseDriveTime;
	private HashMap<String, Double> positionDriveTime;
	private HashMap<String, Double> positionTurretRotateAngle;
	private HashMap<String, Double> positionTiltAngle;
	private HashMap<String, Double> positionRobotRotateAngle;
    
    final double DRIVE_TIME_BEFORE_DEFENSE = 1.5;
    
    private double finalDriveTime;
    
    public AutonomousCommandManager(int defense, int balls, int position) {
    	finalDriveTime = 0.0;
    	
    	defenseDriveTime = new HashMap<String, Double>();
    	positionDriveTime = new HashMap<String, Double>();
    	positionTurretRotateAngle = new HashMap<String, Double>();
    	positionRobotRotateAngle = new HashMap<String, Double>();
    	positionTiltAngle = new HashMap<String, Double>();
    	
    	initHashMaps();
    	
    	if (balls == 4) {
    		// sit there and cry
    	} else if (balls == 3) {
    		// defense cross only
    		crossDefense(defense);
    	} else if (balls == 1) {
    		// one ball auto
    		crossDefense(defense);
    		shootBallOne(position);
    	} else if (balls == 2) {
    		// two ball auto
    		test();
    		//shootBallTwo();
    	}
    	
    	clearHashMaps();
    }
    
    private void initHashMaps() {
    	defenseDriveTime.put("Rough Terrain", 2.0);
    	defenseDriveTime.put("Rock Wall", 2.0);
    	defenseDriveTime.put("Ramparts", 2.0);
    	defenseDriveTime.put("Moat", 2.0);
    	defenseDriveTime.put("Low Bar", 1.5);
    	
    	positionDriveTime.put("Position 1", 2.0);
    	positionDriveTime.put("Position 2", 2.0);
    	positionDriveTime.put("Position 3", 0.8);
    	positionDriveTime.put("Position 4", .75);
    	positionDriveTime.put("Position 5", 1.5);
    	
    	positionTurretRotateAngle.put("Position 1", 15.0);
    	positionTurretRotateAngle.put("Position 2", 45.0);
    	positionTurretRotateAngle.put("Position 3", 0.0);
    	positionTurretRotateAngle.put("Position 4", -15.0);
    	positionTurretRotateAngle.put("Position 5", -25.0);
    	
    	positionTiltAngle.put("Position 1", 30.0);
    	positionTiltAngle.put("Position 2", 30.0);
    	positionTiltAngle.put("Position 3", 30.0);
    	positionTiltAngle.put("Position 4", 30.0);
    	positionTiltAngle.put("Position 5", 40.0);
    }
    
    private void clearHashMaps() {
    	defenseDriveTime.clear();
    	positionDriveTime.clear();
    	positionTurretRotateAngle.clear();
    	positionTiltAngle.clear();
    }
    
    private void test() {
    	addSequential(new DriveStraightCommand(2.0, true, false));
    	addSequential(new DriveStraightCommand(3.0, false, false));
    }
    
    private void crossDefense(int defense) {
    	addParallel(new HoldBallCommand());
		if (Robot.IS_COMPETITION_ROBOT) {
    		addParallel(new ArmDownCommand(), 2.25);
    	}
    	addSequential(new DriveStraightCommand(DRIVE_TIME_BEFORE_DEFENSE, true));
    	System.out.println("Defense: " + defense);
    	if (defense == 1) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Rock Wall")));	
    	} else if (defense == 2) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Rough Terrain"), false));
    	} else if (defense == 3) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Ramparts"), false));
    	} else if (defense == 4) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Moat")));
    	} else if (defense == 5) {
    		addSequential(new DriveBackwardCommand(defenseDriveTime.get("Low Bar")));
    	} else {
    		// Don't do anything. Program fault as we cannot do any other defenses in autonomous right now
    	}
    }
    
    private void shootBallOne(int position) {
//    	addSequential(new RotateBotCommand());
    	addSequential(new DriveStraightCommand((double) positionDriveTime.get("Position " + Integer.toString(position)), false, true));  
		// Turn the turret
    	addSequential(new RotateTurretToAngleCommand(positionTurretRotateAngle.get("Position " + Integer.toString(position))));
		addParallel(new AutoAlignShooterCommand());
    	addSequential(new WaitCommand(), 4.0);
    	addParallel(new SpinShooterCommand());
    	addSequential(new WaitCommand(), 1.0);
    	addSequential(new ShootCommand());
    	addSequential(new StopShooterCommand());
    }
    
}
