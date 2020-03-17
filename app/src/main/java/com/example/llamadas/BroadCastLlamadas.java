package com.example.llamadas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class BroadCastLlamadas extends BroadcastReceiver {
    private TelephonyManager telephonyManager;
    public String entrante;
    Context context;
    //public Mensaje mensaje;
    String numero;
    @Override
    public void onReceive(Context context, Intent intent) {
        numero=Mensaje.numero;

        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener phoneStateListener =  new MyPhoneStateListener();
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);

        //Este metodo recibe el intent que manda el broadcast
        BroadcastReceiver broadcast = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,"Entrante: "+numero,Toast.LENGTH_SHORT).show();
                if(numero == Mensaje.numero){
                    SmsManager smsManager = null;
                        smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Mensaje.numero,null,
                            Mensaje.mensaje,null,null);
                }
                else{/*
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(txtTel.getText().toString(),null,
                        txtText.getText().toString(),null,null);*/
                }
            }
        };

    }

    //Esto si funciona solo cehar porque envia el intent vacio
    public class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);

                Log.d("MyPhoneStateListener",state+" Nop");


        }
    }
}
