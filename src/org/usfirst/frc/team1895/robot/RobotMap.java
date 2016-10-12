package org.usfirst.frc.team1895.robot;

public interface RobotMap {
	//OI
		final int f310_port = 0;
		double f310_axis_scaler = 0.55;
		double f310_axis_scalerX = 1.00;  // Lateral :D
		double f310_axis_scalerY = 0.75;  //  Forward
		int joy_port  = 1;
		
	//Buttons
		//int ArmSeekPositionButtonNumber = 3;
		int arm_upper_limit_button_number = 4;
		int arm_middle_button_number = 2;
		int arm_lower_limit_button_number = 5;
	
	//Analog IO
		int gyro_yi_port = 0;
		int gyro_er_port = 1;
		int rangeFinder_port = 2;
	
	//Digital IO
		int outer_boulder_port  = 6;
		int inner_boulder_port  = 5;
		int left_encoderA_port  = 1;
		int left_encoderB_port  = 2;
		int right_encoderA_port = 3;
		int right_encoderB_port = 4;
		int arm_switch_upper_limit_port = 19;		//7, 19
		int arm_switch_middle_port = 15;			//8, 15
		int arm_switch_lower_limit_port = 11;		//9, 11
	
	//CAN
		int left_motor1_port   = 5;
		int left_motor2_port   = 3;
		int right_motor1_port  = 4;
		int right_motor2_port  = 6;
		int hinge2_motor_port  = 1;
		int hinge1_motor_port  = 2;
		int intake_motor_port  = 7;
		int spare_motor_port   = 8; //not programmed yet. when the talon is finally wired up it will show as 0 and must be changed in 172.22.11.2
	
	//Cameras
		String cam0 = "cam0";
		String cam1 = "cam1";
		String cam2 = "cam2";
		String cam3 = "cam3";
		int num_cams = 4;
	
}
