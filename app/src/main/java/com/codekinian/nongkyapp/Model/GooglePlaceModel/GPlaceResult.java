
package com.codekinian.nongkyapp.Model.GooglePlaceModel;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GPlaceResult {

    @SerializedName("geometry")
    private Geometry mGeometry;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("photos")
    private List<Photo> mPhotos;
    @SerializedName("place_id")
    private String mPlaceId;
    @SerializedName("plus_code")
    private PlusCode mPlusCode;
    @SerializedName("rating")
    private Double mRating;
    @SerializedName("reference")
    private String mReference;
    @SerializedName("scope")
    private String mScope;
    @SerializedName("types")
    private List<String> mTypes;
    @SerializedName("vicinity")
    private String mVicinity;

    public Geometry getGeometry() {
        return mGeometry;
    }

    public void setGeometry(Geometry geometry) {
        mGeometry = geometry;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos = photos;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(String placeId) {
        mPlaceId = placeId;
    }

    public PlusCode getPlusCode() {
        return mPlusCode;
    }

    public void setPlusCode(PlusCode plusCode) {
        mPlusCode = plusCode;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public String getReference() {
        return mReference;
    }

    public void setReference(String reference) {
        mReference = reference;
    }

    public String getScope() {
        return mScope;
    }

    public void setScope(String scope) {
        mScope = scope;
    }

    public List<String> getTypes() {
        return mTypes;
    }

    public void setTypes(List<String> types) {
        mTypes = types;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String vicinity) {
        mVicinity = vicinity;
    }

}
