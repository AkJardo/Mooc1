package es.demo.demo1;

import es.demo.demo1.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class CountryInfoFragment extends Fragment {
	WebView webView;
	TextView Texto;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	
		View view = inflater.inflate(R.layout.fragment_country_info, container, false);
		webView = (WebView)view.findViewById(R.id.webView);
		Texto = (TextView) view.findViewById(R.id.textView1);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);	
		
		Activity actividad = getActivity();
	
		if (actividad instanceof CountryDetailActivity )
		{
			String country = ((CountryDetailActivity)getActivity()).getCountrySelected();
			loadWebViewCountry(country);
					
		}
	}
	
	public void loadWebViewCountry(String country){
		webView.loadUrl("http://es.m.wikipedia.org/wiki/" + country);
		webView.setWebViewClient(new WebViewClient(){
			@Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        return true;
		    }			
		});
	}
	}

