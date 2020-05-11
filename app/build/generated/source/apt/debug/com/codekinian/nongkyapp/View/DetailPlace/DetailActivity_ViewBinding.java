// Generated code from Butter Knife. Do not modify!
package com.codekinian.nongkyapp.View.DetailPlace;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.codekinian.nongkyapp.R;
import com.daimajia.slider.library.SliderLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.opening = Utils.findRequiredViewAsType(source, R.id.opening, "field 'opening'", TextView.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.phone, "field 'phone'", TextView.class);
    target.address = Utils.findRequiredViewAsType(source, R.id.address, "field 'address'", TextView.class);
    target.maps_static = Utils.findRequiredViewAsType(source, R.id.maps_static, "field 'maps_static'", ImageView.class);
    target.iv_fav = Utils.findRequiredViewAsType(source, R.id.image_favorite, "field 'iv_fav'", ImageView.class);
    target.cover = Utils.findRequiredViewAsType(source, R.id.cover, "field 'cover'", SliderLayout.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progressBar'", ProgressBar.class);
    target.collapsingToolbarLayout = Utils.findRequiredViewAsType(source, R.id.collapsing_toolbar, "field 'collapsingToolbarLayout'", CollapsingToolbarLayout.class);
    target.descriptionLayout = Utils.findRequiredViewAsType(source, R.id.descriptionLayout, "field 'descriptionLayout'", CardView.class);
    target.directionPlace = Utils.findRequiredViewAsType(source, R.id.direction_place, "field 'directionPlace'", CardView.class);
    target.sharePlace = Utils.findRequiredViewAsType(source, R.id.share_place, "field 'sharePlace'", CardView.class);
    target.favoritePlace = Utils.findRequiredViewAsType(source, R.id.favorite_place, "field 'favoritePlace'", CardView.class);
    target.reviewsList = Utils.findRequiredViewAsType(source, R.id.reviews_list, "field 'reviewsList'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.opening = null;
    target.phone = null;
    target.address = null;
    target.maps_static = null;
    target.iv_fav = null;
    target.cover = null;
    target.progressBar = null;
    target.collapsingToolbarLayout = null;
    target.descriptionLayout = null;
    target.directionPlace = null;
    target.sharePlace = null;
    target.favoritePlace = null;
    target.reviewsList = null;
  }
}
