package sechan.intern.lessismore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import sechan.intern.lessismore.helpers.Comp;
import sechan.intern.lessismore.helpers.Post;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements LimContract.View {
    private LimContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LimPresenter(LimRepo.getInstance(),this);
    }

    public void displayComponent(Post comps) {

    } //전체 한번에 표시

    public void displayComponent(Comp comp, int index){


    } //한개만 삽입하거나 추가할때

    public void deleteComponent(int index){

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

}
