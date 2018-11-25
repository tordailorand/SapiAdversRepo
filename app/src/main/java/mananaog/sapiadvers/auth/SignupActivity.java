package mananaog.sapiadvers.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mananaog.sapiadvers.R;

public class SignupActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();

    private EditText mFistName;
    private EditText mLastName;
    private EditText mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFistName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mPhone = findViewById(R.id.phone);

        Button mSignupButton = findViewById(R.id.signupButton);
        mSignupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });
    }

    private void attemptSignup() {
        mLastName.setError(null);
        mFistName.setError(null);
        mPhone.setError(null);

        String firstName = mFistName.getText().toString();
        String lastName = mLastName.getText().toString();
        String phone = mPhone.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(firstName)) {
            mFistName.setError(getString(R.string.error_field_required));
            focusView = mFistName;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError(getString(R.string.error_field_required));
            focusView = mPhone;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            User user = new User(firstName, lastName, phone);
            DatabaseReference usersRef = dbRef.child("users").child(phone);
            usersRef.setValue(user);

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void loginClickHandle(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}

