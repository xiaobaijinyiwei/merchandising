package main.java.mongoUtils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import main.java.commodity.resources.Commod;
import main.java.commodity.resources.Commodity;
import main.java.commodity.resources.Constant;
import main.java.commodity.resources1.Commodity1;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
/**
 * 对数据库的操作定义
 * @author Mathartsys.xiaobai
 *
 */

public class MongoDao {
	MongoDBUtils dbUtils = new MongoDBUtils();
	MongoOperations operations = dbUtils.getMongoOps(); 
	
	
	//查询操作
	/**
	 * 根据给定id查找数据
	 * @param id
	 * @return
	 */
	public Commodity findCommodityById(int id) {
		Commodity commodity = operations.findOne(new Query(Criteria.where("_id").is(id)), Commodity.class, Constant.COLL_NAME);
		return commodity ;
	}
	/**
	 * 查找符合给定商品ID的第一个值
	 * @param id
	 * @return
	 */
	public Commodity findCommodityFirstByCId(int id) {
		Commodity commodity = operations.findOne(new Query(Criteria.where("cId").is(id)), Commodity.class, Constant.COLL_NAME);
		return commodity ;
	}
	/**
	 * 查找符合给定商品ID的全部值
	 * @param id
	 * @return
	 */
	public List<Commodity> findCommodityAllByCId(int id){
		List<Commodity> list = new ArrayList<Commodity>();
		list = operations.find(new Query(Criteria.where("cId").is(id)), Commodity.class, Constant.COLL_NAME);
		return list;
		}
	
	/**
	 * 查找符合给定日期的全部值
	 * @param date
	 * @return
	 */
	public List<Commodity> findCommodityAllByDate(String date){
		List<Commodity> list = new ArrayList<Commodity>();
		list = operations.find(new Query(Criteria.where("date").is(date)), Commodity.class, Constant.COLL_NAME);
		return list;
		}
	/**
	 * 查找数据库指定集合中的全部数据
	 * @return
	 */
	
	public List<Commodity> findCommodityAll(){
		
		return operations.findAll(Commodity.class, Constant.COLL_NAME);
		}
	
	/**
	 * 查找符合给定名称的值
	 * @param name
	 * @return
	 */

	public List<Commodity> findCommodityAllByName(String name) {
		List<Commodity> list = new ArrayList<Commodity>();
		list = operations.find(new Query(Criteria.where("cName").is(name)), Commodity.class, Constant.COLL_NAME);
		return list;
	}
	
	/**
	 * 根据给定的条件查询大于小于某个值的数据
	 * @param condition
	 * @param num
	 * @param name
	 * @return
	 */
	public List<Commodity> findCommodity(String condition,double num,String name) {
		List<Commodity> list = new ArrayList<Commodity>();
		Commodity commodity = new Commodity();
		switch (condition) {
		case "lt":
			list = operations.find(new Query(Criteria.where(name).lt(num)), Commodity.class, Constant.COLL_NAME);
			break;
		case "lte":
			list = operations.find(new Query(Criteria.where(name).lte(num)), Commodity.class, Constant.COLL_NAME);
			break;
		case "gt":
			list = operations.find(new Query(Criteria.where(name).gt(num)), Commodity.class, Constant.COLL_NAME);
			break;
		case "gte":
			list = operations.find(new Query(Criteria.where(name).gte(num)), Commodity.class, Constant.COLL_NAME);
			break;

		default:
			break;
		}
		
		return list;
		
	}
	
	/**
	 * 根据给定条件查询符合低于和高于指定值得列表
	 * @param condition
	 * @param number
	 * @return
	 */
	public List<Commodity> findCommodityBySNumber(String condition,int number) {
		List<Commodity> list = new ArrayList<Commodity>();
		Commodity commodity = new Commodity();
		switch (condition) {
		case "lt":
			list = operations.find(new Query(Criteria.where("sNumber").lt(number)), Commodity.class, Constant.COLL_NAME);
			break;
		case "lte":
			list = operations.find(new Query(Criteria.where("sNumber").lte(number)), Commodity.class, Constant.COLL_NAME);
			break;
		case "gt":
			list = operations.find(new Query(Criteria.where("sNumber").gt(number)), Commodity.class, Constant.COLL_NAME);
			break;
		case "gte":
			list = operations.find(new Query(Criteria.where("sNumber").gte(number)), Commodity.class, Constant.COLL_NAME);
			break;

		default:
			break;
		}
		
		return list;
		
	}
	
