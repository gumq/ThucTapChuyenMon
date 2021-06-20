package com.tranlequyen.appdubaothoitiet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tranlequyen.appdubaothoitiet.weatherapp.db.Databases;
import com.tranlequyen.appdubaothoitiet.weatherapp.db.TaskCity;
import com.tranlequyen.appdubaothoitiet.weatherapp.db.TaskAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {
  CardView cardView;
    Databases databases;
    ListView lvTask;
    ArrayList<TaskCity> taskCityArrayList;
    TaskAdapter taskAdapter;
    public static final String DATABASE_NAME = "note_db_sqlite";
    public static final String Data_PATH = "/databases/";
    public static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        addViews();
        CopydatabasefromAssets();
     PrepareDB ();
    // LoadData ();
     GetData ();
    }



    private void CopydatabasefromAssets() {
        File dbFile = getDatabasePath ( "note_db.sqlite" );
        if(!dbFile.exists ()){
            //sao chep du lieu
            copyDatabase();
        }else{
            dbFile.delete ();
            copyDatabase ();
        }
    }

    private void copyDatabase() {
        try {
            InputStream myInput = getAssets ().open (  "note_db.sqlite");
            String outFileName = getApplicationInfo ().dataDir+Data_PATH+"note_db.sqlite";
            File f = new File(getApplicationInfo ().dataDir+Data_PATH);
            if(!f.exists ()){
                f.mkdir ();
            }
            OutputStream myOutPut = new FileOutputStream ( outFileName );
            byte[] buffer = new byte[1024];
            int len;

            while ((len =myInput.read(buffer))>0){
                myOutPut.write(buffer,0,len);
            }
            myOutPut.flush ();
            myInput.close ();
            myOutPut.close ();

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void addViews() {
        cardView = findViewById ( R.id.cardview );
        lvTask = findViewById(R.id.lvTask);
        taskCityArrayList = new ArrayList<> ();
        taskAdapter = new TaskAdapter(CityActivity.this, R.layout.item_row_city,
                taskCityArrayList );
        lvTask.setAdapter(taskAdapter);
    }


    private void PrepareDB() {
        //Create table

        databases = new Databases(this, "note_db.sqlite", null, 1);
        databases.QueryData(
                "CREATE TABLE IF NOT EXISTS Cities(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "Cityname VARCHAR(200))"


        );
        databases.QueryData("INSERT INTO Cities VALUES(null, 'HA NOI')");
        databases.QueryData("INSERT INTO Cities VALUES(null, 'HO CHI MINH')");
        databases.QueryData("INSERT INTO Cities VALUES(null, 'HAI PHONG')");
        databases.QueryData("INSERT INTO Cities VALUES(null, 'VINH')");
        databases.QueryData("INSERT INTO Cities VALUES(null, 'HUE')");

    }
 private  void LoadData(){
     SQLiteDatabase database = openOrCreateDatabase("note_db.sqlite", MODE_PRIVATE, null);
     Cursor cursor = database.rawQuery("SELECT * FROM " + "Cities", null,null);

     while(cursor.moveToNext()){
         int taskId = cursor.getInt(0);
         String taskNameCity = cursor.getString(1);
         taskCityArrayList.add(new TaskCity (taskId, taskNameCity.toUpperCase ()));

     }
     cursor.close(); taskAdapter.notifyDataSetChanged();
 }
    private void GetData() {
        Cursor c = databases.GetData("SELECT * FROM Cities");
        taskCityArrayList.clear();
        while(c.moveToNext()){
            int taskId = c.getInt(0);
            String taskNameCity = c.getString(1);
            taskCityArrayList.add(new TaskCity (taskId, taskNameCity.toUpperCase ()));
        }
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnAddTask){
            openDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        final Dialog dialog = new Dialog (this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edtTaskName = dialog.findViewById(R.id.edtTask);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskNameCity = edtTaskName.getText().toString();
                if(taskNameCity.equals("")){
                    Toast.makeText(CityActivity.this, "Please enter task name CITY!", Toast.LENGTH_SHORT).show();
                }else{
                    databases.QueryData("INSERT INTO Cities VALUES(null, '" + taskNameCity + "')");
                    Toast.makeText(CityActivity.this, R.string.Addedsuccessful, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    GetData();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openDialogEditTask(final int taskId, String taskNameCity){
        final Dialog dialog = new Dialog (this);
        dialog.setContentView(R.layout.custom_dialog_edit);

        final TextView edtTaskName = dialog.findViewById(R.id.edtEditTask);
        Button btnEditTask = dialog.findViewById(R.id.btnEdit);
        Button btnCancel = dialog.findViewById(R.id.btnCancelEdit);

        edtTaskName.setText(taskNameCity);

        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTaskNameCity = edtTaskName.getText().toString();
                databases.QueryData("UPDATE Cities SET Cityname = '" + newTaskNameCity +
                        "' WHERE Id = " +
                        taskId);
                Toast.makeText(CityActivity.this, R.string.DeletedSucess,
                        Toast.LENGTH_LONG).show();
                dialog.dismiss();
                GetData();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void deleteTask(final int taskId, String taskName){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString( R.string.sureDelete) + taskName + "?");
        dialog.setPositiveButton( R.string.YES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databases.QueryData("DELETE FROM Cities WHERE Id = " + taskId);
                Toast.makeText(CityActivity.this, R.string.deletesuccessfull,
                        Toast.LENGTH_LONG).show();
                GetData();
            }
        });
        dialog.setNegativeButton( R.string.NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
}
