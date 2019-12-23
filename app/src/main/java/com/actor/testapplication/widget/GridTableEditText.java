package com.actor.testapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.actor.myandroidframework.utils.TextUtil;
import com.actor.testapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 常用的表格输入布局,这是一个组合控件.
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/7/10 on 17:20
 *
 * 全部属性都是gte开头:
 * 1.右侧箭头显示类型, 默认: 能输入时隐藏, 不能输入时显示
 * @see R.styleable#GridTableEditText_gteArrowRightVisiable //visible/invisible/gone
 * 2.输入限制, 只能输入哪些数字/字母
 * @see R.styleable#GridTableEditText_gteDigits             //0123456789xX
 * 3.右侧输入框文字gravity
 * @see R.styleable#GridTableEditText_gteGravity            //start|centerVertical
 * 4.输入框hint
 * @see R.styleable#GridTableEditText_gteHint               //请输入身份证
 * 5.键盘右下角显示内容
 * @see R.styleable#GridTableEditText_gteImeOptions         //actionNext(下一步)
 * 6.是否能输入, 默认true(false的时候,可以当做TextView展示)
 * @see R.styleable#GridTableEditText_gteInputEnable        //true
 * 7.输入类型
 * @see R.styleable#GridTableEditText_gteInputType          //text
 * 8.左侧提示文字
 * @see R.styleable#GridTableEditText_gteItemName           //请输入身份证：
 * 9.marginTop, 默认1dp
 * @see R.styleable#GridTableEditText_gteMarginTop          //1dp
 * 10.最大输入长度
 * @see R.styleable#GridTableEditText_gteMaxLength          //18
 * 11.左侧红点显示类型, 默认visible
 * @see R.styleable#GridTableEditText_gteRedStarVisiable    //visible/invisible/gone
 * 12.右边EditText的文字
 * @see R.styleable#GridTableEditText_gteText
 *
 *
 * @version 1.1 修改attrs获取@string类型的值时, 获取到的是"@2131755078"的问题. 改用typedArray
 * @version 1.1.1 微小修改
 * @version 1.1.2 增加marginTop功能 & gteGravity功能
 * @version 1.1.3 新增方法 & hint添加默认值
 *                  @see #setDigits(int, boolean)
 *                  @see #setDigits(String, boolean)
 *                  @see #setDigitsRegex(int, boolean)
 *                  @see #setDigitsRegex(String, boolean)
 */
public class GridTableEditText extends LinearLayout implements TextUtil.GetTextAble {

    private TextView  tvRedStar;
    private TextView  tv1;
    private EditText  et1;
    private ImageView ivArrowRight;
    private float     density;//px = dp * density;
    private Space     spaceMarginTop;

    public GridTableEditText(Context context) {
        this(context,null);
    }

