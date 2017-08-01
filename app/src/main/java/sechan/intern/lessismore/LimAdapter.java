package sechan.intern.lessismore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sechan.intern.lessismore.components.CompImage;
import sechan.intern.lessismore.components.LimConstant;
import sechan.intern.lessismore.components.TextStyle.LimAbsoluteSizeSpan;
import sechan.intern.lessismore.components.TextStyle.LimForegroundColorSpan;
import sechan.intern.lessismore.components.TextStyle.LimStyleSpan;
import sechan.intern.lessismore.components.TextStyle.LimUnderlineSpan;
import sechan.intern.lessismore.helpers.Post;

public class LimAdapter extends RecyclerView.Adapter<LimAdapter.ViewHolder> {
    private boolean doSpace = false;
    private LimStyleSpan spanB = null;
    private LimStyleSpan spanI = null;
    private LimUnderlineSpan spanU = null;
    private LimForegroundColorSpan spanC = null;
    private LimAbsoluteSizeSpan spanS = null;
    private Context mContext = null;
    private LimPresenter mPresenter = null;
    private LimEditText mEdit;
    private int mTextSize = -1;
    private int mStart;
    private static final GradientDrawable mGrad = new GradientDrawable();
    private int mEnd;
    private int mPosition = -1;
    private Spannable mSpan;
    private ArrayList<View> mList = new ArrayList<>();
    // Adapter - Presenter 형으로 바꿈

    private Post mPost;

    public void setPresenter(LimPresenter p) {
        mPresenter = p;
    }

    public void setContext(Context c) {
        mContext = c;
    }

    public LimAdapter(Post post) {
        mPost = post;
        mGrad.setCornerRadius(10);
        mGrad.setStroke(4, Color.parseColor("#00C73C"));
        mGrad.setShape(GradientDrawable.RECTANGLE);


        // getItemCount 베이스 구현자체에서 수량 가져와서 돌림
    }

