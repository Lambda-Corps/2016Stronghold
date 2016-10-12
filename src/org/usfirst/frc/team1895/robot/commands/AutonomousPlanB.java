package org.usfirst.frc.team1895.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1895.robot.Robot;

public class AutonomousPlanB extends CommandGroup {

	public AutonomousPlanB() {
		//addSequential(new Command());
		//addSequential(new Command, setTimeOut);
		System.out.println("AutonomousPlanB");
		 
		
		//                           Passing Motor velocity, Current Heading, Timeout  
		addSequential(new DriveStraight(0.2, Robot.driveTrain.getHeading()), 5);
	//	addSequential(new Turn90Degrees(), 30);	//positive is clockwise
		addSequential(new Rotate(45.0)); 
		addSequential(new Rotate(-45.0));
		System.out.println("Trying to rotate");
		addSequential(new Rotate(90.0));
		addSequential(new Rotate(-90.0));
		
	}
}
