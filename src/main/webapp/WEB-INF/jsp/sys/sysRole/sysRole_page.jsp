<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-10">

            <a href="javascript:au()" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 添加角色</a>
            <div class="space-4"></div>
            <table class="table table-actived table-bordered table-striped">
                <thead>
                <tr>
                    <th width="200" class="hidden-xs hidden-sm">系统代码</th>
                    <th width="200">角色名称</th>
                    <th width="200" class="hidden-xs hidden-sm">备注</th>
                    <th  width="150" class="hidden-xs hidden-sm">设定级别</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${sysRoles}" var="sysRole" varStatus="st">
                    <tr>
                        <td class="hidden-xs hidden-sm">${sysRole.role }</td>
                        <td>${sysRole.description }</td>
                        <td class="hidden-xs hidden-sm">${sysRole.remark }</td>
                        <td class="hidden-xs hidden-sm">
                                ${sysRole.isSysHold?"仅允许系统自动设定":"可手动设定"}
                        </td>
                        <td>
                            <div class="buttons">
                                <button class="btn btn-warning btn-mini btn-xs" onclick="au(${sysRole.id})">
                                    <i class="fa fa-edit"></i>  更新权限
                                </button>
                                <button class="btn ${sysRole.isSysHold?'btn-success':'btn-primary'} btn-mini btn-xs" onclick="updateIsSysHold(${sysRole.id})">
                                    <i class="fa fa-key"></i>  ${sysRole.isSysHold?"修改为可手动设定":"仅允许系统自动设定"}
                                </button>
                                <a href="javascript:" onclick="del(${sysRole.id})" class="btn btn-danger btn-mini btn-xs">
                                    <i class="fa fa-trash"></i> 删除</a>
                            </div>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/sysRole_page" target="#page-content" pageNum="5"
                                 model="3"/>
                    </c:if>
        </div>
    </div>
</div>


<script>

    function au(id) {
        url = "${ctx}/sysRole_au";
        if (id > 0)
            url += "?id=" + id;
        loadModal(url);
    }

    function updateIsSysHold(id) {

        bootbox.confirm("确定修改该角色的系统控制权限吗？（如果系统自动维护，则不可以手动给某个账号指定该角色）", function (result) {
            if (result) {
                $.post("${ctx}/sysRole_updateIsSysHold", {id: id}, function (ret) {
                    if(ret.success) {
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }


    function del(id, type) {
        bootbox.confirm("确定删除该角色吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysRole_del", {id: id}, function (ret) {
                    if(ret.success) {
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _search() {

        _tunePage(1, "", "${ctx}/sysRole_page", "#page-content", "", "&" + $("#searchForm").serialize());
    }

    function _reset() {

        _tunePage(1, "", "${ctx}/sysRole_page", "#page-content", "", "");
    }

    function _reload() {
        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sysRole_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>
