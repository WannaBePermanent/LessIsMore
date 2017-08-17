package sechan.intern.lessismore.lim.components.enumcomp;

/**
 * Created by Sechan on 2017-08-02.
 */

public enum EnumComp {
    COMP_TEXT(1), COMP_IMAGE(2), COMP_MAP(3);
    private final int value;
    EnumComp (int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }
}
