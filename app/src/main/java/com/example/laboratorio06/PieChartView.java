package com.example.laboratorio06;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieChartView extends View {
    private int SELECTION_COUNT = 8; // Total secciones diagrama
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private int[] values = {30, 60, 80, 40, 50, 60, 80, 40};
    private int c[] = {Color.MAGENTA, Color.BLUE, Color.RED, Color.CYAN, Color.YELLOW, Color.GREEN, Color.DKGRAY, Color.GRAY};
    private int valuesLength;
    private RectF rectF;
    private Paint slicePaint;
    private Path path;
    private float mWidth;
    private float mHeight;
    private float mRadius;
    private final float[] mTempResult = new float[2];
    private Paint mTextPaint;               // para el texto en la vista.

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(80f);

        valuesLength = values.length;
        slicePaint = new Paint();
        slicePaint.setAntiAlias(true);
        slicePaint.setDither(true);
        slicePaint.setStyle(Paint.Style.FILL);

        path = new Path();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (values != null) {
            int startTop = 0;
            int startLeft = 0;
            int endBottom = getHeight();
            int endRight = endBottom;// Esto hace un cuadrado igual.

            rectF = new RectF(startLeft, startTop, endRight, endBottom);

            float[] scaledValues = scale();
            float sliceStartPoint = 0;
            path.addCircle(rectF.centerX(), rectF.centerY(), 125, Path.Direction.CW);

            for (int i = 0; i < valuesLength; i++) {
                slicePaint.setColor(c[i]);
                path.reset();
                path.addArc(rectF, sliceStartPoint, scaledValues[i]);
                path.lineTo(rectF.centerX(), rectF.centerY());
                canvas.drawPath(path, slicePaint);
                sliceStartPoint += scaledValues[i];//Esto actualiza el punto de partida de la siguiente porción.
            }
            // Draw the text labels.
            final float labelRadius = mRadius + 20;
            StringBuffer label = mTempLabel;
            for (int i = 0; i < SELECTION_COUNT; i++) {
                float[] xyData = computeXYForPosition(i, labelRadius);
                float x = xyData[0];
                float y = xyData[1];
                label.setLength(0);
                label.append(values[i]);
                canvas.drawText(label, 0, label.length(), x, y, mTextPaint);
            }
        }
    }

    private float[] scale() {
        float[] scaledValues = new float[this.values.length];
        float total = getTotal(); //Totalizar todos los valores suministrados al gráfico
        for (int i = 0; i < this.values.length; i++) {
            scaledValues[i] = (this.values[i] / total) * 360; //Escala cada valor
        }
        return scaledValues;
    }

    private float getTotal() {
        float total = 0;
        for (float val : this.values)
            total += val;
        return total;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Calcular el radio a partir de la anchura y la altura.
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
    }
    private float[] computeXYForPosition (final int pos, final float radius) {
        float[] result = mTempResult;
        Double startAngle = Math.PI * (9 / 8d);   // Los ángulos están en radianes.
        Double angle = startAngle + (pos * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle)) + (mWidth / 2);
        result[1] = (float) (radius * Math.sin(angle)) + (mHeight / 2);
        return result;
    }
}
