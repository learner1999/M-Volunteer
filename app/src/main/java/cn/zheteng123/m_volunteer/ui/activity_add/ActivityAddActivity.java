package cn.zheteng123.m_volunteer.ui.activity_add;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.util.MyUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.R.attr.path;

public class ActivityAddActivity extends AppCompatActivity {

    private static final String TAG = "ActivityAddActivity";

    private static final int REQUEST_PICK_IMAGE = 1;

    private String imgUrl = "";

    @BindView(R.id.ll_select_image)
    LinearLayout mLlSelectImage;

    @BindView(R.id.iv_image)
    ImageView mIvImage;

    @BindView(R.id.ll_recruit_time)
    LinearLayout mLlRecruitTime;

    @BindView(R.id.tv_recruit_time)
    TextView mTvRecruitTime;

    @BindView(R.id.ll_activity_time)
    LinearLayout mLlActivityTime;

    @BindView(R.id.tv_activity_time)
    TextView mTvActivityTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        initListener();
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, ActivityAddActivity.class));
    }

    private void initListener() {
        mLlSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_IMAGE);
            }
        });

        mLlRecruitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String recruitTime = String.format("即日起至%d月%d日", month + 1, dayOfMonth);
                        mTvRecruitTime.setText(recruitTime);
                    }
                }, 2017, 1, 1).show();
            }
        });

        mLlActivityTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String activityTime = String.format("%d月%d日", month + 1, dayOfMonth);
                        mTvActivityTime.setText(activityTime);
                    }
                }, 2017, 1, 1).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            Uri imageUri = null;

            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    imageUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if (imageUri == null || bitmap == null) {
                Toast.makeText(this, "获取图片失败，请重新添加！", Toast.LENGTH_SHORT).show();
                return;
            }
            // displayImage(MyUtil.getRealPathFromURI(this, imageUri), bitmap);
            compressImage(MyUtil.getRealPathFromURI(this, imageUri));
        }
    }

    /**
     * 压缩图片
     * @param path 图片路径
     */
    private void compressImage(String path) {
        File file = new File(path);

        Luban.get(this)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File file) {
                        displayImage(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();    //启动压缩
    }

    /**
     * 上传及显示图片
     * @param file 图片文件
     */
    private void displayImage(File file) {

        // 上传图片
        Log.d(TAG, "displayImage: " + path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), "活动图");

        Networks
                .getInstance()
                .getFileUploadApi()
                .uploadImage(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<String> stringResult) {
                        imgUrl = stringResult.getData();
                    }
                });

        // 显示图片
        mIvImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        mIvImage.setVisibility(View.VISIBLE);
        mLlSelectImage.setVisibility(View.GONE);
    }
}
