package org.usfirst.frc.team1895.robot.commands;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.subsystems.ManipulatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManipulatorLowerLimit extends Command {

    public ManipulatorLowerLimit() {
        requires(Robot.manipulator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.manipulator.moveArm(ManipulatorSubsystem.armStateLowerLimit);
    	System.out.println("execute lower limit command");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.manipulator.getDefaultCommand().start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
