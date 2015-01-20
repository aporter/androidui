package course.examples.helloandroidwithlogin;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class HelloAndroidWithImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the Activity's user interface
        setContentView(R.layout.activity_hello_android_with_image_view);

        // Get a reference to the android image
        ImageView androidImage = (ImageView) findViewById(R.id.android);
        androidImage.setRotation(45.0f);

        // Get a reference to the hello world text.
        TextView helloText = (TextView) findViewById(R.id.hello_text);
        helloText.setText(R.string.hello_world);
        helloText.setShadowLayer(16f,12f,32f, Color.GRAY);
    }
}
