package com.flyco.dialog.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BottomBaseDialog;

import java.util.ArrayList;

/**
 * Dialog like iOS ActionSheet(iOS���Ի���)
 */
public class ActionSheetDialog extends BottomBaseDialog<ActionSheetDialog> {
    /** ListView */
    private ListView mLv;
    /** title */
    private TextView mTvTitle;
    /** title underline(�����»���) */
    private View mVLineTitle;
    /** mCancel button(ȡ����ť) */
    private TextView mTvCancel;
    /** corner radius,dp(Բ�ǳ̶�,��λdp) */
    private float mCornerRadius = 5;
    /** title background color(���ⱳ����ɫ) */
    private int mTitleBgColor = Color.parseColor("#ddffffff");
    /** title text(����) */
    private String mTitle = "��ʾ";
    /** title height(�������߶�) */
    private float mTitleHeight = 48;
    /** title textcolor(������ɫ) */
    private int mTitleTextColor = Color.parseColor("#8F8F8F");
    /** title textsize(���������С,��λsp) */
    private float mTitleTextSize = 17.5f;
    /** ListView background color(ListView����ɫ) */
    private int mLvBgColor = Color.parseColor("#ddffffff");
    /** divider color(ListView divider��ɫ) */
    private int mDividerColor = Color.parseColor("#D7D7D9");
    /** divider height(ListView divider�߶�) */
    private float mDividerHeight = 0.8f;
    /** item press color(ListView item��ס��ɫ) */
    private int mItemPressColor = Color.parseColor("#ffcccccc");
    /** item textcolor(ListView item������ɫ) */
    private int mItemTextColor = Color.parseColor("#44A2FF");
    /** item textsize(ListView item���ִ�С) */
    private float mItemTextSize = 17.5f;
    /** item height(ListView item�߶�) */
    private float mItemHeight = 48;
    /** enable title show(�Ƿ���ʾ����) */
    private boolean mIsTitleShow = true;
    /*** cancel btn text(ȡ����ť����) */
    private String mCancelText = "ȡ��";
    /** cancel btn text color(ȡ����ť������ɫ) */
    private int mCancelTextColor = Color.parseColor("#44A2FF");
    /** cancel btn text size(ȡ����ť���ִ�С) */
    private float mCancelTextSize = 17.5f;
    /** adapter(�Զ���������) */
    private BaseAdapter mAdapter;
    /** operation items(����items) */
    private ArrayList<DialogMenuItem> mContents = new ArrayList<DialogMenuItem>();
    private OnOperItemClickL mOnOperItemClickL;
    private LayoutAnimationController mLac;

    public void setOnOperItemClickL(OnOperItemClickL onOperItemClickL) {
        mOnOperItemClickL = onOperItemClickL;
    }

    public ActionSheetDialog(Context context, ArrayList<DialogMenuItem> baseItems, View animateView) {
        super(context, animateView);
        mContents.addAll(baseItems);
        init();
    }

    public ActionSheetDialog(Context context, String[] items, View animateView) {
        super(context, animateView);
        mContents = new ArrayList<DialogMenuItem>();
        for (String item : items) {
            DialogMenuItem customBaseItem = new DialogMenuItem(item, 0);
            mContents.add(customBaseItem);
        }
        init();
    }

    public ActionSheetDialog(Context context, BaseAdapter adapter, View animateView) {
        super(context, animateView);
        mAdapter = adapter;
        init();
    }

