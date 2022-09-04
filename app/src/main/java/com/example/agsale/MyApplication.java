package com.example.agsale;


import com.example.agsale.base.BaseApplication;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.di.component.DaggerAppComponent;
import com.example.agsale.di.module.AppModule;
import com.example.agsale.di.module.AquApiModule;
import com.example.agsale.utils.SdkDelayInitUtil;
import com.example.agsale.utils.SharedPreferencesUtil;
import com.example.agsale.utils.UMInitUtil;

public class MyApplication extends BaseApplication {

    private static MyApplication myApplication;

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    private AppComponent appComponent;
    public boolean welcomedialogshowed = false;

    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .aquApiModule(new AquApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static String USER_TOKEN = "";

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        SharedPreferencesUtil.init(this,"com.example.newag",MODE_PRIVATE);
        UMInitUtil.init(this);
        SdkDelayInitUtil.getInstance().init();
        initComponent();
    }
}
