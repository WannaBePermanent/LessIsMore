package sechan.intern.lessismore.lim;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import sechan.intern.lessismore.R;
import sechan.intern.lessismore.lim.adapter.ItemTouchHelperCallback;
import sechan.intern.lessismore.lim.adapter.LimAdapter;
import sechan.intern.lessismore.lim.adapter.LoadAdapter;
import sechan.intern.lessismore.lim.components.enumcomp.EnumText;
import sechan.intern.lessismore.map.MapListActivity;
import sechan.intern.lessismore.model.LimRepo;

import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTBOLD;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTCOLOR;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTDECSIZE;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTINCSIZE;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTITALIC;
import static sechan.intern.lessismore.lim.components.enumcomp.EnumText.TEXTUNDERLINE;

public class LimActivity extends AppCompatActivity implements LimContract.View {
//public class LimActivity extends AppCompatActivity  {
    // 액티비티에서 직접 뷰 구현


    //private LimContract.Presenter mPresenter;
    private static final int cGreen = -16726212; // 네이버 녹색상
    private LimContract.Presenter mPresenter;

    RecyclerView rv;
    LimAdapter mAdapter;

    static final int REQ_CODE_SELECT_IMAGE = 100;
    static final int REQ_CODE_SELECT_TITLEIMAGE = 101;
    static final int REQ_CODE_SELECT_MAP = 102;

    LinearLayout llTextWidget, llColorPicker;
    ImageButton btnSave, btnLoad, btnAdd, btnRemoveComp;
    ImageButton btnText, btnImage, btnMap;
    ImageButton btnTitleImage;
    ImageButton btnInc, btnDec, btnBold, btnItalic, btnColor, btnUl;
    ImageButton btnColorOK;
    ImageButton btnImageLink, btnImageDivide;
    EditText editTitle;
    ImageView imageTitle;
    boolean isTitleImage = false;
    int mColor;
    int articlePosition = -1;

