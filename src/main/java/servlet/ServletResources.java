package main.java.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import main.java.commodity.resources1.Cdata1;
import main.java.mongoUtils.MongoDao;
import main.java.mongoUtils.MongoDao1;

import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Component;
import com.mongodb.DBObject;


/**
 * 网页访问资源的定义
 * @author Mathartsys.xiaobai
 *
 */
@Component
@Path("/index")
public class ServletResources {
	
	/**
	 * 生成数据
	 * @return
	 */
	@Path("/load")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	
	public String loadCommodity() {
		//Cdata.loadData();
		Cdata1.loadData();
		return "Loaded successfully";
		
	}
	@Path("/find")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	
//	public BasicDBList findCommodity() {
	public Iterable<DBObject> findCommodity() {
//		MongoDao1 dao1 =new MongoDao1();
//		List<Commodity1> list = new ArrayList<Commodity1>();
//		
//		Calendar startDate = Calendar.getInstance();
//		startDate.set(2017, 2, 1, 0, 0, 0);
//		Calendar endDate = Calendar.getInstance();
//		endDate.set(2017, 2, 12, 0, 0, 0);
//		int cId=17;
//		list=dao1.findDate(startDate,endDate,cId);
//		return list;
		
		Calendar startDate = Calendar.getInstance();
		startDate.set(2017, 1, 1, 0, 0, 0);	
		Calendar endDate = Calendar.getInstance();
		endDate.set(2017, 2, 28, 0, 0, 0);	
		MongoDao1 dao1 =new MongoDao1();
		int dayOfYear1 = startDate.get(Calendar.DAY_OF_YEAR);
		int dayOfYear2 = endDate.get(Calendar.DAY_OF_YEAR);
		String cName = "键盘";
//		BasicDBList dbList = dao1.mongoGroupOnMonth(startDate,endDate);	
//		Calendar startDate = Calendar.getInstance();
//		startDate.set(2017, 2, 1, 0, 0, 0);	
//		Calendar endDate = Calendar.getInstance();
//		endDate.set(2017, 2, 12, 0, 0, 0);	
//		MongoDao1 dao1 =new MongoDao1();
//		List<Commodity1> list = new ArrayList<Commodity1>();
//		list=dao1.mongoGroupOnMonth1(startDate,endDate);
		
		Iterable<DBObject> list = dao1.mongoGroupOnMonth1(startDate,endDate);
		//Iterable<DBObject> list = dao1.mongoGroupSometime(startDate, endDate);
		
		
//		String date1 = new SimpleDateFormat("yyyyMMdd").format(startDate.getTime());
//		String date2 = new SimpleDateFormat("yyyyMMdd").format(endDate.getTime());
//		MongoDao dao =new MongoDao();
//		BasicDBList dbList1 = dao.mongoGroupOnMonth(date1,date2);
		return list;
		//return dbList;
		//return dbList1;	
		
//		MongoDao1 dao1 =new MongoDao1();
//		List<Commodity1> list = new ArrayList<Commodity1>();
//		list=dao1.findCommodityAll();
//		return list;
	}
	
	
	/**
	 * 从首页以post方式查看详情
	 */
	@Path("/march")
	@POST
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void findCommodityMarch(
			@FormParam("hdKeys") String cId,  //获取form提交的参数
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		
		//将cId转为int型
		double cID = Double.valueOf(cId);
		int id = (int)cID;
		
		
		//初始化显示盈利
		List<String> cutline1 =new ArrayList<String>();
		cutline1.add("盈利");

		//生成三月份的数据
		JspData jspData = new JspData(cutline1,id,3,31);
		String name = jspData.getName();
		String chartTitle="三月份"+name+"销售详情";
		String subtitle = jspData.getSubtitle();
		String xTitle =jspData.getxTitle();
		String yTitle =jspData.getyTitle();
		String[] cutline = jspData.getCutline();		
		String category[] = jspData.getCategory(); 
		double[][] data = jspData.getData();
		
		//将数据传入画图
		HttpSession session = request.getSession();
		DrawHistogram histogramPositive=new DrawHistogram(chartTitle, subtitle, xTitle, yTitle, cutline, category, data);
		String path = histogramPositive.draw(session,request.getContextPath());
		//将商品名称和商品id装入request
		request.setAttribute("name", name);
		request.setAttribute("cId", cId);
		//生成的图片路径
		request.setAttribute("Path", path);
		//跳转到三月的展示页面
		request.getRequestDispatcher("../../resources/jsp/March.jsp").forward(request, response);
	}
			
	
	
	
	@Path("/date")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//public Iterable<DBObject> findCommodityDate(
	//public Option findCommodityDate(
	public void findCommodityDate(
//			@FormParam("timeStart") String timeStart,  //获取form提交的参数
//			@FormParam("timeEnd") String timeEnd,  //获取form提交的参数
//			@FormParam("select1") String article,  //获取form提交的参数
//			@FormParam("select2") String pattern,  //获取form提交的参数
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		
		//获取开始时间和结束时间
		
		String timeStart = request.getParameter("timeStart");
		String timeEnd = request.getParameter("timeEnd");
		
		//获取商品的图样的选择
		String article=request.getParameter("select1");
		System.out.println(request.getParameter("select1"));
		String pattern=request.getParameter("select2");
		//String article=new String(request.getParameter("select1").getBytes("iso8859-1"),"utf-8");
		//String pattern=new String(request.getParameter("select2").getBytes("iso8859-1"),"GBK");
		//System.out.println(article);
		//System.out.println(pattern);
//		byte source [] = article.getBytes("iso8859-1");
//		String article1 = new String (source,"UTF-8");
//		System.out.println();
//		System.out.println(article);
//        System.out.println(article1);
		//System.out.println(article);
		
		//获取需要显示的数据，进价，售价，数量，盈利
		//String article=new String(request.getParameter("select1").getBytes("iso8859-1"),"GBK");
		String[] cutline=request.getParameterValues("data");
		//List<String> cutline1 =new ArrayList<String>();
		StringBuilder str = new StringBuilder();
		if(cutline==null)
		{
			//cutline1.add("盈利");
			str.append("盈利");
		}else {
			int i = 0;
			for (i = 0; i < cutline.length-1; i++) {
				//cutline1.add(cutline0[i]);
				//String cutline1=new String(cutline[i].getBytes("iso8859-1"),"utf-8");				
				//str.append(cutline1);
				str.append(cutline[i]);
				str.append(",");
			}
			//String cutline1=new String(cutline[i].getBytes("iso8859-1"),"utf-8");
			//str.append(cutline1);
			str.append(cutline[i]);
			
		}
		//System.out.println(str);
//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
//		Date dateStart =sdf.parse(timeStart);
//		Date dateEnd =sdf.parse(timeEnd);
		
//		Calendar calendarStart = Calendar.getInstance();
//		calendarStart.setTime(dateStart);
//		
//		Calendar calendarEnd = Calendar.getInstance();
//		calendarEnd.setTime(dateEnd);
//		

//		MongoDao1 dao1 =new MongoDao1();
//		Iterable<DBObject> list = dao1.mongoGroupOnMonth1(calendarStart,calendarEnd);
//		return list;

//		EChartsData eChartsData = new EChartsData();
//		Option option = eChartsData.selectRemoveCauses(calendarStart, calendarEnd);
//		return option;
		//Option option = eChartsData.selectRemoveCauses(calendarStart,calendarEnd); 
		
		if (pattern.equals("Figure1")) {
			pattern = "bar";
		}else if (pattern.equals("Figure2")) {
			pattern = "line";
		}else if (pattern.equals("Figure3")) {
			pattern = "pie";
		}else {
			pattern = "line";
		}
		//将处理好的数据重新装入request
		
		request.setAttribute("Str",str);
		request.setAttribute("Article",article);
		//request.setAttribute("Cutline", cutline1);
		request.setAttribute("Pattern", pattern);
		request.setAttribute("timeStart", timeStart);
		request.setAttribute("timeEnd", timeEnd);
		request.getRequestDispatcher("../../resources/jsp/datePage.jsp").forward(request, response);
	}
	
	
	@Path("/echarts")
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	

