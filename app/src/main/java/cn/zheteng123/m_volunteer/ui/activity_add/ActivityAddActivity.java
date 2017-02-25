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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.activity_add.ActivityEntity;
import cn.zheteng123.m_volunteer.entity.baidu.ConverseGeographyResult;
import cn.zheteng123.m_volunteer.entity.baidu.GeographyResult;
import cn.zheteng123.m_volunteer.util.MyUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.R.attr.path;

public class ActivityAddActivity extends AppCompatActivity {

    private static final String TAG = "ActivityAddActivity";

    private static final int REQUEST_PICK_IMAGE = 1;

    private String mImgUrl = "";

    private String mProvince, mCity, mDistrict, mStreet;

    private double mLng, mLat;

    private String mServiceType = "青少年活动";

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

    @BindView(R.id.btn_publish)
    Button mBtnPublish;

    @BindView(R.id.et_title)
    EditText mEtTitle;

    @BindView(R.id.et_description)
    EditText mEtDescription;

    @BindView(R.id.et_address)
    EditText mEtAddress;

    @BindView(R.id.et_recruit_num)
    EditText mEtRecruitNum;

    @BindView(R.id.et_working_hours)
    EditText mEtWorkingHours;

    @BindView(R.id.et_service_object)
    EditText mEtServiceObject;

    @BindView(R.id.spinner_service_type)
    Spinner mSpinnerServiceType;

    @BindView(R.id.et_superintendent_name)
    EditText mEtSuperintendentName;

    @BindView(R.id.et_superintendent_telephone)
    EditText mEtSuperintendentTelephone;

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

        mEtAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !mEtAddress.getText().toString().equals("")) {
                    Log.d(TAG, "onFocusChange: 地址输入框失去焦点");
                    MyUtil
                            .getGeographyByAddress(mEtAddress.getText().toString())
                            .flatMap(new Func1<GeographyResult, Observable<ConverseGeographyResult>>() {
                                @Override
                                public Observable<ConverseGeographyResult> call(GeographyResult geographyResult) {
                                    GeographyResult.ResultBean.LocationBean location = geographyResult.getResult().getLocation();
                                    mLat = location.getLat();
                                    mLng = location.getLng();
                                    return MyUtil.getConverseGeographyByCoordinate(mLng, mLat);
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ConverseGeographyResult>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, "onError: " + e.getMessage());
                                }

                                @Override
                                public void onNext(ConverseGeographyResult converseGeographyResult) {
                                    ConverseGeographyResult.ResultBean.AddressComponentBean addressComponent =
                                            converseGeographyResult.getResult().getAddressComponent();
                                    mProvince = addressComponent.getProvince();
                                    mCity = addressComponent.getCity();
                                    mDistrict = addressComponent.getDistrict();
                                    mStreet = addressComponent.getStreet();
                                    Log.d(TAG, "onNext: 完成地址转换——" + mLng + " " + mLat + " " + mProvince + mCity + mDistrict + mStreet);
                                }
                            });
                }
            }
        });

        // mSpinnerServiceType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //     @Override
        //     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //         mServiceType = parent.getSelectedItem().toString();
        //     }
        // });

        mBtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityEntity activityEntity = new ActivityEntity();
                activityEntity.setActivityStatusId(1);
                activityEntity.setAddress(mEtAddress.getText().toString());
                activityEntity.setAddressProvince(mProvince);
                activityEntity.setAddressCity(mCity);
                activityEntity.setAddressDistrict(mDistrict);
                activityEntity.setAddressStreet(mStreet);
                activityEntity.setCoordLat(mLat);
                activityEntity.setCoordLong(mLng);
                activityEntity.setName(mEtTitle.getText().toString());
                activityEntity.setDescription(mEtDescription.getText().toString());
                activityEntity.setPicture(mImgUrl);
                activityEntity.setPrincipalName(mEtSuperintendentName.getText().toString());
                activityEntity.setPrincipalContact(mEtSuperintendentTelephone.getText().toString());
                activityEntity.setRecruitPersonNumber(Integer.parseInt(mEtRecruitNum.getText().toString()));
                activityEntity.setRecruitTime(mTvRecruitTime.getText().toString());
                activityEntity.setServiceObject(mEtServiceObject.getText().toString());
                activityEntity.setServiceType(mSpinnerServiceType.getSelectedItem().toString());
                activityEntity.setTime(mTvActivityTime.getText().toString());
                activityEntity.setTimestamp("2017-02-24");
                activityEntity.setWorkingHours(Integer.parseInt(mEtWorkingHours.getText().toString()));

                Networks
                        .getInstance()
                        .getActivityApi()
                        .addActivity(activityEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Result<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Result<String> stringResult) {
                                Toast.makeText(ActivityAddActivity.this, stringResult.getData(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
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
                        mImgUrl = stringResult.getData();
                    }
                });

        // 显示图片
        mIvImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        mIvImage.setVisibility(View.VISIBLE);
        mLlSelectImage.setVisibility(View.GONE);
    }
}
