package org.androidtown.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Animation flowAnim;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        flowAnim = AnimationUtils.loadAnimation(this, R.anim.flow);

        textView1=(TextView)findViewById(R.id.textView1);
    }

    public void onButton1Clicked(View v){
        flowAnim.setAnimationListener(new FlowAnimationListener());
        textView1.startAnimation(flowAnim);
    }

    private final class FlowAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Toast.makeText(getApplicationContext(), "애니메이션 종료됨.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}

