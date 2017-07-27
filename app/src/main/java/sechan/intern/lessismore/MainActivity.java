package sechan.intern.lessismore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import java.util.ArrayList;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.helpers.Post;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements LimContract.View {
    // 액티비티에서 직접 뷰 구현

    private LimContract.Presenter mPresenter;
/*    private static final int COMP_TEXT = 1;
    private static final int COMP_IMAGE = 2;
    private static final int COMP_IMAGES = 3;
    private static final int COMP_MAP = 4;*/

    ArrayList<String> mDataset;
    RecyclerView rv;
    LimAdapter mAdapter;
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
                // Do whatever you want with the color
                showMessage(Integer.toString(color) + "선택되었습니다");
                //색상 체인지
            }
        });

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

    public void setAdapter(Post comps) {
        mAdapter = new LimAdapter(comps);
        rv.setAdapter(mAdapter);
        mAdapter.setTextListener(new LimAdapter.onTextListener() {
            //@Override
            public void onTextCallBack(int position, int start, int end) {
                Toast.makeText(getApplicationContext(), Integer.toString(position) + "번 텍스트 선택 " + Integer.toString(start) + " 부터 " + Integer.toString(end), Toast.LENGTH_SHORT).show();

            }
        });
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

    public void setPresenter(@NonNull LimContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
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

}
