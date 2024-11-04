package com.android.luxevista;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class DualEventDecorator implements DayViewDecorator {
    private final int color1;
    private final int color2;
    private final HashSet<CalendarDay> dates;

    public DualEventDecorator(int color1, int color2, HashSet<CalendarDay> dates) {
        this.color1 = color1;
        this.color2 = color2;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DualDotSpan(5, color1, color2)); // Custom span with two dots
    }
}

class DualDotSpan extends DotSpan {
    private final int color1;
    private final int color2;

    public DualDotSpan(float radius, int color1, int color2) {
        super(radius, color1);
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lineNumber) {
        int centerX = (left + right) / 2;
        int dotRadius = 5;

        // Draw first dot
        paint.setColor(color1);
        canvas.drawCircle(centerX - dotRadius, bottom + dotRadius, dotRadius, paint);

        // Draw second dot
        paint.setColor(color2);
        canvas.drawCircle(centerX + dotRadius, bottom + dotRadius, dotRadius, paint);
    }
}

