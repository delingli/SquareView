package com.ldl.squareview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ldl.squareview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldl on 2017/3/2.
 */

public class SquareFlowView extends View {

    private Paint mPaint1;
    private Paint mPaint2;

    private int viewWidth;
    private int viewHeight;

    private int rose_color=Color.parseColor("#FFF8C2C2");
    private int fall_color=Color.parseColor("#FFDBF4C2");
    private int line=7;
    private int column=18
            ;

   private List<TipViewBean> elements = new ArrayList<>();
    private Paint mTextPaint;
    private Rect mBound;
    private Paint paint;//表格
    private Context context;
    public SquareFlowView(Context context) {
        this(context, null);
    }

    public SquareFlowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareFlowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareFlowView,defStyleAttr,0);
        int indexCount = typedArray.getIndexCount();
        for(int i=0;i<indexCount;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.SquareFlowView_rose_color:
                    rose_color= typedArray.getColor(attr,Color.parseColor("#FFF8C2C2"));
            break;
                case R.styleable.SquareFlowView_fall_color:
                    fall_color= typedArray.getColor(attr,Color.parseColor("#FFDBF4C2"));
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    /**
     * 变成普通二位数组
     * @param elements
     * @param row
     * @param column
     * @return
     */
    private TipViewBean[][]  arrangeTwoElement(List<TipViewBean> elements,int row, int column){
        TipViewBean[][] array = new TipViewBean[row][column];
        List< List<TipViewBean>> arrangedata=new ArrayList< List<TipViewBean>>();
        int x=0;
     for(int i=0;i<array.length;++i){
         for(int j=0;j<array[i].length;++j){
             if(x<=elements.size()-1&&elements.get(x)!=null){
                 array[i][j]=elements.get(x++);
             }

         }
     }
        return array;

    }

    /**
     * 转换成涨跌数组
     * @param elements
     * @param row
     * @param column
     * @return
     */

    public  TipViewBean[][] arrangeElement(List<TipViewBean> elements, int row, int column) {
        TipViewBean[][] array = new TipViewBean[row][column];
        List<List<TipViewBean>> elementLists = new ArrayList<List<TipViewBean>>();
        for(TipViewBean element : elements) {
            if(elementLists.size() == 0) {
                List<TipViewBean> list = new ArrayList<>();
                list.add(element);
                elementLists.add(list);
            } else {
                List<TipViewBean> list = elementLists.get(elementLists.size() - 1);
                TipViewBean lastElement = list.get(list.size() - 1);
                if(lastElement.isRose() == element.isRose()) {
                    list.add(element);
                } else {
                    list = new ArrayList<>();
                    list.add(element);
                    elementLists.add(list);
                }
            }
        }
        TipViewBean[][] tmpArray = new TipViewBean[row][elements.size()];
        int columnIndex = 0;
        int listIndex = 0;
        for(List<TipViewBean> list : elementLists) {
            int rowIndex = 0;
            columnIndex = listIndex;
            boolean isChangeDirection = false;
            for(TipViewBean element : list) {
                tmpArray[rowIndex][columnIndex] = element;
                if(rowIndex + 1 < row && tmpArray[rowIndex + 1][columnIndex] == null && !isChangeDirection) {
                    rowIndex ++;
                } else {
                    isChangeDirection = true;
                    columnIndex++;
                    if(rowIndex == 0) {
                        listIndex++;
                    }
                }
            }
            if(rowIndex != 0) {
                listIndex++;
            }
        }
        columnIndex = 0;
        for(int i = 0; i < tmpArray[0].length; i++) {
            for(int j = 0; j < tmpArray.length; j++) {
                if(tmpArray[j][i] != null) {
                    columnIndex = Math.max(columnIndex, i);
                    break;
                }
            }
        }
        int startColumnIndex = Math.max(columnIndex + 1 - column, 0);
        System.out.println();
        for(int i = 0; i < array.length; i++) {

            for(int j = 0; j < array[i].length; j++) {
                if(j + startColumnIndex < tmpArray[i].length) {
                    array[i][j] = tmpArray[i][j + startColumnIndex];
                }
                System.out.print(array[i][j] != null ? array[i][j].isRose() + "\t" : "null\t");
            }
            System.out.println();
        }
        return array;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareFlowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(px2dip(context,1));
//        paint.setTypeface(Typeface.NORMAL);
//        paint.setFakeBoldText(false);
        paint.setStyle(Paint.Style.FILL);



        mPaint1 = new Paint();
        mPaint1.setColor(rose_color);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();

        mPaint2.setColor(fall_color);
        mPaint2.setStyle(Paint.Style.FILL);

        // mPaint.setColor(mTitleTextColor);
        /**
         * 获得绘制文本的宽和高
         */
        mTextPaint = new Paint();
        mTextPaint.setTextSize(20);
        mTextPaint.setColor(Color.parseColor("#FF333333"));
        mBound = new Rect();
//        mTextPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    public  int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         int width=   MeasureSpec.getSize(widthMeasureSpec);
         setMeasuredDimension(width,line*(width/column));
    }

    public void setOnFillDadaListener(List<TipViewBean> data,OnFillDataListener listener){
        if(data==null)
            return;
        this.elements=data;
        this.listener=listener;
        this.postInvalidate();
    }
private OnFillDataListener listener;

public interface OnFillDataListener{
    /**
     * true 转换涨跌方格 false普通横向方格
     * @return
     */
    boolean isArrangeElement();
}

    /**
     *画方
     * @param canvas
     */
    private void paintSquare(Canvas canvas) {
        if(elements==null||elements.size()<=0)
            return;
        TipViewBean[][] array ;
        if(listener!=null){
            if(listener.isArrangeElement()){
                array=   arrangeElement(elements, line, column);
            }else{
                array=arrangeTwoElement(elements, line, column);
            }
        }else{
            return;
        }
        int itemWidth= viewWidth/column;
        int itemHeight= itemWidth;

        for(int j=0;j<column+1;++j){
            canvas.drawLine(itemWidth*j,0,itemWidth*j,line*itemWidth,paint);
        }
        for(int j=0;j<line+1;++j){
            canvas.drawLine(0,itemWidth*j,itemWidth*column,itemWidth*j,paint);
        }
        for(int i=0;i<array.length;++i){
            for(int j=0;j<array[i].length;++j){

            if(array[i][j] != null){
                array[i][j].setL(itemWidth*j+1);

                array[i][j].setT(itemWidth*i+1);
                array[i][j].setR(itemWidth*(j+1)-1);
                array[i][j].setB(itemWidth*(i+1)-1);

                System.out.print( array[i][j].getL() +"::"+ array[i][j].getT()+"::"+ array[i][j].getR()+"::"+ array[i][j].getB());
//                mPaint.setColor(mTitleTextColor);
                mBound = new Rect();
                if(array[i][j].isRose()){
                    canvas.drawRect(array[i][j].getL(),array[i][j].getT(),array[i][j].getR(),array[i][j].getB(),mPaint1);

                    mTextPaint.getTextBounds(array[i][j].getNickName(), 0, (array[i][j].getNickName()).length(), mBound);
                    canvas.drawText(array[i][j].getNickName(), array[i][j].getL()+(itemWidth/2)- (mBound.width() / 2), array[i][j].getT()+(itemWidth/2)+(mBound.height() / 2), mTextPaint);
                }else{
                    canvas.drawRect(array[i][j].getL(),array[i][j].getT(),array[i][j].getR(),array[i][j].getB(),mPaint2);
                    mTextPaint.getTextBounds(array[i][j].getNickName(), 0, (array[i][j].getNickName()).length(), mBound);
                    canvas.drawText(array[i][j].getNickName(),array[i][j].getL()+(itemWidth/2)- (mBound.width() / 2), array[i][j].getT()+(itemWidth/2)+(mBound.height() / 2), mTextPaint);
//                    canvas.drawText(array[i][j].getId()+"", array[i][j].getL()+ mBound.width() / 2, array[i][j].getR()+ mBound.height() / 2, mTextPaint);
                }

            }
            }
            System.out.println();
        }
    }
    boolean isOndraw=false;
    @Override
    protected void onDraw(Canvas canvas) {
        if(!isOndraw){
            viewWidth = this.getMeasuredWidth();
            viewHeight = this.getMeasuredHeight();
            Log.d("ALL","长宽多少"+viewWidth+"--------"+viewHeight);
            isOndraw=true;
        }
        if(elements!=null&&elements.size()>0){
            paintSquare(canvas);
            return;
        }
        super.onDraw(canvas);
    }


}