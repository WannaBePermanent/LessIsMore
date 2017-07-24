package sechan.intern.lessismore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import sechan.intern.lessismore.components.Comp;
import sechan.intern.lessismore.helpers.Post;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements LimContract.View {
    // 액티비티에서 직접 뷰 구현

    private LimContract.Presenter mPresenter;
/*    private static final int COMP_TEXT = 1;
    private static final int COMP_IMAGE = 2;
    private static final int COMP_MAP = 3;*/

    Button btn_save, btn_load, btn_add;
    Button btn_text, btn_image, btn_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LimPresenter(LimRepo.getInstance(), this);
        mPresenter.start();

        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv_contents);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //RV-LIST 맵핑 구현해야함 (25일)

        // 다이얼로그 뷰 세팅 시작
        final AlertDialog.Builder alertBuilder_add = new AlertDialog.Builder(
                this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View addDialogView = inflater.inflate(R.layout.layout_add_dialog, null);
        alertBuilder_add.setView(addDialogView);
        final AlertDialog addDialog = alertBuilder_add.create();

        // 다이얼로그 뷰 세팅 끝

        btn_map = (Button) addDialogView.findViewById(R.id.btn_map);
        btn_image = (Button) addDialogView.findViewById(R.id.btn_image);
        btn_text = (Button) addDialogView.findViewById(R.id.btn_text);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_load = (Button) findViewById(R.id.btn_load);
        btn_add = (Button) findViewById(R.id.btn_add);
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
                mPresenter.addCompText(); //-> 모델에서 생성후 Presenter에서 displayCompText 등으로 뷰를 호출해야함

            }
        });
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지 클릭

                showMessage("이미지 클릭");

            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // 맵 클릭

            }
        });


    }

    public void displayComponent(Post comps) {

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
