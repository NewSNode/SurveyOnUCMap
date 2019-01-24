
<p>“外调助手”基于安卓GIS组件UCMap OpenGL版开发（源码官网下载地址 <a href="http://www.creable.cn/kuibu/xiazai.asp" rel="nofollow">http://www.creable.cn/kuibu/xiazai.asp</a> ），适用于林业、国土、管线以及各类POI点等行业的外业数据采集；支持天地图和Google地图为底图，
在天地图或Google地图上可以叠加自己的影像图和ShapeFile矢量图，支持ShapeFile矢量要素的采集和编辑，同时，还支持拍照、录音、录像等多媒体功能。</p>

<p>加载图层的方法：</p>
<p>加载shp，需要将shp的几个文件都拷贝到手机内置存储卡根目录下；</p>
<p>加载影像tiff或tpk瓦片，需要用UCMap（OpenGL版）开发包里的 UCMap地图配置工具 将tiff或tpk统一转成 通用的web墨卡托的mbtiles 格式，
  然后将mbtiles文件拷贝到手机内置存储卡根目录下实现加载，具体怎么用这个转换工具，看UCMap OpenGL版 开发包里的教程最后章节</p>

<p>“外调助手”具有以下几个特点：
一、简单易操作；二、通用，各行业都能适用；三、易扩展、可根据不同行业的特殊需求进行扩展定制；四、地图显示速度快，用户体验好；五、功能强大。</p>

<p>技术特点：</p>
<p>一、支持矢量ShapeFile动态投影，将其他坐标系的shp动态投影叠加到天地图或google底图上，需要shp数据的.prj文件；</p>
<p>二、支持矢量shp、影像tiff、tpk叠加google底图时的坐标纠偏；</p>
<p>三、支持在线瓦片的离线下载</p>
<p>四、支持要素的采集和编辑，包含图形编辑和属性，支持节点的捕捉追踪、添加、删除、移动，文件图斑的合并、裁剪、挖孔等，支持redo、undo；</p>
<p>五、支持定位、轨迹和路径搜索，定位图标指示了当前所在位置，其中图标的箭头指示了手机的朝向，即地图是上北下南，定位箭头的方向便是手机的朝向；
轨迹功能记录了用户行走的路线；路径搜索，调用了天地图的在线接口，返回最优路线；</p>
<p>六、支持多媒体功能，拍照、录音、录像，以及照片文字的标注等；</p>
<p>用户可以参考UCMap OpenGL版开发文档，以及基于该源码做各种修改和扩展，欢迎加入QQ讨论群：543201967；</p>
<p></p>
<p><a target="_blank" rel="noopener noreferrer" href="https://github.com/geochenyj/SurveyOnUCMap/blob/master/img-folder/pic1.png"><img src="https://github.com/geochenyj/SurveyOnUCMap/raw/master/img-folder/pic1.png" alt="Image text" style="max-width:100%;"></a>
<a target="_blank" rel="noopener noreferrer" href="https://github.com/geochenyj/SurveyOnUCMap/blob/master/img-folder/pic2.png"><img src="https://github.com/geochenyj/SurveyOnUCMap/raw/master/img-folder/pic2.png" alt="Image text" style="max-width:100%;"></a>
<a target="_blank" rel="noopener noreferrer" href="https://github.com/geochenyj/SurveyOnUCMap/blob/master/img-folder/pic3.png"><img src="https://github.com/geochenyj/SurveyOnUCMap/raw/master/img-folder/pic3.png" alt="Image text" style="max-width:100%;"></a>
<a target="_blank" rel="noopener noreferrer" href="https://github.com/geochenyj/SurveyOnUCMap/blob/master/img-folder/pic4.png"><img src="https://github.com/geochenyj/SurveyOnUCMap/raw/master/img-folder/pic4.png" alt="Image text" style="max-width:100%;"></a>
<a target="_blank" rel="noopener noreferrer" href="https://github.com/geochenyj/SurveyOnUCMap/blob/master/img-folder/pic5.png"><img src="https://github.com/geochenyj/SurveyOnUCMap/raw/master/img-folder/pic5.png" alt="Image text" style="max-width:100%;"></a>
<a target="_blank" rel="noopener noreferrer" href="https://github.com/geochenyj/SurveyOnUCMap/blob/master/img-folder/pic6.png"><img src="https://github.com/geochenyj/SurveyOnUCMap/raw/master/img-folder/pic6.png" alt="Image text" style="max-width:100%;"></a></p>
