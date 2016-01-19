package swallowguo.shanbaytask.TextProcess;

import android.text.SpannableStringBuilder;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class SpannableStringValue implements Serializable{
    private SpannableStringBuilder spannablestring0,spannablestring1,spannablestring2,
                                   spannablestring3,spannablestring4,spannablestring5,spannablestring6;
    public SpannableStringValue(SpannableStringBuilder[] spannablearray)
    {
        this.spannablestring0=spannablearray[0];this.spannablestring1=spannablearray[1];
        this.spannablestring2=spannablearray[2];this.spannablestring3=spannablearray[3];
        this.spannablestring4=spannablearray[4];this.spannablestring5=spannablearray[5];
        this.spannablestring6=spannablearray[6];
    }
    public SpannableStringBuilder getstring0(){
        return spannablestring0;
    }
    public SpannableStringBuilder getstring1(){
        return spannablestring1;
    }
    public SpannableStringBuilder getstring2(){
        return spannablestring2;
    }
    public SpannableStringBuilder getstring3() { return spannablestring3; }
    public SpannableStringBuilder getstring4(){
        return spannablestring4;
    }
    public SpannableStringBuilder getstring5(){
        return spannablestring5;
    }
    public SpannableStringBuilder getstring6(){
        return spannablestring6;
    }
}
