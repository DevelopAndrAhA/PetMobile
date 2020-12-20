package kg.aloha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import kg.aloha.utils.Conf;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Altynbek on 12.12.2020.
 */

public class SendFile {
    File f ;
    Context context;
    public SendFile(File f,Context context) {
        this.f = f;
        this.context = context;
    }
    public void execute(){
        new Sending(f,context).execute();
    }

    class Sending extends AsyncTask<Void,Void,Void>{
        File f2 ;
        Context context;
        private ProgressDialog dialog;
        public Sending(File f,Context context) {
            this.f2=f;
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("загрузка...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
            try{
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f))
                        .build();
                Request request = new Request.Builder().url(new Conf().getUrl())
                        .post(requestBody).build();
                Response response = client.newCall(request).execute();

                JSONObject resultUpload = new JSONObject(response.body().string());
            }catch (Exception e){
                Log.e("ERRORRRRRR",e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(context,"alohaaaaaaaaaa",Toast.LENGTH_LONG).show();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }
}
