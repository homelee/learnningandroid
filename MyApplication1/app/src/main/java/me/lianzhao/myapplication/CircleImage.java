package me.lianzhao.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class CircleImage extends View {

    private Path _circlePath;
    private Paint _paint;

    public CircleImage(Context context) {
        super(context);
        initView();
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        _circlePath = new Path();
        _circlePath.addCircle(200,200,200, Path.Direction.CW);

        _paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _paint.setStyle(Paint.Style.STROKE);
        _paint.setColor(Color.RED);
        _paint.setStrokeMiter(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(_circlePath, _paint);
    }
}
