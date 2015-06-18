package ua.pp.mmr.mymedicalrecord;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Uliana on 16.06.2015.
 */
public class DayCircle extends View { // наследуем от View
    private float radius;
    public DayCircle(Context context) {
        super(context); // в конструкторе вызываем конструктор класса-родителя
        this.radius = context.getResources().getDimension(R.dimen.height_day_circle_layout)/2;
    }
    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);

        Paint paint = new Paint(); // создаем экземпляр класса Paint
        paint.setStyle(Paint.Style.FILL); // используем заливку во всех графических примитивах
        // закрашиваем всё белым цветом
        paint.setColor(Color.WHITE); // указываем, что используем белый цвет
        c.drawPaint(paint);
        // Рисуем зеленый круг
        paint.setAntiAlias(true); // задаем режим сглаживания
        paint.setColor(Color.BLACK); // выбираем зеленый цвет
        paint.setStyle(Paint.Style.STROKE);
        c.drawCircle(radius, getHeight() / 2, radius, paint); // рисуем круг
        paint.setStyle(Paint.Style.FILL);
        final RectF oval = new RectF(0, 0, radius*2, radius*2);
        paint.setColor(0xFF75AEFF); // выбираем зеленый цвет
        paint.setAlpha(70);
        c.drawArc(oval, 270, Utils.getInstance(null).getDayDegrees(), true, paint); // рисуем пакмана
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        c.drawText(Utils.getInstance(null).getPartOfDay(), getWidth()-radius, radius*2-20, paint);

        // восстанавливаем холст
        c.restore();
    }
}