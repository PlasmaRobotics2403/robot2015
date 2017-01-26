package org.usfirst.frc.team2403.robot;

import com.ctre.CANTalon;

/**
 * Drive system class
 * 
 * @author Nic
 *
 */
/**
 * @author Master Hacker
 *
 */
/**
 * @author Master Hacker
 *
 */
public class Drive {

	CANTalon talon_r1;			// We have 2 CIMs on the right side
	CANTalon talon_r2;
	CANTalon talon_l1;			// and 2 CIMs on the left side
	CANTalon talon_l2;
	CANTalon talon_m1;			// but only 1 CIM for the middle drive
	
	SensorPack sensorPack;
	
	
	/**
	 * Constructor for the Drive system. 
	 * 
	 * @param <b>pack</b> - The SensorPack for the drive system to use
	 * 
	 * @author Cathy
	 */
	public Drive ( SensorPack pack ) {
		talon_r1 = new CANTalon( Constants.TALON_R1_PORT );
		talon_r2 = new CANTalon( Constants.TALON_R2_PORT );
		talon_l1 = new CANTalon( Constants.TALON_L1_PORT );
		talon_l2 = new CANTalon( Constants.TALON_L2_PORT );
		talon_m1 = new CANTalon( Constants.TALON_M1_PORT );
		
		sensorPack = pack;
	}
	
	
	/**
	 * Uses inputs to drive relative to robot
	 * 
	 * @param x - The sideways input
	 * @param y - The forward/backward input
	 * @param z - The rotational input
	 * 
	 * TODO: Define the range of values for x, y and z!
	 * 
	 * @author Cathy
	 */
	public void regDrive( double x, double y, double z ) {
		x *= Constants.MAX_SPEED;
		y *= -Constants.MAX_SPEED;
		z *= Constants.MAX_SPEED;
		
		// Check our sideways movement choices first
		if ( Math.abs( x ) > Constants.MIN_INPUT ) {
			talon_m1.set( x * Constants.DIRECTION_MULTIPLIER );
		}
		else{
			talon_m1.set( 0 );
		}
		
		// Now we check the ???		
		if ( y < -Constants.MIN_INPUT ) {
			// We are moving forward/backward already
			if ( z < -Constants.MIN_INPUT ){
				// Turn left
				
				// TODO: Do the multiplication ONCE at the top and use that value in the rest of the method!
				
				talon_l1.set( y * Constants.DIRECTION_MULTIPLIER );
				talon_l2.set( y * Constants.DIRECTION_MULTIPLIER );
				
				// TODO: Get the abs() values only once at the top and use that value in the rest of the method!
				
				talon_r1.set( y - Math.abs( z ) );
				talon_r2.set( y - Math.abs( z ) );
			
			} else if ( z > Constants.MIN_INPUT ) {
				// Turn right
				talon_r1.set( y );
				talon_r2.set( y );
				talon_l1.set( ( y - Math.abs( z ) ) * Constants.DIRECTION_MULTIPLIER );
				talon_l2.set( ( y - Math.abs( z ) ) * Constants.DIRECTION_MULTIPLIER );
			} else { 
				// No turn
				talon_l1.set( y * Constants.DIRECTION_MULTIPLIER );
				talon_l2.set( y * Constants.DIRECTION_MULTIPLIER );
				talon_r1.set( y );
				talon_r2.set( y );
			}
		} else if ( y > Constants.MIN_INPUT ) {
			// TODO: What case is this??
			if (z > Constants.MIN_INPUT ) {
				// Turn left
				talon_l1.set( y * Constants.DIRECTION_MULTIPLIER );
				talon_l2.set( y * Constants.DIRECTION_MULTIPLIER );
				talon_r1.set( y - Math.abs( z ) );
				talon_r2.set( y - Math.abs( z ) );
			
			} else if ( z < -Constants.MIN_INPUT ) {
				// Turn right
				talon_r1.set( y );
				talon_r2.set( y );
				talon_l1.set( ( y - Math.abs( z ) ) * Constants.DIRECTION_MULTIPLIER );
				talon_l2.set( ( y - Math.abs( z ) ) * Constants.DIRECTION_MULTIPLIER );
			} else { 
				// No turn
				talon_l1.set( y * Constants.DIRECTION_MULTIPLIER );
				talon_l2.set( y * Constants.DIRECTION_MULTIPLIER );
				talon_r1.set( y );
				talon_r2.set( y );
			}
			
		} else { 
			// Idle turn
			talon_l1.set( z );
			talon_l2.set( z );
			talon_r1.set( z );
			talon_r2.set( z );
		}
	}
	
