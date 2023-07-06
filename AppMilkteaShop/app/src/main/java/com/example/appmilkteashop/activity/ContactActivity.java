package com.example.appmilkteashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmilkteashop.R;
import com.example.appmilkteashop.databinding.ActivityContactBinding;
import com.example.appmilkteashop.dto.ResponseErrorDto;
import com.example.appmilkteashop.helper.ApiHelper;
import com.example.appmilkteashop.model.Contact;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {
    ActivityContactBinding activityContactBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact);

        activityContactBinding.btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = setInforToContactModel();
                if (contact != null) {
                    callApiCreateContact(contact);
                }
            }
        });
    }

    public void callApiCreateContact(Contact contact) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenValue", 0);
        String token = "Bearer " + sharedPreferences.getString("token", "");

        ApiHelper.apiService.createContact(token, contact).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()) {
                    dialogSendSuccess();
                } else {
                    ResponseErrorDto error = new Gson().fromJson(response.errorBody().charStream(), ResponseErrorDto.class);
                    if (error == null || error.getStatus().equals("INTERNAL_SERVER_ERROR")) {
                        startActivity(new Intent(ContactActivity.this, ExceptionActivity.class));
                    }
                    Toast.makeText(ContactActivity.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Toast.makeText(ContactActivity.this, "Create Contact unsuccessfully",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public Contact setInforToContactModel() {
        Contact contact = new Contact();
        contact.setSenderName(activityContactBinding.edtSenderName.getText().toString());
        contact.setEmail(activityContactBinding.edtEmail.getText().toString());
        contact.setPhone(activityContactBinding.edtPhone.getText().toString());
        contact.setTitle(activityContactBinding.edtTitleContact.getText().toString());
        contact.setMessage(activityContactBinding.edtMessage.getText().toString());
        contact.setEnabled(true);

        if (contact.getSenderName().equals("") || contact.getEmail().equals("") || contact.getPhone().equals("")
                || contact.getTitle().equals("") || contact.getMessage().equals("")) {
            Toast.makeText(ContactActivity.this, "Please complete all information before send", Toast.LENGTH_SHORT).show();
            return null;
        }
        return contact;
    }

    public void dialogSendSuccess() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        dialog.setContentView(R.layout.validate_success_dialog);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                startActivity(new Intent(ContactActivity.this, HomeActivity.class));
            }
        });

        TextView tvTitle = dialog.findViewById(R.id.tvTitleErr);
        TextView tvInfor = dialog.findViewById(R.id.tvInforErr);
        tvTitle.setText("Send Report Successfully");
        tvInfor.setText("Your information has been recorded\nWe will review it soon. Thank you so much");

        dialog.show();
    }
}