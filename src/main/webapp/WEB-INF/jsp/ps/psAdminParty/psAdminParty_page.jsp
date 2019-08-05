<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <div class="buttons pull-right" style="margin-right: 20px">
        <button onclick="metaType_au()" class="btn btn-info">
            <i class="fa fa-plus"></i> 添加
        </button>
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
                    <th nowrap>操作</th>
                    <th nowrap>备注</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${psAdminParties}" var="psAdminParty">
                    <tr>
                        <td nowrap>${partyMap.get(psAdminParty.partyId).name}</td>
                        <td nowrap>${cm:formatDate(psAdminParty.startDate, "yyyy.MM.dd")}</td>
                        <td>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <button onclick="metaType_au(${psAdminParty.id})" class="btn btn-primary btn-xs">
                                    <i class="fa fa-edit"></i> 修改
                                </button>
                                <button class="delBtn btn btn-danger btn-xs" data-id="${psAdminParty.id}">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                                <button onclick="metaType_au(${psAdminParty.id},true)" class="btn btn-danger btn-xs">
                                    <i class="fa fa-trash"></i> 结束管理
                                </button>
                            </div>
                        </td>
                        <td>${psAdminParty.remark}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
<script>
    function metaType_au(id,isHistory) {
        var url = "${ctx}/ps/psAdminParty_au?adminId=${param.adminId}";
        if (id > 0) url += "&id=" + id;
        if (isHistory) url += "&isHistory=" +isHistory;
        $.loadModal(url);
    }
</script>