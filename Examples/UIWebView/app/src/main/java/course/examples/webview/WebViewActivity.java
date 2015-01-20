package course.examples.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    private static final String URL = "http://www.pearsonhighered.com";

    private WebView mWebView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mWebView = (WebView) findViewById(R.id.webview);

		// Set a kind of listener on the WebView so the WebView can intercept
		// URL loading requests if it wants to

		mWebView.setWebViewClient(new HelloWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);

        // Load the web page
        mWebView.loadUrl(URL);
	}

    // Respond to a click on the Back key
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class HelloWebViewClient extends WebViewClient {

		// Give application a chance to catch additional URL loading requests
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}