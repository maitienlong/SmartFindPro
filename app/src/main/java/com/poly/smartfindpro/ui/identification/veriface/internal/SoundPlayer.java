package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class SoundPlayer {

    private Context mContext;

    private Boolean isEnable = true;

    private MediaPlayer mMediaPlayer = new MediaPlayer();

    public SoundPlayer(Context context, Boolean isEnable) {
        this.mContext = context;
        this.isEnable = isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public void close() {
        this.mContext = null;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }

    }

    public void reset() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
        }

    }

    public void doPlay(int soundID) {
        if (this.isEnable) {
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.reset();

                try {
                    AssetFileDescriptor fileDescriptor = this.mContext.getResources().openRawResourceFd(soundID);
                    this.mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                    fileDescriptor.close();
                    this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            SoundPlayer.this.mMediaPlayer.start();
                        }
                    });
                    this.mMediaPlayer.prepareAsync();
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }

        }
    }
}