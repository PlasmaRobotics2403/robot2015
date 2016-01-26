package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Autonomouse mode code that handles all of our different autonomous modes and actions.
 * 
 * @author Nic
 * Hello from github!
 * TEST
 */
public class AutoMode {
	
	SensorPack sensorPack;
	Drive drive;
	Lift lift;
	int step;
	
	/**
	 * Automatic mode class constructor
	 * 
	 * @param <b>dr</b> - The Drive system to use
	 * @param <b>pack</b> - The SensorPack to use
	 * @param <b>li</b> - The Life to use
	 */
	AutoMode( Drive dr, SensorPack pack, Lift li ) {
		drive = dr;
		sensorPack = pack;
		lift = li;
		step = 0;
		}
	
	public void mode0(){
		lift.slantForward();
	}
	
	/**
	 * Autonomous mode 1: TBD
	 */
	public void mode1() {
		switch ( step ) {
		case 0:
			if(unfold()){
				step++;
			}
			break;
			
		case 1:
			drive.distDrive( .5, 62 );
			break;
		}
	}
	
	/**
	 * Autonomous mode 2: Takes bin to auto zone
	 */
	public void mode2() {
		switch ( step ) {
			case 0:
				if(unfold()){
					step++;
				}
				break;
				
			case 1:
				// Close the claw then
				lift.toteClaw.close();
				step++;
				break;
			
			case 2:
				// Wait for .5 seconds (so it can actually close) then
				if ( waitTime( 500 ) ){
					step++;
				}
				
				break;
				
			case 3:
				// The claw is at the right height (maybe) so just keep moving...
				DriverStation.reportError(sensorPack.enc_l.getDist() + "\n", false);
				drive.distDrive( -.5, -90 );
				break;
				// TODO: Do we ever stop moving??  distDrive() will handle stopping...
		}
	}
	
	/**
	 * Autonomous mode 3: Takes bin to auto zone without dropping arms
	 */
	public void mode3() {
		switch ( step ) {
		case 0:
			lift.slantForward();
			lift.toteClaw.open();
			step++;
			break;
			
		case 1:
			if ( waitTime( 4000 ) ){
				step++;
			}
			break;
		case 2:
			// Close the claw then
			lift.toteClaw.close();
			step++;
			break;
		
		case 3:
			// Wait for .5 seconds (so it can actually close) then
			if ( waitTime( 500 ) ){
				step++;
			}
			
			break;
			
		case 4:
			// The claw is at the right height (maybe) so just keep moving...
			DriverStation.reportError(sensorPack.enc_l.getDist() + "\n", false);
			drive.distDrive( -.5, -90 );
			break;
			// TODO: Do we ever stop moving??  distDrive() will handle stopping...
	}
	}
	
	public boolean unfold(){
		lift.slantForward();
		lift.toteClaw.open();
		return lift.toteClaw.down(.5);
	}
	
	int waitStep = 0;			// Private member used to build our wait state machine
	double tempTime;			// Private member used to track the timestamp of our first wait state call
	
	/**
	 * Returns true when specified number of milliseconds have passed since the first call!
	 * 
	 * @param milis  - Number of milliseconds to wait
	 * 
	 * @return Returns true when specified number of milliseconds have passed
	 */
	public boolean waitTime( int millis ) {
		switch ( waitStep ) {
			case 0:
				tempTime = System.currentTimeMillis();
				waitStep++;
				return false;
				
			case 1:
				
				if ( System.currentTimeMillis() - tempTime >= millis ) {
					waitStep = 0;
					return true;
				}
				return false;
			
			default:
				waitStep = 0;
				return false;
		}
	}
}
