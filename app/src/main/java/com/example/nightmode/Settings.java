package com.example.nightmode;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends Fragment {

    private static ImageButton changeMode;
    Context context;
    RadioGroup rg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        changeMode = v.findViewById(R.id.dark_mode);


        switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())){
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }

        }

        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_layout);
                dialog.setTitle("Chose the mode");
                rg = dialog.findViewById(R.id.radioGroupTheme);
                switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())){
                    case "dark":
                        rg.check(R.id.radioButtonDark);
                        break;
                    case "light":
                        rg.check(R.id.radioButtonLight);
                        break;
                    default:
                        rg.check(R.id.radioButtonDefault);
                }
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.radioButtonDark:
                                DarkModeHelper.getInstance().setPref("dark", getActivity().getBaseContext());
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                dialog.dismiss();
                                break;
                            case R.id.radioButtonLight:
                                DarkModeHelper.getInstance().setPref("light", getActivity().getBaseContext());
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                dialog.dismiss();
                                break;
                            default:
                                DarkModeHelper.getInstance().setPref("default", getActivity().getBaseContext());

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                                }
                                dialog.dismiss();
                        }

                    }
                });

            dialog.show();


            }
        });


        return v;
    }
}