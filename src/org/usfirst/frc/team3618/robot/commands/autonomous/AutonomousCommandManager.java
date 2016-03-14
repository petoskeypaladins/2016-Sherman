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
	private HashMap<String, Double> positionRotateAngle;
	private HashMap<String, Double> positionTiltAngle;
	
	final double[] DEFENSE_TIME = {
		3.46,//Rock Wall
	    2.21,//Rough Terrain
	    2.58,//Ramparts
	    3.05,//Moat
    };
    
    // 1 = Most left position
    // 5 = Most right position
    final double [] POSITION_ROTATE_ANGLE = {30.0,30.0,5.0,-5.0,-30.0};
    
    final double[] POSITION_TILT_ANGLE = {70,60,30,0,-30};
    
    final double DRIVE_TIME_BEFORE_DEFENSE = 1.0;
    
//    public AutonomousCommandManager() {
//         Sit there and do nothing
//    }
    
//    public AutonomousCommandManager(int defense, int position) {
//    	addParallel(new HoldBallCommand());
//    	if (Robot.IS_COMPETITION_ROBOT) {
//    		addParallel(new ArmDownCommand(), 2.25);
//    	}
//    	System.out.println("driving for: " + Double.toString(DRIVE_TIME + DEFENSE_TIME[defense -1]));
//    	addSequential(new DriveStraightCommand(DRIVE_TIME + DEFENSE_TIME[defense - 1]));
//    	addParallel(new AutoAlignShooterCommand());
//    	addSequential(new WaitCommand(), 5.0);
//    	addParallel(new SpinShooterCommand());
//    	addSequential(new WaitCommand(), 1.5);
//    	addSequential(new ShootCommand());
//    	addSequential(new StopShooterCommand());
//    }
    
    public AutonomousCommandManager(int defense, int balls, int position) {
        //do something neat
//    	addParallel(new HoldBallCommand());
//    	addParallel(new ArmDownCommand(), 2.25);
//    	System.out.println("driving for: " + Double.toString(DRIVE_TIME + DEFENSE_TIME[defense -1]));
//    	addSequential(new DriveStraightCommand(DRIVE_TIME + DEFENSE_TIME[defense - 1]));
//    	if (balls == 2) {
//    		
//    	}
    	
    	defenseDriveTime = new HashMap<String, Double>();
    	positionDriveTime = new HashMap<String, Double>();
    	positionRotateAngle = new HashMap<String, Double>();
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
    		crossDefense(defense);
    		shootBallOne(position);
    		//shootBallTwo();
    	}
    	
    	clearHashMaps();
    }
    
    private void initHashMaps() {
    	defenseDriveTime.put("Rough Terrain", 1.0);
    	defenseDriveTime.put("Rock Wall", 1.0);
    	defenseDriveTime.put("Ramparts", 1.0);
    	defenseDriveTime.put("Moat", 1.0);
    	
    	positionDriveTime.put("Position 1", 4.0);
    	positionDriveTime.put("Position 2", 3.0);
    	positionDriveTime.put("Position 3", 2.0);
    	positionDriveTime.put("Position 4", 2.0);
    	positionDriveTime.put("Position 5", 4.0);
    	
    	positionRotateAngle.put("Position 1", 30.0);
    	positionRotateAngle.put("Position 2", 20.0);
    	positionRotateAngle.put("Position 3", 5.0);
    	positionRotateAngle.put("Position 4", -5.0);
    	positionRotateAngle.put("Position 5", -15.0);
    	
    	positionTiltAngle.put("Position 1", 30.0);
    	positionTiltAngle.put("Position 2", 30.0);
    	positionTiltAngle.put("Position 3", 30.0);
    	positionTiltAngle.put("Position 4", 30.0);
    	positionTiltAngle.put("Position 5", 30.0);
    }
    
    private void clearHashMaps() {
    	defenseDriveTime.clear();
    	positionDriveTime.clear();
    	positionRotateAngle.clear();
    	positionTiltAngle.clear();
    }
    
    private void crossDefense(int defense) {
    	addParallel(new HoldBallCommand());
		if (Robot.IS_COMPETITION_ROBOT) {
    		addParallel(new ArmDownCommand(), 2.25);
    	}
		System.out.println("driving for: " + DRIVE_TIME_BEFORE_DEFENSE);
    	addSequential(new DriveStraightCommand(DRIVE_TIME_BEFORE_DEFENSE, true));
    	if (defense == 1) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Rock Wall")));	
    	} else if (defense == 2) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Rough Terrain")));
    	} else if (defense == 3) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Ramparts")));
    	} else if (defense == 4) {
    		addSequential(new DriveStraightCommand(defenseDriveTime.get("Moat")));
    	} else if (defense == 5) {
    		
    	} else {
    		// Don't do anything. Program fault as we cannot do any other defenses in autonomous right now
    	}
    }
    
    private void shootBallOne(int position) {
		addSequential(new DriveStraightCommand(positionDriveTime.get("Postion " + position)));  
		// Turn the turret?
    	addSequential(new RotateTurretToAngleCommand(positionRotateAngle.get("Position " + position)));
		addParallel(new AutoAlignShooterCommand());
    	addSequential(new WaitCommand(), 5.0);
    	addParallel(new SpinShooterCommand());
    	addSequential(new WaitCommand(), 1.5);
    	addSequential(new ShootCommand());
    	addSequential(new StopShooterCommand());
    }
    
}
