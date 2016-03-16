package com.czh.androidforkftvrelease.musicplayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Queue;

import com.czh.androidforkftvrelease.domins.LrcInfo;
import com.czh.androidforkftvrelease.domins.Mp3Info;
import com.czh.androidforkftvrelease.handlelrc.LrcParser;
import com.czh.androidforkftvrelease.httputil.DataUrl;
import com.czh.androidforkftvrelease.httputil.HttpUtil;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
public class MusicService extends Service {

	private Mp3Info mp3Info;
	private MediaPlayer mediaPlayer = null;
	private boolean isplaying = false;
	private boolean isstop = false;
	private boolean isrelease = false;
	private String path;
    private boolean finish=false;
    private UpdateLrc updateLrc;
	private long begin;
	private long curent;
	private long nexttime;
	private String mg;
	private Handler handler=new Handler();
	private LrcInfo lrcInfo;
	private long pauseTime=0;
	private static int counter=0;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		mp3Info = (Mp3Info) intent.getSerializableExtra("mp3");
		int msg = (int) intent.getIntExtra("msg", 0);
		if (msg == AppConstent.PLAY) {
			play(mp3Info);
		}
		if (msg == AppConstent.STOP) {
			stop();
		}
		if (msg == AppConstent.RELESE) {
			release();
		}
		return super.onStartCommand(intent, flags, startId);

	}

	private void release() {
		// TODO Auto-generated method stub
		if(mediaPlayer!=null)
		{
			if(!isrelease)
			{
				
				mediaPlayer.release();
				isplaying=false;
				isrelease=true;
				
			}
			
		}
	}

	private void stop() {
		// TODO Auto-generated method stub
		if (mediaPlayer != null) {
			if (!isrelease) {
				if (!isstop) {
					mediaPlayer.stop();
					isplaying = false;
					isstop = true;
					isrelease = false;
					handler.removeCallbacks(updateLrc);
					pauseTime=System.currentTimeMillis();
				} else {
					mediaPlayer.start();
					handler.postDelayed(updateLrc, 5);
					begin=System.currentTimeMillis()-pauseTime+begin;
					isplaying = true;
					isstop = false;
					isrelease = false;
				}
			}

		}

	}

	private void play(Mp3Info mp3Info2) {
		// TODO Auto-generated method stub
		path = DataUrl.PIC + File.separator + mp3Info2.getMusicfile();
		if (!isplaying) {
			preparelrc();
			mediaPlayer = MediaPlayer.create(this, Uri.parse(path));
			mediaPlayer.start();
			begin=System.currentTimeMillis();
			handler.postDelayed(updateLrc, 5);
			isplaying = true;
			isstop = false;
			isrelease = false;
		}
	}
	public void preparelrc()
	{
		LrcParser lrcParser=new LrcParser();
		try {
			File t=new File(Environment.getExternalStorageDirectory()+File.separator+mp3Info.getMusiclrc());
			if(t.exists()==true)
				System.out.println("file is exist");
			lrcInfo=lrcParser.parser(Environment.getExternalStorageDirectory()+File.separator+mp3Info.getMusiclrc());
			updateLrc=new UpdateLrc(lrcInfo.getTime(), lrcInfo.getMessage());
			begin=0;
			curent=0;
			nexttime=0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

public class UpdateLrc implements Runnable {
		
		private Queue<Long> time;
		private Queue<String> message;
		
		public UpdateLrc(Queue<Long> time,Queue<String> message) {
			super();
			this.time=time;
			this.message=message;
		}
       
		public void run() {
			 long offset=System.currentTimeMillis()-begin;
			 System.out.println("offset:"+String.valueOf(offset));
			// Toast.makeText(this, String.valueOf(offset), Toast.LENGTH_LONG).show();
			 if(curent==0)
			 {
				 if(!time.isEmpty())
				 {
					 nexttime=time.poll();
					 mg=message.poll();
				 }
				 else {
					finish=true;
				}
				 Intent intent=new Intent();
				 intent.setAction(AppConstent.LRCACTION);
				 intent.putExtra("lrc", mg);
				 sendBroadcast(intent);
				 //showlrc.setText(mg);
				 //Toast.makeText(context, mg, Toast.LENGTH_LONG).show();
			 }
			 if(offset>=nexttime+2)
			 {
				 Intent intent=new Intent();
				 intent.setAction(AppConstent.LRCACTION);
				 intent.putExtra("lrc", mg);
				 sendBroadcast(intent);
				 if(!time.isEmpty())
				 {
					 nexttime=time.poll();
					 mg=message.poll();
				 }
				 else {
					finish=true;
				}
			 }
			 System.out.println("time:"+nexttime);
			 curent=curent+10;
			 if(!finish)
			   handler.postDelayed(updateLrc, 10);
			 else {
				handler.removeCallbacks(updateLrc);
			}
			 
		}
	}

}
