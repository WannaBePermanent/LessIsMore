package sechan.intern.lessismore.components;


import android.widget.ImageView;

import java.util.ArrayList;

import static sechan.intern.lessismore.components.Enum.EnumComp.COMP_IMAGE;

public class CompImage extends Comp {
    private ArrayList<String> imagePath = new ArrayList<>();
    private ArrayList<ImageView> imageView;

    public CompImage(String imagePath2) {
        super(COMP_IMAGE);
        imagePath.add(imagePath2);
    }

    public ArrayList<String> getImagePath() {
        return imagePath;
    }
    public String getImagePath(int position){
        return imagePath.get(position);
    }

    public void ConcatImage(String imagePath2) {
        imagePath.add(imagePath2);
    }

    public void setImageView(ArrayList<ImageView> view){
        imageView=view;

    }
    public ArrayList<ImageView> getImageView(){
        return imageView;

    }
    public ImageView getImageView(int position){
        return imageView.get(position);

    }
    public int getSize(){
        return imagePath.size();
    }


}