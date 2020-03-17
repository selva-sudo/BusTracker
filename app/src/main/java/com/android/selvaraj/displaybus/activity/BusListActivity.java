package com.android.selvaraj.displaybus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.selvaraj.displaybus.Interface.RecyclerViewItemClickListener;
import com.android.selvaraj.displaybus.R;
import com.android.selvaraj.displaybus.adapter.BusAdapter;
import com.android.selvaraj.displaybus.utils.GetAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class BusListActivity extends AppCompatActivity {

    private final String TAG = BusListActivity.class.getSimpleName();
    private RecyclerView rvBusList;
    private TextView tvUserEmail;
    private String[] busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        initViews();
        getUserDetails();
        prepareBusData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvBusList.setLayoutManager(layoutManager);
        rvBusList.setHasFixedSize(true);
        rvBusList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvBusList.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewItemClickListener listener = new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(BusListActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BusListActivity.this, DisplayActivity.class);
                FirebaseAuth.getInstance().signOut();
                loginToFireBase(busList[position]);
                startActivity(intent);
            }
        };
        BusAdapter adapter = new BusAdapter(this, listener);
        rvBusList.setAdapter(adapter);
        adapter.updateData(busList, this);
    }

    private void loginToFireBase(String s) {
        String[] getBus;
        getBus = GetAuth.getUserDetails(s);
        String email = getBus[0];
        String password = getBus[1];
        // Authenticate with Firebase and subscribe to updates
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "firebase auth success");
                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }

    private void initViews() {
        rvBusList = findViewById(R.id.rv_bus_list);
        tvUserEmail = findViewById(R.id.tv_welcome_text);
    }

    private void prepareBusData() {
        busList = new String[]{"Bus 1", "Bus 2", "Bus 3", "Bus 4", "Bus 5", "Bus 6", "Bus 7", "Bus 8", "Bus 9", "Bus 10", "Bus 11", "Bus 12", "Bus 13", "Bus 14", "Bus 15", "Bus 16", "Bus 17", "Bus 18", "Bus 19", "Bus 20"};
    }

    private void getUserDetails() {
        String authEmail = getIntent().getStringExtra("Name");
        tvUserEmail.setText("Welcome " + authEmail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about_us:
                openAboutUs();
                return true;
            case R.id.menu_logout:
                createLogoutAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAboutUs() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.about_us_page, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setPositiveButton("Done", null);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void showDetails(int number) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        alert.setTitle("About bus");
        View view = getLayoutInflater().inflate(R.layout.view_bus, null);
        alert.setView(view);
        alert.setPositiveButton("Close", null);
        alert.create();
        alert.show();
    }

    private void createLogoutAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout").
                setMessage("Are you sure to Logout??")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(BusListActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null);
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}
