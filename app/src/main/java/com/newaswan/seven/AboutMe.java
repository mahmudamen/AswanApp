package com.newaswan.seven;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fadein);
        final Animation ot = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fadeout);
        ImageView imageView=(ImageView)findViewById(R.id.imageView3);



        imageView.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextView textView3 =(TextView)findViewById(R.id.textView3);
                TextView textView4 =(TextView)findViewById(R.id.textView4);
                TextView textView5 =(TextView)findViewById(R.id.textView5);
                Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/alyoum.ttf");
                textView3.setText("هيئة تنمية المجتمعات العمرانية الجديدة ");
                textView4.setText("جهاز مدينة اسوان الجديدة ");
                textView5.setText("مركز المعلومات ");
                textView3.setTypeface(typeface);
                textView4.setTypeface(typeface);
                textView5.setTypeface(typeface);
                ImageView imageView=(ImageView)findViewById(R.id.imageView3);
                TextView me = (TextView)findViewById(R.id.textView6);
                me.setText("  ");
                imageView.startAnimation(ot);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ot.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TextView me = (TextView)findViewById(R.id.textView6);
                Typeface fme = Typeface.createFromAsset(getAssets(),"fonts/truelies.ttf");
                me.setTypeface(fme);
                me.setText("Aswan App ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextView textView3 =(TextView)findViewById(R.id.textView3);
                TextView textView4 =(TextView)findViewById(R.id.textView4);
                TextView textView5 =(TextView)findViewById(R.id.textView5);
                Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/alyoum.ttf");
                textView3.setText("هيئة تنمية المجتمعات العمرانية الجديدة ");
                textView5.setText("جهاز مدينة اسوان الجديدة ");
                textView4.setText("مركز المعلومات ");
                textView3.setTypeface(typeface);
                textView4.setTypeface(typeface);
                textView5.setTypeface(typeface);
                ImageView imageView=(ImageView)findViewById(R.id.imageView3);
                TextView me = (TextView)findViewById(R.id.textView6);
                me.setText("  ");
                imageView.startAnimation(an);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
