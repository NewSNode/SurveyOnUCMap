package cn.creable.surveyOnUCMap;

import java.util.ArrayList;
import java.util.List;

import org.gdal.ogr.ogr;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.github.mikephil.charting.charts.PieChart;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.creable.ucmap.openGIS.UCFeatureLayer;
import cn.creable.ucmap.openGIS.UCLayer;
import cn.creable.ucmap.openGIS.UCMapView;

public class LayerLabel extends Fragment {
	private MainActivity activity;
	
	private UCLayer[] layers;
	private UCMapView mView;
	private PieChart mChart;
	
	UCFeatureLayer getLayer(String layerName)
    {
        for (UCLayer layer:layers)
        {
            if (layer.getName().equals(layerName) && layer instanceof UCFeatureLayer)
                return (UCFeatureLayer) layer;
        }
        return null;
    }
	
	public LayerLabel(UCMapView mapView) {
		mView=mapView;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		activity = (MainActivity) getActivity();
        final View view = inflater.inflate(R.layout.layer_label, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_layer_label_close);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.closeMenu();
            }
        });
        int size=activity.layers.size();
        Drawable drawable = getResources().getDrawable(R.drawable.combobox);
        drawable.setBounds(0, 0, 28, 16);
        final List<String> layernames = new ArrayList<>();
        layers=new UCLayer[size];
        for (int i=0;i<size;++i)
        {
        	layers[i]=activity.layers.get(i).layer;
        }
        
        Stream.of(layers).forEach(new Consumer<UCLayer>() {
            @Override
            public void accept(UCLayer iLayer) {
                if (iLayer instanceof UCFeatureLayer) {
                	layernames.add(iLayer.getName());
                }
            }
        });
        final TextView tv_layername = (TextView) view.findViewById(R.id.tv_layer_label_layer);
        final TextView tv_searchcolumn = (TextView) view.findViewById(R.id.tv_layer_label_field);
        tv_layername.setCompoundDrawables(null, null, drawable, null);
        tv_layername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectString.select(activity, "请选择要标注的图层", layernames, new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        tv_layername.setText(s);
                        UCFeatureLayer layer=getLayer(tv_layername.getText().toString().trim());
                        tv_searchcolumn.setText(layer.getNameField());
                    }
                });
            }
        });
        
        tv_searchcolumn.setCompoundDrawables(null, null, drawable, null);
        tv_searchcolumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String layername = tv_layername.getText().toString().trim();
                if ("".equals(layername) || "请选择要标注的图层".equals(layername)) {
                    BluToast.makeText(activity, "请先选择要标注的图层！", BluToast.LENGTH_SHORT).show();
                } else {
                    SelectString.select(activity, "请选择用来标注的字段", DBUtils.getLayerColumns(getLayer(layername),String.class),
                            new Consumer<String>() {
                                @Override
                                public void accept(String s) {
                                    tv_searchcolumn.setText(s);
                                    getLayer(layername).setNameField(s);
                                    mView.refresh();
                                }
                            });
                }
            }
        });
        return view;
	}
}
