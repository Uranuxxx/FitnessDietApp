package com.example.fitnessdietapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        EditText inputWeight = findViewById(R.id.inputWeight);
        EditText inputHeight = findViewById(R.id.inputHeight);
        EditText inputGoalCalories = findViewById(R.id.inputGoalCalories);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        TextView resultView = findViewById(R.id.resultView);

        List<Food> foodDatabase = initializeFoodDatabase();

        btnCalculate.setOnClickListener(v -> {

            String weightText = inputWeight.getText().toString().trim();
            String heightText = inputHeight.getText().toString().trim();
            String goalCaloriesText = inputGoalCalories.getText().toString().trim();

            if (weightText.isEmpty() || heightText.isEmpty() || goalCaloriesText.isEmpty()) {
                resultView.setText("すべての項目を入力してください。");
                return;
            }

            try {

                int goalCalories = Integer.parseInt(goalCaloriesText);

                String recipe = generateRecipe(goalCalories, foodDatabase);

                resultView.setText(recipe);

            } catch (NumberFormatException e) {
                resultView.setText("正しい数値を入力してください。");
            }
        });
    }

    private List<Food> initializeFoodDatabase() {
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("鶏胸肉", 165));
        foodList.add(new Food("牛肉", 250));
        foodList.add(new Food("豚肉", 208));
        foodList.add(new Food("卵", 130));
        foodList.add(new Food("サーモン", 208));
        foodList.add(new Food("りんご", 52));
        foodList.add(new Food("ご飯", 240));
        foodList.add(new Food("ブロッコリー", 55));
        return foodList;
    }

    private String generateRecipe(int goalCalories, List<Food> foodDatabase) {
        StringBuilder recipe = new StringBuilder("おすすめの食事プラン:\n");
        int remainingCalories = goalCalories;

        for (Food food : foodDatabase) {
            if (remainingCalories <= 0) break;
            if (food.getCalories() <= remainingCalories) {
                recipe.append(food.getName())
                        .append(" - ")
                        .append(food.getCalories())
                        .append(" kcal\n");
                remainingCalories -= food.getCalories();
            }
        }



        return recipe.toString();
    }

    private static class Food {
        private final String name;
        private final int calories;

        public Food(String name, int calories) {
            this.name = name;
            this.calories = calories;
        }

        public String getName() {
            return name;
        }

        public int getCalories() {
            return calories;
        }
    }
}
