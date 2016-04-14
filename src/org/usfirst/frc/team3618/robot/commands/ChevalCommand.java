package org.usfirst.frc.team3618.robot.commands;

import org.usfirst.frc.team3618.robot.commands.autonomous.ArmDownCommand;
import org.usfirst.frc.team3618.robot.commands.autonomous.DriveStraightCommand;
import org.usfirst.frc.team3618.robot.commands.autonomous.EncoderDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ChevalCommand extends CommandGroup {
    
    public  ChevalCommand() {
    	final double CHEVAL_DISTANCE = 1; //feet
    	final double CHEVAL_POWER = -1;
    	final double CHEVAL_DRIVE_TIME = .75; //seconds
    	addSequential(new EncoderDriveCommand(CHEVAL_DISTANCE, CHEVAL_POWER));
    	addSequential(new ArmDownCommand());
    	addSequential(new DriveStraightCommand(CHEVAL_DRIVE_TIME));
    }
}
