package com.example.unzipped_apk;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_Game extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_STATE = "EXTRA_STATE";
    private List<ImageButton> arrows;
    int currentLevel;
    private boolean goodToGo;
    int[] steps;

    public Activity_Game() {
        super();
        this.currentLevel = 0;
        this.steps = new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3};
        this.goodToGo = true;
    }

    private void findViews() {
        this.arrows = new ArrayList<>(Arrays.asList(
                this.findViewById(R.id.game_BTN_left),
                this.findViewById(R.id.game_BTN_right),
                this.findViewById(R.id.game_BTN_up),
                this.findViewById(R.id.game_BTN_down)));
    }

    // currentLevel: left=0, right=1, up=2, down=3
    // 313521965 - Ohio             - steps={ 3, 1, 3, 1, 2, 1, 1, 2, 1 }
    //                              - steps={ D, R, D, R, U, R, R, U, R }
    private void arrowClicked(int currentLevel) {
        if (this.goodToGo && currentLevel != this.steps[this.currentLevel]) {
            this.goodToGo = false;
        }
        currentLevel = this.currentLevel + 1;
        if ((this.currentLevel = currentLevel) >= this.steps.length) {
            this.finishGame();
        }
    }

    private void finishGame() {
        final String stringExtra = this.getIntent().getStringExtra("EXTRA_STATE");
        if (this.goodToGo) {
            Toast.makeText((Context) this, (CharSequence) ("Survived in " + stringExtra), Toast.LENGTH_LONG).show();
            Log.e("pttt", "Survived in " + stringExtra);
        } else {
            Toast.makeText((Context) this, (CharSequence) "You Failed ", Toast.LENGTH_LONG).show();
            Log.e("pttt", "You Failed");
        }
        this.finish();
    }

    private void initViews() {
        for (int i = 0; i < arrows.size(); ++i) {
            arrows.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrowClicked(arrows.indexOf((ImageButton)v));
                }
            });
        }
    }

    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_game);
        final String stringExtra = this.getIntent().getStringExtra("EXTRA_ID");
        // 313521965 - Ohio             - steps={ 3, 1, 3, 1, 2, 1, 1, 2, 1 }
        if (stringExtra.length() == this.steps.length) {
            int index = 0;
            while (true) {
                final int[] steps = this.steps;
                if (index >= steps.length) {
                    break;
                }
                steps[index] = Integer.valueOf(String.valueOf(stringExtra.charAt(index))) % 4;
                ++index;
            }
        }
        this.findViews();
        this.initViews();
    }
}
