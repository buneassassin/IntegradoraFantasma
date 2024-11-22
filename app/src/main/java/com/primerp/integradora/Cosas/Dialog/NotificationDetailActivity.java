package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.primerp.integradora.R;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        // Obtén los datos del intent
        String title = getIntent().getStringExtra("notification_title");
        String message = getIntent().getStringExtra("notification_message");
        String date = getIntent().getStringExtra("notification_date");

        // Vincula las vistas
        TextView titleView = findViewById(R.id.detail_notification_title);
        TextView messageView = findViewById(R.id.detail_notification_message);
        TextView dateView = findViewById(R.id.detail_notification_date);
        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());
        // Establece los datos
        titleView.setText(title);
        messageView.setText(message);
        dateView.setText(date);

    }
}