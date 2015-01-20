package course.examples.helloworldwithmenus;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HelloAndroidWithMenuActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Long press on screen invokes Context Menu
        registerForContextMenu(findViewById(R.id.container));

    }

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    // Process clicks on Options Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                showToastAtCenter(getString(R.string.youve_been_helped_string));
                return true;
            case R.id.more_help:
                showToastAtCenter(getString(R.string.youve_been_helped_more_string));
                return true;
            case R.id.even_more_help:
                return true;
            case R.id.give_up:
                showToastAtCenter(getString(R.string.all_out));
                return true;
            default:
                return false;
        }
    }


    private void showToastAtCenter(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    // Create Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // Process clicks on Context Menu Items
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help_guide:
                showToastAtCenter(getString(R.string.context_menu_shown_string));
                return true;
            default:
                return false;
        }
    }
}