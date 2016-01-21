package swallowguo.shanbaytask.TextProcess;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swallowguo.shanbaytask.Activity.MainActivity;
import swallowguo.shanbaytask.Fragments.TextFragment;

/**
 * 匹配高亮生词的子线程.
 */
public class MatchThread implements Runnable {
    String wordstring;
    String matchstring;
    private String level0compare= "";
    private String level1compare= "";
    private String level2compare= "";
    private String level3compare= "";
    private String level4compare= "";
    private String level5compare= "";
    private String level6compare= "";
    public MatchThread(String wordstring,String matchstring) {
        this.wordstring=wordstring;
        this.matchstring=matchstring;
    }
    public void run()
    {
        //匹配生词文本，获得每个level的匹配表达式
        Pattern p = Pattern.compile("([a-zA-Z- &]+)\\s(\\d)");
        Matcher m = p.matcher(wordstring);
        while(m.find())
        {
            switch(m.group(2))
            {
                case"0": level0compare=level0compare+"( "+m.group(1)+")|";break;
                case"1": level1compare=level1compare+"( "+m.group(1)+")|";break;
                case"2": level2compare=level2compare+"( "+m.group(1)+")|";break;
                case"3": level3compare=level3compare+"( "+m.group(1)+")|";break;
                case"4": level4compare=level4compare+"( "+m.group(1)+")|";break;
                case"5": level5compare=level5compare+"( "+m.group(1)+")|";break;
                case"6": level6compare=level6compare+"( "+m.group(1)+")|";break;
            }
        }
        String[] comparewords=new String[]{level0compare,level1compare,level2compare, level3compare,level4compare ,level5compare,level6compare};
       //用匹配表达式数组匹配文本，并高亮显示匹配到的生词
        SpannableStringBuilder[] spannablearray=highlight(matchstring, comparewords);
        SpannableStringValue SpanValue=new SpannableStringValue(spannablearray);
        //将匹配到的各level对应的spannablestring结果通过message传递给UI线程
        Message msg=new Message();
        msg.what=0x123;
        Bundle bundle=new Bundle();
        bundle.putSerializable("spvalue", SpanValue);
        msg.setData(bundle);
       if(MainActivity.flag==2){
        TextFragment.handler.sendMessage(msg);
       }
    }
    //匹配+高亮方法，匹配小于等于各level生词的spannablestring数组并返回
    public  SpannableStringBuilder[] highlight(String text, String[] target) {
        SpannableStringBuilder[] spannablearray = new SpannableStringBuilder[7];
        for (int i = 0; i < 6; i++) {
            if(i==0){//level0匹配结果
                SpannableStringBuilder spannable = new SpannableStringBuilder(text);
                CharacterStyle span = null;
                Pattern p = Pattern.compile(target[0]);
                Matcher m = p.matcher(text);
                while (m.find()) {
                    span=new ForegroundColorSpan(Color.RED);// 需要重复！
                    spannable.setSpan(span, m.start(), m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                spannablearray[0] = spannable;
            }
            else{//利用上一个level匹配结果继续匹配下一个level生词
                SpannableStringBuilder spannable = new SpannableStringBuilder(spannablearray[i-1]);
                CharacterStyle span = null;
                Pattern p = Pattern.compile(target[i]);
                Matcher m = p.matcher(text);
                while (m.find()) {
                    span=new ForegroundColorSpan(Color.RED); // 需要重复！
                    spannable.setSpan(span, m.start(), m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                spannablearray[i] = spannable;
            }
        }
        spannablearray[6] =spannablearray[5];//level6没有生词，省略匹配时间
        return spannablearray;
    }
}
