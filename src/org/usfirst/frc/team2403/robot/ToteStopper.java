package org.usfirst.frc.team2403.robot;

public class ToteStopper {
	
	PlasmaDoubleSolenoid solenoid;
	
	public ToteStopper(PlasmaDoubleSolenoid solen){
		solenoid = solen;
	}
	
	public void openArms(){
		solenoid.retract();
	}
	
	public void closeArms(){
		solenoid.extend();
	}
	
}
