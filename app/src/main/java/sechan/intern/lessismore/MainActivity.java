package sechan.intern.lessismore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import java.util.ArrayList;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.components.LimConstant;

//public class MainActivity extends AppCompatActivity implements LimContract.View {
    public class MainActivity extends AppCompatActivity {
    // 액티비티에서 직접 뷰 구현

    //private LimContract.Presenter mPresenter;
    private LimPresenter mPresenter;
    ArrayList<String> mDataset;
    RecyclerView rv;
    LimAdapter mAdapter;
    LimEditText edit_focus;
    Spannable span;
    int text_start;
    int text_end;
    LinearLayout ll_textWidget;
    ImageButton btn_save, btn_load, btn_add;
    ImageButton btn_text, btn_image, btn_map;
    ImageButton btn_titleimage;
    ImageButton btn_inc, btn_dec, btn_bold, btn_italic, btn_color, btn_ul;


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
                //showMessage(Integer.toString(color) + "선택되었습니다");
                mPresenter.setStyle(LimConstant.TEXTCOLOR,color);
                //색상 체인지
            }
        });
        ll_textWidget = (LinearLayout)findViewById(R.id.ll_textwidget);
        ll_textWidget.setVisibility(View.GONE);

        // 컴포넌트들 추가 버튼
        btn_map = (ImageButton) addDialogView.findViewById(R.id.btn_map);
        btn_image = (ImageButton) addDialogView.findViewById(R.id.btn_image);
        btn_text = (ImageButton) addDialogView.findViewById(R.id.btn_text);
        // 컴포넌트 추가버튼 끝

        // TEXT WIDGET 버튼 설정 시작
        btn_color = (ImageButton) findViewById(R.id.btn_color);
        btn_bold = (ImageButton) findViewById(R.id.btn_bold);
        btn_italic = (ImageButton) findViewById(R.id.btn_italic);
        btn_inc = (ImageButton) findViewById(R.id.btn_inc_size);
        btn_dec = (ImageButton) findViewById(R.id.btn_dec_size);
        btn_ul = (ImageButton) findViewById(R.id.btn_ul);
        // TEXT WIDGET 버튼 설정 끝

        // 저장, 불러오기 컴포넌트추가 버튼
        btn_save = (ImageButton) findViewById(R.id.btn_save);
        btn_load = (ImageButton) findViewById(R.id.btn_load);
        btn_add = (ImageButton) findViewById(R.id.btn_add);
        // 저장, 불러오기 컴포넌트추가 버튼 끝

        btn_titleimage = (ImageButton) findViewById(R.id.btn_titleimage);

        btn_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colorPicker.getVisibility() == View.GONE)
                    colorPicker.setVisibility(View.VISIBLE);
                else colorPicker.setVisibility(View.GONE);
            }
        });

        btn_bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mPresenter.setStyle(LimConstant.TEXTBOLD);

            }
        });
        btn_italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTITALIC);
            }
        });
        btn_ul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTUNDERLINE);
            }
        });


        btn_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTINCSIZE);
            }
        });
        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setStyle(LimConstant.TEXTDECSIZE);
            }
        });




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.save();
            }
        });
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPresenter.loadList();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog.show();
            }
        });

        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 텍스트 클릭


                mPresenter.addCompText();  //-> 모델에서 생성후 Presenter에서 displayCompText 등으로 뷰를 호출해야함
                addDialog.dismiss(); // 다이얼로그 제거도 Presenter가 해야 되는지 생각해볼 것


            }
        });
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지 클릭
                mPresenter.addCompImage("Hello");
                addDialog.dismiss();

            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 맵 클릭

            }
        });


    }

    public void setAdapter(LimAdapter adapter) {
        mAdapter = adapter;
        rv.setAdapter(mAdapter);


/*        mAdapter.setTextListener(new LimAdapter.onTextListener() {
            //@Override
            public void onTextCallBack(LimEditText lte, int position, int start, int end) {
                edit_focus = lte;
                span = lte.getText();
                text_start = start;
                text_end = end;
                Toast.makeText(getApplicationContext(), Integer.toString(position) + "번 텍스트 선택 " + Integer.toString(start) + " 부터 " + Integer.toString(end), Toast.LENGTH_SHORT).show();

            }
        });*/

    } //Post정보 - 어댑터 - RecyclerView 간 매핑

    public void displayComponent(int index) {
        mAdapter.notifyItemInserted(index);
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
        if (loaded) Toast.makeText(getApplicationContext(), "로딩완료", Toast.LENGTH_SHORT).show();

    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    private void setStyle() {
        if (!edit_focus.hasSelection()) {
            edit_focus.getText().insert(edit_focus.getSelectionStart(), " ");
            edit_focus.setSelection(edit_focus.getSelectionStart() - 1, edit_focus.getSelectionStart());
        }


    }

    public void setFocused(int pos, int category2, int start, int end) {


    }
    public void setBtn(int style) {


        switch (style) {
            case LimConstant.TEXTBOLD:
                btn_bold.setColorFilter(Color.GREEN);
                break;
            case LimConstant.TEXTITALIC:
                btn_italic.setColorFilter(Color.GREEN);
                break;
            case LimConstant.TEXTUNDERLINE:
                btn_ul.setColorFilter(Color.GREEN);
                break;

        }

    }
    public void setBtn(int style, int color) {
        btn_color.setColorFilter(color);

    }
    public void clearBtn(){
        btn_bold.clearColorFilter();
        btn_italic.clearColorFilter();
        btn_ul.clearColorFilter();
        btn_color.clearColorFilter();
    }
    public void showTextWidget(boolean show){
        if (show) ll_textWidget.setVisibility(View.VISIBLE);
        else ll_textWidget.setVisibility(View.GONE);


    }

}
