package me.lianzhao.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class CircleImage extends View {

    private Paint circleImagePaint;
    private Paint borderPaint;

    public CircleImage(Context context) {
        super(context);
        initView();
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImage, 0, 0);
        initBorder(typedArray);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImage, defStyleAttr, 0);
        initBorder(typedArray);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImage, defStyleAttr, defStyleRes);
        initBorder(typedArray);
    }

    private void initBorder(TypedArray typedArray) {
        try {
            int borderWidth = typedArray.getInt(R.styleable.CircleImage_borderWidth, 0);
            int borderColor = typedArray.getColor(R.styleable.CircleImage_borderColor, Color.TRANSPARENT);
            borderPaint = new Paint();
            borderPaint.setAntiAlias(false);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(borderWidth);
            borderPaint.setColor(borderColor);
        } finally {
            typedArray.recycle();
        }
    }

    private void initView(){
        circleImagePaint = new Paint();
        circleImagePaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.sample);
        Bitmap bitmap = drawable.getBitmap();
        int canvasSize = Math.min(canvas.getHeight(), canvas.getWidth());
        int center = canvasSize/2;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, canvasSize, canvasSize, false);
        BitmapShader bitmapShader = new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        circleImagePaint.setShader(bitmapShader);

        canvas.drawCircle(center, center, center, circleImagePaint);
        canvas.drawCircle(center, center, center, borderPaint);
    }
}
