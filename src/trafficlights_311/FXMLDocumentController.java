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
        // TODO
        
    }    
    
}
