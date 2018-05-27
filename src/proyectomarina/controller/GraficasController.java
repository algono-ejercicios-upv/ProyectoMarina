/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.HBox;
import proyectomarina.model.MarineAccessor;
import proyectomarina.model.WindChart;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class GraficasController implements Initializable {

    @FXML
    private HBox graficasBox;
    @FXML
    private Spinner<Integer> spinnerTime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Spinner properties
        spinnerTime.setValueFactory(new IntegerSpinnerValueFactory(2, 10, 2));
        //TWD Chart
        WindChart TWDChart = MarineAccessor.getInstance().TWDChart();
        TWDChart.setTitle("Dirección (TWD)");
        TWDChart.setXLabel("Tiempo transcurrido (minutos)");
        TWDChart.setYLabel("Dirección (grados)");
        //Basicamente necesita esa conversion para poder hacer Binding (ReadOnlyObjectProperty<Double> -> ReadOnlyDoubleProperty)
        TWDChart.maxTimeProperty().bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(spinnerTime.valueProperty()));
        graficasBox.getChildren().add(TWDChart.getChart());
        //TWS Chart
        WindChart TWSChart = MarineAccessor.getInstance().TWSChart();
        TWSChart.setTitle("Intensidad (TWS)");
        TWSChart.setXLabel("Tiempo transcurrido (minutos)");
        TWSChart.setYLabel("Velocidad (Kn)");
        //Basicamente necesita esa conversion para poder hacer Binding (ReadOnlyObjectProperty<Double> -> ReadOnlyDoubleProperty)
        TWSChart.maxTimeProperty().bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(spinnerTime.valueProperty()));
        graficasBox.getChildren().add(TWSChart.getChart());
    } 
}
