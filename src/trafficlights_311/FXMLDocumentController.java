/*
/***************************************
* Filename: FXMLDocumentController.java
* Short description: FXML Controller
* @author James McGettigan
* @version  12/2/2019
***************************************/
package trafficlights_311;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {
    
    //FXML Variables
    @FXML 
    private Circle greenLight, yellowLight, redLight,
                        greenOff, yellowOff, redOff;
    @FXML 
    private Slider redSlider, yellowSlider, greenSlider;
    
    @FXML 
    private Text redLabel, yellowLabel, greenLabel;
    
    @FXML 
    private Button startButton, pauseButton;
    
    @FXML 
    private GridPane gr;
    
    //Period of time yellow light should cycle (off 1/2 period, on other 1/2)
    private final double blinkCycle = 1;
    private final double lowOpacity = 0.5;
    
    //Other class variables
    private DoubleProperty greenValue, yellowVal, redValue;
    private BooleanProperty disableButton;
    private SequentialTransition sequence;
    
    /**
     * What the controller is to do on startup
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Make sure the lights are off
        this.darken(redLight);
        this.darken(yellowLight);
        this.darken(greenLight);
        
        //Bind the disabled properties of start and pause buttons
        disableButton = new SimpleBooleanProperty(false);
        startButton.disableProperty().bind(disableButton);
        pauseButton.disableProperty().bind(disableButton.not());
        
        //Create the bindings
        greenValue = new SimpleDoubleProperty();
        yellowVal = new SimpleDoubleProperty();
        redValue = new SimpleDoubleProperty();
        
        //Bind the bindings
        greenValue.bind(redSlider.valueProperty());        
        yellowVal.bind(yellowSlider.valueProperty());       
        redValue.bind(greenSlider.valueProperty());
        
        //Set the text properties
        redLabel.textProperty().bind(Bindings.format("%3.0f", greenValue).concat(" Seconds"));
        yellowLabel.textProperty().bind(Bindings.format("%3.0f", yellowVal).concat(" Seconds"));
        greenLabel.textProperty().bind(Bindings.format("%3.0f", redValue).concat(" Seconds"));
        
        //Setup Lights to resize according to grid size
        redOff.radiusProperty().bind(redLight.radiusProperty());
        yellowOff.radiusProperty().bind(yellowLight.radiusProperty());
        greenOff.radiusProperty().bind(greenLight.radiusProperty());
        
        //I wouldn't need to divide by 8 if I could get the number of rows and multiply by 2
        redLight.radiusProperty().bind(gr.heightProperty().divide(8));
        yellowLight.radiusProperty().bind(gr.heightProperty().divide(8));
        greenLight.radiusProperty().bind(gr.heightProperty().divide(8));
        
        //Finalize and start the program
        this.buildAnimation();
        this.startAnimation();
    }    
    
    /**
     * Start the animation when pushed
     */
    @FXML protected void startAnimation()
    {
        //Reset Lights
        this.darken(redLight);
        this.darken(yellowLight);
        this.darken(greenLight);
        
        //Toggle the buttons
        disableButton.set(true);
        
        //Bebuild and start the animation
        this.buildAnimation();
        sequence.play();
    }
    
    /**
     * Pause the animation where it is
     */
    @FXML protected void pauseAnimation()
    {
        sequence.pause();
        disableButton.set(false);
    }
    
    /**
     * Create the Sequential Transition and allows it to be rebuilt with changes
     */
    private void buildAnimation()
    {
        //Put together the yellow light flashing animation
        SequentialTransition yellowFlash = new SequentialTransition(
        new Timeline(
            new KeyFrame(Duration.seconds(0), e -> lighten(yellowLight)),
            new KeyFrame(Duration.seconds(blinkCycle/2), e -> darken(yellowLight)),
            new KeyFrame(Duration.seconds(blinkCycle))
        ));
        
        //Finish up setup
        yellowFlash.setOnFinished(e -> darken(yellowLight));
        yellowFlash.setCycleCount((int)Math.round(yellowVal.divide(blinkCycle).get()));
        
        //Create hte rest of the sequence
        sequence = new SequentialTransition(
                new Timeline(
                    new KeyFrame(Duration.seconds(0), e -> lighten(redLight)),
                    new KeyFrame(Duration.seconds(greenValue.get()), e -> darken(redLight))),
                new Timeline(
                    new KeyFrame(Duration.seconds(0), e -> lighten(greenLight)),
                    new KeyFrame(Duration.seconds(redValue.get()), e -> darken(greenLight)))
        );
        
        //Add the yellow light handling if the yellow light is required
        if(yellowFlash.getCycleCount() > 0)
            sequence.getChildren().add(yellowFlash);
        
        //If all sliders are at 0, hold for 1 second
        if(redValue.get() < 1 && yellowVal.get() < 1 && greenValue.get() < 1)
        {
            sequence.getChildren().add(new Timeline(
                    new KeyFrame(Duration.seconds(0)),
                    new KeyFrame(Duration.seconds(1))
            ));
        }
        
        //Set the sequence to rebuild and start after ending
        sequence.setOnFinished(e -> {buildAnimation(); sequence.play();});
        
        //Set cycle to 1
        sequence.setCycleCount(1);
    }
    
    /**
     * Brighten the shape by raising the opacity
     * @param target The shape to affect
     */
    private void lighten(Circle target)
    {
        target.setOpacity(1);
    }
    
    /**
     * Darken the shape by lowing the opacity
     * @param target The shape to affect
     */
    private void darken(Circle target)
    {
        target.setOpacity(lowOpacity);
    }
}
