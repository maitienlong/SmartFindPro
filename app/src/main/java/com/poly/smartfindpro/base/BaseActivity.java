package com.poly.smartfindpro.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.poly.smartfindpro.R;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.callback.OnFragmentCloseCallback;
import com.poly.smartfindpro.callback.OnFragmentDataCallBack;
import com.poly.smartfindpro.constants.Constants;
import com.poly.smartfindpro.dialog.AlertFragment;
import com.poly.smartfindpro.dialog.LoadingDialog;
import com.poly.smartfindpro.utils.ActivityUtils;
import com.poly.smartfindpro.utils.CommonUtils;
import com.poly.smartfindpro.utils.LocaleUtils;
import com.poly.smartfindpro.utils.broadcast.LocalBroadcastReceiver;
import com.poly.smartfindpro.utils.broadcast.OrderEnabledLocalBroadcastManager;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseActivity extends AppCompatActivity implements BaseScreen {

    private FragmentManager mFrgManager;

    private Deque<BaseFragment> mFragStack;

    private LoadingDialog mLoadingDialog;

    private AlertFragment alertFragment;

    private final int showUserGuide = 0;

    private LocalBroadcastReceiver showLoadingReceiver = null;

    private LocalBroadcastReceiver hideLoadingReceiver = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleUtils.onAttach(base));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = getWindow().getDecorView().getRootView();
