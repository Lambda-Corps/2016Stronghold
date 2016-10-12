package org.usfirst.frc.team1895.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1895.robot.Robot;

public class DriveStraight extends Command {

	public double velocity;
	public double desiredAngle;
	
	public DriveStraight(double velocity, double gyro) {
		//requires(Robot.exampleSubsystem);
		requires(Robot.driveTrain);
		this.velocity = velocity;
		this.desiredAngle = gyro;
		System.out.println("drive straight command");
	}

	protected void initialize() {
		System.out.println("drive straight initialize");
	}

	protected void execute() {
//		System.out.println("drive straight execute before");
		Robot.driveTrain.driveStraightWithGyro(velocity, desiredAngle);
//		System.out.println("drive straight execute after");
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
