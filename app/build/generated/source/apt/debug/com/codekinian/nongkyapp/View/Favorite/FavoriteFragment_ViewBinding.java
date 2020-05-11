// Generated code from Butter Knife. Do not modify!
package com.codekinian.nongkyapp.View.Favorite;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.codekinian.nongkyapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoriteFragment_ViewBinding implements Unbinder {
  private FavoriteFragment target;

  @UiThread
  public FavoriteFragment_ViewBinding(FavoriteFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycleView, "field 'recyclerView'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progressBar'", ProgressBar.class);
    target.progressTwo = Utils.findRequiredViewAsType(source, R.id.progressTwo, "field 'progressTwo'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoriteFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.progressBar = null;
    target.progressTwo = null;
  }
}
