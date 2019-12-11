package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.adapter.SectionListAdapter;
import com.wallet.hz.model.CountryBean;
import com.wallet.hz.model.CountryMenu;
import com.wallet.hz.utils.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.utils.KeyboardTool;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.ClearEditText;
import share.exchange.framework.widget.LinearDividerItemDecoration;
import share.exchange.framework.widget.QuickIndexBar;

/**
 * Created by MMM on 2018/01/12.
 */
public class CountryActivity extends BaseAppActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.index_bar)
    QuickIndexBar mIndexbar;
    @BindView(R.id.txt_letter)
    TextView mTxtLetter;
    @BindView(R.id.et_searchcode)
    ClearEditText mEtSearchCode;

    private SectionListAdapter mSectionListAdapter;
    private StaggeredGridLayoutManager mStaggManager;
    private List<CountryMenu> mCountryMenus = new ArrayList<>();
    private List<CountryMenu> mCountryTemps = new ArrayList<>();

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CountryActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_country;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 初始化标题
        showSuccessStateLayout();
        showTitleBar(false);
        initIndexBar();
        initCountryMenu();
        initRecyclerView();
    }

    @Override
    protected void initEvent() {
        findViewById(R.id.img_tool_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 搜索输入框输入监听
        mEtSearchCode.setTextChangedListener(new ClearEditText.OnTextChangeListener() {
            @Override
            public void onChanged(String text) {
                if (StringUtil.isEmpty(text)) {
                    mCountryTemps.clear();
                    mSectionListAdapter.setData(mCountryMenus);
                } else {
                    searchCountry(text);
                }
            }
        });
        mEtSearchCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    KeyboardTool keyboardTool = KeyboardTool.getInstance(mAppContext);
                    keyboardTool.hideKeyboard(mEtSearchCode);
                }
                return false;
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    private void searchCountry(String text) {
        // 搜索国家代码算法
        mCountryTemps.clear();
        List<CountryBean> bean = null;
        for (int i = 0; i < mCountryMenus.size(); i++) {
            bean = new ArrayList<>();
            CountryMenu countryMenu = mCountryMenus.get(i);
            List<CountryBean> countryBeen = countryMenu.getCountryBeen();
            for (int j = 0; j < countryBeen.size(); j++) {
                CountryBean countryBean = countryBeen.get(j);
                if (countryBean.name.toUpperCase().contains(text.toUpperCase())
                        || countryBean.code.contains(text)) {
                    bean.add(countryBean);
                    String firstLetter = countryBean.pinyin.charAt(0) + "";
                    mCountryTemps.add(new CountryMenu(firstLetter, bean));
                }
            }
        }
        // 集合去重
        for (int i = 0; i < mCountryTemps.size(); i++) {
            for (int j = 0; j < mCountryTemps.size() - 1; j++) {
                CountryMenu countryMenu = mCountryTemps.get(j);
                CountryMenu countryMenu2 = mCountryTemps.get(j + 1);
                String letter = countryMenu.getCountryBeen().get(0).pinyin.charAt(0) + "";
                String letter2 = countryMenu2.getCountryBeen().get(0).pinyin.charAt(0) + "";
                if (letter.equals(letter2)) {
                    mCountryTemps.remove(j + 1);
                }
            }
        }
        mSectionListAdapter.setData(mCountryTemps);
    }

    private void initCountryMenu() {

        List<CountryBean> countryBeen = CountryBean.getAll(mAppContext);
        for (int i = 0; i < countryBeen.size(); i++) {
            String name = countryBeen.get(i).name;
            String pinyin = PinYinUtil.hanZiToPinyin(name);
            countryBeen.get(i).pinyin = pinyin;
        }
        Collections.sort(countryBeen);

        boolean isGo = false;
        List<CountryBean> bean = null;

        for (int i = 0; i < countryBeen.size(); i++) {

            if (!isGo) {
                bean = new ArrayList<>();
                bean.add(countryBeen.get(i));
            }
            if (i + 1 < countryBeen.size()) {
                String firstLetter = countryBeen.get(i).pinyin.charAt(0) + "";
                String nextLetter = countryBeen.get(i + 1).pinyin.charAt(0) + "";
                if (StringUtil.isEqual(firstLetter, nextLetter)) {
                    bean.add(countryBeen.get(i + 1));
                    isGo = true;
                } else {
                    isGo = false;
                }
            } else {
                isGo = false;
            }
            if (!isGo) {
                String firstLetter = countryBeen.get(i).pinyin.charAt(0) + "";
                mCountryMenus.add(new CountryMenu(firstLetter, bean));
            }
        }
    }

    private void initRecyclerView() {
        // 1、创建管理器和适配器
        mStaggManager = new StaggeredGridLayoutManager(
                1, StaggeredGridLayoutManager.VERTICAL);
        mSectionListAdapter = new SectionListAdapter(mAppContext, mCountryMenus);
        // 2、设置管理器和适配器
        mRecyclerView.setLayoutManager(mStaggManager);
        mRecyclerView.setAdapter(mSectionListAdapter);

        // 3、设置分割线
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(mAppContext));

        // 4、设置监听事件
        mSectionListAdapter.setOnItemClickListener(new SectionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int section, int position) {
                String name;
                String code;
                if (StringUtil.isEmpty(mEtSearchCode.getValue())) {
                    name = mCountryMenus.get(section).getCountryBeen().get(position).name;
                    code = mCountryMenus.get(section).getCountryBeen().get(position).code;
                } else {
                    name = mCountryTemps.get(section).getCountryBeen().get(position).name;
                    code = mCountryTemps.get(section).getCountryBeen().get(position).code;
                }
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("code", code);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initIndexBar() {
        mIndexbar.setOnTouchLetterListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                mTxtLetter.setVisibility(View.VISIBLE);
                mTxtLetter.setText(letter);
                if (StringUtil.isEqual(letter, "#")) {
                    mStaggManager.scrollToPositionWithOffset(0, 0);
                }
                if (StringUtil.isEmpty(mEtSearchCode.getValue())) {
                    for (int i = 0; i < mCountryMenus.size(); i++) {
                        if (letter.equals(mCountryMenus.get(i).getMenuName())) {
                            int positionGroup = mSectionListAdapter.getPositionGroup(i);
                            mStaggManager.scrollToPositionWithOffset(positionGroup, 0);
                        }
                    }
                } else {
                    for (int i = 0; i < mCountryTemps.size(); i++) {
                        if (letter.equals(mCountryTemps.get(i).getMenuName())) {
                            int positionGroup = mSectionListAdapter.getPositionGroup(i);
                            mStaggManager.scrollToPositionWithOffset(positionGroup, 0);
                        }
                    }
                }
            }

            @Override
            public void onLetterCancel() {
                mTxtLetter.setVisibility(View.INVISIBLE);
                mTxtLetter.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountryBean.destroy();
    }
}
