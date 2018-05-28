/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import proyectomarina.model.MarineAccessor;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class EstadoController implements Initializable {

    @FXML
    private Label pitch;
    @FXML
    private Label roll;
    @FXML
    private Label hdg;
    @FXML
    private ImageView pitchImage;
    @FXML
    private ImageView rollImage;


    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        pitch.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().PTCHProperty(), "º"
        ));
        pitchImage.setImage(new Image("/proyectomarina/images/barco-lateral.png"));
        pitchImage.rotateProperty().bind(MarineAccessor.getInstance().PTCHProperty());
        
        roll.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().ROLLProperty(), "º"
        ));
        rollImage.setImage(new Image("/proyectomarina/images/barco-frente.png"));
        rollImage.rotateProperty().bind(MarineAccessor.getInstance().ROLLProperty());
        
        hdg.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().HDGProperty(), "º"
        ));
        
    }    
    
}
