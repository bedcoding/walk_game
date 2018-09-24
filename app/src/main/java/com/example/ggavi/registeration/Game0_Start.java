package com.example.ggavi.registeration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Game0_Start extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game0_activity_start);
        Buttons_Working();
    }

    private void Buttons_Working()
    {
        Button New_Game=findViewById(R.id.New_Game_Button_ID);
        Button Load_Game=findViewById(R.id.Load_Game_Button_ID);

        New_Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                SharedPreferences Data_Box= getApplicationContext().getSharedPreferences("Data_Box",MODE_PRIVATE);
                SharedPreferences.Editor editor = Data_Box.edit();
                editor.putInt("Day",0);

                SharedPreferences.Editor Editor=Data_Box.edit();
                Editor.putInt("Family_One_Hungry",100);
                Editor.putInt("Family_One_Thirst",100);
                Editor.putInt("Family_One_Hp",100);
                Editor.putInt("Family_Two_Hungry",100);
                Editor.putInt("Family_Two_Thirst",100);
                Editor.putInt("Family_Two_Hp",100);
                Editor.putInt("Food",15);
                Editor.putInt("Water",10);
                Editor.putInt("Global_Damage",-1);
                Editor.putInt("Family_One_Damage",-1);
                Editor.putInt("Family_Two_Damage",-1);
                Editor.putBoolean("Family_One_Not_Died", true);
                Editor.putBoolean("Family_Two_Not_Died", true);


                Editor.commit();
                editor.commit();
                Intent Launch_Activity=new Intent(Game0_Start.this,Game1_Day.class);
                startActivity(Launch_Activity);
            }
        });

        Load_Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent Launch_Activity=new Intent(Game0_Start.this,Game1_Day.class);
                startActivity(Launch_Activity);
            }
        });

    }

}
