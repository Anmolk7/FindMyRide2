package com.example.anmol.findmyride;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

/**
 * Created by Anmol on 1/23/2017.
 */
public class MessageReceiver  extends BroadcastReceiver {
    SharedPreferences sf;
    public static final String preference="pref";
    public static  String saveIt="savekey";
    float newbal, balance;

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
        Databasehelper helper= new Databasehelper(context);
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages;
        String str = "";
        String number = "";
        String no=helper.display().toString();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");//portable description object
            messages = new SmsMessage[pdus != null ? pdus.length : 0];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu(pdus != null ? (byte[]) pdus[i] : null);
                number += messages[i].getOriginatingAddress();
                str += messages[i].getMessageBody();
                str += "\n";
            }
            if (number.equals("+977"+no)||number.equals(no))
            {
                //helper.insertBalance(100);
                 balance=helper.getBalance();
                 newbal= (float) (balance - 1.2);
                 helper.updateBalance(newbal,balance);
                 Toast.makeText(context,String.valueOf(helper.getBalance()),Toast.LENGTH_SHORT).show();

               /* Toast.makeText(context, (int) balance,Toast.LENGTH_SHORT).show();
                //balance=balance-1.2;
                sf= context.getSharedPreferences(preference,context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sf.edit();
                editor.putFloat(saveIt,balance);
                editor.commit();
                float someFloat=sf.getFloat(saveIt,0.0f);
                someFloat--;
                if(someFloat<1)
                {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSound(soundUri).setColor(Color.rgb(40, 116, 166))
                                    .setSmallIcon(R.drawable.truckgps)
                                    .setContentTitle("FindMyRide")
                                    .setContentText("Please recharge your account");
                    mBuilder.setOngoing(true);
                    Intent resultIntent = new Intent(context, MainActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                    stackBuilder.addParentStack(MyAccount.class);

                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    mBuilder.setAutoCancel(true);
                    NotificationManager mNotificationManager =
                            (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(0,mBuilder.build());

                }*/



                helper.insertCordinates(str);
                helper.insertPCordinates(str);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSound(soundUri).setColor(Color.rgb(40, 116, 166))
                                .setSmallIcon(R.drawable.carr)
                                .setContentTitle("FindMyRide")
                                .setContentText(str);
                mBuilder.setOngoing(true);
                Intent resultIntent = new Intent(context, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                broadcastIntent.putExtra("message", str);
                context.sendBroadcast(broadcastIntent);

                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                mBuilder.setAutoCancel(true);
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0,mBuilder.build());

                //broadcastIntent = new Intent();
                broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                broadcastIntent.putExtra("message", str);
                context.sendBroadcast(broadcastIntent);
            }
        }
    }

}
