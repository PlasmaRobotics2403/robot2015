package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class ToteClaw {
	
	PlasmaDoubleSolenoid solenoid;
	
	Talon motor1;
	Talon motor2;
	
	PlasmaAbsEncoder encoder;
	DigitalInput topLimSwitch;
	DigitalInput botLimSwitch;
	
	/**
	 * Constructor for Tote Claw
	 * @param solen - The open/close solenoid object
	 * @param enc - The encoder that measures distance
	 * @author Nic
	 */
	public ToteClaw(PlasmaDoubleSolenoid solen, PlasmaAbsEncoder enc, DigitalInput topLim, DigitalInput botLim){
		
		solenoid = solen;
		topLimSwitch = topLim;
		botLimSwitch = botLim;
		
		motor1 = new Talon(Constants.TOTE_MOTOR_PORT_1);
		motor2 = new Talon(Constants.TOTE_MOTOR_PORT_2);
		
		encoder = enc;
	}
	
	/**
	 * Raises the claw
	 */
	public boolean up(double speed){
		if(!topLimSwitch.get()){
			motor1.set(speed * Constants.CLAW_MOTOR_SPEED);
			motor2.set(speed * Constants.CLAW_MOTOR_SPEED);
			return false;
		}
		else{
			motor1.set(Constants.CLAW_MOTOR_STOPPED);
			motor2.set(Constants.CLAW_MOTOR_STOPPED);
			return true;
		}
	}
	
	/**
	 * Lowers the claw
	 */
	public boolean down(double speed){
		if(!botLimSwitch.get()){
			motor1.set(speed * -Constants.CLAW_MOTOR_SPEED);
			motor2.set(speed * -Constants.CLAW_MOTOR_SPEED);
			return false;
		}
		else{
			motor1.set(Constants.CLAW_MOTOR_STOPPED);
			motor2.set(Constants.CLAW_MOTOR_STOPPED);
			return true;
		}
	}
	
	/**
	 * Stops the claw
	 */
	public void stop(double encValue){
		double diff = encoder.getDist() - encValue;
		if(diff > .1){
			down(diff);
		}
		else if(diff < -.1){
			up(-diff);
		}
		else{
			motor1.set(Constants.CLAW_MOTOR_STOPPED);
			motor2.set(Constants.CLAW_MOTOR_STOPPED);
		}
	}
	
	/**
	 * Opens the claw
	 */
	public void open(){
		solenoid.extend();
	}
	
	/**
	 * Closes the claw
	 */
	public void close(){
		solenoid.retract();
	}
	
	/**
	 * Moves the tote claw to the specified height
	 * 
	 * @param reqHeight - The specified height that the claw needs to go to
	 * @return Returns true when the claw has made it to its height and is stopped, false otherwise
	 */
	public boolean goToPosition(double reqHeight){
		double thisHeight = encoder.getDist();
		if(reqHeight - thisHeight > Constants.CLAW_ERROR_ALLOWANCE){
			up(1);
			return false;
		}
		else if(reqHeight - thisHeight < -Constants.CLAW_ERROR_ALLOWANCE){
			down(1);
			return false;
		}
		else{
			stop(1);
			return true;
		}
	}
	
}
