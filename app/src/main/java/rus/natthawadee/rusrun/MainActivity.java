package rus.natthawadee.rusrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private EditText userEditText, passwoedEditText;
    private ImageView imageView;
    private static final String urllogo = "http://swiftcodingthai.com/rus/image/logo_rus.png";
    private String userString,passwordString;
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
        }//if


    }//clickSign



    public void clickSingUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }
} // Main Class คลาสหลัก
