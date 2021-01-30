<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>

	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>
	<style type="text/css">
	h1 {
		text-align: center;
		/*margin-top: 200px;*/
	}
</style>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.gif" >
			<span class="wel_word">订单详情</span>

		<%--静态包含，登录 成功之后的菜单 --%>
		<%@ include file="/pages/common/login_success_menu.jsp"%>
	</div>

<div id="main">
	<table  align="center"  width="80%" border="2" cellspacing="20" cellpadding="10">
		<tr >
			<td>序号</td>
			<td colspan="2">订单号</td>
			<td>名称</td>
			<td>数量</td>
			<td>单价</td>
			<td>总价</td>
		</tr>
		<c:set var="index" value="${(info.pageNum-1)*5}" />

		<c:forEach items="${info.list}" var="orderItem">
			<tr border="2" bgcolor="#4bfca2">
					<%--序号--%>
				<c:set var="index" value="${index+1}" />
				<td>${index}</td>
				<td colspan="2">${orderItem.orderId}</td>
				<td>${orderItem.name}</td>
				<td>${orderItem.count}</td>
				<td>${orderItem.price}</td>
				<td>${orderItem.totalprice}</td>
			</tr>
		</c:forEach>
	</table>
</div>

	<%--静态包含分页条--%>
	<%@include file="/pages/common/page_nav.jsp"%>

	<%--尚硅谷版权信息--%>
	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>