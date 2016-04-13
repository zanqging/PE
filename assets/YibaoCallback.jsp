<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<script>

<%
    String resp = request.getParameter("resp");
    String sign = request.getParameter("sign");
    System.out.println(resp);
    System.out.println(sign);
   
    // 处理resp内容和sign签名...
%>
	respResult = '<%=resp%>';
	signResult = '<%=sign%>';
	
	function returnParameter(){
		window.fromJS.getResp(respResult,signResult);
	}
	returnParameter();
	// setTimeout("returnParameter()",2000);
	
</script>