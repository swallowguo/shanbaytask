package swallowguo.shanbaytask.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import swallowguo.shanbaytask.R;
/**
 * 听力Fragment
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
        //听力问题数组
        question = getActivity().getResources().getStringArray(R.array.question);
        ImageButton iv=(ImageButton)view.findViewById(R.id.listen_image);
        TextView tv= (TextView)view.findViewById(R.id.question);
        tv.setText(question[position]);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "暂时还没有听力音频", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

