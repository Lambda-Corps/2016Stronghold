package org.usfirst.frc.team1895.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1895.robot.Robot;

import org.usfirst.frc.team1895.robot.enums.F310Axis;

public class MainDrive extends Command {

	public MainDrive() {
		requires(Robot.driveTrain);
	}

	protected void initialize() {
		Robot.driveTrain.basicTankDrive(0.0, 0.0);
	}

	protected void execute() {
//		Robot.driveTrain.basicArcadeDrive(Robot.oi.f310.getScaledAxis(F310Axis.RX), Robot.oi.f310.getScaledAxis(F310Axis.LY)); //   Different scaling
		Robot.driveTrain.basicArcadeDrive(Robot.oi.f310.getScaledAxis(F310Axis.RX), Robot.oi.f310.getScaledAxis(F310Axis.LY)); //Avalannnn
		//Robot.driveTrain.basicTankDrive(Robot.oi.f310.getScaledAxis(F310Axis.LY), Robot.oi.f310.getScaledAxis(F310Axis.RY)); //Ethan
		//Robot.driveTrain.driveCar(Robot.oi.f310.getScaledAxis(F310Axis.RX), Robot.oi.f310.getScaledAxis(F310Axis.LY)); //Ethan's Clone
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
