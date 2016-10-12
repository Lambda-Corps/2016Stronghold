package org.usfirst.frc.team1895.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1895.robot.Robot;

public class Rotate extends Command {

	private boolean isFinished;
	double turnAngle = 0;
	
	public Rotate(double turnAngle) {	//positive clockwise
		//requires(Robot.exampleSubsystem);
		requires(Robot.driveTrain);
		isFinished = false;
		this.turnAngle = turnAngle;
	}

	protected void initialize() {
	}

	protected void execute() {
		isFinished = Robot.driveTrain.turn(turnAngle);
		System.out.println(Robot.driveTrain.getHeading());
		
	}

	protected boolean isFinished() {
		return isFinished;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
