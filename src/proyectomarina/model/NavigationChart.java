/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.model;

import javafx.collections.FXCollections;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Alejandro
 */
public class NavigationChart {
    
    private final XYChart.Data<Number,Number> pos;
    private final ScatterChart<Number,Number> chart;
    private final NumberAxis xAxis, yAxis;
    
    private boolean started = false; //Indica si se ha establecido ya o no la primera posición de la que partimos (esta será nuestro centro)
    
    public static final double RANGE = 0.00005; //El rango que tendrá la gráfica (ajustado para que los cambios sean perceptibles)
    public static final double TICK_UNIT = 0.00002; //Cada cuánto se mostrará el valor en las líneas
    
    //Especifica que los valores mostrados en las líneas de las 'x' y las 'y' muestren hasta 5 decimales
    private final NumberStringConverter formatter = new NumberStringConverter("###.#####");
    
    public NavigationChart() {
        pos = new XYChart.Data<>(0, 0);
        xAxis = new NumberAxis("Latitud", -RANGE, RANGE, TICK_UNIT);
        xAxis.setTickLabelFormatter(formatter);
        yAxis = new NumberAxis("Longitud", -RANGE, RANGE, TICK_UNIT);
        yAxis.setTickLabelFormatter(formatter);
        chart = new ScatterChart<>(xAxis, yAxis);
        //Inserta una singletonObservableList, que es una lista inmutable que solo permite un elemento
        //(en este caso la posicion del barco, que será la que iremos cambiando sobre la marcha)
        Series<Number, Number> series = new Series<>("Barco", FXCollections.singletonObservableList(pos));
        chart.getData().add(series);
    }
    
    public ScatterChart<Number,Number> getChart() { return chart; }
    
    public void setPos(double x, double y) {
        pos.setXValue(x); pos.setYValue(y);
        //Establece la primera posición que recibe como el centro de la gráfica (para que siempre sea relativa a esta)
        if (!started) {
            xAxis.setLowerBound(x - RANGE);
            xAxis.setUpperBound(x + RANGE);
            yAxis.setLowerBound(y - RANGE);
            yAxis.setUpperBound(y + RANGE);
        }
        started = true;
        //Obtiene los límites actuales de la gráfica
        double xLower = xAxis.getLowerBound(), xUpper = xAxis.getUpperBound();
        double yLower = yAxis.getLowerBound(), yUpper = yAxis.getUpperBound();
        //Ajusta la gráfica para que la posición sea siempre visible en esta
        //(le hace como un "zoom out" aumentando el rango de todos los bordes de forma proporcional)
        while (x < xLower || x > xUpper || y < yLower || y > yUpper) {
            //Cambia los límites
            xLower -= RANGE; xUpper += RANGE;
            yLower -= RANGE; yUpper += RANGE;
            //Actualiza la gráfica con los nuevos valores
            xAxis.setLowerBound(xLower);
            xAxis.setUpperBound(xUpper);
            yAxis.setLowerBound(yLower);
            yAxis.setUpperBound(yUpper);
        }
    }
}
