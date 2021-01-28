<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
	<%-- 静态包含 base标签、css样式、jQuery文件 --%>
	<%@ include file="/pages/common/head.jsp"%>

	<%--<script type="text/javascript">
		$(function () {
			// 给删除的a标签绑定单击事件，用于删除的确认提示操作
			$("a.deleteClass").click(function () {
				// 在事件的function函数中，有一个this对象。这个this对象，是当前正在响应事件的dom对象。
				/**
				 * confirm是确认提示框函数
				 * 参数是它的提示内容
				 * 它有两个按钮，一个确认，一个是取消。
				 * 返回true表示点击了，确认，返回false表示点击取消。
				 */
				return confirm("你确定要删除【" + $(this).parent().parent().find("td:first").text() + "】?");
				// return false// 阻止元素的默认行为===不提交请求
			});
		});
	</script>--%>

</head>
<body>
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
		<%-- 静态包含 manager管理模块的菜单  --%>
		<%@include file="/pages/common/manage_menu.jsp"%>
	</div>
	<div id="main">
		<table>
			<tr>
				<td>名称</td>
				<td>价格</td>
				<td>作者</td>
				<td>销量</td>
				<td>库存</td>
				<td colspan="2">操作</td>
			</tr>

			<c:forEach items="${info.list}" var="book">
				<tr>
					<td>${book.name}</td>
					<td>${book.price}</td>
					<td>${book.author}</td>
					<td>${book.sales}</td>
					<td>${book.stock}</td>
					<td><a href="manage/bookServlet?id=${book.id}&pageNum=${info.pageNum}">修改</a></td>
					<td><a class="deleteItem" href="manage/bookServlet?id=${book.id}&pageNum=${info.pageNum}">删除</a></td>
				</tr>
			</c:forEach>
				<td><a href="pages/manage/book_edit.jsp">添加图书</a></td>
			</tr>
		</table>
		<%--删除--%>
		<form id = "deleteForm" action="manage/bookServlet?id=${book.id}&pageNum=${info.pageNum}" method="post">
			<input type="hidden" name="_method" value="DELETE">
		</form>

		<%--删除图书的 js --%>
		<script type="text/javascript">
			$(function () {
				$(".deleteItem").click(function () {
					if(confirm("是否要删除 "+$(this).parent().parent().find("td").eq(2).text()+" ?")){
						// 1.改变表单的指向
						$("#deleteForm").attr("action",this.href);
						// 2.提交表单
						$("#deleteForm").submit();
					}
					// 不return false的话就会继续执行链接的指向
					return false;
				});
			});
		</script>

		<%--静态包含分页条--%>
		<%@include file="/pages/common/page_nav.jsp"%>

	</div>
	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>