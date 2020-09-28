package com.bhavinpracticalcavista.ui.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bhavinpracticalcavista.R;
import com.bhavinpracticalcavista.local.entity.ImageEntity;
import com.bhavinpracticalcavista.repository.model.SearchResult;
import com.bhavinpracticalcavista.ui.base.BaseActivity;
import com.bhavinpracticalcavista.utils.SnackBarHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.Observable;
import java.util.Observer;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private SearchResult.Image image;
    private ImageView imageview;
    private EditText edtComment;
    private Button btnSave;
    private DetailViewModel viewModel;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("image", image);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        setListeners();
        if (savedInstanceState == null) {
            image = getIntent().getParcelableExtra("image");
        } else {
            image = savedInstanceState.getParcelable("image");
        }
        if (image.getId() != null)
            initializeToolbar(image.getId());
        else
            initializeToolbar("title");
        setData();
        observeData();
    }

    private void setListeners() {
        btnSave.setOnClickListener(this);
    }

    private void observeData() {
        if (image.getId() != null) {
            viewModel.getAddedComment(image.getId()).observe(this, new androidx.lifecycle.Observer<ImageEntity>() {
                @Override
                public void onChanged(ImageEntity imageEntity) {
                    if (imageEntity != null)
                        edtComment.setText(imageEntity.getComment());
                }
            });
        }
    }

    private void initViews() {
        imageview = findViewById(R.id.imageview);
        edtComment = findViewById(R.id.edt_comment);
        btnSave = findViewById(R.id.btn_save);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);
    }


    private void setData() {
        Glide.with(this)
                .load(image.getLink())
                .placeholder(R.drawable.gallery_place)
                .error(R.drawable.gallery_place)
                .into(imageview);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            if (!TextUtils.isEmpty(edtComment.getText().toString()) && image.getId() != null) {
                ImageEntity imageEntity = new ImageEntity(image.getId(), edtComment.getText().toString());
                viewModel.addComment(imageEntity);
                SnackBarHelper.INSTANCE.showSuccess(
                        findViewById(android.R.id.content),
                        "Comment added to database"
                );
            } else {
                SnackBarHelper.INSTANCE.showWarning(
                        findViewById(android.R.id.content),
                        "Please add comment"
                );
            }
        }
    }
}