<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 修改打卡时间 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <form:form id="admin-form" name="addForm" action="/ding/update_time" modelAttribute="time">
                    <div class="panel-body bg-light">
                        <div class="section-divider mt20 mb40">
                            <span> 上午上班时间 </span>
                        </div>
                        <div class="section">
                            <label for="morningTime" class="field prepend-icon">
                                <form:input path="morningTime" cssClass="gui-input" placeholder="${time.morningTime}"/>
                                <label for="morningTime" class="field-icon">
                                    <i class="fa fa-lock"></i>
                                </label>
                            </label>
                        </div>
                        <div class="section-divider mt20 mb40">
                            <span> 上午下班时间 </span>
                        </div>
                        <div class="section">
                            <label for="morningClosingTime" class="field prepend-icon">
                                <form:input path="morningClosingTime" cssClass="gui-input" placeholder="${time.morningClosingTime}"/>
                                <label for="morningClosingTime" class="field-icon">
                                    <i class="fa fa-lock"></i>
                                </label>
                            </label>
                        </div>
                        <div class="section-divider mt20 mb40">
                            <span> 下午上班时间 </span>
                        </div>
                        <div class="section">
                            <label for="afternoonTime" class="field prepend-icon">
                                <form:input path="afternoonTime" cssClass="gui-input" placeholder="${time.afternoonTime}"/>
                                <label for="afternoonTime" class="field-icon">
                                    <i class="fa fa-lock"></i>
                                </label>
                            </label>
                        </div>
                        <div class="section-divider mt20 mb40">
                            <span> 下午下班时间 </span>
                        </div>
                        <div class="section">
                            <label for="afternoonClosingTime" class="field prepend-icon">
                                <form:input path="afternoonClosingTime" cssClass="gui-input" placeholder="${time.afternoonClosingTime}"/>
                                <label for="afternoonClosingTime" class="field-icon">
                                    <i class="fa fa-lock"></i>
                                </label>
                            </label>
                        </div>
                        <div class="panel-footer text-right">
                            <button type="submit" class="button"> 保存 </button>
                            <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>


<jsp:include page="bottom.jsp"/>