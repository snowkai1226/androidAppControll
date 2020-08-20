<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 信息展示 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 发生错误 </span>
                    </div>
                    <div class="section row" align="center">
                        <div class="col-md-6" style="color: firebrick">${msg}</div>
                    </div>
                    <div class="panel-footer text-right">
                        <a type="button" class="button" href="/phone/info" > 重试 </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<jsp:include page="bottom.jsp"/>
