package fslogger.lizsoft.lv.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fslogger.lizsoft.lv.fslogger.FSLogger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize FSLogger
        FSLogger.init("MyAwesomeAPP");
        FSLogger.addCode(1);
        FSLogger.enableLoggingWithBackTrace();

        FSLogger.w(1, "test log");
    }
}
