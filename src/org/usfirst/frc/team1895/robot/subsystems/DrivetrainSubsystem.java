package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.MainDrive;
import org.usfirst.frc.team1895.robot.custom.DoubleGyro;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivetrainSubsystem extends Subsystem {
	
	private CANTalon left_motor1;//These are paired motors; have to be the same
	private CANTalon left_motor2;//
	private CANTalon right_motor1;//These are paired motors; have to be the same
	private CANTalon right_motor2;//
	
	private DoubleGyro gyro;
	private AnalogInput rangefinder;		
	
	private Encoder left_encoder;
	private Encoder right_encoder;
	
	private double endAngle;
	private boolean firstTimeThrough;

	private boolean firsttimeusingEncoder = true;
	double totaldistancetraveledLeft = 0;
	double totaldistancetraveledRight = 0;
	double desiredDrivingAngle = 0;
		
	/***/
	public DrivetrainSubsystem() {
		left_motor1  = new CANTalon(RobotMap.left_motor1_port);
		left_motor2  = new CANTalon(RobotMap.left_motor2_port);
		right_motor1 = new CANTalon(RobotMap.right_motor1_port);
		right_motor2 = new CANTalon(RobotMap.right_motor2_port);
		
		left_encoder = new Encoder(RobotMap.left_encoderA_port, RobotMap.left_encoderB_port, true);
		right_encoder = new Encoder(RobotMap.right_encoderA_port, RobotMap.right_encoderB_port);
		
		gyro = new DoubleGyro(RobotMap.gyro_yi_port, RobotMap.gyro_er_port);
		
		rangefinder = new AnalogInput(RobotMap.rangeFinder_port);
		
		firstTimeThrough = true;
		firsttimeusingEncoder = true;
	}
	
	/***/
	public void basicArcadeDrive(double x_axis, double y_axis) {
		double w1 = x_axis - y_axis;
		double w2 = x_axis + y_axis;
		
		double w_max = Math.max(Math.abs(w1), Math.abs(w2));
		
		if(Math.abs(w_max) > 1.0) {
			w1 = w1 / w_max;
			w2 = w2 / w_max;
		}
			
		if(w1 >  1.0) {w1 =  1.0;}
		if(w1 < -1.0) {w1 = -1.0;}
		if(w2 >  1.0) {w2 =  1.0;}
		if(w2 < -1.0) {w2 = -1.0;}
		
		left_motor1.set(w1);
		left_motor2.set(w1);
		right_motor1.set(w2);
		right_motor2.set(w2);
		System.out.println("Left Motor 1  " + left_motor1.getOutputCurrent()   + " " +  left_motor2.getOutputCurrent() + 
    			   "Right Motor 1 " + right_motor1.getOutputCurrent()  + " " + right_motor2.getOutputCurrent());
	}
	
	/***/
	public void basicTankDrive(double l_axis, double r_axis) {
		left_motor1.set(-l_axis);
		left_motor2.set(-l_axis);
		right_motor1.set(r_axis);
		right_motor2.set(r_axis);
		
		SmartDashboard.putNumber("Left Motor 1", left_motor1.getOutputCurrent());
		SmartDashboard.putNumber("Left Motor 2", left_motor2.getOutputCurrent());
		SmartDashboard.putNumber("Right Motor 1", right_motor1.getOutputCurrent());
		SmartDashboard.putNumber("Right Motor 2", right_motor2.getOutputCurrent());

		

		
//		System.out.println("Left Motor 1"+ left_motor1.getOutputCurrent());
//		System.out.println("Left Motor 2"+ left_motor2.getOutputCurrent());
//		System.out.println("Right Motor 1"+ right_motor1.getOutputCurrent());
//		System.out.println("Right Motor 2"+ right_motor2.getOutputCurrent());
	}
	
	/***/
	public void driveCar(double drive, double yaw) {
		double w1 = drive;
		double w2 = drive;
		
		double minimum = 0.4;
		
		if(yaw < 0.1) {
			if(drive < 0) {
				w1 *= 1 - (Math.abs(yaw) * (1 - minimum));
			} else {
				w2 *= 1 - (Math.abs(yaw) * (1 - minimum));
			}
			//w1 *= 1 - (Math.abs(yaw) * (1 - minimum));
		} else if(yaw > 0) {
			if(drive > 0) {
				w1 *= 1 - (Math.abs(yaw) * (1 - minimum));
			} else {
				w2 *= 1 - (Math.abs(yaw) * (1 - minimum));
			}
			//w2 *= 1 - (Math.abs(yaw) * (1 - minimum));
		} else {
			//nothing :)
		}
		
		left_motor1.set(-w1);
		left_motor2.set(-w1);
		right_motor1.set(w2);
		right_motor2.set(w2);
		
		SmartDashboard.putNumber("w1", w1);
		SmartDashboard.putNumber("w2", w2);
	}
	
	/***/
	public void driveStraight(double velocity) {
		left_motor1.set(-velocity);
		left_motor2.set(-velocity);
		right_motor1.set(velocity);
		right_motor2.set(velocity);
	}
	
	/***/
	public void driveStraightWithGyro(double velocity, double desiredAngle) {
		double currentAngle = getHeading();
		double threshold = 1.0;
		double leftsideVelocity = velocity;
		double rightsideVelocity = velocity;
		double speedAdjust = 0.75; 
		
		if(Math.abs(currentAngle - desiredAngle) < threshold)	{ //within threshold
//		System.out.println("i'm moving");
			
			leftsideVelocity  = velocity;
			rightsideVelocity = velocity;
			
			left_motor1.set(leftsideVelocity);
			left_motor2.set(leftsideVelocity);
			right_motor1.set(-rightsideVelocity);
			right_motor2.set(-rightsideVelocity);
			
		} else if(currentAngle - desiredAngle > 0) { 			//too far right
			
			leftsideVelocity  = velocity * speedAdjust;  // Slow down left side
			rightsideVelocity = velocity / speedAdjust;
			
			left_motor1.set(leftsideVelocity);
			left_motor2.set(leftsideVelocity);
			right_motor1.set(-rightsideVelocity);
			right_motor2.set(-rightsideVelocity);

//			left_motor1.set(velocity*0.90);
//			left_motor2.set(velocity*0.90);
//			right_motor1.set(-velocity);
//			right_motor2.set(-velocity);
		} else { 												//too far left
			leftsideVelocity  = velocity / speedAdjust;  			
			rightsideVelocity = velocity * speedAdjust;  // Slow down right side
			left_motor1.set(leftsideVelocity);
			left_motor2.set(leftsideVelocity);
			right_motor1.set(-rightsideVelocity);
			right_motor2.set(-rightsideVelocity);

//			left_motor1.set(velocity);
//			left_motor2.set(velocity);
//			right_motor1.set(-velocity*0.90);
//			right_motor2.set(-velocity*0.90);
		}
			;
			SmartDashboard.putNumber("currentAngle", currentAngle);
			SmartDashboard.putNumber("leftsideVelocity",leftsideVelocity );			
			SmartDashboard.putNumber("rightsideVelocity",rightsideVelocity );
			
			
//			SmartDashboard.putNumber("", );
			
	}
	
	/***/
	public void rotate(double yaw) {
		left_motor1.set(-yaw);
		left_motor2.set(-yaw);
		right_motor1.set(yaw);
		right_motor2.set(yaw);
	}
	
	/**turn: turns the robot a certain angle using the gyro. This is supposed to be used as a command.*/
	public boolean turn(double deltaturn) {
		
		double absolutedifference = 0;
		double turnSpeed = -1.00;
		
		if (deltaturn >  180) deltaturn =  180;
		if (deltaturn < -180) deltaturn = -180;
		
		double currentAngle = getHeading();
		
		if (firstTimeThrough) {
			firstTimeThrough = false;
			endAngle = currentAngle + deltaturn;
		}
		if (deltaturn >= 0) {
			// Turn left
			basicTankDrive(turnSpeed,  -0.33*turnSpeed);
		}
		if (deltaturn < 0) {
			// Turn Right
			basicTankDrive(-0.33*turnSpeed, turnSpeed);
		}
		
		absolutedifference = Math.abs(endAngle-currentAngle);
		
		System.out.println("difference = " + absolutedifference);
		System.out.println("endAngle = " + endAngle);
		if (absolutedifference <= 5 ) {
			basicTankDrive(0.0, 0.0);
			System.out.println("Reached turning goal ");
			
			// Reset flag for next interaction thru method
			firstTimeThrough = true;
			return true;
		}
		SmartDashboard.putNumber("desiredAngle", endAngle);
		SmartDashboard.putNumber("currentAngle", currentAngle);
		return false;
	}
	
	/**Drive straight with encoders: called by command. Sets total distance to two feet, starts driving, gets encoders + adds*/
	public boolean drivestraightwithEncoders(double velocity, double totaldistancetoTravel) {
		
		//sets initial return value as false
		boolean returnVal = false;
		
		//sets encoder distances as zero
		double currentleftencoderDistance  = 0;
		double currentrightencoderDistance = 0;
		
		//scaler
		double distancePerPulse = 0.070;

		//starts motors
//			left_motor1.set(-velocity);
//			left_motor2.set(-velocity);
//			right_motor1.set(velocity);
//			right_motor2.set(velocity);
	
		if (firsttimeusingEncoder) {
			firsttimeusingEncoder = false;
			desiredDrivingAngle = getHeading();
			
			//sets distance per pulse. Based on diameter of wheels to ensure accurate distance is traveled.
			left_encoder.setDistancePerPulse( distancePerPulse);
			right_encoder.setDistancePerPulse(distancePerPulse);
			
			//resets encoder values to zero
			left_encoder.reset();
			right_encoder.reset();
			
			//calculates total distance traveled by both encoders.
			totaldistancetraveledLeft  = totaldistancetoTravel + left_encoder.getDistance();
			totaldistancetraveledRight = totaldistancetoTravel + right_encoder.getDistance();
			
		}
		
		driveStraightWithGyro( velocity, desiredDrivingAngle) ;
		
		
		currentleftencoderDistance = left_encoder.getDistance();
		currentrightencoderDistance = right_encoder.getDistance();

    	//double distanceTraveled = endDistance - currentleftencoderDistance
//			SmartDashboard.putNumber("left_encoder", currentleftencoderDistance);
//			SmartDashboard.putNumber("right_encoder", currentrightencoderDistance);
		SmartDashboard.putNumber("distanceTraveled", currentrightencoderDistance);
		SmartDashboard.putNumber("distancetraveledRight", currentrightencoderDistance);
		//print encoders to screen
		System.out.println("Left Encoder: " + currentleftencoderDistance + "Right Encoder: " + currentrightencoderDistance);

		// see if met required distance to travel
		if(totaldistancetraveledLeft <= currentleftencoderDistance ||
		   totaldistancetraveledLeft <= currentrightencoderDistance){
			returnVal = true;
			firsttimeusingEncoder = true;
			
		}
		
		//return boolean 
		return returnVal;
		
	}
	
	/***/
	public boolean driveToRange (double velocity, double rangeToDrive){
			
		//sets initial return value as false
		boolean returnVal = false;
		double currentRange = 0;
		double rangerFinderScaler = 3.0;

					

			// Rangefinder port rangeFinder
		currentRange = rangerFinderScaler * rangefinder.getVoltage();
		SmartDashboard.putNumber("currentRange", currentRange);
		SmartDashboard.putNumber("rangeToDrive", rangeToDrive);
		
		if (currentRange >= rangeToDrive ){ 
			driveStraight(velocity);    //  Continue to true
			returnVal = false;
		} else {
			driveStraight(0.0);    // reached range and Stop the robot
			returnVal = true;
		}
		

		return returnVal;
	}
	
	/***/
	public double getEncoder(int whichOne) {
		switch(whichOne) {
			case 0:
				return left_encoder.getDistance();
			case 1:
				return right_encoder.getDistance();
		}
		return 0.0;
	}
	
	/**Calling DoubleGyro.getHeading()*/
	public double getHeading() {
		return gyro.getHeading();
	}
	
	/**Calling DoubleGyro.calibrate()*/
	public void calibrateGyros() {
		gyro.calibrate();
	}
	
	/**Calling DoubleGyro.reset()*/
	public void resetGyros() {
		gyro.reset();
	}
	
	/***/
	public void initDefaultCommand() {
		setDefaultCommand(new MainDrive());
	}
}