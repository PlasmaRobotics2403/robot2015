package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.Compressor;

public class PneumaticsPack {
	
	Compressor compressor;
	PlasmaDoubleSolenoid binSolen;
	PlasmaDoubleSolenoid toteSolen;
	PlasmaDoubleSolenoid slantSolen;
	PlasmaDoubleSolenoid stopperSolen;

		
	/**
	 * When declared, will initialize all solenoids and compressor
	 * @author Nic
	 */
	PneumaticsPack(){
		compressor = new Compressor();
		compressor.start();
		
		binSolen = new PlasmaDoubleSolenoid(Constants.BIN_SOLEN_PORTS);
		toteSolen = new PlasmaDoubleSolenoid(Constants.TOTE_SOLEN_PORTS);
		slantSolen = new PlasmaDoubleSolenoid(Constants.SLANT_SOLEN_PORTS);
		stopperSolen = new PlasmaDoubleSolenoid(Constants.STOPPER_SOLEN_PORTS);
	}
	
}
