<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>任职列表 <div class="buttons pull-right">
        <shiro:hasPermission name="unitCadreTransfer:edit">
            <a class="btn btn-info btn-sm" onclick="unitCadreTransfer_au()"><i class="fa fa-plus"></i> 添加</a>
        </shiro:hasPermission>
    </div></h3>
</div>
<div class="modal-body">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>关联干部</th>
							<th>姓名</th>
							<th>任职日期</th>
							<th>免职日期</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${unitCadreTransfers}" var="unitCadreTransfer" varStatus="st">
                        <tr>
								<td nowrap>${cm:getUserById(cm:getCadreById(unitCadreTransfer.cadreId).userId).realname}</td>
								<td nowrap>${unitCadreTransfer.name}</td>
								<td nowrap>${cm:formatDate(unitCadreTransfer.appointTime,'yyyy-MM-dd')}</td>
								<td nowrap>${cm:formatDate(unitCadreTransfer.dismissTime,'yyyy-MM-dd')}</td>

                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unitCadreTransfer:edit">
                                    <button onclick="unitCadreTransfer_au(${unitCadreTransfer.id})" class="btn btn-default btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>

                                    <button onclick="unitCadreTransfer_addDispatchs(${unitCadreTransfer.id})" class="btn btn-xs btn-purple">
                                        <i class="fa fa-file"></i> 相关发文
                                    </button>
                                     <shiro:hasPermission name="unitCadreTransfer:del">
                                    <button class="btn btn-danger btn-xs" onclick="unitCadreTransfer_del(${unitCadreTransfer.id})">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<script>
    function unitCadreTransfer_au(id) {
        var url = "${ctx}/unitCadreTransfer_au?groupId=${param.groupId}";
        if (id > 0)  url += "&id=" + id;
        $.loadModal(url);
    }

    function unitCadreTransfer_del(id){

        SysMsg.confirm("确定删除该记录吗？", "操作确认", function () {
            $.post("${ctx}/unitCadreTransfer_del", {id: id}, function (ret) {
                if (ret.success) {
                    unitCadreTransfer_page("${param.groupId}");
                    //SysMsg.success('操作成功。', '成功');
                }
            });
        });
    }
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>