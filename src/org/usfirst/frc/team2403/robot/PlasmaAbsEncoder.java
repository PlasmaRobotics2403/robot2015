package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class PlasmaAbsEncoder extends AnalogInput {
	
	private int direction;
	
	private double angle;
	private double lastAngle;
	private double dist;
	private double cycleDist;
	private double directionMult;
	private final double conversion;
	
	// Should be .0349066 for 4 inch wheels
	// Should be .0523599 for 6 inch wheels
	
	
	/**
	 * Constructor for encoder class
	 * 
	 * @param port - Analog port encoder is plugged into
	 * @param conv - The distance traveled (in inches) per degree of rotation
	 * @param forward - If true, forward distance is clockwise. If false, forward distance is counter clockwise
	 * 
	 * @author Nic
	 */
	PlasmaAbsEncoder(int port, double conv, boolean forward){
		super(port);
		
		conversion = conv;
		
		lastAngle = this.getAngle();
		
		dist = 0;
		direction = Constants.LIFT_STATIONARY;
		
		if ( forward == true ) {
			directionMult = 1;
		}
		else {
			directionMult = -1;
		}
	}
	
	/**
	 * Should be called once per iteration. This updates the object with info from the encoder
	 * @author Nic
	 */
	public void update(){
		angle = this.getAngle();
		
		if ( lastAngle > 300 && angle < 60 ) {
			// Handle wrapping around past 360
			cycleDist = ( angle - ( lastAngle - 360 ) ) * conversion * directionMult;
		}
		else if ( lastAngle < 60 && angle > 300 ) {
			// Handle wrapping around past 0
			cycleDist = ( ( angle - 360 ) - lastAngle ) * conversion * directionMult;
		}
		else{
			cycleDist = ( angle - lastAngle ) * conversion * directionMult;
		}
		
		dist += cycleDist;
		if(cycleDist > .02){
			// Moving forwards
			direction = Constants.LIFT_FORWARDS;
		}
		else if(cycleDist < -.02){
			// Hnadle backwards
			direction = Constants.LIFT_BACKWARDS;
		}
		else{
			// Stationary
			direction = Constants.LIFT_STATIONARY;
		}
		
		lastAngle = angle;
		
	}
	
	/**
	 * Resets the distance to 0, will NOT reset angle
	 * @author Nic
	 */
	public void reset(){
		dist = 0;
		lastAngle = this.getAngle();
	}
	
	/**
	 * 
	 * @return Returns the distance traveled since last reset
	 * @author Nic
	 */
	public double getDist(){
		return dist;
	}
	
	public double getDistClean(){
		return (getDist()*1000)/1000.0;
	}
	
	/**
	 * 
	 * @return Returns the angle of the encoder in degrees
	 * @author Nic
	 */
	public double getAngle(){
		// Angle is based on 0-5V so to get 0-360 we scale up by 72
		return this.getVoltage() * 72;
	}
	
	/**
	 * 
	 * @return Returns <b>Constants.LIFT_FORWARD</b> if currently moving forward, 
	 * <b>Constants.LIFT_BACKWARDS</b> if moving backward, and 
	 * <b>Constants.LIFT_STATIONARY</b> if not moving
	 * @author Nic
	 */
	public int getDirection(){
		return direction;
	}
}
