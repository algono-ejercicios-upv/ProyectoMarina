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
        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(0, 10, 1), new NumberAxis());
        lineChart.setTitle("Test");
        lineChart.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("TWD");
        series.setData(MarineAccessor.getInstance().TWDList().getObservableList());
        lineChart.getData().add(series);
        root.setCenter(lineChart);
    }   
}
