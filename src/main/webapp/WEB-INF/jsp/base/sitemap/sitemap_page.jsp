<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row" style="padding-bottom: 20px;">
    <div class="col-xs-12">

        <div class="col-sm-12">
            <a href="javascript:;" onclick="_appendChild('')"
               class="btn btn-success btn-xs"><i class="fa fa-plus"></i> 添加</a>
            <div class="space-4"></div>
            <table class="table table-actived tree table-bordered table-striped">
                <thead>
                <tr>
                    <th class="col-xs-3">标题</th>
                    <th>排序</th>
                    <th>URL路径</th>
                    <th>关联资源</th>
                    <th>备注</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sitemaps}" var="sitemap">
                    <tr class='treegrid-${sitemap.id}<c:if test="${sitemap.fid!=0}"> treegrid-parent-${sitemap.fid}</c:if>'>
                        <td>${sitemap.title}</td>
                        <td>${sitemap.sortOrder}</td>
                        <td>${sitemap.url}</td>
                        <td>
                            <c:forEach items="${cm:getSysResourcePath(sitemap.resourceId, false)}" var="sysResource" varStatus="vs">
                                ${sysResource.name}
                                -
                                ${vs.last?sysResource.permission:""}
                            </c:forEach>
                        </td>

                        <td>${sitemap.remark}</td>
                        <td nowrap>
                                <%--<shiro:hasPermission name="sitemap:create">--%>
                                    <c:if test="${empty sitemap.fid}">
                                <a href="javascript:;" onclick="_appendChild(${sitemap.id})"
                                   class="btn btn-success btn-xs"><i class="fa fa-plus"></i> 添加子节点</a>
                                </c:if>
                            <a href="javascript:;" onclick="_update(${sitemap.id})"
                               class="btn btn-info btn-xs"><i class="fa fa-pencil-square-o"></i> 修改</a>

                            <c:if test="${sitemap.fid!=0}">
                                <a href="javascript:;" onclick="_del(${sitemap.id})" class="btn btn-danger btn-xs">
                                    <i class="fa fa-trash"></i> 删除</a>
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
    <c:if test="${fn:length(sitemaps)>0}">
    $('.tree').treegrid({
        'initialState': 'collapsed',
        'saveState': true,
        expanderExpandedClass: 'fa fa-minus',
        expanderCollapsedClass: 'fa fa-plus'
    });
    $('.tree tbody tr:first').treegrid('expand');
    </c:if>

    function _appendChild(fid) {
        url = "${ctx}/sitemap_au?fid=" + fid;

        $.loadModal(url);

    }
    function _update(id) {

        url = "${ctx}/sitemap_au?id=" + id;

        $.loadModal(url);

    }

    function _del(id) {

        SysMsg.confirm("确定删除该资源吗？", "操作确认", function () {
            $.post("${ctx}/sitemap_del", {id: id}, function (ret) {
                if(ret.success) {
                    _reload();
                    //SysMsg.success('操作成功。', '成功');
                }
            });
        });
    }

    function _search() {

        _tunePage(1, "", "${ctx}/sitemap", "#page-content", "", "&" + $("#searchForm").serialize());
    }

    function _reset() {

        _tunePage(1, "", "${ctx}/sitemap", "#page-content", "", "");
    }

    function _reload() {

        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sitemap?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>
