package main.java.servlet;

import java.util.Calendar;
import java.util.Map;


import main.java.mongoUtils.MongoDao1;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.mongodb.DBObject;

public class EChartsData {
	
	public Option selectRemoveCauses(Calendar calendarStart,Calendar calendarEnd) {
		
		MongoDao1 dao1 =new MongoDao1();
		Iterable<DBObject> list = dao1.mongoGroupOnMonth1(calendarStart,calendarEnd);
		System.out.println(list);
		//创建Option  
	    Option option = new Option();  
	    option.title("剔除药品").tooltip(Trigger.axis).legend("金额（元）"); 
	    
	    //横轴为值轴  
	    option.xAxis(new ValueAxis().boundaryGap(0d, 0.01));  
	    //创建类目轴  
	    CategoryAxis category = new CategoryAxis();  
	    //柱状数据  
	    Bar bar = new Bar("金额（元）");  
	    //饼图数据  
	    Pie pie = new Pie("金额（元）");  
	    
	    //循环数据  
	    for (DBObject dbObject:list) {  
	        //设置类目  
	    	Map<String, String> map = (Map<String, String>) dbObject.get("_id");
	        category.data(map.get("cName"));  
	        //System.out.println(map.get("cName"));
	        //类目对应的柱状图  
	        bar.data(dbObject.get("sumtTurnover"));  
	        //饼图数据  
	        //pie.data(map.get("cName").toString(), dbObject.get("sumtTurnover")));  
	    }  
	    
	    //设置类目轴  
	    option.yAxis(category);  
	    //饼图的圆心和半径  
	    pie.center(900,380).radius(100);  
	    //设置数据  
	    option.series(bar, pie);  
	    //由于药品名字过长，图表距离左侧距离设置180，关于grid可以看ECharts的官方文档  
	    option.grid().x(180);  
	    //返回Option  
	    return option;  
		
	}

}
