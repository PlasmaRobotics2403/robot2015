package org.usfirst.frc.team2403.robot;

import com.kauailabs.navx_mxp.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//git work now plz

public class SensorPack {
	
	AHRS navX;
	SerialPort navPort;

	PlasmaAbsEncoder enc_l;		// Encoder for the left side drives
	PlasmaAbsEncoder enc_r;		// Encoder for the right side drives
	PlasmaAbsEncoder enc_m;		// Encoder for the middle (aka "H") drive
	PlasmaAbsEncoder tote_enc;
	
	DigitalInput binTopLimSwitch;
	DigitalInput binBotLimSwitch;
	
	
	/**
	 * When called, will initialize all sensors used on robot
	 * @author Nic
	 */
	public SensorPack(){
		navPort = new SerialPort( Constants.NAVX_PORT_BAUD_RATE, Constants.NAVX_PORT_PORT );
		navX = new AHRS( navPort, Constants.NAVX_REF_RATE );
		
		enc_l = new PlasmaAbsEncoder(Constants.ENC_L_PORT, Constants.DRIVE_ENCODER_CONV, Constants.ENC_L_DIR);
		enc_r = new PlasmaAbsEncoder(Constants.ENC_R_PORT, Constants.DRIVE_ENCODER_CONV, Constants.ENC_R_DIR);
		enc_m = new PlasmaAbsEncoder(Constants.ENC_M_PORT, Constants.DRIVE_ENCODER_CONV, Constants.ENC_M_DIR);
		tote_enc = new PlasmaAbsEncoder(Constants.TOTE_ENC_PORT, Constants.LIFT_ENCODER_CONV, Constants.TOTE_ENC_DIR);
		
		binTopLimSwitch = new DigitalInput(Constants.BIN_TOP_LIM_SWITCH_PORT);
		binBotLimSwitch = new DigitalInput(Constants.BIN_BOT_LIM_SWITCH_PORT);
		
		navX.resetDisplacement();
	}
	
	/**
	 * Prints to the driver station the inches traveled by each encoder and the gyro angle
	 * @author Nic
	 */
	public void printData(int mode, int liftHeight, int reqAction, int substate){
		SmartDashboard.putNumber("Gyro", navX.getYaw());
		SmartDashboard.putNumber("Left Encoder", ((int)(enc_l.getDistClean())));
		SmartDashboard.putNumber("Right Encoder", ((int)(enc_r.getDistClean())));
		SmartDashboard.putNumber("Middle Encoder", ((int)(enc_m.getDistClean())));
		
		//DriverStation.reportError("Mode: " + mode + "   Lift Height: " + liftHeight + "   Requested Action: " + reqAction + "   Substate: " + substate + "\n", false);
	}
	
	/**
	 * Resets all 3 drive encoders
	 * @author Nic
	 */
	public void resetEncoders(){
		enc_l.reset();
		enc_r.reset();
		enc_m.reset();
	}
	
	
	/**
	 * Updates all 3 drive encoder objects with info from the encoders
	 * Updates the two Hall Effect sensors
	 * @author Nic
	 */
	public void update(){
		enc_l.update();
		enc_r.update();
		enc_m.update();
		tote_enc.update();
	}
	
	
}
