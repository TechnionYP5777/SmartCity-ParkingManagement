package gui.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

import java.util.Locale;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
/*
 * @Autor Shay Segal
 */
public class PmMap extends Application implements MapComponentInitializedListener
{

    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    protected DirectionsPane directions;
    private Label lblCenter;
    private Label lblClick;
    private ComboBox<MapTypeIdEnum> mapTypeCombo;
	
	private MarkerOptions markerOptions2;
	private Marker myMarker;
	private Marker myMarker2;
	private Button btnHideMarker;
	private Button btnDeleteMarker;
	private Button btnReturn;
	private Scene scene;
        
    @Override
    public void start(final Stage s) throws Exception {
        mapComponent = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapComponent.addMapInializedListener(this);
                
        BorderPane bp = new BorderPane();
        ToolBar tb = new ToolBar();
        lblCenter = new Label();
        lblClick = new Label();
        
        mapTypeCombo = new ComboBox<>();
        mapTypeCombo.setOnAction( e -> {
           map.setMapType(mapTypeCombo.getSelectionModel().getSelectedItem() );
        });
        mapTypeCombo.setDisable(true);
        
        Button btnType = new Button("Map type");
        btnType.setOnAction(e -> {
            map.setMapType(MapTypeIdEnum.HYBRID);
        });
		
		btnHideMarker = new Button("Hide Marker");
		btnHideMarker.setOnAction(e -> {hideMarker();});
		
		btnDeleteMarker = new Button("Delete Marker");
		btnDeleteMarker.setOnAction(e -> {deleteMarker();});
		
		btnReturn = new Button("return");
		btnReturn.setOnAction(e->{s.close();});
        tb.getItems().addAll(new Label("MapType: "),mapTypeCombo,
                new Label("Coordinates: "), lblCenter,
                new Label("Click: "), lblClick,
				btnHideMarker, btnDeleteMarker,btnReturn);

        bp.setTop(tb);
        
        bp.setCenter(mapComponent);
        scene = new Scene(bp);
        s.setScene(scene);
        s.show();
    }

    DirectionsRenderer renderer;
    
    @Override
    public void mapInitialized() {
        //Once the map has been loaded by the Webview, initialize the map details.
        LatLong center = new LatLong(32.777, 35.0225);
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            checkCenter(center);
        });
        lblClick.setText((center + ""));
        MapOptions options = new MapOptions();
        options.center(center)
        		.zoom(2)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(true)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.HYBRID);
        map = mapComponent.createMap(options,false);
        directions = mapComponent.getDirec();        
        map.setHeading(123.2);

        MarkerOptions markerOptions = new MarkerOptions();
        LatLong markerLatLong = new LatLong(32.777157, 35.023131);//Ulman
        markerOptions.position(markerLatLong)
                .title("The Technion")
                .animation(Animation.DROP)
                .visible(true);

        myMarker = new Marker(markerOptions);

        markerOptions2 = new MarkerOptions();
        LatLong markerLatLong2 = new LatLong(32.778032, 35.023663);//TAUB
        markerOptions2.position(markerLatLong2)
                .title("My new Marker")
                .visible(true);

        myMarker2 = new Marker(markerOptions2);

        map.addMarker(myMarker);
        map.addMarker(myMarker2);

        InfoWindowOptions infoOptions = new InfoWindowOptions();
        infoOptions.content("<h3>Ulman</h3>")
                .position(new LatLong(32.777157, 35.023131));

        InfoWindow window = new InfoWindow(infoOptions);
        window.open(map, myMarker);
        InfoWindowOptions infoOptions2 = new InfoWindowOptions();
        infoOptions2.content("<h3>Taub</h3>")
                .position(new LatLong(32.778032, 35.023663));

        InfoWindow window2 = new InfoWindow(infoOptions2);
        window2.open(map, myMarker2);
        
        map.fitBounds(new LatLongBounds(center,new LatLong(32.779032, 35.024663)));

        lblCenter.setText((map.getCenter() + ""));
        map.centerProperty().addListener((ObservableValue<? extends LatLong> obs, LatLong o, LatLong n) -> {
            lblCenter.setText((n + ""));
        });
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            lblClick.setText((new LatLong((JSObject) obj.getMember("latLng")) + ""));
        });
        mapTypeCombo.setDisable(false);
        
        mapTypeCombo.getItems().addAll( MapTypeIdEnum.ALL );
        LatLong[] ary = new LatLong[]{markerLatLong, markerLatLong2};
        MVCArray mvc = new MVCArray(ary);

        PolylineOptions polyOpts = new PolylineOptions()
                .path(mvc)
                .strokeColor("red")
                .strokeWeight(2);

        Polyline poly = new Polyline(polyOpts);
        map.addMapShape(poly);
        scene.getWindow().sizeToScene();
    }
	
	
	private void hideMarker() {
		myMarker.setVisible(!myMarker2.getVisible());
		myMarker2.setVisible(!myMarker2.getVisible());
	}
	
	private void deleteMarker() {
		map.removeMarker(myMarker);
		map.removeMarker(myMarker2);
	}
	
    private void checkCenter(LatLong center) {
        System.out.println("Testing fromLatLngToPoint using: " + center);
        Point2D p = map.fromLatLngToPoint(center);
        System.out.println("Testing fromLatLngToPoint result: " + p);
        System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
    }
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }
}