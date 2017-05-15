package cn.ucai.fulicenter_2017.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.data.bean.MessageBean;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.net.IUserModel;
import cn.ucai.fulicenter_2017.data.net.OnCompleteListener;
import cn.ucai.fulicenter_2017.data.net.UserModel;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;
import cn.ucai.fulicenter_2017.ui.activity.CollectsActivity;
import cn.ucai.fulicenter_2017.ui.activity.SettingActivity;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class PersonalFragment extends Fragment {
    @BindView(R.id.iv_PersonalAvatar)
    ImageView ivPersonalAvatar;
    @BindView(R.id.tvPersonalUserName)
    TextView tvPersonalUserName;
    Unbinder unbinder;
    User user;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.collect)
    LinearLayout collect;
    @BindView(R.id.tvCollect)
    TextView tvCollect;
    int collectCount = 0;
    IUserModel model;
    @BindView(R.id.layout_setting)
    RelativeLayout layoutSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_personal, null);
        unbinder = ButterKnife.bind(this, view);
        model = new UserModel();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getInstance().getCurrentUser();
        if (user != null) {
            tvCollect.setText(String.valueOf(collectCount));
            tvPersonalUserName.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), ivPersonalAvatar);
            initCollectCount();
        }
    }

    private void initCollectCount() {
        user = FuLiCenterApplication.getInstance().getCurrentUser();
        model.loadCollectsCount(getContext(), user.getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    collectCount = Integer.parseInt(result.getMsg());
                    Log.i("main","assss"+result.getMsg());
                } else {
                    collectCount = 0;
                }
                tvCollect.setText(String.valueOf(collectCount));
            }
            @Override
            public void onError(String error) {
                collectCount = 0;
                tvCollect.setText(collectCount);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.setting, R.id.layout_setting})
    public void onViewClicked(View view) {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @OnClick(R.id.collect)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(),CollectsActivity.class));
    }
}
