package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.lights.LEDlights;
import frc.robot.subsystems.lights.LEDlights.Colour;
import frc.robot.subsystems.lights.LEDlights.CommonColours;

public class LEDFlashbangCommand extends Command{
    private final LEDlights ledSubsystem;
    private final Timer timer = new Timer();
    private final Timer trailTimer = new Timer();
    private int indexAmount;
    private Colour currentColour;
    private Colour chaosColour;
    private int colourIndex = 0;
    private int lightTrail = indexAmount - 10;
    
    public LEDFlashbangCommand (LEDlights ledSubsystem) {
        this.ledSubsystem = ledSubsystem;
        addRequirements(ledSubsystem);
    }

    @Override
    public void initialize() {
        timer.start();
        timer.reset();
        trailTimer.start();
        trailTimer.reset();
        indexAmount = -1;
    }

    public void colourPicker() {
        if (colourIndex == 0) {
            currentColour = CommonColours.BLUE.colour;
        }
        else if (colourIndex == 1) {
            currentColour = CommonColours.RED.colour;
        }
        else if (colourIndex == 2) {
            currentColour = CommonColours.GREEN.colour;
        }
        else if (colourIndex == 3) {
            currentColour = CommonColours.PURPLE.colour; 
        }
        else if (colourIndex == 4) {
            currentColour = CommonColours.CYAN.colour;
        }
        else if (colourIndex == 5) {
            currentColour = CommonColours.CHARTREUSE.colour;
        }
        else {
            currentColour = CommonColours.OFF.colour;
        }
        if (colourIndex >= 6) {
            colourIndex = 0;
        }
    }
    
    
    public void execute() {
        if (lightTrail < 0) {
            lightTrail = 0;
        }
        if (timer.get() > 0.1 && indexAmount < 212) {
            timer.reset();
            ledSubsystem.setLEDS(CommonColours.CHARTREUSE.colour, indexAmount,10);
            indexAmount++;
        }
        if (trailTimer.get() > 0.25 && indexAmount < 212 ) {
            ledSubsystem.setLEDS(chaosColour, lightTrail, 10);
        }
        if (indexAmount >= 212 && timer.get() > 0.1) {
            colourPicker();
            timer.reset();
            ledSubsystem.setLEDS(currentColour, 1, 212);
            colourIndex++;
        }
    int chaosColourIndex = (int) (Math.random() * 6);
    chaosColour = switch (chaosColourIndex) {
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

