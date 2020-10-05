package com.poly.smartfindpro.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.poly.smartfindpro.callback.AlertDialogListener;

import java.util.Objects;

public abstract class BaseDialogFragment extends DialogFragment implements BaseScreen {
    private boolean isFullScreen = false;

    protected abstract int getStyleDialog();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()), getStyleDialog());
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isFullScreen) {
            Window window = getDialog().getWindow();
            assert window != null;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            Window window = getDialog().getWindow();
            assert window != null;
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

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

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
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
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).goToDialogFragment(mBaseDialog, mBundle);
        }
    }

    public void goToFragment(@IdRes int fragmentContainerId, BaseFragment mBaseFragment, Bundle mBundle) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).goToFragment(fragmentContainerId, mBaseFragment, mBundle);
        }
    }

    public void onBackFragment() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).onBackFragment();
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

    public void showAlertDialog(int title, int message,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getString(title), getString(message), cancellable, listener);
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

    private void showShortToast(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showShortToast(message);
        }
    }

    private void showLongToast(String message) {
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
}
