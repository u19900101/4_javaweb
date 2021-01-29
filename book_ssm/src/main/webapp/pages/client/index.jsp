<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: liupannnnnnnnnn
  Date: 2021/1/6
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>书城首页</title>
<%-- 静态包含 base标签、css样式、jQuery文件 --%>
<%@ include file="/pages/common/head.jsp"%>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" >
    <span class="wel_word">网上书城</span>
    <div>
        <%-- 用户未登录 --%>
        <c:if test="${empty sessionScope.user}">
            <a href="pages/user/login.jsp">登录</a> |
            <a href="pages/user/register.jsp">注册</a> &nbsp;&nbsp;
        </c:if>
            <a href="client/orderServlet/page">我的订单</a>
            <a href="pages/cart/cart.jsp">购物车</a>

        <%-- 用户已登录 --%>
            <c:if test="${not empty sessionScope.user}">
                <a href="index.jsp">返回</a>
                <a href="client/userServlet/logout">注销</a>&nbsp;&nbsp;
                <span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
            <a href="pages/manage/manage.jsp">后台管理</a>
        </c:if>

    </div>
</div>

<div id="main">
    <div id="book">
        <div class="book_cond">
            <form action="client/bookServlet/queryByPrice" method="post">
                价格：
                <input id="min" type="text" name="min" value="${min}"> 元 -
                <input id="max" type="text" name="max" value="${max}"> 元
                <input type="submit" value="查询" id = "searchPriceBtn"/>
                <c:if test="${not empty min or not empty max}">
                    <a href="client/bookServlet/page">清空查询条件</a>
                </c:if>

            </form>

        </div>
        <script type="text/javascript">
            /* 处理刷新后信息消失的情况 */
            $(function () {
                // 跳到指定的页码
                $("#searchPriceBtn").click(function () {
                    var min = $("#min").val();
                    var max = $("#max").val();
                    // alert(min);
                    // alert(max);
                    if(""==min){
                        min = 0;
                    }
                    if(""==max){
                        max = 1000000000;
                    }
                    if($.isNumeric(min)&&$.isNumeric(max)){
                        min = Number(min);
                        max = Number(max);
                        if(min>max){
                            var a = min;
                            min = max;
                            max = a;
                            $("#min").val(min);
                            $("#max").val(max);
                        }
                    }
                    else {
                        alert("数字输入错误,请从新输入");
                        if($.isNumeric(min)){
                            $("#min").val(Number(min));
                        }
                        if($.isNumeric(max)){
                            $("#max").val(Number(max));
                        }
                    }
                });

                // 加入购物车
                $("button.cartBtn").click(function () {
                    if(${not empty sessionScope.user}){
                        var bookId = $(this).attr("bookId");
                        // 使用ajax修改
                        $.post(
                            "http://localhost:8080/book_ssm/client/cartServlet",
                            "id="+bookId,
                            function (data) {
                                $("#totalCount").text(data.totalCount);
                                $("#lastAddBook").text(data.lastAddBook);
                                // 首次添加商品则进行刷新操作
                                if(data.createCart){
                                        location.href = "client/bookServlet/page";
                                    }
                                },
                            "json"
                        );
                        /*$.getJSON("client/cartServlet","action=addItem&id="+bookId,function (data) {
                            $("#totalCount").text(data.totalCount);
                            $("#lastAddBook").text(data.lastAddBook);
                            // 首次添加商品则进行刷新操作
                            if(data.createCart){
                                location.href = "client/bookServlet?action=page";
                            }
                        });*/
                    }else {
                        location.href = "pages/user/login.jsp";
                    }
                    /**
                     * 在事件响应的function函数 中，有一个this对象，这个this对象，是当前正在响应事件的dom对象
                     * @type {jQuery}
                     */
                    // alert(bookId);
                    // location.href = "client/cartServlet?action=addItem&id="+bookId;
                        /*此处不能使用属性进行取值，因为循环后相同属性的很多*/
                        // location.href = "client/cartServlet?action=addItem"+$("#cartBtn").val();

                });
            });
        </script>
        <div style="text-align: center">
            <c:if test="${empty sessionScope.cart}">
                <div>
                    <span  style="color: red">当前购物车为空</span>
                </div>
            </c:if>

            <c:if test="${not empty sessionScope.cart}">

                <div>
                    您的购物车中有<span style="color: red" id="totalCount"><%--防止刷新时数据为空--%>${sessionScope.totalCount}</span>件商品
                </div>
                <div>
                    您刚刚将<span style="color: red" id="lastAddBook">${sessionScope.lastAddBook}</span>加入到了购物车中
                </div>
            </c:if>
        </div>

        <%-- 遍历图书 --%>
        <c:forEach items="${info.list}" var="book">
            <div class="b_list">
                <div class="img_div">
                    <img class="book_img" alt="" src="static/img/default.jpg" />
                </div>
                <div class="book_info">
                    <div class="book_name">
                        <span class="sp1">书名:</span>
                        <span class="sp2">${book.name}</span>
                    </div>
                    <div class="book_author">
                        <span class="sp1">作者:</span>
                        <span class="sp2">${book.author}</span>
                    </div>
                    <div class="book_price">
                        <span class="sp1">价格:</span>
                        <span class="sp2">${book.price}</span>
                    </div>
                    <div class="book_sales">
                        <span class="sp1">销量:</span>
                        <span class="sp2">${book.sales}</span>
                    </div>
                    <div class="book_amount">
                        <span class="sp1">库存:</span>
                        <span class="book_amount">${book.stock}</span>
                    </div>
                    <div class="book_add">
                        <button class="cartBtn" bookId="${book.id}" >加入购物车</button>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>
    <%@include file="/pages/common/page_nav.jsp"%>

</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>

</body>
</html>
