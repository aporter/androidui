package course.examples.staticlayout;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TitlesFragment extends ListFragment {
    private ListSelectionListener mListener;

    @SuppressWarnings("unused")
    private static final String TAG = "TitlesFragment";

    // Callback interface that allows this Fragment to notify the QuoteViewerActivity when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the QuoteViewerActivity that the item in position pos has been selected
        mListener.onListSelection(pos);
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
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set the list adapter for the ListView
        // Discussed in more detail in the user interface classes lesson
        setListAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.title_item, QuoteViewerActivity.sTitleArray));
    }
}