	public List<Map<String, String>> jqueryData(
			//@FormParam("timeStart") String timeStart,  //获取form提交的参数
			//@FormParam("timeEnd") String timeEnd,  //获取form提交的参数
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws ParseException, IOException{
			
		String timeStart = request.getParameter("timeStart");//开始时间
		String timeEnd = request.getParameter("timeEnd");//结束时间
		String article = request.getParameter("Article");//商品名称
		//String article=new String(request.getParameter("Article").getBytes("iso8859-1"),"utf-8");
		//String cutline = request.getParameter("Cutline");//查询数据
		//String pattern = request.getParameter("Pattern");//图形样式
		//设置时间格式
		//System.out.println(article);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart =sdf.parse(timeStart);
		Date dateEnd =sdf.parse(timeEnd);
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(dateStart);
		
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(dateEnd);	
	
		MongoDao1 dao1 =new MongoDao1();
		
		//设置返回前的数据
		List<Map<String, String>> rList = new ArrayList<Map<String,String>>();
		if (article.equals("all")) {//如果选择的是汇总选项
			Iterable<DBObject> list = null;
			list = dao1.mongoGroupOnMonth1(calendarStart,calendarEnd);
			for (DBObject dbObject:list) {  
		        //设置类目  
				Map<String, String> dbMap = (Map<String, String>) dbObject.get("_id");
				Map<String, String> map = new HashMap<String,String>();
				//将查询到的数据装入map
				map.put("cName", dbMap.get("cName"));
				map.put("sumtTurnover", dbObject.get("sumtTurnover").toString());
				map.put("sumpPrice", dbObject.get("sumpPrice").toString());
				map.put("sumsPrice", dbObject.get("sumsPrice").toString());
				map.put("count", dbObject.get("count").toString());
				rList.add(map);
			}
		}else {
			
			//如果开始时间不超过结束时间
			while (calendarStart.before(calendarEnd)) {
				//获取开始时间在一年之内的天数
				int dayOfYearStart = calendarStart.get(Calendar.DAY_OF_YEAR);
				String date = sdf.format(calendarStart.getTime());
				//日期加1
				calendarStart.add(Calendar.DATE, 1);
				//获取时间增加一天之后的天数
				int dayOfYearEnd = calendarStart.get(Calendar.DAY_OF_YEAR);
				Iterable<DBObject> list = null;
				//根据条件查找
				list = dao1.mongoGroupOnMonthOneDay(dayOfYearStart,dayOfYearEnd,article);
				DBObject dbObject = list.iterator().next();
				Map<String, String> dbMap = (Map<String, String>) dbObject.get("_id");
				Map<String, String> map = new HashMap<String,String>();
				
				//将查找到的数据存入map
				map.put("cName", dbMap.get("cName"));
				map.put("sumtTurnover", dbObject.get("sumtTurnover").toString());
				map.put("sumpPrice", dbObject.get("sumpPrice").toString());
				map.put("sumsPrice", dbObject.get("sumsPrice").toString());
				map.put("count", dbObject.get("count").toString());
				map.put("date", date);
				rList.add(map);				
			}
			
		}				
		return rList;
		
		
		//response.setContentType("text/html; charset=utf-8");
		//PrintWriter writer = response.getWriter();
//		List<Map<String, String>> listMaps = new ArrayList<Map<String,String>>();
//		for (DBObject dbObject:list) {  
//		     //设置类目  
//		     Map<String, String> map = (Map<String, String>) dbObject.get("_id");
//		     Map<String, String> map1 = new HashMap<String, String>();
//		     map1.put("cName", map.get("cName"));
//		     map1.put("sumtTurnover", dbObject.get("sumtTurnover").toString());
//		     listMaps.add(map1);
//		}
		//System.out.println(list);
		
//		System.out.println(option);
//		writer.println(option);
//		writer.flush();
//		//关闭输出流
//		writer.close();
	}
	
	
	
	@Path("/echartsall")	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@WebResult
	public Iterable<DBObject> jqueryDataall(
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws ParseException, IOException{
				
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart =sdf.parse("2017-1-1");
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(dateStart);
		
		Calendar calendarEnd = Calendar.getInstance();
		MongoDao1 dao1 =new MongoDao1();
		Iterable<DBObject> list = dao1.mongoGroupOnMonth1(calendarStart,calendarEnd);	
		return list;
	}
	
	
	
	
	
	
//	
//	@RequestMapping("/removecauses")  
//	public Viewable removecauses() throws Exception {  
// 
//	       Option option = EChartsData.selectRemoveCauses(null, null);  
//	       //request.set
//	}
	
	
	
	/**
	 * 一月份的数据生成，页面跳转
	 */
	@Path("/january")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable findCommodityJanuary( 
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		
		//获取由request传来的参数转换为int型
		String cId = request.getParameter("cId");	
		double cID = Double.valueOf(cId);
		int id = (int)cID;
		
		
		//获得用户选取的复选框的内容
		String[] cutline0=request.getParameterValues("data");
		List<String> cutline1 =new ArrayList<String>();
		if(cutline0==null)
		{
			cutline1.add("盈利");
		}else {
			for (int i = 0; i < cutline0.length; i++) {
				cutline1.add(cutline0[i]);
			}
		}
		
		//生成一月份的数据
		JspData jspData = new JspData(cutline1,id,1,31);
		String name = jspData.getName();
		String chartTitle="一月份"+name+"销售详情";
		String subtitle = jspData.getSubtitle();
		String xTitle =jspData.getxTitle();
		String yTitle =jspData.getyTitle();
		String[] cutline = jspData.getCutline();		
		String category[] = jspData.getCategory(); 
		double[][] data = jspData.getData();

		//将数据传入画图
		HttpSession session = request.getSession();
		DrawHistogram histogramPositive=new DrawHistogram(chartTitle, subtitle, xTitle, yTitle, cutline, category, data);
		String path = histogramPositive.draw(session,request.getContextPath());
		
		//将商品名称、商品id和生成图片的路径装入request
		request.setAttribute("Path", path);
		request.setAttribute("name", name);
		request.setAttribute("cId", cId);
		
		//跳转到一月的展示页面
		request.getRequestDispatcher("../../resources/jsp/January.jsp").forward(request, response);
		return null;
	}
	
	/**
	 * 二月份的数据生成，页面跳转
	 */
	@Path("/february")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable findCommodityFebruary(
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		
		//获取由request传来的参数转换为int型
		String cId = request.getParameter("cId");	
		double cID = Double.valueOf(cId);
		int id = (int)cID;
		
		//获得用户选取的复选框的内容
		String[] cutline0=request.getParameterValues("data");
		List<String> cutline1 =new ArrayList<String>();
		if(cutline0==null)
		{
			cutline1.add("盈利");
		}else {
			for (int i = 0; i < cutline0.length; i++) {
				cutline1.add(cutline0[i]);
			}
		}
		
		//生成二月份的数据
		JspData jspData = new JspData(cutline1,id,2,28);
		String name = jspData.getName();
		String chartTitle="二月份"+name+"销售详情";
		String subtitle = jspData.getSubtitle();
		String xTitle =jspData.getxTitle();
		String yTitle =jspData.getyTitle();
		String[] cutline = jspData.getCutline();		
		String category[] = jspData.getCategory(); 
		double[][] data = jspData.getData();

		
		//将数据传入画图
		HttpSession session = request.getSession();
		DrawHistogram histogramPositive=new DrawHistogram(chartTitle, subtitle, xTitle, yTitle, cutline, category, data);
		String path = histogramPositive.draw(session,request.getContextPath());
		
		//将商品名称、商品id和生成图片的路径装入request
		request.setAttribute("Path", path);
		request.setAttribute("name", name);
		request.setAttribute("cId", cId);
		
		//跳转到二月的展示页面
		request.getRequestDispatcher("../../resources/jsp/February.jsp").forward(request, response);
		return null;
	}
	
	/**
	 * 三月份的数据生成，页面跳转
	 */
	@Path("/march_GET")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable findCommodityMarch( 
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		//获取由request传来的参数转换为int型
		String cId = request.getParameter("cId");		
		double cID = Double.valueOf(cId);
		int id = (int)cID;
		
		//获得用户选取的复选框的内容
		String[] cutline0=request.getParameterValues("data");
		List<String> cutline1 =new ArrayList<String>();
		if(cutline0==null)
		{
			cutline1.add("盈利");
		}else {
			for (int i = 0; i < cutline0.length; i++) {
				cutline1.add(cutline0[i]);
			}
		}
		
		//生成三月份的数据
		JspData jspData = new JspData(cutline1,id,3,31);
		String name = jspData.getName();
		String chartTitle="三月份"+name+"销售详情";
		String subtitle = jspData.getSubtitle();
		String xTitle =jspData.getxTitle();
		String yTitle =jspData.getyTitle();
		String[] cutline = jspData.getCutline();		
		String category[] = jspData.getCategory(); 
		double[][] data = jspData.getData();

		
		//将数据传入画图
		HttpSession session = request.getSession();
		DrawHistogram histogramPositive=new DrawHistogram(chartTitle, subtitle, xTitle, yTitle, cutline, category, data);
		String path = histogramPositive.draw(session,request.getContextPath());
		
		//将商品名称、商品id和生成图片的路径装入request
		request.setAttribute("Path", path);
		request.setAttribute("name", name);
		request.setAttribute("cId", cId);
		
		//跳转到三月的展示页面
		request.getRequestDispatcher("../../resources/jsp/March.jsp").forward(request, response);	
		return null;
	}
	
	
	/**
	 * 一月所有商品的总销售数量展示
	 */
	
	@Path("/SumJanuary")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable findAllCommodityOnJanuary( 
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		//获取由request传来的参数
		String cId = request.getParameter("cId");		
		//获得用户选取的复选框的内容
		String[] cutline0=request.getParameterValues("data");
		List<String> cutline1 =new ArrayList<String>();
		if(cutline0==null)
		{
			cutline1.add("盈利");
		}else {
			for (int i = 0; i < cutline0.length; i++) {
				cutline1.add(cutline0[i]);
			}
		}	
		
		String startDate="20170101";
		String endDate ="20170131";
		SumData sumData = new SumData(cutline1, startDate, endDate,1);
		String[] cutline =sumData.getCutline();
		String chartTitle="一月份销售数据汇总";
		String subtitle = sumData.getSubtitle();	
		String category[] = sumData.getCategory();
		double[][] data =sumData.getData();
		String xTitle = sumData.getxTitle();
		String yTitle = sumData.getyTitle();
		
		//将数据传入画图
		HttpSession session = request.getSession();
		DrawHistogram histogramPositive=new DrawHistogram(chartTitle, subtitle, xTitle, yTitle, cutline, category, data);
		String path = histogramPositive.draw(session,request.getContextPath());
		
		//将商品名称、商品id和生成图片的路径装入request
		request.setAttribute("Path", path);
		request.setAttribute("cId", cId);
		//跳转到三月的展示页面
		request.getRequestDispatcher("../../resources/jsp/January.jsp").forward(request, response);	
		return null;
	}
	
	
	
	/**
	 * 二月所有商品的总销售数量展示
	 */
	
	@Path("/SumFebruary")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable findAllCommodityOnFebruary( 
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		//获取由request传来的参数
		String cId = request.getParameter("cId");		
		//获得用户选取的复选框的内容
		String[] cutline0=request.getParameterValues("data");
		List<String> cutline1 =new ArrayList<String>();
		if(cutline0==null)
		{
			cutline1.add("盈利");
		}else {
			for (int i = 0; i < cutline0.length; i++) {
				cutline1.add(cutline0[i]);
			}
		}	
		
		String startDate="20170201";
		String endDate ="20170228";
		SumData sumData = new SumData(cutline1, startDate, endDate,2);
		String[] cutline =sumData.getCutline();
		String chartTitle="二月份销售数据汇总";
		String subtitle = sumData.getSubtitle();	
		String category[] = sumData.getCategory();
		double[][] data =sumData.getData();
		String xTitle = sumData.getxTitle();
		String yTitle = sumData.getyTitle();
		
		//将数据传入画图
		HttpSession session = request.getSession();
		DrawHistogram histogramPositive=new DrawHistogram(chartTitle, subtitle, xTitle, yTitle, cutline, category, data);
		String path = histogramPositive.draw(session,request.getContextPath());
		
		//将商品名称、商品id和生成图片的路径装入request
		request.setAttribute("Path", path);
		request.setAttribute("cId", cId);
		//跳转到三月的展示页面
		request.getRequestDispatcher("../../resources/jsp/February.jsp").forward(request, response);	
		return null;
	}
	
	
	/**
	 * 三月所有商品的总销售数量展示
	 */
	
	@Path("/SumMarch")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable findAllCommodityOnMarch( 
			@Context HttpServletRequest request,  
			@Context HttpServletResponse response) throws IOException, ServletException, ParseException {
		//获取由request传来的参数
		String cId = request.getParameter("cId");		
		//获得用户选取的复选框的内容
		String[] cutline0=request.getParameterValues("data");
		List<String> cutline1 =new ArrayList<String>();
		if(cutline0==null)
		{
			cutline1.add("盈利");
		}else {
			for (int i = 0; i < cutline0.length; i++) {
				cutline1.add(cutline0[i]);
			}
		}	
		
		String startDate="20170301";
		String endDate ="20170331";
		SumData sumData = new SumData(cutline1, startDate, endDate,3);
		String[] cutline =sumData.getCutline();
		String chartTitle="三月份销售数据汇总";
		String subtitle = sumData.getSubtitle();	
		String category[] = sumData.getCategory();
		double[][] data =sumData.getData();
		String xTitle = sumData.getxTitle();
		String yTitle = sumData.getyTitle();
		
		//将数据传入画图
		HttpSession session = request.getSession();
		DrawHistogram histogramPositive=new DrawHistogram(chartTitle, subtitle, xTitle, yTitle, cutline, category, data);
		String path = histogramPositive.draw(session,request.getContextPath());
		
		//将商品名称、商品id和生成图片的路径装入request
		request.setAttribute("Path", path);
		request.setAttribute("cId", cId);
		//跳转到三月的展示页面
		request.getRequestDispatcher("../../resources/jsp/March.jsp").forward(request, response);	
		return null;
	}
	
	
		
		
		
		
		
		
	/**
	 *删除所有数据
	 */
	@Path("/delete")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_ATOM_XML})
	public String deleteCommodity() {
		MongoDao dao = new MongoDao();
		dao.removeCommodityAll();
		return "delete successfully";
	}
	
}
