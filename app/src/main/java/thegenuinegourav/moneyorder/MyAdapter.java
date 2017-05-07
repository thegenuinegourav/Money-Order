package thegenuinegourav.moneyorder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.data;

public class MyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public MyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.sender); // title
        TextView artist = (TextView)vi.findViewById(R.id.receiver); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.amount); // duration

        HashMap<String, String> transaction = new HashMap<String, String>();
        transaction = data.get(position);

        // Setting all values in listview
        title.setText(transaction.get(TransactionsFragment.KEY_SENDER));
        artist.setText(transaction.get(TransactionsFragment.KEY_RECEIVER));
        duration.setText(transaction.get(TransactionsFragment.KEY_AMOUNT));
        return vi;
    }
}