    ListView lvLoad;
    ImageButton btnLoadArticle, btnRemoveArticle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            showMessage("read/write storage permission authorized");
        }





        new LimPresenter(LimRepo.getInstance(), this);
        rv = (RecyclerView) findViewById(R.id.rv_contents);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.start();
        // 다이얼로그 뷰 세팅 시작
        final AlertDialog.Builder alertBuilderAdd = new AlertDialog.Builder(
                this);
        final LayoutInflater addInflater = this.getLayoutInflater();
        final View addDialogView = addInflater.inflate(R.layout.layout_add_dialog, null);
        alertBuilderAdd.setView(addDialogView);
        final AlertDialog addDialog = alertBuilderAdd.create();
        // 다이얼로그 뷰 세팅 끝

        // 저장 목록 뷰 시작
        final AlertDialog.Builder alertBuilderLoad = new AlertDialog.Builder(
                this);
        final LayoutInflater loadInflater = this.getLayoutInflater();
        final View loadDialogView = loadInflater.inflate(R.layout.layout_load_list, null);
        alertBuilderLoad.setView(loadDialogView);
        final AlertDialog loadDialog = alertBuilderLoad.create();
        // 저장 목록 뷰 끝


        final HSLColorPicker colorPicker = (HSLColorPicker) findViewById(R.id.colorPicker);
        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {

                mColor = color;
                btnColorOK.setColorFilter(color);
                btnColor.setColorFilter(color);
                //색상 체인지
            }
        });
        llTextWidget = (LinearLayout) findViewById(R.id.ll_textwidget);
        llColorPicker = (LinearLayout) findViewById(R.id.llColorPicker);

        // 컴포넌트들 추가 버튼
        btnMap = (ImageButton) addDialogView.findViewById(R.id.btn_map);
        btnImage = (ImageButton) addDialogView.findViewById(R.id.btn_image);
        btnText = (ImageButton) addDialogView.findViewById(R.id.btn_text);
        // 컴포넌트 추가버튼 끝

        // TEXT WIDGET 버튼 설정 시작
        btnColor = (ImageButton) findViewById(R.id.btn_color);
        btnBold = (ImageButton) findViewById(R.id.btn_bold);
        btnItalic = (ImageButton) findViewById(R.id.btn_italic);
        btnInc = (ImageButton) findViewById(R.id.btn_inc_size);
        btnDec = (ImageButton) findViewById(R.id.btn_dec_size);
        btnUl = (ImageButton) findViewById(R.id.btn_ul);
        btnColorOK = (ImageButton) findViewById(R.id.btnColorOK);
        // TEXT WIDGET 버튼 설정 끝

        // 저장, 불러오기 컴포넌트추가 버튼
        btnSave = (ImageButton) findViewById(R.id.btn_save);
        btnLoad = (ImageButton) findViewById(R.id.btn_load);
        btnAdd = (ImageButton) findViewById(R.id.btn_add);
        btnRemoveComp = (ImageButton) findViewById(R.id.btn_deletecomp);
        // 저장, 불러오기 컴포넌트추가 버튼 끝

        //불러오기 다이얼로그 버튼 가져오기
        lvLoad = loadDialogView.findViewById(R.id.lvLoad);
        btnLoadArticle = loadDialogView.findViewById(R.id.btnLoadArticle);
        btnRemoveArticle = loadDialogView.findViewById(R.id.btnDeleteArticle);


        //불러오기 다이얼로그 끝

        //타이틀 설정
        editTitle = (EditText) findViewById(R.id.edit_title);
        btnTitleImage = (ImageButton) findViewById(R.id.btn_titleimage);
        imageTitle = (ImageView) findViewById(R.id.imageTitle);
        //

        btnImageLink = (ImageButton) findViewById(R.id.btn_imagelink);
        btnImageDivide = (ImageButton) findViewById(R.id.btn_imagedivide);
        btnColorOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(TEXTCOLOR, mColor);
                showMessage("색상이 선택되었습니다");
                llColorPicker.setVisibility(View.GONE);

            }
        });
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llColorPicker.getVisibility() == View.GONE)
                    llColorPicker.setVisibility(View.VISIBLE);
                else llColorPicker.setVisibility(View.GONE);
            }
        });

        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(TEXTBOLD);

            }
        });
        btnItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(TEXTITALIC);
            }
        });
        btnUl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(TEXTUNDERLINE);
            }
        });


        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(TEXTINCSIZE);
            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(TEXTDECSIZE);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.saveArticle();
                mPresenter.saveArticle(editTitle.getText().toString());


            }
        });
        btnRemoveComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.saveArticle();
                mPresenter.removeComp();


            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articlePosition = -1;
                LoadAdapter loadAdapter = new LoadAdapter(mPresenter.loadArticleList());
                lvLoad.setAdapter(loadAdapter);
                lvLoad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        v.setSelected(true);
                        articlePosition = position;
                    }
                });


                loadDialog.show();

            }
        });
        btnLoadArticle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (articlePosition >=0) {
                    loadDialog.dismiss();
                    mPresenter.loadArticle(articlePosition);
                }
            }
        });
        btnRemoveArticle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (articlePosition >=0) {
                    loadDialog.dismiss();
                    mPresenter.removeArticle(articlePosition);

                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.saveText();
                addDialog.show();
            }
        });

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                // 텍스트 클릭


                mPresenter.addCompText();
                addDialog.dismiss();


            }
        });
        btnTitleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지 클릭
                if (!isTitleImage) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_TITLEIMAGE);
                } else {
                    mPresenter.setTitleImage(null);
                }
                addDialog.dismiss();

            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지 클릭
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                addDialog.dismiss();

            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          addDialog.dismiss();
                                          Intent intent = new Intent(LimActivity.this, MapListActivity.class);
                                          startActivityForResult(intent, REQ_CODE_SELECT_MAP);
                                          // MapActivity로 넘어가야함함

                                      }
                                  }


        );
        btnImageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.imageStrip();
            }
        });
        btnImageDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.imageDivide();
            }
        });


    }
    @Override
    public void setAdapter(LimAdapter adapter) {
        mAdapter = adapter;
        rv.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(rv);


    }


    @Override
    public void setPresenter(LimContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setBtn(EnumText style, boolean isSet) {


        switch (style) {
            case TEXTBOLD:
                if (isSet)
                    btnBold.setColorFilter(cGreen);
                else btnBold.clearColorFilter();
                break;
            case TEXTITALIC:
                if (isSet)
                    btnItalic.setColorFilter(cGreen);
                else
                    btnItalic.clearColorFilter();

                break;
            case TEXTUNDERLINE:
                if (isSet)
                    btnUl.setColorFilter(cGreen);
                else
                    btnUl.clearColorFilter();
                break;

        }

    }
    @Override
    public void showRemoveButton(boolean isVisible) {
        if (isVisible) btnRemoveComp.setVisibility(View.VISIBLE);
        else btnRemoveComp.setVisibility(View.INVISIBLE);
    }
    @Override
    public void setBtn(EnumText style, int color) {
        btnColor.setColorFilter(color);
    }
    @Override
    public void clearBtn() {
        btnBold.clearColorFilter();
        btnItalic.clearColorFilter();
        btnUl.clearColorFilter();
        btnColor.clearColorFilter();
    }
    @Override
    public void showTextWidget(boolean show) {
        if (show) llTextWidget.setVisibility(View.VISIBLE);
        else llTextWidget.setVisibility(View.GONE);


    }
    @Override
    public void showStripBtn(boolean show) {
        if (show) btnImageLink.setVisibility(View.VISIBLE);
        else btnImageLink.setVisibility(View.INVISIBLE);
    }
    @Override
    public void showDivideBtn(boolean show) {
        if (show) btnImageDivide.setVisibility(View.VISIBLE);
        else btnImageDivide.setVisibility(View.INVISIBLE);
    }
    @Override
    public void setTitleImage(String imagePath) {
        if (imagePath != null) {
            Glide.with(this).load(imagePath).into(imageTitle);
            editTitle.setTextColor(Color.WHITE);
            btnTitleImage.setColorFilter(Color.WHITE);
            imageTitle.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
            btnTitleImage.setImageResource(R.drawable.icon_delete);
            isTitleImage = true;
        } else {
            editTitle.setTextColor(Color.BLACK);
            btnTitleImage.setImageResource(R.drawable.icon_image);
            btnTitleImage.clearColorFilter();
            imageTitle.clearColorFilter();
            imageTitle.setImageResource(android.R.color.transparent);
            isTitleImage = false;
        }
    }
    @Override
    public void setTitleText(String title){
        editTitle.setText(title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_MAP) {
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String address = data.getStringExtra("address");
                int mapx = data.getIntExtra("mapx", 0);
                int mapy = data.getIntExtra("mapy", 0);
                mPresenter.addCompMap(mapx, mapy, title, address);
                //showMessage(title + "\n"+address);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        } else if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    mPresenter.addCompImage(data.getDataString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQ_CODE_SELECT_TITLEIMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    mPresenter.setTitleImage(data.getDataString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
