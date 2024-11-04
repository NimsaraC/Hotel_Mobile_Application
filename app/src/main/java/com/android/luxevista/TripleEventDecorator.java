package com.android.luxevista;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class TripleEventDecorator implements DayViewDecorator {
    private final int color1;
    private final int color2;
    private final int color3;
    private final HashSet<CalendarDay> dates;

    public TripleEventDecorator(int color1, int color2, int color3, HashSet<CalendarDay> dates) {
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new TripleDotSpan(5, color1, color2, color3)); // Custom span with three dots
    }
}

class TripleDotSpan extends DotSpan {
    private final int color1;
    private final int color2;
    private final int color3;

    public TripleDotSpan(float radius, int color1, int color2, int color3) {
        super(radius, color1);
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lineNumber) {
        int centerX = (left + right) / 2;
        int dotRadius = 5;

        paint.setColor(color1);
        canvas.drawCircle(centerX - dotRadius, bottom + dotRadius, dotRadius, paint);

        paint.setColor(color2);
        canvas.drawCircle(centerX, bottom + dotRadius, dotRadius, paint);

        paint.setColor(color3);
        canvas.drawCircle(centerX + dotRadius, bottom + dotRadius, dotRadius, paint);
    }
}
