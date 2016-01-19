package swallowguo.shanbaytask.Fragments;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import swallowguo.shanbaytask.Fragments.TextFragment;
import swallowguo.shanbaytask.R;

/**
 * 知道碎片界面
 * @author wwj_748
 *
 */
public class TranslationFragment extends Fragment {
    private int position,Width;
    private String transstring,showstring;
    public static List<Integer> addCharPosition = new ArrayList<Integer>();  //增加空格的位置
    public static final char SPACE = ' '; //空格;
    public static List<Character> punctuation = new ArrayList<Character>(); //标点符号
    //标点符号用于在textview右侧多出空间时，将空间加到标点符号的后面,以便于右端对齐
    static {
        punctuation.clear();punctuation.add(',');punctuation.add('.');punctuation.add('?');punctuation.add('!');punctuation.add(';');
        punctuation.add('，');punctuation.add('。');punctuation.add('？');punctuation.add('！');punctuation.add('；');punctuation.add('）');
        punctuation.add('】');punctuation.add(')');punctuation.add(']');punctuation.add('}');
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("lesson");
        Width = getArguments().getInt("Width");
        TypedArray ar = getActivity().getResources().obtainTypedArray(R.array.trans_id);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);
        ar.recycle();
        InputStream inputStream = getActivity().getResources().openRawResource(resIds[position]);
        transstring = TextFragment.getString(inputStream);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab4_fragment, container, false);
        TextView tv= (TextView)view.findViewById(R.id.translation);
        Paint paint= tv.getPaint();
        showstring=justifyText(paint,transstring,Width);
        tv.setText(transstring);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        return view;
    }

    /**
     * 处理多行文本
     * @param paint 画笔
     * @param text  文本
     * @param Width 最大可用宽度
     * @return 处理后的文本
     */
    private static String justifyText(Paint paint, String text, int Width) {
        if (text == null || text.length() == 0||Width == 0) {
            return "";
        }
        String[] lines = text.split("\\n");
        StringBuilder newText = new StringBuilder();
        for (String line : lines) {
            newText.append('\n');
            newText.append(processLine(paint, line, Width, newText.length() - 1));
        }
        if (newText.length() > 0) {
            newText.deleteCharAt(0);
        }
        return newText.toString();
    }
    /**
     * 处理单行文本
     *
     * @param paint                     画笔
     * @param text                      文本
     * @param width                     最大可用宽度
     * @param addCharacterStartPosition 添加文本的起始位置
     * @return 处理后的文本
     */
    private static String processLine(Paint paint, String text, int width, int addCharacterStartPosition) {
        if (text == null || text.length() == 0) {
            return "";
        }
        StringBuilder old = new StringBuilder(text);
        int startPosition = 0; // 起始位置

        float chineseWidth = paint.measureText("中");
        float spaceWidth = paint.measureText(SPACE + "");

        //最大可容纳的汉字，每一次从此位置向后推进计算
        int maxChineseCount = (int) (width / chineseWidth);

        //减少一个汉字宽度，保证每一行前后都有一个空格
        maxChineseCount--;

        //如果不能容纳汉字，直接返回空串
        if (maxChineseCount <= 0) {
            return "";
        }

        for (int i = maxChineseCount; i < old.length(); i++) {
            if (paint.measureText(old.substring(startPosition, i + 1)) > (width - spaceWidth)) {

                //右侧多余空隙宽度
                float gap = (width - spaceWidth - paint.measureText(old.substring(startPosition,
                        i)));

                List<Integer> positions = new ArrayList<Integer>();
                for (int j = startPosition; j < i; j++) {
                    char ch = old.charAt(j);
                    if (punctuation.contains(ch)) {
                        positions.add(j + 1);
                    }
                }

                //空隙最多可以使用几个空格替换
                int number = (int) (gap / spaceWidth);

                //多增加的空格数量
                int use = 0;

                if (positions.size() > 0) {
                    for (int k = 0; k < positions.size() && number > 0; k++) {
                        int times = number / (positions.size() - k);
                        int position = positions.get(k / positions.size());
                        for (int m = 0; m < times; m++) {
                            old.insert(position + m, SPACE);
                            addCharPosition.add(position + m + addCharacterStartPosition);
                            use++;
                            number--;
                        }
                    }
                }

                //指针移动，将段尾添加空格进行分行处理
                i = i + use;
                old.insert(i, SPACE);
                addCharPosition.add(i + addCharacterStartPosition);

                startPosition = i + 1;
                i = i + maxChineseCount;
            }
        }
        return old.toString();
    }
}

