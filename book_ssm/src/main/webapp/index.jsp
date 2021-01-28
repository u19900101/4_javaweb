<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 只负责请求转发--%>
<% System.out.println("访问了 index 页面");%>
<jsp:forward page="/client/bookServlet/page"></jsp:forward>