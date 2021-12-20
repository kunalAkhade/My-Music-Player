package com.example.music_player;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DownloadSongs extends AppCompatActivity{
 private Button youtube;
 private Button mp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloaded);
        youtube=findViewById(R.id.Youtube);
        mp3=findViewById(R.id.mp3);
      youtube.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(Intent.ACTION_VIEW);
              intent.setData(Uri.parse("https://www.youtube.com/"));
              intent.setPackage("com.google.android.youtube");
              PackageManager manager = getPackageManager();
              List<ResolveInfo> info = manager.queryIntentActivities(intent, 0);
              if (info.size() > 0) {
                  startActivity(intent);
              } else {
                  //No Application can handle your intent
              }
          }
      });
      mp3.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view) {
              startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ytmp3.cc/youtube-to-mp3/")));
          }
      });



    }


}
