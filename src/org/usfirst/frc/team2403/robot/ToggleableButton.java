package org.usfirst.frc.team2403.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class ToggleableButton extends JoystickButton{
    protected boolean isHeld = false; //If the button is being held

    /**
     * Creates an instance of the toggleableButton class
     * @param joystick Joystick that the button will be found on
     * @param buttonNumber which button on the joystick
     */
    public ToggleableButton( GenericHID joystick, int buttonNumber ) {
        super( joystick, buttonNumber );
    }

    /**
     * Prevents the button from creating infinite loops
     * @return True for one time that the button is pressed
     */
    public boolean isPressed() {
        if ( super.get() ) {
            
            if( ! isHeld ){
                isHeld = true;
                return true;
            } else {
                return false;
            }
        } else{
            isHeld = false;
            return false;
        }
    }
    
    /**
     * Prevents the button from creating infinite loops
     * @return true only when the button is released
     */
    public boolean isReleased(){
        if ( super.get()){
            isHeld = true;
            return false;
        } else if ( isHeld ) {
            isHeld = false;
            return true;
        }
        
        System.out.println("isReleased in toggleableButton has returned false unexpectantly");
        return false;
    }
    
}
