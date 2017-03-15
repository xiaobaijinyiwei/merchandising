package main.java.servlet;

import java.util.List;

import main.java.mongoUtils.MongoDao;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class SumData {
	private String[] cutline;// 图例名称
	private String category[]; // 统计种类
	private double[][] data;// 绘图数据 
	private String subtitle;//副标题
	private String xTitle;//x轴名称
	private String yTitle;//y轴名称
	

	public SumData() {
		// TODO Auto-generated constructor stub
	}
	
	public SumData(List<String> cutline1,String startDate,String endDate,int monthNumber) {
		//设置xy轴名称
		String xTitle1 ="商品种类";
		String yTitle1 ="销售成果";
		//查询结果
		MongoDao dao = new MongoDao();
		BasicDBList dbList = dao.mongoGroupOnMonth(startDate, endDate);
	
		//x轴数据
		String cat[] = new String[dbList.size()];
		//数据
		double[][] data1 = new double[cutline1.size()][dbList.size()];
		
		//图例
		String[] cut = new String[cutline1.size()];
		for (int i = 0; i < cutline1.size(); i++) {
			cut[i]=cutline1.get(i);
		}
		
		if (dbList!=null) {
			for (int i = 0; i < dbList.size(); i++) {
				//获取查询数据
				DBObject obj = (DBObject) dbList.get(i);
				//设置横坐标的值
				cat[i]=(String) obj.get("cName");
				//给data[][]写添数据
				for (int k = 0; k < cutline1.size(); k++) {
					switch (cut[k]) {
					case "进价":
						double pPrice = Double.valueOf(obj.get("sumpPrice").toString());
						double count = Double.valueOf(obj.get("count").toString());
						double averagepPrice = pPrice/count;
						data1[k][i]= averagepPrice;
						break;
					case "售价":
						double sPrice = Double.valueOf(obj.get("sumsPrice").toString());
						double count1 = Double.valueOf(obj.get("count").toString());
						double averagesPrice = sPrice/count1;
						data1[k][i]= averagesPrice;
						break;
					case "销售数量":
						double sNumber = Double.valueOf(obj.get("sumsNumber").toString());
						data1[k][i]= sNumber;
						break;
					case "盈利":
						double tTurnover = Double.valueOf(obj.get("sumtTurnover").toString());
						data1[k][i]= tTurnover;
						break;

					default:
						break;
					}
				}
			}
		}	
		
		this.subtitle="             "+ "统计时间：2017年"+monthNumber+"月";
		this.data =data1;
		this.category=cat;
		this.xTitle=xTitle1;
		this.yTitle=yTitle1;
		this.cutline=cut;
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

}
