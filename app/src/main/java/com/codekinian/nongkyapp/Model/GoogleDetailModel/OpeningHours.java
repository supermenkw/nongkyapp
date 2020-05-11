
package com.codekinian.nongkyapp.Model.GoogleDetailModel;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class OpeningHours {

    @SerializedName("open_now")
    private Boolean mOpenNow;
    @SerializedName("periods")
    private List<Period> mPeriods;
    @SerializedName("weekday_text")
    private List<String> mWeekdayText;

    public Boolean getOpenNow() {
        return mOpenNow;
    }

    public void setOpenNow(Boolean openNow) {
        mOpenNow = openNow;
    }

    public List<Period> getPeriods() {
        return mPeriods;
    }

    public void setPeriods(List<Period> periods) {
        mPeriods = periods;
    }

    public List<String> getWeekdayText() {
        return mWeekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        mWeekdayText = weekdayText;
    }

}
