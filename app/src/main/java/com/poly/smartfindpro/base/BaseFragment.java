package com.poly.smartfindpro.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.poly.smartfindpro.R;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.callback.OnFragmentCloseCallback;
import com.poly.smartfindpro.callback.OnFragmentDataCallBack;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseFragment extends Fragment implements BaseScreen {
    private FragmentManager mFrgManager;
    private Deque<BaseFragment> mFragStack;

    private OnFragmentCloseCallback onFragmentCloseCallback;

    private OnFragmentDataCallBack onFragmentDataCallBack;

    private int resultCode = Activity.RESULT_CANCELED;
    private Intent resultData;

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMessage(message);
        }
    }

    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    private void hideLoadingDialog() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingDialog();
        }
    }

    public void showLoadingDialog(final boolean cancelable) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoadingDialog();
        }
    }

    public void goToDialogFragment(BaseDialogFragment mBaseDialog, Bundle mBundle) {
        FragmentManager fragmentManager = getChildFragmentManager();
        mBaseDialog.setArguments(mBundle);
        mBaseDialog.show(fragmentManager, mBaseDialog.getClass().getName());
    }

    public void goToFragment(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (mFragStack == null && mFrgManager == null) {
            mFragStack = new ArrayDeque<>();
            mFrgManager = getChildFragmentManager();
        }
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mBundle != null) {
            mBaseFragment.setArguments(mBundle);
        }
        if (mFragStack != null && mFragStack.size() >= 1 &&
                !mFragStack.getLast().equals(mBaseFragment)) {
            trans.hide(mFragStack.getLast());
        }

        if (mFragStack != null)
            mFragStack.push(mBaseFragment);

        trans.add(fragmentContainerId, mBaseFragment, mBaseFragment.getClass().getSimpleName());
        trans.commit();
        mFrgManager.executePendingTransactions();
    }

    public void goToFragments(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (mFragStack == null && mFrgManager == null) {
            mFragStack = new ArrayDeque<>();
            mFrgManager = getActivity().getSupportFragmentManager();

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

        trans.add(fragmentContainerId, mBaseFragment, mBaseFragment.getClass().getSimpleName());
        trans.commitAllowingStateLoss();
        mFrgManager.executePendingTransactions();
    }

    public boolean onBackFragment() {
        if (mFrgManager == null || mFragStack == null)
            return false;
        FragmentTransaction trans = mFrgManager.beginTransaction();
        if (mFragStack.size() > 0) {
            Fragment fragment = mFragStack.pop();
            Fragment rmFragment = mFrgManager.findFragmentByTag(fragment.getClass().getSimpleName());
            if (rmFragment != null) {
                trans.remove(rmFragment);
                trans.commit();
            }
            mFrgManager.executePendingTransactions();
            return true;
        } else {
            return false;
        }
    }

    public void openActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).openActivityForResult(clazz, bundle, requestCode);
        }
    }

    public void openActivity(Class<?> clazz) {
        openActivity(clazz, null);
    }

    public void openActivity(Class<?> clazz, boolean isFinish) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).openActivity(clazz, isFinish);
        }
    }

    public void openActivity(Class<?> clazz, Bundle bundle) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).openActivity(clazz, bundle);
        }
    }

    public void showAlertDialog(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showAlertDialog(message, getString(R.string.common_close), null);
        }
    }

    public void showAlertDialog(int title, int message,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getString(title), getString(message), cancellable, listener);
    }

    public void showAlertDialog(int title, int message,
                                int acceptLabel, int cancelLabel,
                                boolean cancellable,
                                AlertDialogListener listener) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showAlertDialog(title, message,
                    acceptLabel, cancelLabel, cancellable, listener);
        }
    }

    public void showAlertDialog(String title, String message,
                                boolean cancellable,
                                AlertDialogListener listener) {

        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showAlertDialog(title, message, cancellable, listener);
        }
    }

    public void showAlertDialog(String title, String message,
                                String acceptLabel, String cancelLabel,
                                boolean cancellable,
                                AlertDialogListener listener) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showAlertDialog(title, message, acceptLabel,
                    cancelLabel, cancellable, listener);
        }
    }

    public void showShortToast(int message) {
        showShortToast(getString(message));
    }

    public void showLongToast(int message) {
        showLongToast(getString(message));
    }

    public void showShortToast(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showShortToast(message);
        }
    }

    public void showLongToast(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLongToast(message);
        }
    }

    public boolean assetHasNetwork() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).assetHasNetwork();
        }

        return true;
    }

    public void setOnFragmentCloseCallback(OnFragmentCloseCallback onFragmentCloseCallback) {
        this.onFragmentCloseCallback = onFragmentCloseCallback;
    }
    public void setOnFragmentDataCallback(OnFragmentDataCallBack onFragmentDataCallback) {
        this.onFragmentDataCallBack = onFragmentDataCallback;
    }

    public OnFragmentDataCallBack getOnFragmentDataCallBack() {
        return onFragmentDataCallBack;
    }

    public void setResult(int resultCode, Intent resultData) {
        this.resultCode = resultCode;
        this.resultData = resultData;
    }

    public void setResult(int resultCode) {
        this.resultCode = resultCode;
    }


    public void onBackData(){
        if(getActivity() != null && getActivity() instanceof BaseActivity){
            if(onFragmentDataCallBack != null){
              onFragmentDataCallBack.onResult(resultCode, resultData);
            }
        }
    }


    public void finish() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {

            if (onFragmentCloseCallback != null)
                onFragmentCloseCallback.onClose(resultCode, resultData);

            ((BaseActivity) getActivity()).onBackFragment();
        }
    }
}
