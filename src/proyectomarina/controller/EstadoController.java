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
    @FXML
    private ImageView arrowImage1;
    @FXML
    private ImageView arrowImage2;
    
    //Imágenes (modo día y modo noche)
    private final Image barcoLateral = new Image("/proyectomarina/images/barco-lateral.png");
    private final Image barcoLateralNoche = new Image("/proyectomarina/images/barco-lateral-blanco.png");
    private final Image barcoFrente = new Image("/proyectomarina/images/barco-frente.png");
    private final Image barcoFrenteNoche = new Image("/proyectomarina/images/barco-frente-blanco.png");
    private final Image flecha = new Image("/proyectomarina/images/flecha.png");
    private final Image flechaNoche = new Image("/proyectomarina/images/flecha-blanco.png");
    
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
        hdg.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().HDGProperty(), "º"
        ));
        //Se ponen las imágenes iniciales
        pitchImage.setImage(barcoLateral); rollImage.setImage(barcoFrente);
        arrowImage1.setImage(flecha); arrowImage2.setImage(flecha);
        
        //Bindings para que las imágenes de los barcos roten teniendo en cuenta los valores de PITCH y ROLL
        pitchImage.rotateProperty().bind(MarineAccessor.getInstance().PTCHProperty());
        rollImage.rotateProperty().bind(MarineAccessor.getInstance().ROLLProperty());
        
        //Cuando se active el modo noche, se ponen las imágenes de color blanco (y viceversa)
        PrincipalController.isNightMode.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                pitchImage.setImage(barcoLateralNoche);
                rollImage.setImage(barcoFrenteNoche);
                arrowImage1.setImage(flechaNoche); arrowImage2.setImage(flechaNoche);
            } else {
                pitchImage.setImage(barcoLateral);
                rollImage.setImage(barcoFrente);
                arrowImage1.setImage(flecha); arrowImage2.setImage(flecha);
            }
        });
        
    }    
    
}
