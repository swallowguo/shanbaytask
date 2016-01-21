package swallowguo.shanbaytask.Activity;

/**
 * Created by Administrator on 2016/1/9 0009.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import static android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import swallowguo.shanbaytask.R;


public class MainActivity extends Activity
{
    public static int flag;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        flag=1;
        //加载列表项，用SimpleAdapter实现列表视图
        String[] Lesson_Id = this.getResources().getStringArray(R.array.lesson_id);
        String[] English_Title =this.getResources().getStringArray(R.array.english_title);
        String[] Chinese_Title =this.getResources().getStringArray(R.array.chinese_title);
        // 创建一个List集合，List集合的元素是Map
        List<Map<String, Object>> listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i <Lesson_Id.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("lesson", Lesson_Id[i]);
            listItem.put("english_title",English_Title[i]);
            listItem.put("chinese_title", Chinese_Title[i]);
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.simple_item_main,
                new String[] { "lesson", "english_title" ,"chinese_title"},
                new int[] { R.id.lesson, R.id.english_title , R.id.chinese_title });
        ListView list = (ListView) findViewById(R.id.mylist);
        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);


        // 为ListView的列表项的单击事件绑定事件监听器，传入文章索引号
        list.setOnItemClickListener(new OnItemClickListener()
        {
            // 第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Intent intent=new Intent(MainActivity.this,TextActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        flag=1;//MainActivity回到前台时改变flag，MatchThread将不向TextActivity发送消息，否则会导致程序崩溃
    }
}