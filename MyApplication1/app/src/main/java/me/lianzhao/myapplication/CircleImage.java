package me.lianzhao.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class CircleImage extends View {

    private Paint _circleImagePaint;
    private Paint _circleBorderPaint;

    private Bitmap _circleBitmap;

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
        _circleImagePaint = new Paint();
        _circleImagePaint.setAntiAlias(false);
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
        _circleImagePaint.setShader(bitmapShader);

        canvas.drawCircle(center, center, center, _circleImagePaint);

    }
}
