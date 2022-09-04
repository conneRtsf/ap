package com.example.agsale.mvp.ui.log;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.LoginApiService;
import com.example.agsale.mvp.model.bean.RegisterTranslation;
import com.example.agsale.mvp.ui.main.MainActivity;
import com.example.agsale.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PasswordLogActivity extends BaseActivity {
    Handler mHandler;
    @OnClick(R.id.newMan)
    void cl(){
        Intent intent=new Intent();
        intent.setClass(PasswordLogActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.username)
    EditText username;
    @OnClick(R.id.message)
    void cl2(){
        Intent intent=new Intent();
        intent.setClass(PasswordLogActivity.this, MessageLogActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.log)
    void cl3(){
        passwordLog();
    }
    @Override
    protected void initBaseData() {

    }
    @SuppressLint("HandlerLeak")
    @SuppressWarnings("deprecation")
    @Override
    protected void baseConfigView() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Log.e("handleMessage: ", (String) msg.obj);
                    SharedPreferencesUtil.init(PasswordLogActivity.this,"com.example.newag",MODE_PRIVATE);
                    SharedPreferencesUtil.getInstance().putString("token", (String) msg.obj).commit();
                }
            }
        };
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_log;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
    private void passwordLog(){
        String name=String.valueOf(username.getText());
        String pass=String.valueOf(password.getText());
        if(name.isEmpty()||pass.isEmpty()){
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        LoginApiService loginApiService=retrofit.create(LoginApiService.class);
        Call<RegisterTranslation> call=loginApiService.passwordLog(name,pass);
        call.enqueue(new Callback<RegisterTranslation>() {
            @Override
            public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                RegisterTranslation registerTranslation = response.body();
                assert registerTranslation != null;
                String msg = registerTranslation.getMsg();
                Object body = response.body();
                if (body == null) return;

                PasswordLogActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(PasswordLogActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if(msg.equals("登录成功")){
                                Intent intent = new Intent();
                                RegisterTranslation.DataDTO outside=registerTranslation.getData();
                                String token=outside.getToken();
                                Message message = new Message();
                                message.what = 1;
                                message.obj = token;
                                mHandler.sendMessage(message);
                                Log.e("abc: ", token);
                                intent.setClass(PasswordLogActivity.this, MainActivity.class);
                                startActivity(intent);
                                PasswordLogActivity.this.finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RegisterTranslation> call, Throwable t) {
                Log.e(TAG, "info：" + t.getMessage() + "," + t.toString());
                Toast.makeText(PasswordLogActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}