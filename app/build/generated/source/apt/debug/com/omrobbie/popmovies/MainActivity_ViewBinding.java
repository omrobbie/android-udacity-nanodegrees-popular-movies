// Generated code from Butter Knife. Do not modify!
package com.omrobbie.popmovies;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.swipeRefresh = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'swipeRefresh'", SwipeRefreshLayout.class);
    target.rv = Utils.findRequiredViewAsType(source, R.id.rv, "field 'rv'", RecyclerView.class);
    target.parentMain = Utils.findRequiredViewAsType(source, R.id.parentMain, "field 'parentDetail'", RelativeLayout.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    target.sHighestRated = res.getString(R.string.highest_rated);
    target.sMostPopular = res.getString(R.string.most_popular);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.swipeRefresh = null;
    target.rv = null;
    target.parentMain = null;
  }
}
