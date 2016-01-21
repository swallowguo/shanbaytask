package swallowguo.shanbaytask.Activity;

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
import swallowguo.shanbaytask.Fragments.ListenFragment;
import swallowguo.shanbaytask.Fragments.TextFragment;
import swallowguo.shanbaytask.Fragments.TranslationFragment;
import swallowguo.shanbaytask.Fragments.WordsFragment;
import swallowguo.shanbaytask.R;
/**
 * 含有3个Fragment的Activity
 */
public class TextActivity extends FragmentActivity  {
    // 三个tab布局
    private RelativeLayout rl_listen,rl_text,rl_words,rl_trans;
    // 底部标签切换的Fragment
    private Fragment listenFragment, textFragment, wordsFragment,transFragment, currentFragment;
    // 底部标签图片
    private ImageView listenImg, textImg, wordsImg,transImg;
    // 底部标签的文本
    private TextView listenTv, textTv, wordsTv,transTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textactivity);
        Intent intent=getIntent();
        int position=intent.getIntExtra("position", -1);
        initUI();
        Bundle arguments = new Bundle();
        arguments.putInt("lesson", position);
        initTab(arguments);
        rl_listen.setOnClickListener(new OnClickListener() {
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
        rl_trans.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTab4Layout();
            }
        });
    }
    /**
     * 初始化UI
     */
    private void initUI() {
        rl_listen=(RelativeLayout) findViewById(R.id.rl_listen);
        rl_text=(RelativeLayout) findViewById(R.id.rl_text);
        rl_words=(RelativeLayout) findViewById(R.id.rl_vocabulary);
        rl_trans=(RelativeLayout) findViewById(R.id.rl_translation);
        listenImg = (ImageView) findViewById(R.id.iv_listen);
        textImg = (ImageView) findViewById(R.id.iv_text);
        wordsImg = (ImageView) findViewById(R.id.iv_vocabulary);
        transImg = (ImageView) findViewById(R.id.iv_translation);
        listenTv = (TextView) findViewById(R.id.tv_listen);
        textTv = (TextView) findViewById(R.id.tv_text);
        wordsTv = (TextView) findViewById(R.id.tv_vocabulary);
        transTv = (TextView) findViewById(R.id.tv_translation);
    }
    /**
     * 初始化底部标签
     */
    private void initTab(Bundle arguments)
    {
       // commit Fragmrnt
        listenFragment = new ListenFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, listenFragment).commit();
        textFragment = new TextFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, textFragment).commit();
        wordsFragment = new WordsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, wordsFragment).commit();
        transFragment=new TranslationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_layout, transFragment).commit();
        listenFragment.setArguments(arguments);
        textFragment.setArguments(arguments);
        wordsFragment.setArguments(arguments);
        transFragment.setArguments(arguments);
        currentFragment = listenFragment;
        clickTab1Layout();
    }
    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        ShowFragment(getSupportFragmentManager().beginTransaction(), listenFragment);
        // 设置底部tab变化
        listenImg.setImageResource(R.drawable.btn_listen_pre);
        listenTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        textImg.setImageResource(R.drawable.btn_text_nor);
        textTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        wordsImg.setImageResource(R.drawable.btn_vocabulary_nor);
        wordsTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        transImg.setImageResource(R.drawable.btn_text_nor);
        transTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }
    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        ShowFragment(getSupportFragmentManager().beginTransaction(), textFragment);
        listenImg.setImageResource(R.drawable.btn_listen_nor);
        listenTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        textImg.setImageResource(R.drawable.btn_text_pre);
        textTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        wordsImg.setImageResource(R.drawable.btn_vocabulary_nor);
        wordsTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        transImg.setImageResource(R.drawable.btn_text_nor);
        transTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        ShowFragment(getSupportFragmentManager().beginTransaction(), wordsFragment);
        listenImg.setImageResource(R.drawable.btn_listen_nor);
        listenTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        textImg.setImageResource(R.drawable.btn_text_nor);
        textTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        wordsImg.setImageResource(R.drawable.btn_vocabulary_pre);
        wordsTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        transImg.setImageResource(R.drawable.btn_text_nor);
        transTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }
    /**
     * 点击第四个tab
     */
    private void clickTab4Layout() {
        ShowFragment(getSupportFragmentManager().beginTransaction(), transFragment);
        listenImg.setImageResource(R.drawable.btn_listen_nor);
        listenTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        textImg.setImageResource(R.drawable.btn_text_nor);
        textTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        wordsImg.setImageResource(R.drawable.btn_vocabulary_nor);
        wordsTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        transImg.setImageResource(R.drawable.btn_text_pre);
        transTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
    }

    /**
     * 添加或者显示Fragment
     *
     * @param transaction
     * @param fragment
     */
    private void ShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;
         else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
}

