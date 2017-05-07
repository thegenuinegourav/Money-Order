package thegenuinegourav.moneyorder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class MyAdapter2 extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public MyAdapter2(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.list_row_2, null);

        TextView title = (TextView)vi.findViewById(R.id.number); // title
        TextView artist = (TextView)vi.findViewById(R.id.cash); // artist name

        HashMap<String, String> wallet = new HashMap<String, String>();
        wallet = data.get(position);

        // Setting all values in listview
        title.setText(wallet.get(WalletsFragment.KEY_NUMBER));
        artist.setText(wallet.get(WalletsFragment.KEY_CASH));
        return vi;
    }
}
