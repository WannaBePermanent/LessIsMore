package sechan.intern.lessismore.Model.helpers;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sechan.intern.lessismore.lim.LimPresenter;

/**
 * Created by Sechan on 2017-08-04.
 */

public class ImageHelper {
    private ArrayList<ImageView> imageView;
    private ArrayList<String> imagePath;
    private LimPresenter mPresenter;
    private static ImageHelper instance = null;

    public static ImageHelper getInstance() {
        if (instance == null) {
            instance = new ImageHelper();
        }
        return instance;
    }

    public void setPresenter(LimPresenter pres) {
        mPresenter = pres;
    }

    public void setCompImage(ArrayList<ImageView> image, ArrayList<String> path) {
        imageView = image;
        imagePath = path;
    }

    public void setCompImage(ArrayList<ImageView> image, ArrayList<String> path, int position) {
        imageView = image;
        imagePath = path;
        setImage(position); // 첫번째 이미지 로드
    }

    public void setImage(int position) {
        Glide.with(((View) imageView.get(0).getParent()).getContext().getApplicationContext()).load(imagePath.get(position)).into(imageView.get(position));
        imageView.get(position).setVisibility(View.VISIBLE);
    }

    public void imageStrip(ArrayList<String> path) {
        if ((path.size() + imagePath.size()) <= 3) {
            imagePath.addAll(path);
            for (int i=0;i<imagePath.size();i++) {
                setImage(i); // 추가된 것만큼 그리면 리사이클일어나서 꼬임
                imageView.get(i).setVisibility(View.VISIBLE);
            }
            /*
            for (int i = (imagePath.size() - 1); i >= (imagePath.size() - path.size()); i--) {
                setImage(i); // 추가된 것만큼 그리면 된다
                imageView.get(i).setVisibility(View.VISIBLE);
            }*/
            mPresenter.removeComp(); // 합친후 기존것은 삭제
        }

    }

    public void imageDivide() {
        int lastIndex = imagePath.size() - 1;
        imageView.get(lastIndex).setVisibility(View.GONE);
        mPresenter.addCompImage(imagePath.get(lastIndex),mPresenter.getPosition()+1); //다음 순서에 넣어야함
        imagePath.remove(lastIndex);
        for (int i=0;i<imagePath.size();i++) setImage(i);

    }


}
