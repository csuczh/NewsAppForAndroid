package com.czh.androidforkftvrelease.musicplayer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.domins.LrcInfo;
import com.czh.androidforkftvrelease.domins.Mp3Info;
import com.czh.androidforkftvrelease.handlelrc.LrcParser;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.httputil.HttpDownloader;
import com.czh.androidforkftvrelease.musicplayer.MusicService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends Activity {

	private Mp3Info mp3Info;
	private Button play;
	private Button stop;
	private Button release;
	private TextView showlrc;
	private Context context;
	private boolean isPlaying = false;
	//private boolean finish = false;
	private LrcThread thread;

	private IntentFilter intentFilter;
	private BroadcastReceiver broadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music);
		mp3Info = (Mp3Info) getIntent().getExtras().get("mp3");
		context = this;
		thread = new LrcThread();
		thread.run();
		intentFilter = getIntentFilter();
		broadcastReceiver = new LrcBroadcast();
		registerReceiver(broadcastReceiver, intentFilter);
		play = (Button) this.findViewById(R.id.playmusic);
		stop = (Button) this.findViewById(R.id.stopmusic);
		release = (Button) this.findViewById(R.id.releasemusic);
		showlrc = (TextView) this.findViewById(R.id.showlrc);
		play.setOnClickListener(new PlayListener());
		stop.setOnClickListener(new StopListener());
		release.setOnClickListener(new ReleaseListener());
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		intentFilter = getIntentFilter();
		registerReceiver(broadcastReceiver, intentFilter);
	}
	

	public class LrcThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// ÏÂÔØlrcÎÄ¼þ
			HttpDownloader httpDownloader = new HttpDownloader();
			int k = httpDownloader.downFile(
					DataUrl.PIC + mp3Info.getMusiclrc(), "",
					mp3Info.getMusiclrc());
			System.out.println("k:::::::::::::::::::" + k + "    "
					+ DataUrl.PIC + mp3Info.getMusiclrc());
		}

	}

	public class PlayListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent();
			intent.putExtra("mp3", mp3Info);
			intent.putExtra("msg", AppConstent.PLAY);
			intent.setClass(MusicPlayer.this, MusicService.class);

			while (thread.isAlive()) {

			}

			startService(intent);

			isPlaying = true;
		}
	}

	public class StopListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("msg", AppConstent.STOP);
			intent.setClass(MusicPlayer.this, MusicService.class);
			startService(intent);
			
			isPlaying = isPlaying ? false : true;

		}

	}

	public class ReleaseListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("msg", AppConstent.RELESE);
			intent.setClass(MusicPlayer.this, MusicService.class);
			startService(intent);
		}

	}

	
	public IntentFilter getIntentFilter() {

		if (intentFilter == null) {
			intentFilter = new IntentFilter(AppConstent.LRCACTION);

		}
		return intentFilter;
	}

	class LrcBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String msg = intent.getExtras().getString("lrc");
			showlrc.setText(msg);
		}

	}
}
