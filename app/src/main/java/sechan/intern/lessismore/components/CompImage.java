package sechan.intern.lessismore.components;


import static sechan.intern.lessismore.components.EnumComp.COMP_IMAGE;

public class CompImage extends Comp {
    private String imagePath;
    public CompImage(String imagePath){
        super(COMP_IMAGE);
        this.imagePath=imagePath;
    }
    public String ImagePath(){
        return imagePath;
    }




}