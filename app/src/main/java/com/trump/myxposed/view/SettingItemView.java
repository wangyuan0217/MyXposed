package com.trump.myxposed.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.trump.myxposed.R;
import com.trump.myxposed.util.SPUtil;

/**
 * Author: TRUMP
 * Date:   2022/2/11 0011 14:37
 * Desc:
 */
public class SettingItemView extends FrameLayout {
    private TextView mTvText;
    private TextView mTvSubheading;
    private Switch mSbBtn;
    private CompoundButton.OnCheckedChangeListener mSbCheckedChangeListener;
    private View mVDivider;
    private View mVContact;
    private String mPreferencesKey;

    public SettingItemView(@NonNull Context context) {
        super(context);
    }

    public SettingItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.item_setting_view, this, true);
        this.mTvText = (TextView) this.findViewById(R.id.tv_text);
        this.mTvSubheading = (TextView) this.findViewById(R.id.tv_subheading);
        this.mSbBtn = (Switch) this.findViewById(R.id.sw);
        this.mVDivider = this.findViewById(R.id.v_divider);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        if (attributes != null) {
            String labelText = attributes.getString(R.styleable.SettingItemView_label_text);
            String subheadingText = attributes.getString(R.styleable.SettingItemView_subheading_text);
            if (subheadingText == null) {
                this.mTvSubheading.setVisibility(GONE);
            } else {
                this.mTvSubheading.setText(subheadingText);
            }

            this.mPreferencesKey = attributes.getString(R.styleable.SettingItemView_preferences_key);

            final int contactViewId = attributes.getResourceId(R.styleable.SettingItemView_contact_view, 0);
            this.mTvText.post(() -> {
                if (contactViewId != 0) {
                    SettingItemView.this.mVContact = ((Activity) context).findViewById(contactViewId);
                }

                SettingItemView.this.changedChecked(SettingItemView.this.getValue());
            });
            this.mTvText.setText(labelText);
            attributes.recycle();
        }

        this.setOnClickListener(v -> {
            boolean isChecked = SettingItemView.this.mSbBtn.isChecked();
            SettingItemView.this.changedChecked(!isChecked);
            if (SettingItemView.this.mSbCheckedChangeListener != null) {
                SettingItemView.this.mSbCheckedChangeListener.onCheckedChanged(SettingItemView.this.mSbBtn, !isChecked);
            }

        });
    }

    public void setCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        this.mSbCheckedChangeListener = listener;
    }

    public boolean isChecked() {
        return this.mSbBtn.isChecked();
    }

    public void setChecked(boolean checked) {
        this.changedChecked(checked);
    }

    private void changedChecked(boolean checked) {
        this.mSbBtn.setChecked(checked);
        if (this.mVContact != null) {
            this.mVContact.setVisibility(checked ? VISIBLE : GONE);
            this.mVDivider.setVisibility(checked ? VISIBLE : GONE);
        }

        this.putValue(checked);
    }

    private boolean getValue() {
        return !TextUtils.isEmpty(this.mPreferencesKey) && SPUtil.getInstance().getBoolean(this.mPreferencesKey);
    }

    private void putValue(boolean value) {
        if (!TextUtils.isEmpty(this.mPreferencesKey)) {
            SPUtil.getInstance().put(this.mPreferencesKey, value);
        }
    }
}