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
    final double[] DEFENSE_TIME = {3.46,//Rock Wall
                             	2.21,//Rough Terrain
                             	2.58,//Ramparts
                             	3.05,//Moat
    };
    
    final double[] POSITION_ANGLE = {70,
    		60,
    		30,
    		0,
    		-30};
    
    final double DRIVE_TIME = 1.0;
    
    public AutonomousCommandManager() {
        
    }
    
    public AutonomousCommandManager(int defense, int position) {
    	addParallel(new HoldBallCommand());
    	addParallel(new ArmDownCommand(), 2.25);
    	System.out.println("driving for: " + Double.toString(DRIVE_TIME + DEFENSE_TIME[defense -1]));
    	addSequential(new DriveStraightCommand(DRIVE_TIME + DEFENSE_TIME[defense - 1]));
    	if (position < 6) {
    		addSequential(new RotateCommand(POSITION_ANGLE[position - 1]));
    	}
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
    	System.out.println("driving for: " + Double.toString(DRIVE_TIME + DEFENSE_TIME[defense -1]));
    	addSequential(new DriveStraightCommand(DRIVE_TIME + DEFENSE_TIME[defense - 1]));
    	if (balls == 2) {
    		
    	}
    }
}
