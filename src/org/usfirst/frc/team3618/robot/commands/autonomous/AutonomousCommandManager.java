package org.usfirst.frc.team3618.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 *
 */
public class AutonomousCommandManager extends CommandGroup {
    double[] defenseTimes = {3.46,//Rock Wall
                             2.21,//Rough Terrain
                             2.58,//Ramparts
                             3.05,//Moat
    };
    
    public AutonomousCommandManager() {
        
    }
    
    public AutonomousCommandManager(int defense) {
    	
    }

    public AutonomousCommandManager(int defense, int position, int balls) {
        //do something neat
    }
}
