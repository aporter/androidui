package course.examples.staticconfiglayout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuotesFragment extends Fragment {

    @SuppressWarnings("unused")
    private static final String TAG = "QuotesFragment";

    private TextView mQuoteView;
    private int mCurrIdx = -1;
    private int mQuoteArrayLen = 0;

    public int getShownIndex() {
        return mCurrIdx;
    }

    // Show the Quote string at position newIndex
    public void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mQuoteArrayLen)
            return;
        mCurrIdx = newIndex;
        mQuoteView.setText(QuoteViewerActivity.mQuoteArray[mCurrIdx]);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment across Activity reconfigurations
        setRetainInstance(true);

    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout defined in quote_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        mQuoteView = (TextView) inflater.inflate(R.layout.quote_fragment, container, false);
        return mQuoteView;
    }

    // Set up some information about the mQuoteView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQuoteArrayLen = QuoteViewerActivity.mQuoteArray.length;
        showQuoteAtIndex(mCurrIdx);
    }
}
