package com.example.admin.simplecalculaterapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private int tableIndex=0;
    private   TableLayout table;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams lparam;
    LinearLayout.LayoutParams lparam2;
    LinearLayout.LayoutParams lparam3;
    AlertDialog.Builder alt_bld;
    AlertDialog dialog;
    AlertDialog.Builder alt_bld2;
    AlertDialog dialog2;
    LinearLayout layout;
    TextView result;
    ArrayList<String> dataKeySave = new ArrayList<>();
    HashMap<String, String > dataSave = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = (TableLayout) findViewById(R.id.tb_layout);
        result = findViewById(R.id.result_tx);
        table.setStretchAllColumns(true);
        linearLayout = new LinearLayout(this);
        linearLayout.setGravity(LinearLayout.HORIZONTAL);
       // linearLayout.setWeightSum(9);
        lparam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lparam.weight = 3;
        lparam2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lparam2.weight = 3;
        lparam3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lparam3.weight = 3;
        TextView tt= new TextView(this);
        tt.setLayoutParams(lparam);
        TextView t1= new TextView(this);
        t1.setLayoutParams(lparam);
        TextView t2= new TextView(this);
        t2.setLayoutParams(lparam2);
        TextView t3= new TextView(this);
        t3.setLayoutParams(lparam3);
        tt.setText("재료명");
        tt.setTextSize(13);
        tt.setGravity(Gravity.CENTER);
        tt.setBackgroundResource(R.drawable.edge);
        t1.setText("베이스 재료 \n총 가격(원)");
        t1.setTextSize(13);
        t1.setGravity(Gravity.CENTER);
        t1.setBackgroundResource(R.drawable.edge);
        t2.setText("베이스 재료 총 무게(g,ml 등)");
        t2.setGravity(Gravity.CENTER);
        t2.setTextSize(13);

        t2.setBackgroundResource(R.drawable.edge);
        t3.setText("사용량(단위 동일)");
        t3.setGravity(Gravity.CENTER);
        t3.setTextSize(13);
        t3.setBackgroundResource(R.drawable.edge);
        TableRow tr = new TableRow(this);
        linearLayout.addView(tt);
        linearLayout.addView(t1);
        linearLayout.addView(t2);
        linearLayout.addView(t3);

        tr.addView(linearLayout);
        table.addView(tr,tableIndex++);

        Button add_bt = findViewById(R.id.add_bt);
        add_bt .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText("");
                edt2.setText("");
                edt3.setText("");
                edt4.setText("");
                dialog.show();

            }
        });
        Button del_bt = findViewById(R.id.delete_bt);
        del_bt .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTable(tableIndex-1);
            }
        });
        Button cal_bt = findViewById(R.id.cal_bt);
        cal_bt .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal();
            }
        });
