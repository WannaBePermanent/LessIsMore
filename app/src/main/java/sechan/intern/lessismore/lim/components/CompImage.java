package sechan.intern.lessismore.lim.components;


import java.util.ArrayList;

import static sechan.intern.lessismore.lim.components.enumcomp.EnumComp.COMP_IMAGE;

public class CompImage extends Comp {
    // 규칙 14 접근자 메서드 적용
    private ArrayList<String> imagePath = new ArrayList<>();
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

    public int getSize(){
        return imagePath.size();
    }


}