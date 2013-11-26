package gergonzalezg.tienda.fragmentos;

import gergonzalezg.tienda.actividades.MainActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;

public class StoreMapFragment extends SupportMapFragment
							  implements OnConnectionFailedListener,ConnectionCallbacks{
	private GoogleMap map;
	private Bundle savedInstanceState;
	LocationClient locationClient;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        locationClient= new LocationClient(MainActivity.contexto,this,this);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        setupMap();
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
}
