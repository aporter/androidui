// This project requires the v13 support library. 
// See http://developer.android.com/tools/support-library/setup.html for more information


package course.examples.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class GalleryWithViewPagerActivity extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);

		// Create a new ImageAdapter (subclass of FragmentStatePagerAdapter)
        ImageAdapter mImageAdapter = new ImageAdapter(getFragmentManager(), this);

		// Set the Adapter on the ViewPager
		mViewPager.setAdapter(mImageAdapter);

	}
}
