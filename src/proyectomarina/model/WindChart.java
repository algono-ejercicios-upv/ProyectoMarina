/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.model;

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
    private final IntegerProperty maxTime = new SimpleIntegerProperty(2); //En minutos, pues es lo que usa el xAxis
    
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
    public ObservableList<XYChart.Data<Number,Number>> getObservableList() { return list; }
    //Obtienes la grafica con los datos de list
    public LineChart<Number,Number> getChart() {
        NumberAxis xAxis = new NumberAxis(0, 2, 1); //upperBound (2) aqui es un valor arbitrario, ya que luego hacemos binding
        xAxis.upperBoundProperty().bind(maxTime);
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
        series.setData(list);
        chart.getData().add(series);
        return chart;
    }
    //Properties
    public IntegerProperty maxTimeProperty() { return maxTime; }
    //Setters
    public void setTitle(String t) { title = t; }
    public void setSeriesName(String sn) { seriesName = sn; }
    public void setXLabel(String xn) { xLabel = xn; }
    public void setYLabel(String yn) { yLabel = yn; }
    public void setMaxTime(int time) { maxTime.set(time); checkSize(); }
    //Other methods
    private void checkSize() {
        //maxTime * 60 (1 dato == 1 segundo) + 1 = maxSize (pues dato nº 121 es del segundo 120)
        while (list.size() > maxTime.get() * 60 + 1) { list.remove(0); }
    }
    public void add(Number n) {
        XYChart.Data<Number, Number> dato = new XYChart.Data<>(0, n);
        for (XYChart.Data<Number, Number> d : list) {
            d.setXValue(d.getXValue().doubleValue() + (1/60.0)); //Le añadimos 1 segundo por cada dato, o sea, 1/60 de minuto
        }
        list.add(dato);
        checkSize();
    }
}
