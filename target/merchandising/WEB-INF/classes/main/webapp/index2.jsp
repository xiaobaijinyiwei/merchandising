<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="main.java.commodity.resources.WebInitialization"
		import = "java.util.*"
		import="java.text.DecimalFormat"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
		<title>Mathartsys</title>
		<link href="resources/css/all.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body style="background: #e1e9eb;">
	<%!
		WebInitialization web = new WebInitialization();
		List<Map<String,Object>> list = web.Initialization();
		
		
	 %>
			<div class="right">
				<div class="current">当前位置：<a href="javascript:void(0)" style="color:#6E6E6E;">销售展示</a> &gt; 销售汇总</div>
				<div class="rightCont">
					<p class="g_title fix">销售汇总</p>
					
					<div class="zixun fix">
						<table class="tab2" width="100%">
							<tbody>
								<tr>
								    <th>序号</th>
									<th>商品ID</th>
								    <th>商品名称</th>
								    <th>平均进价</th>
								    <th>平均售价</th>
								    <th>销售数量</th>
								    <th>商品盈利</th>
								    <th>销售详情</th>
								</tr>
								 <% for(int i=0;i<list.size();i++){ %>
								 <tr <% if((i%2)==1) {%> style="background-color:#ECF6EE;"<%}%>>
								 	<!-- 序号 -->
									<td><%=i+1%></td>	
									<!-- 商品ID -->				
									<td>
										<% 
											double cID = Double.valueOf(list.get(i).get("cId").toString());
											int cId = (int)cID;
										%>
										<%=cId %>
									</td>
									<!-- 商品名称 -->
									<td><%=list.get(i).get("cName") %></td>
									
									<!-- 平均进价 -->
									<td>
										<% 
											double pPrice = Double.valueOf(list.get(i).get("pPrice").toString());
											DecimalFormat dcmFmt = new DecimalFormat("0.00");
											double count = Double.valueOf(list.get(i).get("count").toString());
											double averagepPrice = pPrice/count;
											String sAveragepPrice = dcmFmt.format(averagepPrice);
										%>
										<%=sAveragepPrice %>
									</td>
									
									<!-- 平均售价 -->
									<td>
										<% double sPrice = Double.valueOf(list.get(i).get("sPrice").toString());
										   double averagesPrice = sPrice/count;
										   String sAveragesPrice = dcmFmt.format(averagesPrice);
										%>
										<%=sAveragesPrice %>
									</td>
									
									<!-- 销售数量-->
									<td>
										<% double sNumber = Double.valueOf(list.get(i).get("sNumber").toString());
											int number = (int)sNumber;%>
										<%=number %>
									</td>
									
									<!-- 商品盈利 -->
									<td>
										<%
										double tTurnover = Double.valueOf(list.get(i).get("tTurnover").toString());
										String Turnover = dcmFmt.format(tTurnover); 
										%>
										<%=Turnover %>
									</td>
									
									<!-- 销售详情 -->
									<td>
										<form action="web/index/march" id="" method="post">
											<div>
												<input type="hidden" id="hdKeys" name="hdKeys"  value="<%=cId%>"/>
												<input type="submit" value = "查看" class="btn03"/>
											</div>
										</form>
									</td>								 								 
								 </tr>								 	
								 <%} %>
							</tbody>
						</table>
					</div>
				</div>
			</div>
	</body>
	<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
</html>