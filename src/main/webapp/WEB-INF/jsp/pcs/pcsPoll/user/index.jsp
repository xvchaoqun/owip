<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<c:set value="${_pMap['pcs_poll_site_name']}" var="_p_pcsSiteName"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>${_p_pcsSiteName}</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
    <t:link href="/css/dr.css"/>
</head>
<body style="background-color: inherit;">
<div id="navbar" class="navbar navbar-default navbar-fixed-top">
    <div class="container" id="navbar-container">
        <div class="navbar-header" style="width: 100%">
            <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                <t:img src="/img/logo_white.png"/></div>
            <div class="separator"></div>
            <div class="txt" style="cursor: pointer;">${_p_pcsSiteName}</div>

            <ul class="nav nav-pills pull-right">
                <c:if test="${not empty inspector}">
                    <li>
                        <a href="javascript:;"><i
                                class="ace-icon fa fa-user"></i> 当前账号：${inspector.username}</a>
                    </li>
                </c:if>
                 <c:if test="${tempResult.agree}">
                    <li>
                        <a href="${ctx}/user/pcs/index?notice=1"><i
                                class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
                    </li>
                 </c:if>
                <li>
                    <a href="javascript:;" onclick="_logout()"><i class="ace-icon fa fa-power-off"></i> 退出</a>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="container" style="padding-top: 100px">
    <div class="main-content eva">
        <c:if test="${param.notice==1 || !tempResult.agree}">
            <form id="agreeForm" method="post">
                <div style="width:70%; margin:0 auto;">
                    <div class="modal-body" style="text-align: left;word-wrap:break-word">
                            ${cm:htmlUnescape(_1_pc.content)}
                    </div>
                </div>

                <div class="span12" style="margin-top: 30px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                    <center>
                         <c:if test="${param.notice==1}">
                        <a class="btn btn-primary btn-lg" id="enterBtn" href="${ctx}/user/pcs/index"
                                type="button"><i class="fa fa-hand-o-right"></i> 返回投票页面
                        </a>
                        </c:if>
                        <c:if test="${param.notice!=1}">
                            <div style="margin-bottom: 15px">
                        <input type="checkbox" id="agree" name="agree"
                               style="width: 17px; height: 17px;vertical-align: text-after-edge;">
                        我确认已阅读推荐说明
                                </div>
                        <button class="btn btn-success btn-lg" id="enterBtn" onclick="_confirm(0)"
                                type="button"><i class="fa fa-hand-o-right"></i> 进入投票页面
                        </button>
                        </c:if>
                    </center>
                </div>
            </form>
        </c:if>
        <c:if test="${param.notice!=1 && tempResult.agree}">
            <div class="alert alert-block alert-success left bolder"
                 style="width:800px;margin: 0 auto 5px;font-size: larger;">
                ${pcsPoll.name}
            </div>
            <form id="candidateForm" method="post" action="${ctx}/user/pcs/submit">
                <input type="hidden" name="flag" value="0">
                <input type="hidden" name="isMobile" value="0">
                <input type="hidden" name="isSubmit" value="0">
                <input type="hidden" name="type" value="${type}"><%--提交的推荐人类型--%>
                <table class="table table-bordered table-unhover2" style="width:800px;margin: 0 auto;">
                <c:set var="userIds" value="${tempResult.firstResultMap.get(type)}"/>
                <c:set var="_num" value="${fn:length(userIds)}"/>
                    <tbody>
                        <tr>
                            <td align="right"><span class="star">*</span> 投票人身份</td>
                            <td>
                                <div class="input-group">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input type="radio" name="isPositive"
                                           id="isPositive_1" value="1" ${inspector.isPositive?"checked":""}>
                                    <label for="isPositive_1">正式党员</label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input type="radio" name="isPositive"
                                           id="isPositive_0" value="0" ${empty inspector.isPositive?"":(inspector.isPositive?"":"checked")}>
                                    <label for="isPositive_0">预备党员</label>
                                </div>
                                    </div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><span class="star">*</span> 推荐人类型</td>
                            <td align="left">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type"
                                           id="type_2" value="${PCS_USER_TYPE_DW}" ${type==PCS_USER_TYPE_DW?"checked":""}>
                                    <label for="type_2">党委委员</label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type"
                                           id="type_3" value="${PCS_USER_TYPE_JW}" ${type==PCS_USER_TYPE_JW?"checked":""}>
                                    <label for="type_3">纪委委员</label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type"
                                           id="type_1" value="${PCS_USER_TYPE_PR}" ${type==PCS_USER_TYPE_PR?"checked":""}>
                                    <label for="type_1">代表</label>
                                </div>
                            </td>
                        </tr>
                        <c:forEach items="${userIds}" var="userId" varStatus="vs">
                            <c:set var="candidate" value="${cm:getUserById(userId)}"/>
                            <tr>
                                <td align="right">推荐人${vs.index+1}</td>
                                <td>
                                    <select data-rel="select2-ajax" data-width="272"
                                            name="userIds" data-placeholder="请输入推荐人姓名或学工号">
                                        <option value="${candidate.id}">${candidate.realname}-${candidate.code}</option>
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:forEach begin="${_num+1}" end="${num}" var="idx">
                            <tr>
                                <td align="right">推荐人${idx}</td>
                                <td>
                                    <select data-rel="select2-ajax" data-width="272"
                                            name="userIds" data-placeholder="请输入推荐人姓名或学工号">
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <button id="saveBtn" class="btn btn-primary" type="button" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 保存中"
                                        onclick="_save(0)"><i class="fa fa-save"></i> 暂存
                                </button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button id="checkSubmitBtn" class="btn btn-success"  data-loading-text="<i class='fa fa-spinner fa-spin '></i> 保存中"
                                        type="button"
                                        onclick="_submit(4)"><i class="fa fa-check"></i> 提交
                                </button>
                            </td>
                        </tr>
                     </tbody>
                </table>
            </form>
        </c:if>
    </div>
</div>
<div id="modal" class="modal fade">
    <div class="modal-dialog" role="document" <%--style="min-width: 650px;"--%>>
        <div class="modal-content">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/scripts.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/pcs/pcsPoll/pcs_poll_js.jsp"></jsp:include>
</body>
</html>
