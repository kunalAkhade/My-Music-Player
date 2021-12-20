package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    private MediaPlayer mediaPlayer;
    private  ImageButton play;
    private SeekBar seekbar;
    private ImageView img;
    private ImageButton next;
    private ImageButton back;
    private TextView textView;
    int a = 0;
    ArrayList<File> songs;
    String textContent;
    int position;
    Thread updateSeek;
   // int image[] = {R.drawable.ic1, R.drawable.ic2, R.drawable.ic3, R.drawable.ic4};
    //int song[] = {R.raw.on, R.raw.on1, R.raw.on2, R.raw.on3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=findViewById(R.id.Play);
        back = findViewById(R.id.back);
        img = findViewById(R.id.imageView);
        next = findViewById(R.id.next);
        seekbar = findViewById(R.id.seekBar);
        textView=findViewById(R.id.textView);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        songs=(ArrayList) bundle.getParcelableArrayList("songList");
        textContent=intent.getStringExtra("currentSong");
        textView.setText(textContent);
        //int intValue = intent.getIntExtra("currentsong", 0);
        position=intent.getIntExtra("position",0);
        Uri uri=Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();
        seekbar.setMax(mediaPlayer.getDuration());

        //seekbar.setMax(mediaPlayer.getDuration());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int check, boolean user) {

                if (user) {
                    mediaPlayer.seekTo(check);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        updateSeek=new Thread(){
            @Override
                    public void run() {
                int currentPosition = 0;
                try {
                    while (currentPosition < mediaPlayer.getDuration()) {
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekbar.setProgress(currentPosition);
                        sleep(800);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }


        };
        updateSeek.start();


        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play.setImageResource(android.R.drawable.ic_media_play);


                } else {
                    mediaPlayer.start();
                    play.setImageResource(android.R.drawable.ic_media_pause);


                }
            }
        });
    }


   /* public void press(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            play.setImageResource(android.R.drawable.ic_media_play);


        } else {
            mediaPlayer.start();
            play.setImageResource(android.R.drawable.ic_media_pause);


        }
    }*/

    /*public void imge(View view) {
        img.setImageResource(image[a]);
        a++;
        if (a == 4) {
            a = 0;
        }

    }*/

   /* public void pre(View view) {
        mediaPlayer.pause();
        Intent it = new Intent(this, SongsList.class);
        startActivity(it);
    }*/
    public void nxt(View view){
        mediaPlayer.stop();
        mediaPlayer.release();
        if(position!=songs.size()-1){
            position=position+1;
        }
        else{
            position=0;
        }
        Uri uri =Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();
        seekbar.setMax(mediaPlayer.getDuration());
        textContent=songs.get(position).getName().toString();
        textView.setText(textContent);

    }

    public void bck(View view){
        mediaPlayer.stop();
        mediaPlayer.release();
        if(position!=0){
            position=position-1;
        }
        else{
            position=songs.size()-1;
        }
        Uri uri =Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();
        seekbar.setMax(mediaPlayer.getDuration());
        textContent=songs.get(position).getName().toString();
        textView.setText(textContent);

    }

}