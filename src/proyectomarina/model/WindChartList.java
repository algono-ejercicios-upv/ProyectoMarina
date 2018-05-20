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
import javafx.scene.chart.XYChart.Data;

/**
 *
 * @author Alejandro
 */
public class WindChartList {
    
    private final ObservableList<Data<Number,Number>> list;
    private final IntegerProperty maxSize = new SimpleIntegerProperty(10);
    
    public WindChartList() {
        list = FXCollections.observableArrayList();
        maxSize.addListener((obs, oldValue, newValue) -> checkSize());
    }
    private void checkSize() {
        while (list.size() > maxSize.get()) { list.remove(0); }
    }
    public void add(double e) {
        Data<Number, Number> data = new Data<>(0, e);
        for (Data<Number, Number> d : list) {
            d.setXValue(d.getXValue().intValue()+1);
        }
        checkSize();
        list.add(data);
    }
    //Devolvemos una version solo lectura para que solo se puedan insertar o eliminar elementos de la lista desde esta clase
    public ObservableList<Data<Number,Number>> getObservableList() { return FXCollections.unmodifiableObservableList(list); }
    public IntegerProperty maxSizeProperty() { return maxSize; }
}