//        Button mod_bt = findViewById(R.id.mod_bt);
//        mod_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        settingUI();
        if(alt_bld==null) {
            alt_bld = new AlertDialog.Builder(this);
            alt_bld.setTitle("설정")
                    .setCancelable(false)
                    .setView(layout)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            boolean isSet = false;
                            int index=0;

                            String tmp="";
                            if (dataSave.containsKey(edt1.getText().toString())) {
                                tmp+="| 재료명 |";
                                isSet=false;
                            }
                            try {
                                double test = Double.valueOf(edt2.getText().toString());
                                isSet=true;
                            }
                            catch (Exception e)
                            {
                                tmp+="| 총 가격 |";
                                isSet=false;
                            }
                            try {
                                double tD = Double.valueOf(edt3.getText().toString());
                                isSet=true;
                            }
                            catch (Exception e)
                            {
                                tmp+="| 총 무게 |";
                                isSet=false;
                            }
                            try {
                                int tI = Integer.valueOf(edt4.getText().toString());
                                isSet=true;
                            }
                            catch (Exception e)
                            {
                                tmp+="| 사용량 |";
                                isSet=false;
                            }

                            if(!isSet) {

                                Toast.makeText(getApplicationContext(), tmp+" 잘못된 데이터를 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                dataKeySave.add(edt1.getText().toString());
                                dataSave.put(edt1.getText().toString(),edt2.getText().toString()+"/"+edt3.getText().toString()+"/"+edt4.getText().toString());
                                if(table_mod) {
                                    deleteTable(table_index);
                                    dataKeySave.add(edt1.getText().toString());
                                    dataSave.put(edt1.getText().toString(),edt2.getText().toString()+"/"+edt3.getText().toString()+"/"+edt4.getText().toString());
                                }
                                addTable(edt1.getText().toString(), edt2.getText().toString(), edt3.getText().toString(), edt4.getText().toString());
                                table_mod=false;
                                dialog.dismiss();
                            }
                        }

                    });
            dialog = alt_bld.create();
        }



        if(alt_bld2==null){
            alt_bld2 = new AlertDialog.Builder(this);
            alt_bld2.setTitle("해당 테이블을 수정하시겠습니까?")
                    .setCancelable(false)
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog23, int id) {
                            dialog2.dismiss();
                        }

                    })
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog23, int id) {
                            String[] data = dataSave.get(table_tag).split("/");
                            edt1.setText(table_tag);
                            edt2.setText(data[0]);
                            edt3.setText(data[1]);
                            edt4.setText(data[2]);
                            dialog.show();
                            dialog2.dismiss();
                        }

                    });
            dialog2 = alt_bld2.create();
        }
    }


    EditText edt1 ;
    EditText edt2 ;
    EditText edt3 ;
    EditText edt4;
    String table_tag="";
    int table_index=0;
    boolean table_mod=false;
    public void settingUI(){
        //보류
//        RecyclerView recyclerViewDate = new RecyclerView(context);
//        recyclerViewDate.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(context);
//        recyclerViewDate.setLayoutManager(layoutManager);
//        rvDateAdapter = new RecycleViewDateAdapter(dateStr,context);
//        recyclerViewDate.setAdapter(rvDateAdapter);
//


         edt1 = new EditText(this);
         edt2 = new EditText(this);
         edt3 = new EditText(this);
         edt4 = new EditText(this);
        edt1.setHint("재료명을 입력하세요");
        edt2.setHint("해당 재료의 총 가격을 입력하세요");
        edt3.setHint("해당 재료의 총 무게을 입력하세요");
        edt4.setHint("해당 재료의 사용량(무게)를 입력하세요");

        TextView et_text = new TextView(this);
        et_text.setGravity(Gravity.CENTER);
        TextView et1_text = new TextView(this);
        et1_text.setGravity(Gravity.CENTER);
        TextView et2_text = new TextView(this);
        et2_text.setGravity(Gravity.CENTER);
        TextView et3_text = new TextView(this);
        et3_text.setGravity(Gravity.CENTER);
        Space tmp = new Space(this);
        tmp.setMinimumHeight(50);
        Space tmp1 = new Space(this);
        tmp1.setMinimumHeight(50);
        Space tmp2 = new Space(this);
        tmp2.setMinimumHeight(50);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        et_text.setText("재료명");
        //date_text.setText(Influx_Java.dateToDate());
        layout.addView(et_text);
        layout.addView(edt1);
        layout.addView(tmp);
        et1_text.setText("베이스 재료 총 가격(원)");
        layout.addView(et1_text);
        layout.addView(edt2);
        layout.addView(tmp1);
        et2_text.setText("베이스 재료 총 무게(g,ml)");
        layout.addView(et2_text);
        layout.addView(edt3);
        layout.addView(tmp2);
        et3_text.setText("베이스 사용량 무게(g,ml)");
        layout.addView(et3_text);
        layout.addView(edt4);

    }

    private void addTable(String tStr,String pStr,String wStr,String uStr){
        linearLayout = new LinearLayout(this);

        final TextView tt= new TextView(this);
        tt.setLayoutParams(lparam);
        TextView t1= new TextView(this);
        t1.setLayoutParams(lparam);
        TextView t2= new TextView(this);
        t2.setLayoutParams(lparam2);
        TextView t3= new TextView(this);
        t3.setLayoutParams(lparam3);
        tt.setText(tStr);
        tt.setTextSize(13);
        tt.setGravity(Gravity.CENTER);
        tt.setBackgroundResource(R.drawable.edge);
        t1.setText(pStr);
        t1.setTextSize(13);
        t1.setGravity(Gravity.CENTER);
        t1.setBackgroundResource(R.drawable.edge);
        t2.setText(wStr);
        t2.setGravity(Gravity.CENTER);
        t2.setTextSize(13);

        t2.setBackgroundResource(R.drawable.edge);
        t3.setText(uStr);
        t3.setGravity(Gravity.CENTER);
        t3.setTextSize(13);
        t3.setBackgroundResource(R.drawable.edge);
        final TableRow tr = new TableRow(this);
        linearLayout.addView(tt);
        linearLayout.addView(t1);
        linearLayout.addView(t2);
        linearLayout.addView(t3);

        tr.addView(linearLayout);
        tr.setTag(tStr);
        tr.setId(tableIndex);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                table_mod=true;
                table_index = tr.getId();
                table_tag=(String)tr.getTag();
                dialog2.show();
            }
        });
        table.addView(tr,tableIndex++);
    }
    private void deleteTable(int tindex){
        if(tindex<1)
            tableIndex=1;
        else {
            dataSave.remove(dataKeySave.get(tindex-1));
            dataKeySave.remove(tindex-1);
            tableIndex--;
            table.removeViewAt(tindex);
        }
    }

    private void cal(){
        Set key = dataSave.keySet();
        StringBuilder logStr = new StringBuilder();
        /*
         * 출력 형식
         * 재료명 사용량 원가
         *
         *
         * 총 원가 합 :
         * */
        double sum = 0;
        try {
            logStr.append("재료명").append("\t").append("\t").append("사용량").append("\t").append("\t").append("계산 원가").append("\n");
            for (Object value : key) {

                String keyName = (String) value;
                String[] tmp = dataSave.get(keyName).split("/");

                double price = Calculater.setData(Double.valueOf(tmp[0]), Double.valueOf(tmp[1]), Double.valueOf(tmp[2]));
                sum += price;
                logStr.append(keyName).append("\t").append("\t").append(tmp[2]).append("\t").append("\t").append(price).append("\n");

            }
            logStr.append("총 원가 합 : ").append((int) sum).append("원");
            result.setText(logStr.toString());
        }
        catch (Exception e){

        }
    }
}
