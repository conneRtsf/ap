package com.example.agsale.mvp.ui.log;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

import androidx.appcompat.app.AlertDialog;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agsale.R;
import com.example.agsale.base.BaseActivity;
import com.example.agsale.di.component.AppComponent;
import com.example.agsale.mvp.model.api.HttpClientUtils;
import com.example.agsale.mvp.model.api.service.RegisterApiService;
import com.example.agsale.mvp.model.bean.RegisterTranslation;

import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.verification)
    Button verification;
    @BindView(R.id.getVerification)
    EditText getVerification;
    @BindView(R.id.password)
    EditText Apassword;
    @BindView(R.id.register)
    Button register;
    @Override
    protected void initBaseData() {

    }

    @Override
    protected void baseConfigView() {
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVerification();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    private void register(){
        String num=String.valueOf(phone.getText());
        String verification=String.valueOf(getVerification.getText());
        String password=String.valueOf(Apassword.getText());
        if(!detailsCheck(num,verification,password)){
            Toast.makeText(this, "手机号,验证码或密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit=HttpClientUtils.getRetrofitWithGsonAdapter();
        RegisterApiService registerApiService=retrofit.create(RegisterApiService.class);
        Call<RegisterTranslation> call=registerApiService.register(num,password,verification);
        call.enqueue(new Callback<RegisterTranslation>() {
            @Override
            public void onResponse(Call<RegisterTranslation> call, Response<RegisterTranslation> response) {
                RegisterTranslation registerTranslation = response.body();
                Integer code = registerTranslation.getCode();
                String msg = registerTranslation.getMsg();
                Object body = response.body();
                if (body == null) return;

                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if(msg.equals("注册成功")){
                                RegisterActivity.this.finish();
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
                Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
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
                .url("http://180.101.147.217:9000/consumer/sms/send/register/"+nub)
                .build();
        System.out.println("http://180.101.147.217:9000/consumer/sms/send/register/"+nub);
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
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, fd, Toast.LENGTH_SHORT).show();
                            CountDownTimer countDownTimer=new CountDownTimer(60000,1000) {
                                @Override
                                public void onTick(long l) {
                                    verification.setEnabled(false);
                                    verification.setText(l/1000+"秒");
                                }

                                @Override
                                public void onFinish() {
                                    verification.setEnabled(true);
                                    verification.setText("获取验证码");
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
    public Boolean detailsCheck(String uname, String pwd, String icode) {
        if(uname.isEmpty()||pwd.isEmpty()||icode.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}