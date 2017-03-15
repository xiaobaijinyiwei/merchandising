package main.java.commodity.resources;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * 生成各种随机数
 * @author Mathartsys.xiaobai
 *
 */
public class Random {

	
	public Random() {
	// TODO Auto-generated constructor stub
	}
	
	/**
	 * 生成double类型的随机数
	 * @param number
	 * @return
	 */
	public static double rDouble(int number){
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		double d = Math.random()*number;
		String d1 = dcmFmt.format(d);
		return 	Double.valueOf(d1);
	}
	
	/**
	 * 生成int类型的随机数
	 * @param number
	 * @return
	 */
	public static int rInt(int number) {
		return (int) Math.round(Math.random()*number);
	}
	
	/**
	 * 生成int类型的不重复的随机数
	 * @return
	 */
	public static List<Integer> RcIdList() {
		List<Integer> cId = new ArrayList<Integer>();
		while (cId.size()<Constant.Number) {
			int id = rInt(Constant.CID);
			if (!cId.contains(id)) {
				cId.add(id);
			}	
		}
		return cId;
		
	}
	

}
