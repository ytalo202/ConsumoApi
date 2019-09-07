package com.example.consumoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.consumoandroid.api.APIUtils;
import com.example.consumoandroid.api.LaravelService;
import com.example.consumoandroid.model.Resul;
import com.example.consumoandroid.model.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainView {

    LaravelService laravelService;
    List<Task> taskList = new ArrayList<>();
    RecyclerView recyclerView;
    TaskAdapter mAdapter;
    ProgressDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edName, edType, edValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        laravelService = APIUtils.getLaravelService();
        recyclerView = findViewById(R.id.task_list);
        loadData();
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        swipeRefreshLayout = findViewById(R.id.swipelayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                taskList.clear();
                mAdapter.notifyDataSetChanged();

                loadData();
            }
        });

        edName = findViewById(R.id.edNombre);
        edType = findViewById(R.id.edType);
        edValue = findViewById(R.id.edValue);
    }


    @Override
    public void loadData() {

        final Call<List<Task>> call = laravelService.getTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                Log.i("z- onResponse", new Gson().toJson(response.body()));
                taskList = response.body();
                showData(taskList);
                dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("Error12", t.getMessage());
                dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void showData(List<Task> list) {
        mAdapter = new TaskAdapter(list, MainActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void deleteItem(final Task task) {
        dialog.show();
        Call<Resul> call = laravelService.deleteTask(task.getId());
        call.enqueue(new Callback<Resul>() {
            @Override
            public void onResponse(Call<Resul> call, Response<Resul> response) {
                Resul resul = response.body();
                Log.i("z- listaa", new Gson().toJson(resul.getRespuesta()));

                if (resul.getRespuesta().equals("ok")) {
                    Toast.makeText(MainActivity.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
                    taskList.remove(task);
                    mAdapter.load(taskList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Resul> call, Throwable t) {
                Log.e("Error", t.getMessage());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void editItem(Task task) {
        showDialogUpdate(task);
    }


    public void add_task(View view) {

        String name = edName.getText().toString();
        String type = edType.getText().toString();
        String value = edValue.getText().toString();
        if (name.length() > 0 && type.length() > 0 & value.length() > 0) {
            Task task = new Task(name, Integer.parseInt(type),
                    Integer.parseInt(value));

            createItem(task);
            Log.i("z- listaa", new Gson().toJson(task));
        } else {
            Toast.makeText(this, "complete", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void createItem(Task task) {
        dialog.show();
        Call<Task> call = laravelService.createTask(task);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                Task task = response.body();
                taskList.add(task);
                mAdapter.load(taskList);
                mAdapter.notifyDataSetChanged();
                clean();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error", t.getMessage());
            }
        });
    }

    public void clean() {
        edName.setText("");
        edType.setText("");
        edValue.setText("");

        edName.clearFocus();
        edType.clearFocus();
        edValue.clearFocus();
    }


    public void showDialogUpdate(final Task task) {

        final ArrayList<String> espejo = new ArrayList<>();

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Saltos Sucesivos");
        dialog.setContentView(R.layout.dialog_update);
        final EditText digName = dialog.findViewById(R.id.digName);
        final EditText digType = dialog.findViewById(R.id.digType);
        final EditText digValue = dialog.findViewById(R.id.digValue);

        digName.setText(task.getName());
        digType.setText(String.valueOf(task.getType()));
        digValue.setText(String.valueOf(task.getValue()));

        Button bt = dialog.findViewById(R.id.btnOk);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = digName.getText().toString();
                String type = digType.getText().toString();
                String value = digValue.getText().toString();
                Integer id = task.getId();
                if (name.length() > 0 && type.length() > 0 & value.length() > 0) {
                    Task task = new Task(id, name, Integer.parseInt(type),
                            Integer.parseInt(value));

                    updateItem(task);
                    Log.i("z- listaa", new Gson().toJson(task));
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplication(), "complete", Toast.LENGTH_SHORT).show();
                }

            }

        });

        Button btcancel = dialog.findViewById(R.id.btnCancelar);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void updateItem(final Task task) {
        dialog.show();
        Call<Resul> call = laravelService.updateUser(task.getId(), task);
        call.enqueue(new Callback<Resul>() {
            @Override
            public void onResponse(Call<Resul> call, Response<Resul> response) {

                Resul resul = response.body();

                if (resul.getRespuesta().equals("ok")) {
                    Toast.makeText(MainActivity.this, "Elemento actualizado", Toast.LENGTH_SHORT).show();

                    for (Task task1 : taskList) {
                        if (task1.getId() == task.getId()) {
                            task1.setName(task.getName());
                            task1.setType(task.getType());
                            task1.setValue(task.getValue());
                        }
                    }
                    mAdapter.load(taskList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<Resul> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error", t.getMessage());
            }
        });
    }
}
