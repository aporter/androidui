package course.examples.fragmentactionbar;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import course.examples.fragmentactionbar.TitlesFragment.ListSelectionListener;

public class QuoteViewerActivity extends Activity implements ListSelectionListener {

    public static String[] TitleArray;
    public static String[] QuoteArray;
    private final TitlesFragment mTitlesFragment = new TitlesFragment();
    private final QuoteFragment mDetailsFragment = new QuoteFragment();
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and quotes
        TitleArray = getResources().getStringArray(R.array.Titles);
        QuoteArray = getResources().getStringArray(R.array.Quotes);

        setContentView(R.layout.main);

        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.replace(R.id.title_fragment_container, mTitlesFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {

        // If the QuoteFragment has not been added, add it now
        if (!mDetailsFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the QuoteFragment to the layout
            fragmentTransaction.replace(R.id.quote_fragment_container, mDetailsFragment);

            // Add this FragmentTransaction to the back stack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mDetailsFragment.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index
            mDetailsFragment.showQuoteAtIndex(index);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Get the MenuInflater
        // Inflate the menu using activity_menu.xml
        getMenuInflater().inflate(
                R.menu.activity_menu, menu);

        // Return true to display the menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_menu_item:

                // Show a Toast Message.
                Toast.makeText(getApplicationContext(),
                        getString(R.string.provided_by_quote_activity_string),
                        Toast.LENGTH_SHORT)
                        .show();

                // return value true indicates that the menu click has been handled
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}