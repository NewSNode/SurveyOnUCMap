package cn.creable.surveyOnUCMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import org.jeo.vector.Field;

import cn.creable.ucmap.openGIS.UCFeatureLayer;

/**
 * Created by blucelee on 2017/3/27.
 */

public class DBUtils {
    public static List<String> getLayerColumns(UCFeatureLayer layer) {
        List<String> columns = new ArrayList<>();
        int fieldCount=layer.getFieldCount();
        for (int i=0;i<fieldCount;++i)
        {
        	columns.add(layer.getField(i).name());
        }
        return columns;
    }
    
    public static List<String> getLayerColumns(UCFeatureLayer layer,Class<?> type) {
        List<String> columns = new ArrayList<>();
        int fieldCount=layer.getFieldCount();
        for (int i=0;i<fieldCount;++i)
        {
        	Field f=layer.getField(i);
        	if (f.type()==type)
        		columns.add(f.name());
        }
        return columns;
    }
}
