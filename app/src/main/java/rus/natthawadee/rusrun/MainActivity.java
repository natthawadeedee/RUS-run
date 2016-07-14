package rus.natthawadee.rusrun;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private EditText userEditText, passwoedEditText;
    private ImageView imageView;
    private static final String urllogo = "http://swiftcodingthai.com/rus/image/logo_rus.png";
    private String userString,passwordString;
    private static final String urlJson = "http://swiftcodingthai.com/rus/get_user_Natthawadee.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Bion Widget
        userEditText = (EditText) findViewById(R.id.editText4);
        passwoedEditText = (EditText) findViewById(R.id.editText5);
        imageView = (ImageView) findViewById(R.id.imageView6);

                // Load image from Server
        Picasso.with(this).load(urllogo).into(imageView);

    }// Main Method

    // Create Inner Class
    private class SynUser extends AsyncTask<Void, Void, String> {

        //Explicit
        private String myJSONString,myUserString, myPasswordString;
        private Context context;

        public SynUser(String myJSONString,
                       String myUserString,
                       String myPasswordString,
                       Context context) {
            this.myJSONString = myJSONString;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();

            } catch (Exception e) {
                Log.d("RusV1", "e doIn ==>" + e.toString());
                return null;
            }

            return null;
        }// doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }//onPist

    }// SynUser Class



    public void clickSingIn(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwoedEditText.getText().toString().trim();

        // check space
        if (userString.equals("") || passwordString.equals("")) {
            // Have space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"Have Space",
                    "Please Fill All Every Blank");
        }else {
            //No Space
            SynUser synUser = new SynUser(urlJson, userString, passwordString, this);
            synUser.execute();
        }//if


    }//clickSign



    public void clickSingUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }
} // Main Class คลาสหลัก
