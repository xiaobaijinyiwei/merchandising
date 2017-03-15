package main.java.mongoUtils;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
/**
 * 数据库的连接
 * @author Mathartsys.xiaobai
 *
 */
public class MongoDBUtils {
	private  MongoOperations mongoOps;
	public MongoDBUtils() {
		try {
			this.mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "merchanDB"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  MongoOperations getMongoOps() {
		return mongoOps;
	}
}
