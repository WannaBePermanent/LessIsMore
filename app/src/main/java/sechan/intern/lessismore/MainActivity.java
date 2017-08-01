package sechan.intern.lessismore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.LimConstant;

//public class MainActivity extends AppCompatActivity implements LimContract.View {
public class MainActivity extends AppCompatActivity {
    // 액티비티에서 직접 뷰 구현

    //private LimContract.Presenter mPresenter;
    private static final int cGreen = -16726212; // 네이버 녹색상
    private LimPresenter mPresenter;

    RecyclerView rv;
    LimAdapter mAdapter;
    static final int REQ_CODE_SELECT_IMAGE = 100;
    //boolean currentBold = false, currentItalic = false, currentUnderline = false;
    LinearLayout llTextWidget, llColorPicker;
    ImageButton btnSave, btnLoad, btnAdd, btnDelComp;
    ImageButton btnText, btnImage, btnMap;
    ImageButton btnTitleImage;
    ImageButton btnInc, btnDec, btnBold, btnItalic, btnColor, btnUl;
    ImageButton btnColorOK;
    int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LimPresenter(LimRepo.getInstance(), this);
        rv = (RecyclerView) findViewById(R.id.rv_contents);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.start();


        // 다이얼로그 뷰 세팅 시작
        final AlertDialog.Builder alertBuilder_add = new AlertDialog.Builder(
                this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View addDialogView = inflater.inflate(R.layout.layout_add_dialog, null);
        alertBuilder_add.setView(addDialogView);
        final AlertDialog addDialog = alertBuilder_add.create();
        // 다이얼로그 뷰 세팅 끝

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
        btnDelComp = (ImageButton) findViewById(R.id.btn_deletecomp);
        // 저장, 불러오기 컴포넌트추가 버튼 끝

        btnTitleImage = (ImageButton) findViewById(R.id.btn_titleimage);
        btnColorOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTCOLOR, mColor);
                showMessage("색상이 선택되었습니다");

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
                mPresenter.setStyle(LimConstant.TEXTBOLD);

            }
        });
        btnItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTITALIC);
            }
        });
        btnUl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTUNDERLINE);
            }
        });


        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTINCSIZE);
            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTDECSIZE);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.save();
                mPresenter.save();



            }
        });
        btnDelComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.save();
                mPresenter.removeComp();



            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.loadList();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog.show();
            }
        });

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                // 텍스트 클릭


                mPresenter.addCompText();  //-> 모델에서 생성후 Presenter에서 displayCompText 등으로 뷰를 호출해야함
                addDialog.dismiss(); // 다이얼로그 제거도 Presenter가 해야 되는지 생각해볼 것


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
                // 맵 클릭

            }
        });


    }

    public void setAdapter(LimAdapter adapter) {
        mAdapter = adapter;
        rv.setAdapter(mAdapter);


    } //Post정보 - 어댑터 - RecyclerView 간 매핑

    public void displayComponent(int index) {
        //   mAdapter.notifyItemInserted(index);
        //mAdapter.notifyDataSetChanged();
    } //

    public void displayComponent() {
        //mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemInserted(0);
        // mAdapter.notifyItemRemoved(0);
        // 추후 성능을 위해 위 두개 함수로 대체할 것
    } //전체 한번에 표시

    public void displayComponent(Comp comp, int index) {


    } //한개만 삽입하거나 추가할때

    public void deleteComponent(int index) {

    } // 삭제는 인덱스만 필요함

    /*public void setPresenter(@NonNull LimContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }*/
    public void setPresenter(LimPresenter presenter) {
        mPresenter = presenter;
    }

    public void displayTitleBackground(Comp img) {

    }

    public void showSaved() {

    }

    public void showLoaded() {


    }

    public void showHelperLoaded(boolean loaded) {
        if (loaded) Toast.makeText(getApplicationContext(), "로딩 완료", Toast.LENGTH_SHORT).show();

    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }


    public void setBtn(int style, boolean b) {


        switch (style) {
            case LimConstant.TEXTBOLD:
                if (b)
                    btnBold.setColorFilter(cGreen);
                else btnBold.clearColorFilter();


                break;
            case LimConstant.TEXTITALIC:
                if (b)
                    btnItalic.setColorFilter(cGreen);
                else
                    btnItalic.clearColorFilter();

                break;
            case LimConstant.TEXTUNDERLINE:
                if (b)
                    btnUl.setColorFilter(cGreen);
                else
                    btnUl.clearColorFilter();
                break;

        }

    }
    public void showRemoveButton(boolean b){
        if (b) btnDelComp.setVisibility(View.VISIBLE);
        else btnDelComp.setVisibility(View.INVISIBLE);
    }
    public void setBtn(int style, int color) {
        btnColor.setColorFilter(color);
    }

    public void clearBtn() {
        btnBold.clearColorFilter();
        btnItalic.clearColorFilter();
        btnUl.clearColorFilter();
        btnColor.clearColorFilter();
    }

    public void showTextWidget(boolean show) {
        if (show) llTextWidget.setVisibility(View.VISIBLE);
        else llTextWidget.setVisibility(View.GONE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    mPresenter.addCompImage(data.getDataString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
