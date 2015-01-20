package course.examples.helloandroidwithlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText uname = (EditText) findViewById(R.id.enter_username);
        final EditText passwd = (EditText) findViewById(R.id.enter_password);

        //Set listener for keyboard actions
        passwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tryLogin(uname, passwd);
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void tryLogin(EditText uname, EditText passwd) {
        if (checkPassword(uname.getText(), passwd.getText())) {

            // Create an explicit Intent for starting the HelloAndroid Activity
            Intent helloAndroidIntent = new Intent(this,
                    HelloAndroidWithImageViewActivity.class);

            // Use the Intent to start the HelloAndroid Activity
            startActivity(helloAndroidIntent);

        } else {
            uname.setText("");
            passwd.setText("");
        }
    }

    private boolean checkPassword(Editable uname, Editable passwd) {
        // Just pretending to extract text and check password
        return true;
    }
}
