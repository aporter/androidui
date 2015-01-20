package course.examples.viewpager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

// Manages Fragments holding ImageViews
class ImageAdapter extends FragmentStatePagerAdapter {

    private final String[] mNames;

    // List of IDs corresponding to the images
    private final Integer[] mImageIds = {R.drawable.sample_1, R.drawable.sample_2,
            R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7};

    public ImageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mNames = context.getResources().getStringArray(R.array.dog_names);
    }

    @Override
    public Fragment getItem(int i) {

        // Create new Fragment
        Fragment fragment = new ImageHolderFragment();

        // Pass in arguments
        Bundle args = new Bundle();
        args.putInt(ImageHolderFragment.RES_ID, mImageIds[i]);
        args.putString(ImageHolderFragment.NAME, mNames[i]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

}