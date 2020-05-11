// Generated code from Butter Knife. Do not modify!
package com.codekinian.nongkyapp.Adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.codekinian.nongkyapp.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReviewsAdapter$ReviewsViewHolder_ViewBinding implements Unbinder {
  private ReviewsAdapter.ReviewsViewHolder target;

  @UiThread
  public ReviewsAdapter$ReviewsViewHolder_ViewBinding(ReviewsAdapter.ReviewsViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.tv_item_name, "field 'name'", TextView.class);
    target.date = Utils.findRequiredViewAsType(source, R.id.tv_item_date, "field 'date'", TextView.class);
    target.reviews = Utils.findRequiredViewAsType(source, R.id.tv_reviews, "field 'reviews'", TextView.class);
    target.cover = Utils.findRequiredViewAsType(source, R.id.img_item_photo, "field 'cover'", ImageView.class);
    target.rate = Utils.findRequiredViewAsType(source, R.id.reviews_rate, "field 'rate'", SimpleRatingBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReviewsAdapter.ReviewsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.date = null;
    target.reviews = null;
    target.cover = null;
    target.rate = null;
  }
}
