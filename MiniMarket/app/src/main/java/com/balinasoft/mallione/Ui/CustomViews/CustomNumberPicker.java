package com.balinasoft.mallione.Ui.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.balinasoft.mallione.interfaces.Title;

import java.util.ArrayList;

/**
 * Created by Microsoft on 20.06.2016.
 */
public class CustomNumberPicker extends NumberPicker {
    public boolean isWheel() {
        return selectorWheel;
    }

    public void setSelectorWheel(boolean wheel) {
        selectorWheel = wheel;
        setWrapSelectorWheel(wheel);
    }

    boolean selectorWheel = false;
    private SelectListener selectListener = new SelectListener() {
        @Override
        public void onSelect(Title title) {

        }
    };
    private FistItemListener fistItemListener = new FistItemListener() {
        @Override
        public void onFistItem() {

        }
    };
    LastItemListener lastItemListener = new LastItemListener() {
        @Override
        public void onLastItem() {

        }
    };

    public ArrayList<Title> getTitles() {
        return (ArrayList<Title>) titles;
    }

    public void setTitles(ArrayList<? extends Title> titles) {
        setDisplayedValues(null);
        if (titles != null && titles.size() > 0) {
            setMinValue(0);
            setMaxValue(titles.size() - 1);
            //   setValue(0);
            this.titles = (ArrayList<Title>) titles;
            this.setDisplayedValues();
        } else {
            this.titles = new ArrayList<>();
            for (int i = 0; i < 3; i++)

                this.titles.add(new Title() {
                    @Override
                    public String getTitle() {
                        return " ";
                    }
                });
            setMinValue(0);
            setMaxValue(1);
            setDisplayedValues();
        }
    }

    public void addTitles(ArrayList<? extends Title> titles) {

        this.titles.addAll(titles);
        setDisplayedValues(null);
        setMinValue(0);
        setMaxValue(this.titles.size() - 1);
        setDisplayedValues();
    }

    ArrayList<Title> titles = new ArrayList<>();

    public CustomNumberPicker(Context context) {
        super(context);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

    }

    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }


    private void setDisplayedValues() {

        String[] values = new String[titles.size()];
        int i = 0;
        for (Title title : titles) {
            if (i < titles.size()) {
                values[i] = title.getTitle();
            } else break;
            i++;
        }
        setWrapSelectorWheel(isWheel());
        super.setDisplayedValues(values);


    }

    public Title getSelectValue() {
        return titles.get(getValue());
    }

    public SelectListener getClickListener() {
        return selectListener;
    }

    public FistItemListener getFistItemListener() {
        return fistItemListener;
    }

    public void setSelectListener(final SelectListener selectListener) {
        this.selectListener = selectListener;
        OnValueChangeListener onValueChangeListener = new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (newVal == titles.size() - 1 && fistItemListener!=null) {

                    fistItemListener.onFistItem();
                }
                if (getMaxValue() >=(newVal-1) && (newVal) >=getMinValue() && selectListener!=null)
                    selectListener.onSelect(titles.get(newVal));

            }
        };
        super.setOnValueChangedListener(onValueChangeListener);
    }

    public void setFistItemListener(FistItemListener fistItemListener) {
        this.fistItemListener = fistItemListener;
    }


    public interface SelectListener {
        void onSelect(Title title);
    }

    public interface FistItemListener {
        void onFistItem();
    }

    public interface LastItemListener {
        void onLastItem();
    }

    public LastItemListener getLastItemListener() {
        return lastItemListener;
    }

    public void setLastItemListener(LastItemListener lastItemListener) {
        this.lastItemListener = lastItemListener;
    }


}
