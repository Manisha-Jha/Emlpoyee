package com.jams.itsolution.employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class EmpShowSetails extends AppCompatActivity {

    TextView emp_name;
    ImageView emp_img;
    TextView emp_des,emp_age,emp_gen,emp_leave;
    RelativeLayout demand_leave;

    public EMPLeaveDb embDb;
    ListData EmpDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_show_setails);
        embDb = new EMPLeaveDb(this);
        Intent intent = getIntent();
        EmpDetails = (ListData)intent.getSerializableExtra("ListData");

        emp_name = (TextView)findViewById(R.id.emp_name);
        emp_img = (ImageView)findViewById(R.id.emp_img);

        emp_des = (TextView)findViewById(R.id.emp_des);
        emp_age = (TextView)findViewById(R.id.emp_age);
        emp_gen = (TextView)findViewById(R.id.emp_gen);
        emp_leave = (TextView)findViewById(R.id.emp_leave);

        demand_leave = (RelativeLayout)findViewById(R.id.demand_leave);

        emp_name.setText(EmpDetails.getName());

        Glide.with(this).load(EmpDetails.getImage()).into(emp_img);

        emp_des.setText(EmpDetails.getDes());
        emp_age.setText(EmpDetails.getAge());
        emp_gen.setText(EmpDetails.getGen());


        assLeave(embDb.getDays(EmpDetails.getId()));

        demand_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();

            }
        });

    }


    public void showMenu(){

        CharSequence menu[] = new CharSequence[] {"  Half day", "  A day", "  More than a day"};

        final  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Leave Option");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

             if(which==0){
                 insertLeaveDays(EmpDetails.getId(),String.valueOf(0.5));

             }else if(which==1){
                 insertLeaveDays(EmpDetails.getId(),String.valueOf(1));
             }else if(which==2){
                 EditeBox();

             }



            }
        });


        // Set the action buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


            }
        });

        builder.show();


    }


    public void EditeBox(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EmpShowSetails.this);
        alertDialog.setTitle("Enter Days");
        final EditText input = new EditText(EmpShowSetails.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                              insertLeaveDays(EmpDetails.getId(),String.valueOf(input.getText().toString()));

                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    public void insertLeaveDays(String empID,String days){

         if(!embDb.checkIsAvalable(empID)){
            if(embDb.Add(empID,days)){
               assLeave(days);
                Toast.makeText(getApplicationContext(),
                    "Allow leave your  ", Toast.LENGTH_SHORT).show();
               }else {
                 Toast.makeText(getApplicationContext(),
                        "Tech Prob.... ", Toast.LENGTH_SHORT).show();
             }
         }else {
             Toast.makeText(getApplicationContext(),
                     "already your leave granted  " , Toast.LENGTH_SHORT).show();
         }

    }


    public void assLeave(String days){
        String dayVal;
        if(days.equals("0.5")){
            dayVal = "Half day";
        }else if(days.equals("1")){
            dayVal = "a day";
        }else {
          dayVal = days +" days";
        }

        emp_leave.setText(dayVal);
    }


}
