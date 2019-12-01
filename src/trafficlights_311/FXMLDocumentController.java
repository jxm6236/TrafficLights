/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficlights_311;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author jmhaz
 */
public class FXMLDocumentController implements Initializable {
    
    //FXML Variables
    @FXML
    private Text greenLabel, yellowLabel, redLabel;
    
    @FXML
    private Slider greenSlide, yellowSlide, redSlide;
    
    @FXML
    private Button start, pause;
    
    @FXML
    private Circle green, greenOff, yellow, yellowOff, red, redOff;
    
    @FXML
    private GridPane gr;
    
    //yellow light cycle
    private final double blinkCycle = 1;
    private final double lowOpacity = 0.5;
    
    //VAriables
    private DoubleProperty greenValue, yellowVal, redValue;
    private BooleanProperty disableBtn;
    private SequentialTransition sequence;
    
    private void handleButtonAction(ActionEvent event) {
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Bind disabled properties
        disableBtn = new SimpleBooleanProperty(false);
        start.disableProperty().bind(disableBtn);
        pause.disableProperty().bind(disableBtn.not());
        
        //Create Binds
        greenValue = new SimpleDoubleProperty();
        yellowVal = new SimpleDoubleProperty();
        redValue = new SimpleDoubleProperty();
        
        //Bind Action
        greenValue.bind(greenSlide.valueProperty());
        yellowVal.bind(yellowSlide.valueProperty());
        redValue.bind(redSlide.valueProperty());
        
        //Set Testing Properties
        greenLabel.textProperty().bind(Bindings.format("%3.0f", redValue).concat(" Seconds"));
        yellowLabel.textProperty().bind(Bindings.format("%3.0f", redValue).concat(" Seconds"));
        redLabel.textProperty().bind(Bindings.format("%3.0f", redValue).concat(" Seconds"));
        
        //Light setup to fit grid
        greenOff.radiusProperty().bind(green.radiusProperty());
        yellowOff.radiusProperty().bind(yellow.radiusProperty());
        redOff.radiusProperty().bind(red.radiusProperty());
        
        green.radiusProperty().bind(gr.heightProperty().divide(8));
        yellow.radiusProperty().bind(gr.heightProperty().divide(8));
        red.radiusProperty().bind(gr.heightProperty().divide(8));
    }    
    
    @FXML
    protected void startAnimation() {
        sequence.play();
        disableBtn.set(true);
    }
    
    @FXML
    protected void pauseAnimation() {
        sequence.pause();
        disableBtn.set(false);
    }
    
    //Build Aniumation
    private void buildAnimation() {
        //yellow light flashing 
        SequentialTransition yellowFlash = new SequentialTransition(
        new Timeline(
            new KeyFrame(Duration.seconds(0), e -> light(yellow)),
            new KeyFrame(Duration.seconds(blinkCycle/2), e -> darken(yellow)),
            new KeyFrame(Duration.seconds(blinkCycle))    
        ));
        
         //Finish up setup
        yellowFlash.setOnFinished(e -> darken(yellow));
        yellowFlash.setCycleCount((int)Math.round(yellowVal.divide(blinkCycle).get()));
        
        //Other sequence 
        sequence = new SequentialTransition(
                new Timeline(
                    new KeyFrame(Duration.seconds(0), e -> light(green)),
                    new KeyFrame(Duration.seconds(greenValue.get()), e -> darken(green))),
                new Timeline(
                    new KeyFrame(Duration.seconds(0), e -> light(red)),
                    new KeyFrame(Duration.seconds(redValue.get()), e -> darken(red))),
                yellowFlash
        );
        
        //Rebuild sequence and start up
        sequence.setOnFinished(e -> {buildAnimation(); sequence.play();});
        
        //Reset cycle
        sequence.setCycleCount(1);   
    }
    
    private void light(Circle target) {
        target.setOpacity(1);
    }
    
    private void darken(Circle target) {
        target.setOpacity(lowOpacity);
    }
    
}
