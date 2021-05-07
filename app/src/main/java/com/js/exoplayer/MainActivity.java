package com.js.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    private String[] song_url=new String[]{"https://opengameart.org/sites/default/files/8_bit_lofi_abandoned_metropolis_0.mp3",
            "https://opengameart.org/sites/default/files/swing.wav","https://opengameart.org/sites/default/files/chill_guitar.wav",
    };

    private int[] song_url_raw= new int[]{R.raw.music1,R.raw.music2,R.raw.music3,R.raw.music4,R.raw.music5,R.raw.music6,R.raw.music7,R.raw.music8
    };

    private ProgressBar progressBar;

    private Player.EventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.my_progress_bar);
//        progressBar.set

        playerView=findViewById(R.id.player_view);
        simpleExoPlayer=new SimpleExoPlayer.Builder(this).build();

        playerView.setPlayer(simpleExoPlayer);
//        for(String music_url:song_url){
//            MediaItem mediaItem=MediaItem.fromUri(Uri.parse(music_url));
//            simpleExoPlayer.addMediaItem(mediaItem);
//        }
//        for(int music_url:song_url_raw){
//            MediaItem mediaItem=MediaItem.fromUri(music_url+"");
//            simpleExoPlayer.addMediaItem(mediaItem);
//        }
//
//
//        simpleExoPlayer.prepare();
//        simpleExoPlayer.play();

        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());
        DefaultDataSourceFactory defaultDataSourceFactory=new DefaultDataSourceFactory(this, Util.getUserAgent(this,"YourApplicationName"));
        simpleExoPlayer.setPlayWhenReady(true);
        for(int music:song_url_raw){
            ExtractorMediaSource extractorMediaSource=new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(RawResourceDataSource.buildRawResourceUri(music));
            simpleExoPlayer.addMediaSource(extractorMediaSource);

        }
        simpleExoPlayer.prepare();
        playerView.setPlayer(simpleExoPlayer);

        eventListener=new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {

            }

            @Override
            public void onPlaybackStateChanged(int state) {
                if(state==Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                } else if(state==Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                }
            }
        };
        simpleExoPlayer.addListener(eventListener);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(simpleExoPlayer!=null){
            simpleExoPlayer.release();
            simpleExoPlayer=null;
        }
    }
}