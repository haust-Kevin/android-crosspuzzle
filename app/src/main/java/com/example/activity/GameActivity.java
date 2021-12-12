package com.example.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.CrosspuzzleApplication;
import com.example.R;
import com.example.adapter.CubeAdapter;
import com.example.data.Record;
import com.example.game.drawable.IDrawable4ColorMapper;
import com.example.game.generator.IColorGenerator;
import com.example.view.SquareView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private IColorGenerator generator = CrosspuzzleApplication.getGenerator();  // 在这初始化
    private IDrawable4ColorMapper mapper = CrosspuzzleApplication.getMapper(); // 在这初始化
    private GridView gvGraph;
    private CubeAdapter adapter;
    private List<Integer> colors;

    private SoundPool sp;
    private int emptySound;
    private int destorySound;
    private int bgmSound;
    private int gameoverSound;

    int windowWidth;
    int windowHeight;

    private int columnNum = 12;
    private int colorNum = 9;
    private double blankRate = 0.2;
    private int rowNum;

    private static final int TIME_PER_EMPTY = 10;


    private int score;
    /**
     * 计时模块
     */
    private final static int TOTAL_TIME = 100;
    private int time = TOTAL_TIME;
    private final Timer timer = new Timer();
    private TimerTask task;
    private TextView tvScore;
    private View vTime;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gvGraph = findViewById(R.id.gv_graph);
        tvScore = findViewById(R.id.tv_score);
        vTime = findViewById(R.id.time_view);
        score = 0;
        initSound();
        initGraph();
        bindItemClickListener();
        startTimer();
    }

    private void initSound() {
        sp = new SoundPool.Builder().setMaxStreams(10).build();
        emptySound = sp.load(this, R.raw.empty, 2);
        destorySound = sp.load(this, R.raw.destroy, 2);
        bgmSound = sp.load(this, R.raw.bgm, 0);
        gameoverSound = sp.load(this, R.raw.gameover, 2);
        sp.setOnLoadCompleteListener((soundPool, sampleId, status) -> bgm());
    }

    private void initGraph() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

       ConstraintLayout layout = findViewById(R.id.window_layout);

        Display display = getWindowManager().getDefaultDisplay();
        windowWidth = display.getWidth();
        windowHeight = display.getHeight();
//        Toast.makeText(this, windowWidth +"*" +windowHeight, Toast.LENGTH_LONG).show();
        int cubeSize = windowWidth / columnNum;

        gvGraph.setNumColumns(columnNum);
        gvGraph.setColumnWidth(cubeSize);
        rowNum = (int) (windowHeight * 0.8
                / cubeSize);

        colors = generator.generate(rowNum, columnNum, blankRate, colorNum);
        adapter = new CubeAdapter(this, colors);
        gvGraph.setAdapter(adapter);
    }

    private void bindItemClickListener() {
        gvGraph.setOnItemClickListener((parent, view, position, id) -> {
            if(colors.get(position) == 0)
                click(position);
        });
    }

    private void click(int position) {
        List<Integer> pos = new ArrayList<>();
        System.out.println(position);
        if (colors.get(position) == 0) {
            pos = crossItem(position);
        }
        if (pos.size() > 0) {
            updateScore(pos.size());
            destory();
        } else {
            updateTime(-TIME_PER_EMPTY);
            empty();
        }

        for (Integer p : pos) {
            SquareView view = getView(p);
            colors.set(p, 0);
            view.setBackground(mapper.map(0));
        }
    }

    private void empty() {
        sp.play(emptySound, 0.8f, 0.8f, 2, 0, 1);
    }

    private void destory() {
        sp.play(destorySound, 1f, 1f, 2, 0, 1);
    }

    private void bgm() {
       sp.play(bgmSound, 0.5f, 0.5f, 0, -1, 0.8f);
    }

    private void gameover() {
        sp.play(gameoverSound, 0.8f, 0.8f, 3, 0, 1);
    }


    private void updateScore(int score) {
        this.score += score;
        updateScoreView();
    }

    private void updateTime(int time) {
        synchronized (timer) {
            this.time += time;
        }
        updateTimeView();
    }

    private void updateScoreView() {
        tvScore.setText(String.valueOf(score));
    }

    private void updateTimeView() {
        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) vTime.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.width = (int) (windowWidth * ((double) time / TOTAL_TIME));
        vTime.setLayoutParams(linearParams);
    }

    // 核心算法
    private List<Integer> crossItem(int position) {
        System.out.println(position);

        ArrayList<Integer> poss = new ArrayList<>();
        ArrayList<Integer> ret = new ArrayList<>();

        poss.add(getTopPos(position));
        poss.add(getBottomPos(position));
        poss.add(getLeftPos(position));
        poss.add(getRightPos(position));

        for (Integer integer : poss) {
            if (integer != null)
                System.out.println(integer);
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer p : poss) {
            if (p != null) {
                if (!map.containsKey(colors.get(p))) {
                    map.put(colors.get(p), 0);
                }
                map.put(colors.get(p), map.get(colors.get(p)) + 1);
            }
        }

        for (Integer c : map.keySet()) {
            if (map.get(c) >= 2) {
                for (Integer p : poss) {
                    if (p != null && colors.get(p).equals(c)) {
                        ret.add(p);
                    }
                }
            }
        }
        System.out.println(ret);
        return ret;
    }

    // 获取四个直线方向的第一个非空白快
    private Integer getTopPos(int position) {
        for (int p = position - columnNum; p >= 0; p -= columnNum) {
            if (colors.get(p) != 0) {
                return p;
            }
        }
        return null;
    }

    private Integer getBottomPos(int position) {
        for (int p = position + columnNum; p < colors.size(); p += columnNum) {
            if (colors.get(p) != 0) {
                return p;
            }
        }
        return null;
    }

    private Integer getLeftPos(int position) {
        for (int p = position - 1; p >= (position / columnNum) * columnNum; p--) {
            if (colors.get(p) != 0) {
                return p;
            }
        }
        return null;
    }

    private Integer getRightPos(int position) {
        for (int p = position + 1; p < (position / columnNum + 1) * columnNum; p++) {
            if (colors.get(p) != 0) {
                return p;
            }
        }
        return null;
    }


    private SquareView getView(int position) {
        return gvGraph.getChildAt(position).findViewById(R.id.cube_view);
    }


    public void startTimer() {
        handler = new Handler(msg -> {
            if (msg.what == 1) {
                gameover();
                saveScore();
                gameoverDialog();
            } else {
                updateTimeView();
            }
            return true;
        });

        task = new TimerTask() {
            @Override
            public void run() {
                if (time <= 0) {
                    handler.sendEmptyMessage(1);
                    timer.cancel();
                } else {
                    synchronized (timer) {
                        time--;
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        };
        // 顶部计时模块
        Thread thread = new Thread(() -> timer.schedule(task, 1000, 1000));
        thread.start();
    }

    private void saveScore() {
        new Record(this).save(score);
    }

    private void gameoverDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon)
                .setTitle("Game Over")
                .setMessage("Your Score: " + score)
                .setNegativeButton("HOME",
                        (dialog, which) -> finish())
                .setPositiveButton("REPLAY",
                        (dialog, which) -> {
                            finish();
                            Intent intent = new Intent(GameActivity.this, GameActivity.class);
                            startActivity(intent);
                        })
                .setCancelable(false)
                .show();
    }

    @Override
    public void finish() {
        super.finish();
        sp.release();
    }
}