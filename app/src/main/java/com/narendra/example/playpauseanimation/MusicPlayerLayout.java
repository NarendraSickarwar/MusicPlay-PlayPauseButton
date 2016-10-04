package com.narendra.example.playpauseanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by narendra on 10/4/16.
 */
public class MusicPlayerLayout extends RelativeLayout implements View.OnClickListener {
    CheckBox shuffel_chkbox;
    CheckBox rotation_checkbox;
    ImageView previous_button;
    ImageView next_button;
    PlayPauseView play_pause_view;
    GettingClickListner gettingClickListner;

    public MusicPlayerLayout(Context context) {
        super(context);
        initializeViews(context);
        setGettingClickListner(((GettingClickListner) context));
    }

    public MusicPlayerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
        setGettingClickListner(((GettingClickListner) context));
    }

    public MusicPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
        setGettingClickListner(((GettingClickListner) context));
    }

    public void setGettingClickListner(GettingClickListner gettingClickListner) {
        this.gettingClickListner = gettingClickListner;
    }

    // public MusicPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //   super(context, attrs, defStyleAttr, defStyleRes);
    //}

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.media_player_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Sets the images for the previous and next buttons. Uses
        // built-in images so you don't need to add images, but in
        // a real application your images should be in the
        // application package so they are always available.
        shuffel_chkbox = (CheckBox) this
                .findViewById(R.id.shuffel_chkbox);
        rotation_checkbox = (CheckBox) this
                .findViewById(R.id.rotation_checkbox);
        previous_button = (ImageView) this
                .findViewById(R.id.previous_button);
        next_button = (ImageView) this
                .findViewById(R.id.next_button);
        play_pause_view = (PlayPauseView) this
                .findViewById(R.id.play_pause_view);

        shuffel_chkbox.setOnClickListener(this);
        rotation_checkbox.setOnClickListener(this);
        previous_button.setOnClickListener(this);
        next_button.setOnClickListener(this);
        play_pause_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shuffel_chkbox:
                gettingClickListner.shuffelButtonclick();
                break;
            case R.id.rotation_checkbox:
                gettingClickListner.rotationButtonclick();
                break;
            case R.id.previous_button:
                gettingClickListner.previousButtonclick();
                break;
            case R.id.next_button:
                gettingClickListner.nextButtonclick();
                break;
            case R.id.play_pause_view:
                play_pause_view.toggle();
                gettingClickListner.playpauseButtonclick();
                break;
        }

    }

    public interface GettingClickListner {
        void shuffelButtonclick();

        void rotationButtonclick();

        void previousButtonclick();

        void nextButtonclick();

        void playpauseButtonclick();


    }

}
