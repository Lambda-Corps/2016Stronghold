package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.commands.ManipulatorLowerLimit;
import org.usfirst.frc.team1895.robot.commands.ManipulatorMiddle;
import org.usfirst.frc.team1895.robot.commands.ManipulatorUpperLimit;
import org.usfirst.frc.team1895.robot.custom.F310;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public F310 f310;
	public Joystick joy;
	
	public OI() {
		f310 = new F310(RobotMap.f310_port);
		joy = new Joystick(RobotMap.joy_port);
		
		JoystickButton ArmUpperLimitButton = new JoystickButton(joy, RobotMap.arm_upper_limit_button_number);
		JoystickButton ArmMiddleButton = new JoystickButton(joy, RobotMap.arm_middle_button_number);
		JoystickButton ArmLowerLimitButton = new JoystickButton(joy, RobotMap.arm_lower_limit_button_number);
		
		//ArmUpperLimitButton.whenPressed(new ManipulatorUpperLimit());
		//ArmMiddleButton.whenPressed(new ManipulatorMiddle());
		//ArmLowerLimitButton.whenPressed(new ManipulatorLowerLimit());
		
		
	}
}