//        rootView.getViewTreeObserver().addOnGlobalLayoutListener((() -> {
//            View vToolbar = findViewById(R.id.toolbar);
//            if (vToolbar == null) {
//                return;
//            }
//            TextView tvTitle = vToolbar.findViewById(R.id.tv_title);
//            if (tvTitle != null) {
//                tvTitle.setSelected(true);
//            }
//        }));

        showLoadingReceiver = new LocalBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showLoading();
            }
        };

        hideLoadingReceiver = new LocalBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                hideLoading();
            }
        };

        OrderEnabledLocalBroadcastManager.getInstance(this).registerReceiver(showLoadingReceiver,
                new IntentFilter(Constants.Actions.NOTIFY_SHOW_LOADING));

        OrderEnabledLocalBroadcastManager.getInstance(this).registerReceiver(hideLoadingReceiver,
                new IntentFilter(Constants.Actions.NOTIFY_HIDE_LOADING));
    }

    @Override
    protected void onDestroy() {
        if (showLoadingReceiver != null) {
            try {
                OrderEnabledLocalBroadcastManager.getInstance(this).unregisterReceiver(showLoadingReceiver);
            } catch (Exception ignored) {
            }
        }

        if (hideLoadingReceiver != null) {
            try {
                OrderEnabledLocalBroadcastManager.getInstance(this).unregisterReceiver(hideLoadingReceiver);
            } catch (Exception ignored) {
            }
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        try {
            showLoadingDialog();
        } catch (Exception ignored) {

        }
    }

    @Override
    public void hideLoading() {
        try {
            hideLoadingDialog();
        } catch (Exception ignored) {

        }
    }

    @Override
    public void showMessage(String message) {
        showAlertDialog(message);
    }

    public AlertFragment getAlertFragment() {
        return alertFragment;
    }

    public int getShowUserGuide() {
        return showUserGuide;
    }

    /**
     * Show loading dialog without leak window
     */
    public void showLoadingDialog() {
        try {
            hideLoadingDialog();
            if (mLoadingDialog == null) {
                mLoadingDialog = LoadingDialog.newInstance();
            }
            mLoadingDialog.setCancelable(false);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(mLoadingDialog, LoadingDialog.class.getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hide loading dialog, with check activity working or not
     */
    public void hideLoadingDialog() {
        try {
            if (mLoadingDialog == null) {
                return;
            }
            mLoadingDialog.dismissAllowingStateLoss();
            mLoadingDialog = null;
        } catch (Exception ignored) {
        }
    }

    public void goToDialogFragment(BaseDialogFragment mBaseDialog, Bundle mBundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mBaseDialog.setArguments(mBundle);
        mBaseDialog.show(fragmentManager, mBaseDialog.getClass().getName());
    }

    public void goToFragment(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (mFragStack == null && mFrgManager == null) {
            mFragStack = new ArrayDeque<>();
            mFrgManager = getSupportFragmentManager();
        }
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mBundle != null) {
            mBaseFragment.setArguments(mBundle);
        }
        if (mFragStack != null && mFragStack.size() >= 1) {
            trans.hide(mFragStack.getLast());
        }

        if (mFragStack != null)
            mFragStack.push(mBaseFragment);
        trans.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        trans.add(fragmentContainerId, mBaseFragment, mBaseFragment.getClass().getSimpleName());
        trans.commitAllowingStateLoss();

        mFrgManager.executePendingTransactions();
    }

    public void goToFragmentReplace(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (mFragStack == null && mFrgManager == null) {
            mFragStack = new ArrayDeque<>();
            mFrgManager = getSupportFragmentManager();
        }
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mBundle != null) {
            mBaseFragment.setArguments(mBundle);
        }
        if (mFragStack != null && mFragStack.size() >= 1) {
            trans.hide(mFragStack.getLast());
        }

        if (mFragStack != null)
            mFragStack.push(mBaseFragment);

        trans.replace(fragmentContainerId, mBaseFragment, mBaseFragment.getClass().getSimpleName());
        trans.commitAllowingStateLoss();
        mFrgManager.executePendingTransactions();
    }

    public void goToFragment(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle,
                             OnFragmentCloseCallback callback) {
        mBaseFragment.setOnFragmentCloseCallback(callback);
        goToFragment(fragmentContainerId, mBaseFragment, mBundle);
    }

    public void goToFragmentCallBackData(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle,
                             OnFragmentDataCallBack callback) {
        mBaseFragment.setOnFragmentDataCallback(callback);
        goToFragment(fragmentContainerId, mBaseFragment, mBundle);
    }

    public boolean onBackFragment() {
        if (mFrgManager == null)
            return false;
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mFragStack.size() > 0) {
            trans.remove(mFragStack.pop());
            if (mFragStack.size() > 0) {
                trans.show(mFragStack.getLast());
                mFragStack.getLast().onResume();
            }
            trans.commitAllowingStateLoss();
            ActivityUtils.hideSoftInput(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean onBackFragment(BaseFragment mBaseFragment) {
        if (mFrgManager == null)
            return false;
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mFragStack.size() > 0) {
            trans.addToBackStack(mBaseFragment.getClass().getSimpleName());
            if (mFragStack.size() > 0) {
                trans.show(mFragStack.getLast());
                mFragStack.getLast().onResume();
            }
            trans.commitAllowingStateLoss();
            ActivityUtils.hideSoftInput(this);
            return true;
        } else {
            return false;
        }
    }

    public void onBackAllFragments() {
        dismissAlertDialog();
        if (mFrgManager == null) {
            return;
        }
        FragmentTransaction trans = mFrgManager.beginTransaction();
        while (mFragStack.size() > 0) {
            trans.remove(mFragStack.pop());
        }
        try {
            runOnUiThread(trans::commitNowAllowingStateLoss);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActivityUtils.hideSoftInput(this);
    }

    public void openActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void openActivity(Class<?> clazz) {
        openActivity(clazz, null);
    }

    public void openActivity(Class<?> clazz, boolean isFinish) {
        openActivity(clazz);
        if (isFinish) {
            finish();
        }
    }

    public void openActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getString(title), getString(message), cancellable, listener);
    }

    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                @StringRes int acceptLabel, @StringRes int cancelLabel,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getString(title), getString(message),
                getString(acceptLabel), getString(cancelLabel), cancellable, listener);
    }

    public void showAlertDialog(String message) {
        try {
            if (isFinishing()) {
                return;
            }
            dismissAlertDialog();
            alertFragment = AlertFragment.newInstance();
            alertFragment.setMessage(message);
            alertFragment.setButtonOk(getString(R.string.common_close));
            alertFragment.setShowButtonOk(true);
            alertFragment.setShowButtonCancel(false);
            alertFragment.setCancelable(false);
            addAlertDialog(alertFragment);
        } catch (Exception ignored) {
        }
    }

    public void showAlertDialog(String message, AlertDialogListener listener) {
        try {
            if (isFinishing()) {
                return;
            }
            dismissAlertDialog();
            alertFragment = AlertFragment.newInstance();
            alertFragment.setMessage(message);
            alertFragment.setButtonOk(getString(R.string.common_close));
            alertFragment.setShowButtonOk(true);
            alertFragment.setShowButtonCancel(false);
            alertFragment.setListener(listener);
            alertFragment.setCancelable(false);
            addAlertDialog(alertFragment);
        } catch (Exception ignored) {
        }
    }

    public void showAlertDialog(String title, String message, boolean cancellable, AlertDialogListener listener) {
        try {
            if (isFinishing()) {
                return;
            }
            dismissAlertDialog();
            alertFragment = AlertFragment.newInstance();
            alertFragment.setTitle(title);
            alertFragment.setMessage(message);
            alertFragment.setShowButtonCancel(cancellable);
            alertFragment.setListener(listener);
            alertFragment.setCancelable(false);
            addAlertDialog(alertFragment);
        } catch (Exception ignored) {
        }
    }

    public void showAlertDialog(String message, String acceptLabel, AlertDialogListener listener) {
        try {
            if (isFinishing()) {
                return;
            }
            dismissAlertDialog();
            alertFragment = AlertFragment.newInstance();
            alertFragment.setMessage(message);
            alertFragment.setShowButtonCancel(false);
            alertFragment.setButtonOk(acceptLabel);
            alertFragment.setListener(listener);
            alertFragment.setCancelable(false);
            addAlertDialog(alertFragment);
        } catch (Exception ignored) {
        }
    }

    public void showAlertSuccessDialog(String message, String acceptLabel, AlertDialogListener listener) {
        try {
            if (isFinishing()) {
                return;
            }
            dismissAlertDialog();
            alertFragment = AlertFragment.newInstance();
            alertFragment.setMessage(message);
            alertFragment.setShowButtonCancel(false);
            alertFragment.setButtonOk(acceptLabel);
            alertFragment.setListener(listener);
            alertFragment.setCancelable(false);
            alertFragment.setShowIvSuccess(true);
            addAlertDialog(alertFragment);
        } catch (Exception ignored) {
        }
    }

    public void showAlertDialog(String title, String message, String acceptLabel, String cancelLabel, boolean cancellable, AlertDialogListener listener) {
        try {
            if (isFinishing()) {
                return;
            }
            dismissAlertDialog();
            alertFragment = AlertFragment.newInstance();
            alertFragment.setTitle(title);
            alertFragment.setMessage(message);
            alertFragment.setShowButtonOk(true);
            alertFragment.setButtonOk(acceptLabel);
            alertFragment.setShowButtonCancel(true);
            alertFragment.setButtonCancel(cancelLabel);
            alertFragment.setListener(listener);
            alertFragment.setCancelable(cancellable);
            addAlertDialog(alertFragment);
        } catch (Exception ignored) {

        }
    }

    private void addAlertDialog(AlertFragment alertFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(alertFragment, AlertFragment.class.getSimpleName());
        ft.commitAllowingStateLoss();
    }

    private void dismissAlertDialog() {
        try {
            if (alertFragment != null) {
                alertFragment.dismissAllowingStateLoss();
                alertFragment = null;
            }
        } catch (Exception ignored) {
        }
    }

    public void showShortToast(@StringRes int message) {
        showShortToast(getString(message));
    }

    public void showLongToast(@StringRes int message) {
        showLongToast(getString(message));
    }

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public boolean assetHasNetwork() {
        boolean hasNetwork = CommonUtils.isConnectingToInternet(getBaseContext());
        if (!hasNetwork) {
            showAlertDialog(getString(R.string.common_error_no_internet));
        }
        return hasNetwork;
    }
}
