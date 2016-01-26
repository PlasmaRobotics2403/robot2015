package org.usfirst.frc.team2403.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * A class to handle reading input from an XBox360 controller. It extends the Joystick class
 * 
 * @author Allek
 */
public class PlasmaGamepad extends Joystick {
    
     private final ToggleableButton aButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_1 );
     private final ToggleableButton bButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_2 );
     private final ToggleableButton xButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_3 );
     private final ToggleableButton yButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_4 );
     private final ToggleableButton leftBumper = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_5 );
     private final ToggleableButton rightBumper = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_6 );
     private final ToggleableButton backButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_7 );
     private final ToggleableButton startButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_8 );
     private final ToggleableButton leftJoystickButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_9 );
     private final ToggleableButton rightJoystickButton = new ToggleableButton( this, Constants.CONTROLLER_BUTTON_10 );
     
     int lastDir = -1; //Used for getDPadPressed()

     /**
      * 
      * Constructor for the Plasma Gamepad class.
      * 
      * @param port - What port the controller is connected to.
      */
    public PlasmaGamepad( int port ) {
        super( port );
    }
    

    /**
     * 
     * @param ang - Angle to check for - Can be any multiple of 45 up to 360
     * @return Returns true first time that angle is pressed, will return false until released and pressed again
     * @author Nic
     */
    public boolean getDPadPressed(int ang){
    	return getPOV() == ang;
    }
    
    /**
     * Return the A button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getAButton() {
        return this.aButton;
    }
    
    /**
     * Return the B button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getBButton() {
        return this.bButton;
    }
    
    /**
     * Return the X button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getXButton() {
        return this.xButton;
    }

    /**
     * Return the Y button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getYButton() {
        return this.yButton;
    }
    
    /**
     * Return the left shoulder button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getLeftBumper() {
        return this.leftBumper;
    }
    
    /**
     * Return the right shoulder button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getRightBumper() {
        return this.rightBumper;
    }
    
    /**
     * Return the Back button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getBackButton() {
        return this.backButton;
    }
    
    /**
     * Return the Start button so its status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getStartButton() {
        return this.startButton;
    }
    
    /**
     * Return the left joystick as a button so its pushed status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getLeftJoystickButton() {
        return this.leftJoystickButton;
    }
    
    /**
     * Return the the right joystick as a button so its pushed status can be checked.
     * 
     * @return A ToggleableButton object
     */
    public ToggleableButton getRightJoystickButton() {
        return this.rightJoystickButton;
    }
    
    /**
     * Get left joystick x-axis (left-right).
     * Full left = -1, full right = +1.
     * 
     * @return joystick axis value
     **/
    public double getLeftJoystickXAxis() {
        return super.getRawAxis( 0 );
    }

    /**
     * Get left joystick y-axis (up-down).
     * Full up = -1, full down = +1.
     * 
     * @return joystick axis value
     **/
    public double getLeftJoystickYAxis() {
        return super.getRawAxis( 1 );
    }
    
    /**
     * Get right joystick x-axis (left-right).
     * Full left = -1, full right = +1.
     * 
     * @return joystick axis value
     **/
    public double getRightJoystickXAxis() {
        return super.getRawAxis( 4 );
    }
    
    /**
     * Get right joystick y-axis (up-down).
     * Full up = -1, full down = +1.
     * 
     * @return Joystick axis value
     **/
    public double getRightJoystickYAxis() {
        return super.getRawAxis( 5 );
    }
    
    /**
     * Get right trigger axis.
     * 
     * @return Trigger axis value
     **/
    public double getRightTriggerAxis() {
        return super.getRawAxis( 3 );
    }
    
    /**
     * Get left trigger axis.
     * 
     * @return Trigger axis value
     **/
    public double getLeftTriggerAxis() {
        return super.getRawAxis( 2 );
    }
    
    /**
     * Get DPad axis.
     * Right = 1
     * Left = -1
     * 
     * @return DPad axis
     **/
    public int getDPadXAxis() {
        return (int) ( super.getRawAxis( 6 ) );
    }
}