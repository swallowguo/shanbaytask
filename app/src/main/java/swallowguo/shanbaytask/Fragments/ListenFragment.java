package swallowguo.shanbaytask.Fragments;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swallowguo.shanbaytask.R;

/**
 * 知道碎片界面
 * @author wwj_748
 *
 */
public class ListenFragment extends Fragment {
    int position;
    String[] question;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("lesson");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab1_fragment, container, false);
       question = getActivity().getResources().getStringArray(R.array.question);

        TextView tv= (TextView)view.findViewById(R.id.question);
        tv.setText(question[position]);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        return view;
    }

}

