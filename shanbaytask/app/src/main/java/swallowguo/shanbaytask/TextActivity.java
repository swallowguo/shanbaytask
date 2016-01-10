package swallowguo.shanbaytask;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 主Activity
 *
 * @author wwj_748
 *
 */
public class TextActivity extends FragmentActivity  {

    // 三个tab布局
    private RelativeLayout knowLayout, iWantKnowLayout, meLayout,rl_question,rl_text,rl_words;

    // 底部标签切换的Fragment
    private Fragment listenFragment, textFragment, wordsFragment,
            currentFragment;
    // 底部标签图片
    private ImageView knowImg, iWantKnowImg, meImg;
    // 底部标签的文本
    private TextView knowTv, iWantKnowTv, meTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textactivity);
        Intent intent=getIntent();
        int position=intent.getIntExtra("position", -1);
        Bundle arguments = new Bundle();
        arguments.putInt("lesson", position);
        initUI();
        initTab(arguments);
        rl_question.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTab1Layout();
            }
        });
        rl_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTab2Layout();
            }
        });
        rl_words.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTab3Layout();
            }
        });

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        rl_question=(RelativeLayout) findViewById(R.id.rl_question);
        rl_text=(RelativeLayout) findViewById(R.id.rl_text);
        rl_words=(RelativeLayout) findViewById(R.id.rl_words);
        knowImg = (ImageView) findViewById(R.id.iv_know);
        iWantKnowImg = (ImageView) findViewById(R.id.iv_i_want_know);
        meImg = (ImageView) findViewById(R.id.iv_me);
        knowTv = (TextView) findViewById(R.id.tv_know);
        iWantKnowTv = (TextView) findViewById(R.id.tv_i_want_know);
        meTv = (TextView) findViewById(R.id.tv_me);

    }

    /**
     * 初始化底部标签
     */
    private void initTab(Bundle arguments)
    {
        listenFragment = new ListenFragment();
        textFragment = new TextFragment();
        wordsFragment = new WordsFragment();
        listenFragment.setArguments(arguments);
        textFragment.setArguments(arguments);
        wordsFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, listenFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, textFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, wordsFragment).commit();

        currentFragment = listenFragment;
        ShowFragment(getSupportFragmentManager().beginTransaction(), listenFragment);
            // 设置图片文本的变化
            knowImg.setImageResource(R.drawable.btn_know_pre);
            knowTv.setTextColor(getResources()
                    .getColor(R.color.bottomtab_press));
            iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
            iWantKnowTv.setTextColor(getResources().getColor(
                    R.color.bottomtab_normal));
            meImg.setImageResource(R.drawable.btn_my_nor);
            meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }



    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        ShowFragment(getSupportFragmentManager().beginTransaction(), listenFragment);
        // 设置底部tab变化
        knowImg.setImageResource(R.drawable.btn_know_pre);
        knowTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
        iWantKnowTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.btn_my_nor);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        ShowFragment(getSupportFragmentManager().beginTransaction(), textFragment);
        knowImg.setImageResource(R.drawable.btn_know_nor);
        knowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        iWantKnowImg.setImageResource(R.drawable.btn_wantknow_pre);
        iWantKnowTv.setTextColor(getResources().getColor(
                R.color.bottomtab_press));
        meImg.setImageResource(R.drawable.btn_my_nor);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        ShowFragment(getSupportFragmentManager().beginTransaction(), wordsFragment);
        knowImg.setImageResource(R.drawable.btn_know_nor);
        knowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
        iWantKnowTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.btn_my_pre);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void ShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;
         else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }

}

