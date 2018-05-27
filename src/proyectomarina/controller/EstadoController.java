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


    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        pitch.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().PTCHProperty(), "º"
        ));
        roll.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().ROLLProperty(), "º"
        ));
    }    
    
}
