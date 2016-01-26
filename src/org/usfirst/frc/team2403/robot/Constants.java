package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.SerialPort;

public class Constants {
	
// Sensors
	// Sensor pack settings
	public static final byte NAVX_REF_RATE = 50;
	public static final SerialPort.Port NAVX_PORT_PORT = SerialPort.Port.kMXP;
	public static final int NAVX_PORT_BAUD_RATE = 57600;
		
	// Lift movement directions
	public static final int LIFT_BACKWARDS = -1;
	public static final int LIFT_STATIONARY = 0;
	public static final int LIFT_FORWARDS = 1;
	
	// Ports for the limit switches on the bin claw
	public static final int BIN_TOP_LIM_SWITCH_PORT = 0;
	public static final int BIN_BOT_LIM_SWITCH_PORT = 1;

	// Encoder ports
	public static final int ENC_L_PORT = 1;
	public static final int ENC_R_PORT = 2;
	public static final int ENC_M_PORT = 3;
	public static final int TOTE_ENC_PORT = 0;
	
	// Encoder directions
	public static final boolean ENC_L_DIR = false;
	public static final boolean ENC_R_DIR = true;
	public static final boolean ENC_M_DIR = false;
	public static final boolean TOTE_ENC_DIR = true;
	
	// Scale factor for movement calculations.  Value will vary by wheel size!
	public static final double DRIVE_ENCODER_CONV = .0523599; //changed for the 6in wheels from 4in
	public static final double LIFT_ENCODER_CONV = .01614583; //Should be correct for 2 in diameter, .00872665 for 1 in diameter
		
	
//Claws
	//Lift states
	public static final int PICKUP_MODE = 0;
	public static final int REGULAR_DROP_MODE = 1;
	public static final int STEP_DROP_MODE = 2;
	
	// Lift speed (up/down)
	public static final double CLAW_MOTOR_SPEED = -.8;
	public static final double CLAW_MOTOR_STOPPED = 0;
	
	//Error Size for going to location
	public static final double CLAW_ERROR_ALLOWANCE = .25;
	
	//Max number of stack states
	public static final int MAX_STACK_STATES = 7;
	
	//Lift location array
	public static final double[] TOTE_REGULAR_LOCATIONS = {0, 10, 20, 30, 40, 50, 60};
	public static final double STEP_HEIGHT = 6.25;
	public static final double NORMAL_HEIGHT = 0;
	
	//Bin Claw location pointers
	public static final int GROUND_LEVEL = 0;
	public static final int ZERO_TOTES_UP = 1;
	public static final int ONE_TOTE_UP = 2;
	public static final int TWO_TOTES_UP = 3;
	public static final int THREE_TOTES_UP = 4;
	public static final int FOUR_TOTES_UP = 5;
	public static final int FIVE_TOTES_UP = 6;
	

	
//Pneumatics
	// Pneumatic ports of the solenoid
	public static final int[] BIN_SOLEN_PORTS = { 0, 1 };
	public static final int[] TOTE_SOLEN_PORTS = { 5, 4 };// Array with the extension ([0]) & retraction ([1]) 
	public static final int[] SLANT_SOLEN_PORTS = { 7, 6 };
	public static final int[] STOPPER_SOLEN_PORTS = { 2, 3 };
	
	
//Motor Controllers
	// Ports for the lift Talons
	public static final int BIN_MOTOR_PORT = 3;
	public static final int TOTE_MOTOR_PORT_1 = 1;
	public static final int TOTE_MOTOR_PORT_2 = 2;
	
	// Ports for the tote sucker Talons
	public static final int TOTE_SUCKER_LEFT_PORT = 4;
	public static final int TOTE_SUCKER_RIGHT_PORT = 5;
	
	//Ports for the drive CAN Talons
	public static final int TALON_R1_PORT = 4; //CHANNELS for talons
	public static final int TALON_R2_PORT = 5;
	public static final int TALON_L1_PORT = 1;
	public static final int TALON_L2_PORT = 2;
	public static final int TALON_M1_PORT = 3;
	
	
//Drive
	public static final double MAX_SPEED = 0.5;
	public static final int DIRECTION_MULTIPLIER = -1;
	public static final double MIN_INPUT = 0.2;

//Gamepad
	// Gamepad buttons
	public static final int CONTROLLER_BUTTON_1  = 1;
	public static final int CONTROLLER_BUTTON_2  = 2;
	public static final int CONTROLLER_BUTTON_3  = 3;
	public static final int CONTROLLER_BUTTON_4  = 4;
	public static final int CONTROLLER_BUTTON_5  = 5;
	public static final int CONTROLLER_BUTTON_6  = 6;
	public static final int CONTROLLER_BUTTON_7  = 7;
	public static final int CONTROLLER_BUTTON_8  = 8;
	public static final int CONTROLLER_BUTTON_9  = 9;
	public static final int CONTROLLER_BUTTON_10 = 10;

}
