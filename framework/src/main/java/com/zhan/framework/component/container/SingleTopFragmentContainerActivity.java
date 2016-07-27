package com.zhan.framework.component.container;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;

/**
 * Created by WuYue on 2016/4/21.
 */
public class SingleTopFragmentContainerActivity extends FragmentContainerActivity {
    /**
     * 启动一个界面
     *
     * @param activity
     * @param clazz
     * @param args
     */
    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (args != null)
            intent.putExtra("args", args);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args,boolean showActionbar) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        intent.putExtra("showActionbar", showActionbar);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (args != null)
            intent.putExtra("args", args);
        activity.startActivity(intent);
    }

    public static void launchForResult(Fragment fragment, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        if (fragment.getActivity() == null)
            return;
        Activity activity = fragment.getActivity();

        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (args != null)
            intent.putExtra("args", args);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void launchForResult(BaseActivity from, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        intent.putExtra("className", clazz.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (args != null)
            intent.putExtra("args", args);
        from.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(fragment!=null&&(fragment instanceof ABaseFragment)){
            ((ABaseFragment) fragment).onNewIntent(intent);
        }
    }
}
