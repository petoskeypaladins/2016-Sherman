package org.usfirst.frc.team3618.robot.commands.autonomous;

import org.usfirst.frc.team3618.robot.commands.AutoAlignShooterCommand;
import org.usfirst.frc.team3618.robot.commands.DriveCommand;
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
    final double DRIVE_TIME = 2.0;
    
    public AutonomousCommandManager() {
        
    }
    
    public AutonomousCommandManager(int defense) {
    	addParallel(new ArmDownCommand(), 1.0);
    	addSequential(new DriveStraightCommand(), DRIVE_TIME + defenseTimes[defense - 1]);
    	addSequential(new AutoAlignShooterCommand());
    	addParallel(new SpinShooterCommand());
    	addSequential(new WaitCommand(), 1.5);
    	addSequential(new ShootCommand());
    	addSequential(new StopShooterCommand());
    }

    public AutonomousCommandManager(int defense, int position, int balls) {
        //do something neat
    }
}
