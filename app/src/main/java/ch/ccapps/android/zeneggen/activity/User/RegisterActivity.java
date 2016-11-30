package ch.ccapps.android.zeneggen.activity.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.model.AppUser;


public class RegisterActivity extends ActionBarActivity {

    private EditText emailET;
    private EditText profileNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_register);
        getSupportActionBar().setTitle(R.string.title_register);

        emailET = (EditText)findViewById(R.id.profile_name);
        profileNameET = (EditText)findViewById(R.id.profile_name);
    }

    public void onRegister(View v){
        String email = emailET.getText().toString();
        String profileName = profileNameET.getText().toString();

        if (email != null && !email.isEmpty() && profileName != null && profileName.isEmpty()){

        }

    }


}
