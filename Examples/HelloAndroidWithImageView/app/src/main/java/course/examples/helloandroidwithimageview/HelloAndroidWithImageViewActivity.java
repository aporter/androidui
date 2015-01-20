package course.examples.helloandroidwithimageview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class HelloAndroidWithImageViewActivity extends Activity {

    // Text shadow radius
    private static final float RADIUS = 16f;

    // Text shadow x offset
    private static final float DX = 12f;

    // Text shadow y offset
    private static final float DY = 32f;

    // Text shadow color
    private static final int COLOR = Color.GRAY;

    // Image rotation in degrees
    private static final float ROTATION = 45.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the Activity's user interface
        setContentView(R.layout.activity_hello_android_with_image_view);

        // Get a reference to the android image
        ImageView androidImage = (ImageView) findViewById(R.id.android);

        // Rotate androidImage by 45 degrees
        androidImage.setRotation(ROTATION);

        // Get a reference to the hello world text.
        TextView helloText = (TextView) findViewById(R.id.hello_text);

        // Set the text
        helloText.setText(R.string.hello_world);

        // Add a shadow to the TextView
        helloText.setShadowLayer(RADIUS, DX, DY, COLOR);

    }
}
