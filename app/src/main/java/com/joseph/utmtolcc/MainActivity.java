package com.joseph.utmtolcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MaterialButton btn;
    TextInputEditText ed_x_1, ed_y_1, ed_y_2, ed_x_2;
    AutoCompleteTextView auto_1, auto_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_enrg_saise);
        ed_x_1 = findViewById(R.id.ed_x_saise_1);
        ed_x_2 = findViewById(R.id.ed_x_saise_2);
        ed_y_1 = findViewById(R.id.ed_y_saise_1);
        ed_y_2 = findViewById(R.id.ed_y_saise_2);
        auto_1 = findViewById(R.id.auto_list_saise_1);
        auto_2 = findViewById(R.id.auto_list_saise_2);

        ArrayList<String> array_1 = new ArrayList<>();
        ArrayList<String> ref_array=new ArrayList<>();

        array_1.add("Utm Zone 29_Wgs84");
        array_1.add("Utm Zone 30_Wgs84");
        array_1.add("Utm Zone 31_Wgs84");
        array_1.add("Utm Zone 32_Wgs84");
        array_1.add("Utm Zone 29_Nord Sahara 1959");
        array_1.add("Utm Zone 30_Nord Sahara 1959");
        array_1.add("Utm Zone 31_Nord Sahara 1959");
        array_1.add("Utm Zone 32_Nord Sahara 1959");
        array_1.add("Lcc_Nord Algérie (IGN)");
        array_1.add("Lcc_Sud Algérie (IGN)");
        array_1.add("Lcc_Nord Algérie (INCT)");
        array_1.add("Lcc_Sud Algérie (INCT)");
        array_1.add("Lcc_Nord Maroc");
        array_1.add("Lcc_Sud Maroc");

        ref_array.add("epsg:32629");
        ref_array.add("epsg:32630");
        ref_array.add("epsg:32631");
        ref_array.add("epsg:32632");
        ref_array.add("epsg:30729");
        ref_array.add("epsg:30730");
        ref_array.add("epsg:30731");
        ref_array.add("epsg:30732");
        ref_array.add("epsg:30491");
        ref_array.add("epsg:30492");
        ref_array.add("epsg:30791");
        ref_array.add("epsg:30792");
        ref_array.add("epsg:26191");
        ref_array.add("epsg:26192");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.text_auto_itm, array_1);
        auto_1.setAdapter(adapter);
        auto_2.setAdapter(adapter);
        btn.setOnClickListener(v -> {
            String x_1=ed_x_1.getText().toString();
            String y_1=ed_y_1.getText().toString();

            if (x_1.isEmpty()||y_1.isEmpty()){
               if (x_1.isEmpty()){
                   ed_x_1.setError("entre x");
               }
               if (y_1.isEmpty()){
                ed_y_1.setError("entre y");}
            }else {




                String st_auto_1=auto_1.getText().toString();
                String st_auto_2=auto_2.getText().toString();

                String st_1=ref_array.get(array_1.indexOf(st_auto_1));
                String st_2=ref_array.get(array_1.indexOf(st_auto_2));

                CRSFactory crsFactory = new CRSFactory();
                CoordinateReferenceSystem WGS84 = crsFactory.createFromName(st_1);
                CoordinateReferenceSystem UTM = crsFactory.createFromName(st_2);


                CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
                CoordinateTransform wgsToUtm = ctFactory.createTransform(WGS84, UTM);

                ProjCoordinate result = new ProjCoordinate();
                wgsToUtm.transform(new ProjCoordinate(Double.parseDouble(y_1), Double.parseDouble(x_1)), result);

                ed_x_2.setText( Integer.toString((int) result.y));
                /*hghh*/
                ed_y_2.setText(Integer.toString((int) result.x));

            }
        });

    }
}