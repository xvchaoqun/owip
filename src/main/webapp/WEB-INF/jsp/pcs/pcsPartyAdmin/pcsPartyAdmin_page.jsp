<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box" style="width: 800px">
            <div class="widget-header">
                <h4 class="smaller">
                    【${party.name}】-党代会管理员
                    <c:if test="${!hasNormalAdmin}">
                    <div class="pull-right"  style="margin-right: 10px">
                    <a class="popupBtn btn btn-success btn-xs" data-url="${ctx}/pcsPartyAdmin_add"><i class="fa fa-plus"></i> 添加管理员</a>
                    </div>
                    </c:if>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="qualification-content">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th width="100">姓名</th>
                            <th width="100">教工号</th>
                            <th>所在单位</th>
                            <th width="100">类别</th>
                            <th width="100"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pcsAdmins}" var="admin">
                            <tr>
                                <td>${admin.user.realname}</td>
                                <td>${admin.user.code}</td>
                                <td>${admin.unit}</td>
                                <td>${PCS_ADMIN_TYPE_MAP.get(admin.type)}</td>
                                <td>
                                    <c:if test="${admin.type==PCS_ADMIN_TYPE_NORMAL}">
                                    <button class="confirm btn btn-danger btn-xs"
                                            data-url="${ctx}/pcsPartyAdmin_del?id=${admin.id}"
                                            data-title="删除管理员"
                                            data-msg="确定删除该管理员？" data-callback="_reload"
                                            ><i class="fa fa-times"></i> 删除</button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
function _reload(){
    $.hashchange();
}
</script>