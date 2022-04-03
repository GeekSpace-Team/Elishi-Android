package com.elishi.android.Common;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.elishi.android.Fragment.Profile.CreateAccount;
import com.elishi.android.R;
import com.elishi.android.databinding.AlertDialogBinding;

public class AppAlertDialog {
    private Context context;
    private Dialog dialog;
    private AlertDialogBinding binding;
    public interface AlertListener{
        public void onCancelClickListener();
        public void onOkClickListener();
    }
    private AlertListener listener;

    public AppAlertDialog(Context context){
        this.context=context;
        binding=AlertDialogBinding.inflate(LayoutInflater.from(context),null,false);
        dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    public void setTitle(String title){
        binding.alertTitle.setText(title);
    }

    public void setAlertListener(AlertListener listener){
        this.listener=listener;
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancelClickListener();
            }
        });
        binding.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOkClickListener();
            }
        });
    }




    public void show() {
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }


}
