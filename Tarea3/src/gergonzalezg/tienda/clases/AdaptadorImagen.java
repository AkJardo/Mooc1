package gergonzalezg.tienda.clases;

import es.gergonzalezg.tarea2.R;
import gergonzalezg.tienda.actividades.MainActivity;
import gergonzalezg.tienda.image.BitmapLRUCache;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class AdaptadorImagen extends BaseAdapter
{


	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private ArrayList<Photo> dataArray;

	public AdaptadorImagen(Context context, ArrayList<Photo> dataArray) {
		this.dataArray = dataArray;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = new ImageLoader(MainActivity.requestQueue, new BitmapLRUCache());        
	}

	@Override
	public int getCount() {
		
		return dataArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		Photo currentImage = dataArray.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.shop_list_item, null);

			holder = new ViewHolder();
			holder.imgCommunity = (NetworkImageView) convertView.findViewById(R.id.imgCommunity);
			holder.txtCommunity = (TextView) convertView.findViewById(R.id.txtCommunity);


			convertView.setTag(holder);	        
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.imgCommunity.setImageUrl(currentImage.getURL(), imageLoader);
		holder.txtCommunity.setText(currentImage.getDescripcion());
		return convertView;

	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}	

	private static class ViewHolder{

		NetworkImageView imgCommunity;
		TextView txtCommunity;

	}
}