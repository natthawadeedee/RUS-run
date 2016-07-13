package rus.natthawadee.rusrun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText,userEditText,passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton avataoRadi0Button,avata1RadioButton,avata2RadioButton,
            avata3RadioButton,avata4RadioButton;
    private String nameString,userString,passwordString,avtaString;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        radioGroup = (RadioGroup) findViewById(R.id.radAvata);
        avataoRadi0Button = (RadioButton) findViewById(R.id.radioButton);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton5);

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.radioButton:
                        avtaString = "0";
                        break;
                    case R.id.radioButton2:
                        avtaString = "2";
                        break;
                    case  R.id.radioButton5:
                        avtaString = "5";
                        break;
                    case  R.id.radioButton3:
                        avtaString = "3";
                        break;
                    case  R.id.radioButton4:
                        avtaString = "4";
                        break;



                }   //switchc

            }
        });



    }//Main Method

    public void  clickSignUpSign(View view){

        // Get Value from Edit Text
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (nameString.equals("") || userString.equals("")|| passwordString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกทุกช่อง คะ");


        } else if (checkChoose()) {

        } else {
            // Un Check
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"ยังไม่เลือก Avata","กรุณาเลือก Avata ด้วยคะ");


        }




    } //clickSignUp

    private boolean checkChoose() {

        boolean status = true;

        status = avataoRadi0Button.isChecked() ||
                avata1RadioButton.isChecked() ||
                avata2RadioButton.isChecked() ||
                avata3RadioButton.isChecked() ||
                avata4RadioButton.isChecked();

        return status;
    }

} //Main Class
