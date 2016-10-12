package org.usfirst.frc.team1895.robot.custom;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.enums.F310Axis;
import org.usfirst.frc.team1895.robot.enums.F310Buttons;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class F310 extends Joystick {
	
	private Button button_a;
	private Button button_b;
	private Button button_x;
	private Button button_y;
	private Button button_lb;
	private Button button_rb;
	private Button button_back;
	private Button button_start;
	private Button button_laxis;
	private Button button_raxis;
	
	/***/
	public F310(int port) {
		super(port, 6, 10);
		
		button_a	 = new JoystickButton(this, 0);
		button_b	 = new JoystickButton(this, 1);
		button_x	 = new JoystickButton(this, 2);
		button_y	 = new JoystickButton(this, 3);
		button_lb    = new JoystickButton(this, 4);
		button_rb    = new JoystickButton(this, 5);
		button_back  = new JoystickButton(this, 6);
		button_start = new JoystickButton(this, 7);
		button_laxis = new JoystickButton(this, 8);
		button_raxis = new JoystickButton(this, 9);
	}
	
	/***/
	public double getAxis(F310Axis axis) {
		switch(axis) {
			case LX:
				return this.getRawAxis(0);
			case LY:
				return this.getRawAxis(1);
			case LT:
				return this.getRawAxis(2);
			case RT:
				return this.getRawAxis(3);
			case RX:
				return this.getRawAxis(4);
			case RY:
				return this.getRawAxis(5);
		}
		return 0.0;
	}
	
	/***/
	public double getScaledAxis(F310Axis axis) {
		switch(axis) {
			case LX:
				return this.getRawAxis(0) * RobotMap.f310_axis_scalerX;   // DF added scaler
//				return this.getRawAxis(0) * RobotMap.f310_axis_scaler;
			case LY:
				return this.getRawAxis(1) * RobotMap.f310_axis_scalerY;   // DF Added scaler
//				return this.getRawAxis(1) * RobotMap.f310_axis_scaler;
			case LT:
				return this.getRawAxis(2) * RobotMap.f310_axis_scaler;
			case RT:
				return this.getRawAxis(3) * RobotMap.f310_axis_scaler;
			case RX:
				return this.getRawAxis(4) * RobotMap.f310_axis_scalerX;  // DF added scaler
//				return this.getRawAxis(4) * RobotMap.f310_axis_scaler;
			case RY:
				return this.getRawAxis(5) * RobotMap.f310_axis_scalerY;  // DF Added scaler
//				return this.getRawAxis(5) * RobotMap.f310_axis_scaler;
		}
		return 0.0;
	}
	
	/***/
	public boolean getButtonPressed(F310Buttons button) {
		switch(button) {
			case A:
				return this.getRawButton(0);
			case B:
				return this.getRawButton(1);
			case X:
				return this.getRawButton(2);
			case Y:
				return this.getRawButton(3);
			case LB:
				return this.getRawButton(4);
			case RB:
				return this.getRawButton(5);
			case START:
				return this.getRawButton(6);
			case BACK:
				return this.getRawButton(7);
			case LAXIS:
				return this.getRawButton(8);
			case RAXIS:
				return this.getRawButton(9);
			
		}
		return false;
	}
	
	/***/
	public Button getButton(F310Buttons button) {
		switch(button) {
			case A:
				return this.button_a;
			case B:
				return this.button_b;
			case X:
				return this.button_x;
			case Y:
				return this.button_y;
			case LB:
				return this.button_lb;
			case RB:
				return this.button_rb;
			case START:
				return this.button_start;
			case BACK:
				return this.button_back;
			case LAXIS:
				return this.button_laxis;
			case RAXIS:
				return this.button_raxis;
		}
		return null;
	}
}
