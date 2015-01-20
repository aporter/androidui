package course.examples.modernartpiano;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

    private AudioManager mAudioManager;
    private SoundPool mSoundPool;
    private final int[] mSoundIDs = new int[MAX_SOUNDS];
    private float mPitch = 1.0f;
    private boolean mCanPlay;
    private MenuItem mRaisePitchMenuItem;
    private MenuItem mLowerPitchMenuItem;

    private static final int MAX_SOUNDS = 5;

    @SuppressWarnings("unused")
    private static final String TAG = "MainActivity";
    private static int sNumLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the Audio Manager
        mAudioManager = (AudioManager) this
                .getSystemService(Context.AUDIO_SERVICE);

        // Set up volume control Seekbar
        setupVolumeControl();

        // Set up actions associated with each View
        initViews();

    }

    private void setupVolumeControl() {

        final SeekBar volumeControl = (SeekBar) findViewById(R.id.volume_bar);

        // Set control range 1..Max
        volumeControl.setMax(mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        // Set the current position of the SeekBar slider
        volumeControl.setProgress(mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));

        // Set a listener for changes to the SeekBar slider
        volumeControl
                .setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {

                        // Change the volume level
                        mAudioManager.setStreamVolume(
                                AudioManager.STREAM_MUSIC, progress, 0);
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Not implemented
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Not implemented
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build())
                .setMaxStreams(MAX_SOUNDS).build();

        // Set a listener for completion of sound loading
        mSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                sNumLoaded++;
                if (sNumLoaded == MAX_SOUNDS) {
                    mCanPlay = true;
                }
            }

        });

        sNumLoaded = 0;

        // Load sounds
        mSoundIDs[0] = mSoundPool.load(this, R.raw.c, 1);
        mSoundIDs[1] = mSoundPool.load(this, R.raw.d, 1);
        mSoundIDs[2] = mSoundPool.load(this, R.raw.e, 1);
        mSoundIDs[3] = mSoundPool.load(this, R.raw.applause, 1);
        mSoundIDs[4] = mSoundPool.load(this, R.raw.g, 1);

    }

    @Override
    protected void onPause() {
        mSoundPool.release();
        mSoundPool = null;
        mCanPlay = false;
        super.onPause();
    }

    private void initViews() {
        ImageButton mIndigo = (ImageButton) findViewById(R.id.indigo);
        mIndigo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick();
            }

            private void performClick() {
                if (mCanPlay) {
                    playSoundNumber(0);
                }
            }
        });

        ImageButton mPink = (ImageButton) findViewById(R.id.pink);
        mPink.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                performClick();
            }

            private void performClick() {
                if (mCanPlay) {
                    playSoundNumber(1);
                }
            }
        });

        ImageButton mWhite1 = (ImageButton) findViewById(R.id.white1);
        mWhite1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                performClick();
            }

            private void performClick() {
                if (mCanPlay) {
                    playSoundNumber(2);
                }
            }
        });

        ImageButton mWhite2 = (ImageButton) findViewById(R.id.white2);
        mWhite2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                performClick();
            }

            private void performClick() {
                if (mCanPlay) {
                    playSoundNumber(3);
                }
            }
        });

        ImageButton mWhite3 = (ImageButton) findViewById(R.id.white3);
        mWhite3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                performClick();
            }

            private void performClick() {
                if (mCanPlay) {
                    playSoundNumber(4);
                }
            }
        });
    }

    void playSoundNumber(int number) {
        mSoundPool.play(mSoundIDs[number], 1, 1, 1, 0, mPitch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        mRaisePitchMenuItem = menu.getItem(0);
        mLowerPitchMenuItem = menu.getItem(1);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mCanPlay) {
            switch (item.getItemId()) {
                case R.id.raise_pitch:
                    if (mPitch > 0.9) {
                        mPitch = 1.7f;
                        enableRaisePitchMenuItem(false);
                    } else {
                        enableLowerPitchMenuItem(true);
                        mPitch += 0.7;
                    }
                    break;
                case R.id.lower_pitch:
                    if (mPitch < 1.1) {
                        mPitch = 0.3f;
                        enableLowerPitchMenuItem(false);
                    } else {
                        enableRaisePitchMenuItem(true);
                        mPitch -= 0.7;
                    }
                    break;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableLowerPitchMenuItem(boolean b) {
        if (null != mLowerPitchMenuItem) {
            mLowerPitchMenuItem.setEnabled(b);
        }
    }

    private void enableRaisePitchMenuItem(boolean b) {
        if (null != mRaisePitchMenuItem) {
            mRaisePitchMenuItem.setEnabled(b);
        }
    }
}
