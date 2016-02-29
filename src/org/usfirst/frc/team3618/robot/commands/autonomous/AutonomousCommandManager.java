package org.usfirst.frc.team3618.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandManager extends CommandGroup {
    
    public  AutonomousCommandManager(int ballType, int defenseType, int position) {
        if (ballType == 1) {
        	// One Ball Autonomous
        	manageDefenses(defenseType);
        } else if (ballType == 2) {
        	// Two Ball Autonomous - Needs planning
        } else if (ballType == 3) {
        	// Defense Crossing, don't score
        	manageDefenses(defenseType);
        } else if (ballType == 4) {
        	// Don't do anything. Something is wrong.
        }
    }
    
    public void manageDefenses(int defenseType) {
    	if (defenseType == 1) {
    		// Low Bar
    		addSequential(new EncoderDriveCommand(10.5, 1.0));
    	}
    	if (defenseType == 2) {
    		// Portcullis
    	}
    	if (defenseType == 3) {
    		// Cheval de Frise
    	}
    	if (defenseType == 4) {
    		// Rock Wall
    	}
    	if (defenseType == 5) {
    		// Rough Terrain
    		addSequential(new EncoderDriveCommand(4.0, 1.0));
    	}
    	if (defenseType == 6) {
    		// Ramparts
    	}
    	if (defenseType == 7) {
    		// Moat
    	}
    	if (defenseType == 8) {
    		// Drawbridge
    	}
    	if (defenseType == 9) {
    		// Sally Port
    	}
    }
    
}
