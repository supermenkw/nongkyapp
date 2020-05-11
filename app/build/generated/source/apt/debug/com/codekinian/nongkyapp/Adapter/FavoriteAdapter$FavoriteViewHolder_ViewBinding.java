// Generated code from Butter Knife. Do not modify!
package com.codekinian.nongkyapp.Adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.codekinian.nongkyapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoriteAdapter$FavoriteViewHolder_ViewBinding implements Unbinder {
  private FavoriteAdapter.FavoriteViewHolder target;

  @UiThread
  public FavoriteAdapter$FavoriteViewHolder_ViewBinding(FavoriteAdapter.FavoriteViewHolder target,
      View source) {
    this.target = target;

    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.address = Utils.findRequiredViewAsType(source, R.id.address, "field 'address'", TextView.class);
    target.rating = Utils.findRequiredViewAsType(source, R.id.rating, "field 'rating'", TextView.class);
    target.distance = Utils.findRequiredViewAsType(source, R.id.distance, "field 'distance'", TextView.class);
    target.cover = Utils.findRequiredViewAsType(source, R.id.cover, "field 'cover'", ImageView.class);
    target.iv_vote = Utils.findRequiredViewAsType(source, R.id.ratingBarID, "field 'iv_vote'", RatingBar.class);
    target.iv_fav = Utils.findRequiredViewAsType(source, R.id.btnFav, "field 'iv_fav'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoriteAdapter.FavoriteViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.title = null;
    target.address = null;
    target.rating = null;
    target.distance = null;
    target.cover = null;
    target.iv_vote = null;
    target.iv_fav = null;
  }
}
