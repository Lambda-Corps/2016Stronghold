package org.usfirst.frc.team1895.robot.custom;

import edu.wpi.first.wpilibj.AnalogGyro;

public class DoubleGyro {
	
	private AnalogGyro gyro_yi;
	private AnalogGyro gyro_er;
	
	/***/
	public DoubleGyro(int yi_port, int er_port) {
		gyro_yi = new AnalogGyro(yi_port);
		gyro_er = new AnalogGyro(er_port);
	}
	
	/***/
	public double getHeading() {
		return gyro_yi.getAngle();
	}
	
	/***/
	public void calibrate() {
		gyro_yi.calibrate();
		gyro_er.calibrate();
	}
	
	/***/
	public void reset() {
		gyro_yi.reset();
		gyro_er.reset();
	}
}
