package com.codekinian.nongkyapp.View.Favorite;

import android.content.Intent;

public interface FavoriteView {
        void showLoading();

        void hideLoading();

        void moveToDetail(Intent intent);
}
