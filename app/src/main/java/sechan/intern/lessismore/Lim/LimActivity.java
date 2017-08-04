package sechan.intern.lessismore.Lim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import sechan.intern.lessismore.Lim.Adapater.ItemTouchHelperCallback;
import sechan.intern.lessismore.Lim.Adapater.LimAdapter;
import sechan.intern.lessismore.Model.LimRepo;
import sechan.intern.lessismore.R;
import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.Enum.EnumText;

import static sechan.intern.lessismore.components.Enum.EnumText.TEXTBOLD;
import static sechan.intern.lessismore.components.Enum.EnumText.TEXTCOLOR;
import static sechan.intern.lessismore.components.Enum.EnumText.TEXTDECSIZE;
import static sechan.intern.lessismore.components.Enum.EnumText.TEXTINCSIZE;
import static sechan.intern.lessismore.components.Enum.EnumText.TEXTITALIC;
import static sechan.intern.lessismore.components.Enum.EnumText.TEXTUNDERLINE;

//public class LimActivity extends AppCompatActivity implements LimContract.View {
public class LimActivity extends AppCompatActivity {
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
    ImageButton btnImageLink, btnImageDivide;
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

    public void setAdapter(LimAdapter adapter) {
        mAdapter = adapter;
        rv.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(rv);


    }
    public RecyclerView getRecyclerView(){
        return rv;

    }


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
    public void showRemoveButton(boolean isVisible){
        if (isVisible) btnDelComp.setVisibility(View.VISIBLE);
        else btnDelComp.setVisibility(View.INVISIBLE);
    }
    public void setBtn(EnumText style, int color) {
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
    public void showStripBtn(boolean show) {
        if (show) btnImageLink.setVisibility(View.VISIBLE);
        else btnImageLink.setVisibility(View.INVISIBLE);
    }
    public void showDivideBtn(boolean show) {
        if (show) btnImageDivide.setVisibility(View.VISIBLE);
        else btnImageDivide.setVisibility(View.INVISIBLE);
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
    public RecyclerView.ViewHolder getHolder(int position){
        return rv.findViewHolderForAdapterPosition(position);
    }

}
