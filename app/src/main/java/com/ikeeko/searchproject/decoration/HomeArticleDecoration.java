package com.ikeeko.searchproject.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ZSC on 2020-06-23.
 */
public class HomeArticleDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;

    public HomeArticleDecoration() {
        this.mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
    }

    //设置ItemView的内嵌偏移长度（inset）
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(50, 20, 50, 20);
    }

    // 在子视图上设置绘制范围，并绘制内容，绘制图层在ItemView以下，所以如果绘制区域与ItemView区域相重叠，会被遮挡
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //获取recyclerview的child个数
        int childCount = parent.getChildCount();
        //遍历每个Item，分别获取他们的位置信息，然后再绘制对应的分割线
        for (int i = 0; i < childCount; i++) {
            //获取每个item的位置
            final View child = parent.getChildAt(i);
            //设置矩形（分割线）的宽度为10px
            final int mDivider = 10;
            //矩形左上顶点
            final int left = child.getLeft() + 40;
            final int top = child.getBottom();
            //矩形的右下角
            final int right = child.getRight() - 40;
            final int bottom = top + mDivider;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    //同样是绘制内容，但与onDraw（）的区别是：绘制在图层的最上层
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //获取recyclerview的child个数
        int childCount = parent.getChildCount();
        //遍历每个Item，分别获取他们的位置信息，然后再绘制对应的分割线
        for (int i = 0; i < childCount; i++) {
            //获取每个item的位置
            final View child = parent.getChildAt(i);
            //设置矩形（分割线）的宽度为10px
            final int mDivider = 10;
            //矩形左上顶点
            final int left = child.getLeft() + 40;
            final int top = child.getTop();
            //矩形的右下角
            final int right = child.getRight() - 40;
            final int bottom = top + mDivider;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
