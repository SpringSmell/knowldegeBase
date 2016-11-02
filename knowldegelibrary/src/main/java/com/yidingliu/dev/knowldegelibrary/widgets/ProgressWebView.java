package com.yidingliu.dev.knowldegelibrary.widgets;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.tools.FormatUtils;


/**
 * 带进度条的WebView
 * 
 */
public class ProgressWebView extends WebView {
	private ProgressBar progressbar;

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT, FormatUtils
				.dip2px ( context, 2 ),
		                                              0, 0));
		progressbar.setProgressDrawable (
				ContextCompat.getDrawable ( context, R.drawable.layer_list_progress_bar ) );
		addView(progressbar);
		setWebViewClient(new MyWebViewClient());
		setWebChromeClient(new MyWebChromeClient());
	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
				progressbar.postInvalidate();
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	private class MyWebViewClient extends WebViewClient {
		
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			progressbar.setVisibility(View.GONE);
		}
	}
}
