package course.examples.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private final List<String> mNames;
    private final int mRowLayout;

    public MyRecyclerViewAdapter(List<String> names, int rowLayout) {
        mNames = names;
        mRowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
        return new MyRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mName.setText(mNames.get(i));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Display a Toast message indicting the selected item
            Toast.makeText(view.getContext(),
                    mName.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return (null == mNames) ? 0 : mNames.size();
    }

}

