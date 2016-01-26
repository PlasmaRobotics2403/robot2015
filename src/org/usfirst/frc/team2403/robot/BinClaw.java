package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

/**
 * Class to handle the recycle bin claw.
 * 
 * MAY be obsolete if we do not actually use a 2nd claw
 * 
 * @author Nic
 */
public class BinClaw {

	PlasmaDoubleSolenoid solenoid;

	Talon motor1;
	
	DigitalInput topLimitSwitch;
	DigitalInput botLimitSwitch;
	
	/**
	 * Constructor for the bin claw
	 * 
	 * @param solen - The open/close solenoid object
	 * @param topSwitch - The limit switch that is mounted to the top of the claw
	 * @param botSwitch - The limit switch that is mounted to the bottom of the claw
	 */
	public BinClaw( PlasmaDoubleSolenoid solen, DigitalInput topSwitch, DigitalInput botSwitch ) {
		
		solenoid = solen;
		
		motor1 = new Talon( Constants.BIN_MOTOR_PORT );
		
		topLimitSwitch = topSwitch;
		botLimitSwitch = botSwitch;
		}
	
	/**
	 * Raise the claw.  The claw will keep going until told otherwise
	 */
	public void up() {
		motor1.set( Constants.CLAW_MOTOR_SPEED );
		}
	
	/**
	 * Lowers the claw.  The claw will keep going until told otherwise
	 */
	public void down() {
		motor1.set( -Constants.CLAW_MOTOR_SPEED );
		}
	
	/**
	 * Stop the claw from moving any more
	 */
	public void stop() {
		motor1.set( Constants.CLAW_MOTOR_STOPPED );
		}
	
	/**
	 * Open the claw
	 */
	public void open() {
		solenoid.extend();
		}
	
	/**
	 * Close the claw
	 */
	public void close() {
		solenoid.retract();
		}
	
	/**
	 * Move the claw all the way to the top until it triggers its top limit switch
	 * 
	 * @return Returns true when it has stopped
	 */
	public boolean goAllUp() {
		if ( topLimitSwitch.get() ) {
			stop();
		}
		else {
			up();
		}
		
		return topLimitSwitch.get();
	}
	
	/**
	 * Move the claw down until it triggers its bottom limit switch
	 * @return Returns true when it has stopped
	 */
	public boolean goAllDown() {
		if ( botLimitSwitch.get() ) {
			stop();
		}
		else{
			down();
		}
		
		return botLimitSwitch.get();
	}
	
}