	/**
	 * Simple tank drive - Used for basic movement in autonomous
	 * 
	 * @param left - Speed for left wheels
	 * @param right - Speed for right wheels
	 * @param middle - TBD
	 * 
	 * TODO: Update the Javadoc above
	 * 
	 * @author Nic
	 */
	public void manualTank( double left, double right, double middle ) {
		left *= Constants.MAX_SPEED;
		right *= Constants.MAX_SPEED;
		middle *= Constants.MAX_SPEED;
		
		talon_r1.set( right * Constants.DIRECTION_MULTIPLIER );
		talon_r2.set( right * Constants.DIRECTION_MULTIPLIER );
		talon_l1.set( left );
		talon_l2.set( left );
		talon_m1.set( middle );
	}
	
	/**
	 * TODO: Fill out this Javadoc with a useful member and input description (and outputs)
	 *  
	 * @param speed
	 * @param distance
	 * @return 
	 */
	public boolean distDrive( double speed, double distance ) {
		
		if ( Math.abs(sensorPack.enc_l.getDist()) < Math.abs(distance) && Math.abs(sensorPack.enc_l.getDist()) < Math.abs(distance) ) {
			manualTank( speed, speed, 0 );
			
			return false;
		}
		else {
			manualTank( 0, 0, 0 );
			
			return true;
		}		
	}
	
	/**
	 * TODO: Fill out this Javadoc with a useful member and input description (and outputs)
	 *  
	 * @param speed
	 * @param ang
	 * @return
	 */
	public boolean gyroTurn( double speed, int ang ) {
		int errorAllowance = 2;
		int angleDiff = ang - (int) sensorPack.navX.getYaw();
		
		if ( angleDiff > errorAllowance ) {
			manualTank( speed, -speed, 0 );
			
			return false;
		} else if ( angleDiff < -errorAllowance ) {
			manualTank( -speed, speed, 0 );
			
			return false;
		} else {
			manualTank( 0, 0, 0 );
			
			return true;
		}
	}
	
	
	/**
	 * Uses inputs to drive relative to the field
	 * 
	 * @param x - The sideways input
	 * @param y - The forward/backward input
	 * @param z - The rotational input
	 * 
	 * @author Nic
	 * 
	 * @deprecated - Torn apart for last second bug fixing. Will be repaired sometime. (Not used anyway)
	 */
	public void fieldOrientedDrive(double x, double y, double z){
		
		double contAng;
		double ang;
		double forward = 0;
		double sideways = 0;
		double magnitude;
		
		z *= Constants.MAX_SPEED;
		
		if (Math.abs(y) > Constants.MIN_INPUT || Math.abs(x) > Constants.MIN_INPUT){
			//radians
			contAng = Math.atan(x/y);
			
			if (y < 0){
				contAng += Math.PI;
			}
			
			ang = contAng - Math.toRadians(sensorPack.navX.getYaw());
			
			while(ang > Math.PI){
				ang -= Math.PI*2;
			}
			while(ang < -Math.PI){
				ang += Math.PI*2;
			}
			
			magnitude = Math.sqrt(y*y + x*x);
			forward = magnitude * Math.cos(ang);
			sideways = magnitude * Math.sin(ang);
			
			talon_l1.set(forward + (z));
			talon_l2.set(forward + (z));
			talon_r1.set(forward * Constants.DIRECTION_MULTIPLIER + (z));
			talon_r2.set(forward * Constants.DIRECTION_MULTIPLIER + (z));
			talon_m1.set(sideways * Constants.DIRECTION_MULTIPLIER );
		}
		else{
			talon_r1.set(z);
			talon_r2.set(z);
			talon_l1.set(z);
			talon_l2.set(z);
			talon_m1.set(0);
		}
	}
	
}
