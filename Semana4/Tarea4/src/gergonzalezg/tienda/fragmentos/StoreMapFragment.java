package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea4.R;
import gergonzalezg.tienda.actividades.DetailActivity;
import gergonzalezg.tienda.actividades.MainActivity;
import gergonzalezg.tienda.app.App;
import gergonzalezg.tienda.clases.Shop;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreMapFragment extends SupportMapFragment
							  implements OnConnectionFailedListener,ConnectionCallbacks,InfoWindowAdapter{
	
	private List<Shop> tiendas;
	private HashMap<Marker, Shop> markerPlacesMap = new HashMap<Marker, Shop>();
	private GoogleMap map;
	private Bundle savedInstanceState;
	LocationClient locationClient;
	public static final int MAP_NORMAL=0;
	public static final int MAP_HYBRID=1;
	public static final int MAP_SATELLITE=2;
	public static final int MAP_TERRAIN=3;
	
	
	
	
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        locationClient= new LocationClient(MainActivity.contexto,this,this);
        tiendas=((App)getActivity().getApplicationContext()).getTiendas();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        setupMap();
        addLugar();
    }	
    
    
    
    public void setupMap() {
    	if (map == null) {
    		map = getMap();
            if (map != null) {
            	if (savedInstanceState == null) {
            		if (locationClient.isConnected())
            		{
            			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationClient.getLastLocation().getLatitude(), locationClient.getLastLocation().getLatitude()), 12));
            		}
            	}
            	map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					
					@Override
					public void onInfoWindowClick(Marker marker) {
						
						Shop tiendaSeleccionada=(Shop) markerPlacesMap.get(marker);		
						Intent intent=new Intent(getActivity(), DetailActivity.class);
						intent.putExtra("tienda",tiendaSeleccionada);
						startActivity(intent);					
					}
				});
            	map.setInfoWindowAdapter(this);
                map.getUiSettings().setZoomControlsEnabled(false);
            }
        }    	
    }


    public void reloadPosition() {
    	if (map != null) {

    		if (locationClient.isConnected())
    		{
    			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationClient.getLastLocation().getLatitude(), locationClient.getLastLocation().getLongitude()), 12));
    			map.setMyLocationEnabled(true);
    			
    			
    		}
    	}

    	map.getUiSettings().setZoomControlsEnabled(false);
    }
      
    public void reloadMapMode(int modoMapa) {
    	if (map != null) {
    		switch(modoMapa){
    		case MAP_NORMAL:
    			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    			break;
    		case MAP_HYBRID:
    			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    			break;
    		case MAP_SATELLITE:
    			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    			break;
    		case MAP_TERRAIN:
    			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    			break;
    		}

    	}

    	
    }
   
    
    
	@Override
	public void onStart() {
		super.onStart();
		locationClient.connect();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		locationClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		reloadPosition();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	public void addLugar()
	{

		if (tiendas.size()>0){
			
			MarkerOptions markerOptions;
		
			
			for(int i=0;i<tiendas.size();i++){

				markerOptions = new MarkerOptions();
				markerOptions.title(tiendas.get(i).getNombre());
				markerOptions.snippet(getString(R.string.infoSnippet, tiendas.get(i).getActividad(),tiendas.get(i).getTelefono()));
				LatLng position = new LatLng(tiendas.get(i).getLocalizacion().getLatitude(),tiendas.get(i).getLocalizacion().getLongitude());
				markerOptions.position(position);
				if (map!=null){
					markerPlacesMap.put(map.addMarker(markerOptions),tiendas.get(i));
				}
				
			}
		}

	}

	@Override
	public View getInfoContents(Marker marker) {
		
		View window = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.info_window_shop, null);
        
        TextView txt_title = (TextView)window.findViewById(R.id.txtInfoTitle);
        TextView txt_snippet = (TextView)window.findViewById(R.id.txtInfoSnippet);
        
        txt_title.setText(marker.getTitle());
        txt_snippet.setText(marker.getSnippet());        
        
        return window;
		
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}
	
	
	
}
