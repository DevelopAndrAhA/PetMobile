package kg.aloha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   /* Button selectImg;final int Pic_image=2;
    Uri uriToSend=null;InputStream input = null; BitmapFactory.Options options = null;
    Bitmap bitmap = null;
    ImageView imageView;
    Button button;*/


    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Tab1Fragment tab1Fragment;
    private Tab2Fragment tab2Fragment;
    private Tab3Fragment tab3Fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       /* selectImg = (Button) findViewById(R.id.selectImg);
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        options = new BitmapFactory.Options();
        imageView = findViewById(R.id.imageView2);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f = new File(getRealPathFromUri(uriToSend));
                SendFile sendFile = new SendFile(f,MainActivity.this);
                Log.e("FILE INTO ",f.getAbsolutePath());
                Log.e("FILE INTO ",f.getName());
                Log.e("FILE INTO ",f.getPath());
                sendFile.execute();
            }
        });*/

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);



        adapter = new TabAdapter(getSupportFragmentManager());
        new AsyncInit(MainActivity.this).execute();

/*
        tabLayout.getTabAt(0).setIcon(R.drawable.prezident);
        tabLayout.getTabAt(1).setIcon(R.drawable.prezident);
        tabLayout.getTabAt(2).setIcon(R.drawable.prezident);*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


 /*   public void selectImage(){
       Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Pic_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case Pic_image:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    uriToSend = uri;
                    try {
                        input = getContentResolver().openInputStream(uri);

                        options.inSampleSize = 4;
                        bitmap = BitmapFactory.decodeStream(input, null,options);
                        imageView.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    }  }


        }

    }*/

 class AsyncInit extends AsyncTask<Void,Void,Void>{
     private ProgressDialog dialog;
     Context context;
     public AsyncInit(Context context) {
         this.context = context;
     }

     @Override
     protected void onPreExecute() {
         dialog = new ProgressDialog(context);
         dialog.setMessage("загрузка...");
         dialog.setCancelable(false);
         dialog.show();
     }

     @Override
     protected Void doInBackground(Void... voids) {
         return null;
     }

     @Override
     protected void onPostExecute(Void aVoid) {
         if(tab1Fragment==null || tab2Fragment==null || tab3Fragment==null){
             tab1Fragment = new Tab1Fragment();
             tab2Fragment = new Tab2Fragment();
             tab3Fragment = new Tab3Fragment();

             adapter.addFragment(tab1Fragment, "Выборы 2020");
             adapter.addFragment(tab2Fragment, "Депутаты");
             adapter.addFragment(tab3Fragment, "Активисты");
             viewPager.setAdapter(adapter);
             tabLayout.setupWithViewPager(viewPager);
         }
         if (dialog.isShowing()) {
             dialog.dismiss();
         }
     }
 }

    private String getRealPathFromUri(Uri contentUri){
        String result=null;
        Cursor cursor = getContentResolver().query(contentUri,null,null,null,null);
        if(cursor == null){
            result = contentUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        Log.e("PATH_REGresult",result);
        return result;
    }
}
