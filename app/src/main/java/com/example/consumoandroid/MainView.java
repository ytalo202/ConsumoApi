package com.example.consumoandroid;

import com.example.consumoandroid.model.Task;

import java.util.List;

public interface MainView {

    void loadData();

    void showData(List<Task> response);

    void deleteItem(Task task);

    void editItem(Task task);

    void createItem(Task task);

    void updateItem(Task task);


}
