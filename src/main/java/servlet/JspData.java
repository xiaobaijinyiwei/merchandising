package main.java.servlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.java.commodity.resources.Commodity;
import main.java.mongoUtils.MongoDao;

public class JspData {
	
	private String[] cutline;// 图例名称
	private String category[]; // 统计种类
	private double[][] data;// 绘图数据 
	private String subtitle;//副标题
	private String xTitle;//x轴名称
	private String yTitle;//y轴名称
	private String Name;//查询的商品名称
	

	public JspData(){}
	
	public JspData(List<String> cutline,int id,int monthNumber,int dayNumber) throws ParseException{
		
		//连接数据库，根据传入的id查询数据
		MongoDao dao = new MongoDao();
		List<Commodity> list = new ArrayList<Commodity>();	
		list = dao.findCommodityAllByCId(id);
		
		//根据选择绘图的图例
		int datasize = cutline.size();
		String[] cut = new String[datasize];
		for (int i = 0; i < datasize; i++) {
			cut[i]=cutline.get(i);
		}
		
		String cat[] = new String[dayNumber]; 
		double[][] data1 = new double[datasize][dayNumber];// 绘图数据
		
		//设置时间格式
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");	
		
		for (int i = 0,j = 0; i < list.size(); i++) {
			//取出列表中的数据并获得日期
			Commodity commodity = list.get(i);
			String s = commodity.getDate();
			//将获得的日期转换为日期格式
			Date date =  formatter.parse(s);
			Calendar cal=Calendar.getInstance();  
			cal.setTime(date);
			
			if (cal.get(Calendar.MONTH)==(monthNumber-1)) {
				//获得查询月份中的每一天
				cat[j]=commodity.getDate().substring(6);
				for (int k = 0; k < datasize; k++) {
					switch (cut[k]) {
					case "进价":
						data1[k][j]=commodity.getpPrice();
						break;
					case "售价":
						data1[k][j]=commodity.getsPrice();
						break;
					case "销售数量":
						data1[k][j]=commodity.getsNumber();
						break;
					case "盈利":
						data1[k][j]=commodity.gettTurnover();
						break;

					default:
						break;
					}
				}
//				data1[0][j]=commodity.getsNumber();
//				data1[1][j]=commodity.getsPrice();
//				data1[2][j]=commodity.getpPrice();
				j++;
			}
		}
		this.Name = list.get(0).getcName();
		this.subtitle="                  统计时间：2017年"+monthNumber+"月";
		this.xTitle = "时间";
		this.yTitle = "销量";
		this.category=cat;
		this.data =data1;
		this.cutline =cut;
	}

	public String[] getCutline() {
		return cutline;
	}

	public String[] getCategory() {
		return category;
	}

	public double[][] getData() {
		return data;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getxTitle() {
		return xTitle;
	}

	public String getyTitle() {
		return yTitle;
	}
	public String getName() {
		return Name;
	}
}
