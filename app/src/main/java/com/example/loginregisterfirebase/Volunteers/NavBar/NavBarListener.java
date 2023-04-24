package com.example.loginregisterfirebase.Volunteers.NavBar;

import android.content.Context;
import android.widget.Button;

public interface NavBarListener {
    void onMapButtonClick(Button button, Context context);
    void onHomeButtonClick(Button button,Context context);
    void onSettingsButtonClick(Button button, Context context);
}
