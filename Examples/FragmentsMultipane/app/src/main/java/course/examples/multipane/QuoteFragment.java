package course.examples.multipane;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuoteFragment extends Fragment {

    @SuppressWarnings("unused")
    private static final String TAG = "Lab-Fragments";
    private static final List<String> mQuotes = new ArrayList<>();
    private TextView mQuoteView;
    private int mCurrIdx = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment across Activity reconfigurations
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mQuoteView = (TextView) inflater.inflate(R.layout.quote, container, false);
        return mQuoteView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Read in all quotes
        if (mQuotes.size() == 0) {
            Collections.addAll(mQuotes, getResources().getStringArray(R.array.Quotes));
        }

        // Update Display with currently selected quote
        updateFeedDisplay(mCurrIdx);
    }

    // Display the Quote at position
    void updateFeedDisplay(int position) {
        if (position < 0 || position >= mQuotes.size()) {
            return;
        }
        mCurrIdx = position;
        mQuoteView.setText(mQuotes.get(position));
    }

}
