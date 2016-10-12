package org.usfirst.frc.team1895.robot.subsystems;

// D.Frederick  Feb 27 Notes on Manipulator
//
//  Need to be sure we switch back to CANTalon when we deploy  ( !! two places )
//
//

/*
Feb 27
Important Changes:

Updated code to handle "armStateUnknown" differently.  If in unknown state, we will drive the gripper upward 
until a switch is detected and then set the current state to the detected switch

Line 122:      Updated not return if in "armStateUnknown" state
Lines 160-220: Removed "while" conditionals
Lines 222-240: Added code to drive gripper upward until we reach a known state
*/


import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.ManipulatorSensorDefaultCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManipulatorSubsystem extends Subsystem {
	
	//motors go here
	private CANTalon left_arm_motor; //These are paired motors; have to be the same
	private CANTalon right_arm_motor;//
	
	private CANTalon intake_motor;
//	private Talon intake_motor;
//	private Talon left_arm_motor;
//	private Talon right_arm_motor;
	
	public DigitalInput outerSensor;
	public DigitalInput innerSensor;
	 
	private DigitalInput arm_switch_upper_limit;
	private DigitalInput arm_switch_middle;
	private DigitalInput arm_switch_lower_limit;
	
	private int boulderState;
	
	private boolean manual_control = true;
	
	/* 
	 *  These are the state variables for the locations that the arm buttons will travel to. The states are:
	 *  0 = unknown
	 *  1 = on middle switch
	 *  2 = lower limit switch
	 *  3 = upper limit switch
	 */
	public static final int armStateUnknown    = 0;
	public static final int armStateLowerLimit = 1;
	public static final int armStateMiddle     = 2;
	public static final int armStateUpperLimit = 3;
	
	private int armPositionState ;
	
	boolean lower;
	boolean middle;
	boolean upper;
	/***/
	public ManipulatorSubsystem() {
//		left_arm_motor = new CANTalon(RobotMap.hinge2_motor_port);
//		right_arm_motor = new CANTalon(RobotMap.hinge1_motor_port);
//		
//		intake_motor = new CANTalon(RobotMap.intake_motor_port);
		intake_motor = new CANTalon(RobotMap.intake_motor_port);
		left_arm_motor = new CANTalon(RobotMap.hinge1_motor_port);
		right_arm_motor = new CANTalon(RobotMap.hinge2_motor_port);

		innerSensor = new DigitalInput(RobotMap.inner_boulder_port);
		outerSensor = new DigitalInput(RobotMap.outer_boulder_port);
		
		
		//  (DF)  Testing has shown the sensors are True when no magnet is present
		arm_switch_upper_limit = new DigitalInput(RobotMap.arm_switch_upper_limit_port);
		arm_switch_middle      = new DigitalInput(RobotMap.arm_switch_middle_port);
		arm_switch_lower_limit = new DigitalInput(RobotMap.arm_switch_lower_limit_port);
		
		// TODO REmove
		lower = middle = upper = false;
		
		// Print to screen current 
		SmartDashboard.putNumber("armPositionState",  armPositionState);
		SmartDashboard.putBoolean("Sensor Mid",  middle);
		SmartDashboard.putBoolean("Sensor Up",  upper);
		SmartDashboard.putBoolean("Sensor Low",  lower);
		
		// No boulder present    
		boulderState = 0;
		armPositionState = armStateUnknown;
		
 	}
	
	/*This method moves the arm between the three available magnet switches**/
	//  Inputs: 
	//	   Input parameter is the desired position
	//     Read the switches to get current position
	//  Outputs:
	//     Changes motor speed
	//
	public boolean moveArm(int toPosition) {
		double speed = 0.0;
		final double SpeedMovingUp       =  0.55;
		final double SpeedMovingDown     = -0.35;
		final double SpeedMovingUpslow   =  0.33;
	
		System.out.println ("Desired position: " + toPosition +  " Current position: "  + armPositionState + "  Speed:" + speed);
		System.out.println ("Sensors: (true = open/No magnet:   " 
				+ arm_switch_upper_limit.get() + "  " + arm_switch_middle.get() + " "  + arm_switch_lower_limit.get());
		
		// Input validation
		// Skip all the code if the "toPosition" is not a valid number
		if(toPosition > 3 || toPosition < 1) {
			return false;
		}
		
		// Skip all the code if you are in the correct position
		if(toPosition == armPositionState) {
			System.out.println (">>>>>>>>  At desired position ==================== ");
			return true;
		}
		
		if(armPositionState == armStateUnknown) {
			System.out.println (">>>>>>>>  At unknown position ???????????????? ");
//			return false;
		}
		//  Only continue through this code is Desired position is different from current position
		
		
		//  This code defines the motor direction and speed based on our current position
		//  Does not actually move the motors
		switch(armPositionState) { // decide based on our current position 
		case armStateLowerLimit:         //  We are at the bottom and need to Move the gripper upward
			speed = SpeedMovingUp;
			break;
		case armStateUpperLimit:         //  We are at the top and need to Move the gripper downward
			speed = SpeedMovingDown;
			break;
		case armStateMiddle:             //  Move the gripper 
			if(toPosition < armStateMiddle) {
				speed = SpeedMovingDown;
			}
			else {
				speed = SpeedMovingUp;
			}
			break;
			
		case armStateUnknown:         //  We do not know where the gripper is so move upward slowly
			speed = SpeedMovingUpslow;
			break;
			//  We are between settings and we are not changing motor speed
			default:
			break;
		}
		
		SmartDashboard.putNumber("speed",  speed);
		
		//   Send speed value to the motor based on direction and stop when we get there.
		//   Sets new arm position state (current value) - Class level variable !!!  
		//   Requires that you are at a know position
		
		switch(armPositionState) {
		case armStateLowerLimit:
			if(toPosition == armStateMiddle) {        // Run this code if moving to middle position
				if(arm_switch_middle.get()) {         // Changed from WHILE to IF  /// Removed NOT (!)
					left_arm_motor.set(speed);	      // going up
					right_arm_motor.set(speed);
				}                                     // Run the motors until we get to the desired position
				else {
					left_arm_motor.set(0.0);              // then stop and set the new position
					right_arm_motor.set(0.0);
					armPositionState = armStateMiddle;    // !! Setting new current position
				}
			}
			else {                                     // Run this code if going to upper position
				
				if(arm_switch_upper_limit.get()) {
					left_arm_motor.set(speed);	       //going up
					right_arm_motor.set(speed);
				}                                      // Run the motors until we get to the desired position
				else {
					left_arm_motor.set(0.0);               // then stop and set the new position
					right_arm_motor.set(0.0);
					armPositionState = armStateUpperLimit; // !! Setting new current position
				}
			}
			break;
		case armStateUpperLimit:	
			if(toPosition == armStateMiddle) {        // currently at upper position moving to middle
				if(arm_switch_middle.get()) {
					left_arm_motor.set(speed);	      //going down
					right_arm_motor.set(speed);
				}                                     // Run the motors until we get to the desired position
				else {
					left_arm_motor.set(0.0);              // then stop and set the new position
					right_arm_motor.set(0.0);
					armPositionState = armStateMiddle;    // !! Setting new current position
				}
			}
			else {								      // currently at upper position moving to lowest
				if(arm_switch_lower_limit.get()) {
					left_arm_motor.set(speed);	      //going down
					right_arm_motor.set(speed);
				}                                     // Run the motors until we get to the desired position
				else {
					left_arm_motor.set(0.0);              // then stop and set the new position
					right_arm_motor.set(0.0);
					armPositionState = armStateLowerLimit; // !! Setting new current position
				}
			}
			break;
		case armStateMiddle:
			if(toPosition == armStateUpperLimit) {
				if(arm_switch_upper_limit.get()) {    // currently at middle position moving to upper
					left_arm_motor.set(speed);	      //going down   (shouldn't this be going up?)
					right_arm_motor.set(speed);
				}                                     // Run the motors until we get to the desired position
				else {
					left_arm_motor.set(0.0); 		  // then stop and set the new position
					right_arm_motor.set(0.0);
					armPositionState = armStateUpperLimit;
				}
			}
			else {
				if(arm_switch_lower_limit.get()) {    // currently at middle position moving to lower
					left_arm_motor.set(speed);	      //going down
					right_arm_motor.set(speed);
				}                                     // Run the motors until we get to the desired position
				else {
					left_arm_motor.set(0.0);              // then stop and set the new position
					right_arm_motor.set(0.0);
					armPositionState = armStateLowerLimit;// !! Setting new current position
				}
			}
			break;
			
			// Current position is unknown, will move upward slowly with goal of finding position
		case armStateUnknown:                
			System.out.println (" ^ ^ ^ ^ ^ ^ ^ ^ ^ ^   Seeking ");
			left_arm_motor.set(speed);	              // Move gripper upward until we find a position or STALL
			right_arm_motor.set(speed);
			if(arm_switch_lower_limit.get()) {        // Check for switch closure
				left_arm_motor.set(0.0);              // then stop and set the new position
				right_arm_motor.set(0.0);
				armPositionState = armStateLowerLimit;// !! Setting new current position
				};
			if(arm_switch_middle.get()) {
				left_arm_motor.set(0.0);              // then stop and set the new position
				right_arm_motor.set(0.0);
				armPositionState = armStateLowerLimit;// !! Setting new current position
				};
			if(arm_switch_upper_limit.get()) {
				left_arm_motor.set(0.0);              // then stop and set the new position
				right_arm_motor.set(0.0);
				armPositionState = armStateLowerLimit;// !! Setting new current position
				};
			
			break;
			
		}	// End of Switch  "switch(armPositionState)"
			
		return true;
	}
		
		
	public void checkSensorValues(){
//		System.out.println("Check sensor values");
		lower = arm_switch_lower_limit.get();
		middle = arm_switch_middle.get();
		upper = arm_switch_upper_limit.get();
	}
	
	public void manuallyMoveArm(double speed) {
		System.out.println ("Sensors: (true = open/No magnet:   " 
				+ arm_switch_upper_limit.get() + "  " + arm_switch_middle.get() + " "  + arm_switch_lower_limit.get());
		System.out.println("Manually Moving Arm with Speed: " + speed);
		SmartDashboard.putNumber("Manual Speed", speed);
		if(speed > 0) { 
			left_arm_motor.set(speed * 0.75);	//going down
			right_arm_motor.set(speed* 0.75);
		} else if(speed < 0) {
			left_arm_motor.set(speed * 0.45);	//going up
			right_arm_motor.set(speed* 0.45);
		} else {
			left_arm_motor.set( 0.0);
			right_arm_motor.set(0.0);
		}
		
//		if(arm_switch_lower_limit.get()) {
//			left_arm_motor.set( 0.0);
//			right_arm_motor.set(0.0);
//			armPositionState = armStateLowerLimit;
//		}
//		else if(arm_switch_middle.get()){
//			left_arm_motor.set( 0.0);
//			right_arm_motor.set(0.0);
//			armPositionState = armStateMiddle;
//		}
//		else if(arm_switch_upper_limit.get()) {
//			left_arm_motor.set( 0.0);
//			right_arm_motor.set(0.0);
//			armPositionState = armStateUpperLimit;
//		}
	}
	
	
	/***/
	public void intakeSpeed(double velocity) {
		intake_motor.set(velocity);
	}
	
	/***/
	public int getState() {
		return boulderState;
	}
	
	/***/
	public void updateState() {
		System.out.println("updateState start");
		SmartDashboard.putBoolean("getOuterSensor", outerSensor.get());
		SmartDashboard.putBoolean("getInnerSensor", innerSensor.get());
		if(innerSensor.get()==false && outerSensor.get()==false && boulderState==3) boulderState=0; //not in range; just left
		if(innerSensor.get()==false && outerSensor.get()==true  && boulderState==0) boulderState=1; //boulder detected; intaking
		if(innerSensor.get()==true  && outerSensor.get()==true  && boulderState==1) boulderState=2; //boulder held
		if(Robot.oi.joy.getRawButton(1)                         && boulderState==2) boulderState=3; //firing
		SmartDashboard.putNumber("updateState", boulderState);

	}

	/**Hi Marty!*/
	public void updateMotorState() {
		if(manual_control) {
			driveMotor(Robot.oi.joy.getRawButton(1), Robot.oi.joy.getRawButton(2));
		} else {
			switch(boulderState) {
				case 0:
					intake_motor.set( 0.00);
					break;
				case 1:
					intake_motor.set( 0.30);
					break;
				case 2:
					intake_motor.set( 0.00);
					break;
				case 3:
					intake_motor.set(-0.20);
					break;
			}
		}
	}
	
	public void driveMotor(boolean in, boolean out) {
		if(in && !out) {
			intake_motor.set( 0.70);
		} else if (!in && out) {
			intake_motor.set(-0.45);
		} else {
			intake_motor.set( 0.00);
		}
	}
	

	public void initDefaultCommand() {
		setDefaultCommand(new ManipulatorSensorDefaultCommand());
//		System.out.println("initializae default command for manipulator");
	}
}

