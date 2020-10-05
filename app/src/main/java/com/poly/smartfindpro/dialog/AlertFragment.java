package com.poly.smartfindpro.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.poly.smartfindpro.R;
import com.poly.smartfindpro.callback.AlertDialogListener;

import org.sufficientlysecure.htmltextview.HtmlTextView;


public class AlertFragment extends DialogFragment {
    private String title;

    private String message;

    private String buttonOk;

    private String buttonCancel;

    private boolean showIvSuccess;

    private boolean showButtonOk;

    private boolean showButtonCancel;

    private AlertDialogListener listener;

    public static AlertFragment newInstance() {
        AlertFragment fragment = new AlertFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TransparentDialog);
        return fragment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setButtonOk(String buttonOk) {
        this.buttonOk = buttonOk;
    }

    public void setButtonCancel(String buttonCancel) {
        this.buttonCancel = buttonCancel;
    }

    public void setShowIvSuccess(boolean showIvSuccess) {
        this.showIvSuccess = showIvSuccess;
    }

    public void setShowButtonOk(boolean showButtonOk) {
        this.showButtonOk = showButtonOk;
    }

    public void setShowButtonCancel(boolean showButtonCancel) {
        this.showButtonCancel = showButtonCancel;
    }

    public void setListener(AlertDialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_message, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        ImageView ivSuccess = view.findViewById(R.id.iv_success);
        HtmlTextView tvMessage = view.findViewById(R.id.tv_message);
        Button btnOk = view.findViewById(R.id.btn_ok);
        View viewLine = view.findViewById(R.id.view_line);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setHtml(message);
        }
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(buttonOk)) {
            btnOk.setText(buttonOk);
        }
        if (!TextUtils.isEmpty(buttonCancel)) {
            btnCancel.setText(buttonCancel);
        }
        if (!showButtonCancel) {
            viewLine.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }
        if (showIvSuccess) {
            ivSuccess.setVisibility(View.VISIBLE);
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAccept();
                }
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancel();
                }
                dismiss();
            }
        });
        view.clearFocus();
    }
}
