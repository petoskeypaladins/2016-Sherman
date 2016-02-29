package org.usfirst.frc.team3618.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import ord.usfirst.frc.team3618.robot.commands.autonomus.EncoderDriveCommand;

/**
 *
 */
public class AutonomousCommandManager extends CommandGroup {
    float[] defenseTimes = {}
    
    public  AutonomousCommandManager() {
        
    }
    
    public AutonomousCommandManager(int defense) {
        new EncoderDriveCommand(defenseTimes[defense]);
    }

    public AutonomousCommandManager(int defense, int position, int balls) {
        //do something neat
    }
}
