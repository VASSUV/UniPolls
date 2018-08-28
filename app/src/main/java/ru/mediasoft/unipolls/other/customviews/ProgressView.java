package ru.mediasoft.unipolls.other.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import ru.mediasoft.unipolls.R;

public class ProgressView extends View {
    private static final float MAX_PROGRESS_VALUE = 100f;
    private float DEF_PROGRESS_VALUE = 0f;
    private float progress;
    private final Paint progressPaint = new Paint();


    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0);
        progress = a.getFloat(R.styleable.ProgressView_progress, DEF_PROGRESS_VALUE);
        progressPaint.setColor(a.getColor(R.styleable.ProgressView_progressColor, getResources().getColor(R.color.white)));
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(0, 0, canvas.getWidth() * progress / MAX_PROGRESS_VALUE, canvas.getHeight(), progressPaint);
//        canvas.drawRoundRect(0,0, 10, 10, canvas.getWidth() * progress / MAX_PROGRESS_VALUE, canvas.getHeight(), progressPaint );
        canvas.drawRoundRect(0,0, canvas.getWidth() * progress / MAX_PROGRESS_VALUE, canvas.getHeight(), 100,100, progressPaint);
    }

    public void setProgressPaintColor(int progressPaintColor) {
        progressPaint.setColor(getResources().getColor(progressPaintColor));
        refreshDrawableState();
    }

    public void setProgressValue(float defProgressValue) {
        progress = defProgressValue;
    }
}