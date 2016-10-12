package org.usfirst.frc.team1895.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1895.robot.commands.*;
import org.usfirst.frc.team1895.robot.subsystems.*;

import com.ni.vision.VisionException;

public class Robot extends IterativeRobot {
	
	public static DrivetrainSubsystem driveTrain;
	public static ManipulatorSubsystem manipulator;
	public static CameraPartialSubsystem camera;
	public static OI oi;
	
	Command autonomousCommand;
	SendableChooser chooser;

	/**Called when the robot is powered on*/
	public void robotInit() {
		driveTrain = new DrivetrainSubsystem();
		manipulator = new ManipulatorSubsystem();
		camera = new CameraPartialSubsystem();
		
		chooser = new SendableChooser();
		chooser.addDefault("Autonomous Default", new Autonomous());
		chooser.addObject("Autonomous PlanB", new AutonomousPlanB());
		SmartDashboard.putData("Autonomous Mode Selector", chooser);
		
		driveTrain.calibrateGyros();
	
		autonomousCommand = new Autonomous(); 
		System.out.println("End of robot init");
		oi = new OI();
	}
	
	/**Called when the robot is disabled*/
	public void disabledInit(){
		try {
			camera.endStream();
		} catch (NIVisionException e) {
			
		} catch (VisionException f) {
			
		}
	}
	
	/**Find out what this does please*/
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**Called at the beginning of autonomous mode*/
	public void autonomousInit() {
		//autonomousCommand = new Autonomous();
		
		autonomousCommand = (Command) chooser.getSelected();
		String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
			case "Autonomous Default":
				autonomousCommand = new Autonomous();
				break;
			case "Autonomous PlanB":
				autonomousCommand = new AutonomousPlanB();
				break;
			default:
				autonomousCommand = new Autonomous();
				break;
		}
		
		driveTrain.resetGyros();
		
		if (autonomousCommand != null) autonomousCommand.start();
	}
	
	/**Called repeatedly during autonomous mode*/
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**Called at the beginning of teleop mode*/
	public void teleopInit() {
		if (autonomousCommand != null) autonomousCommand.cancel();
		try {
			camera.init();
		} catch (NIVisionException e) {
			
		} catch (VisionException f) {
			
		}
	}
	
	/**Called repeatedly during teleop mode*/
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		try {
			camera.run();
		} catch (NIVisionException e) {
			
		} catch (VisionException f) {
			
		}
	}
	
	/**Called repeatedly during test mode*/
	public void testPeriodic() {
		LiveWindow.run();
	}
}
