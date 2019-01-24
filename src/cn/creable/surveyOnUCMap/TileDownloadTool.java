package cn.creable.surveyOnUCMap;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;

import org.jeo.vector.Field;

import com.annimon.stream.function.Supplier;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import cn.creable.ucmap.openGIS.UCMapView;
import cn.creable.ucmap.openGIS.UCRasterLayer;
import cn.creable.ucmap.openGIS.UCTileListener;
import cn.creable.ucmap.openGIS.UCVectorLayer;

public class TileDownloadTool implements OnGestureListener,IMapTool{
	
	private UCMapView mapView;
	private UCVectorLayer vlayer;
	public Point startPoint;
	private Point endPoint;
	private LineString line;
	private UCRasterLayer layer;
	private UCRasterLayer layer2;
	private double _xmin;
	private double _ymin;
	private double _xmax;
	private double _ymax;
	private int _zmin;
	private int _zmax;
	private int _cur;
	
	private MainActivity act;
	ProgressDialog dialog;
	
	private GeometryFactory gf=new GeometryFactory();
	
	int total;
	int current;
	
	private UCTileListener tileListener=new UCTileListener() {

		@Override
		public void onTileEvent(String tile) {
			++current;
			if (current==total) {
				switch (_cur)
				{
				case 0:
					mapView.unbindTileListener(layer, tileListener);
					layer.setVisible(true);
					break;
				case 1:
					mapView.unbindTileListener(layer2, tileListener);
					layer2.setVisible(true);
					break;
				}
				
				mapView.refresh();
				dialog.dismiss();
				if (_cur==0 && layer2!=null) {
					++_cur;
					download(act,layer2,_xmin,_ymin,_xmax,_ymax,_zmin,_zmax);
				}
			}
			dialog.setProgress(current);
			
		}
		
	};
	
	public TileDownloadTool(UCMapView mapView,MainActivity act)
	{
		this.act=act;
		this.mapView=mapView;
//		this.layer=layer;
//		this.layer2=layer2;
	}
	
	public void start()
	{
		vlayer=mapView.addVectorLayer();
		mapView.move(false);
		mapView.setListener(this, null);
	}
	
	public void stop()
	{
		if (vlayer!=null) mapView.deleteLayer(vlayer);
		mapView.setListener(null, null);
		mapView.move(true);
		act=null;
		mapView=null;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		startPoint=mapView.toMapPoint(e.getX(), e.getY());
		if (line!=null) {
			vlayer.remove(line);
			mapView.refresh();
			line=null;
		}
		endPoint=null;
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}
	
	public void action()
	{
		if (startPoint==null || endPoint==null) return;
		switch (act.type)
		{
		case 0:
		case 1:
		case 2:
			layer=act.gLayer;
			layer2=null;
			break;
		case 3:
		case 4:
			layer=act.tLayer1;
			layer2=act.tLayer2;
			break;
		}
		double xmin,ymin,xmax,ymax;
		if (startPoint.getX()<endPoint.getX())
		{
			xmin=startPoint.getX();
			xmax=endPoint.getX();
		}
		else
		{
			xmax=startPoint.getX();
			xmin=endPoint.getX();
		}
		if (startPoint.getY()<endPoint.getY())
		{
			ymin=startPoint.getY();
			ymax=endPoint.getY();
		}
		else
		{
			ymax=startPoint.getY();
			ymin=endPoint.getY();
		}
		showDialog(xmin,ymin,xmax,ymax);
	}

