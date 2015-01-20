package course.examples.multipane;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements
        TitlesFragment.SelectionListener {

    @SuppressWarnings("unused")
    private static final String TAG = "Lab-Fragments";
    private static final int QUOTE_FRAG_CONTAINER = R.id.fragment_container;
    private static final int FEED_FRAG = R.id.feed_frag;
    private TitlesFragment mTitlesFragment;
    private QuoteFragment mQuoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // If the layout is single-pane, create the FriendsFragment
        // and add it to the Activity

        if (!isInTwoPaneMode()) {
            mTitlesFragment = new TitlesFragment();

            // Add (replace) the TitlesFragment
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            transaction.replace(QUOTE_FRAG_CONTAINER, mTitlesFragment);
            transaction.commit();
        } else {

            // Otherwise, save a reference to the QuoteFragment for later use
            mQuoteFragment = (QuoteFragment) getFragmentManager()
                    .findFragmentById(FEED_FRAG);
        }
    }

    // If there is no fragment_container ID, then the application is in
    // two-pane mode

    boolean isInTwoPaneMode() {
        return findViewById(QUOTE_FRAG_CONTAINER) == null;
    }

    // Display selected quote

    public void onItemSelected(int position) {

        // If there is no QuoteFragment instance, then create one
        if (mQuoteFragment == null)
            mQuoteFragment = new QuoteFragment();

        // If in single-pane mode, replace single visible Fragment
        if (!isInTwoPaneMode()) {

            //Replace the fragment_container with the FeedFragment

            // Get reference to the fragment manager
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();

            // Replace the QuoteFragment
            transaction.replace(QUOTE_FRAG_CONTAINER, mQuoteFragment);

            // Save previous state to the Activity backstack
            transaction.addToBackStack(null);

            // Commit the Fragment Transaction
            transaction.commit();

            // Force execution of the Fragment Transaction
            getFragmentManager().executePendingTransactions();

        }

        // Update QuoteFragment display
        mQuoteFragment.updateFeedDisplay(position);

    }
}
