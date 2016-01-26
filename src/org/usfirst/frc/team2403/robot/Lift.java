package org.usfirst.frc.team2403.robot;

public class Lift {
	
	BinClaw binClaw;
	ToteClaw toteClaw;
	
	SensorPack sensorPack;
	PneumaticsPack pneumaticsPack;

	PlasmaDoubleSolenoid  slant;
	
	int pickupState;
	int pickupSubstate;
	int toteClawPosition;
	int liftMode;
	double timeHolder;
	double addedHeight;
	
	/**
	 * Constructor for Lift object
	 * @param pack - The sensor pack
	 * @param pneuPack - The pneumatics pack
	 */
	public Lift(SensorPack pack, PneumaticsPack pneuPack){
		
		sensorPack = pack;
		pneumaticsPack = pneuPack;
		
		
		binClaw = new BinClaw(pneumaticsPack.binSolen, sensorPack.binTopLimSwitch, sensorPack.binBotLimSwitch);
		toteClaw = new ToteClaw(pneumaticsPack.toteSolen, sensorPack.tote_enc, sensorPack.binTopLimSwitch, sensorPack.binBotLimSwitch);

		slant = pneumaticsPack.slantSolen;
			
		pickupState = 0;
		pickupSubstate = 0;
		toteClawPosition = Constants.GROUND_LEVEL;
		addedHeight = Constants.NORMAL_HEIGHT;
		liftMode = Constants.PICKUP_MODE;
		
	}
	
	/**
	 * Slants the arms forward
	 */
	public void slantForward(){
		slant.extend();
	}
	
	/**
	 * Slants the arms backward
	 */
	public void slantBackward(){
		slant.retract();
	}
	
	/**
	 * Switches mode to one requested
	 * @param mode - The mode requested to switch to
	 * @return Returns true when mode switch has been completed
	 */
	public boolean switchMode(int mode){
		liftMode = mode;
		if(mode > 2 || mode < 0){
			liftMode = Constants.PICKUP_MODE;
		}
		switch(liftMode){
		
		case Constants.PICKUP_MODE:		
			if(pickupState <= 1){
				toteClawPosition = Constants.GROUND_LEVEL;
				pickupState = 1;
				toteClaw.open();
				binClaw.close();
				addedHeight = Constants.NORMAL_HEIGHT;
				return binClaw.goAllUp() && toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[toteClawPosition]);
			}
			else{
				toteClawPosition = Constants.ONE_TOTE_UP;
				addedHeight = Constants.NORMAL_HEIGHT;
				return binClaw.goAllUp() && toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[toteClawPosition]);
			}
		
