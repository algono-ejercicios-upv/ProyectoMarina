/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Alejandro
 */
public class WindChartList {
    
    private final ObservableList<XYChart.Data<Number,Number>> list;
    private final IntegerProperty maxSize = new SimpleIntegerProperty(2);
    
    public WindChartList() {
        list = FXCollections.observableArrayList();
        maxSize.addListener((obs, oldValue, newValue) -> checkSize());
    }
    //Ponemos la etiqueta synchronized para evitar condiciones de carrera con maxSize
    private synchronized void checkSize() {
        while (list.size() > maxSize.get()) { list.remove(0); }
    }
    public void add(double e) {
        XYChart.Data<Number, Number> data = new XYChart.Data<>(0, e);
        for (XYChart.Data<Number, Number> d : list) {
            d.setXValue(d.getXValue().intValue()+1);
        }
        list.add(data);
        checkSize();
    }
    //Devolvemos una version solo lectura para que solo se puedan insertar o eliminar elementos de la lista desde esta clase
    public ObservableList<XYChart.Data<Number,Number>> getObservableList() { return FXCollections.unmodifiableObservableList(list); }
    
    public IntegerProperty maxSizeProperty() { return maxSize; }
    
    public LineChart<Number,Number> getChart() {
        NumberAxis xAxis = new NumberAxis(0, maxSize.get()-1, 1);
        xAxis.upperBoundProperty().bind(Bindings.subtract(maxSize, 1)); // maxSize - 1
        xAxis.setForceZeroInRange(true);
        LineChart<Number,Number> chart = new LineChart<>(xAxis, new NumberAxis());
        chart.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        chart.setCreateSymbols(false);
        chart.setAnimated(false);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setData(getObservableList()); //Le pasamos la version solo lectura, ya que desde la chart se puede acceder a esta lista
        chart.getData().add(series);
        return chart;
    }
    
}