    @Override
    public LimAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comp, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //업데이트 될 때마다
//          holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        final LimEditText lt;
        final LinearLayout ll_view = (LinearLayout) holder.mLLView;
        ll_view.removeAllViews();
        // 다른 종류들을 삭제하고 해당하는 컴포넌트만 남겨놓는다.
        switch (mPost.get(position).category) {
            case LimConstant.COMP_TEXT:
                ll_view.addView(holder.editComp);
                mList.add(position, holder.editComp);
                lt = holder.editComp;
                mTextSize = (int) lt.getTextSize();
                lt.setText(Integer.toString(position));
                //setOnClick(holder.editComp, position);
                lt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            mStart = lt.getSelectionStart();
                            mEnd = lt.getSelectionEnd();
                            mEdit = lt;
                            mSpan = lt.getText();
                            getStyle();
                            mPresenter.textFocus(true);
                            mPosition = position;
                            hasFocused(position);
                        } else mPresenter.textFocus(false);

                    }
                });
                lt.addOnSelectionChangedListener(new LimEditText.onSelectionChangedListener(
                ) {
                    @Override
                    public void onSelectionChanged(int selStart, int selEnd) {
                        mStart = selStart;
                        mEnd = selEnd;
                        mEdit = lt;
                        if (mSpan == null) mSpan = mEdit.getText();
                        if (!doSpace) getStyle(); //셀렉션 한칸 넣는 작업 때는 검사 안함

                    }


                });


                break;
            case LimConstant.COMP_IMAGE:
                ll_view.addView(holder.ivComp);
                mList.add(position, holder.ivComp);
                Glide.with(mContext).load(((CompImage) mPost.get(position)).ImagePath()).into((ImageView) holder.ivComp);
                setOnClick(holder.ivComp, position);
                break;
            case LimConstant.COMP_MAP:
                break;

        }


    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LimEditText editComp;
        public View mLLView;
        public View ivComp;

        public ViewHolder(View v) {
            super(v);
            this.mLLView = v;
            this.ivComp = v.findViewById(R.id.iv_comp);
            this.editComp = v.findViewById(R.id.edit_comp);

        }
    }


    public void setOnClick(final View v, final int position) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition = position;
                hasFocused(position);
                mPresenter.showMessage(Integer.toString(mPosition));
            }
        });

    }

    public void saveText() {
        for (int viewIndex = 0; viewIndex < mList.size(); viewIndex++) {
            if (mList.get(viewIndex).toString().equals("LimEditText")) {
                LimEditText tempEdit = (LimEditText) mList.get(viewIndex);
                Spannable tempSpan = tempEdit.getText();
                mPresenter.saveText(viewIndex, tempEdit.getText().toString());
                LimStyleSpan ss[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimStyleSpan.class);
                LimUnderlineSpan us[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimUnderlineSpan.class);
                LimForegroundColorSpan fs[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimForegroundColorSpan.class);
                LimAbsoluteSizeSpan as[] = tempSpan.getSpans(0, tempSpan.length() - 1, LimAbsoluteSizeSpan.class);
                for (LimStyleSpan s : ss) {
                    s.start = tempSpan.getSpanStart(s);
                    s.end = tempSpan.getSpanEnd(s);
                    mPresenter.saveTextStyle(viewIndex, s.getStyle(), s.start, s.end);

                }
                for (LimUnderlineSpan u : us) {
                    u.start = tempSpan.getSpanStart(u);
                    u.end = tempSpan.getSpanEnd(u);
                    mPresenter.saveTextStyle(viewIndex, LimConstant.TEXTUNDERLINE, u.start, u.end);
                }
                for (LimForegroundColorSpan f : fs) {
                    f.start = tempSpan.getSpanStart(f);
                    f.end = tempSpan.getSpanEnd(f);
                    mPresenter.saveTextStyle(viewIndex, LimConstant.TEXTCOLOR, f.start, f.end, f.getForegroundColor());
                }
                for (LimAbsoluteSizeSpan a : as) {
                    a.start = tempSpan.getSpanStart(a);
                    a.end = tempSpan.getSpanEnd(a);
                    mPresenter.saveTextStyle(viewIndex, LimConstant.TEXTSIZE, a.start, a.end, a.getSize());
                }

            }

        }
    }

    public void deleteComp() {
        mList.remove(mPosition);
        notifyItemRemoved(mPosition);
        mPosition = -1;


    }

    public int getPosition() {
        return mPosition;
    }

    private void hasFocused(int position) {
        mPresenter.setFocused(position);
        for (int viewIndex = 0; viewIndex < mList.size(); viewIndex++) {
            View v = mList.get(viewIndex);
            if (position == viewIndex) v.setBackground(mGrad);
            else {
                v.setBackground(null);
                v.clearFocus();
            }

        }
    }




    // =====이하로 텍스트 스타일 지정, 가져오기

    public void setBold() {

        if (spanB == null) {
            preStyle();
            spanB = new LimStyleSpan(Typeface.BOLD);
            mSpan.setSpan(spanB,
                    mStart, mEnd,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mPresenter.isBold(true);
        } else {
            mSpan.removeSpan(spanB);
            int end2 = spanB.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            //mSpan.setSpan(spanB,spanB.mStart,spanB.mEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            if (spanB.start <= mStart && mStart <= spanB.end) {
                mSpan.setSpan(new LimStyleSpan(Typeface.BOLD), spanB.start, mStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanB.start <= mEnd && mEnd <= spanB.end) {
                mSpan.setSpan(new LimStyleSpan(Typeface.BOLD), mEnd, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            } //쪼개서 스펜넣기
            mPresenter.isBold(false);
            spanB = null;
        }


    }


    public void setItalic() {
        if (spanI == null) {
            spanI = new LimStyleSpan(Typeface.ITALIC);
            preStyle();
            mSpan.setSpan(spanI,
                    mStart, mEnd,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mPresenter.isItalic(true);
        } else {
            mSpan.removeSpan(spanI);
            int end2 = spanI.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanI.start <= mStart && mStart <= spanI.end) {
                mSpan.setSpan(new LimStyleSpan(Typeface.ITALIC), spanI.start, mStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanI.start <= mEnd && mEnd <= spanI.end) {
                mSpan.setSpan(new LimStyleSpan(Typeface.ITALIC), mEnd, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            mPresenter.isItalic(false);
            spanI = null;
        }
    }

    public void setUnderline() {
        if (spanU == null) {
            preStyle();
            spanU = new LimUnderlineSpan();
            mSpan.setSpan(spanU,
                    mStart, mEnd,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mPresenter.isUnderLine(true);
        } else {
            mSpan.removeSpan(spanU);
            int end2 = spanU.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanU.start <= mStart && mStart <= spanU.end) {
                mSpan.setSpan(new LimUnderlineSpan(), spanU.start, mStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanU.start <= mEnd && mEnd <= spanU.end) {
                mSpan.setSpan(new LimUnderlineSpan(), mEnd, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            mPresenter.isUnderLine(false);
            spanU = null;
        }
    }

    public void setColor(int color) {
        if (spanC == null) {
            preStyle();
            spanC = new LimForegroundColorSpan(color);
            mSpan.setSpan(spanC,
                    mStart, mEnd,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        } else {
            mSpan.removeSpan(spanC);
            int end2 = spanC.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanC.start <= mStart && mStart <= spanC.end) {
                mSpan.setSpan(new LimForegroundColorSpan(spanC.getForegroundColor()), spanC.start, mStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanC.start <= mEnd && mEnd <= spanC.end) {
                mSpan.setSpan(new LimForegroundColorSpan(spanC.getForegroundColor()), mEnd, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            mSpan.setSpan(new LimForegroundColorSpan(color),
                    mStart, mEnd,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        mPresenter.isColor(color);
    }

    public void setTextSize(boolean inc) {
        if (spanS == null) {
            preStyle();
            if (inc) spanS = new LimAbsoluteSizeSpan(mTextSize + 5);
            else spanS = new LimAbsoluteSizeSpan(mTextSize - 5);
            mSpan.setSpan(spanS,
                    mStart, mEnd,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        } else {
            mSpan.removeSpan(spanS);
            int end2 = spanS.end;
            int textSize2 = spanS.getSize();
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanS.start <= mStart && mStart <= spanS.end) {
                mSpan.setSpan(new LimAbsoluteSizeSpan(spanS.getSize()), spanS.start, mStart, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanS.start <= mEnd && mEnd <= spanS.end) {
                mSpan.setSpan(new LimAbsoluteSizeSpan(spanS.getSize()), mEnd, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (inc) spanS = new LimAbsoluteSizeSpan(textSize2 + 5);
            else spanS = new LimAbsoluteSizeSpan(textSize2 - 5);
            mSpan.setSpan(spanS,
                    mStart, mEnd,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        }

    }


    private boolean preStyle() { // 공백 생성후 선택

        if (!mEdit.hasSelection()) {
            doSpace = true;
            mEdit.getText().insert(mEdit.getSelectionStart(), " ");
            mEdit.setSelection(mEdit.getSelectionStart() - 1, mEdit.getSelectionStart());
            doSpace = false;
            return true;
        }
        return false;
    }

    private void getStyle() {
        LimStyleSpan ss[] = mSpan.getSpans(mStart, mEnd, LimStyleSpan.class);
        LimUnderlineSpan us[] = mSpan.getSpans(mStart, mEnd, LimUnderlineSpan.class);
        LimForegroundColorSpan fs[] = mSpan.getSpans(mStart, mEnd, LimForegroundColorSpan.class);
        LimAbsoluteSizeSpan as[] = mSpan.getSpans(mStart, mEnd, LimAbsoluteSizeSpan.class);
        mPresenter.clearStyleCheck();
        spanB = null;
        spanI = null;
        spanU = null;
        spanC = null;
        for (LimStyleSpan s : ss) {
            if (mSpan.getSpanStart(s) == mEnd) continue; // 선택 앞쪽에서는 스타일 적용이 일어나면 안된다
            if (s.getStyle() == Typeface.BOLD) {
                spanB = s;
                spanB.start = mSpan.getSpanStart(s);
                spanB.end = mSpan.getSpanEnd(s);
                mPresenter.isBold(true);
            }
            if (s.getStyle() == Typeface.ITALIC) {
                spanI = s;
                spanI.start = mSpan.getSpanStart(s);
                spanI.end = mSpan.getSpanEnd(s);
                mPresenter.isItalic(true);

            }
        }
        for (LimUnderlineSpan u : us) {
            if (mSpan.getSpanStart(u) == mEnd) continue;
            spanU = u;
            spanU.start = mSpan.getSpanStart(u);
            spanU.end = mSpan.getSpanEnd(u);
            mPresenter.isUnderLine(true);

        }
        for (LimForegroundColorSpan f : fs) {
            if (mSpan.getSpanStart(f) == mEnd) continue;
            spanC = f;
            spanC.start = mSpan.getSpanStart(f);
            spanC.end = mSpan.getSpanEnd(f);
            mPresenter.isColor(f.getForegroundColor());
        }
        for (LimAbsoluteSizeSpan a : as) {
            if (mSpan.getSpanStart(a) == mEnd) continue;
            spanS = a;
            spanS.start = mSpan.getSpanStart(a);
            spanS.end = mSpan.getSpanEnd(a);


        }
    }


}