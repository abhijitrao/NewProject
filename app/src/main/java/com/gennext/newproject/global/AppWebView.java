package com.gennext.newproject.global;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gennext.newproject.R;
import com.gennext.newproject.util.ApiCallError;


public class AppWebView extends CompactFragment implements ApiCallError.ErrorListener{
	String BrowserSetting = "";
	Activity context;
	WebView webView;
	ProgressBar progressBar;
	FragmentManager mannager;
	String webUrl;
	int URLTYPE=0;
	String browserName;
	public static int URL=0, PDF=1;



	public static AppWebView newInstance(String title, String url, int urlType) {
		AppWebView fragment=new AppWebView();
		fragment.browserName=title;
		fragment.webUrl=url;
		fragment.URLTYPE=urlType;
		return fragment;
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.app_web_view, container, false);
		mannager = getFragmentManager();
		setActionBarOption(getActivity(),v);
		initUi(v);
		return v;
	}


	private void initUi(View v) {
		// TODO Auto-generated method stub
		webView = (WebView) v.findViewById(R.id.webView_browser);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);

		webView.setInitialScale(0);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				progressBar.setProgress(progress);
				if (progress == 100) {
					progressBar.setVisibility(View.GONE);
				} else {
					progressBar.setVisibility(View.VISIBLE);
				}
			}
		});
		// Javascript inabled on webview

		if (isOnline() == true) {
			
			if(URLTYPE==URL){
				startWebView(webUrl);
			}else{
			    String Doc="<iframe src='http://docs.google.com/gview?embedded=true&url="+webUrl+"' width='100%' height='100%' style='border: none;'></iframe>";
			    startWebView(Doc);
			}
		} else {
			AlertInternet();
		}

	}

	@Override
	public void onErrorRetryClick(DialogFragment dialog) {
		startWebView(webUrl);
	}

	@Override
	public void onErrorCancelClick(DialogFragment dialog) {

	}



	public class myWebclient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

	}

	private void startWebView(String url) {
		webView.setWebViewClient(new WebViewClient() {
			
			// If you will not use this method url links are opeen in new brower
			// not in webview
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(URLTYPE==URL){
					view.loadUrl(url);
		        }else{
					view.loadData(url, "text/html", "UTF-8");
				}
				return true;
			}

			// Show loader on url load
			public void onLoadResource(WebView view, String url) {

			}

			public void onPageFinished(WebView view, String url) {

			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
//				Toast.makeText(getActivity(), "Your Internet Connection May not be active Or " + errorCode , Toast.LENGTH_LONG).show();
//				hideProgressDialog();
				ApiCallError.newInstance(description+"\n"+errorCode,AppWebView.this);
			}

		});
		
		

		// Javascript inabled on webview
		webView.getSettings().setJavaScriptEnabled(true);

		// Other webview options 
		webView.getSettings().setAllowFileAccess(true);
	    webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		//webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		//webView.setScrollbarFadingEnabled(false); 
		if(URLTYPE==URL){
			webView.loadUrl(url);
        }else{
    		webView.loadData( url , "text/html", "UTF-8");
		}
	}

	
	public void setActionBarOption(final Activity act, View v) {

		Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
		toolbar.setTitle(browserName);
		AppCompatActivity activity = (AppCompatActivity) act;
		activity.setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.mipmap.ic_back);
		toolbar.setNavigationOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						hideKeybord(act);
						if (webView.canGoBack()) {
							webView.goBack();
						} else {
							// Let the system handle the back button
							mannager.popBackStack();
						}
					}
				}

		);
	}
	
	// Detect when the back button is pressed
	public void onBackPressed() {

		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			// Let the system handle the back button
			mannager.popBackStack();
		}
	}

	public void AlertInternet() {

		new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Not Connected!").setMessage("Please Check Your Internet Connection.")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				}).show();
		// Toast.makeText(getApplicationContext(),"No Internet Connection
		// found...",Toast.LENGTH_LONG).show();

	}

}

