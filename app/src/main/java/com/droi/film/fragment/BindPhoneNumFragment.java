package com.droi.film.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.film.R;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindPhoneNumFragment extends BackHandledFragment {

    private static final String TAG = "BindPhoneNumFragment";
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.phone_num)
    EditText phoneNumEditText;
    @BindView(R.id.country_code)
    TextView countryCodeEditText;
    ProgressDialog mProgressDialog;
    Context mContext;

    public BindPhoneNumFragment() {
    }

    public static BindPhoneNumFragment newInstance() {
        BindPhoneNumFragment fragment = new BindPhoneNumFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this.getActivity());
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_phone_num, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @OnClick(R.id.next_button)
    void onNextButtonPressed() {
        final DroiUser user = DroiUser.getCurrentUser();
        String countryCode = countryCodeEditText.getText().toString();
        String phoneNum = phoneNumEditText.getText().toString();
        user.setPhoneNumber(countryCode + phoneNum);
        showInValidationProgress();
        user.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (aBoolean && droiError.isOk()) {
                    user.validatePhoneNumberInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            hideInValidationProgress();
                            if (droiError.isOk()) {
                                Log.i(TAG, "sendPinCode:success");
                                if (mListener != null) {

                                }
                            } else {
                                Log.i(TAG, "sendPinCode:failed:" + droiError.toString());
                                Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    hideInValidationProgress();
                    Log.i(TAG, "sendPinCode:user:" + droiError.toString());
                    Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showInValidationProgress() {
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage("验证中");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    public void hideInValidationProgress() {
        mProgressDialog.cancel();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "BindPhoneNumFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "BindPhoneNumFragment");
    }
}
