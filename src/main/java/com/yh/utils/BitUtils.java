package com.yh.utils;

/**
 * Created by FQ.CHINA on 2016/10/28.
 */
public class BitUtils {
    //取value第bit位的数值(bit 最小是0)
    public static boolean getBit(int value,int bit)
    {
        return getBit((long)value,bit);
    }
    public static boolean getBit(long value,int bit)
    {
        int i;
        //bit=bit-1;
        for(i=0;i<bit;i++)
        {
            value=(long)value/2;
        }
        value=value%2;
        if(value==1L)
            return true;
        else
            return false;

    }
    //设置value的第bit位的数值(bit 最小是0)
    public static int  setBit(int value,int bit,boolean bitValue)
    {
        return (int)setBit((long)value,bit,bitValue);
    }
    public static long setBit(long value,int bit,boolean bitValue)
    {

        long newValue=value;
        long addValue=1;
        if(getBit(value,bit)==bitValue)
            return newValue;
        else
        {
            //bit=bit-1;
            for(int i=0;i<bit;i++)
            {
                addValue=addValue*2;
            }

            if(bitValue)
            {
                newValue=value+addValue;
            }
            else
            {
                newValue=value-addValue;
            }
        }
        return newValue;
    }



    private static int aclState;
    public static void setPermission(int permission,boolean yes){
        int temp = 1;
        temp = temp << permission;
        if(yes){
            aclState |= temp;
        }else{
            aclState &= ~temp;
        }
    }

    public static int setPermission(int aclState,int permission,boolean yes){
        int temp = 1;
            temp = temp << permission;
            if(yes){
            aclState |= temp;
        }else{
            aclState &= ~temp;
        }
        return aclState;
    }


    public static int getPermission(int permission){
        int temp = 1;
        temp = temp << permission;
        temp &= aclState;
        if(temp != 0){
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        //System.out.print(getBit(1l,2));

        setPermission(9,true);
        System.out.println(2+aclState);
        System.out.println(getPermission(1));
        int s = setBit(2,9,true);
        System.out.println(s);
        boolean b = getBit(s,1);
        System.out.println(b);
        // System.out.println(getLowRank(65569));
        //System.out.println(getHighRank(65569));
        //System.out.println(mixRank(1,33));
    }

    public static int getLowRank(int value){
        int result = value << 16;
        result >>= 16;
        return result;
    }

    public static int getHighRank(int value){
        return value >> 16;
    }

    public static int mixRank(int high,int low){
        int result = high << 16;
        result += low;
        return result;
    }
}
