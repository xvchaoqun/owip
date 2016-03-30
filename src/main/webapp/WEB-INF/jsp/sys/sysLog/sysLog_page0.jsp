<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="col-sm-12">
            <div class="buttons">
                <form class="form-inline" id="searchForm">
                    <select name="typeId" data-placeholder="请选择类别">
                        <option></option>
                        <c:forEach var="metaType" items="${metaTypeMap}">
                            <option value="${metaType.value.id}">${metaType.value.name}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#searchForm select[name=typeId]").val('${param.typeId}');
                        $("#searchForm select[name=typeId]").select2();
                    </script>
                    <input class="form-control search-query search-input"
                           name="content" type="text" value="${param.content}" placeholder="请输入日志内容">
                    <button type="button" class="btn btn-default btn-sm" onclick="_search()">
                        <i class="fa fa-search"></i> 查找
                    </button>
                    <c:if test="${not empty param.typeId || not empty param.content}">
                        <button type="button" class="btn btn-warning btn-sm" onclick="_reset()">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </form>
            </div>
            <div class="space-4"></div>
            <div class="table-container">
                <table style="min-width: 1200px"class="overflow-y table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th nowrap>模块</th>
                    <th nowrap>账号ID</th>
                    <th nowrap>账号</th>
                    <th>请求</th>
                    <th>客户端</th>
                    <th>内容</th>
                    <th>时间</th>
                    <th>IP</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sysLogs}" var="sysLog" varStatus="st">
                    <tr>
                        <td>${metaTypeMap.get(sysLog.typeId).name}</td>
                        <td>${sysLog.userId}</td>
                        <td>${sysLog.operator}</td>
                        <td>${sysLog.api}</td>
                        <td  title='${sysLog.agent}'>${cm:substr(sysLog.agent, 0, 30, "...")}</td>
                        <td  title='${sysLog.content}'>${cm:substr(sysLog.content, 0, 30, "...")}</td>
                        <td nowrap>${cm:formatDate(sysLog.createTime, "yyyy-MM-dd HH:mm")}</td>
                        <td nowrap>${sysLog.ip}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                </div>
                <wo:page commonList="${commonList}" uri="sysLog_page" target="#page-content" pageNum="5"
                         model="3"/>
        </div>  </div>
            <div id="item-content">
            </div>
    </div>
</div>
<script>
    stickheader();

    function del(id, type) {

        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/sysLog_del", {id: id, type: type}, function () {

                    _reload();
                    SysMsg.success('操作成功。', '成功');
                });
            }
        });
    }
    function _search() {

        _tunePage(1, "", "${ctx}/sysLog_page", "#page-content", "", "&" + $("#searchForm").serialize());
    }
    function _reset() {

        _tunePage(1, "", "${ctx}/sysLog_page", "#page-content", "", "");
    }
    function _reload() {
        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/sysLog_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>