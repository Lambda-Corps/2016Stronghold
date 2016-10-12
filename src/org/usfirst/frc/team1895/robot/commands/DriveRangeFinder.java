package org.usfirst.frc.team1895.robot.commands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRangeFinder extends Command {

	double speed = 0.0; 
	double distance = 0;
	boolean isFinished;
	
    public DriveRangeFinder(double speed, double  distance) {
    	requires(Robot.driveTrain);
    	this.speed = speed;				
    	this.distance= distance;
    	
    	isFinished = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	isFinished = Robot.driveTrain.drivestraightwithEncoders(speed, distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return isFinished; 
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