		case Constants.REGULAR_DROP_MODE:
			toteClawPosition = Constants.ZERO_TOTES_UP;
			addedHeight = Constants.NORMAL_HEIGHT;
			return binClaw.goAllUp() && toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[toteClawPosition]);
		
		
		case Constants.STEP_DROP_MODE:
			toteClawPosition = Constants.ZERO_TOTES_UP;
			addedHeight = Constants.STEP_HEIGHT;
			return binClaw.goAllUp() && toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[toteClawPosition]);
		
		default:
			liftMode = Constants.PICKUP_MODE;
			return false;
		}
	}
	
	/**
	 * Triggers action to pick up bin or tote, chooses automatically
	 * @return Returns true when pickup is complete, false otherwise
	 */
	public boolean pickUp(){
		switch(pickupState){
		case 0:
			return pickUp0();
		case 1:
			return pickUp1();
		default:
			return pickUpN();
		}
	}
	
	/**
	 * Code to pick up bin
	 * @return Returns true when pickup is complete, false otherwise
	 */
	private boolean pickUp0(){
		switch(pickupSubstate){
		case 0:
			binClaw.close();
			timeHolder = System.currentTimeMillis();
			pickupSubstate++;
			return false;
		case 1:
			if(System.currentTimeMillis() - timeHolder >= 500){
				pickupSubstate ++;
			}
			return false;
		case 2:
			if(binClaw.goAllUp()){
				pickupSubstate = 0;
				pickupState ++;
				return true;
			}
			return false;
		default:
			pickupSubstate = 0;
			return false;
		}
	}
	
	/**
	 * Code to pick up first tote
	 * @return Returns true when pickup is complete, false otherwise
	 */
	private boolean pickUp1(){
		switch(pickupSubstate){
		case 0:
			toteClaw.close();
			timeHolder = System.currentTimeMillis();
			pickupSubstate++;
			return false;
		case 1:
			if(System.currentTimeMillis() - timeHolder >= 500){
				pickupSubstate ++;
			}
			return false;
		case 2:
			if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[Constants.ONE_TOTE_UP])){
				toteClawPosition = Constants.ONE_TOTE_UP;
				pickupSubstate = 0;
				pickupState ++;
				return true;
			}
			return false;
		default:
			pickupSubstate = 0;
			return false;
		}
	}
	
	/**
	 * Code to pick up all subsequent totes
	 * @return Returns true when pickup is complete, false otherwise
	 */
	private boolean pickUpN(){
		if(pickupState < Constants.MAX_STACK_STATES){
			switch(pickupSubstate){
			case 0:
				if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[Constants.GROUND_LEVEL])){
					toteClawPosition = Constants.GROUND_LEVEL;
					pickupSubstate ++;
				}
				return false;
			
			case 1:
				if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[Constants.ONE_TOTE_UP])){
					toteClawPosition = Constants.ONE_TOTE_UP;
					pickupSubstate = 0;
					pickupState ++;
					return true;
				}
				return false;
				
			default:
				pickupSubstate = 0;
				return false;
			}
		}
		else{
			return true;
		}
	}
	
	/**
	 * Triggers action to undo last pickup move
	 * @return Returns true when undo is complete, false otherwise
	 */
	public boolean undoPickUp(){
		switch(pickupState){
		case 0:
			return true;
		case 1:
			return undo1();
		case 2:
			return undo2();
		default:
			return undoN();
		}
	}
	
	/**
	 * Code to undo bin pickup
	 * @return Returns true when undo is complete, false otherwise
	 */
	private boolean undo1(){
		switch(pickupSubstate){
		case 0:
			binClaw.open();
			timeHolder = System.currentTimeMillis();
			pickupSubstate++;
			return false;
		case 1:
			if(System.currentTimeMillis() - timeHolder >= 500){
				pickupSubstate ++;
			}
			return false;
		case 2:
			if(binClaw.goAllDown()){
				pickupSubstate = 0;
				pickupState --;
				return true;
			}
			return false;
		default:
			pickupSubstate = 0;
			return false;
		}
	}
	
	/**
	 * Code to undo first tote pickup
	 * @return Returns true when undo is complete, false otherwise
	 */
	private boolean undo2(){
		switch(pickupSubstate){
		case 0:
			toteClaw.open();
			timeHolder = System.currentTimeMillis();
			pickupSubstate++;
			return false;
		case 1:
			if(System.currentTimeMillis() - timeHolder >= 500){
				pickupSubstate ++;
			}
			return false;
		case 2:
			if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[Constants.GROUND_LEVEL])){
				toteClawPosition = Constants.GROUND_LEVEL;
				pickupSubstate = 0;
				pickupState --;
				return true;
			}
			return false;
		default:
			pickupSubstate = 0;
			return false;
		}
	}
	
	/**
	 * Code to undo all subsequent pickups
	 * @return Returns true when undo is complete, false otherwise
	 */
	private boolean undoN(){
		pickupState --;
		return true;
	}
	
	/**
	 * Completes the first half of a drop, which is lowering the two arms and opening the claws
	 * @return Returns true when actions are complete
	 */
	public boolean dropPart1(){
		switch(pickupSubstate){
		case 0:
			if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[toteClawPosition-1])){
				toteClawPosition --;
				pickupSubstate++;
			}
			return false;
		case 1:
			if(binClaw.goAllDown()){
				pickupSubstate ++;
			}
			return false;
		case 2:
			toteClaw.open();
			binClaw.open();
			pickupSubstate = 0;
			return true;
		default:
			pickupSubstate = 0;
			return false;
		}
	}
	
	/**
	 * Completes the second half of the drop, which is resetting the two arms
	 * @return Returns true when action is complete, false otherwise
	 */
	public boolean dropPart2(){
		switch(pickupSubstate){
		case 0:
			if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[Constants.GROUND_LEVEL])){
				toteClawPosition = Constants.GROUND_LEVEL;
				pickupSubstate++;
			}
			return false;
		case 1:
			if(binClaw.goAllDown()){
				pickupSubstate = 0;
				pickupState = 0;
				liftMode = Constants.PICKUP_MODE;
				return true;
			}
			return false;
		default:
			pickupSubstate = 0;
			return false;
		}
	}
	
	/**
	 * Raises the tote claw up by 1 tote size
	 * @return Returns true when action is complete
	 */
	public boolean toteClawUp(){
		if(toteClawPosition < Constants.FIVE_TOTES_UP){
			if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[toteClawPosition + 1])){
				toteClawPosition ++;
				return true;
			}
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Lowers the tote claw down by 1 tote size
	 * @return Returns true when action is complete
	 */
	public boolean toteClawDown(){
		if(toteClawPosition > Constants.ZERO_TOTES_UP){
			if(toteClaw.goToPosition(addedHeight + Constants.TOTE_REGULAR_LOCATIONS[toteClawPosition - 1])){
				toteClawPosition --;
				return true;
			}
			return false;
		}
		else{
			return true;
		}
	}
	
	
	
	
}


