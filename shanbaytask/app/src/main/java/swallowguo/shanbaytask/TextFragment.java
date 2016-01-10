package swallowguo.shanbaytask;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 我想知道的碎片页面
 * @author wwj_748
 *
 */
public class TextFragment extends Fragment {
    int position;
    String[] question;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("lesson");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_tab2_fragment, container, false);
        TypedArray ar = getActivity().getResources().obtainTypedArray(R.array.text_id);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);
        ar.recycle();
        InputStream inputStream = getActivity().getResources().openRawResource(resIds[position]);
        String string = getString(inputStream);
        TextView tv= (TextView)view.findViewById(R.id.english_text);

        //Display display = getActivity().getWindowManager().getDefaultDisplay();
        //DisplayMetrics dm = new DisplayMetrics();
        //display.getMetrics(dm);
        //int width = dm.widthPixels;
        //根据屏幕调整文字大小

        //tv.setLineSpacing(0f, 1.5f);
        //tv.setTextSize(8 * (float) width / 320f);
       //设置TextView
        TextJustification.justify(string,tv, tv.getWidth());
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
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
}
