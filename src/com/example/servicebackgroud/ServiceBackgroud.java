package com.example.servicebackgroud;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class ServiceBackgroud extends Service {
	private NotificationManager mNM;

	Bundle b;
	Intent notificationIntent;
	private final IBinder mBinder = new LocalBinder();
	private String newtext;

	public class LocalBinder extends Binder {
		ServiceBackgroud getService() {
			return ServiceBackgroud.this;
		}
	}

	@Override
	public void onCreate() {
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		newtext = "BackGroundApp Service Running";
		
		notificationIntent = new Intent(this, ServiceBackgroud.class);
	
		showNotification();		
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
	
		return START_STICKY;
	}
	public void onDestroy() {
		mNM.cancel(R.string.abc_action_bar_up_description);
		stopSelf();
	}
	private void showNotification() {
		CharSequence text = getText(R.string.abc_action_bar_home_description);
		
		Notification notification = new Notification(R.drawable.ic_launcher, text, System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, ServiceBackgroud.class), 0);
		notification.setLatestEventInfo(this, "BackgroundAppExample",newtext, contentIntent);
		notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;     
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		mNM.notify(R.string.abc_action_menu_overflow_description, notification);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}