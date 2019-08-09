<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <div class="buttons pull-right" style="margin-right: 20px">
        <shiro:hasPermission name="psAdmin:edit">
        <button onclick="_au()" class="btn btn-info">
            <i class="fa fa-plus"></i> 添加
        </button>
        </shiro:hasPermission>
    </div>
    <h3>管理的单位</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/ps/psAdminParty"
         data-url-del="${ctx}/ps/psAdminParty_del"
         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
        <c:if test="${true}">
            <table class="table table-actived table-center table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th nowrap>管理的单位</th>
                    <th nowrap>开始时间</th>
                    <th nowrap>结束时间</th>
                    <th nowrap>状态</th>
                    <shiro:hasPermission name="psAdmin:edit">
                    <th nowrap>操作</th>
                    </shiro:hasPermission>
                    <th nowrap>备注</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${psAdminParties}" var="record">
                    <tr>
                        <td class="align-left">${partyMap.get(record.partyId).name}</td>
                        <td nowrap>${cm:formatDate(record.startDate, "yyyy.MM.dd")}</td>
                        <td nowrap>${cm:formatDate(record.endDate, "yyyy.MM.dd")}</td>
                        <td>${record.isHistory?'已结束':'未结束'}</td>
                        <shiro:hasPermission name="psAdmin:edit">
                        <td>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <button onclick="_au(${record.id})" class="btn btn-primary btn-xs">
                                    <i class="fa fa-edit"></i> 修改
                                </button>
                                <button class="delBtn btn btn-danger btn-xs" data-id="${record.id}">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </div>
                        </td>
                        </shiro:hasPermission>
                        <td>${record.remark}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
<script>
    function _au(id) {
        var url = "${ctx}/ps/psAdminParty_au?adminId=${param.adminId}";
        if(id>0) url += "&id=" + id;
        $.loadModal(url);
    }
</script>