package cn.ucai.fulicenter_2017.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulicenter_2017.R;
import cn.ucai.fulicenter_2017.application.FuLiCenterApplication;
import cn.ucai.fulicenter_2017.data.bean.User;
import cn.ucai.fulicenter_2017.data.utils.ImageLoader;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_personal, null);
        unbinder = ButterKnife.bind(this, view);
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
            tvPersonalUserName.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), ivPersonalAvatar);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.setting, R.id.layout_setting})
    public void onViewClicked(View view) {
        startActivity(new Intent(getActivity(),SettingActivity.class));
    }
}
