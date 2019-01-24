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

public class SpatialAnalysis extends Fragment {
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
	
	public SpatialAnalysis(UCMapView mapView) {
		mView=mapView;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		activity = (MainActivity) getActivity();
        final View view = inflater.inflate(R.layout.analysis, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_analysis_close);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.closeMenu();
            }
        });
        mChart = (PieChart) view.findViewById(R.id.pc_analysis);
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
                	int type=((UCFeatureLayer)iLayer).getGeometryType();
                	if (type==ogr.wkbPolygon || (type-3)==ogr.wkbPolygon) { //-3是用来兼容Mutli类型的geometry)
                		layernames.add(iLayer.getName());
                	}
                }
            }
        });
        final TextView tv_layername = (TextView) view.findViewById(R.id.tv_analysis_layer);
        tv_layername.setCompoundDrawables(null, null, drawable, null);
        tv_layername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectString.select(activity, "请选择要分析的图层", layernames, new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        tv_layername.setText(s);
                    }
                });
            }
        });
        final TextView tv_searchcolumn = (TextView) view.findViewById(R.id.tv_analysis_field);
        tv_searchcolumn.setCompoundDrawables(null, null, drawable, null);
        tv_searchcolumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String layername = tv_layername.getText().toString().trim();
                if ("".equals(layername) || "请选择要分析的图层".equals(layername)) {
                    BluToast.makeText(activity, "请先选择要分析的图层！", BluToast.LENGTH_SHORT).show();
                } else {
                    SelectString.select(activity, "请选择用来分类的字段", DBUtils.getLayerColumns(getLayer(layername),String.class),
                            new Consumer<String>() {
                                @Override
                                public void accept(String s) {
                                    tv_searchcolumn.setText(s);
                                    BitmapDrawable bd=(BitmapDrawable) getResources().getDrawable(R.drawable.marker_poi);
                			        AnalysisTool tool=new AnalysisTool(mView,getLayer(layername),bd.getBitmap(),mChart,s);
                					activity.setCurrentTool(tool);
                					tool.start();
                                }
                            });
                }
            }
        });
      //将统计界面保存为图片
        final RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_analysis);
        TextView tv_save = (TextView) view.findViewById(R.id.tv_analysis_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.INVISIBLE);
                GVS.getInstance().viewSaveToImage(view, "统计" + System.currentTimeMillis() + ".png");
                rl.setVisibility(View.VISIBLE);
                BluToast.makeText(activity, "保存成功！", BluToast.LENGTH_SHORT).show();
            }
        });
        return view;
	}
}
