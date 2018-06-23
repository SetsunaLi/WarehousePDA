package com.example.ridko.warehousepda.view;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ridko.warehousepda.R;

/**
 * Created by mumu on 2018/5/22.
 */

public class SeekBarPreferenceVolume extends Preference implements SeekBar.OnSeekBarChangeListener ,View.OnKeyListener {
    private TextView value;
    private int mProgress;
    private int mMax = 10000;
    private boolean mTrackingTouch;
    private OnSeekBarPrefsChangeListener mListener = null;
    private int max;
    private int current;
    private Context mContext;
    private AudioManager mAudioManager;
    private SeekBar seekBar;
    private int nowVolume;
//    private VoiceAssistantSettings voiceAssistantSettings;
    public SeekBarPreferenceVolume(Context context, AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setLayoutResource(R.layout.ring_volume_prefs);
    }

    public SeekBarPreferenceVolume(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        mContext = context;
    }


    public SeekBarPreferenceVolume(Context context) {
        super(context,null);
        mContext = context;
    }

    public void getAudiosize(){
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
    }

    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if (mListener != null) {
            mListener.onProgressChanged(getKey(),seekBar, progress, fromUser);
        }
        if (seekBar.getProgress() != mProgress) {
            syncProgress(seekBar);
        }
        if (fromUser && !mTrackingTouch) {
            syncProgress(seekBar);
        }

    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mListener != null) {
            mListener.onStartTrackingTouch(getKey(),seekBar);
        }
        mTrackingTouch = true;

    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mListener != null) {
            mListener.onStopTrackingTouch(getKey(),seekBar);
        }
        mTrackingTouch = false;
        if (seekBar.getProgress() != mProgress) {
            syncProgress(seekBar);
        }
        notifyHierarchyChanged();

    }

    public void setMax(int max) {
        if (max != mMax) {
            mMax = max;
            notifyChanged();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }

        final SavedState myState = new SavedState(superState);
        myState.progress = mProgress;
        myState.maxs = mMax;
        return myState;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        getAudiosize();
        seekBar = (SeekBar) view.findViewById(R.id.volume_seekbar);
        seekBar.setMax(max);
        seekBar.setProgress(current);
        seekBar.setEnabled(isEnabled());
        seekBar.setOnSeekBarChangeListener(this);
        value = (TextView)view.findViewById(R.id.volume_title);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mProgress = myState.progress;

        mMax = myState.maxs;
        notifyChanged();
    }

    public void setDefaultProgressValue(int defaultValue) {
        if(getPersistedInt(-1) == -1) {

            setProgress(defaultValue);
        }
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }

    private void setProgress(int progress, boolean notifyChanged) {
        if (progress > mMax) {
            progress = mMax;
        }
        if (progress < 0) {
            progress = 0;
        }
        if (progress != mProgress) {
            mProgress = progress;
            persistInt(progress);
            if (notifyChanged) {
                notifyChanged();
            }
        }
    }

    public int getProgress() {
        return mProgress;
    }


    public void setOnSeekBarPrefsChangeListener(OnSeekBarPrefsChangeListener listener) {
        mListener = listener;
    }

    public interface OnSeekBarPrefsChangeListener {
        public void onStopTrackingTouch(String key ,SeekBar seekBar) ;
        public void onStartTrackingTouch(String key ,SeekBar seekBar);
        public void onProgressChanged(String key ,SeekBar seekBar, int progress,boolean fromUser);
    }

    void syncProgress(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        if (progress != mProgress) {
            if (callChangeListener(progress)) {
                setProgress(progress, false);
            } else {
                seekBar.setProgress(mProgress);
            }
        }
    }


    private static class SavedState extends BaseSavedState {

        int progress;
        int maxs;
        

        public SavedState(Parcel source) {
            super(source);
            progress = source.readInt();
            maxs = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(progress);
            dest.writeInt(maxs);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

    }
/*
    public void getVoiceAssistantSettings(VoiceAssistantSettings voiceAss){
        voiceAssistantSettings = voiceAss;
    }*/

    public boolean onKey(View v, int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(current != 0){
                    current--;
                }
                seekBar.setProgress(current);
                mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, current, 0);
                nowVolume = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(current != max){
                    current++;
                }
                seekBar.setProgress(current);
                mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, current, 0);
                nowVolume = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
                break;

            case KeyEvent.KEYCODE_DPAD_UP:

                return false;

            case KeyEvent.KEYCODE_DPAD_DOWN:

                return false;

            case KeyEvent.KEYCODE_BACK:
                Activity act = (Activity)mContext;
                act.finish();
                return true;

            default:
                break;
        }
        return true;
    }
}
