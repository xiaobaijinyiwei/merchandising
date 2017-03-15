package main.java.commodity.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.mongoUtils.MongoDao;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class WebInitialization {
	public WebInitialization() {
		// TODO Auto-generated constructor stub
	}
	
	
	public List<Map<String,Object>> Initialization() {
		MongoDao dao = new MongoDao();
		BasicDBList dbList = dao.mongoGroup();
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		if (dbList!=null) {
			for (int i = 0; i < dbList.size(); i++) {
				DBObject obj = (DBObject) dbList.get(i);
//				Object cId = obj.get("cId");
//				Object cName = obj.get("cName");
//				Object sNumber = obj.get("sumsNumber");
//				Object tTurnover = obj.get("sumtTurnover");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cId", obj.get("cId"));
				map.put("cName", obj.get("cName"));
				map.put("sNumber", obj.get("sumsNumber"));
				map.put("pPrice", obj.get("sumpPrice"));
				map.put("sPrice", obj.get("sumsPrice"));
				map.put("count", obj.get("count"));
				map.put("tTurnover", obj.get("sumtTurnover"));
				list.add(map);
			}
		}	
		return list;
	}

}
