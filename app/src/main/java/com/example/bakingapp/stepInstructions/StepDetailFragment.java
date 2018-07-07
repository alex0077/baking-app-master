package com.example.bakingapp.stepInstructions;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.squareup.picasso.Picasso;

import com.example.bakingapp.R;
import com.example.bakingapp.models.StepInstructions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class StepDetailFragment extends Fragment  {

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    public static final String ARG_ITEM_ID = "item_id";
    private StepInstructions stepInstructions;
    private PlayerView playerView;
    private TextView stepDetailTxt;
    private SimpleExoPlayer player;

    private ImageView imageViewStep;

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        stepDetailTxt = rootView.findViewById(R.id.step_detail);
        stepDetailTxt.setText("Step Instructions");
        playerView = rootView.findViewById(R.id.player_view);
        imageViewStep = rootView.findViewById(R.id.image_view_step);



        if (getArguments().containsKey(ARG_ITEM_ID)) {
            stepInstructions = getArguments().getParcelable(ARG_ITEM_ID);
            stepDetailTxt.setText(stepInstructions.getDescription());
        }

        if (stepInstructions.getVideoURL().length() > 0) {
            playerView.setVisibility(View.VISIBLE);
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            playerView.setPlayer(player);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "exo-demo"));
            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(stepInstructions.getVideoURL()));
            player.prepare(mediaSource);
        } else {
            playerView.setVisibility(View.GONE);
            stepDetailTxt.append("Video tutorial for this step will be uploaded soon. Please check again later.");
        }

        // Initialize the Media Session.
        initializeMediaSession();

        if (savedInstanceState != null) {
            player.seekTo(savedInstanceState.getLong("current_position"));
        } else {
            player.setPlayWhenReady(true);
        }

      String imageUrl = stepInstructions.getThumbnailURL();
        int placeholderId = R.drawable.food;
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(placeholderId)
                    .into(imageViewStep);
        } else {
            Picasso.get()
                    .load(placeholderId)
                    .into(imageViewStep);
        }

        return rootView;
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        mMediaSession = new MediaSessionCompat(getContext(), this.getClass().getSimpleName());
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("current_position", player.getCurrentPosition());
    }

/*    @Override
    public void onPause() {
        super.onPause();
        if (player == null) {
            return;
        }
        player.setPlayWhenReady(false);
    }*/

    @Override
    public void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        if (Util.SDK_INT > 23) {
            player.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerView.setPlayer(null);
        if(player!=null)
            player.release();
    }


    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            player.setPlayWhenReady(false);
            super.onPause();
            if (Util.SDK_INT <= 23) {
                player.release();
            }
        }

        @Override
        public void onSkipToPrevious() {
            player.seekTo(0);
        }
    }

}
