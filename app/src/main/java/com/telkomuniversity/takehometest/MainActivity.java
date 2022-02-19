package com.telkomuniversity.takehometest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvListUser)
    RecyclerView rvListUser;
    Button button;

    private List<DataItem> listItem;
    private RecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        button = (Button) findViewById(R.id.btn_refresh);

        RestClient.getService().getList().enqueue(new Callback<ListUserResponse>() {
            @Override
            public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                if (response.isSuccessful()) {
                    listItem = response.body().getData();

                    adapter = new RecycleAdapter(listItem, MainActivity.this);
                    rvListUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rvListUser.setAdapter(adapter);

                    new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            listItem.remove(viewHolder.getAdapterPosition());
                            adapter.notifyDataSetChanged();
                        }
                    })).attachToRecyclerView(rvListUser);
                }
            }

            @Override
            public void onFailure(Call<ListUserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Update Data Failed", Toast.LENGTH_SHORT);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.getService().getList().enqueue(new Callback<ListUserResponse>() {
                    @Override
                    public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                        if (response.isSuccessful()) {
                            listItem = response.body().getData();

                            adapter = new RecycleAdapter(listItem, MainActivity.this);
                            rvListUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rvListUser.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ListUserResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Update Data Failed", Toast.LENGTH_SHORT);
                    }
                });
            }
        });

    }

}