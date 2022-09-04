package com.example.agsale.mvp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.agsale.R;
import com.example.agsale.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class FirstFragment extends BaseFragment {
    @OnClick(R.id.vegetable)
    void goItem1(){
        System.out.println(1);
    }
    private VideoView video;
    private MediaController mediaController;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
    }
    public void OnBannerClick(int position) {
    }
    protected void initView() {
        video =requireActivity().findViewById(R.id.video);
        mediaController = new MediaController(requireActivity());
        video.setVideoURI(Uri.parse("https://zjl-1309557729.cos.ap-nanjing.myqcloud.com/xuanchuan/8506993427114508293266208396.mp4"));
        mediaController.setMediaPlayer(video);
        video.setMediaController(mediaController);
        video.requestFocus();
        video.start();
    }

    @Override
    protected void initData() {
    }

    private class MyImageLoader extends ImageLoader {
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =LayoutInflater.from(getActivity()).inflate(R.layout.fragment_share,container,false);
        initData();
        initView();
        return view;
    }
    @OnClick(R.id.com)
    void onClick(View view) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        startActivity(intent);
    }
}