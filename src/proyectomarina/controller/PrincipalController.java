/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import proyectomarina.model.MarineAccessor;

/**
 *
 * @author Alejandro
 */
public class PrincipalController implements Initializable {

    @FXML
    private CheckBox nightMode;
    @FXML
    private BorderPane root;
    @FXML
    private Label temp;
    @FXML
    private ToolBar buttonBar;

    //Array para las ventanas centrales
    private final Parent[] roots = new Parent[4];
    //Indica si el modo noche está puesto o no (para que otras clases lo puedan comprobar)
    public static final BooleanProperty isNightMode = new SimpleBooleanProperty();
    
    private void initWindows() {
        try {
            roots[0] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/VientoView.fxml"));
            roots[1] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/EstadoView.fxml"));
            roots[2] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/GPSView.fxml"));
            roots[3] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/GraficasView.fxml"));
        } catch (IOException ex) {
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initWindows();
        //Definir comportamiento de checkbox para modo noche
        isNightMode.bind(nightMode.selectedProperty());
        nightMode.setOnAction((evt) -> {
            if (nightMode.isSelected()) { root.setStyle("-fx-base: rgba(60, 63, 65, 255)"); } //Enable Night Mode
            else { root.setStyle(Application.STYLESHEET_MODENA); } //Disable Night Mode
        });
        //Binding para temperatura
        temp.textProperty().bind(Bindings.concat(
            MarineAccessor.getInstance().TEMPProperty(), "ºC"
        ));
        //Definimos acciones para los botones de la ToolBar
        ObservableList<Node> items = buttonBar.getItems();
        for (int i = 0; i < items.size(); i++) {
            Parent otherRoot = roots[i];
            ((Button) items.get(i)).setOnAction((evt) -> root.setCenter(otherRoot));
        }
        //Al iniciar el programa, se muestra la primera ventana
        root.setCenter(roots[0]);
    }   
}
