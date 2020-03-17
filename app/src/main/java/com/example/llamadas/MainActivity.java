package com.example.llamadas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtTel, txtText;
    SmsManager smsManager;
    Context context;
    BroadCastLlamadas broadcastSms;
    private TelephonyManager telephonyManager;
    public String entrante;
    static public String Numero;
    //Mensaje mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTel =  findViewById(R.id.txtTel);
        txtText = findViewById(R.id.txtMen);
        smsManager = SmsManager.getDefault();

        checarPermisos();
        broadcastSms = new BroadCastLlamadas();

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("android.intent.action.PHONE_STATE");

        registerReceiver(broadcastSms, intentFilter);

        //Esto es igual para el localbroadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastSms,
                new IntentFilter("key"));
    }

    public boolean checarPermisos(){

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if( (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if( (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) ||
                (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) ){
            PermisosRecomendados();
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS}, 100);
        }
        return false;
    }

    private void PermisosRecomendados() {
        AlertDialog.Builder dialogo =  new AlertDialog.Builder(this);
        dialogo.setTitle("Pls no sea malo");
        dialogo.setMessage("Se pasa");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M) //esto se agrego porque marcaba error el requestPermissions
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS}, 100);
            }
        });
        dialogo.show();
    }

    public void enviarMensaje(View view) {
        Mensaje.numero=txtTel.getText().toString();
        Mensaje.mensaje=txtText.getText().toString();
        Toast.makeText(this,""+Mensaje.numero+ " "+ Mensaje.mensaje,Toast.LENGTH_LONG).show();
    }

}
