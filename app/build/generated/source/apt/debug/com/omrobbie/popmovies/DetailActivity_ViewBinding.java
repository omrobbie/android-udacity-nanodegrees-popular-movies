// Generated code from Butter Knife. Do not modify!
package com.omrobbie.popmovies;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
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
    target.backdrop = Utils.findRequiredViewAsType(source, R.id.backdrop, "field 'backdrop'", ImageView.class);
    target.poster = Utils.findRequiredViewAsType(source, R.id.poster, "field 'poster'", ImageView.class);
    target.releaseDate = Utils.findRequiredViewAsType(source, R.id.releaseDate, "field 'releaseDate'", TextView.class);
    target.rating = Utils.findRequiredViewAsType(source, R.id.rating, "field 'rating'", TextView.class);
    target.synopsis = Utils.findRequiredViewAsType(source, R.id.synopsis, "field 'synopsis'", TextView.class);
    target.parentDetail = Utils.findRequiredViewAsType(source, R.id.parentDetail, "field 'parentDetail'", CoordinatorLayout.class);
    target.ratingStarViews = Utils.listOf(
        Utils.findRequiredViewAsType(source, R.id.star1, "field 'ratingStarViews'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.star2, "field 'ratingStarViews'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.star3, "field 'ratingStarViews'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.star4, "field 'ratingStarViews'", ImageView.class), 
        Utils.findRequiredViewAsType(source, R.id.star5, "field 'ratingStarViews'", ImageView.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.backdrop = null;
    target.poster = null;
    target.releaseDate = null;
    target.rating = null;
    target.synopsis = null;
    target.parentDetail = null;
    target.ratingStarViews = null;
  }
}
