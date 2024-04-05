package com.example.mysampletexttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

private EditText editText;
private TextToSpeech textToSpeech;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);

editText = findViewById(R.id.editText);
textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
@Override
public void onInit(int i) {
textToSpeech.setLanguage(Locale.US);
textToSpeech.setSpeechRate((float) 2.5);

}
});
}

public void TextToSpeechButton(View view){
textToSpeech.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
}
}

<?xml version=”1.0″ encoding=”utf-8″?>
<manifest xmlns:android=”http://schemas.android.com/apk/res/android&#8221;
package=”com.example.mysampletexttospeech”>

<application
android:allowBackup=”true”
android:icon=”@mipmap/ic_launcher”
android:label=”@string/app_name”
android:roundIcon=”@mipmap/ic_launcher_round”
android:supportsRtl=”true”
android:theme=”@style/AppTheme”>
<activity android:name=”.MainActivity”>
<intent-filter>
<action android:name=”android.intent.action.MAIN” />

<category android:name=”android.intent.category.LAUNCHER” />
</intent-filter>
</activity>
</application>

</manifest>

<?xml version=”1.0″ encoding=”utf-8″?>
<RelativeLayout xmlns:android=”http://schemas.android.com/apk/res/android&#8221;
xmlns:app=”http://schemas.android.com/apk/res-auto&#8221;
xmlns:tools=”http://schemas.android.com/tools&#8221;
android:layout_width=”match_parent”
android:layout_height=”match_parent”
tools:context=”.MainActivity”>

<EditText
android:id=”@+id/editText”
android:layout_width=”360dp”
android:layout_height=”wrap_content”
android:layout_marginStart=”20dp”
android:layout_marginTop=”150dp”
android:ems=”10″
android:gravity=”start|top”
android:hint=”@string/your_text_here”
android:inputType=”textMultiLine”
android:autofillHints=”” />

<Button
android:id=”@+id/button”
android:layout_width=”wrap_content”
android:layout_height=”wrap_content”
android:layout_marginStart=”120dp”
android:layout_marginTop=”60dp”
android:onClick=”TextToSpeechButton”
android:text=”@string/text_to_speech_convert” />
</RelativeLayout>

package com.example.mypersonalvoiceassitance;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        textView = findViewById(R.id.textView);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }
            @Override
            public void onBeginningOfSpeech() {
            }
            @Override
            public void onRmsChanged(float rmsdB) {
            }
            @Override
            public void onBufferReceived(byte[] buffer) {
            }
            @Override
            public void onEndOfSpeech() {
            }
            @Override
            public void onError(int error) {
            }
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                String string = "";
                textView.setText("");
                if (matches != null){
                    string = matches.get(0);
                    textView.setText(string);

                    if (string.equals("create")){
                        createMethod();
                    }
                }
            }
            @Override
            public void onPartialResults(Bundle partialResults) {
            }
            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });
    }

    public void startButton(View view){
        textToSpeech.speak("Please tell me, how can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        speechRecognizer.startListening(intent);
    }

    private void createMethod(){
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "PersonalAssistant.txt");
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append("My 1st Personal voice assistance App development");
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        textToSpeech.speak("The text file has been created. Thank you for using my service.", TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.mypersonalvoiceassitance">

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="153dp"
        android:layout_marginTop="98dp"
        android:onClick="startButton"
        android:text="@string/start"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>

