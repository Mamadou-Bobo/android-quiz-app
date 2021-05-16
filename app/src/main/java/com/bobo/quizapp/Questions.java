package com.bobo.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Questions extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<String> questions = new ArrayList<>();
    private final ArrayList<String> correctAnswer = new ArrayList<>();
    private final ArrayList<String> incorrecteAnswers = new ArrayList<>();

    private int count = 0, deletedId, score, counter = 1;
    private String userName;

    private ProgressBar progressBar;

    private RelativeLayout parent;

    private TextView singleQuestion, txtViewOne, txtViewTwo, txtViewThree, txtViewFour, txtViewCounter;

    private Button btnNext;

    private boolean isClicked = false;

    private final ArrayList<Integer> txtViewId = new ArrayList<>();

    // Contient les identifiants des text view
    private final ArrayList<Integer> allTxtViewId = new ArrayList<>();

    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_activity);

        String level = "", category = "";
        int position;

        Intent intent = getIntent();

        userName = intent.getStringExtra("username");
        category = intent.getStringExtra("category");
        position = intent.getIntExtra("position",0);
        level = intent.getStringExtra("level");

        if(category.equals("any category") && level.equals("any difficulty")) {
            url = "https://opentdb.com/api.php?amount=10";
        } else if(category.equals("any category")) {
            url = "https://opentdb.com/api.php?amount=10&difficulty="+level;
        } else if(level.equals("any difficulty")) {
            url = "https://opentdb.com/api.php?amount=10&category="+position;
        } else {
            url = "https://opentdb.com/api.php?amount=10&category="+position+"&difficulty="+level;
        }

        singleQuestion = findViewById(R.id.question);
        txtViewOne = findViewById(R.id.txtViewOne);
        txtViewTwo = findViewById(R.id.txtViewTwo);
        txtViewThree = findViewById(R.id.txtViewThree);
        txtViewFour = findViewById(R.id.txtViewFour);
        txtViewCounter = findViewById(R.id.counter);

        progressBar = findViewById(R.id.progressBar);

        parent = findViewById(R.id.parent);

        btnNext = findViewById(R.id.btnNext);

        txtViewOne.setOnClickListener(this);
        txtViewTwo.setOnClickListener(this);
        txtViewThree.setOnClickListener(this);
        txtViewFour.setOnClickListener(this);

        allTxtViewId.add(txtViewOne.getId());
        allTxtViewId.add(txtViewTwo.getId());
        allTxtViewId.add(txtViewThree.getId());
        allTxtViewId.add(txtViewFour.getId());

        StringRequest request = new StringRequest(url, this::parseJsonData, volleyError -> Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show());

        RequestQueue rQueue = Volley.newRequestQueue(Questions.this);
        rQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        setColorOnClick(v.getId());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setColorOnClick(int viewId) {
        if(!txtViewId.contains(viewId)) {
            txtViewId.add(viewId);
        }

        this.isClicked = true;

        deleteCurrentTextView(viewId);

        TextView txtView = findViewById(viewId);
        TextView textView;

        Spanned currentQuestion = formatString(this.correctAnswer.get(this.count));

        if(currentQuestion.toString().equals(txtView.getText().toString())) {
            txtView.setTextColor(Color.WHITE);
            txtView.setBackground(getResources().getDrawable(R.drawable.rounded_textview_green));
            this.score++;
        } else {

            if(currentQuestion.toString().equals(txtViewOne.getText().toString())) {
                textView = findViewById(txtViewOne.getId());
                textView.setTextColor(Color.WHITE);
                textView.setBackground(getResources().getDrawable(R.drawable.rounded_textview_green));
            } else if(currentQuestion.toString().equals(txtViewTwo.getText().toString())) {
                textView = findViewById(txtViewTwo.getId());
                textView.setTextColor(Color.WHITE);
                textView.setBackground(getResources().getDrawable(R.drawable.rounded_textview_green));
            } else if(txtViewThree.getVisibility() == View.VISIBLE && currentQuestion.toString().equals(txtViewThree.getText().toString())) {
                textView = findViewById(txtViewThree.getId());
                textView.setTextColor(Color.WHITE);
                textView.setBackground(getResources().getDrawable(R.drawable.rounded_textview_green));
            } else if(txtViewFour.getVisibility() == View.VISIBLE && txtViewFour.getText().toString().equals(currentQuestion.toString())) {
                textView = findViewById(txtViewFour.getId());
                textView.setTextColor(Color.WHITE);
                textView.setBackground(getResources().getDrawable(R.drawable.rounded_textview_green));
            }

            txtView.setTextColor(Color.WHITE);
            txtView.setBackground(getResources().getDrawable(R.drawable.rounded_textview_red));
        }
    }

    private void deleteCurrentTextView(int currentTxtViewId) {
        // on supprime d'abord l'identifiant du text view sélectionné
        for(int i = 0; i < this.allTxtViewId.size(); i++) {
            if(this.allTxtViewId.get(i).equals(currentTxtViewId)) {
                this.deletedId = this.allTxtViewId.get(i);
                this.allTxtViewId.remove(i);
                break;
            }
        }

        setTxtViewClickableOrNot(this.allTxtViewId,false);
    }

    private void setTxtViewClickableOrNot(ArrayList<Integer> array, boolean isTrueOrFalse) {
        for (Integer id : array) {
            TextView txtView = findViewById(id);
            if (isTrueOrFalse) {
                if(!txtView.equals(null)) {
                    txtView.setClickable(true);
                }
            } else {
                if(!txtView.equals(null)) {
                    txtView.setClickable(false);
                }
            }
        }
    }

    private void setTxtViewClickable() {
        // ajout de l'identifiant du text view qui a été supprimé
        this.allTxtViewId.add(this.deletedId);

        // ensuite on rend tous les text view clickables
        setTxtViewClickableOrNot(this.allTxtViewId,true);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void hideTextViewBackgroundColorOnBtnNextClicked() {
        this.txtViewId.forEach((id) -> {
            TextView txtView = findViewById(id);
            txtView.setBackground(getResources().getDrawable(R.drawable.rounded_textview_white));
            txtView.setTextColor(Color.BLACK);
        });

        txtViewOne.setBackground(getResources().getDrawable(R.drawable.rounded_textview_white));
        txtViewTwo.setBackground(getResources().getDrawable(R.drawable.rounded_textview_white));

        txtViewOne.setTextColor(Color.BLACK);
        txtViewTwo.setTextColor(Color.BLACK);

        if(txtViewThree.getVisibility() == View.VISIBLE && txtViewFour.getVisibility() == View.VISIBLE) {
            txtViewThree.setTextColor(Color.BLACK);
            txtViewFour.setTextColor(Color.BLACK);
            txtViewThree.setBackground(getResources().getDrawable(R.drawable.rounded_textview_white));
            txtViewFour.setBackground(getResources().getDrawable(R.drawable.rounded_textview_white));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);

            JSONArray allResult = object.getJSONArray("results");

            final int numberOfResponse = allResult.length();

            for(int i = 0; i < numberOfResponse; ++i) {
                JSONObject obj = allResult.getJSONObject(i);

                this.questions.add(obj.getString("question"));
                this.incorrecteAnswers.add(obj.getString("incorrect_answers"));
                this.correctAnswer.add(obj.getString("correct_answer"));
            }
            showCurrentQuestion();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int incrementValue() {
        if(this.count < 9) {
            this.count++;
        }
        return this.count;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showCurrentQuestion() throws JSONException {
        if(this.count == 0) {
            txtViewCounter.setText(this.counter + "/10");
            singleQuestion.setText(formatString(this.questions.get(this.count)));
            setQuestion(this.count);
        }
            btnNext.setOnClickListener(v -> {
                if(this.isClicked) {
                    if(this.count == 9) {
                        Intent intent = new Intent(Questions.this, Result.class);
                        intent.putExtra("name", this.userName);
                        intent.putExtra("score", this.score);
                        startActivity(intent);
                    }

                    int count = incrementValue();

                    try {
                        setQuestion(count);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    singleQuestion.setText(formatString(this.questions.get(count)));

                    try {
                        setQuestion(count);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    this.counter++;

                    progressBar.incrementProgressBy(1);

                    if(this.counter < 11) {
                        txtViewCounter.setText(this.counter + "/10");
                    }

                    setTxtViewClickable();

                } else {
                    Toast.makeText(this, "Select Answer", Toast.LENGTH_SHORT).show();
                }

                this.isClicked = false;
            });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setQuestion(int count) throws JSONException {
        String incorrectAnswer = this.incorrecteAnswers.get(count);

        JSONArray incorrectArray = new JSONArray(incorrectAnswer);

        Random rand = new Random();

        if(incorrectArray.getString(0).equals("True")  || incorrectArray.getString(0).equals("False")) {

            QuestionModel questionModel = new QuestionModel();

            int randomValue = 1 + rand.nextInt(2);
            switch (randomValue) {
                case 1:
                    questionModel = new QuestionModel(incorrectArray.getString(0),
                            correctAnswer.get(count));
                    break;
                case 2:
                    questionModel = new QuestionModel(correctAnswer.get(count),
                            incorrectArray.getString(0));
                    break;
                default:
                    break;
            }

            txtViewOne.setText(questionModel.getReponseOne());
            txtViewTwo.setText(questionModel.getReponseTwo());
            txtViewThree.setVisibility(View.GONE);
            txtViewFour.setVisibility(View.GONE);
        } else {

            txtViewThree.setVisibility(View.VISIBLE);
            txtViewFour.setVisibility(View.VISIBLE);

            QuestionModel questionModel = new QuestionModel();

            int randomValue = 1 + rand.nextInt(4);

            switch (randomValue) {
                case 1:
                    questionModel = new QuestionModel(incorrectArray.getString(0),
                            incorrectArray.getString(1),
                            incorrectArray.getString(2),
                            correctAnswer.get(count));
                    break;
                case 2:
                    questionModel = new QuestionModel(incorrectArray.getString(1),
                            correctAnswer.get(count),
                            incorrectArray.getString(0),
                            incorrectArray.getString(2));
                    break;
                case 3:
                    questionModel = new QuestionModel(incorrectArray.getString(1),
                            incorrectArray.getString(0),
                            correctAnswer.get(count),
                            incorrectArray.getString(2));
                    break;
                case 4:
                    questionModel = new QuestionModel(correctAnswer.get(count),
                            incorrectArray.getString(2),
                            incorrectArray.getString(1),
                            incorrectArray.getString(0));
                    break;
                default:
                    break;
            }

            txtViewOne.setText(formatString(questionModel.getReponseOne()));
            txtViewTwo.setText(formatString(questionModel.getReponseTwo()));
            txtViewThree.setText(formatString(questionModel.getReponseThree()));
            txtViewFour.setText(formatString(questionModel.getReponseFour()));
        }

        hideTextViewBackgroundColorOnBtnNextClicked();
    }

    private Spanned formatString(String newString) {
        return HtmlCompat.fromHtml(newString,HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

}
