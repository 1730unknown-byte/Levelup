package com.levelup.productivity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.view.*;
import java.util.*;

public class MainActivity extends Activity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
    }

    class GameView extends View {

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        SharedPreferences prefs;

        int xp;
        int level;
        int completed;

        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<Boolean> done = new ArrayList<>();

        int page = 0;

        int bg = Color.rgb(10, 15, 13);
        int card = Color.rgb(22, 29, 26);
        int text = Color.WHITE;
        int secondary = Color.rgb(170, 180, 175);
        int accent = Color.rgb(124, 77, 255);
        int green = Color.rgb(70, 200, 120);

        GameView(Context context) {
            super(context);

            prefs = getSharedPreferences("levelup", MODE_PRIVATE);

            xp = prefs.getInt("xp", 0);
            level = prefs.getInt("level", 1);
            completed = prefs.getInt("completed", 0);

            tasks.add("شرب الماء");
            tasks.add("ممارسة الرياضة");
            tasks.add("قراءة القرآن");
            tasks.add("تعلم شيء جديد");

            for (int i = 0; i < tasks.size(); i++) {
                done.add(false);
            }

            p.setTypeface(Typeface.create("sans", Typeface.NORMAL));
        }

        void save() {
            prefs.edit()
                    .putInt("xp", xp)
                    .putInt("level", level)
                    .putInt("completed", completed)
                    .apply();
        }

        @Override
        protected void onDraw(Canvas c) {
            super.onDraw(c);

            c.drawColor(bg);

            if (page == 0) drawHome(c);
            else if (page == 1) drawTasks(c);
            else if (page == 2) drawCharacter(c);
            else drawAchievements(c);

            drawBottomBar(c);
        }

        void drawText(Canvas c, String s, float x, float y,
                      float size, int color, Paint.Align align) {

            p.setTextSize(size * 1.20f);
            p.setColor(color);
            p.setTextAlign(align);
            p.setTypeface(Typeface.create("sans", Typeface.NORMAL));
            c.drawText(s, x, y, p);
        }

        void roundRect(Canvas c, float l, float t, float r, float b,
                       float radius, int color) {
            p.setColor(color);
            c.drawRoundRect(l, t, r, b, radius, radius, p);
        }

        void drawHome(Canvas c) {

            drawText(c, "LevelUp", getWidth() - 30, 55,
                    28, text, Paint.Align.RIGHT);

            drawText(c, "طوّر نفسك كل يوم", getWidth() - 30, 82,
                    15, secondary, Paint.Align.RIGHT);

            // Level card
            roundRect(c, 25, 110, getWidth() - 25, 270, 25, card);

            drawText(c, "المستوى", getWidth() - 50, 150,
                    15, secondary, Paint.Align.RIGHT);

            drawText(c, String.valueOf(level), getWidth() - 50, 205,
                    52, text, Paint.Align.RIGHT);

            drawText(c, xp + " XP", 50, 150,
                    18, green, Paint.Align.LEFT);

            float progress = (xp % 100) / 100f;

            p.setColor(Color.rgb(45, 52, 48));
            c.drawRoundRect(50, 225, getWidth() - 50, 242,
                    10, 10, p);

            p.setColor(accent);
            c.drawRoundRect(50, 225,
                    50 + (getWidth() - 100) * progress,
                    242, 10, 10, p);

            drawText(c, "التقدم إلى المستوى التالي",
                    getWidth() - 50, 260,
                    13, secondary, Paint.Align.RIGHT);

            // Today's tasks
            drawText(c, "مهام اليوم", getWidth() - 30, 315,
                    22, text, Paint.Align.RIGHT);

            int y = 350;

            for (int i = 0; i < Math.min(3, tasks.size()); i++) {

                roundRect(c, 25, y, getWidth() - 25,
                        y + 62, 18, card);

                drawText(c, done.get(i) ? "✓" : "○",
                        50, y + 40, 25,
                        done.get(i) ? green : secondary,
                        Paint.Align.CENTER);

                drawText(c, tasks.get(i),
                        getWidth() - 50, y + 39,
                        17, text, Paint.Align.RIGHT);

                y += 75;
            }
        }

        void drawTasks(Canvas c) {

            drawText(c, "المهام", getWidth() - 30, 60,
                    28, text, Paint.Align.RIGHT);

            int y = 100;

            for (int i = 0; i < tasks.size(); i++) {

                roundRect(c, 25, y, getWidth() - 25,
                        y + 70, 20, card);

                drawText(c, done.get(i) ? "✓" : "○",
                        55, y + 45, 28,
                        done.get(i) ? green : secondary,
                        Paint.Align.CENTER);

                drawText(c, tasks.get(i),
                        getWidth() - 55, y + 43,
                        18, text, Paint.Align.RIGHT);

                drawText(c, "+25 XP",
                        getWidth() - 55, y + 63,
                        11, secondary, Paint.Align.RIGHT);

                y += 85;
            }

            roundRect(c, 25, y + 10, getWidth() - 25,
                    y + 70, 20, accent);

            drawText(c, "+ إضافة مهمة",
                    getWidth() / 2, y + 48,
                    17, Color.WHITE, Paint.Align.CENTER);
        }

        void drawCharacter(Canvas c) {

            drawText(c, "شخصيتي", getWidth() - 30, 60,
                    28, text, Paint.Align.RIGHT);

            roundRect(c, 25, 100, getWidth() - 25,
                    430, 30, card);

            // Avatar
            p.setColor(accent);
            c.drawCircle(getWidth() / 2, 210, 75, p);

            drawText(c, "L", getWidth() / 2,
                    230, 70, Color.WHITE, Paint.Align.CENTER);

            drawText(c, "المغامر", getWidth() / 2,
                    330, 24, text, Paint.Align.CENTER);

            drawText(c, "المستوى " + level,
                    getWidth() / 2, 365,
                    16, secondary, Paint.Align.CENTER);

            drawText(c, "المهام المكتملة: " + completed,
                    getWidth() / 2, 400,
                    15, green, Paint.Align.CENTER);
        }

        void drawAchievements(Canvas c) {

            drawText(c, "الإنجازات", getWidth() - 30, 60,
                    28, text, Paint.Align.RIGHT);

            String[] names = {
                    "أول خطوة",
                    "5 مهام مكتملة",
                    "10 مهام مكتملة",
                    "المستوى 5"
            };

            int y = 100;

            for (int i = 0; i < names.length; i++) {

                roundRect(c, 25, y, getWidth() - 25,
                        y + 70, 20, card);

                drawText(c, "★",
                        55, y + 45,
                        27, accent, Paint.Align.CENTER);

                drawText(c, names[i],
                        getWidth() - 55, y + 44,
                        17, text, Paint.Align.RIGHT);

                y += 85;
            }
        }

        void drawBottomBar(Canvas c) {

            int h = getHeight();

            p.setColor(Color.rgb(15, 21, 18));
            c.drawRect(0, h - 80, getWidth(), h, p);

            String[] names = {"الرئيسية", "المهام", "الشخصية", "الإنجازات"};

            for (int i = 0; i < 4; i++) {

                float x = getWidth() * (i + 0.5f) / 4f;

                drawText(c, names[i], x, h - 25,
                        28,
                        page == i ? green : secondary,
                        Paint.Align.CENTER);
            }
        }

        @Override
        public boolean onTouchEvent(android.view.MotionEvent event) {

            if (event.getAction() != MotionEvent.ACTION_UP)
                return true;

            float x = event.getX();
            float y = event.getY();

            int h = getHeight();

            // Bottom navigation
            if (y > h - 90) {

                int selected = (int)(x / (getWidth() / 4f));

                if (selected >= 0 && selected <= 3) {
                    page = selected;
                    invalidate();
                }

                return true;
            }

            // Task completion
            if (page == 0 || page == 1) {

                int startY = page == 0 ? 350 : 100;
                int itemHeight = page == 0 ? 75 : 85;

                for (int i = 0; i < tasks.size(); i++) {

                    if (y >= startY && y <= startY + 65) {

                        if (!done.get(i)) {

                            done.set(i, true);

                            xp += 25;
                            completed++;

                            if (xp >= level * 100) {
                                level++;
                            }

                            save();
                        }

                        invalidate();
                        return true;
                    }

                    startY += itemHeight;
                }
            }

            return true;
        }
    }
}
