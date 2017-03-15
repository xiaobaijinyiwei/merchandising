<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="main.java.commodity.resources.WebInitialization"
		import = "java.util.*"
		import="com.github.abel533.echarts.Option"
		import="java.text.SimpleDateFormat"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
		<title>Mathartsys</title>
		<link href="resources/css/all.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		
		<script src="js/echarts.js"></script>
	    <script src="js/echarts.min.js"></script>
	    <script src="js/jquery-3.0.0.js"></script>
	    <script src="js/jquery-3.0.0.min.js"></script>
	</head>
	
	
	<body style="background: #e1e9eb;">
	<%
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	String dateEnd= format.format(new Date());
	Calendar dateStart1 = Calendar.getInstance();
	dateStart1.set(2017,0,1);
	Date date = dateStart1.getTime();
	String dateStart=format.format(date);
	%>
			<div class="right">
				<div class="current">当前位置：<a href="javascript:void(0)" style="color:#6E6E6E;">销售展示</a> &gt; 销售汇总</div>
				<div class="rightCont">				
					<form action="web/index/date" method="get"  name="form1"> 
 						<p class="g_title fix">销售汇总 	
 							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 					<a >商品：&nbsp;</a>
		 					<select name="select1" class="select">
		    					<option value="all" selected><a>汇总</a></option>
								<option value="风衣">风衣</option>	
								<option value="皮鞋">皮鞋</option>
								<option value="鼠标">鼠标</option>
								<option value="键盘">键盘</option>
								<option value="羽绒服">羽绒服</option>
								<option value="帆布鞋">帆布鞋</option>
								<option value="旅游鞋">旅游鞋</option>
								<option value="运动鞋">运动鞋</option>
								<option value="运动服">运动服</option>
								<option value="巧克力">巧克力</option>
								<option value="耳机">耳机</option>
								<option value="手表">手表</option>
								<option value="手链">手链</option>
								<option value="棉被">棉被</option>
								<option value="床垫">床垫</option>
								<option value="衣柜">衣柜</option>
								<option value="办公桌">办公桌</option>
								<option value="办公椅">办公椅</option>
								<option value="书包">书包</option>
								<option value="牛仔裤">牛仔裤</option>
		    				</select>
    				
		    				&nbsp;&nbsp;&nbsp;
		 					<a >图样：&nbsp;</a>
		    				<select name="select2" class="select">
		    					<option value="Figure1" >柱状图</option>
								<option value="Figure2">折线图</option>	
								<option value="Figure3"  selected>饼状图</option>
		    				</select>	
		    							
 							<input class = "btn05" type="submit"  value="查询"/>		
	 							<input id="edate" name="timeEnd" value="<%=dateEnd%>" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'sdate\')}',maxDate:'%y-%M-%d',startDate:'#F{$dp.$D(\'sdate\',{d:+1})}'})" />	
		 						<a class = "time">&nbsp;:&nbsp;</a>
		 						<input id="sdate" name="timeStart" value="<%=dateStart%>" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'edate\')}'})" />  
		 						<a class = "time"> 时间 :&nbsp;</a>  						
 							 				 						
			 			</p>			 	
			 		
			 	
			 		<div style="width:1305px;height:550px;">
			 			<div id="main1" style="width:100px;height:550px;float:left; border-right:2px solid #F00">
			 				<div style="height:50px;"><input type="checkbox" id="checkbox1" name="data"  value="进价"/><a>进价</a></div>
							<div style="height:50px;"><input type="checkbox" id="checkbox2" name="data"  value="售价"/><a>售价</a></div>
							<div style="height:50px;"><input type="checkbox" id="checkbox3" name="data"  value="数量"/><a>数量</a></div>
							<div style="height:50px;"><input type="checkbox" id="checkbox4" name="data"  value="盈利" checked="checked"/><a>盈利</a></div>
			 		
			 			</div>
			 			<div  id="main" style="width: 1200px;height:550px;float:left;"></div>
			 		</div>
			 	</form>		
			 	
			 	 	
			 	<script type="text/javascript">				 	
				 	var myChart = echarts.init(document.getElementById('main')); 
				 		    
			        function loadDrugs() {  
        				myChart.clear(); 
        				var arr=[];           				 				
        				var option = {
	           			series : [
							     {
							     name: '销售状况',
					             type: 'pie',
					             radius: '80%',
					             roseType: 'angle',
					             data:[]
							     }
							    ]
				        	};				        		
					             	  					             	
					         $.ajax({
		              				type : "get",
		               				async : false, //同步执行
		               				url : "web/index/echartsall", 
		               				data : {},
		               				dataType : "json", //返回数据形式为json
		               				success : function(result) { 			               										
           								if(result){
           									var selfme=null;
           									for(var i=0;i<result.length;i++){
                              	  				selfme={value:result[i].sumtTurnover,name:result[i]._id.cName} ;
                              	  				arr.push(selfme);                              	  
                              				}                                 	                               
           								} 
           								myChart.setOption({
					           				series : [
											     {
											     name: '销售状况',
									             type: 'pie',
									             radius: '80%',
									             roseType: 'angle',
									             data:arr
											     }
											  ]
								        });              					              						               			                 		               			              			
              						} ,
                					error : function(errorMsg) {  
                    					alert("不好意思，图表请求数据失败啦!");   
                					}                    						
        							});   
				
				        	// 为echarts对象加载数据 
				        		
				        	
				       		 
				  			       		
				       		} 
    				//载入图表  
   					loadDrugs(); 			        					
   	 			</script>
				</div>
			</div>
	</body>
</html>