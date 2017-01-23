package org.usfirst.frc.team2403.robot;

//import java.util.ArrayList;

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public final int GAMEPAD_PORT = 0;
	
	PlasmaGamepad gamepad;
	Drive drive;
	SensorPack sensorPack;
	PneumaticsPack pneumaticsPack;
	AutoMode autoMode;
	Lift lift;
	ToteStopper toteStopper;
	
	//Teleop variables
	
	boolean isFieldOriented;
	boolean isDropPart2;
	int[] requestedActions = {-1,-1,-1,-1,-1,-1,-1};
	/**
	 * requestedActions array location explanation:
	 * [0] = switchMode()
	 * [1] = pickUp()
	 * [2] = undoPickUp()
	 * [3] = dropPart1()
	 * [4] = dropPart2()
	 * [5] = toteClawUp()
	 * [6] = toteClawDown()
	 */
	
    boolean isPushed = true;
    double encValue;
    
	//Auto variables
	
	int state;
	
	/**
	 * Robot-wide initialization code goes here.  You should override this method for default Robot-wide initialization 
	 * which will be called when the robot is first powered on.  It will be called exactly 1 time.
	 */
    public void robotInit() {
    	gamepad = new PlasmaGamepad( GAMEPAD_PORT );
    	sensorPack = new SensorPack();
    	drive = new Drive( sensorPack );
    	pneumaticsPack = new PneumaticsPack();
    	lift = new Lift(sensorPack, pneumaticsPack);
    	toteStopper = new ToteStopper(pneumaticsPack.stopperSolen);
    	autoMode = new AutoMode( drive, sensorPack, lift);
    }
    
    /**
     * Initialization code for autonomous mode goes here. This code is for handling initialization 
     * which happens each time the robot enters autonomous mode.
     */
    public void autonomousInit(){
    	state = 0;
    	autoMode.step = 0;
    	sensorPack.resetEncoders();
    	sensorPack.navX.zeroYaw();
    }
    
    /**
     * Periodic code for autonomous mode goes here.  This code will be called periodically at 
     * a regular rate while the robot is in autonomous mode.
     */
    public void autonomousPeriodic() {
    	
    	sensorPack.update();
    	//sensorPack.printData();
    	
    	switch ( state ) {
    	
    		case 0:
    			autoMode.mode0();
    			break;
	    	case 1:
	    		autoMode.mode1();
	    		break;
	    		
	    	case 2:
	    		autoMode.mode2();
	    		break;
	    		
	    	case 3: 
	    		autoMode.mode3();
	    		break;
    	}
    }

    /**
     * Initialization code for teleop mode goes here.  This code is for  handing initialization 
     * which happens each time the robot enters teleop mode.
     */
    public void teleopInit(){
    	isFieldOriented = false;
    	sensorPack.resetEncoders();
    	sensorPack.navX.zeroYaw();
    	isDropPart2 = false;
    	lift.liftMode = Constants.PICKUP_MODE;
    	lift.pickupState = 0;
    	lift.pickupSubstate = 0;
    	encValue = sensorPack.tote_enc.getDist();
    	isPushed = false;
    }
    
    /**
     * Periodic code for teleop mode goes here.  This code which is called periodically at a 
     * regular rate while the robot is in teleop mode.
     * 
     * In Telop mode we are going to use the following controls:
     * 
     * A = Reset the NavX board (primarily the compass)
     * Y = Reset the encoders
     * Left Bumper = DISABLE field oriented drive
     * Right Bumper = ENABLE field oriented drive
     */
    public void teleopPeriodic() {
    	
    	drive.regDrive( gamepad.getLeftJoystickXAxis(), -gamepad.getLeftJoystickYAxis(), gamepad.getRightJoystickXAxis() );

    	sensorPack.update();
    	sensorPack.printData(lift.liftMode, lift.toteClawPosition, requestedActionLoc(), lift.pickupSubstate);
    	
    	basicLift();
    	controlStopper();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
    
    public void controlStopper(){
    	if(gamepad.getBButton().isPressed()){
    		toteStopper.closeArms();
    	}
    	else if(gamepad.getXButton().isPressed()){
    		toteStopper.openArms();
    	}
    }
    

    public void parkLift(){
    	if(isPushed == true){
			encValue = sensorPack.tote_enc.getDist();
		}
		isPushed = false;     
		lift.toteClaw.stop(encValue);
		
		if(gamepad.getAButton().isPressed()){
			lift.slantBackward();
		}
		else if(gamepad.getYButton().isPressed()){
			lift.slantForward();
		}
    }
    
    public void basicLift(){
    	if(gamepad.getRightTriggerAxis() > .1){
    		isPushed = true;
    		lift.toteClaw.up(gamepad.getRightTriggerAxis());
    	}
    	else if(gamepad.getLeftTriggerAxis() > .1){
    		isPushed = true;
    		lift.toteClaw.down(gamepad.getLeftTriggerAxis());
    	}
    	else if(gamepad.getDPadPressed(0)){
    		isPushed = true;
    		lift.toteClaw.up(.5);
    	}
    	else if(gamepad.getDPadPressed(180)){
    		isPushed = true;
    		lift.toteClaw.down(.5);
    	}
    	else{
    		if(isPushed == true){
    			encValue = sensorPack.tote_enc.getDist();
    		}
    		isPushed = false;     
    		lift.toteClaw.stop(encValue);
    		
    	}
    	
    	if(gamepad.getRightBumper().isPressed()){
    		lift.toteClaw.close();
    	}
    	else if(gamepad.getLeftBumper().isPressed()){
    		lift.toteClaw.open();
    	}
    	
    	if(gamepad.getAButton().isPressed()){
			lift.slantBackward();
		}
		else if(gamepad.getYButton().isPressed()){
			lift.slantForward();
		}
    }
    
    public void controlLift(){
    	if(requestedActionLoc() == -1 && isDropPart2){
    		if(gamepad.getRightBumper().isPressed()){
    			requestedActions[4] = 1;
    			isDropPart2 = false;
    		}
    	}
    	else if(requestedActionLoc() == -1){
    		if(gamepad.getYButton().isPressed() && lift.liftMode != Constants.PICKUP_MODE){
    			requestedActions[0] = Constants.PICKUP_MODE;
    		}
    		else if(gamepad.getXButton().isPressed()  && lift.liftMode != Constants.REGULAR_DROP_MODE){
    			requestedActions[0] = Constants.REGULAR_DROP_MODE;
    		}
    		else if(gamepad.getBButton().isPressed()  && lift.liftMode != Constants.STEP_DROP_MODE){
    			requestedActions[0] = Constants.STEP_DROP_MODE;
    		}
    		
    		else if(lift.liftMode == Constants.PICKUP_MODE){
    			if(gamepad.getRightBumper().isPressed()){
    				requestedActions[1] = 1; //Pick up
    			}
    			else if(gamepad.getLeftBumper().isPressed()){
    				requestedActions[2] = 1; //Undo
    			}
    		}
    		
    		else if(lift.liftMode == Constants.REGULAR_DROP_MODE || lift.liftMode == Constants.STEP_DROP_MODE){
    			if(gamepad.getRightBumper().isPressed()){
    				requestedActions[3] = 1; //Drop part 1
    				isDropPart2 = true;
    			}
    			else if(gamepad.getDPadPressed(0)){
    				requestedActions[5] = 1; //Tote claw up
    			}
    			else if(gamepad.getDPadPressed(180)){
    				requestedActions[6] = 1; //Tote claw down
    			}
    		}

    	}
    	
    	else{
    		switch(requestedActionLoc()){
    		case 0:
    			if(lift.switchMode(requestedActions[0])){
    				requestedActions[0] = -1;
    			}
    			break;
    		case 1:
    			if(lift.pickUp()){
    				requestedActions[1] = -1;
    			}
    			break;
    		case 2:
    			if(lift.undoPickUp()){
    				requestedActions[2] = -1;
    			}
    			break;
    		case 3:
    			if(lift.dropPart1()){
    				requestedActions[3] = -1;
    			}
    			break;
    		case 4:
    			if(lift.dropPart2()){
    				requestedActions[4] = -1;
    			}
    			break;
    		case 5:
    			if(lift.toteClawUp()){
    				requestedActions[5] = -1;
    			}
    			break;
    		case 6:
    			if(lift.toteClawDown()){
    				requestedActions[6] = -1;
    			}
    			break;
    		}
    	}
    }
    
    public int requestedActionLoc(){
    	for(int i = 0; i < requestedActions.length; i++){
    		if(requestedActions[i] != -1){
    			return i;
    		}
    	}
    	return -1;
    	
    }
}
