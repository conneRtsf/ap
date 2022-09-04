package com.example.agsale.mvp.ui.my;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;
import static com.luck.picture.lib.config.PictureSelectionConfig.selectorStyle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agsale.Engine.GlideEngine;
import com.example.agsale.Engine.ImageCropEngine;
import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.InformationApiService;
import com.example.agsale.mvp.model.bean.InformationTranslation;
import com.example.agsale.mvp.model.bean.PersonalPhotoTranslation;
import com.example.agsale.mvp.model.bean.PhotoTranslation;
import com.example.agsale.mvp.model.bean.RegisterTranslation;
import com.example.agsale.utils.AppUtils;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.engine.SandboxFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnCallbackIndexListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.SandboxTransformUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PravacyActivity extends BaseActivity {
    @BindView(R.id.head1)
    ImageView head1;
    @OnClick(R.id.change)
    void uploads(){
        upload();
        getInformation();
    }
    @BindView(R.id.head)
    ImageButton head;
    @BindView(R.id.name1)
    EditText name1;
    private String paths;
    public File png;

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public File getPng() {
        return png;
    }

    public void setPng(File png) {
        this.png = png;
    }

    private static final String TAG = "test";
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.account)
    TextView account;
    @OnClick(R.id.backS)
    void back(){
        finish();
    }
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {
        getInformation();
        getPhoto();
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopueWindow();
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_pravacy;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {


    }
    @Override
    public void onPause() {
        super.onPause();
        getInformation();
        getPhoto();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformation();
        getPhoto();
    }
    private void upload(){
        String N=String.valueOf(name1.getText());
        if((N.equals("null")||N.equals(""))&& getPaths() == null){
            Toast.makeText(this, "请输入用户名或头像", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        if (!N.equals("null")&&!N.equals("")){
            Call<RegisterTranslation> call2=informationApiService.updataName(N);
            call2.enqueue(new Callback<RegisterTranslation>() {
                @Override
                public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                    Log.e( "onResponse: ", String.valueOf(response));
                    RegisterTranslation registerTranslation=response.body();
                    PravacyActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("run: ",registerTranslation.getMsg() );
                            Toast.makeText(PravacyActivity.this, registerTranslation.getMsg(), Toast.LENGTH_SHORT).show();
                            getInformation();
                        }
                    });
                }

                @Override
                public void onFailure(Call<RegisterTranslation> call, Throwable t) {

                }
            });
        }
        if(!(getPaths() == null)) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), getPng());
            MultipartBody.Builder multiBuilder= new MultipartBody.Builder();
            multiBuilder.addFormDataPart("multipartFile",getPng().getName(),requestFile);
            multiBuilder.setType(MultipartBody.FORM);
            multiBuilder.addFormDataPart("multipartFile",getPng().getName(),requestFile);
            RequestBody multiBody=multiBuilder.build();
            Call<PersonalPhotoTranslation> call=informationApiService.uploadPhoto(multiBody);
            call.enqueue(new Callback<PersonalPhotoTranslation>() {
                @Override
                public void onResponse(Call<PersonalPhotoTranslation> call, Response<PersonalPhotoTranslation> response) {
                    Log.e( "onResponse: ", String.valueOf(response));
                    PersonalPhotoTranslation personalPhotoTranslation=response.body();
                    PravacyActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("run: ",personalPhotoTranslation.getStatus() );
                            if(personalPhotoTranslation.getStatus().equals("done")){
                                Toast.makeText(PravacyActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call<PersonalPhotoTranslation> call, Throwable t) {

                }
            });
        }

    }
    private void getInformation(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<InformationTranslation> call=informationApiService.getInformation();
        call.enqueue(new Callback<InformationTranslation>() {
            @Override
            public void onResponse(Call<InformationTranslation> call, Response<InformationTranslation> response) {
                InformationTranslation informationTranslation = response.body();
                assert informationTranslation != null;
                InformationTranslation.MyData data=informationTranslation.getData();

                PravacyActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(data.getUsername());
                        account.setText(data.getUid());
                    }
                });
            }

            @Override
            public void onFailure(Call<InformationTranslation> call, Throwable t) {

            }
        });
    }
    private void showPopueWindow(){
        View popView = View.inflate(this,R.layout.popupwindow_camera_need,null);
        Button bt_album = (Button) popView.findViewById(R.id.btn_pop_album);
//        Button bt_camera = (Button) popView.findViewById(R.id.btn_pop_camera);
        Button bt_cancle = (Button) popView.findViewById(R.id.btn_pop_cancel);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels*1/3;

        final PopupWindow popupWindow = new PopupWindow(popView,weight,height);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);

        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoSelector();
                popupWindow.dismiss();

            }
        });
