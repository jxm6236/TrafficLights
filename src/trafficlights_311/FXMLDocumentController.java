/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficlights_311;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
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
        
    }    
    
}
