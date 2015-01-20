package course.examples.staticlayout;

import android.app.Activity;
import android.os.Bundle;

import course.examples.staticlayout.TitlesFragment.ListSelectionListener;

public class QuoteViewerActivity extends Activity implements
        ListSelectionListener {

    static String[] sTitleArray;
    static String[] sQuoteArray;
    private QuotesFragment mDetailsFragment;

    @SuppressWarnings("unused")
    private static final String TAG = "QuoteViewerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and quotes
        sTitleArray = getResources().getStringArray(R.array.Titles);
        sQuoteArray = getResources().getStringArray(R.array.Quotes);

        setContentView(R.layout.main);

        // Get a reference to the QuotesFragment
        mDetailsFragment = (QuotesFragment) getFragmentManager()
                .findFragmentById(R.id.details);
    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {
        if (mDetailsFragment.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index
            mDetailsFragment.showQuoteAtIndex(index);
        }
    }
}