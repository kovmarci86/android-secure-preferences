package com.github.kovmarci86.android.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kovmarci86.android.secure.service.settings.UserDataServce;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class SecureLogin extends Activity {
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int LOGIN_TASK_TIME = 2000;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureLogin.class);

    /**
     * Login result types.
     * @author NoTiCe
     *
     */
    public enum LoginResult {
        /**
         * Successful.
         */
        SUCCES(1),
        /**
         * Failed.
         */
        FAILED(2),
        /**
         * Just registered.
         */
        REGISTRED(3),
        /**
         * Error with login.
         */
        ERROR(4);

        private int value;

        /**
         * Initializes with the enum value.
         * @param value The value of the enum.
         */
        private LoginResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // Values for email and password at the time of the login attempt.
    private String mEmail;
    private String mPassword;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    // private View mLoginFormView;
    // private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    // Added code.
    private UserDataServce dataServce;

    private Toast toast;

    // Added code end.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Added code.
        try {
            dataServce = new UserDataServce(getApplicationContext());
        } catch (Exception e) {
            finish();
            LOGGER.error("Can not initialize secured shared prefs", e);
            return;
        }
        // Added code end.

        setContentView(R.layout.activity_secure_login);

        // Set up the login form.
        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
        mEmailView = (EditText) findViewById(R.id.email);
        mEmailView.setText(mEmail);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // mLoginFormView = findViewById(R.id.login_form);
        // mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < MIN_PASSWORD_LENGTH) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!mEmail.contains("@")) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
        }
    }

    private void showProgress(boolean b) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), "Progress", Toast.LENGTH_SHORT);
        }
        if (b) {
            toast.show();
        } else {
            toast.cancel();
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, LoginResult> {

        @Override
        protected LoginResult doInBackground(Void... params) {
            // simulating an attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(LOGIN_TASK_TIME);
            } catch (InterruptedException e) {
                return LoginResult.ERROR;
            }

            String username = dataServce.getUsername();
            String password = dataServce.getPassword();

            LoginResult result;
            if (username != null && password != null) {
                if (username.equals(mEmail) && password.equals(mPassword)) {
                    result = LoginResult.SUCCES;
                } else {
                    result = LoginResult.FAILED;
                }
            } else {
                dataServce.setUsername(mEmail);
                dataServce.setPassword(mPassword);
                result = LoginResult.REGISTRED;
            }

            return result;
        }

        @Override
        protected void onPostExecute(final LoginResult success) {
            mAuthTask = null;
            showProgress(false);

            if (LoginResult.SUCCES.equals(success)) {
                finish();
            } else if (LoginResult.FAILED.equals(success)) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            } else if (LoginResult.REGISTRED.equals(success)) {
                Toast.makeText(getApplicationContext(), "Registred", Toast.LENGTH_SHORT).show();
                finish();
            } else if (LoginResult.ERROR.equals(success)) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
