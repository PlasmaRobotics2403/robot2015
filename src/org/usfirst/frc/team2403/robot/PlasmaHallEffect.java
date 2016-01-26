package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class PlasmaHallEffect extends DigitalInput {

	boolean lastState;
	boolean thisState;
	boolean hasTicked;
	
	public PlasmaHallEffect(int port){
		super(port);
		thisState = false;
		lastState = false;
		hasTicked = false;
	}
	
	public void update(){
		lastState = thisState;
		thisState = this.get();
		if(lastState == false && thisState == true){
			hasTicked = true;
		}
	}
	
	/**
	 *
	 * @return
	 */
	
	public boolean getTicked(){
		if(hasTicked){
			hasTicked = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean get(){
		return !super.get();
	}
	
}
