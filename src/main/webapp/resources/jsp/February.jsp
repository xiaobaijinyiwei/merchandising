
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 

<%@page language="java" contentType="text/html; charset=utf-8" %>
 <%@ page import="main.java.servlet.DrawHistogram"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
	<title>February</title>
	<link href="../../resources/css/all.css" rel="stylesheet" type="text/css" />
	<script>
   		 	function january()
    		{
    			document.form1.action="january";
    			document.form1.submit();
    		}
    		function february()
    		{
    			document.form1.action="february";
    			document.form1.submit();
    		}
    		function sumfebruary()
    		{
    			document.form1.action="SumFebruary";
    			document.form1.submit();
    		}
    		function march_GET()
    		{
    			document.form1.action="march_GET";
    			document.form1.submit();
    		}
	</script>
</head>

<body style="background: #e1e9eb;">
	<div class="right">
		<div class="current">当前位置：<a href="javascript:void(0)" style="color:#6E6E6E;">销售展示</a> &gt; 二月份销售详情</div>
		<div class="rightCont">
			<form action="" method="get"  name="form1"> 
 				<p class="g_title">
					<input type="hidden" id="cId" name="cId"  value="${cId}"/>
					<input type="checkbox" id="checkbox1" name="data"  value="进价"/>进价
					<input type="checkbox" id="checkbox2" name="data"  value="售价"/>售价
					<input type="checkbox" id="checkbox3" name="data"  value="销售数量"/>销售数量
					<input type="checkbox" id="checkbox4" name="data"  value="盈利"/>盈利
					
					<td><a onclick="january()" class="btn03" href="#">一月&nbsp;&nbsp;</a></td>
					<td><a onclick="february()" class="btn04" href="#">二月&nbsp;&nbsp;</a></td>
					<td><a onclick="march_GET()" class="btn03" href="#">三月&nbsp;&nbsp;</a></td>
					<td><a onclick="sumfebruary()" class="btn03" href="#">汇总&nbsp;&nbsp;</a></td>
					<td><a class="btn03" href="../../index.jsp">首页  &nbsp;&nbsp;</a></td>		
					
				<!-- 
				<td><input type="sumbit" value="一月" onclick="modify1()" class="btn03"/></td>	
				<td><input type="sumbit" value="二月" onclick="modify1()" class="btn03"/></td>
				<td><input type="sumbit" value="三月" onclick="modify1()" class="btn03"/></td>
				<td><input type="sumbit" value="首页" onclick="modify1()" class="btn03"/></td>	
				-->	
			 	</p>
			 	
			 </form>
			<%  	
				DrawHistogram histogramPositive=new DrawHistogram();  
      		%>
      		<tr align="center">
        		<td>
					<td>
						<img src="${Path}" border="1" alt="" >
					</td>
        		</td>
     		 </tr>
		</div>
	</div>				

      
</body> 
</html>