package com.tqc.gdd01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class GDD01 extends Activity
{
  private EditText etheight;
  private EditText etweight;
  private RadioButton rb1;
  private RadioButton rb2;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Button b1 = (Button) findViewById(R.id.button1);
    etheight = findViewById(R.id.etheight);
    etweight = findViewById(R.id.etweight);
    rb1 = findViewById(R.id.rb1);
    rb2 = findViewById(R.id.rb2);
    b1.setOnClickListener(new Button.OnClickListener() {
      public void onClick(View v)
      {
        //從輸入介面中取出了的身高、體重值，要將身高、體重值傳送給 child_Activity 後作計算
        double height = Double.parseDouble(etheight.getText().toString());
        double weight = Double.parseDouble(etweight.getText().toString());
        double bmi = weight / (height* height);

        //判斷勾選的是男性/女性
        String sex;
        if(rb1.isChecked()){
          sex = "M";
        } else{
          sex = "F";
        }
        //這些附加在 Intent 上的訊息都儲存在 Bundle 物件中
        Intent intent = new Intent();
        intent.setClass(GDD01.this,GDD01_child.class);

        Bundle bundle = new Bundle();
        bundle.putDouble("height",height);
        bundle.putDouble("weight",weight);
        bundle.putString("Sex",sex);
        //透過「intent.putExtras(bundle)」敘述，將「bundle」 物件附加在 Intent 上，隨著 Intent 送出而送出
        intent.putExtras(bundle);
        startActivityForResult(intent,0);
      }
      private void onActivityResult(int requestCode, int resultCode,Intent data){
        switch (resultCode){
          case RESULT_OK:
            Bundle bundle = data.getExtras();
            String Sex = bundle.getString("Sex");
            double height = bundle.getDouble("height");
            double weight = bundle.getDouble("weight");

            etheight.setText("" + height);
            etweight.setText("" + weight);
            if(Sex.equals("M")){
              rb1.setChecked(true);
            } else {
              rb2.setChecked(true);
            }
            break;
          default:
            break;
        }
      }
    });
  }

}
