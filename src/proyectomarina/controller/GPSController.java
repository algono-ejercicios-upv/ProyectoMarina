/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.sf.marineapi.nmea.util.Position;
import proyectomarina.model.MarineAccessor;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class GPSController implements Initializable {

    @FXML
    private Label lat;
    @FXML
    private Label lon;
    @FXML
    private Label cog;
    @FXML
    private Label sog;

   

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
        * Uso de String.format(): 
        * Los % delimitan el formato que se le aplica a cada argumento que se le pasa
        * .5f = Numero con decimales, muestra hasta 5 cifras decimales
        * s = Un string normal
        */
        MarineAccessor.getInstance().GPSProperty().addListener((observable, oldValue, newValue) -> { 
            lat.setText(String.format("%.5f%s", newValue.getLatitude(), "º " + newValue.getLatitudeHemisphere()));
            lon.setText(String.format("%.5f%s", newValue.getLongitude(), "º " + newValue.getLongitudeHemisphere()));
        });

        cog.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().COGProperty(), "º"
        ));
        sog.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().SOGProperty(), "Kn"
        ));
        
    }    
    
}
