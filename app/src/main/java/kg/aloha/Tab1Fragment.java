package kg.aloha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;

import android.widget.AdapterView.OnItemClickListener;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kg.aloha.models.Prezident;
import kg.aloha.utils.Conf;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Tab1Fragment extends ListFragment implements OnItemClickListener{
    Conf conf = new Conf();
    ArrayList<Prezident>prezidents = new ArrayList<Prezident>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor =null;
    long g_id_global;
    long p_id_global;
    String p_fio_global;
    View views[] = new View[18];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_content,container,  false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences  = getActivity().getSharedPreferences(Conf.SETTINGS_STR,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        g_id_global =  sharedPreferences.getLong("g_id",0);
        p_id_global =  sharedPreferences.getLong("p_id",0);
        p_fio_global =  sharedPreferences.getString("p_fio","");
        Log.e("g_id_global",g_id_global+"");
        Log.e("p_id_global",p_id_global+"");
        Log.e("p_fio_global",p_fio_global);
        new GetPrezidentData().execute();
        getListView().setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


           Snackbar.make(view, prezidents.get(i).getP_id()+"", Snackbar.LENGTH_LONG)
                        .setAction(prezidents.get(i).getFio(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getContext(),"click on snakeBAR",Toast.LENGTH_LONG).show();
                            }
                        }).show();


    }

    public class MyPrezListAdapter extends ArrayAdapter<String> {

        private Context mContext;
        ArrayList<Prezident> prezidents;
        public MyPrezListAdapter(Context context, int textViewResourceId,ArrayList<Prezident> prezidents) {
            super(context, textViewResourceId);
            mContext = context;
            this.prezidents = prezidents;
        }
        @Override
        public int getCount() {
            return prezidents.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // return super.getView(position, convertView, parent);

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.p_layout_item_0, parent,
                    false);
            TextView catNameTextView = (TextView) row.findViewById(R.id.fio);
            TextView za = (TextView) row.findViewById(R.id.za);
            ImageView img = (ImageView) row.findViewById(R.id.ivFullScreenAd);
            final ImageView img_check = (ImageView) row.findViewById(R.id.imageView3);
            catNameTextView.setText(prezidents.get(position).getFio().substring(4));
            Picasso.with(mContext)
                    .load(prezidents.get(position).getPhoto())
                    .placeholder(R.mipmap.pet_icon)
                    .into(img);

            img_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AddGolos(prezidents.get(position),img_check).execute();
                }
            });
            if(p_id_global==prezidents.get(position).getP_id()){
                img_check.setBackground(getResources().getDrawable(R.drawable.background_blue));
            }
            views[position] = img_check;
            return row;
        }
    }



    class GetPrezidentData extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;

        public void GetPrezidentData(){

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

            if(prezidents.size()==0){
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(conf.getUrl_2()+ Conf.GET_ALL_PREZ)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String responseStr = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseStr);
                    for(int i=0;i<jsonArray.length();i++){
                        Prezident prezident = new Prezident();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        prezident.setP_id(jsonObject.getInt("p_id"));
                        prezident.setFio(jsonObject.getString("fio"));
                        prezident.setBirth_date(jsonObject.getString("birth_date"));
                        prezident.setAge(jsonObject.getString("age"));
                        prezident.setMale(jsonObject.getString("male"));
                        prezident.setNation(jsonObject.getString("nation"));
                        prezident.setStatus(jsonObject.getString("status"));

                        try{
                            Document doc = Jsoup.connect(jsonObject.getString("photo")).get();
                            Element a_url =  doc.getElementById("download-url");
                            String attrSrc = a_url.attr("href");
                            prezident.setPhoto(attrSrc);
                        }catch (Exception e){e.printStackTrace();}

                        prezidents.add(prezident);
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.e("prezidents.size",prezidents.size()+"");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            MyPrezListAdapter myListAdapter = new MyPrezListAdapter(getActivity(),R.layout.p_layout_item_0,prezidents);
            getListView().setAdapter(myListAdapter);
        }
    }

    class AddGolos extends AsyncTask<Void,Void,Void>{
        Prezident prezident;
        View view;
        long p_id,g_id;
        public AddGolos(Prezident prezident,View view) {
            this.prezident=prezident;
            this.view=view;
        }

        private ProgressDialog dialog;
        JSONObject resJSON;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Сохранение...");
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("u_id",Conf.U_ID);
                jsonObject.put("p_id",prezident.getP_id());
                jsonObject.put("g_id",sharedPreferences.getLong("g_id",0));

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(conf.getUrl_2()+Conf.SAVE_GOLOS_PREZIDENT)
                        .post(body)
                        .build();

                okhttp3.Response response = client.newCall(request).execute();

                String networkResp = response.body().string();
                if (!networkResp.isEmpty()) {
                    try{
                        resJSON = new JSONObject(networkResp);
                        g_id = resJSON.getLong("g_id");
                        p_id = resJSON.getLong("p_id");
                        editor.putLong("g_id", g_id);
                        editor.putLong("p_id", p_id);
                        editor.putString("p_fio", prezident.getFio());
                        editor.apply();
                    }catch (Exception e){e.printStackTrace();}
                }

            }catch (Exception e){e.printStackTrace();}


            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                String str = "Вы проголосовали за :"+prezident.getFio().substring(4);
                if(!p_fio_global.isEmpty()){
                    str = str +" , ранее вы голосовали за "+p_fio_global;
                }
                Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
                for(int i=0;i<views.length;i++){
                    views[i].setBackground(getResources().getDrawable(R.drawable.background_gray));
                }
                view.setBackground(getResources().getDrawable(R.drawable.background_blue));
                p_id_global = p_id;
            }
        }
    }
}


