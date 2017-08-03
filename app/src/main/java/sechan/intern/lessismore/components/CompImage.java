package sechan.intern.lessismore.components;


import static sechan.intern.lessismore.components.EnumComp.COMP_IMAGE;

public class CompImage extends Comp {
    private String[] imagePath = new String[3];

    public CompImage(String imagePath) {
        super(COMP_IMAGE);
        this.imagePath[0] = imagePath;
    }

    public String[] ImagePath() {
        return imagePath;
    }

    public void ConcatImage(String imagePath2) {
        if (imagePath[1] == null) {
            imagePath[1] = imagePath2;
        } else {
            //3개넘으면 Error 처리

        }
    }


}