package kg.aloha;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import android.widget.AdapterView.OnItemClickListener;


public class Tab1Fragment extends ListFragment implements OnItemClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_content,container,  false);
        return v;
    }

     ArrayList<String> imgUrl = new ArrayList<String>();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for(int i=0;i<15;i++){
            imgUrl.add("URL "+i);
        }
        MyListAdapter customArrayAdapter = new MyListAdapter(getActivity(),R.layout.p_layout_item,imgUrl);
        setListAdapter(customArrayAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           Snackbar.make(view, imgUrl.get(i), Snackbar.LENGTH_LONG)
                        .setAction(imgUrl.get(i), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getContext(),"click on snakeBAR",Toast.LENGTH_LONG).show();
                            }
                        }).show();
    }

    public class MyListAdapter extends ArrayAdapter<String> {

        private Context mContext;
        ArrayList<String> imgUrl;
        public MyListAdapter(Context context, int textViewResourceId,
                             ArrayList<String> imgUrl) {
            super(context, textViewResourceId, imgUrl);
            mContext = context;
            this.imgUrl = imgUrl;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // return super.getView(position, convertView, parent);

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.p_layout_item, parent,
                    false);
            TextView catNameTextView = (TextView) row.findViewById(R.id.fio);
            TextView za = (TextView) row.findViewById(R.id.za);
            TextView protiv = (TextView) row.findViewById(R.id.protiv);
            ImageView img = (ImageView) row.findViewById(R.id.ivFullScreenAd);
            catNameTextView.setText(imgUrl.get(position));

            return row;
        }
    }

}

