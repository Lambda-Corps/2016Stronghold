package org.usfirst.frc.team1895.robot.custom;

import edu.wpi.first.wpilibj.Joystick;

public class F310 extends Joystick {
	
	private static final int numAxis = 6;
	private static final int numButtons = 10;
	
	private double[] scalar = new double[numAxis];
	
	public F310(int port) {
		super(port, numAxis, numButtons);
		for(double i : scalar) { i = 1.0;}
	}
	
	public double get(Axis axis) {
		return this.getRawAxis(axis.getID()) * scalar[axis.getID()];
	}
	
	public double getRaw(Axis axis) {
		return this.getRawAxis(axis.getID());
	}
	
	public boolean get(Buttons button) {
		return this.getRawButton(button.getID());
	}
	
	public void setScalar(Axis axis, double value) {
		scalar[axis.getID()] = value;
	}
	
	public enum Buttons {
		A(0),
		B(1),
		X(2),
		Y(3),
		LB(4),
		RB(5),
		START(6),
		SELECT(7),
		LAxis(8),
		RAxis(9);
		
		private final int id;
		
		private Buttons(int id) {
			this.id = id;
		}
		
		public int getID() { return id;}
	}
	
	public enum Axis {
		LX(0),
		RX(1),
		LY(2),
		RY(3),
		LT(4),
		RT(5);
		
		private final int id;
		
		private Axis(int id) {
			this.id = id;
		}
		
		public int getID() { return id;}
	}
}