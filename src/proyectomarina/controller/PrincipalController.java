/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nightMode.setOnAction((evt) -> {
            if (nightMode.isSelected()) { root.setStyle("-fx-base: rgba(60, 63, 65, 255)"); } //Enable Night Mode
            else { root.setStyle(Application.STYLESHEET_MODENA); } //Disable Night Mode
        });
        temp.textProperty().bind(Bindings.concat(
                MarineAccessor.getInstance().TEMPProperty(),
                " ºC"
        ));
        //Codigo de prueba para la grafica
        LineChart<Number, Number> lineChart = MarineAccessor.getInstance().TWDList().getChart();
        lineChart.setTitle("Test");
        lineChart.getData().get(0).setName("TWD");
        root.setCenter(lineChart);
        //Codigo de prueba para el spinner
        Spinner<Integer> spinner = new Spinner<>(2, 10, 2);
        MarineAccessor.getInstance().TWDList().maxSizeProperty().bind(spinner.valueProperty());
        root.setBottom(spinner);
    }   
}
