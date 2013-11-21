package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.actividades.MainActivity;
import gergonzalezg.tienda.clases.AdaptadorImagen;
import gergonzalezg.tienda.clases.Photo;
import gergonzalezg.tienda.image.Helper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ComunidadFragment extends Fragment {

	AdaptadorImagen adapter;
	ArrayList<Photo> imagesArray;
	ImageButton btnFoto;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		
		imagesArray = new ArrayList<Photo>();
		adapter = new AdaptadorImagen(getActivity(), imagesArray);
		
	
	    ListView listView = (ListView) getActivity().findViewById(R.id.listCommunity);
	    btnFoto = (ImageButton) getActivity().findViewById(R.id.btnFoto);
	    btnFoto.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View button, MotionEvent event) {
				  if (event.getAction() == MotionEvent.ACTION_DOWN) {
			            ((ImageView) button).setColorFilter(Color.argb(150, 155, 155, 155));
			            //Mostramos el AlertDialog
			            new FotoSeleccionFragment().show(getFragmentManager(), "");
			            return true;
			        } else if (event.getAction() == MotionEvent.ACTION_UP) {
			            ((ImageView) button).setColorFilter(Color.argb(0, 155, 155, 155)); // or null
			            return true;
			        }
			        
				return false;
			}
		});
		
	    listView.setAdapter(adapter);	
	    
	    APICall();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View vista=inflater.inflate(R.layout.fragment_comunidad, null);
		
		
		return vista;
	}
	
	

	public void APICall() {
		String url = Helper.getRecentUrl("landscape");
		
		
	    Response.Listener<JSONObject> successListener = 
	    		new Response.Listener<JSONObject>() {
		            @Override
		            public void onResponse(JSONObject response) {		            	
		            	
		            	JSONArray data;
						try {
							data = response.getJSONArray("data");
			            	for (int i = 0; i < data.length(); i++) {
			            		JSONObject currentElement = data.getJSONObject(i);
			            		String type = currentElement.getString("type");
			            		if (type.equals("image")) {
			            			JSONObject user = currentElement.getJSONObject("user");
			            			JSONObject images = currentElement.getJSONObject("images");
			            			JSONObject standardResolution = images.getJSONObject("standard_resolution");
			            			
			            			String imgUrl = standardResolution.getString("url");
			            			String userName = user.getString("username");
			            			
			            			Photo image = new Photo();
			            			image.setURL(imgUrl);
			            			imagesArray.add(image);
			            		}
			            	}	
			            	adapter.notifyDataSetChanged();


						} catch (JSONException e) {
							Log.e("ERROR",Log.getStackTraceString(e));
						}
	
						
		            }
	    };
	
	    
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, 
															   url, 
															   null, 
															   successListener,
															   null);		
		MainActivity.requestQueue.add(jsObjRequest);		
	}
	
	
}


