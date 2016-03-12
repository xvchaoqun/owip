<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-12">
            <table class="table tree table-bordered table-striped">
                <thead>
                <tr>
                    <th class="col-xs-3">名称</th>
                    <th class="col-xs-1">类型</th>
                    <th>排序</th>
                    <th>菜单样式</th>
                    <th>URL路径</th>
                    <th>权限字符串</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sysResources}" var="sysResource">
                    <tr class='treegrid-${sysResource.id}<c:if test="${sysResource.parentId!=0}"> treegrid-parent-${sysResource.parentId}</c:if>'>
                        <td>${sysResource.name}</td>
                        <td>${sysResource.type}</td>
                        <td>${sysResource.sortOrder}</td>
                        <td>
                            <i class="${sysResource.menuCss}"></i>
                        </td>
                        <td>${sysResource.url}</td>
                        <td>${sysResource.permission}</td>
                        <td nowrap>
                                <%--<shiro:hasPermission name="sysResource:create">--%>

                                <a href="javascript:;" onclick="_appendChild(${sysResource.id})"
                                   class="btn btn-success btn-mini btn-xs"><i class="fa fa-plus"></i> 添加子节点</a>


                            <a href="javascript:;" onclick="_update(${sysResource.id})"
                               class="btn btn-info btn-mini btn-xs"><i class="fa fa-pencil-square-o"></i> 修改</a>
                            <c:if test="${sysResource.parentId!=0}">
                                <a href="javascript:;" onclick="_del(${sysResource.id})" class="btn btn-danger btn-mini btn-xs">
                                    <i class="fa fa-times"></i> 删除</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<link rel="stylesheet" href="${ctx}/extend/css/jquery.treegrid.css">
<script type="text/javascript" src="${ctx}/extend/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/extend/js/jquery.treegrid.min.js"></script>
<script>

    $('.tree').treegrid({
        'initialState': 'collapsed',
        'saveState': true,
        expanderExpandedClass: 'fa fa-minus',
        expanderCollapsedClass: 'fa fa-plus'
    });
    $('.tree tbody tr:first').treegrid('expand');

    function _appendChild(parentId) {
        url = "${ctx}/sysResource_au?parentId=" + parentId;

        loadModal(url);

    }
    function _update(id) {

        url = "${ctx}/sysResource_au?id=" + id;

        loadModal(url);

    }

    function _del(id) {

        bootbox.confirm("确定删除该资源吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysResource_del", {id: id}, function (ret) {
                    if(ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _search() {

        _tunePage(1, "", "${ctx}/sysResource_page", "#page-content", "", "&" + $("#searchForm").serialize());
    }

    function _reset() {

        _tunePage(1, "", "${ctx}/sysResource_page", "#page-content", "", "");
    }

    function _reload() {

        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sysResource_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>
