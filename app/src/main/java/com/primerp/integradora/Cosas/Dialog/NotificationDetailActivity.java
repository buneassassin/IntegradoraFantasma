package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.ViewModelFactory.TinacoDetalleViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.NotificationDetailViewModel;
import com.primerp.integradora.Cosas.viewmodel.TinacoDetalleViewModel;
import com.primerp.integradora.R;

public class NotificationDetailActivity extends AppCompatActivity {

    private NotificationDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        viewModel = new ViewModelProvider(this).get(NotificationDetailViewModel.class);

        String title = getIntent().getStringExtra("notification_title");
        String message = getIntent().getStringExtra("notification_message");
        String date = getIntent().getStringExtra("notification_date");

        viewModel.setNotificationDetails(title, message, date);

        TextView titleView = findViewById(R.id.detail_notification_title);
        TextView messageView = findViewById(R.id.detail_notification_message);
        TextView dateView = findViewById(R.id.detail_notification_date);
        ImageView backIcon = findViewById(R.id.iconback);

        viewModel.getTitle().observe(this, titleView::setText);
        viewModel.getMessage().observe(this, messageView::setText);
        viewModel.getDate().observe(this, dateView::setText);

        backIcon.setOnClickListener(v -> finish());
    }
}
