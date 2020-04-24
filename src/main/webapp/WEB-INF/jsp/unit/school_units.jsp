<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <jsp:include page="menu.jsp"/>
        <div class="tab-content">
            <div class="tab-pane in active">
                <div class="buttons">
                    <button type="button" class="downloadBtn btn btn-success btn-sm tooltip-success"
                       data-url="${ctx}/unit?cls=3&export=1"
                       data-rel="tooltip" data-placement="top" title="导出学校单位信息">
                        <i class="fa fa-download"></i> 导出</button>
                    <button class="runBtn btn btn-primary btn-sm" data-url="${ctx}/refreshSchoolUnits"
                            data-callback="_reload">
                        <i class="fa fa-refresh"></i> 刷新
                    </button>
                </div>
                <div class="space-4"></div>
                <table class="table table-actived table-striped table-bordered table-hover table-center"
                       >
                    <thead>
                    <tr>
                        <th width="50">序号</th>
                        <%-- <th width="100">单位ID</th>--%>
                        <th width="100">单位编码</th>
                        <th>单位名称</th>
                        <th width="150">类型</th>
                        <th>上级单位</th>
                        <%--<th width="100">成立时间</th>--%>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${schoolUnits}" var="record" varStatus="vs">
                        <tr>
                            <td>${vs.count}</td>
                                <%--<td>${record.id}</td>--%>
                            <td>${record.code}</td>
                            <td style="text-align: left">${record.name}</td>
                            <td>${record.type}</td>
                            <td>${record.top}</td>
                                <%--<td>${cm:formatDate(record.workTime, "yyyy-MM-dd")}</td>--%>
                            <td>${record.remark}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="footer-margin lower"/>
<script>
    function _reload() {
        toastr.success('操作成功。', '成功');
    }
</script>

