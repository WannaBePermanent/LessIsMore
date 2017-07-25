package sechan.intern.lessismore.components;


import static sechan.intern.lessismore.LimContract.COMP_IMAGE;

public class CompImage extends Comp {
    String imagePath;
    public CompImage(String imagePath){
        Category=COMP_IMAGE;
        this.imagePath=imagePath;
    }




}