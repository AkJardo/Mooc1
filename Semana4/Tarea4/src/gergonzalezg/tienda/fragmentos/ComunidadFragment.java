package gergonzalezg.tienda.fragmentos;

import es.gergonzalezg.tarea4.R;
import gergonzalezg.tienda.actividades.MainActivity;
import gergonzalezg.tienda.app.App;
import gergonzalezg.tienda.clases.AdaptadorImagen;
import gergonzalezg.tienda.clases.Photo;
import gergonzalezg.tienda.fragmentos.DialogFotoSeleccionFragment.NoticeDialogListener;
import gergonzalezg.tienda.image.Helper;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ComunidadFragment extends Fragment 
implements NoticeDialogListener,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int LOAD_IMAGE=1;
	private static final int TAKE_PHOTO=2;
	private gergonzalezg.tienda.data.DBAdapter db; 
	AdaptadorImagen adapter;
	ArrayList<Photo> imagesArray;
	ImageButton btnFoto;
	private String photoPath;


	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db=((App)getActivity().getApplicationContext()).getDB();
		imagesArray = ((App)getActivity().getApplicationContext()).getImagesArray();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		outState.putString("rutafoto", photoPath);
		
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);


		if (savedInstanceState!=null){
			photoPath=savedInstanceState.getString("rutafoto");
		}
		
		
		
		adapter = new AdaptadorImagen(getActivity(), imagesArray);


		ListView listView = (ListView) getActivity().findViewById(R.id.listCommunity);
		btnFoto = (ImageButton) getActivity().findViewById(R.id.btnFoto);
		btnFoto.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View button, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((ImageView) button).setColorFilter(Color.argb(150, 155, 155, 155));
					//Mostramos el Dialogo
					DialogFotoSeleccionFragment dialogo = DialogFotoSeleccionFragment.getInstance(ComunidadFragment.this);
					/*Tenemos el dialogo y le hemos pasado nuestro fragmento, que implementa la interfaz de comunicación
			            Ahora desde el dialogo ponemos la opcion escogida 
			            -CAMERA
			            -GALLERY
			            y el resultado lo manejamos en nuestro evento de la interfaz
					 */
					dialogo.show(getActivity().getSupportFragmentManager(), "");

					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					((ImageView) button).setColorFilter(Color.argb(0, 155, 155, 155)); // or null
					return true;
				}

				return false;
			}
		});

		listView.setAdapter(adapter);	

		
		//Si no hay fotos guardadas en DB las cargamos mediante el API de instagram la primera vez
		//En otro caso recuperamos las de la DB
		if (db.getTotalPhotosinDatabase()>0){
			imagesArray=(ArrayList<Photo>) db.getPhotos();
			adapter.notifyDataSetChanged();
		}else{
			APICall();
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View vista=inflater.inflate(R.layout.fragment_comunidad, null);


		return vista;
	}



	public void APICall() {
		String url = Helper.getRecentUrl("shop");


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
							JSONObject caption = currentElement.getJSONObject("caption");

							JSONObject standardResolution = images.getJSONObject("standard_resolution");

							String titulo = caption.getString("text");
							String imgUrl = standardResolution.getString("url");
							String userName = user.getString("username");


							Photo image = new Photo();
							image.setURL(imgUrl);
							image.setDescripcion(titulo);
							image.setUsuario(userName);

							imagesArray.add(image);
							int idPhoto = db.getTotalPhotosinDatabase();
							image.setId(idPhoto);
							db.insertPhoto(image);
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

	public void fromCamera () {

		NetworkImageView imageView = (NetworkImageView) getActivity().findViewById(R.id.imgCommunity);

		Bitmap bitmap = resizeBitmap(imageView.getWidth(), imageView.getHeight());


		imageView.setImageBitmap(bitmap);

		Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(photoPath);
		Uri contentUri = Uri.fromFile(f);

		Photo newPhoto = new Photo();
		newPhoto.setURI(contentUri.getPath());

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", 
				Locale.getDefault())
		.format(Calendar.getInstance().getTime());

		newPhoto.setDescripcion("Descripción imagen: " + timeStamp);

		imagesArray.add(0,newPhoto);
		int idPhoto = db.getTotalPhotosinDatabase();
		newPhoto.setId(idPhoto);
		db.insertPhoto(newPhoto);
		
		newPhoto.enviarParse().saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(ComunidadFragment.this.getActivity())
				        .setSmallIcon(R.drawable.ic_launcher)
				        .setContentTitle("Imagen de la cámara")
				        .setContentText("Imagen de la cámara subida a Parse.com");
				
				NotificationManager mNotificationManager =
					    (NotificationManager) ComunidadFragment.this.getActivity().getSystemService(ComunidadFragment.this.getActivity().NOTIFICATION_SERVICE);
					// mId allows you to update the notification later on.
					mNotificationManager.notify(0, mBuilder.build());

				
			}
		});

		mediaScanIntent.setData(contentUri);
		getActivity().sendBroadcast(mediaScanIntent);
		
		
		
		adapter.notifyDataSetChanged();
	}

	public void fromGallery(Intent data) {
		if (data != null) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Photo newPhoto = new Photo();

			newPhoto.setURI(picturePath);

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", 
					Locale.getDefault())
			.format(Calendar.getInstance().getTime());

			newPhoto.setDescripcion("Descripción imagen: " + timeStamp);

			imagesArray.add(0,newPhoto);
			int idPhoto = db.getTotalPhotosinDatabase();
			newPhoto.setId(idPhoto);
			db.insertPhoto(newPhoto);
			
			newPhoto.enviarParse().saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException arg0) {
					NotificationCompat.Builder mBuilder =
					        new NotificationCompat.Builder(ComunidadFragment.this.getActivity())
					        .setSmallIcon(R.drawable.ic_launcher)
					        .setContentTitle("Imagen de la cámara")
					        .setContentText("Imagen de la cámara subida a Parse.com");
					
					NotificationManager mNotificationManager =
						    (NotificationManager) ComunidadFragment.this.getActivity().getSystemService(ComunidadFragment.this.getActivity().NOTIFICATION_SERVICE);
						// mId allows you to update the notification later on.
						mNotificationManager.notify(0, mBuilder.build());

					
				}
			});
			adapter.notifyDataSetChanged();
			
			
			//ImageView imageView = (ImageView) findViewById(R.id.img);
			//imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		}
	}

	public Bitmap resizeBitmap(int targetW, int targetH) {
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		return BitmapFactory.decodeFile(photoPath, bmOptions);    	
	}

	public File setUpFile() {
		File albumDir;
		String albumName = "ejemplo";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			albumDir = new File(
					Environment.getExternalStoragePublicDirectory(
							Environment.DIRECTORY_PICTURES
							), 
							albumName
					);		
		} else {
			albumDir = new File (
					Environment.getExternalStorageDirectory()
					+ "/dcim/"
					+ albumName);				
		}

		albumDir.mkdirs();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", 
				Locale.getDefault())
		.format(Calendar.getInstance().getTime());
		String imageFileName = "IMG_" + timeStamp + ".jpg";
		File imageF = new File(albumDir + "/" + imageFileName);
		return imageF;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case LOAD_IMAGE:
			if (resultCode ==Activity.RESULT_OK) {
				fromGallery(data);
			}
			break;
		case TAKE_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				fromCamera();
			}        		
			break;
		}
	}	

	@Override
	public void onItemSelected(int source) {

		int code=0;
		Intent intent = new Intent();

		//Dependiendo de si es camara o galería
		switch (source){
		case DialogFotoSeleccionFragment.CAMERA:
			code = TAKE_PHOTO;
			intent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			File photo = setUpFile();
			photoPath = photo.getAbsolutePath();
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
			break;
			
		case DialogFotoSeleccionFragment.GALLERY:
			code = LOAD_IMAGE;
			intent = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						
			break;
		}

		startActivityForResult(intent, code);

	}
}


