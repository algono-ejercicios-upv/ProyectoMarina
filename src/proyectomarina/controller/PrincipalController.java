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

    //Windows
    private final Parent[] roots = new Parent[4];

    private void initWindows() {
        try {
            //roots[0] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/GraficasView.fxml"));
            //roots[1] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/GraficasView.fxml"));
            //roots[2] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/GraficasView.fxml"));
            roots[3] = FXMLLoader.load(getClass().getResource("/proyectomarina/view/GraficasView.fxml"));
        } catch (IOException ex) {
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initWindows();
        //Definir comportamiento de checkbox para modo noche
        nightMode.setOnAction((evt) -> {
            if (nightMode.isSelected()) { root.setStyle("-fx-base: rgba(60, 63, 65, 255)"); } //Enable Night Mode
            else { root.setStyle(Application.STYLESHEET_MODENA); } //Disable Night Mode
        });
        //Binding para temperatura
        temp.textProperty().bind(Bindings.concat(
                MarineAccessor.getInstance().TEMPProperty(),
                " ºC"
        ));
        //Definimos acciones para los botones de la ToolBar
        ObservableList<Node> items = buttonBar.getItems();
        for (int i = 0; i < items.size(); i++) {
            Parent otherRoot = roots[i];
            ((Button) items.get(i)).setOnAction((evt) -> root.setCenter(otherRoot));
        }
        /*//Codigo de prueba para la grafica
        WindChart TWDList = MarineAccessor.getInstance().TWDList();
        TWDList.setTitle("Test");
        TWDList.setSeriesName("TWD");
        TWDList.setXLabel("Minutos pasados desde el momento actual");
        TWDList.setYLabel("Dirección (grados)");
        LineChart<Number, Number> lineChart = MarineAccessor.getInstance().TWDList().getChart();
        root.setCenter(lineChart);
        //Codigo de prueba para el spinner
        Spinner<Integer> spinner = new Spinner<>(2, 10, 2);
        MarineAccessor.getInstance().TWDList().maxTimeProperty().bind(
        Bindings.multiply(
        //Basicamente necesita esa conversion para poder hacer Binding (ReadOnlyObjectProperty<Double> -> ReadOnlyDoubleProperty)
        ReadOnlyDoubleProperty.readOnlyDoubleProperty(spinner.valueProperty()),
        60)
        );
        root.setTop(spinner);*/
    }   
}
