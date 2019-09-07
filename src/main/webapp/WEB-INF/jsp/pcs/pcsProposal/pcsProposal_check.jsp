<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" style="width: 900px">
            <table class="table table-bordered table-unhover">
                <tr>
                    <td width="100">提案人姓名</td>
                    <td>${candidate.realname}</td>
                    <td width="100">工作单位</td>
                    <td>${candidate.unitName}</td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td>${candidate.mobile}</td>
                    <td>工作证号</td>
                    <td>${candidate.code}</td>
                </tr>
                <tr>
                    <td>邮 箱</td>
                    <td>${candidate.email}</td>
                    <td>提案日期</td>
                    <td>${cm:formatDate(empty pcsProposal?now:pcsProposal.createTime, "yyyy-MM-dd")}</td>
                </tr>
                <tr>
                    <td>标 题</td>
                    <td colspan="3">
                        ${pcsProposal.name}
                    </td>
                </tr>
                <tr>
                    <td>关 键 字</td>
                    <td colspan="3">
                        <div class="input-group">
                            ${pcsProposal.keywords}
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>提案类型</td>
                    <td colspan="3">
                        <div class="input-group">
                            <c:forEach items="${prTypes}" var="_type">
                                <label>
                                    <input disabled name="type" ${pcsProposal.type==_type.id?'checked':''}
                                           type="radio" class="ace" value="${_type.id}"/>
                                    <span class="lbl" style="padding-right: 5px;"> ${_type.name}</span>
                                </label>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 50px">提案内容及建议</td>
                    <td colspan="3">
                        <textarea disabled name="content" rows="10"
                                  style="width: 100%">${pcsProposal.content}</textarea>
                    </td>
                </tr>
                <c:if test="${_user.id== pcsProposal.userId || cm:isPermitted('pcsProposalOw:*')}">
                <tr>
                    <td>邀请附议人</td>
                    <td colspan="3">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>工作证号</th>
                                <th>代表姓名</th>
                                <th>所在单位</th>
                                <th>手机号</th>
                                <th>邮箱</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${inviteSeconders}" var="u">
                                <tr>
                                    <td>${u.code}</td>
                                    <td>${u.realname}</td>
                                    <td>${u.unitName}</td>
                                    <td>${u.mobile}</td>
                                    <td>${u.email}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <td>附议人</td>
                    <td colspan="3">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>工作证号</th>
                                <th>代表姓名</th>
                                <th>所在单位</th>
                                <th>手机号</th>
                                <th>邮箱</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${seconders}" var="u">
                                <tr>
                                    <td>${u.code}</td>
                                    <td>${u.realname}</td>
                                    <td>${u.unitName}</td>
                                    <td>${u.mobile}</td>
                                    <td>${u.email}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>附件</td>
                    <td colspan="3">
                        <c:set var="np" value="false"/>
                        <c:set var="nd" value="false"/>
                        <shiro:lacksPermission name="pcsProposalOw:*">
                            <c:set var="np" value="true"/>
                            <c:set var="nd" value="true"/>
                        </shiro:lacksPermission>
                        <c:forEach var="file" items="${pcsProposal.files}">
                            <div class="file">
                                <t:preview filePath="${file.filePath}" fileName="${file.fileName}" np="${np}" nd="${nd}"
                                           label="${cm:substr(file.fileName, 0, 25, '...')}"/>
                            </div>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <c:if test="${param.type==1}">
                        <div class="modal-footer center">
                            <button type="button" data-id="${pcsProposal.id}" data-status="0"
                                    class="checkBtn btn btn-danger btn-lg"><i
                                    class="fa fa-times"></i> 审核不通过
                            </button>

                            <button type="button" data-id="${pcsProposal.id}" data-status="1"
                                    class="checkBtn btn btn-success btn-lg"><i
                                    class="fa fa-check"></i> 审核通过
                            </button>
                        </div>
                        </c:if>
                        <c:if test="${param.type==2 && _user.id!=pcsProposal.userId}">
                        <div class="modal-footer center">
                            <button type="button" class="hideView btn btn-default btn-lg"><i
                                    class="fa fa-reply"></i> 返回
                            </button>

                            <button type="button" data-id="${pcsProposal.id}" data-status="1"
                                    class="seconderBtn btn btn-success btn-lg"><i
                                    class="fa fa-check"></i> 确定附议
                            </button>
                        </div>
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<style>
    .table.table-unhover > tbody > tr > td:nth-of-type(odd) {
        text-align: center;
        font-size: 18px;
        font-weight: bolder;
        vertical-align: middle;
    }

    textarea {
        text-indent: 32px;
        line-height: 25px;
        /*font-family: "Arial";*/
        font-size: 16px;
        padding: 2px;
        margin: 2px;
        border: none;
        background: #FFFFFF url(/img/dot_25.gif);
        resize: none;
    }

    .files {
        width: 300px;
        float: left;
        margin-top: 20px;
    }

    .file {
        height: 25px;
        line-height: 25px;
        background: #FFFFFF url(/img/dot_25.gif);
        width: 350px;
        /*border-bottom: dotted 1px;*/
    }

    .file:hover {
        background-color: #eadda9;
    }
</style>
<script>

    $(".checkBtn").click(function () {
        var id = $(this).data("id");
        var status = $(this).data("status");
        SysMsg.confirm(status == 0 ? "确认审核不通过？" : "确认审核通过？", "审核", function () {
            $.post("${ctx}/pcsProposal_check", {id: id, status: status}, function () {
                $.hideView();
            });
        })
    });

    $(".seconderBtn").click(function(){
        var id = $(this).data("id");
        $.post("${ctx}/pcsProposal_seconder", {id: id}, function () {
            $.hideView();
        });
    });

    $.register.fancybox();
</script>