    private void init() {
        widthScale(0.95f);
        /** LayoutAnimation */
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 6f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(350);
        animation.setStartOffset(150);

        mLac = new LayoutAnimationController(animation, 0.12f);
        mLac.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    public View onCreateView() {
        LinearLayout ll_container = new LinearLayout(mContext);
        ll_container.setOrientation(LinearLayout.VERTICAL);
        ll_container.setBackgroundColor(Color.TRANSPARENT);

        /** title */
        mTvTitle = new TextView(mContext);
        mTvTitle.setGravity(Gravity.CENTER);
        mTvTitle.setPadding(dp2px(10), dp2px(5), dp2px(10), dp2px(5));

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = dp2px(20);

        ll_container.addView(mTvTitle, params);

        /** title underline */
        mVLineTitle = new View(mContext);
        ll_container.addView(mVLineTitle);

        /** listview */
        mLv = new ListView(mContext);
        mLv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
        mLv.setCacheColorHint(Color.TRANSPARENT);
        mLv.setFadingEdgeLength(0);
        mLv.setVerticalScrollBarEnabled(false);
        mLv.setSelector(new ColorDrawable(Color.TRANSPARENT));

        ll_container.addView(mLv);

        /** mCancel btn */
        mTvCancel = new TextView(mContext);
        mTvCancel.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.topMargin = dp2px(7);
        lp.bottomMargin = dp2px(7);
        mTvCancel.setLayoutParams(lp);

        ll_container.addView(mTvCancel);

        return ll_container;
    }

    @Override
    public void setUiBeforShow() {
        /** title */
        float radius = dp2px(mCornerRadius);
        mTvTitle.setHeight(dp2px(mTitleHeight));
        mTvTitle.setBackgroundDrawable(CornerUtils.cornerDrawable(mTitleBgColor, new float[]{radius, radius, radius,
                radius, 0, 0, 0, 0}));
        mTvTitle.setText(mTitle);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);

        /** title underline */
        mVLineTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(mDividerHeight)));
        mVLineTitle.setBackgroundColor(mDividerColor);
        mVLineTitle.setVisibility(mIsTitleShow ? View.VISIBLE : View.GONE);

        /** mCancel btn */
        mTvCancel.setHeight(dp2px(mItemHeight));
        mTvCancel.setText(mCancelText);
        mTvCancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCancelTextSize);
        mTvCancel.setTextColor(mCancelTextColor);
        mTvCancel.setBackgroundDrawable(CornerUtils.listItemSelector(radius, mLvBgColor, mItemPressColor, 1, 0));

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        /** listview */
        mLv.setDivider(new ColorDrawable(mDividerColor));
        mLv.setDividerHeight(dp2px(mDividerHeight));

        if (mIsTitleShow) {
            mLv.setBackgroundDrawable(CornerUtils.cornerDrawable(mLvBgColor, new float[]{0, 0, 0, 0, radius, radius, radius,
                    radius}));
        } else {
            mLv.setBackgroundDrawable(CornerUtils.cornerDrawable(mLvBgColor, radius));
        }

        if (mAdapter == null) {
            mAdapter = new ListDialogAdapter();
        }

        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnOperItemClickL != null) {
                    mOnOperItemClickL.onOperItemClick(parent, view, position, id);
                }
            }
        });

        mLv.setLayoutAnimation(mLac);
    }

    /** set title background color(���ñ���������ɫ) */
    public ActionSheetDialog titleBgColor(int titleBgColor) {
        mTitleBgColor = titleBgColor;
        return this;
    }

    /** set title text(���ñ�������) */
    public ActionSheetDialog title(String title) {
        mTitle = title;
        return this;
    }

    /** set titleHeight(���ñ���߶�) */
    public ActionSheetDialog titleHeight(float titleHeight) {
        mTitleHeight = titleHeight;
        return this;
    }

    /** set title textsize(���ñ��������С) */
    public ActionSheetDialog titleTextSize_SP(float titleTextSize_SP) {
        mTitleTextSize = titleTextSize_SP;
        return this;
    }

    /** set title textcolor(���ñ���������ɫ) */
    public ActionSheetDialog titleTextColor(int titleTextColor) {
        mTitleTextColor = titleTextColor;
        return this;
    }

    /** enable title show(���ñ����Ƿ���ʾ) */
    public ActionSheetDialog isTitleShow(boolean isTitleShow) {
        mIsTitleShow = isTitleShow;
        return this;
    }

    /** set ListView background color(����ListView����) */
    public ActionSheetDialog lvBgColor(int lvBgColor) {
        mLvBgColor = lvBgColor;
        return this;
    }

    /** set corner radius(����Բ�ǳ̶�,��λdp) */
    public ActionSheetDialog cornerRadius(float cornerRadius_DP) {
        mCornerRadius = cornerRadius_DP;
        return this;
    }

    /** set divider color(ListView divider��ɫ) */
    public ActionSheetDialog dividerColor(int dividerColor) {
        mDividerColor = dividerColor;
        return this;
    }

    /** set divider height(ListView divider�߶�) */
    public ActionSheetDialog dividerHeight(float dividerHeight_DP) {
        mDividerHeight = dividerHeight_DP;
        return this;
    }

    /** set item press color(item��ס��ɫ) */
    public ActionSheetDialog itemPressColor(int itemPressColor) {
        mItemPressColor = itemPressColor;
        return this;
    }

    /** set item textcolor(item������ɫ)* @return ActionSheetDialog */
    public ActionSheetDialog itemTextColor(int itemTextColor) {
        mItemTextColor = itemTextColor;
        return this;
    }

    /** set item textsize(item�����С) */
    public ActionSheetDialog itemTextSize(float itemTextSize_SP) {
        mItemTextSize = itemTextSize_SP;
        return this;
    }

    /** set item height(item�߶�) */
    public ActionSheetDialog itemHeight(float itemHeight_DP) {
        mItemHeight = itemHeight_DP;
        return this;
    }

    /** set layoutAnimation(����layout���� ,����null������ʾlayout����) */
    public ActionSheetDialog layoutAnimation(LayoutAnimationController lac) {
        mLac = lac;
        return this;
    }

    /** set cancel btn text(����ȡ����ť����) */
    public ActionSheetDialog cancelText(String cancelText) {
        mCancelText = cancelText;
        return this;
    }

    /** cancel btn text color(ȡ����ť������ɫ) */
    public ActionSheetDialog cancelText(int cancelTextColor) {
        mCancelTextColor = cancelTextColor;
        return this;
    }

    /** cancel btn text size(ȡ����ť���ִ�С) */
    public ActionSheetDialog cancelTextSize(float cancelTextSize) {
        mCancelTextSize = cancelTextSize;
        return this;
    }

    class ListDialogAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mContents.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final DialogMenuItem item = mContents.get(position);

            LinearLayout llItem = new LinearLayout(mContext);
            llItem.setOrientation(LinearLayout.HORIZONTAL);
            llItem.setGravity(Gravity.CENTER_VERTICAL);

            ImageView ivItem = new ImageView(mContext);
            ivItem.setPadding(0, 0, dp2px(15), 0);
            llItem.addView(ivItem);

            TextView tvItem = new TextView(mContext);
            tvItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            tvItem.setSingleLine(true);
            tvItem.setGravity(Gravity.CENTER);
            tvItem.setTextColor(mItemTextColor);
            tvItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, mItemTextSize);
            tvItem.setHeight(dp2px(mItemHeight));

            llItem.addView(tvItem);
            float radius = dp2px(mCornerRadius);
            if (mIsTitleShow) {
                llItem.setBackgroundDrawable((CornerUtils.listItemSelector(radius, Color.TRANSPARENT, mItemPressColor,
                        position == mContents.size() - 1)));
            } else {
                llItem.setBackgroundDrawable(CornerUtils.listItemSelector(radius, Color.TRANSPARENT, mItemPressColor,
                        mContents.size(), position));
            }

            ivItem.setImageResource(item.mResId);
            tvItem.setText(item.mOperName);
            ivItem.setVisibility(item.mResId == 0 ? View.GONE : View.VISIBLE);

            return llItem;
        }
    }
}
