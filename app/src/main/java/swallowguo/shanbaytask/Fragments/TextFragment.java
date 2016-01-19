package swallowguo.shanbaytask.Fragments;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
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
import android.text.method.ScrollingMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
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

/**
 * 我想知道的碎片页�?
 * @author wwj_748
 *
 */
public class TextFragment extends Fragment {
    int position;
    private static String originstring,matchstring,wordstring;
    private static SpannableStringBuilder spannablestring0,spannablestring1,spannablestring2,
            spannablestring3,spannablestring4,spannablestring5,spannablestring6;
    private static int spannablecount=7;
    private Switch match_switch,getwords_switch;
    private TextView leveltv;
    private TextView tv;
    private SeekBar slide_bar;
    public static Handler handler;
    MatchThread matchThread;
    private boolean match_on=false,getwords_on=false;
    private int slide_bar_level=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("lesson");
        InputStream inputStream1 = getActivity().getResources().openRawResource(R.raw.nce4_words);
        wordstring = TextFragment.getString(inputStream1);
        TypedArray ar = getActivity().getResources().obtainTypedArray(R.array.text_id);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);
        ar.recycle();
        InputStream inputStream = getActivity().getResources().openRawResource(resIds[position]);
        matchstring = getString(inputStream);
        matchThread =new MatchThread(wordstring,matchstring);
        new Thread(matchThread).start();
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
            }
        };
        //TextView textView = (TextView) getActivity().findViewById(R.id.english_text);
        //textView.setText(matchstring);
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
        tv.setText(matchstring, TextView.BufferType.SPANNABLE);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        slide_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0:
                        leveltv.setText("level0");
                        leveltv.setTextColor(0xffffee00);
                        slide_bar_level = 0;
                        if (match_on) {
                            tv.setText(spannablestring0, TextView.BufferType.SPANNABLE);
                            spannablecount = 0;
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
                tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                if (getwords_on) {
                    getEachWord(tv);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        match_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    match_on = true;
                    switch (slide_bar_level) {
                        case 0:
                            tv.setText(spannablestring0, TextView.BufferType.SPANNABLE);
                            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 0;
                            if (getwords_on) {
                                getEachWord(tv);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            break;
                        case 1:
                            tv.setText(spannablestring1, TextView.BufferType.SPANNABLE);
                            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 1;
                            if (getwords_on) {
                                getEachWord(tv);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            break;
                        case 2:
                            tv.setText(spannablestring2, TextView.BufferType.SPANNABLE);
                            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 2;
                            if (getwords_on) {
                                getEachWord(tv);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            break;
                        case 3:
                            tv.setText(spannablestring3, TextView.BufferType.SPANNABLE);
                            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 3;
                            if (getwords_on) {
                                getEachWord(tv);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            break;
                        case 4:
                            tv.setText(spannablestring4, TextView.BufferType.SPANNABLE);
                            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 4;
                            if (getwords_on) {
                                getEachWord(tv);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            break;
                        case 5:
                            tv.setText(spannablestring5, TextView.BufferType.SPANNABLE);
                            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 5;
                            if (getwords_on) {
                                getEachWord(tv);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            break;
                        case 6:
                            tv.setText(spannablestring6, TextView.BufferType.SPANNABLE);
                            tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                            spannablecount = 6;
                            if (getwords_on) {
                                getEachWord(tv);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            break;
                    }
                } else {
                    match_on = false;
                    tv.setText(matchstring, TextView.BufferType.SPANNABLE);
                    tv.setMovementMethod(ScrollingMovementMethod.getInstance());
                    spannablecount = 7;
                    if (getwords_on) {
                        getEachWord(tv);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                    }
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
                                spannable = new SpannableStringBuilder(matchstring);
                                tv.setText(spannable);
                                break;
                        }
                }
            }
        });


    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) getActivity().findViewById(R.id.english_text);
                Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }*/  return view;
    }
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
    public static void getEachWord(TextView textView)
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
    private static ClickableSpan getClickableSpan()
    {
        return new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                TextView tv = (TextView) widget;
                CharacterStyle span = new ForegroundColorSpan(Color.BLUE);
                SpannableStringBuilder spannable;
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
                    case(7): spannable = new SpannableStringBuilder(matchstring);
                        spannable.setSpan(span, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv.setText(spannable);break;
                }
                getEachWord(tv);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
            @Override
            public void updateDrawState(TextPaint ds)
            {
               // ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }
    public static Integer[] getIndices(String s, char c)
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
