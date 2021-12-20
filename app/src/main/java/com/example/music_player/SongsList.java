package com.example.music_player;


import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class SongsList extends AppCompatActivity {

    ListView listView ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);




        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener(){


                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        listView=findViewById(R.id.listView);
                        ArrayList<File> mySongs=fetchSongs(Environment.getExternalStorageDirectory());
                        String [] items=new String[mySongs.size()];
                        for(int i=0;i<mySongs.size();i++){
                            items[i]=mySongs.get(i).getName().replace(".mp3","");
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(SongsList.this, android.R.layout.simple_list_item_1,items);
                        listView.setAdapter(adapter);
                       listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                           @Override
                           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                               Intent intent=new Intent(SongsList.this,MainActivity.class);
                               String currentSong= listView.getItemAtPosition(i).toString();
                               intent.putExtra("songList",mySongs);
                               intent.putExtra("currentSong",currentSong);
                               intent.putExtra("position",i);
                               startActivity(intent);
                           }
                       });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                         permissionToken.continuePermissionRequest();
                    }
                })


                .check();

    }

    public ArrayList<File> fetchSongs(File file){
        ArrayList arrayList=new ArrayList();
        File []songs=file.listFiles();
        if(songs!=null){

            for (File myFile: songs){

                if(!myFile.isHidden() && myFile.isDirectory()){
                    arrayList.addAll(fetchSongs(myFile));
                }
                else{
                    if(myFile.getName().endsWith(".mp3")&& !myFile.getName().startsWith(".")){
                        arrayList.add(myFile);
                    }
                }
            }
        }
        return arrayList;
    }

    public void Download(View view){
       Intent intent=new Intent(SongsList.this,DownloadSongs.class);
        startActivity(intent);
    }






}