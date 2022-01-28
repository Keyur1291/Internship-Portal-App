package com.android.internshipportal;

public class NumbersView {

    private int ivNumbersImageId;
    private String mNumberInDigit;
    private String mNumbersInText;

    public NumbersView(int NumbersImageId, String NumbersInDigit, String NumbersInText) {
        ivNumbersImageId = NumbersImageId;
        mNumberInDigit = NumbersInDigit;
        mNumbersInText = NumbersInText;
    }

    public int getNumbersImageId() {
        return ivNumbersImageId;
    }

    public String getNumberInDigit() {
        return mNumberInDigit;
    }

    public String getNumbersInText() {
        return mNumbersInText;
    }
}
