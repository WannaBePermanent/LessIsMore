package sechan.intern.lessismore;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

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
    private LimEditText currentEdit;
    private int textSize = -1;
    private int start;
    private int end;
    private Spannable span;

    // Adapter - Presenter 형으로 바꿈

    private Post mPost;

    public void setPresenter(LimPresenter p) {
        mPresenter = p;
    }

    public void setContext(Context c) {
        mContext = c;
    }

    public LimAdapter(Post post) {
        mPost = post; // getItemCount 베이스 구현자체에서 수량 가져와서 돌림
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
                lt = holder.editComp;
                textSize = (int) lt.getTextSize();
                lt.setText(Integer.toString(position));
                lt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            start = lt.getSelectionStart();
                            end = lt.getSelectionEnd();
                            currentEdit = lt;
                            span = lt.getText();
                            getStyle();
                            mPresenter.textFocus(true);
                        } else mPresenter.textFocus(false);

                    }
                });
                lt.addOnSelectionChangedListener(new LimEditText.onSelectionChangedListener(
                ) {
                    @Override
                    public void onSelectionChanged(int selStart, int selEnd) {
                        start = selStart;
                        end = selEnd;
                        currentEdit = lt;
                        if (span == null) span = currentEdit.getText();
                        if (!doSpace) getStyle(); //셀렉션 한칸 넣는 작업 때는 검사 안함

                    }


                });


                break;
            case LimConstant.COMP_IMAGE:
                ll_view.addView(holder.ivComp);
                Glide.with(mContext).load(((CompImage) mPost.get(position)).ImagePath()).into((ImageView) holder.ivComp);
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

    public void setBold() {

        if (spanB == null) {
            preStyle();
            spanB = new LimStyleSpan(Typeface.BOLD);
            span.setSpan(spanB,
                    start, end,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mPresenter.isBold(true);
        } else {
            span.removeSpan(spanB);
            int end2 = spanB.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            //span.setSpan(spanB,spanB.start,spanB.end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            if (spanB.start <= start && start <= spanB.end) {
                span.setSpan(new LimStyleSpan(Typeface.BOLD), spanB.start, start, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanB.start <= end && end <= spanB.end) {
                span.setSpan(new LimStyleSpan(Typeface.BOLD), end, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            } //쪼개서 스펜넣기
            mPresenter.isBold(false);
            spanB = null;
        }


    }


    public void setItalic() {
        if (spanI == null) {
            spanI = new LimStyleSpan(Typeface.ITALIC);
            preStyle();
            span.setSpan(spanI,
                    start, end,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mPresenter.isItalic(true);
        } else {
            span.removeSpan(spanI);
            int end2 = spanI.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanI.start <= start && start <= spanI.end) {
                span.setSpan(new LimStyleSpan(Typeface.ITALIC), spanI.start, start, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanI.start <= end && end <= spanI.end) {
                span.setSpan(new LimStyleSpan(Typeface.ITALIC), end, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            mPresenter.isItalic(false);
            spanI = null;
        }
    }

    public void setUnderline() {
        if (spanU == null) {
            preStyle();
            spanU = new LimUnderlineSpan();
            span.setSpan(spanU,
                    start, end,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mPresenter.isUnderLine(true);
        } else {
            span.removeSpan(spanU);
            int end2 = spanU.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanU.start <= start && start <= spanU.end) {
                span.setSpan(new LimUnderlineSpan(), spanU.start, start, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanU.start <= end && end <= spanU.end) {
                span.setSpan(new LimUnderlineSpan(), end, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            mPresenter.isItalic(false);
            spanU = null;
        }
    }

    public void setColor(int color) {
        if (spanC == null) {
            preStyle();
            spanC = new LimForegroundColorSpan(color);
            span.setSpan(spanC,
                    start, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        } else {
            span.removeSpan(spanC);
            int end2 = spanC.end;
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanC.start <= start && start <= spanC.end) {
                span.setSpan(new LimForegroundColorSpan(spanC.getForegroundColor()), spanC.start, start, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanC.start <= end && end <= spanC.end) {
                span.setSpan(new LimForegroundColorSpan(spanC.getForegroundColor()), end, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            span.setSpan(new LimForegroundColorSpan(color),
                    start, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        mPresenter.isColor(color);
    }

    public void setTextSize(boolean inc) {
        if (spanS == null) {
            preStyle();
            if (inc) spanS = new LimAbsoluteSizeSpan(textSize + 5);
            else spanS = new LimAbsoluteSizeSpan(textSize - 5);
            span.setSpan(spanS,
                    start, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        } else {
            span.removeSpan(spanS);
            int end2 = spanS.end;
            int textSize2 = spanS.getSize();
            if (preStyle()) end2++; //공백을 만들어주면 기존 텍스트가 중간 기준으로 한칸 밀리므로 바꿔줘야한다
            if (spanS.start <= start && start <= spanS.end) {
                span.setSpan(new LimAbsoluteSizeSpan(spanS.getSize()), spanS.start, start, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (spanS.start <= end && end <= spanS.end) {
                span.setSpan(new LimAbsoluteSizeSpan(spanS.getSize()), end, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (inc) spanS = new LimAbsoluteSizeSpan(textSize2 + 5);
            else spanS = new LimAbsoluteSizeSpan(textSize2 - 5);
            span.setSpan(spanS,
                    start, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        }

    }


    private boolean preStyle() { // 공백 생성후 선택

        if (!currentEdit.hasSelection()) {
            doSpace = true;
            currentEdit.getText().insert(currentEdit.getSelectionStart(), " ");
            currentEdit.setSelection(currentEdit.getSelectionStart() - 1, currentEdit.getSelectionStart());
            doSpace = false;
            return true;
        }
        return false;
    }

    private void getStyle() {
        LimStyleSpan ss[] = span.getSpans(start, end, LimStyleSpan.class);
        LimUnderlineSpan us[] = span.getSpans(start, end, LimUnderlineSpan.class);
        LimForegroundColorSpan fs[] = span.getSpans(start, end, LimForegroundColorSpan.class);
        LimAbsoluteSizeSpan as[] = span.getSpans(start, end, LimAbsoluteSizeSpan.class);
        mPresenter.clearStyleCheck();
        spanB = null;
        spanI = null;
        spanU = null;
        spanC = null;
        for (LimStyleSpan s : ss) {
            if (span.getSpanStart(s) == end) continue; // 선택 앞쪽에서는 스타일 적용이 일어나면 안된다
            if (s.getStyle() == Typeface.BOLD) {
                spanB = s;
                spanB.start = span.getSpanStart(s);
                spanB.end = span.getSpanEnd(s);
                mPresenter.isBold(true);
            }
            if (s.getStyle() == Typeface.ITALIC) {
                spanI = s;
                spanI.start = span.getSpanStart(s);
                spanI.end = span.getSpanEnd(s);
                mPresenter.isItalic(true);

            }
        }
        for (LimUnderlineSpan u : us) {
            if (span.getSpanStart(u) == end) continue;
            spanU = u;
            spanU.start = span.getSpanStart(u);
            spanU.end = span.getSpanEnd(u);
            mPresenter.isUnderLine(true);

        }
        for (LimForegroundColorSpan f : fs) {
            if (span.getSpanStart(f) == end) continue;
            spanC = f;
            spanC.start = span.getSpanStart(f);
            spanC.end = span.getSpanEnd(f);
            mPresenter.isColor(f.getForegroundColor());
        }
        for (LimAbsoluteSizeSpan a : as) {
            if (span.getSpanStart(a) == end) continue;
            spanS = a;
            spanS.start = span.getSpanStart(a);
            spanS.end = span.getSpanEnd(a);


        }
    }


}