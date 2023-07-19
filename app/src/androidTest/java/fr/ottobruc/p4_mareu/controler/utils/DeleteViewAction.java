package fr.ottobruc.p4_mareu.controler.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

//import com.openclassrooms.entrevoisins.R;

import org.hamcrest.Matcher;

import fr.ottobruc.p4_mareu.R;

public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(R.id.deleteButton);
        // Maybe check for null
        button.performClick();
    }
}