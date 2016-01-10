package swallowguo.shanbaytask;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * “我的”碎片页面
 * @author wwj_748
 *
 */
public class WordsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab3_fragment, container,
                false);
        return view;
    }

}

