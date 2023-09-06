package com.meritumads.settings;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.meritumads.databinding.WebviewSponsorDialogBinding;

public class WebViewSponsorActivity extends Activity {

    private Bundle bundle;
    private String mainLink = "";
    private WebviewSponsorDialogBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WebviewSponsorDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bundle = getIntent().getExtras();
        if(bundle!=null){
            mainLink = bundle.getString("main_link");
        }
        setupWebView();


        binding.back.setColorFilter(Color.parseColor(MsAdsSdk.getInstance().getArrowBackColor()));
        binding.header.setBackgroundColor(Color.parseColor(MsAdsSdk.getInstance().getActionBarColor()));

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupWebView() {

        binding.webview.setWebViewClient(new MyWebViewClient());
        binding.webview.setWebChromeClient(new ChromeClient());
        binding.webview.getSettings().setUseWideViewPort(true);
        binding.webview.getSettings().setLoadWithOverviewMode(true);
        binding.webview.getSettings().setDomStorageEnabled(true);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webview.loadUrl(mainLink);

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(mainLink);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(this!=null) {
                binding.progressBar.setVisibility(View.GONE);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if(Util.isNetworkConnected(view.getContext()) == false) {
                hideErrorPage(view);
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if(Util.isNetworkConnected(view.getContext()) == false) {
                hideErrorPage(view);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        private void hideErrorPage(WebView view) {
            // Here we configurating our custom error page
            // It will be blank
            String customErrorPageHtml = "<html>"+"No Internet Connection"+"</html>";
            view.loadData(customErrorPageHtml, "text/html; charset=utf-8", "UTF-8");
        }
    }

    private class ChromeClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            super.onPermissionRequest(request);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.getResources());
                    }
                }
            });
        }
    }
}
