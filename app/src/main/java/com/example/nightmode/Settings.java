package com.example.nightmode;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
public class Settings extends Fragment {

    private static ImageButton changeMode;
    private Context mContext;
    RadioGroup rg, langRG;
    private RadioGroup languageRadioGroup;
    Button langBtn;
    Button changLangbtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        changeMode = v.findViewById(R.id.dark_mode);
        langBtn = v.findViewById(R.id.lang_btn);
        switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())) {
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
        }

                changeMode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(mContext);
                        dialog.setContentView(R.layout.custom_layout);
                        rg = dialog.findViewById(R.id.radioGroupTheme);
                        switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())) {
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
                                        getActivity().recreate();
                                        break;
                                    case R.id.radioButtonLight:
                                        DarkModeHelper.getInstance().setPref("light", getActivity().getBaseContext());
                                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                        dialog.dismiss();
                                        getActivity().recreate();
                                        break;
                                    default:
                                        DarkModeHelper.getInstance().setPref("default", getActivity().getBaseContext());

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                        } else {
                                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                                        }
                                        dialog.dismiss();
                                        getActivity().recreate();

                                }

                            }
                        });

                        dialog.show();


                    }
                });

        changLangbtn = v.findViewById(R.id.lang_btn);

        changLangbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage();
            }
        });


        return v;

    }

    private void changeLanguage(){

        final Dialog langDialog = new Dialog(mContext);
        langDialog.setContentView(R.layout.lang_custom_layout);
        languageRadioGroup = langDialog.findViewById(R.id.radioGroupLang);
        String currentLanguageCode = LocaleHelper.getLanguage(getActivity());
        switch (currentLanguageCode) {

            case "en":
                languageRadioGroup.check(R.id.radioButtonEnglish);
                break;
            case "fa":
                languageRadioGroup.check(R.id.radioButtonFarsi);
                break;
            case "de":
                languageRadioGroup.check(R.id.radioButtonGerman);
                break;
        }
        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButtonEnglish:
                        LocaleHelper.setLocale(mContext,"en");
                        langDialog.dismiss();
                        getActivity().recreate();
                        break;
                    case R.id.radioButtonFarsi:
                        LocaleHelper.setLocale(mContext,"fa");
                        langDialog.dismiss();
                        getActivity().recreate();
                        break;
                    case R.id.radioButtonGerman:
                        LocaleHelper.setLocale(mContext,"de");
                        langDialog.dismiss();
                        getActivity().recreate();
                        break;
                }

            }
        });
        langDialog.show();



    }
}