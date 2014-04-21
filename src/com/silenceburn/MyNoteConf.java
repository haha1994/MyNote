package com.silenceburn;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MyNoteConf extends Activity {

	int mAppWidgetId;
	TextView widgetText;
	Button sure;

	final String mPerfName = "com.silenceburn.MyNoteConf";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_note_conf);

		setResult(RESULT_CANCELED);
		// Find the widget id from the intent.
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		// If they gave us an intent without the widget id, just bail.
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
		
		sure = (Button)findViewById(R.id.sure);
		sure.setOnClickListener(mBtnClick);

	}

	OnClickListener mBtnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			

			TextView mTextView = (TextView) MyNoteConf.this
					.findViewById(R.id.EditText02);
			String note = mTextView.getText().toString();
			SharedPreferences.Editor prefs = MyNoteConf.this
					.getSharedPreferences(mPerfName, 0).edit();
			prefs.putString("DAT" + mAppWidgetId, note);
			prefs.commit();

			
			

			RemoteViews views = new RemoteViews(MyNoteConf.this
					.getPackageName(), R.layout.my_note_widget);
			views.setTextViewText(R.id.text, note);
			
			Intent intent = new Intent(MyNoteConf.this, MyNoteEdit.class);
			intent.setAction(mPerfName + mAppWidgetId);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
			PendingIntent pendingIntent = PendingIntent.getActivity(MyNoteConf.this, 0,
					intent, 0);
			views.setOnClickPendingIntent(R.id.text, pendingIntent);

			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(MyNoteConf.this);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);

			// return OK
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					mAppWidgetId);

			setResult(RESULT_OK, resultValue);
			finish();
		}
	};
}