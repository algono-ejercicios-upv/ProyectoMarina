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
    
    //Imágenes (modo día y modo noche)
    private final Image barcoLateral = new Image("/proyectomarina/images/barco-lateral.png");
    private final Image barcoLateralNoche = new Image("/proyectomarina/images/barco-lateral-blanco.png");
    private final Image barcoFrente = new Image("/proyectomarina/images/barco-frente.png");
    private final Image barcoFrenteNoche = new Image("/proyectomarina/images/barco-frente-blanco.png");
    
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
        
        //Bindings para que las imágenes de los barcos roten teniendo en cuenta los valores de PITCH y ROLL
        //PITCH: Valor positivo = Proa levantada 
        //(Como este barco mira hacia la derecha, necesitamos que rote en sentido antihorario, así que negamos el valor)
        pitchImage.rotateProperty().bind(Bindings.negate(MarineAccessor.getInstance().PTCHProperty()));
        //ROLL: Valor positivo = Babor (izquierda) levantado 
        //(Como este barco mira hacia nosotros, necesitamos que rote en sentido antihorario, así que negamos el valor)
        rollImage.rotateProperty().bind(Bindings.negate(MarineAccessor.getInstance().ROLLProperty()));
        
        //Cuando se active el modo noche, se ponen las imágenes de color blanco (y viceversa)
        PrincipalController.isNightMode.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                pitchImage.setImage(barcoLateralNoche);
                rollImage.setImage(barcoFrenteNoche);
            } else {
                pitchImage.setImage(barcoLateral);
                rollImage.setImage(barcoFrente);
            }
        });
        
    }    
    
}
