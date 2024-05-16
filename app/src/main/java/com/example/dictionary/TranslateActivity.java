package com.example.dictionary;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.Translation;

import java.util.ArrayList;
import java.util.Locale;

public class TranslateActivity extends AppCompatActivity {

    private TextInputEditText sourceText;
    private MaterialButton EngToVietBtn, VietToEngBtn;
    private TextView translateTV;

    private ImageView micTV;

    ImageButton goBackButton;

    private final ActivityResultLauncher<Intent> speechResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            ArrayList<String> speechResult = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            sourceText.setText(speechResult.get(0));
                            identifyLanguageAndTranslate(speechResult.get(0));
                        }
                    }
            );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        micTV = findViewById(R.id.idIVMic);
        sourceText = findViewById(R.id.idEditSource);
        EngToVietBtn = findViewById(R.id.EngToVietBtn);
        VietToEngBtn = findViewById(R.id.VietToEngBtn);
        translateTV = findViewById(R.id.idTranslatedTV);
        goBackButton = findViewById(R.id.btn_goBack);

        EngToVietBtn.setOnClickListener(v -> translateText(TranslateLanguage.ENGLISH, TranslateLanguage.VIETNAMESE));
        VietToEngBtn.setOnClickListener(v -> translateText(TranslateLanguage.VIETNAMESE, TranslateLanguage.ENGLISH));
        micTV.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
            try {
                speechResultLauncher.launch(intent);
            }catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(TranslateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        goBackButton.setOnClickListener(v -> {NavigationHelper.navigateToMainActivity(TranslateActivity.this);});
    }

    private void translateText(String sourceLanguage, String targetLanguage) {
        String tobeTranslatedText = sourceText.getText().toString();

        if(TextUtils.isEmpty(tobeTranslatedText)){
            Toast.makeText(TranslateActivity.this, getString(R.string.enter_text), Toast.LENGTH_SHORT).show();
            return;
        }

        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguage)
                .setTargetLanguage(targetLanguage)
                .build();

        Translator translator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(v -> {
                    Task<String> result = translator.translate(tobeTranslatedText);
                    result.addOnSuccessListener(s -> {
                        translateTV.setText(s);
                        translator.close();
                    }).addOnFailureListener(e -> Toast.makeText(TranslateActivity.this, getString(R.string.translation_error), Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(TranslateActivity.this, getString(R.string.model_download_error), Toast.LENGTH_SHORT).show());
    }

    private void identifyLanguageAndTranslate(String text) {
        LanguageIdentifier languageIdentifier = LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(text)
                .addOnSuccessListener(languageCode -> {
                    if (!languageCode.equals("und")) {
                        String targetLanguage = languageCode.equals("en") ? TranslateLanguage.VIETNAMESE : TranslateLanguage.ENGLISH;
                        translateText(languageCode, targetLanguage);
                    } else {
                        Toast.makeText(TranslateActivity.this, getString(R.string.language_not_identified), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(TranslateActivity.this, getString(R.string.language_identification_error), Toast.LENGTH_SHORT).show());
    }
}