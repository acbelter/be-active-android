package com.beactive.schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.FrameLayout;

public class LeisureView extends FrameLayout {
    private Paint mLinePaint;

    public LeisureView(Context context) {
        super(context);
        setWillNotDraw(false);
        mLinePaint = new Paint();
        mLinePaint.setColor(context.getResources().getColor(android.R.color.darker_gray));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ScheduleConstants.COLOR_LEISURE);
        for (int i = ScheduleConstants.MIN_HEIGHT; i < getHeight(); i+= ScheduleConstants.MIN_HEIGHT) {
            canvas.drawLine(0, i, getWidth(), i, mLinePaint);
        }
    }
}
