package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.lights.LEDlights;
import frc.robot.subsystems.lights.LEDlights.Colour;
import frc.robot.subsystems.lights.LEDlights.CommonColours;

public class LEDChainCommand extends Command {
    private final LEDlights ledSubsystem; 
    private final Timer timer = new Timer();
    private final Timer secondTimer = new Timer();
    private int indexAmount = 1;
    Colour randomColour;
    
    public LEDChainCommand (LEDlights ledSubsystem) {
        this.ledSubsystem = ledSubsystem;
        addRequirements(ledSubsystem);
        
    }
    
    @Override
    public void initialize() {
        timer.start();
        secondTimer.start();
        timer.reset();
        secondTimer.reset();
        indexAmount = -1;
    }
    public void execute() {
        int turnOffLights = indexAmount - 10;
        if (turnOffLights < 0) {
            turnOffLights = 0;
        }
        if (timer.get() > 0.1 ) {
            indexAmount++;
            timer.reset();
            ledSubsystem.setLEDS(CommonColours.CHARTREUSE.colour, indexAmount, 10);
        }
        else if (secondTimer.get() > 0.25 ) {
            ledSubsystem.setLEDS(randomColour, turnOffLights, 10);
            secondTimer.reset();
        }


        if (indexAmount >= 212) {
            indexAmount = -1;
        }

        
        int randomColourIndex = (int) (Math.random() * 6);
        randomColour = switch (randomColourIndex) {
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