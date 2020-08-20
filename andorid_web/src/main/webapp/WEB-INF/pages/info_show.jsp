<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 手机信息详情 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">手机型号</div>
                        <div class="col-md-6">${phoneInfo.model}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">系统版本</div>
                        <div class="col-md-4">${phoneInfo.version}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">是否root</div>
                        <div class="col-md-4">${phoneInfo.root}</div>
                    </div>
                    <%--<div class="panel-footer text-right">--%>
                        <%--<button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>
    </div>
</section>


<jsp:include page="bottom.jsp"/>