	/**
	 * 根据名称和日期联合查询
	 * @param name
	 * @param date
	 * @return
	 */
	public List<Commodity> findCommodityNameAndDate(String name,String date){
		List<Commodity> list = new ArrayList<Commodity>();
		list =operations.find(new Query(Criteria.where("cName").is(name).and("date").is(date)) ,Commodity.class, Constant.COLL_NAME);
		return list;
	}
	
	
	/**
	 * 根据给定日期统计
	 * @param name
	 * @param date
	 * @return
	 */
	public List<Commodity> findCommodityOnMonth(String name,String startDate,String endDate){
		List<Commodity> list = new ArrayList<Commodity>();
		list =operations.find(new Query(Criteria.where("cName").is(name).and("date").gte(startDate).lte(endDate)) ,Commodity.class, Constant.COLL_NAME);
		
		
		return list;
	}
	
	
	
	
	/**
	 * 根据商品Id和商品名称分组查询
	 * @return
	 */
	public BasicDBList mongoGroup() {
		GroupBy groupBy = GroupBy.key("cId","cName").initialDocument("{sumsNumber:0,sumpPrice:0,sumsPrice:0,"
				+ "sumtTurnover:0,count:0}")
				.reduceFunction("function(doc, out)"
				+ "{out.sumsNumber+= doc.sNumber,out.sumpPrice+=doc.pPrice,"
				+ "out.sumsPrice+=doc.sPrice,out.sumtTurnover+=doc.tTurnover,out.count++}")
				.finalizeFunction("function(out){return out;}");
//		GroupBy groupBy = GroupBy.key("cId").initialDocument("{count:0}")
//				.reduceFunction("function(doc, out){out.count++}")
//				.finalizeFunction("function(out){return out;}");

		GroupByResults<Commod> res = operations.group(Constant.COLL_NAME, groupBy, Commod.class);
	
		DBObject obj = res.getRawResults();
		
		BasicDBList dbList = (BasicDBList) obj.get("retval");

		return dbList;

		}
	
	
	/**
	 * 根据商品Id和商品名称分组查询
	 * @return
	 */
	public BasicDBList mongoGroupOnMonth(String startDate,String endDate) {
		GroupBy groupBy = GroupBy.key("cId","cName").initialDocument("{sumsNumber:0,sumpPrice:0,sumsPrice:0,"
				+ "sumtTurnover:0,count:0}")
				.reduceFunction("function(doc, out){"
				+ "if(doc.date>="+startDate+" && doc.date<="+endDate+"){"
				
				+ "out.sumsNumber+= doc.sNumber,out.sumpPrice+=doc.pPrice,"
				+ "out.sumsPrice+=doc.sPrice,out.sumtTurnover+=doc.tTurnover,out.count++"	
				+"}"
				+ "}").finalizeFunction("function(out){return out;}");
//		GroupBy groupBy = GroupBy.key("cId").initialDocument("{count:0}")
//				.reduceFunction("function(doc, out){out.count++}")
//				.finalizeFunction("function(out){return out;}");

		GroupByResults<Commod> res = operations.group(Constant.COLL_NAME, groupBy, Commod.class);
	
		DBObject obj = res.getRawResults();
		
		BasicDBList dbList = (BasicDBList) obj.get("retval");

		return dbList;

		}
	
	
	
	
	//插入操作
	/**
	 * 插入一个Commodity对象
	 * @param commodity
	 */
	public void insertCommodityOne(Commodity commodity) {
		operations.insert(commodity, Constant.COLL_NAME);
	}
	
