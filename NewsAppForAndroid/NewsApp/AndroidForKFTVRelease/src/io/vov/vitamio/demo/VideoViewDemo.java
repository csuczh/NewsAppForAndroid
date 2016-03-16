/*
 * Copyright (C) 2012 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vov.vitamio.demo;

import java.util.zip.Inflater;

import com.czh.androidforkftvrelease.R;
import com.czh.androidforkftvrelease.httputil.DataUrl;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
public class VideoViewDemo extends Activity {

	//private String path = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
	private String path = DataUrl.PIC;
	private VideoView mVideoView;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.videoview);
		path+=(String)getIntent().getExtras().get("videopath");
		ViewGroup group=(ViewGroup)findViewById(R.id.control);
		LayoutInflater inflater=getLayoutInflater().from(this);
	    group.addView(inflater.inflate(R.layout.mediacontroller, null));
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		mVideoView.setVideoPath(path);
		mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
		mVideoView.setMediaController(new MediaController(this));
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (mVideoView != null)
			mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		super.onConfigurationChanged(newConfig);
	}
}
