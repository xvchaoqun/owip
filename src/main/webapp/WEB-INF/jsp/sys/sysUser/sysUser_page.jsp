<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="USER_SOURCE_ADMIN" value="<%=SystemConstants.USER_SOURCE_ADMIN%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-12">

            <div class="widget-box hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <mytag:sort-form css="form-horizontal " id="searchForm">
                            <div class="row">
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">账号</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="username" type="text" value="${param.username}"
                                                   placeholder="请输入账号">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">姓名</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="realname" type="text" value="${param.realname}"
                                                   placeholder="请输入姓名">
                                        </div>
                                    </div>

                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">类别</label>
                                        <div class="col-xs-6">
                                            <select name="type" data-placeholder="请选择" class="select2 tag-input-style">
                                                <option></option>
                                                <c:forEach items="${userTypeMap}" var="userType">
                                                    <option value="${userType.key}">${userType.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}');
                                            </script>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">角色</label>
                                        <div class="col-xs-6">
                                            <select name="roleId" data-placeholder="请选择" class="select2 tag-input-style">
                                                <option></option>
                                                <c:forEach items="${roleMap}" var="role">
                                                    <option value="${role.key}">${role.value.description}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=roleId]").val('${param.roleId}');
                                            </script>
                                             
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">状态</label>
                                        <div class="col-xs-6">
                                            <select name="locked" data-placeholder="请选择">
                                                <option></option>
                                                <option value="0">正常账号</option>
                                                <option value="1">禁用账号</option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=locked]").val('${param.locked}');
                                            </script>
                                             
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div class="clearfix form-actions center">
                                <a class="btn btn-sm" onclick="_search()"><i class="fa fa-search"></i> 查找</a>
                                <c:set var="_query" value="${not empty param.type ||not empty param.realname ||not empty param.username ||not empty param.roleId ||not empty param.typeId || not empty param.locked || not empty param.sort}"/>
                                <c:if test="${_query}">&nbsp; &nbsp; &nbsp;
                                    <button type="button" class=" btn btn-warning btn-sm" onclick="_reset()">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </mytag:sort-form>
                    </div>
                </div>
            </div>
            <div class="buttons pull-right">
                <a onclick="au()" class="btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加账号</a>
                <a class="btn btn-success btn-sm"><i class="fa fa-download"></i> 导出账号</a>
            </div>
            <h4>&nbsp;</h4>
            <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th class="center">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                    <mytag:sort-th field="username">账号</mytag:sort-th>
                    <mytag:sort-th field="code">学工号</mytag:sort-th>
                    <th>类别</th>
                    <th >姓名</th>
                    <th >性别</th>
                    <th >账号来源</th>
                    <th class="hidden-480">创建时间</th>
                    <th class="hidden-480"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sysUsers}" var="sysUser" varStatus="st">
                    <tr class="<c:if test="${sysUser.locked}">danger</c:if> ">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </td>
                        <td nowrap>
                            <a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId=${sysUser.id}">
                        ${sysUser.username}
                        </a>
                        </td>
                        <td nowrap>${sysUser.code}</td>
                        <td >${userTypeMap.get(sysUser.type)}</td>
                        <td >${sysUser.realname}</td>
                        <td >${GENDER_MAP.get(sysUser.gender)}</td>
                        <td >${userSourceMap.get(sysUser.source)}</td>

                        <td  class="hidden-480">${cm:formatDate(sysUser.createTime, "yyyy-MM-dd HH:mm")}</td>
                        <td  class="hidden-480">
                            <c:if test="${sysUser.source==USER_SOURCE_ADMIN}">
                            <button onclick="au(${sysUser.id})" class="btn btn-mini">
                                <i class="fa fa-edit"></i> 编辑
                            </button>
                            </c:if>
                            <button class="btn btn-warning btn-mini" onclick="updateUserRole(${sysUser.id})">
                                <i class="fa fa-pencil"></i> 修改角色
                            </button>
                            <c:if test="${sysUser.locked}">
                            <button onclick="_del('${sysUser.username}', 0)" class="btn btn-success btn-mini">
                                <i class="fa fa-edit"></i> 解禁
                            </button>
                            </c:if>
                            <c:if test="${!sysUser.locked}">
                                <button onclick="_del('${sysUser.username}', 1)" class="btn btn-danger btn-mini">
                                    <i class="fa fa-edit"></i> 禁用
                                </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                <wo:page commonList="${commonList}" uri="${ctx}/sysUser_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
        </div><div id="item-content"></div>
    </div>
</div>

<script>

    $(".table th.sortable").click(function(){

        var $this = $(this);
        var order = $this.hasClass("asc")?"desc":"asc";

        $("#searchForm input[name=sort]").val($this.data("field"));
        $("#searchForm input[name=order]").val(order);
        //alert($("div.myTableDiv #searchForm").serialize())
        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "&" + $("#searchForm").serialize());
    });

    $("#searchForm select").select2();
    function au(id) {

        url = "${ctx}/sysUser_au";
        if (id > 0)
            url += "?id=" + id;

        loadModal(url);
    }

    function _del(username, locked){

        bootbox.confirm("确定"+(locked==0?"解禁":"禁用")+"该账号吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysUser_del", {username: username, locked:locked}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function updateUserRole(id) {

        url = "${ctx}/sysUserRole?id=" + id;

        loadModal(url);
    }
    function _export() {

        location.href = "${ctx}/sysUser_export?" + $("searchForm").serialize();
    }
    function _search() {

        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "&" + $("#searchForm").serialize());
    }
    function _reset() {

        _tunePage(1, "", "${ctx}/sysUser_page", "#page-content", "", "");
    }
    function _reload() {
        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sysUser_page?${pageContext.request.queryString}");
    }
</script>