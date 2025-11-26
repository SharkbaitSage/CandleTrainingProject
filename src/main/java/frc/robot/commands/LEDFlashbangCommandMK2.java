package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.lights.LEDlights;
import frc.robot.subsystems.lights.LEDlights.Colour;
import frc.robot.subsystems.lights.LEDlights.CommonColours;

public class LEDFlashbangCommandMK2 extends Command {
    private final LEDlights ledSubsystem;
    private final Timer timer = new Timer();
    private int LEDPosition = -1;
    private int colourIndex = 0;
    
    
    public LEDFlashbangCommandMK2 (LEDlights ledSubsystem) {
        this.ledSubsystem = ledSubsystem;
        addRequirements(ledSubsystem);
    }

    @Override
    public void initialize() {
        timer.start();
        timer.reset();
        LEDPosition = 0;
    }
    
    @Override
    public void execute() {
        int lightTrail = LEDPosition - 10;
        if (lightTrail < 0) {
            lightTrail = 0;
        }
        if (timer.get() >= 0.1 && LEDPosition < 212) {
            timer.reset();

            int chaosColourIndex = (int) (Math.random() * 6);
            ledSubsystem.setLEDS(getSetColour(chaosColourIndex), LEDPosition,10);
            LEDPosition++;
        }
       
        if (LEDPosition >= 212 && timer.get() >= 1) {
            
            timer.reset();
            ledSubsystem.setLEDS(CommonColours.OFF.colour, 0, 212);
            ledSubsystem.setLEDS(getSetColour(colourIndex), 0, 212);
            colourIndex++;
            if (colourIndex >= 6) {
                colourIndex = 0;
            }
        }
    }

    /**
     * Returns set colours for a colour index between 0-5 inclusive
     * 
     */
    public Colour getSetColour(int colourIndex) {
        return switch(colourIndex) {
            case 0 -> CommonColours.BLUE.colour;
            case 1 -> CommonColours.CYAN.colour;
            case 2 -> CommonColours.RED.colour;
            case 3 -> CommonColours.GREEN.colour;
            case 4 -> CommonColours.PURPLE.colour;
            case 5 -> CommonColours.YELLOW.colour;
            default -> CommonColours.OFF.colour;
        };
    }
}