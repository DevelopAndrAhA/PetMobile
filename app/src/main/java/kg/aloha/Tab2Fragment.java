package kg.aloha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kg.aloha.models.Deputat;
import kg.aloha.utils.Conf;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Tab2Fragment extends ListFragment  {
    Conf conf = new Conf();
    ArrayList<Deputat> deputats = new ArrayList<Deputat>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_content_2,container,  false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetDeputatData().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    class GetDeputatData extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;

        public void GetDeputatData(){
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("загрузка...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("загрузка...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(deputats.size()==0){
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(conf.getUrl_2()+Conf.GET_ALL_DEP)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String responseStr = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseStr);
                    for(int i=0;i<jsonArray.length();i++){
                        Deputat deputat = new Deputat();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        deputat.setD_id(jsonObject.getInt("d_id"));
                        deputat.setPhoto(jsonObject.getString("photo"));
                        deputat.setFio(jsonObject.getString("fio"));
                        deputat.setInfo(jsonObject.getString("info"));
                        deputat.setF_id(jsonObject.getInt("f_id"));
                        deputats.add(deputat);
                    }
                    Log.e("deputats.size",deputats.size()+"");
                }catch (Exception e){e.printStackTrace();}
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            MyAdapter myListAdapter = new MyAdapter(getActivity(),R.layout.d_layout_item,deputats);
            getListView().setAdapter(myListAdapter);
        }
    }




    public class MyAdapter extends ArrayAdapter<String> {
        private Context mContext;
        private List<Deputat> deputats;
        public MyAdapter(Context context, int textViewResourceId,List list) {
            super(context, textViewResourceId);
            mContext = context;
            this.deputats = list;
        }

        @Override
        public int getCount() {
            return deputats.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.d_layout_item, parent,
                    false);
            TextView catNameTextView = (TextView) row.findViewById(R.id.depfio);
            TextView za = (TextView) row.findViewById(R.id.dep_za);
            ImageView img = (ImageView) row.findViewById(R.id.deImg);
            catNameTextView.setText(deputats.get(position).getFio());
            Picasso.with(mContext)
                    .load(deputats.get(position).getPhoto())
                    .placeholder(R.mipmap.pet_icon)
                    .into(img);
            return row;
        }
    }
}