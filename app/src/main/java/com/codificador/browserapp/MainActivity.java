package com.codificador.browserapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editTextUrl;
    Button buttonGo, buttonBack, buttonForward, buttonRefresh;

    WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        buttonGo = findViewById(R.id.buttonGo);
        buttonBack = findViewById(R.id.buttonBack);
        buttonForward = findViewById(R.id.buttonForward);
        buttonRefresh = findViewById(R.id.buttonRefresh);
        webView = findViewById(R.id.webview);

//        webView.loadUrl("file:///android_asset/index.html");
        webView.loadUrl("http://www.google.com");
//        editTextUrl.setText("http://www.google.com");
        webView.setWebViewClient(new CustomWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading page...");
        progressDialog.show();

//        String summary = "<html><body>You scored <b>192</b> points.</body></html>";
//        webView.loadData(summary, "text/html", null);

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goForward();
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    private void goBack(){
        if(webView.canGoBack())
            webView.goBack();
    }

    private void goForward(){
        if(webView.canGoForward())
            webView.goForward();
    }

    private void go(){
        String strUrl = editTextUrl.getText().toString();
        if(strUrl != null && !strUrl.equals(""))
            webView.loadUrl(strUrl);
    }

    private void refresh(){
        webView.reload();
    }

    class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
            view.loadUrl(url);
            editTextUrl.setText(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }
}
