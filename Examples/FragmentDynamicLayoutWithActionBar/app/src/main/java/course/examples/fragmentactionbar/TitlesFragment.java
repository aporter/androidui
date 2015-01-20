package course.examples.fragmentactionbar;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TitlesFragment extends ListFragment {
    private ListSelectionListener mListener;
    private int mCurrIdx = -1;

    // Callback interface that allows this Fragment to notify the QuoteViewerActivity
    // when user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Set the ListSelectionListener for communicating with the QuoteViewerActivity
            mListener = (ListSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This Fragment will add items to the ActionBar
        setHasOptionsMenu(true);

        // Retain this Fragment across Activity Reconfigurations
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        // Discussed in more detail in the user interface classes lesson
        setListAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.list_item, QuoteViewerActivity.TitleArray));

        if (mCurrIdx != -1) {
            setSelection(mCurrIdx);
        }
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        mCurrIdx = pos;

        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the QuoteViewerActivity that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the options Menu using title_menu.xml
        inflater.inflate(R.menu.title_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.title_menu_item:

                // Show a Toast Message.
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.provided_by_title_fragment_string),
                        Toast.LENGTH_SHORT).show();

                // Return value true indicates that the menu click has been handled
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}