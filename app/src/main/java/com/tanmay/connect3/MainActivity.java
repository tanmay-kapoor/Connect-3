package com.tanmay.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int activePlayer = 1;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameIsActive = true;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 0 && gameIsActive) {
            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = activePlayer;

            if(activePlayer == 1) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 2;
            }else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            }

            counter.animate()
                    .translationYBy(1000f)
                    .rotation(1800f)
                    .setDuration(500);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 0) {

                    gameIsActive = false;

                    String winner = "Yellow";
                    if (gameState[winningPosition[0]] == 2)
                        winner = "Red";

                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout = findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                } else {
                    boolean draw = true;

                    for(int i : gameState) {
                        if(i == 0)
                            draw = false;
                    }

                    if(draw) {
                        TextView winnerMessage = findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a Draw!");
                        LinearLayout layout = findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);

                        gameIsActive = false;
                    }
                }
            }
        }
    }

    public void resetScreen(View view) {

        activePlayer = 1;
        gameIsActive = true;

        for (int i=0; i<gameState.length; i++)
            gameState[i] = 0;

        LinearLayout layout = findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout grid = findViewById(R.id.grid);

        for (int i=0; i<grid.getChildCount(); i++)
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
