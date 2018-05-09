package com.test.question.bean;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.test.question.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimetableView extends ViewGroup {

    private Context context;

    private List<CursorTableView> mList;

    private static final int MAX_DAY = 7; //一周 7 天

    private static final int MAX_SECTION = 12; //一天 12 节课

    private int screenWidth;//屏幕宽度
    private int screenHeight;//屏幕高度

    private int gridItemWidth;//格子宽高相等
    private int gridItemHeight;

    private int titleHeight;//顶部显示周几栏的高度
    private int sectionWidth;//左边显示第几节课栏的宽度

    private Paint mDividerPaint; //边线画笔
    private Paint mTitlePaint;//文字画笔

    private String[] dayOfWeek = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    public static final int colors[] = {R.drawable.select_course_item_blue,
            R.drawable.select_course_item_green, R.drawable.select_course_item_red,
            R.drawable.select_course_item_cyan, R.drawable.select_course_item_pink,
            R.drawable.select_course_item_purple, R.drawable.select_course_item_orange,
            R.drawable.select_course_item_deep_orange, R.drawable.select_course_item_deep_purple,
            R.drawable.select_course_item_light_blue, R.drawable.select_course_item_light_green,
            R.drawable.select_course_item_indigo, R.drawable.select_course_item_teal,
            R.drawable.select_course_item_deep_orange, R.drawable.select_course_item_deep_purple,
            R.drawable.select_course_item_light_blue, R.drawable.select_course_item_light_green,
            R.drawable.select_course_item_indigo, R.drawable.select_course_item_teal,
            R.drawable.select_course_item_indigo, R.drawable.select_course_item_teal,
            R.drawable.select_course_item_deep_orange, R.drawable.select_course_item_deep_purple,
            R.drawable.select_course_item_light_blue, R.drawable.select_course_item_light_green,
            R.drawable.select_course_item_indigo, R.drawable.select_course_item_teal
    };


    private String[] courseColors = new String[30];
    private int CourseColorArrIndex = 0;

    private OnCourseItemClickListener mListener;

    private int currentWeek;//当前周

    public TimetableView(Context context) {
        this(context, null);
    }

    public TimetableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimetableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mList = new ArrayList<>();

        mDividerPaint = new Paint();
        mDividerPaint.setColor(0xffbdbdbd);

        mTitlePaint = new Paint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setTextSize(32);
        mTitlePaint.setColor(0xff444444);

        screenWidth = getScreenWidth();
        screenHeight = getScreenHeight();

        sectionWidth = dp2px(20);
        titleHeight = dp2px(30);
        gridItemWidth = (screenWidth - sectionWidth) / MAX_DAY + 1;
        gridItemHeight = (screenHeight - titleHeight) / MAX_SECTION;

        //在 ViewGroup 中,如果不设置这个属性为 false,默认是不会调用 onDraw 方法的
        setWillNotDraw(false);

        currentWeek = 0;//默认显示全部课程
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(screenWidth, screenHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void drawViews(List<CursorTableView> list) {
        Map<Integer, List<CursorTableView>> allDays = getOneDayCourses(list);
        for (int i = 1; i <= MAX_DAY; i++) {
            drawOneDayCourse(i, allDays.get(i));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawVerticalDivider(canvas);
        drawHorizontalDivider(canvas);
    }


    /**
     * 画一天的课程,首先需要将一天的课程按照开始节次排序,然后从上到下依次画
     */
    private void drawOneDayCourse(int day, List<CursorTableView> courses) {

        if (courses == null) {
            return;
        }

        //将一天的课程排序
        Collections.sort(courses, new Comparator<CursorTableView>() {
            @Override
            public int compare(CursorTableView lhs, CursorTableView rhs) {
                return lhs.getFirst() - rhs.getFirst();
            }
        });

        for (int i = 0; i < courses.size(); i++) {
            final CursorTableView course = courses.get(i);

            TextView tvItem = new TextView(context);

            tvItem.layout(gridItemWidth * (day - 1) + sectionWidth + 1, gridItemHeight * (course.getFirst() - 1) + 1 + titleHeight, gridItemWidth * day + sectionWidth - 1, gridItemHeight * course.getEnd() + titleHeight - 1);

            tvItem.setTextColor(0xffffffff);
            tvItem.setTextSize(12);
            tvItem.setText(new StringBuilder().append(course.getName()).append("@").append(course.getAddress()));
            tvItem.setGravity(Gravity.CENTER);

            tvItem.setBackgroundResource(colors[getColorIndex(course.getName())]);

            tvItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        mListener.onCourseItemClick(course);

                    }
                }
            });

            addView(tvItem);
        }
    }

    public void loadCourses(List<CursorTableView> list) {
        removeAllViews();
        mList.clear();
        mList.addAll(list);
        drawViews(mList);
        invalidate();
    }

    public void clear() {
        removeAllViews();
        mList.clear();
        invalidate();
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
        removeAllViews();
        drawViews(mList);
        invalidate();
    }


    private Map<Integer, List<CursorTableView>> getOneDayCourses(List<CursorTableView> list) {
        Map<Integer, List<CursorTableView>> allDays = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            matchCourseName(list.get(i).getName());
            if (allDays.containsKey(list.get(i).getDay())) {
                allDays.get(list.get(i).getDay()).add(list.get(i));
            } else {
                List<CursorTableView> oneDayList = new ArrayList<>();
                oneDayList.add(list.get(i));
                allDays.put(list.get(i).getDay(), oneDayList);
            }
        }
        return allDays;
    }

    /**
     * 画竖直方向的分割线和周几
     */
    private void drawVerticalDivider(Canvas canvas) {
        for (int i = 0; i <= MAX_DAY; i++) {

            if (i == 0) {
                canvas.drawLine(sectionWidth, 0, sectionWidth, screenHeight, mDividerPaint);
            } else {

                canvas.drawLine(i * gridItemWidth + sectionWidth, 0, i * gridItemWidth + sectionWidth, screenHeight, mDividerPaint);


                String day = dayOfWeek[i - 1];
                // 画文字
                Paint.FontMetrics fontMetrics = mTitlePaint.getFontMetrics();
                float textWidth = mTitlePaint.measureText(day);
                float cx = (i - 1) * gridItemWidth + sectionWidth + gridItemWidth / 2;
                float cy = (float) titleHeight / 2;

                if (i == currentDayOfWeek()) {//对今天高亮显示
                    mTitlePaint.setColor(0xffffffff);
                    RectF rectF = new RectF((i - 1) * gridItemWidth + sectionWidth, 0, i * gridItemWidth + sectionWidth, titleHeight);
                    canvas.drawRect(rectF, mDividerPaint);
                } else {
                    mTitlePaint.setColor(0xff444444);
                }
                canvas.drawText(day, cx - textWidth / 2, cy + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, mTitlePaint);
            }
        }
    }


    /**
     * 画水平方向的分割线和节次
     */
    private void drawHorizontalDivider(Canvas canvas) {
        for (int j = 0; j <= MAX_SECTION; j++) {
            if (j == 0) {
                canvas.drawLine(0, titleHeight, screenWidth, titleHeight, mDividerPaint);
            } else {
                canvas.drawLine(0, j * gridItemHeight + titleHeight, screenWidth, j * gridItemHeight + titleHeight, mDividerPaint);

                Paint.FontMetrics fontMetrics = mTitlePaint.getFontMetrics();
                //画文字
                String section = String.valueOf(j);
                float textWidth = mTitlePaint.measureText(section);
                float cx = (float) sectionWidth / 2;
                float cy = (j - 1) * gridItemHeight + titleHeight + gridItemHeight / 2;
                mTitlePaint.setColor(0xff444444);
                canvas.drawText(section, cx - textWidth / 2, cy + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, mTitlePaint);
            }
        }
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();

        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    private int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();

        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

        return dm.heightPixels - getStatusBarHeight();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 将课程名装入颜色数组,每一个课程对应一种颜色,相同课程颜色相同
     */
    private void matchCourseName(String courseName) {
        boolean isRepeat = true;
        for (int i = 0; i < 30; i++) {
            if (courseName.equals(courseColors[i])) {
                isRepeat = true;
                break;
            } else {
                isRepeat = false;
            }
        }
        if (!isRepeat) {
            courseColors[CourseColorArrIndex] = courseName;
            CourseColorArrIndex++;
        }
    }


    /**
     * 当前是一周中的第几天
     */
    public int currentDayOfWeek() {
        Calendar cal = Calendar.getInstance();

        int day = cal.get(Calendar.DAY_OF_WEEK);

        //Java中的Calendar,一周的第一天是星期日,这里做一个转换
        int[] days = {7, 1, 2, 3, 4, 5, 6};

        return days[day - 1];
    }

    /**
     * 获取数组中的课程对应的索引
     */
    public int getColorIndex(String courseName) {
        int index = 0;
        for (int i = 0; i < 20; i++) {
            if (courseName.equals(courseColors[i])) {
                index = i;
            }
        }
        return index;
    }


    public void setOnCourseItemClickListener(OnCourseItemClickListener listener) {
        mListener = listener;

    }

    public interface OnCourseItemClickListener {
        void onCourseItemClick(CursorTableView course);
    }
}




