package com.businessdecision.kayoo.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.businessdecision.kayoo.R;

/**
 * Created by Manuel Gonzalez Villegas on 27/1/16.
 */
public class ContinueReadingTextView extends TextView {

    /**
     * Default max length
     */
    private final int MAX_LENGTH = 100;

    /**
     * Default continue reading
     */
    private final String DEFAULT_CONTINUE_READING_MESSAGE = "Continue reading";

    /**
     * The maxLength, it can be setted from xml(using 'app:maxLength') or from code.
     */
    private int maxLegth;

    /**
     * The continue reading message. It can be setted from xml(using 'app:continueReading') or from code.
     */
    private String continueReadingMessage;

    /**
     * Store the full text whithout crop
     */
    private SpannableString fullText;

    private ContinueReadingListener continueReadingListener;

    public interface ContinueReadingListener {
        void onClick(View textView);
    }

    public ContinueReadingTextView(Context context) {
        super(context);
        init(null);
    }

    public ContinueReadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ContinueReadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ContinueReadingTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        continueReadingMessage = DEFAULT_CONTINUE_READING_MESSAGE;
        maxLegth = MAX_LENGTH;
        if (attrs != null) {
            TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.ContinueReadingTextView);
            continueReadingMessage = styled.getString(R.styleable.ContinueReadingTextView_continueReading);
            maxLegth = styled.getInt(R.styleable.ContinueReadingTextView_textCroppedLength, 0);

        }
    }

    public int getMaxLegth() {
        if (maxLegth <= 0) {
            setMaxLegth(MAX_LENGTH);
        }

        return maxLegth;
    }

    public void setMaxLegth(int maxLegth) {
        this.maxLegth = maxLegth;
    }

    public String getResContinueReading() {
        if (continueReadingMessage == null || "".equals(continueReadingMessage)) {
            continueReadingMessage = DEFAULT_CONTINUE_READING_MESSAGE;
        }
        return continueReadingMessage;
    }

    public void setContinueReadingMessage(int resContinueReadingMessage) {
        if (resContinueReadingMessage > 0) {
            setContinueReadingMessage(getContext().getString(resContinueReadingMessage));
        }
    }

    public void setContinueReadingMessage(String continueReadingMessage) {
        this.continueReadingMessage = continueReadingMessage;
    }

    /**
     * If the text length is bigger than MAX_LENGTH, it's cropped
     *
     * @param text
     * @param type
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        fullText = new SpannableString(text);
        if (text.length() > getMaxLegth()) {
            String continueReading = getResContinueReading();
            int croppedLength = getMaxLegth() - 4 - continueReading.length();
            String partialText = text.subSequence(0, croppedLength) + "... " + continueReading;
            SpannableString ss = new SpannableString(partialText);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    if (continueReadingListener != null) {
                        continueReadingListener.onClick(textView);
                    }
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };
            int start = partialText.indexOf(continueReading, croppedLength);
            int end = partialText.length();
            ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            super.setText(ss, type);
            setMovementMethod(LinkMovementMethod.getInstance());
            setHighlightColor(Color.TRANSPARENT);
        } else {
            super.setText(text, type);
        }

    }

    public void setContinueReadingListener(ContinueReadingListener continueReadingListener) {
        this.continueReadingListener = continueReadingListener;
    }
}
