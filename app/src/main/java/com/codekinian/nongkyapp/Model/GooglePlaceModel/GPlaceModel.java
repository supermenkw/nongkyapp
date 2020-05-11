
package com.codekinian.nongkyapp.Model.GooglePlaceModel;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GPlaceModel {

    @SerializedName("html_attributions")
    private List<Object> mHtmlAttributions;
    @SerializedName("next_page_token")
    private String mNextPageToken;
    @SerializedName("results")
    private List<GPlaceResult> mResults;
    @SerializedName("status")
    private String mStatus;

    public List<Object> getHtmlAttributions() {
        return mHtmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        mHtmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return mNextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        mNextPageToken = nextPageToken;
    }

    public List<GPlaceResult> getResults() {
        return mResults;
    }

    public void setResults(List<GPlaceResult> results) {
        mResults = results;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
