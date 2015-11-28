<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>成员列表 <div class="buttons pull-right">
        <shiro:hasPermission name="unitCadreTransfer:edit">
            <a class="btn btn-info btn-sm" onclick="unitAdmin_au()"><i class="fa fa-plus"></i> 添加</a>
        </shiro:hasPermission>
    </div></h3>
</div>
<div class="modal-body">
            <c:if test="${fn:length(unitAdmins)>0}">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>所属班子</th>
							<th>关联干部</th>
							<th>是否管理员</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${unitAdmins}" var="unitAdmin" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${unitAdmin.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>${unitAdmin.groupId}</td>
								<td>${unitAdmin.cadreId}</td>
								<td>${unitAdmin.isAdmin}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unitAdmin:edit">
                                    <button data-id="${unitAdmin.id}" class="editBtn btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="unitAdmin:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${unitAdmin.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fn:length(unitAdmins)==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<script>

    function unitAdmin_au(id) {
        var url = "${ctx}/unitAdmin_au?groupId=${param.groupId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function unitAdmin_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/unitAdmin_del", {id: id}, function (ret) {
                    if (ret.success) {
                        unitAdmin_page("${param.groupId}");
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>
</div>