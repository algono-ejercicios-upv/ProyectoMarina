
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomarina.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.HDGSentence;
import net.sf.marineapi.nmea.sentence.MDASentence;
import net.sf.marineapi.nmea.sentence.MWVSentence;
import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.XDRSentence;
import net.sf.marineapi.nmea.util.Measurement;
import net.sf.marineapi.nmea.util.Position;


public class MarineAccessor {

    //implementa el patron singleton
    // esto asegura que solamente se va a crear una instancia de la clase model
    // y se podra acceder a ella desde cualquier clase del proyecto
    private static MarineAccessor model;

    private MarineAccessor() {
    }
    public static MarineAccessor getInstance() {
        if (model == null) {
            model = new MarineAccessor();
        }
        return model;
    }
    
    
    //===================================================================
    // CUIDADO, el objeto de la clase SentenceReader se ejecuta en un hilo
    // no se pueden modificar las propiedades de los objetos graficos desde
    // un metodo ejecutado en este hilo
    
    //Para arreglar el problema mencionado arriba, utilizo Platform.runLater() en todos los Listeners de esta clase
    //(para info más extendida, mirar en esa parte)
    private SentenceReader reader;
    
    //Heading - compas magnetic
    private final DoubleProperty HDG = new SimpleDoubleProperty();
    public DoubleProperty HDGProperty() {
        return HDG;
    }
    
    //True Wind Dir -- direccion del viento respecto al norte
    private final DoubleProperty TWD = new SimpleDoubleProperty();
    public DoubleProperty TWDProperty() {
        return TWD;
    }
    //Lista de TWD para grafica
    private final WindChart TWDChart = new WindChart();
    public WindChart TWDChart() { return TWDChart; }
    
    // True Wind Speed -- intensidad de viento
    private final DoubleProperty TWS = new SimpleDoubleProperty();
    public DoubleProperty TWSProperty() {
        return TWS;
    }
    //Lista de TWS para grafica
    private final WindChart TWSChart = new WindChart();
    public WindChart TWSChart() { return TWSChart; }
    
    // Air Temperature -- temperatura del aire
    private final DoubleProperty TEMP = new SimpleDoubleProperty();
    public DoubleProperty TEMPProperty() {
        return TEMP;
    }
    
    // AWA -- Dirección del viento relativo a la proa
    private final DoubleProperty AWA = new SimpleDoubleProperty();
    public DoubleProperty AWAProperty() {
        return AWA;
    }
    
    // AWS -- Velocidad del viento relativo a la proa
    private final DoubleProperty AWS = new SimpleDoubleProperty();
    public DoubleProperty AWSProperty() {
        return AWS;
    }
    
    // True Wind Speed -- intensidad de viento
    private final DoubleProperty PTCH = new SimpleDoubleProperty();
    public DoubleProperty PTCHProperty() {
        return PTCH;
    }
    
    // True Wind Speed -- intensidad de viento
    private final DoubleProperty ROLL = new SimpleDoubleProperty();
    public DoubleProperty ROLLProperty() {
        return ROLL;
    }
    
    //==================================================================
    // anade todas las propiedades que necesites, en el hilo principal
    // podras anadir listeners sobre estas propiedades que modifquen la interfaz
    
    // Position -- posicion del GPS
    private final ObjectProperty<Position> GPS = new SimpleObjectProperty();
    public ObjectProperty<Position> GPSProperty() {
        return GPS;
    }
    
    private final NavigationChart GPSChart = new NavigationChart();
    public NavigationChart GPSChart() {
        return GPSChart;
    }
    // COG -- rumbo del GPS
    private final DoubleProperty COG = new SimpleDoubleProperty();
    public DoubleProperty COGProperty() {
        return COG;
    }
    // SOG -- velocidad del GPS
    private final DoubleProperty SOG = new SimpleDoubleProperty();
    public DoubleProperty SOGProperty() {
        return SOG;
    }
    
    //====================================================================
    //anadir tantos sentenceListener como tipos de sentence queremos tratar
    // anade todas las clases de que extiendan AbstractSentenceListener que necesites
    
    //Usar Platform.runLater() en todos los listener me permite usar bindings
    //para asociar directamente la Property indicada con su Label (o el elemento que sea)
    //sin tener que preocuparme ya que siempre se harán los cambios en la FX Application Thread
    
    class HDGSentenceListener
            extends AbstractSentenceListener<HDGSentence> {

        @Override
        public void sentenceRead(HDGSentence sentence) {
            Platform.runLater(() -> {
                HDG.set(sentence.getHeading());
            });     
        }
    };

    class MDASentenceListener
            extends AbstractSentenceListener<MDASentence> {

        @Override
        public void sentenceRead(MDASentence sentence) {
            Platform.runLater(() -> {
                double twd = sentence.getTrueWindDirection();
                TWD.set(twd);
                TWDChart.add(twd);
                double tws = sentence.getWindSpeedKnots();
                TWS.set(tws);
                TWSChart.add(tws);
                TEMP.set(sentence.getAirTemperature());
            });
        }
    }
    
    class MWVSentenceListener
            extends AbstractSentenceListener<MWVSentence> {

        @Override
        public void sentenceRead(MWVSentence sentence) {
            Platform.runLater(() -> {
                AWA.set(sentence.getAngle());
                AWS.set(sentence.getSpeed());
            });
        }
    }
    
    class XDRSentenceListener
            extends AbstractSentenceListener<XDRSentence> {

        @Override
        public void sentenceRead(XDRSentence sentence) {
            Platform.runLater(() -> {
                List<Measurement> ms = sentence.getMeasurements();
                for (Measurement m : ms) {
                    if (m.getName().equals("PTCH")) PTCH.set(m.getValue());
                    else if (m.getName().equals("ROLL")) ROLL.set(m.getValue());
                }
            });
        }
    }
    
    class RMCSentenceListener
            extends AbstractSentenceListener<RMCSentence> {

        @Override
        public void sentenceRead(RMCSentence sentence) {
            Platform.runLater(() -> {
                Position gps = sentence.getPosition();
                GPS.set(gps); // GPS = LAT + LON
                GPSChart.setPos(gps.getLatitude(), gps.getLongitude());
                COG.set(sentence.getCourse());
                SOG.set(sentence.getSpeed());
            });
        }
    }
    
//=========================================================================    
    
    // falta por gestionar que solamente hay un sentenceReader
    public void addSentenceReader(File file) throws FileNotFoundException {
        addSentenceReader(new FileInputStream(file));
    }
    public void addSentenceReader(InputStream stream) {
        if (reader != null) {  // esto ocurre si ya estamos leyendo un fichero
            reader.stop();
        }
        reader = new SentenceReader(stream);
 
        //==================================================================
        //============= Registra todos los sentenceListener que necesites
        HDGSentenceListener hdg = new HDGSentenceListener();
        reader.addSentenceListener(hdg);

        MDASentenceListener mda = new MDASentenceListener();
        reader.addSentenceListener(mda);

        MWVSentenceListener mwv = new MWVSentenceListener();
        reader.addSentenceListener(mwv);
        
        XDRSentenceListener xdr = new XDRSentenceListener();
        reader.addSentenceListener(xdr);

        RMCSentenceListener rmd = new RMCSentenceListener();
        reader.addSentenceListener(rmd);
        
         //===============================================================

         //===============================================================
         //== Anadimos un exceptionListener para que capture las tramas que 
         // == no tienen parser, ya que no las usamos
         reader.setExceptionListener(e->{System.out.println(e.getMessage());});
         
         //================================================================
         //======== arrancamos el SentenceReader para que empieze a escuchar             
        reader.start();
    }
}
