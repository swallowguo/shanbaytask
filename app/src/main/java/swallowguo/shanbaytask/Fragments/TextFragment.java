package swallowguo.shanbaytask.Fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import swallowguo.shanbaytask.R;
import swallowguo.shanbaytask.TextProcess.MatchThread;
import swallowguo.shanbaytask.TextProcess.SpannableStringValue;
import swallowguo.shanbaytask.TextProcess.TextJustify;
/**
 * 英文文本Fragment
 */
public class TextFragment extends Fragment {
    private boolean match_on=false,getwords_on=false;
    private int position,Width,spannablecount=7,slide_bar_level=0;
    private String showstring,matchstring,wordstring;
    private SpannableStringBuilder spannablestring0,spannablestring1,spannablestring2,
                                          spannablestring3,spannablestring4,spannablestring5,spannablestring6;
    private Switch match_switch,getwords_switch;
    private TextView leveltv,tv;;
    private SeekBar slide_bar;
    public static Handler handler;
    MatchThread matchThread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("lesson");//获取需要显示的文本索引号
        InputStream inputStream1 = getActivity().getResources().openRawResource(R.raw.nce4_words);
        wordstring = getString(inputStream1);//获取用于匹配的单词文本串
        //根据索引号获得相应的英文文章资源
        TypedArray ar = getActivity().getResources().obtainTypedArray(R.array.text_id);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);
        ar.recycle();
        InputStream inputStream = getActivity().getResources().openRawResource(resIds[position]);
        matchstring = getString(inputStream);
        //获取屏幕宽度（用于两边对齐）
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Width = dm.widthPixels;
        //消息处理，得到子线程计算的各等级spannablestring字符串
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    SpannableStringValue spanablevalue = (SpannableStringValue)msg.getData().getSerializable("spvalue");
                    spannablestring0=spanablevalue.getstring0();spannablestring1=spanablevalue.getstring1();
                    spannablestring2=spanablevalue.getstring2();spannablestring3=spanablevalue.getstring3();
                    spannablestring4=spanablevalue.getstring4();spannablestring5=spanablevalue.getstring5();
                    spannablestring6=spanablevalue.getstring6();
                    Toast.makeText(getActivity(), "spannablestring was sent already" , Toast.LENGTH_LONG).show();
                }
                else return;
            }
        };
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_tab2_fragment, container, false);
        slide_bar= (SeekBar)view.findViewById(R.id.slide_bar);
        leveltv= (TextView)view.findViewById(R.id.slide_leveltext);
        match_switch=(Switch)view.findViewById(R.id.match_switch);
        getwords_switch=(Switch)view.findViewById(R.id.getwords_switch);
        tv= (TextView)view.findViewById(R.id.english_text);
        TextPaint paint = tv.getPaint();//获得textview的画笔
        showstring= TextJustify.justify(paint, matchstring, Width);//根据textview宽度对字符串两端做对齐处理
        //开启子线程，输入对齐处理过的原文本，匹配获得各等级下的高亮spannablestring串
        matchThread =new MatchThread(wordstring,showstring);
        new Thread(matchThread).start();
        //初始化输出文本（无高亮）
        tv.setText(showstring, TextView.BufferType.SPANNABLE);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        //设置slide-bar拖动事件
        slide_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0:
                        leveltv.setText("level0");
                        leveltv.setTextColor(0xffffee00);//显示等级的文本颜色随着拖动而加深
                        slide_bar_level = 0;
                        if (match_on) {//如果生词开关打开，直接随拖动改变显示内容
                            tv.setText(spannablestring0, TextView.BufferType.SPANNABLE);
                            spannablecount = 0;//改变spannablestring索引序号
                        }
                        break;
                    case 1:
                        leveltv.setText("level1");
                        leveltv.setTextColor(0xffffcc00);
                        slide_bar_level = 1;
                        if (match_on) {
                            tv.setText(spannablestring1, TextView.BufferType.SPANNABLE);
                            spannablecount = 1;
                        }
                        break;
                    case 2:
                        leveltv.setText("level2");
                        leveltv.setTextColor(0xffffaa00);
                        slide_bar_level = 2;
                        if (match_on) {
                            tv.setText(spannablestring2, TextView.BufferType.SPANNABLE);
                            spannablecount = 2;
                        }
                        break;
                    case 3:
                        leveltv.setText("level3");
                        leveltv.setTextColor(0xffff7700);
                        slide_bar_level = 3;
                        if (match_on) {
                            tv.setText(spannablestring3, TextView.BufferType.SPANNABLE);
                            spannablecount = 3;
                        }
                        break;
                    case 4:
                        leveltv.setText("level4");
                        leveltv.setTextColor(0xffff4400);
                        slide_bar_level = 4;
                        if (match_on) {
                            tv.setText(spannablestring4, TextView.BufferType.SPANNABLE);
                            spannablecount = 4;
                        }
                        break;
                    case 5:
                        leveltv.setText("level5");
                        leveltv.setTextColor(0xffff2200);
                        slide_bar_level = 5;
                        if (match_on) {
                            tv.setText(spannablestring5, TextView.BufferType.SPANNABLE);
                            spannablecount = 5;
                        }
                        break;
                    case 6:
                        leveltv.setText("level6");
                        leveltv.setTextColor(0xffff0000);
                        slide_bar_level = 6;
                        if (match_on) {
                            tv.setText(spannablestring6, TextView.BufferType.SPANNABLE);
                            spannablecount = 6;
                        }
                        break;
                }
                //tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                //若取词开关已打开，添加单词点击高亮效果
                if (getwords_on) {
                    getEachWord(tv);
                }
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //设置过滤生词switch点击事件，开启/关闭显示过滤生词功能
        match_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    match_on = true;
                    switch (slide_bar_level) {
                        case 0:
                            tv.setText(spannablestring0, TextView.BufferType.SPANNABLE);
                            //tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 0;
                            if (getwords_on) {//若取词开关已打开，添加单词点击高亮效果
                                getEachWord(tv);
                            }
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                        case 1:
                            tv.setText(spannablestring1, TextView.BufferType.SPANNABLE);
                            spannablecount = 1;
                            if (getwords_on) {
                                getEachWord(tv);
                            }
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                        case 2:
                            tv.setText(spannablestring2, TextView.BufferType.SPANNABLE);
                            spannablecount = 2;
                            if (getwords_on) {
                                getEachWord(tv);
                            }
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                        case 3:
                            tv.setText(spannablestring3, TextView.BufferType.SPANNABLE);
                            spannablecount = 3;
                            if (getwords_on) {
                                getEachWord(tv);
                            }
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                        case 4:
                            tv.setText(spannablestring4, TextView.BufferType.SPANNABLE);
                            spannablecount = 4;
                            if (getwords_on) {
                                getEachWord(tv);
                            }
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                        case 5:
                            tv.setText(spannablestring5, TextView.BufferType.SPANNABLE);
                            spannablecount = 5;
                            if (getwords_on) {
                                getEachWord(tv);
                            }
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                        case 6:
                            tv.setText(spannablestring6, TextView.BufferType.SPANNABLE);
                            spannablecount = 6;
                            if (getwords_on) {
                                getEachWord(tv);
                            }
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            break;
                    }
                } else {
                    match_on = false;
                    tv.setText(showstring, TextView.BufferType.SPANNABLE);
                    spannablecount = 7;
                    if (getwords_on) {
                        getEachWord(tv);
                    }
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
    });
        getwords_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    getwords_on = true;
                    getEachWord(tv);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                } else {
                    getwords_on = false;
                    SpannableStringBuilder spannable;
                    switch (spannablecount) {
                        case (0):
                            spannable = new SpannableStringBuilder(spannablestring0);
                            tv.setText(spannable);
                            break;
                        case (1):
                            spannable = new SpannableStringBuilder(spannablestring1);
                            tv.setText(spannable);
                            break;
                        case (2):
                            spannable = new SpannableStringBuilder(spannablestring2);
                            tv.setText(spannable);
                            break;
                        case (3):
                            spannable = new SpannableStringBuilder(spannablestring3);
                            tv.setText(spannable);
                            break;
                        case (4):
                            spannable = new SpannableStringBuilder(spannablestring4);
                            tv.setText(spannable);
                            break;
                        case (5):
                            spannable = new SpannableStringBuilder(spannablestring5);
                            tv.setText(spannable);
                            break;
                        case (6):
                            spannable = new SpannableStringBuilder(spannablestring6);
                            tv.setText(spannable);
                            break;
                        case (7):
                            spannable = new SpannableStringBuilder(showstring);
                            tv.setText(spannable);
                            break;
                    }
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
        });
     return view;
    }
    //获取inputStream，返回字符串
    public static String getString(InputStream inputStream)
    {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    //给每个单词添加ClickSpan
    public void getEachWord(TextView textView)
    {
        Spannable spans = (Spannable)textView.getText();
        Integer[] indices = getIndices("    "+textView.getText().toString().trim() + " ", ' ');
        int start = 0;
        int end = 0;
        for (int i = 0; i <= indices.length; i++)
        {
            ClickableSpan clickSpan = getClickableSpan();
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //改变选中文本的高亮颜色
        textView.setHighlightColor(Color.BLUE);
    }
    //添加ClickSpan的点击高亮事件
    private  ClickableSpan getClickableSpan()
    {
        return new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                TextView tv = (TextView) widget;
                CharacterStyle span = new ForegroundColorSpan(Color.BLUE);//点击单词蓝色高亮
                SpannableStringBuilder spannable;
                //每次点击前刷新SpannableString至初始状态，以确保只有被点击的单词高亮
                switch(spannablecount){
                    case(0): spannable = new SpannableStringBuilder(spannablestring0);
                            spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tv.setText(spannable);break;
                    case(1): spannable = new SpannableStringBuilder(spannablestring1);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                    case(2): spannable = new SpannableStringBuilder(spannablestring2);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                    case(3): spannable = new SpannableStringBuilder(spannablestring3);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                    case(4): spannable = new SpannableStringBuilder(spannablestring4);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                    case(5): spannable = new SpannableStringBuilder(spannablestring5);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                    case(6): spannable = new SpannableStringBuilder(spannablestring6);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                    case(7): spannable = new SpannableStringBuilder(showstring);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                }
                getEachWord(tv);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
            @Override
            public void updateDrawState(TextPaint ds)
            {
                ds.setUnderlineText(false);
            }
        };
    }
    //返回字符串中每个字符c的位置
    private  Integer[] getIndices(String s, char c)
    {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1)
        {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return (Integer[]) indices.toArray(new Integer[0]);
    }
}
