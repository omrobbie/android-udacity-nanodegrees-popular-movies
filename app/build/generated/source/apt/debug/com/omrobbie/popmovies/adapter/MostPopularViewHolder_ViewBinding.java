// Generated code from Butter Knife. Do not modify!
package com.omrobbie.popmovies.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.omrobbie.popmovies.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MostPopularViewHolder_ViewBinding implements Unbinder {
  private MostPopularViewHolder target;

  @UiThread
  public MostPopularViewHolder_ViewBinding(MostPopularViewHolder target, View source) {
    this.target = target;

    target.poster = Utils.findRequiredViewAsType(source, R.id.img_movie, "field 'poster'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MostPopularViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.poster = null;
  }
}
