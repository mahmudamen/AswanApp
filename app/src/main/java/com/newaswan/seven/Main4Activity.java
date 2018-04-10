package com.newaswan.seven;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        String URL = "https://goo.gl/6pP6CJ";
        WebView view =(WebView) this.findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(URL);
        Toast toast = Toast.makeText(this,"الصفحة الرسمية للمدينة ",Toast.LENGTH_LONG);
        toast.show();
        FloatingActionButton fabex = (FloatingActionButton)findViewById(R.id.fabexit);

        fabex.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                finish();

            }

        });
    }
}
