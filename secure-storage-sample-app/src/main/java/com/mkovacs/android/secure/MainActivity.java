package com.mkovacs.android.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mkovacs.android.secure.service.settings.UserDataServce;

public class MainActivity extends Activity {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MainActivity.class);
	private Button loginButton;
	private UserDataServce dataServce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			dataServce = new UserDataServce(getApplicationContext());
		} catch (Exception e) {
			finish();
			LOGGER.error("Can not initialize secured shared prefs", e);
			return;
		}
		setContentView(R.layout.activity_main);
		loginButton = (Button) findViewById(R.id.login_button);
		loginButton.setOnClickListener(clickListener);
	}

	private void openLoginWindow() {
		String username = dataServce.getUsername();
		Intent intent = new Intent(getApplicationContext(), SecureLogin.class);
		intent.putExtra(SecureLogin.EXTRA_EMAIL, username);
		startActivity(intent);
	}

	private OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			if (loginButton.equals(v)) {
				openLoginWindow();
			}
		}
	};

}