//        bt_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//
//            }
//        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM,0,50);

    }
    public void openPhotoSelector(){
        PictureSelectionModel selectionModel = PictureSelector.create(PravacyActivity.this)
                .openGallery(SelectMimeType.TYPE_IMAGE)//0 TYPE ALL 1 Image
                .setSelectorUIStyle(selectorStyle)
                .setImageEngine(GlideEngine.createGlideEngine())//Glide Picasso
                .setCropEngine(new ImageCropEngine(selectorStyle))//是否裁剪 null
                .setCompressEngine(null)//是否压缩
                .setSandboxFileEngine(new PravacyActivity.MeSandboxFileEngine())
                .setCameraInterceptListener(null)//自定义相机 null
                .setRecordAudioInterceptListener(null) //录音回调
                .setSelectLimitTipsListener(null)//拦截自定义提示
                .setEditMediaInterceptListener(null)//自定义编辑时间 null
                .setPermissionDescriptionListener(null)//权限说明 null
                .setPreviewInterceptListener(null)//预览 null
                .setPermissionDeniedListener(null)//权限说明null
                //.setExtendLoaderEngine(getExtendLoaderEngine())
                .setInjectLayoutResourceListener(null)//注入自定义布局 null
                .setSelectionMode(SelectModeConfig.SINGLE)//多选单选
//                .setLanguage(Tools.getLanage().equals("zh") ? 0 : 1) //-2 简体0繁体1
                .setQuerySortOrder("")//降序 升序 查询
//                .setOutputCameraDir(chooseMode == SelectMimeType.ofAudio()
//                        ? getSandboxAudioOutputPath() : getSandboxCameraOutputPath())
//                .setOutputAudioDir(chooseMode == SelectMimeType.ofAudio()
//                        ? getSandboxAudioOutputPath() : getSandboxCameraOutputPath())
//                .setQuerySandboxDir(chooseMode == SelectMimeType.ofAudio()
//                        ? getSandboxAudioOutputPath() : getSandboxCameraOutputPath())
                .isDisplayTimeAxis(true)//显示资源时间轴
//                .isOnlyObtainSandboxDir(cb_only_dir.isChecked())
                .isPageStrategy(false)//false 指定目录
                .isOriginalControl(false)//false开启原图
                .isDisplayCamera(false)//显示摄像 图标
                .isOpenClickSound(false)//是否开启点击声音 false
                .setSkipCropMimeType(getNotSupportCrop())
                .isFastSlidingSelect(true)//true 滑动选择
                //.setOutputCameraImageFileName("luck.jpeg")
                //.setOutputCameraVideoFileName("luck.mp4")
                .isWithSelectVideoImage(false)//图片视频同时选择选 true
                .isPreviewFullScreenMode(false)
                .isPreviewZoomEffect(false)
                .isPreviewImage(false)
                //.setQueryOnlyMimeType(PictureMimeType.ofGIF())
//                .isMaxSelectEnabledMask(cbEnabledMask.isChecked())//达到最大可选 显示蒙层
                .isDirectReturnSingle(false) //单选模式直接返回
                .setMaxSelectNum(1)
//                .setMaxVideoSelectNum(maxSelectVideoNum)
                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)
                .isGif(false);//是否显示gif false
//                .setSelectedData(mAdapter.getData());
        selectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
    }
    private void analyticalSelectResults(ArrayList<LocalMedia> result) {
        StringBuilder builder = new StringBuilder();
        builder.append("Result").append("\n");
        for (LocalMedia media : result) {
            if (media.getWidth() == 0 || media.getHeight() == 0) {
                if (PictureMimeType.isHasImage(media.getMimeType())) {
                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(this,media.getPath());
                    media.setWidth(imageExtraInfo.getWidth());
                    media.setHeight(imageExtraInfo.getHeight());
                } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                    MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(PictureAppMaster.getInstance().getAppContext(), media.getPath());
                    media.setWidth(videoExtraInfo.getWidth());
                    media.setHeight(videoExtraInfo.getHeight());
                }
            }
            String path;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCut() || media.isCompressed()) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            setPaths(path);

            builder.append(media.getAvailablePath()).append("\n");
            Bitmap bm = BitmapFactory.decodeFile(path);

            head.setImageBitmap(bm);
            File file = new File(path);
            setPng(file);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ArrayList<LocalMedia> selectorResult = PictureSelector.obtainSelectorList(data);
            analyticalSelectResults(selectorResult);
        } else if (resultCode == RESULT_CANCELED) {
            Log.i(TAG, "onActivityResult PictureSelector Cancel");
        }
    }
    private static class MeSandboxFileEngine implements SandboxFileEngine {

        @Override
        public void onStartSandboxFileTransform(Context context, boolean isOriginalImage,
                                                int index, LocalMedia media,
                                                OnCallbackIndexListener<LocalMedia> listener) {
            if (PictureMimeType.isContent(media.getAvailablePath())) {//沙盒文件
                String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, media.getPath(),
                        media.getMimeType());
                media.setSandboxPath(sandboxPath);
            }
            if (isOriginalImage) {
                String originalPath = SandboxTransformUtils.copyPathToSandbox(context, media.getPath(),
                        media.getMimeType());
                media.setOriginalPath(originalPath);
                media.setOriginal(!TextUtils.isEmpty(originalPath));
            }
            listener.onCall(media, index);
        }
    }
    private String[] getNotSupportCrop() {
        return new String[]{PictureMimeType.ofGIF(), PictureMimeType.ofWEBP()};
    }
    private void getPhoto(){
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        InformationApiService informationApiService=retrofit.create(InformationApiService.class);
        Call<PhotoTranslation> call=informationApiService.getPhoto();
        call.enqueue(new Callback<PhotoTranslation>() {
            @Override
            public void onResponse(Call<PhotoTranslation> call, Response<PhotoTranslation> response) {
                PhotoTranslation photoTranslation=response.body();
                assert photoTranslation != null;
                PhotoTranslation.MyData photo=photoTranslation.getData();
                System.out.println(photo);
                PravacyActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        head1.setImageBitmap(returnBitMap(photo.getPersonalphoto()));
                    }
                });
            }

            @Override
            public void onFailure(Call<PhotoTranslation> call, Throwable t) {
                Log.e(TAG, "info：" + t.getMessage() + "," + t.toString());
                Toast.makeText(PravacyActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}