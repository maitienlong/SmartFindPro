package com.poly.smartfindpro.base;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.callback.AlertDialogListener;


public abstract class BaseDialog extends Dialog implements BaseScreen {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
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
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showMessage(message);
        }
    }

    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    public void hideLoadingDialog() {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).hideLoadingDialog();
        }
    }

    public void showLoadingDialog(final boolean cancelable) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showLoadingDialog();
        }
    }

    public void showAlertDialog(String message) {
        showAlertDialog(getContext().getString(R.string.common_notify), message, false, null);
    }

    public void showAlertDialog(int title, int message,
                                boolean cancellable,
                                AlertDialogListener listener) {
        showAlertDialog(getContext().getString(title), getContext().getString(message), cancellable, listener);
    }

    public void showAlertDialog(int title, int message,
                                int acceptLabel, int cancelLabel,
                                boolean cancellable,
                                AlertDialogListener listener) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showAlertDialog(title, message,
                    acceptLabel, cancelLabel, cancellable, listener);
        }
    }

    public void showAlertDialog(String title, String message,
                                boolean cancellable,
                                AlertDialogListener listener) {

        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showAlertDialog(title, message, cancellable, listener);
        }
    }

    public void showAlertDialog(String title, String message,
                                String acceptLabel, String cancelLabel,
                                boolean cancellable,
                                AlertDialogListener listener) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showAlertDialog(title, message, acceptLabel,
                    cancelLabel, cancellable, listener);
        }
    }

    public void showShortToast(int message) {
        showShortToast(getContext().getString(message));
    }

    public void showLongToast(int message) {
        showLongToast(getContext().getString(message));
    }

    public void showShortToast(String message) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showShortToast(message);
        }
    }

    public void showLongToast(String message) {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showLongToast(message);
        }
    }

    public boolean assetHasNetwork() {
        if (getContext() != null && getContext() instanceof BaseActivity) {
            return ((BaseActivity) getContext()).assetHasNetwork();
        }

        return true;
    }
}
