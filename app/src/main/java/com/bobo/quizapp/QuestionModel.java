package com.bobo.quizapp;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class QuestionModel {

    private String reponseOne;
    private String reponseTwo;
    private String reponseThree;
    private String reponseFour;

    public QuestionModel() {

    }

    public QuestionModel(String reponseOne, String reponseTwo, String reponseThree, String reponseFour) {
        this.reponseOne = reponseOne;
        this.reponseTwo = reponseTwo;
        this.reponseThree = reponseThree;
        this.reponseFour = reponseFour;
    }

    public QuestionModel(String reponseOne, String reponseTwo) {
        this.reponseOne = reponseOne;
        this.reponseTwo = reponseTwo;
    }

    public String getReponseOne() {
        return reponseOne;
    }

    public String getReponseTwo() {
        return reponseTwo;
    }

    public String getReponseThree() {
        return reponseThree;
    }

    public String getReponseFour() {
        return reponseFour;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "reponseOne='" + reponseOne + '\'' +
                ", reponseTwo='" + reponseTwo + '\'' +
                ", reponseThree='" + reponseThree + '\'' +
                ", reponseFour='" + reponseFour + '\'' +
                '}';
    }

}
