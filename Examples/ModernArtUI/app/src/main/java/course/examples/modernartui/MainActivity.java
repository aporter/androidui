package course.examples.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

	private ImageView mLeftOne, mLeftTwo, mRightOne, mRightThree;

    private static final int MAX_TINT = 150;
    private static final int MAX_COLOR_COMP = 255;
    private static final String VISIT_MOMA_URL = "http://www.moma.org";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupTintControl();
		initViews();

	}

	// Set up the SeekBar the manages the tint level
	private void setupTintControl() {

        SeekBar tintControl = (SeekBar) findViewById(R.id.slider);
        tintControl.setMax(MAX_TINT);
        tintControl.setProgress(0);

		// Set an OnSeekBarChangeListener on the
        tintControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            static final int MAX_THREADS = 4;

            // Use a ThreadPoolExecutor to handle the work of responding to tint
            // control. Using the DiscardOldestPolicy to throw away oldest work
            // requests if too many jobs are scheduled.

            final ThreadPoolExecutor executor = new ThreadPoolExecutor(MAX_THREADS,
                    MAX_THREADS, Long.MAX_VALUE, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(MAX_THREADS),
                    new ThreadPoolExecutor.DiscardOldestPolicy());

            public void onProgressChanged(SeekBar seekBar, final int progress,
                                          boolean fromUser) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        updateAllViews(progress);
                    }
                });
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not implemented
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not implemented
            }
        });

	}

	// Update all Views
    void updateAllViews(int alpha) {
        updateView(mLeftOne, alpha);
		updateView(mLeftTwo, alpha);
		updateView(mRightOne, alpha);
		updateView(mRightThree, alpha);
	}

	// Create a Runnable to execute on the UI Thread
	// When run sets a new color filter on the specified ImageView
	private void updateView(final ImageView image, final int newAlpha) {
		runOnUiThread(new Runnable() {
			public void run() {
				image.setColorFilter(
						Color.argb(newAlpha, MAX_COLOR_COMP, MAX_COLOR_COMP, 0),
						PorterDuff.Mode.ADD);
			}
		});
	}

	// Get references to required Views
	private void initViews() {
		mLeftOne = (ImageView) findViewById(R.id.left_one);
		mLeftTwo = (ImageView) findViewById(R.id.left_two);
		mRightOne = (ImageView) findViewById(R.id.right_one);
		mRightThree = (ImageView) findViewById(R.id.right_three);
	}

	// Create an information PopupWindow
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	// Respond to menu item click
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.more_info) {
			showDialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	void showDialog() {
		DialogFragment newFragment = MyAlertDialogFragment.newInstance();
		newFragment.show(getFragmentManager(), "dialog");
	}

	public static class MyAlertDialogFragment extends DialogFragment {

		public static MyAlertDialogFragment newInstance() {
			return new MyAlertDialogFragment();
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Inflate the popup_layout.xml
			LayoutInflater layoutInflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View myView = layoutInflater.inflate(R.layout.more_info_view, null);

			// Attach listener to "Not Now" Button
			Button dismissButton = (Button) myView
					.findViewById(R.id.dismiss_button);
			dismissButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});

			// Visit MOMA button takes the user to the URL stored in
			// VISIT_MOMA_URL
			Button momaButton = (Button) myView
					.findViewById(R.id.visit_moma_button);
			momaButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse(VISIT_MOMA_URL)));
					dismiss();
				}
			});

			return new AlertDialog.Builder(getActivity()).setView(myView)
					.create();
		}
	}
}
