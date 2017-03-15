package main.java.commodity.resources;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.java.mongoUtils.MongoDao;

/**
 * 生成数据并且插入数据库
 * @author Mathartsys.xiaobai
 *
 */
public class Cdata {
	public Cdata() {
		// TODO Auto-generated constructor stub
	}
	
public static void loadData(){
	int[] sNum = new int[Constant.Number];
	double[] pPrice = new double[Constant.Number];
	double[] sPrice = new double[Constant.Number];
	double[] tTurnover = new double[Constant.Number];
	String[] cName =  {"风衣","皮鞋","鼠标","键盘","羽绒服","帆布鞋","旅游鞋","运动鞋","运动服","巧克力",
						"耳机","手表","手链","棉被","床垫","衣柜","办公桌","办公椅","书包","牛仔裤"};
	
	GenerateDate dateGenerateDate = new GenerateDate();
	List<Calendar> dateList = dateGenerateDate.getDate();
	List<String> dataStrings = dateGenerateDate.dateToString(dateList);
	
	List<Integer> cId = Random.RcIdList();
	MongoDao dao = new MongoDao();
	List<Commodity> list = new ArrayList<Commodity>();
	
	for (int i = 0; i < dateList.size(); i++) {
		for (int j = 0; j < Constant.Number; j++) {
			sNum[j] = Random.rInt(Constant.SNUM);
			pPrice[j] = Random.rDouble(Constant.PPRICE);
			sPrice[j] = Random.rDouble(Constant.SPRICE)+pPrice[j];
			tTurnover[j] = (sPrice[j]-pPrice[j])*sNum[j];
			Commodity commodity = new Commodity(i*Constant.Number+j,cId.get(j), cName[j], 
						sNum[j], pPrice[j], sPrice[j], tTurnover[j],dataStrings.get(i));
			list.add(commodity);
		}
		
	}
	dao.insertCommodityAll(list);
}

}
