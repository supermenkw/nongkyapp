package com.codekinian.nongkyapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.codekinian.nongkyapp.Model.ClusterModel;
import com.codekinian.nongkyapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class CustomClusterRenderer extends DefaultClusterRenderer<ClusterModel> {

    private final IconGenerator mClusterIconGenerator;
    private final Context mContext;

    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<ClusterModel> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
        mClusterIconGenerator = new IconGenerator(mContext.getApplicationContext());
    }

    @Override protected void onBeforeClusterItemRendered(ClusterModel item,
                                                         MarkerOptions markerOptions) {

        final BitmapDescriptor markerDescriptor =
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);

        markerOptions.icon(markerDescriptor).title(item.getTitle()).snippet(item.getSnippet());
    }

    @Override protected void onBeforeClusterRendered(Cluster<ClusterModel> cluster,
                                                     MarkerOptions markerOptions) {
        mClusterIconGenerator.setBackground(
                ContextCompat.getDrawable(mContext, R.drawable.background_circle));

        mClusterIconGenerator.setTextAppearance(R.style.AppTheme_WhiteTextAppearance);

        final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }
}
