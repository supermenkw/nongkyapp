
package com.codekinian.nongkyapp.Model.GoogleDetailModel;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GDetailModel {

    @SerializedName("html_attributions")
    private List<Object> mHtmlAttributions;
    @SerializedName("result")
    private GDetailResult mResult;
    @SerializedName("status")
    private String mStatus;

    public List<Object> getHtmlAttributions() {
        return mHtmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        mHtmlAttributions = htmlAttributions;
    }

    public GDetailResult getResult() {
        return mResult;
    }

    public void setResult(GDetailResult result) {
        mResult = result;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
