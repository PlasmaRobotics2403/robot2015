package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PlasmaDoubleSolenoid extends DoubleSolenoid {
	
	/**
	 * Constructor for new solenoid
	 * 
	 * @param port1 - Extension port to solenoid
	 * @param port2 - Retraction port to solenoid
	 * 
	 * @author Nic
	 */
	PlasmaDoubleSolenoid( int ports[] ){
		super( ports[0], ports[1] );
	}
	
	/**
	 * Extends the solenoid when called
	 * @author Nic
	 */
	public void extend(){
		this.set( DoubleSolenoid.Value.kForward );
	}
	
	/**
	 * Retracts the solenoid when called
	 * @author Nic
	 */
	public void retract(){
		this.set( DoubleSolenoid.Value.kReverse );
	}
	
}