	@Override
	public boolean onScroll(MotionEvent e, MotionEvent e2, float arg2, float arg3) {
		Point pt=mapView.toMapPoint(e2.getX(), e2.getY());
		if (startPoint.getX()!=pt.getX() || startPoint.getY()!=pt.getY())
		{
			endPoint=pt;
			Coordinate[] cds=new Coordinate[5];
			cds[0]=new Coordinate(startPoint.getX(),startPoint.getY());
			cds[1]=new Coordinate(endPoint.getX(),startPoint.getY());
			cds[2]=new Coordinate(endPoint.getX(),endPoint.getY());
			cds[3]=new Coordinate(startPoint.getX(),endPoint.getY());
			cds[4]=cds[0];
			LineString newLine=gf.createLineString(cds);
			if (line!=null)
				vlayer.updateGeometry(line, newLine);
			else
				vlayer.addLine(newLine, 2, 0xFFFF0000);
			line=newLine;
			mapView.refresh();
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void showDialog(double xmin,double ymin,double xmax,double ymax)
    {
		final View v = LayoutInflater.from(act).inflate(R.layout.download_dlg,null,false);
		final EditText etXMin=(EditText) v.findViewById(R.id.et_download_xmin);
		etXMin.setText(String.valueOf(xmin));
		final EditText etYMin=(EditText) v.findViewById(R.id.et_download_ymin);
		etYMin.setText(String.valueOf(ymin));
		final EditText etXMax=(EditText) v.findViewById(R.id.et_download_xmax);
		etXMax.setText(String.valueOf(xmax));
		final EditText etYMax=(EditText) v.findViewById(R.id.et_download_ymax);
		etYMax.setText(String.valueOf(ymax));
		final EditText etZMin=(EditText) v.findViewById(R.id.et_download_zmin);
		etZMin.setText("0");
		final EditText etZMax=(EditText) v.findViewById(R.id.et_download_zmax);
		etZMax.setText("15");
		v.findViewById(R.id.iv_download_close).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DialogUtils.finishDialog(333);
			}
		});
		v.findViewById(R.id.tv_download_ss).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				_xmin=Double.parseDouble(etXMin.getText().toString());
				_ymin=Double.parseDouble(etYMin.getText().toString());
				_xmax=Double.parseDouble(etXMax.getText().toString());
				_ymax=Double.parseDouble(etYMax.getText().toString());
				_zmin=Integer.parseInt(etZMin.getText().toString());
				_zmax=Integer.parseInt(etZMax.getText().toString());
				_cur=0;
				download(act,layer,_xmin,_ymin,_xmax,_ymax,_zmin,_zmax);
				DialogUtils.finishDialog(333);
			}
		});
		
		DialogUtils.showFullScreenDialog(act, 333, true, v, 250, 10, 250, 40, new Supplier() {
			@Override
			public Object get() {
				//editTool.cancel();
				return null;
			}
		});
		
