package swallowguo.shanbaytask.Fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import swallowguo.shanbaytask.R;
/**
 * 词汇列表Fragment
 */
public class WordsFragment extends Fragment {
    int position;
    String[] words,pos,wordtrans;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("lesson");
        //根据索引号获取列表项资源
        TypedArray words_ar = getActivity().getResources().obtainTypedArray(R.array.words_id);
        TypedArray pos_ar = getActivity().getResources().obtainTypedArray(R.array.pos_id);
        TypedArray wordtrans_ar = getActivity().getResources().obtainTypedArray(R.array.wordtrans_id);
        int len = words_ar.length();
        int[] words_resIds = new int[len];
        int[] pos_resIds = new int[len];
        int[] wordtrans_resIds = new int[len];
        for (int i = 0; i < len; i++){
            words_resIds[i] = words_ar.getResourceId(i, 0);
            pos_resIds[i] = pos_ar.getResourceId(i, 0);
            wordtrans_resIds[i] = wordtrans_ar.getResourceId(i, 0);}
        words_ar.recycle();
        pos_ar.recycle();
        wordtrans_ar.recycle();
        words = getActivity().getResources().getStringArray(words_resIds[position]);
        pos = getActivity().getResources().getStringArray(pos_resIds[position]);
        wordtrans = getActivity().getResources().getStringArray(wordtrans_resIds[position]);
        // 创建一个List集合，List集合的元素是Map


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab3_fragment, container, false);
       //SimpleAdapter加载列表项
        List<Map<String, Object>> listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i <words.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("words", words[i]);
            listItem.put("pos",pos[i]);
            listItem.put("wordtrans", wordtrans[i]);
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.simple_item_words,
                new String[] { "pos" ,"words", "wordtrans"},
                new int[] { R.id.pos, R.id.words , R.id.wordtrans });
        ListView list = (ListView) view.findViewById(R.id.wordslist);
        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);
        return view;
    }

}

