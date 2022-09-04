package com.example.agsale.mvp.ui.log;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
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

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MessageLogActivity extends BaseActivity {
    Handler mHandler;
    @OnClick(R.id.newMan)
    void cl(){
        Intent intent=new Intent();
        intent.setClass(MessageLogActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.getVerification)
    EditText verification;
    @OnClick(R.id.password)
    void cl2(){
        Intent intent=new Intent();
        intent.setClass(MessageLogActivity.this, PasswordLogActivity.class);
        startActivity(intent);
        finish();
    }
    @BindView(R.id.verification)
    Button message;
    @OnClick(R.id.verification)
    void cl3(){
        getVerification();
    }
    @OnClick(R.id.log)
    void cl4(){
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
                    SharedPreferencesUtil.init(MessageLogActivity.this, "com.example.newag", MODE_PRIVATE);
                    SharedPreferencesUtil.getInstance().putString("token", (String) msg.obj).commit();
                }
            }
        };
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_message_log;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }
    private void getVerification(){
        String nub=String.valueOf(phone.getText());
        Log.e("getVerification: ", nub);
        if (nub.equals("")){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpClient httpClient=new OkHttpClient.Builder().build();
        Request request=new Request.Builder()
                .get()
                .url("http://180.101.147.217:9000/consumer/sms/send/vlogin/"+nub)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    okhttp3.Call call = httpClient.newCall(request);
                    okhttp3.Response response = call.execute();
                    assert response.body() != null;
                    String responsePond = response.body().string();
                    JSONObject jsonObject = new JSONObject(responsePond);
                    System.out.println(responsePond);
                    String fd = jsonObject.getString("msg");
                    MessageLogActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MessageLogActivity.this, fd, Toast.LENGTH_SHORT).show();
                            CountDownTimer countDownTimer=new CountDownTimer(60000,1000) {
                                @Override
                                public void onTick(long l) {
                                    message.setEnabled(false);
                                    message.setText(l/1000+"秒");
                                }

                                @Override
                                public void onFinish() {
                                    message.setEnabled(true);
                                    message.setText("获取验证码");
                                }
                            }.start();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void passwordLog(){
        String name=String.valueOf(phone.getText());
        String pass=String.valueOf(verification.getText());
        if(name.isEmpty()||pass.isEmpty()){
            Toast.makeText(this, "手机号或验证码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit= HttpClientUtils.getRetrofitWithGsonAdapter();
        LoginApiService loginApiService=retrofit.create(LoginApiService.class);
        Call<RegisterTranslation> call=loginApiService.messageLog(name,pass);
        call.enqueue(new Callback<RegisterTranslation>() {
            @Override
            public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                RegisterTranslation registerTranslation = response.body();
                assert registerTranslation != null;
                String msg = registerTranslation.getMsg();
                Object body = response.body();
                if (body == null) return;

                MessageLogActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(MessageLogActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if(msg.equals("登录成功")){
                                Intent intent = new Intent();
                                RegisterTranslation.DataDTO outside=registerTranslation.getData();
                                String token=outside.getToken();
                                Message message = new Message();
                                message.what = 1;
                                message.obj = token;
                                mHandler.sendMessage(message);
                                Log.e("abc: ", token);
                                intent.setClass(MessageLogActivity.this, MainActivity.class);
                                startActivity(intent);
                                MessageLogActivity.this.finish();
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
                Toast.makeText(MessageLogActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}