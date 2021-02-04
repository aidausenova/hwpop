package io.geektech.myapplicationrecyclerview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ApplicationActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText etName;
    private EditText etNumber;
    private Button btnSave;
    private static final int REQUEST_CODE = 3;
    public static final String KEY = "key";
    private Uri imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        init();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            MainModel mainModel = (MainModel) intent.getSerializableExtra(MainActivity.KEY);
            if (mainModel.getName()!=null&&mainModel.getNumber()!=null){
                etName.setText(mainModel.getName());
                etNumber.setText(mainModel.getNumber());
            }
            if (mainModel.getImageVIew()!=null){
                imageData=Uri.parse(mainModel.getImageVIew());
                Glide.with(this).load(imageData).apply(RequestOptions.circleCropTransform()).into(imageView);
            }
        }
    }

    private void init() {
        imageView = findViewById(R.id.imageView);
        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        btnSave = findViewById(R.id.btnSave);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String phone = etNumber.getText().toString();

                MainModel model = new MainModel();
                model.setName(name);
                model.setNumber(phone);
                if (imageData != null) {
                    String image = imageData.toString();
                    model.setImageVIew(image);
                }
                Intent intent = new Intent();
                intent.putExtra(KEY, model);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageData = data.getData();

            Glide.with(this)
                    .load(imageData)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);

        }
    }
}