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
public class WindChart {
    
    private final ObservableList<XYChart.Data<Number,Number>> list;
    private final IntegerProperty maxTime = new SimpleIntegerProperty(120); //En segundos, pues 1 dato = 1 segundo
    
    private String title, seriesName, xLabel, yLabel;
    
    public WindChart() {
        list = FXCollections.observableArrayList();
        maxTime.addListener((obs, oldValue, newValue) -> checkSize());
    }
    //Getters
    public String getTitle() { return title; }
    public String getSeriesName() { return seriesName; }
    public String getXLabel() { return xLabel; }
    public String getYLabel() { return yLabel; }
    public int getMaxTime() { return maxTime.get(); }
    //Devolvemos una version solo lectura para que solo se puedan insertar o eliminar elementos de la lista desde esta clase
    public ObservableList<XYChart.Data<Number,Number>> getObservableList() { return FXCollections.unmodifiableObservableList(list); }
    //Obtienes la grafica con los datos de list
    public LineChart<Number,Number> getChart() {
        NumberAxis xAxis = new NumberAxis(0, 2, 1); //upperBound (2) aqui es un valor arbitrario, ya que luego hacemos binding
        xAxis.upperBoundProperty().bind(Bindings.divide(maxTime, 60)); // maxTime / 60
        xAxis.setForceZeroInRange(true);
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xLabel); yAxis.setLabel(yLabel);
        LineChart<Number,Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        chart.setCreateSymbols(false);
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        series.setData(getObservableList()); //Le pasamos la version solo lectura, ya que desde la chart se puede acceder a esta lista
        chart.getData().add(series);
        return chart;
    }
    //Setters
    public void setTitle(String t) { title = t; }
    public void setSeriesName(String sn) { seriesName = sn; }
    public void setXLabel(String xn) { xLabel = xn; }
    public void setYLabel(String yn) { yLabel = yn; }
    public void setMaxTime(int time) { maxTime.set(time); checkSize(); }
    //Properties
    public IntegerProperty maxTimeProperty() { return maxTime; }
    //Other methods
    private void checkSize() {
        while (list.size() > maxTime.get()+1) { list.remove(0); } //maxTime + 1 = maxSize (pues dato nº 121 es del segundo 120)
    }
    public void add(double e) {
        XYChart.Data<Number, Number> dato = new XYChart.Data<>(0, e);
        for (XYChart.Data<Number, Number> d : list) {
            d.setXValue(d.getXValue().doubleValue() + (1/60.0)); //Le añadimos 1 segundo por cada dato, o sea, 1/60 de minuto
        }
        list.add(dato);
        checkSize();
    }    
}
