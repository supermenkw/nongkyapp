
package com.codekinian.nongkyapp.Model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Places {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("next_page")
    private String mNextPage;
    @SerializedName("per_page")
    private Long mPerPage;
    @SerializedName("result")
    private List<PlaceResult> mResult;
    @SerializedName("status_code")
    private Long mStatusCode;
    @SerializedName("total_data")
    private Long mTotalData;
    @SerializedName("total_page")
    private Long mTotalPage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getNextPage() {
        return mNextPage;
    }

    public void setNextPage(String nextPage) {
        mNextPage = nextPage;
    }

    public Long getPerPage() {
        return mPerPage;
    }

    public void setPerPage(Long perPage) {
        mPerPage = perPage;
    }

    public List<PlaceResult> getResult() {
        return mResult;
    }

    public void setResult(List<PlaceResult> result) {
        mResult = result;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

    public Long getTotalData() {
        return mTotalData;
    }

    public void setTotalData(Long totalData) {
        mTotalData = totalData;
    }

    public Long getTotalPage() {
        return mTotalPage;
    }

    public void setTotalPage(Long totalPage) {
        mTotalPage = totalPage;
    }

}