	/**
	 * 插入给定列表中的全部对象
	 * @param list
	 */
	public void insertCommodityAll(List<Commodity> list) {
		for (int i = 0; i < list.size(); i++) {
			insertCommodityOne(list.get(i));
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	//删除操作
	/**
	 * 删除符合给定id的第一个值
	 * @param id
	 */
	public void removeCommodityById(int id) {
		Commodity commodity = findCommodityById(id);
		operations.remove(commodity, Constant.COLL_NAME);	
	}
	
	/**
	 * 删除符合给定商品ID的所有值
	 * @param id
	 */
	public void removeCommodityByCId(int id) {
		List<Commodity> list = new ArrayList<Commodity>();
		list = findCommodityAllByCId(id);
		for (int i = 0; i < list.size(); i++) {
			removeCommodityById(list.get(i).getId());
		}
	}
	
	
	/**
	 * 删除符合给定名称的所有值
	 * @param name
	 */
		public void removeCommodityByName(String name) {
			List<Commodity> list = new ArrayList<Commodity>();
			list = findCommodityAllByName(name);
			for (int i = 0; i < list.size(); i++) {
				removeCommodityById(list.get(i).getId());
			}
		}
		
	/**
	 * 删除符合给定名称的所有值
	 * @param date
	 */
		public void removeCommodityBy(String date) {
			List<Commodity> list = new ArrayList<Commodity>();
			list = findCommodityAllByName(date);
			for (int i = 0; i < list.size(); i++) {
				removeCommodityById(list.get(i).getId());
			}
		}
	
	
	/**
	 * 删除集合中的所有数据
	 */
	public void removeCommodityAll() {
		List<Commodity> list = findCommodityAll();
		for (int i = 0; i < list.size(); i++) {
			removeCommodityById(list.get(i).getId());
		}
	}
	
	
	
	
	
	
	
	
	
	//更新操作
	/**
	 * 根据所给id改变商品名称
	 * @param id
	 * @param name
	 */
	public void updataCommodityName(int id,String name) {
		operations.updateFirst(new Query(Criteria.where("cId").is(id)),new Update().set("cName", name), Commodity.class, Constant.COLL_NAME);
		
	}
	
	/**
	 * 根据所给id改变商品售出数量
	 * @param id
	 * @param number
	 */
	public void updataCommoditysNumber(int id,int number) {
		operations.updateFirst(new Query(Criteria.where("cId").is(id)),new Update().set("sNumber", number), Commodity.class, Constant.COLL_NAME);
		
	}
	/**
	 * 根据所给id改变商品进价
	 * @param id
	 * @param price
	 */
	public void updataCommoditypPrice(int id,double price) {
		operations.updateFirst(new Query(Criteria.where("cId").is(id)),new Update().set("pPrice", price), Commodity.class, Constant.COLL_NAME);
		
	}
	
	/**
	 * 根据所给id改变商品售价
	 * @param id
	 * @param price
	 */
	public void updataCommoditysPrice(int id,double price) {
		operations.updateFirst(new Query(Criteria.where("cId").is(id)),new Update().set("sPrice", price), Commodity.class, Constant.COLL_NAME);
	}
	
	/**
	 * 根据所给id改变商品的出售日期
	 * @param id
	 * @param date
	 */
	public void updataCommoditydate(int id,String date) {
		operations.updateFirst(new Query(Criteria.where("cId").is(id)),new Update().set("date", date), Commodity.class, Constant.COLL_NAME);
		
	}
	
	/**
	 * 根据所给id更新整个Commodity对象
	 * @param id
	 * @param commodity
	 */
	public void updataCommodityOne(int id,Commodity commodity){
		updataCommodityName(id, commodity.getcName());
		updataCommoditysNumber(id, commodity.getsNumber());
		updataCommoditypPrice(id, commodity.getpPrice());
		updataCommoditysPrice(id, commodity.getsPrice());
		updataCommoditydate(id, commodity.getDate());
	}
	
	/**
	 * 将列表中的所有Commodity对象更新
	 * @param list
	 */
	public void updataCommodityAll(List<Commodity> list){
		for (int i = 0; i < list.size(); i++) {
			updataCommodityOne(list.get(i).getcId(), list.get(i));
		}
	}
	

}
