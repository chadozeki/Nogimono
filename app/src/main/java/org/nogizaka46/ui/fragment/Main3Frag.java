package org.nogizaka46.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyx.beautifylib.model.BLPickerParam;
import com.yyx.beautifylib.model.BLResultParam;
import com.yyx.beautifylib.utils.ToastUtils;

import org.nogizaka46.R;
import org.nogizaka46.ui.activity.AboutActivity;
import org.nogizaka46.utils.ToastHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class Main3Frag extends Fragment {
    View view;
    Button btn;
    @InjectView(R.id.settings)
    TextView settings;
    @InjectView(R.id.about)
    TextView about;
    @InjectView(R.id.exit_btn)
    Button exitBtn;
    @InjectView(R.id.layout1)
    LinearLayout layout1;
    @InjectView(R.id.layout2)
    LinearLayout layout2;
    @InjectView(R.id.layout3)
    LinearLayout layout3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main3_frag, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            return;
        initView();

    }

    private void initView() {

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.showToast(getActivity(), "暂未开发");


            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPhotoPickActivity();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    //跳转图片选择页面
    @AfterPermissionGranted(0)
    private void gotoPhotoPickActivity() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            BLPickerParam.startActivity(getActivity());
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问读写权限", 0, perms);
        }
    }

    //获取返回结果数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BLPickerParam.REQUEST_CODE_PHOTO_PICKER) {
            BLResultParam param = data.getParcelableExtra(BLResultParam.KEY);
            List<String> imageList = param.getImageList();
            StringBuilder sb = new StringBuilder();
            for (String path : imageList) {
                sb.append(path);
                sb.append("\n");
            }
            ToastUtils.toast(getActivity(), sb.toString());
        }

    }



}
