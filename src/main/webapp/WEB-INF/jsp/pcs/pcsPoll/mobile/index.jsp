<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${_p_pcsSiteName}</title>
    <jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
    <style>
        #candidateForm .table td, #candidateForm .table th {
            vertical-align: middle;
        }
        #candidateForm td.post-name {
            text-align: left !important;
            font-weight: bolder;
            font-size: larger;
            background-color: #d9edf7;
        }
        #candidateForm td.realname {
            text-align: center;
            font-weight: bolder;
        }

        #candidateForm tr.other .realname {
            font-weight: inherit;
        }
    </style>
</head>
<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="javascript:;" class="navbar-brand">
                <span style="font-size: 16px; font-weight: bold"><i class="ace-icon fa fa-signal"></i> ${_p_pcsSiteName}</span>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${ctx}/extend/img/default.png" width="90" alt="头像"/>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <c:if test="${tempResult.agree}">
                            <li style="margin-bottom: 5px">
                                <a href="${ctx}/user/pcs/index?isMobile=1&notice=1"><i
                                        class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
                            </li>
                        </c:if>
                        <li style="margin-bottom: 5px">
                            <a href="javascript:;" onclick="_logout()">
                                <i class="ace-icon fa fa-power-off"></i> 安全退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.navbar-container -->
</div>
<div class="main-container" id="main-container">
    <div class="main-content">
        <div class="main-content-inner">

            <div class="page-content" id="page-content">
                <c:if test="${param.notice==1 || !tempResult.agree}">
                    <form id="agreeForm" method="post">
                        <div class="modal-body" style="align: left;word-wrap:break-word">
                            ${cm:htmlUnescape(_1_m.content)}
                        </div>
                        <div class="span12"
                             style="margin-top: 10px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                            <center>
                                <c:if test="${param.notice==1}">
                                <a class="btn btn-primary btn-lg" id="enterBtn" href="${ctx}/user/pcs/index?isMobile=1"
                                        type="button"><i class="fa fa-hand-o-right"></i> 返回投票页面
                                </a>
                                </c:if>
                                <c:if test="${param.notice!=1}">
                                    <div style="margin-bottom: 15px">
                                <input type="checkbox" id="agree" name="agree"
                                       style="width: 17px; height: 17px;vertical-align: text-after-edge;">
                                我确认已阅读推荐说明
                                        </div>
                                <button class="btn btn-success btn-lg" id="enterBtn" onclick="_confirm(1)"
                                        type="button"><i class="fa fa-hand-o-right"></i> 进入投票页面
                                </button>
                                </c:if>
                            </center>
                        </div>
                    </form>
                </c:if>
                <c:if test="${param.notice!=1 && tempResult.agree}">
                    <div class="alert alert-block alert-success bolder" style="margin-bottom: 5px;">
                        ${pcsPoll.name}
                    </div>
                    <form id="candidateForm" method="post" action="${ctx}/user/pcs/submit">
                        <input type="hidden" name="flag" value="0">
                        <input type="hidden" name="isMobile" value="1">
                        <input type="hidden" name="isSubmit" value="0">
                        <input type="hidden" name="type" value="${type}">
                        <c:set var="userIds" value="${tempResult.firstResultMap.get(type)}"/>
                        <c:set var="_num" value="${fn:length(userIds)}"/>
                        <table class="table table-bordered">
                            <tbody>
                                <tr>
                                    <td><span class="star">*</span> 投票人身份</td>
                                </tr>
                                <tr>
                                    <td align="center">
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
                                    <td><span class="star">*</span> 推荐人类型</td>
                                </tr>
                                <tr>
                                    <td align="center">
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
                                        <td>推荐人${vs.index+1}</td>
                                    </tr>
                                    <tr>

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
                                        <td>推荐人${idx}</td>
                                    </tr>
                                    <tr>

                                        <td>
                                            <select data-rel="select2-ajax" data-width="272"
                                                    name="userIds" data-placeholder="请输入推荐人姓名或学工号">
                                            </select>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button id="saveBtn" class="btn btn-primary" type="button" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 保存中"
                                            onclick="_save(0)"><i class="fa fa-save"></i> 暂存
                                    </button>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button id="checkSubmitBtn" class="btn btn-success" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 保存中"
                                            type="button"
                                            onclick="_submit(4)"><i class="fa fa-check"></i> 提交
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>

<a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>

<div id="modal" class="modal fade">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/pcs/pcsPoll/pcs_poll_js.jsp"></jsp:include>
</body>
</html>
