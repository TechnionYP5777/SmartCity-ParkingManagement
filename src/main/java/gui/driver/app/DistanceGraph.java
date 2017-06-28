package gui.driver.app;

import java.util.Map;
import java.util.logging.Logger;

import org.parse4j.ParseGeoPoint;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.Graph;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class DistanceGraph extends Application {

	private static ParseGeoPoint dest;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void main(String[] args){
		ParseGeoPoint p = new ParseGeoPoint(32.777566, 35.022484);
		PresentGraph(p);
	}
	public static void PresentGraph(ParseGeoPoint p){
		dest = p;
		launch();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void start(Stage s) throws Exception {
		s.setTitle("Price - Distance graph");
        final NumberAxis xAxis = new NumberAxis(), yAxis = new NumberAxis();
        xAxis.setLabel("Distance");
        yAxis.setLabel("Price");

        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Price - Distance");
        
        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("Price - Distance");
        
        //get data to present
        Graph g = new Graph();
        Map<Double, Double> data = g.CreatePriceDistanceData(dest);

        //populating the series with data
        for (Map.Entry<Double, Double> entry : data.entrySet()){
        	LOGGER.info("distance: "+ entry.getKey() + " price: " + entry.getValue());
        	series.getData().add(new Data<Number, Number>(entry.getKey(), entry.getValue()));
        }
	        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series);
       
        s.setScene(scene);
        s.show();
		
	}
}
