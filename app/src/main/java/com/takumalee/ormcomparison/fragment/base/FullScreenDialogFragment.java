package com.takumalee.ormcomparison.fragment.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.takumalee.ormcomparison.R;


/**
 * Created by TakumaLee on 15/2/28.
 */
public class FullScreenDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.fullScreenDialog);
        return dialog;
    }

}
