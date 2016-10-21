package com.yh.utils;

public class NumUtils {
    public static int String2Int(String s)
    {
        int i = 0;
        try
        {
            i = Integer.parseInt(s);
        }
        catch(Exception e)
        {
        }
        return i;
    }
    public static int String2Int(String s, int def_value)
    {
        int i = 0;
        try
        {
            i = Integer.parseInt(s);
        }
        catch(Exception e)
        {
            i = def_value;
        }
        return i;
    }
}
