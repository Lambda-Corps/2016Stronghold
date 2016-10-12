package org.usfirst.frc.team1895.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1895.robot.Robot;

public class Autonomous extends CommandGroup {

	public Autonomous() {
		//addSequential(new Command());
		//addSequential(new Command, setTimeOut);
		System.out.println("autonomous");
		 
		
		//                           Passing Motor velocity, Current Heading, Timeout  
//		addSequential(new DriveRangeFinder (0.2, 10.0));
//		addSequential(new DriveEncoder(0.3, 1000.0));    // public DriveEncoder(double speed, double  distance) {
//		addSequential(new Rotate(90.0)); 
//		addSequential(new Rotate(90.0)); 
//		addSequential(new DriveEncoder(0.3, 1000.0));    // public DriveEncoder(double speed, double  distance) {
//		addSequential(new Rotate(90.0)); 
//		addSequential(new Rotate(90.0)); 
		//addSequential(new DriveEncoder(0.2, -72.0));    // public DriveEncoder(double speed, double  distance) {
		
//		addSequential(new DriveStraight(0.2, Robot.driveTrain.getHeading()), 1);
//		
//		addSequential(new Rotate(45.0)); 
//		addSequential(new Rotate(-45.0));
//		System.out.println("Try0ing to rotate");
//		addSequential(new Rotate(90.0));
//		addSequential(new Rotate(-90.0));
				
//============BELOW: CODE FROM FREDERICK================================================	
		
//                           Passing Motor velocity, Current Heading, Timeout  

		//addSequential(new DriveEncoder(0.2, 120.0));
		//addSequential(new DriveEncoder(0.3, 10.0));
		//addSequential(new DriveRangeFinder (0.3, 40.0));
		//addSequential(new Rotate(-45.0));                 //positive is clockwise
		//addSequential(new DriveEncoder(0.3, 10.0));    // public DriveEncoder(double speed, double  distance) {
//		addSequential(new Rotate(90.0));
//		addSequential(new DriveEncoder(0.3, 200.0));    // public DriveEncoder(double speed, double  distance) {
//		addSequential(new Rotate(90.0)); 
//		addSequential(new Rotate(90.0)); 
//		addSequential(new DriveRangeFinder(0.3, 40.0));    // public DriveEncoder(double speed, double  distance) {
//		addSequential(new Rotate(90.0)); 
//		addSequential(new Rotate(90.0)); 
		//addSequential(new DriveEncoder(0.2, -72.0));    // public DriveEncoder(double speed, double  distance) {
				
//		addSequential(new DriveStraight(0.2, Robot.driveTrain.getHeading()), 1);
//				
//		addSequential(new Rotate(45.0)); 
//		addSequential(new Rotate(-45.0));
//		System.out.println("Trying to rotate");
//		addSequential(new Rotate(90.0));
//		addSequential(new Rotate(-90.0));
//=============Below is code from Ethan :D================
		addSequential(new DriveEncoder(0.45, 153.0));			//speed, inches
		addSequential(new Rotate(50.0));						//degrees
		addSequential(new DriveEncoder(0.45, 45));
		addSequential(new ShootBoulder());
	}
}
