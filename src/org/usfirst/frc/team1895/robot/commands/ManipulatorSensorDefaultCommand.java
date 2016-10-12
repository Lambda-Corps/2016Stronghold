package org.usfirst.frc.team1895.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1895.robot.Robot;

public class ManipulatorSensorDefaultCommand extends Command {

	public ManipulatorSensorDefaultCommand() {
		requires(Robot.manipulator);
	}

	protected void initialize() {
		Robot.manipulator.manuallyMoveArm(0.0);
		Robot.manipulator.intakeSpeed(0.0);
		SmartDashboard.putNumber("Controller Axis Value", Robot.oi.joy.getRawAxis(1));
		
	}

	protected void execute() {
		Robot.manipulator.manuallyMoveArm(Robot.oi.joy.getRawAxis(1));
		Robot.manipulator.updateState();
		Robot.manipulator.updateMotorState();
		
		Robot.manipulator.checkSensorValues();
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
