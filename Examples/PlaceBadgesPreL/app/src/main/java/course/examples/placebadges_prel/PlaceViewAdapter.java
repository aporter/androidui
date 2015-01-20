package course.examples.placebadges_prel;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class PlaceViewAdapter extends BaseAdapter {

    private final ArrayList<PlaceRecord> list = new ArrayList<PlaceRecord>();
    private static LayoutInflater sInflater = null;
    private final Context mContext;

    public PlaceViewAdapter(Context context) {
        mContext = context;
        sInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View newView = convertView;
        ViewHolder holder;

        final PlaceRecord curr = list.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = sInflater.inflate(R.layout.place_badge_view, null);
            holder.flag = (ImageView) newView.findViewById(R.id.flag);
            holder.country = (TextView) newView.findViewById(R.id.country_name);
            holder.place = (TextView) newView.findViewById(R.id.place_name);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.flag.setImageBitmap(curr.getFlagBitmap());
        holder.place.setText(curr.getPlace() + ",");
        holder.country.setText(curr.getCountryName());

        newView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,
                        PlaceViewDetailActivity.class);
                intent.putExtras(curr.packageIntent());
                mContext.startActivity(intent);
            }
        });

        return newView;
    }

    static class ViewHolder {

        ImageView flag;
        TextView country;
        TextView place;

    }

    public boolean intersects(Location location) {
        for (PlaceRecord item : list) {
            if (item.intersects(location)) {
                return true;
            }
        }
        return false;
    }

    public void add(PlaceRecord listItem) {
        list.add(listItem);
        notifyDataSetChanged();
    }

    public void removeAllViews() {
        list.clear();
        this.notifyDataSetChanged();
    }
}
