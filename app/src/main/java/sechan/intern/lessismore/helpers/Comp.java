package sechan.intern.lessismore.helpers;

import java.util.List;

/**
 * Created by NAVER on 2017-07-21.
 */

public class Comp {

    public class TextComp extends Comp {

        List<Style> style;

        void setStyle(int start, int end,  int attributes,int mode) {// mode 1 = Color, 2 = Size, 3 = Bold, 4 = Italic, 5 = Underline
            // mode 3~5는 attributes가 필요없음

        }
            // 추후 Builder Pattern 적용가능한지 생각
            class Style {
                int start;
                int end;
                int attributes;
                int mode;
            }


        }

        public class MapComp extends Comp {


        }

        public class ImageComp extends Comp {
            // Glide glide;
            void setImage(String imagePath) {

            }


        }

        public class ImagesComp extends Comp {
            List<ImageComp> imageComp;

            ImagesComp concatImages(List<ImageComp> imgcomps) {

                return null;
            }


        }
    }

