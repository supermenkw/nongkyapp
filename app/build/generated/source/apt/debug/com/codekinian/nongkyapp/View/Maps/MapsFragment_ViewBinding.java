// Generated code from Butter Knife. Do not modify!
package com.codekinian.nongkyapp.View.Maps;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.codekinian.nongkyapp.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MapsFragment_ViewBinding implements Unbinder {
  private MapsFragment target;

  @UiThread
  public MapsFragment_ViewBinding(MapsFragment target, View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progressBar'", ProgressBar.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.detail_rate, "field 'ratingBar'", SimpleRatingBar.class);
    target.namePlace = Utils.findRequiredViewAsType(source, R.id.name_place, "field 'namePlace'", TextView.class);
    target.addressDetail = Utils.findRequiredViewAsType(source, R.id.address_detail, "field 'addressDetail'", TextView.class);
    target.distanceDetail = Utils.findRequiredViewAsType(source, R.id.distance_detail, "field 'distanceDetail'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MapsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.ratingBar = null;
    target.namePlace = null;
    target.addressDetail = null;
    target.distanceDetail = null;
  }
}
