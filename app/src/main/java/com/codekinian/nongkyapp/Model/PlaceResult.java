
package com.codekinian.nongkyapp.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PlaceResult {

    @SerializedName("deskripsi")
    private String mDeskripsi;
    @SerializedName("type")
    private String mType;
    @SerializedName("place_id")
    private String mPlaceId;
    @SerializedName("id")
    private int mId;
    @SerializedName("kategori")
    private String mKategori;
    @SerializedName("kecamatan")
    private String mKecamatan;
    @SerializedName("kelurahan")
    private String mKelurahan;
    @SerializedName("latitude")
    private String mLatitude;
    @SerializedName("lokasi")
    private String mLokasi;
    @SerializedName("cover")
    private String mCover;
    @SerializedName("longitude")
    private String mLongitude;
    @SerializedName("nama_tempat")
    private String mNamaTempat;
    @SerializedName("sub_wilayah_kota")
    private String mSubWilayahKota;
    @SerializedName("tipe")
    private String mTipe;

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(String mPlaceId) {
        this.mPlaceId = mPlaceId;
    }

    public String getDeskripsi() {
        return mDeskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        mDeskripsi = deskripsi;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getKategori() {
        return mKategori;
    }

    public void setKategori(String kategori) {
        mKategori = kategori;
    }

    public String getKecamatan() {
        return mKecamatan;
    }

    public void setKecamatan(String kecamatan) {
        mKecamatan = kecamatan;
    }

    public String getKelurahan() {
        return mKelurahan;
    }

    public void setKelurahan(String kelurahan) {
        mKelurahan = kelurahan;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLokasi() {
        return mLokasi;
    }

    public void setLokasi(String lokasi) {
        mLokasi = lokasi;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getNamaTempat() {
        return mNamaTempat;
    }

    public void setNamaTempat(String namaTempat) {
        mNamaTempat = namaTempat;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String mCover) {
        mCover = mCover;
    }

    public String getSubWilayahKota() {
        return mSubWilayahKota;
    }

    public void setSubWilayahKota(String subWilayahKota) {
        mSubWilayahKota = subWilayahKota;
    }

    public String getTipe() {
        return mTipe;
    }

    public void setTipe(String tipe) {
        mTipe = tipe;
    }

}
