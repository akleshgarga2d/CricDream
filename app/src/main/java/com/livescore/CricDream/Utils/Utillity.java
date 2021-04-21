package com.livescore.CricDream.Utils;

import android.util.Log;

import com.livescore.CricDream.R;

public class Utillity
{
   public static void p( String source, String message)
    {
        Log.e(" "+source ," --"+message+"--");
    }

    public static String checkString(String string)
    {
        return (string.equals(Constants.CHECK_STRING+"") ? "-" : ""+string);
    }

    public static String checkImage(String abc)
    {
        return (abc.equals(Constants.CHECK_IMAGE+"") ? ""+ R.mipmap.ic_launcher : ""+abc );
    }
}
