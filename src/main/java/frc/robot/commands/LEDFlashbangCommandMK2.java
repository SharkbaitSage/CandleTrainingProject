package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.lights.LEDlights;
import frc.robot.subsystems.lights.LEDlights.Colour;
import frc.robot.subsystems.lights.LEDlights.CommonColours;

public class LEDFlashbangCommandMK2 extends Command{
    private final LEDlights ledSubsystem;
    private final Timer timer = new Timer();
    private int LEDPosition = -1;
    private Colour[] currentColour = {CommonColours.RED.colour,
        CommonColours.BLUE.colour,CommonColours.WHITE.colour,CommonColours.GREEN.colour
        , CommonColours.PURPLE.colour,CommonColours.YELLOW.colour };
    private Colour chaosColour;
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

    public void colourPicker() {
        currentColour[0] = CommonColours.BLUE.colour;
        currentColour[1] = CommonColours.RED.colour;
        currentColour[2] = CommonColours.WHITE.colour;
        currentColour[3] = CommonColours.GREEN.colour;
        currentColour[4] = CommonColours.PURPLE.colour;
        currentColour[5] = CommonColours.YELLOW.colour;
    }
    
    
    public void execute() {
        //colourPicker();
        int lightTrail = LEDPosition - 10;
        if (lightTrail < 0) {
            lightTrail = 0;
        }
        if (timer.get() >= 0.1 && LEDPosition < 212) {
            timer.reset();
            ledSubsystem.setLEDS(chaosColour, LEDPosition,10);
            LEDPosition++;
           
        }
       
        if (LEDPosition >= 212 && timer.get() >= 1) {
            
            timer.reset();
            ledSubsystem.setLEDS(CommonColours.OFF.colour, 0, 212);
            ledSubsystem.setLEDS(currentColour[colourIndex], 0, 212);
            colourIndex++;
            if (colourIndex >= 6) {
                colourIndex = 0;
            }
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

