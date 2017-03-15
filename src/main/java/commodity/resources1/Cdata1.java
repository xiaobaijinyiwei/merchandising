package main.java.commodity.resources1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.java.commodity.resources.Constant;
import main.java.commodity.resources.Random;
import main.java.mongoUtils.MongoDao1;

public class Cdata1 {
	
	public Cdata1(){}
	
	public static void loadData(){
		
		//时间
//		GenerateTime timeSecon = new GenerateTime();
//		List<Calendar> timeCalendars = timeSecon.getDataTime();
		//商品名称
		String[] cName =  {"风衣","皮鞋","鼠标","键盘","羽绒服","帆布鞋","旅游鞋","运动鞋","运动服","巧克力",
							"耳机","手表","手链","棉被","床垫","衣柜","办公桌","办公椅","书包","牛仔裤"};
		//进价
		double[] pPrice = new double[Constant.Number];
		for (int i = 0; i < Constant.Number; i++) {
			//生成随机进价
			pPrice[i]=Random.rDouble(Constant.PPRICE);
		}
		//利润
		double tTurnover=0;
		//售价
		double sPrice = 0;
		List<Integer> cId = Random.RcIdList();//Id
		MongoDao1 dao = new MongoDao1();
		//List<Commodity1> list = new ArrayList<Commodity1>();
		
		
		
		//时间
		
		//时间格式
		SimpleDateFormat sdf= new SimpleDateFormat("yy/MM/dd HH:mm:ss ");  
		
		//开始时间
		Calendar calStart = Calendar.getInstance();
		calStart.set(2017, 0, 1, 0, 0, 0);
		//每天的开始
		Calendar dayStart = (Calendar) calStart.clone();
		////每天的结束
		Calendar dayEnd;
		
		//结束时间
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2017, 3, 1, 0, 0, 0);
		int i=0;
		int time=0;
		int dayOfYear=0;
		while (calStart.before(calEnd)) {	
			//时间变量的随机值
			time = Random.rInt(Constant.Time);
			//在开始时间的基础上增加一个随机值（单位：秒）
			calStart.add(Calendar.SECOND, time);
			//克隆此时的时间
			Calendar cal = (Calendar) calStart.clone();
			//每天的结束
			dayEnd = cal;	
			
			//让dayStart等于下一天的00：00：00
			dayStart.set(dayEnd.get(Calendar.YEAR),dayEnd.get(Calendar.MONTH),
					dayEnd.get(Calendar.DAY_OF_MONTH),0,0,0);
			//重新随机进价
			for (int j = 0; j < Constant.Number; j++) {
				pPrice[j]=Random.rDouble(Constant.PPRICE);
			}
			
			
			tTurnover=Random.rDouble(Constant.SPRICE);					
			int cIdSelect = Random.rInt(19);
			sPrice = tTurnover+pPrice[cIdSelect];
			
			Date date = cal.getTime();
			dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
			Commodity1 commodity1 = new Commodity1(i++, cId.get(cIdSelect), 
					cName[cIdSelect], pPrice[cIdSelect], sPrice, tTurnover, dayOfYear,date);
						
			dao.insertCommodityOne(commodity1);
			System.out.println(i);
		}
		//Calendar dayStart = timeCalendars.get(0);
		
		
		
		
//		for (int i = 0; i < timeCalendars.size(); i++) {
//			Calendar dayEnd = timeCalendars.get(i);			
//			if (dayStart.get(Calendar.DAY_OF_YEAR)!=dayEnd.get(Calendar.DAY_OF_YEAR)) {
//				
//				dayStart.set(dayEnd.get(Calendar.YEAR),dayEnd.get(Calendar.MONTH),
//						dayEnd.get(Calendar.DAY_OF_MONTH),0,0,0);
//				for (int j = 0; j < Constant.Number; j++) {
//					pPrice[j]=Random.rDouble(Constant.PPRICE);
//				}	
//			}
//			tTurnover=Random.rDouble(Constant.SPRICE);					
//			int cIdSelect = Random.rInt(19);
//			sPrice = tTurnover+pPrice[cIdSelect] ;
//			Commodity1 commodity1 = new Commodity1(i, cId.get(cIdSelect), 
//					cName[cIdSelect], pPrice[cIdSelect], sPrice, tTurnover, timeCalendars.get(i));
//			
//			list.add(commodity1);
//			
//			
//			//dao.insertCommodityOne(commodity1);
//		}
//		dao.insertCommodityAll(list);
	}


}
