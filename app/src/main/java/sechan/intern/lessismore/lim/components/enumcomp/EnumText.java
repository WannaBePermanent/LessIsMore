package sechan.intern.lessismore.lim.components.enumcomp;

/**
 * Created by Sechan on 2017-08-02.
 */

public enum EnumText {
    //규칙 30, 31 static int 대신 enum, 객체 필드 사용
    TEXTBOLD(1), TEXTITALIC(2), TEXTUNDERLINE(3),TEXTCOLOR(4),TEXTINCSIZE(5),TEXTDECSIZE(6),TEXTSIZE(7);
    private final int value;
    EnumText(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }

}