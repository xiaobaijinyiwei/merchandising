package main.java.commodity.resources1;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 实现按条件查询的输出类
 * @author Mathartsys.xiaobai
 */

@Document
public class Commod1 implements Serializable{
	
	private int cId;// 商品ID
	private String cName;// 商品名称
	private double pPrice;// 进价
	private double sPrice;// 售价
	private double tTurnover;// 盈利

	public Commod1() {
		// TODO Auto-generated constructor stub
	}

	public Commod1(int cId, String cName, int sNumber,
			double pPrice, double sPrice, double tTurnover) {
		super();
		this.cId = cId;
		this.cName = cName;
		this.pPrice = pPrice;
		this.sPrice = sPrice;
		this.tTurnover = tTurnover;
	}


	public int getcId() {
		return cId;
	}

	public String getcName() {
		return cName;
	}


	public double getpPrice() {
		return pPrice;
	}


	public double getsPrice() {
		return sPrice;
	}

	public double gettTurnover() {
		return tTurnover;
	}

	@Override
	public String toString() {
		return "Commodity [cId=" + cId + ", cName=" + cName + ", pPrice=" + pPrice
				+ ", sPrice=" + sPrice + ", tTurnover=" + tTurnover +"]";
	}

}
