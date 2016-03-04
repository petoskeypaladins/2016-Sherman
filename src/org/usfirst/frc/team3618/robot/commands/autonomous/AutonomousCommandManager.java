package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.commands.AutoAlignShooterCommand;
import org.usfirst.frc.team3618.robot.commands.DriveCommand;
import org.usfirst.frc.team3618.robot.commands.HoldBallCommand;
import org.usfirst.frc.team3618.robot.commands.ShootCommand;
import org.usfirst.frc.team3618.robot.commands.SpinShooterCommand;
import org.usfirst.frc.team3618.robot.commands.StopShooterCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 *
 */
public class AutonomousCommandManager extends CommandGroup {
    final double[] defenseTimes = {3.46,//Rock Wall
                             	2.21,//Rough Terrain
                             	2.58,//Ramparts
                             	3.05,//Moat
    };
    final double DRIVE_TIME = 1.0;
    
    public AutonomousCommandManager() {
        
    }
    
    public AutonomousCommandManager(int defense) {
    	addParallel(new HoldBallCommand());
    	addParallel(new ArmDownCommand(), 2.25);
    	System.out.println("driving for: " + Double.toString(DRIVE_TIME + defenseTimes[defense -1]));
    	addSequential(new DriveStraightCommand(DRIVE_TIME + defenseTimes[defense - 1]));
    	addParallel(new AutoAlignShooterCommand());
    	addSequential(new WaitCommand(), 5.0);
    	addParallel(new SpinShooterCommand());
    	addSequential(new WaitCommand(), 1.5);
    	addSequential(new ShootCommand());
    	addSequential(new StopShooterCommand());
    }
    
    public AutonomousCommandManager(int defense, int balls, int position) {
        //do something neat
    	addParallel(new HoldBallCommand());
    	addParallel(new ArmDownCommand(), 2.25);
    	System.out.println("driving for: " + Double.toString(DRIVE_TIME + defenseTimes[defense -1]));
    	addSequential(new DriveStraightCommand(DRIVE_TIME + defenseTimes[defense - 1]));
    	if (balls == 2) {
    		
    	}
    }
}