//    	ScrollView sv   = new ScrollView(act);     
//    	sv.setLayoutParams( new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT) );
//    	
//    	LinearLayout layout = new LinearLayout(act);
//    	layout.setOrientation( LinearLayout.VERTICAL );
//    	sv.addView( layout ); 
//    	
//    	TextView tv;
//    	EditText et;
//    	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(400,LayoutParams.WRAP_CONTENT);
//    	//lp.setMargins(70, 0, 0, 0);
//    	
//    	final Vector<EditText> ets=new Vector<EditText>();
//    	
//    	tv = new TextView(act);
//    	tv.setText("最小经度");
//    	tv.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	layout.addView( tv );
//    	et=new EditText(act);
//    	et.setLayoutParams(lp);
//    	et.setSingleLine(true);
//    	et.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	et.setTextColor(0xFF000000);
//    	et.setText(String.valueOf(xmin));
//    	layout.addView(et);
//    	ets.addElement(et);
//    	
//    	tv = new TextView(act);
//    	tv.setText("最小纬度");
//    	tv.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	layout.addView( tv );
//    	et=new EditText(act);
//    	et.setLayoutParams(lp);
//    	et.setSingleLine(true);
//    	et.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	et.setTextColor(0xFF000000);
//    	et.setText(String.valueOf(ymin));
//    	layout.addView(et);
//    	ets.addElement(et);
//    	
//    	tv = new TextView(act);
//    	tv.setText("最大经度");
//    	tv.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	layout.addView( tv );
//    	et=new EditText(act);
//    	et.setLayoutParams(lp);
//    	et.setSingleLine(true);
//    	et.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	et.setTextColor(0xFF000000);
//    	et.setText(String.valueOf(xmax));
//    	layout.addView(et);
//    	ets.addElement(et);
//    	
//    	tv = new TextView(act);
//    	tv.setText("最大纬度");
//    	tv.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	layout.addView( tv );
//    	et=new EditText(act);
//    	et.setLayoutParams(lp);
//    	et.setSingleLine(true);
//    	et.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	et.setTextColor(0xFF000000);
//    	et.setText(String.valueOf(ymax));
//    	layout.addView(et);
//    	ets.addElement(et);
//    	
//    	tv = new TextView(act);
//    	tv.setText("最小层号");
//    	tv.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	layout.addView( tv );
//    	et=new EditText(act);
//    	et.setLayoutParams(lp);
//    	et.setSingleLine(true);
//    	et.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	et.setTextColor(0xFF000000);
//    	et.setText("2");
//    	layout.addView(et);
//    	ets.addElement(et);
//    	
//    	tv = new TextView(act);
//    	tv.setText("最大层号");
//    	tv.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	layout.addView( tv );
//    	et=new EditText(act);
//    	et.setLayoutParams(lp);
//    	et.setSingleLine(true);
//    	et.setTextAppearance(act, android.R.style.TextAppearance_Medium);
//    	et.setTextColor(0xFF000000);
//    	et.setText("10");
//    	layout.addView(et);
//    	ets.addElement(et);
//
//    	AlertDialog.Builder builder = new AlertDialog.Builder(act);
//    	builder.setView(sv).setTitle("下载瓦片").setIcon(R.mipmap.ic_launcher)
//    	.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				int count=ets.size();
//				String[] values=new String[count];
//				for (int i=0;i<count;++i)
//				{
//					values[i]=ets.elementAt(i).getText().toString();
//				}
//				_xmin=Double.parseDouble(values[0]);
//				_ymin=Double.parseDouble(values[1]);
//				_xmax=Double.parseDouble(values[2]);
//				_ymax=Double.parseDouble(values[3]);
//				_zmin=Integer.parseInt(values[4]);
//				_zmax=Integer.parseInt(values[5]);
//				_cur=0;
//				download(act,layer,_xmin,_ymin,_xmax,_ymax,_zmin,_zmax);
//			}
//    		
//    	})
//    	.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				
//			}
//		});
//    	builder.create().show();
    }
	
	public void download(Activity a,final UCRasterLayer layer,double xmin,double ymin,double xmax,double ymax,int zmin,int zmax)
	{
		layer.setVisible(false);
		mapView.bindTileListener(layer, tileListener);
		current=0;
		//total=mapView.download(layer, 118.749515,32.124866,118.882519,32.250841,0,17);
		//total=mapView.download(layer, 118.880942,32.169288,118.921771,32.248406,0,16);
        total=mapView.download(layer, xmin, ymin, xmax, ymax, zmin, zmax);
		
        dialog = new ProgressDialog(a);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("下载中");
        dialog.setMax(total);
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    	System.out.println("ok");
//                    }
//                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	mapView.unbindTileListener(layer, tileListener);
        				layer.setVisible(true);
        				mapView.refresh();
                    }
                });
        
        dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				mapView.unbindTileListener(layer, tileListener);
				layer.setVisible(true);
				mapView.refresh();
			}
        	
        });
        dialog.show();
        
        
        
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                int i = 0;
//                while (i < 100) {
//                    try {
//                        Thread.sleep(200);
//                        // 更新进度条的进度,可以在子线程中更新进度条进度
//                        dialog.incrementProgressBy(100);
//                        // dialog.incrementSecondaryProgressBy(10)//二级进度条更新方式
//                        i++;
//
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//                }
//                // 在进度条走完时删除Dialog
//                dialog.dismiss();
//
//            }
//        }).start();
          
        
	}

}