    public GridTableEditText(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public GridTableEditText(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置view, 并找到子view
        View inflate = View.inflate(context, R.layout.item_grid_table_edit_text, this);
        spaceMarginTop = inflate.findViewById(R.id.space_margin_top_for_gte);
        tvRedStar = inflate.findViewById(R.id.tv_red_star_for_gte);
        tv1 = inflate.findViewById(R.id.tv_item_name_for_gte);
        et1 = inflate.findViewById(R.id.et_input_for_gte);
        ivArrowRight = inflate.findViewById(R.id.iv_arrow_right_for_gte);

        if (attrs == null) return;
        density = getResources().getDisplayMetrics().density;
        //根据xml中属性, 给view赋值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridTableEditText);
        //左侧红点是否显示
        int redStarVisiable = typedArray.getInt(R.styleable.GridTableEditText_gteRedStarVisiable, 0);
        //EditText是否能输入
        boolean inputEnable = typedArray.getBoolean(R.styleable.GridTableEditText_gteInputEnable, true);
        //左侧TextView的Text
        String gteItemName = typedArray.getString(R.styleable.GridTableEditText_gteItemName);
        //输入框的Hint
        String gteHint = typedArray.getString(R.styleable.GridTableEditText_gteHint);
        //输入框的Text
        String gteText = typedArray.getString(R.styleable.GridTableEditText_gteText);
        //输入类型(下一步, 完成...)
        int gteImeOptions = typedArray.getInt(R.styleable.GridTableEditText_gteImeOptions, -1);
        //最大输入长度
        int gteMaxLength = typedArray.getInt(R.styleable.GridTableEditText_gteMaxLength, -1);
        //输入框文字gravity
        int gravity = typedArray.getInt(R.styleable.GridTableEditText_gteGravity, Gravity.START | Gravity.CENTER_VERTICAL);
        //marginTop, 默认1dp
        int marginTop = typedArray.getDimensionPixelSize(R.styleable.GridTableEditText_gteMarginTop, (int) density);
        //输入类型(text, number...)
        int gteInputType = typedArray.getInt(R.styleable.GridTableEditText_gteInputType, -1);
        //输入限定(例如数字: digits=0123456789)
        String gteDigits = typedArray.getString(R.styleable.GridTableEditText_gteDigits);
        //右侧箭头显示状态
        int arrowRightVisiable = typedArray.getInt(R.styleable.GridTableEditText_gteArrowRightVisiable, -1);
        typedArray.recycle();

        tvRedStar.setVisibility(redStarVisiable * 4);
        if (!inputEnable) setInputEnable(false);
        if (gteItemName != null) {
            getTextViewItem().setText(gteItemName);
            if (gteHint == null) {//hint=null
                if (inputEnable) {//能输入
                    setHint("请输入".concat(gteItemName));//"请输入" + item名称
                } else setHint("请选择".concat(gteItemName));//"请输入请选择 + item名称
            } else setHint(gteHint);
        }
        if (gteText != null) setText(gteText);
        if(gteImeOptions != -1) getEditText().setImeOptions(gteImeOptions);
        if (gteMaxLength >= 0) setMaxLength(gteMaxLength);
        setGravityInput(gravity);
        setMarginTop(marginTop);
        if (gteInputType != -1) getEditText().setInputType(gteInputType);
        if (!TextUtils.isEmpty(gteDigits)) setDigits(gteDigits, false);
        if (arrowRightVisiable == -1) {
            if (inputEnable) {//如果能输入
                ivArrowRight.setVisibility(GONE);//隐藏
            } else ivArrowRight.setVisibility(VISIBLE);//显示
        } else ivArrowRight.setVisibility(arrowRightVisiable * 4);//根据属性来设置显示状态
    }

    /**
     * @return 获取红点
     */
    public TextView getTextViewRedStar() {
        return tvRedStar;
    }

    /**
     * @return 返回Item的TextView
     */
    public TextView getTextViewItem() {
        return tv1;
    }

    /**
     * @return 获取输入框
     */
    @Override
    public EditText getEditText() {
        return et1;
    }

    /**
     * @return 获取右侧箭头
     */
    public ImageView getIvArrowRight() {
        return ivArrowRight;
    }

    @Override
    public Editable getText(){
        return getEditText().getText();
    }

    public void setText(CharSequence text) {
        getEditText().setText(text);
    }

    /**
     * @param maxLength 设置输入最大长度
     */
    public void setMaxLength(@IntRange(from = 0) int maxLength) {
        getEditText().setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * @param digits 设置输入限制, 示例只能输入数字: 0123456789
     * @param reFilter 是否重新对已经输入的内容按照digits过滤一次
     */
    public void setDigits(@StringRes int digits, boolean reFilter) {
        setDigits(getContext().getResources().getString(digits), reFilter);
    }

    /**
     * @param digits 设置输入限制, 示例只能输入数字: 0123456789
     * @param reFilter 是否重新对已经输入的内容按照digits过滤一次
     */
    public void setDigits(String digits, boolean reFilter) {
        if (digits == null) return;
        String regex = "[^" + digits + "]";//例: [^a-zA-Z0-9]
        getEditText().setKeyListener(DigitsKeyListener.getInstance(digits));//设置输入限制
        if (reFilter) filter(regex);
    }

    /**
     * @param regex strings.xml中的资源, 示例: <string name="regex">[^a-zA-Z0-9\u4E00-\u9FA5]</string>
     * @param reFilter 是否重新对已经输入的内容按照digits过滤一次
     */
    public void setDigitsRegex(@StringRes int regex, boolean reFilter) {
        setDigitsRegex(getContext().getResources().getString(regex), reFilter);
    }
    /**
     * @param regex 输入限制的正则, 示例只能输入数字: [^0-9]   //过滤0-9以外的字符
     * @param reFilter 是否重新对已经输入的内容按照digits过滤一次
     */
    public void setDigitsRegex(String regex, boolean reFilter) {
        if (regex == null) return;
        InputFilter[] filters = getEditText().getFilters();//获取所有filter
        boolean hasRegexFilter = false;
        for (InputFilter filter : filters) {
            if (filter instanceof RegexFilter) {//如果有就替换RegexFilter中的regex
                ((RegexFilter) filter).setRegex(regex);
                hasRegexFilter = true;
            }
        }
        if (!hasRegexFilter) {//如果没有RegexFilter, 就增加一个
            InputFilter[] newFilters = new InputFilter[filters.length + 1];
            System.arraycopy(filters, 0, newFilters, 0, filters.length);
            newFilters[filters.length] = new RegexFilter(regex);
            filters = newFilters;
        }
        getEditText().setFilters(filters);
        if (reFilter) filter(regex);
    }

    //正则过滤器
    private class RegexFilter implements InputFilter {

        private String regex;//正则

        private RegexFilter(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source.toString());
            boolean find = matcher.find();
            if (find) return "";
            return source;
        }
    }

    //根据正则, 对已经输入的内容进行过滤
    protected void filter(String regex) {
        if (regex != null) {
            String text = getText().toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            String newText = matcher.replaceAll("");//将digits以外的字符替换成""
            //要判断, 否则会递归栈溢出
            if (!TextUtils.equals(text, newText)) setText(newText);
        }
    }

    @Override
    public CharSequence getHint(){
        return getEditText().getHint();
    }

    public void setHint(CharSequence hilt){
        getEditText().setHint(hilt);
    }

    /**
     * 设置是否可输入(false的时候,可以当做TextView展示)
     * @param enable
     */
    public void setInputEnable(boolean enable) {
//        getEditText().setEnabled(enable);//这样不能编辑,可用于隐藏输入法,但是EditText的点击事件无反应,不能做点击事件
        getEditText().setFocusable(enable);
        getEditText().setClickable(!enable);
        getEditText().setFocusableInTouchMode(enable);
//        if (enable) getEditText().requestFocus();//把光标移动到这一个et1,但是不弹出键盘
//        getEditText().setCursorVisible(false);
    }

    /**
     * 设置marginTop, 单位dp
     */
    public void setMarginTopDp(int dp) {
        setMarginTop((int) (dp * density + 0.5));
    }

    /**
     * 设置marginTop, 单位px
     */
    public void setMarginTop(@Px int px) {
        ViewGroup.LayoutParams layoutParams = spaceMarginTop.getLayoutParams();
        layoutParams.height = px;
        spaceMarginTop.setLayoutParams(layoutParams);
    }

    //设置输入框文字gravity
    public void setGravityInput(int gravity) {
        getEditText().setGravity(gravity);
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener onClickListener) {
        //getChildAt(0) = android.widget.LinearLayout
        getChildAt(0).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) onClickListener.onClick(GridTableEditText.this);
            }
        });
        getEditText().setOnClickListener(new OnClickListener() {//必须要设置,否则点击EditText无效
            @Override
            public void onClick(View v) {
                if (onClickListener != null && getEditText().isClickable()) {
                    onClickListener.onClick(GridTableEditText.this);
                }
            }
        });
    }
}
