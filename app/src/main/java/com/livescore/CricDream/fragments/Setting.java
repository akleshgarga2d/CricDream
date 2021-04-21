package com.livescore.CricDream.fragments;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.livescore.CricDream.R;
import com.livescore.CricDream.activities.BrowseSeriesActivity;


public class Setting extends Fragment {

    private RelativeLayout mBrowseMatches, mAboutUs, mTerms, mPrivacy, mRate, mCheckUpdate;
    ImageView finish;
    ImageView ivBell;

    public Setting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mBrowseMatches = view.findViewById(R.id.browse_series);

        mAboutUs = view.findViewById(R.id.about_us);
        mTerms = view.findViewById(R.id.terms_condition);
        mPrivacy = view.findViewById(R.id.privacy_policy);
        mRate = view.findViewById(R.id.rate_us);
        mCheckUpdate = view.findViewById(R.id.check_update);


      //  ivBell.setVisibility(View.GONE);

    /*    mBrowseMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent il = new Intent(getActivity(), BrowseSeriesActivity.class);
                ;
                il.putExtra("type", 2);
                startActivity(il);
            }
        });*/

        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            dialog.dismiss();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true).setTitle("About Us").setMessage(getString(R.string.about)).setPositiveButton("OK", dialogClickListener).show();

            }
        });

        mTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            dialog.dismiss();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true).setTitle("Terms of Use").setMessage(getString(R.string.terms_m)).setPositiveButton("OK", dialogClickListener).show();

            }
        });

        mPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            dialog.dismiss();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true).setTitle("Privacy Policy").setMessage(getString(R.string.privacy_m)).setPositiveButton("OK", dialogClickListener).show();

            }
        });
        mRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "App is Not Present in Play Store, Will Be Added Soon", Toast.LENGTH_LONG).show();
                }

            }
        });

        mCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            dialog.dismiss();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true).setTitle("Update").setMessage(getString(R.string.check_update_m)).setPositiveButton("OK", dialogClickListener).show();

            }
        });
        return view;
    }

}
