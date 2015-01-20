package course.examples.multipane;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class TitlesFragment extends ListFragment {

    @SuppressWarnings("unused")
    private static final String TAG = "Lab-Fragments";
    private static final ArrayList<String> mTitles = new ArrayList<>();
    private int mCurrIdx = -1;
    private SelectionListener mCallback;

    public interface SelectionListener {
        public void onItemSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load titles
        Collections.addAll(mTitles, getResources().getStringArray(R.array.Titles));

        // Retain this Fragment across Activity reconfigurations
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Make sure that the hosting Activity has implemented
        // the SelectionListener callback interface. We need this
        // because when an item in this ListFragment is selected,
        // the hosting Activity's onItemSelected() method will be called.

        try {
            mCallback = (SelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SelectionListener");
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // When using two-pane layout, configure the ListView to highlight the
        // selected list item

        if (((MainActivity) getActivity()).isInTwoPaneMode()) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        // Set the list adapter for this ListFragment
        setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, mTitles));

        // If an item has been selected, set its checked state
        if (-1 != mCurrIdx)
            getListView().setItemChecked(mCurrIdx, true);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {

        if (mCurrIdx != position) {
            mCurrIdx = position;

            // Notify the hosting Activity that a selection has been made.
            mCallback.onItemSelected(position);
        }

        // Indicates the selected item has been checked
        l.setItemChecked(mCurrIdx, true);

    }
}
