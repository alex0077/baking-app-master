package com.example.bakingapp.stepInstructions;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public static final String ARG_ITEM_ID = "item_id";

    private StepInstructions stepInstructions;
    private PlayerView playerView;
    private TextView stepDetailTxt;
    private SimpleExoPlayer player;

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        stepDetailTxt = rootView.findViewById(R.id.step_detail);
        stepDetailTxt.setText("StepInstructions Instructions here");

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            stepInstructions = getArguments().getParcelable(ARG_ITEM_ID);
            stepDetailTxt.setText(stepInstructions.getDescription());
        }

         player = ExoPlayerFactory.newSimpleInstance(getContext(),new DefaultTrackSelector());
         playerView= rootView.findViewById(R.id.player_view);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(stepInstructions.getVideoURL().length()>0) {
            playerView.setVisibility(View.VISIBLE);
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            playerView.setPlayer(player);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "exo-demo"));
            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(stepInstructions.getVideoURL()));
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }else{
            playerView.setVisibility(View.GONE);
            stepDetailTxt.append("Video tutorial for this stepInstructions will be uploaded soon. Check again after you are done with the cooking and the eating part as well :) .");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
    }

}
