<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.id ||not empty param.code ||not empty param.unitId ||not empty param.classId ||not empty param.phone || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpParty:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dp/dpParty_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqEditBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpParty_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改信息</button>
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/dp/dpParty_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpOrgAdmin:list">
                <button data-url="${ctx}/dp/dp_org_admin"
                        data-id-name="partyId" class="jqOpenViewBtn btn btn-warning btn-sm">
                    <i class="fa fa-user"></i> 编辑管理员
                </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/dp/dpParty_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                <shiro:hasPermission name="dpParty:del">
                <c:if test="${cls==1}">
                    <shiro:hasPermission name="dpParty:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dp/dpParty_batchDel" data-title="撤销民主党派"
                           data-msg="确定撤销这{0}个民主党派吗？"><i class="fa fa-history"></i> 撤销</a>
                        【注：撤销操作将删除其下所有的委员会和相关管理员权限，请谨慎操作！】
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==2}">
                    <shiro:hasPermission name="dpParty:del">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/dp/dpParty_batchDel"
                           data-querystr="isDeleted=0"
                           data-title="恢复已撤销民主党派"
                           data-msg="确定恢复这{0}个民主党派吗？"><i class="fa fa-reply"></i> 恢复</a>
                        【注：恢复操作之后需要重新设置委员会及相关管理员权限！】
                    </shiro:hasPermission>
                </c:if>
                </shiro:hasPermission>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="cls" value="${cls}">
                            <div class="form-group">
                            <label>名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入名称">
                        </div>
                        <div class="form-group">
                            <label>编号</label>
                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                   placeholder="请输入编号">
                        </div>

                            <div class="form-group">
                                <label>所属单位</label>
                                <select name="unitId" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${unitMap}" var="unit"> 
                                        <option value="${unit.key}">${unit.value.name}</option>
                                          </c:forEach>  </select>
                                <script>         $("#searchForm select[name=unitId]").val('${param.unitId}');     </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dp/dpParty"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dp/dpParty"
                                            data-target="#page-content">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div></div>
                    </div>
            </div></div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    $("#jqGrid").jqGrid({
        rownumbers:false,
        url: '${ctx}/dp/dpParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情',
                name: '_detail',
                width: 80,
                frozen: true,
                formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-warning btn-xs" ' +
                        'data-url="${ctx}/dp/dpParty_view?id={0}">'
                        + '<i class="fa fa-search"></i> 查看</button>').format(rowObject.id)}},
            {label: '编号', name: 'code', frozen: true},
            {label: '名称', name: 'name', align: 'left', width: 400, frozen: true},
            <shiro:hasPermission name="dpParty:edit">
            <c:if test="${!_query}">
            {label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: '${ctx}/dp/dpParty_changeOrder'}, frozen: true},
            </c:if>
            </shiro:hasPermission>
            {label: '党员总数', name: 'memberCount', width: 80, formatter: function (cellvalue, option, rowObject) {
                    return cellvalue == undefined ? 0 : cellvalue;}},
            {label: '在职教职工',
                name: 'teacherMemberCount',
                width: 90,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined || cellvalue == 0) return 0;
                    <shiro:hasPermission name="dpMember:list">
                    return '<a href="#${ctx}/dp/dpMember?cls=2&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="dpMember:list">
                    return cellvalue;
                    </shiro:lacksPermission>}},
            { label:'学生', name: 'studentMemberCount', width: 50, formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                    <shiro:hasPermission name="dpMember:list">
                    return '<a href="#${ctx}/dp/dpMember?cls=1&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="dpMember:list">
                    return cellvalue;
                    </shiro:lacksPermission>}},
            {label: '离退休党员',
                name: 'retireMemberCount',
                width: 90,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined || cellvalue == 0) return 0;
                    <shiro:hasPermission name="dpMember:list">
                    return '<a href="#${ctx}/dp/dpMember?cls=3&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="dpMember:list">
                    return cellvalue;
                    </shiro:lacksPermission>}},
            {label: '委员会总数', name: 'groupCount', width: 90, formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == undefined ? 0 : cellvalue;}},
            {label: '是否已设立现任委员会',
                name: 'presentGroupCount',
                width: 160,
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue >= 1 ? "是" : "否";}},
            {label: '简称', name: 'shortName', width: 180},
            {label:'所属单位', name: 'unitId', width: 180, formatter: $.jgrid.formatter.unit},
            {label: '民族党派类别', name: 'classId', formatter: $.jgrid.formatter.MetaType},
            {label: '联系电话', name: 'phone'},
            {label: '传真', name: 'fax'},
            {label: '邮箱', name: 'email'},
            {label: '信箱', name: 'mailbox'},
            {label: '成立时间', name: 'foundTime', width: 200}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('[data-rel="select2"]').select2();
</script>