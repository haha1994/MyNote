package com.silenceburn;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MyNoteEdit extends Activity {
	int mAppWidgetId;
	TextView mTextView , widgetText;
	Button sure;

	final String mPerfName = "com.silenceburn.MyNoteConf";
	SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_note_conf);
		
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  //titlebar为自己标题栏的布局

		Intent t = getIntent();
		mAppWidgetId = t.getExtras().getInt(
				AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

		mPref = getSharedPreferences(mPerfName, 0);
		String noteContent = mPref.getString("DAT" + mAppWidgetId, "");

		mTextView = (TextView) findViewById(R.id.EditText02);
		mTextView.setText(noteContent);
		
		sure = (Button)findViewById(R.id.sure);
		sure.setOnClickListener(mBtnClick);

	}

	OnClickListener mBtnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			SharedPreferences.Editor prefsEdit = mPref.edit();
			String note = mTextView.getText().toString();
			prefsEdit.putString("DAT" + mAppWidgetId, note);
			prefsEdit.commit();

			

			RemoteViews views = new RemoteViews(MyNoteEdit.this
					.getPackageName(), R.layout.my_note_widget);
			views.setTextViewText(R.id.text, note);

			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(MyNoteEdit.this);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);

			MyNoteEdit.this.finish();
		}
	};
}
