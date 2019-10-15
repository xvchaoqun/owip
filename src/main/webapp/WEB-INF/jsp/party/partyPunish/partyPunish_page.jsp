<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<shiro:hasPermission name="partyPunish:edit">
          <div class="jqgrid-vertical-offset buttons">
                <c:if test="${cls==OW_PARTY_REPU_PARTY}">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/party/partyPunish_au?partyId=${param.partyId}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/party/partyPunish_au?partyId=${param.partyId}"
                       data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改</button>
                </c:if>
              <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
                  <button class="popupBtn btn btn-info btn-sm"
                          data-url="${ctx}/party/partyPunish_au?branchId=${param.branchId}&cls=2">
                      <i class="fa fa-plus"></i> 添加</button>
                  <button class="jqOpenViewBtn btn btn-primary btn-sm"
                          data-url="${ctx}/party/partyPunish_au?branchId=${param.branchId}&cls=2"
                          data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                      修改</button>
              </c:if>
              <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
                  <button class="popupBtn btn btn-info btn-sm"
                          data-url="${ctx}/party/partyPunish_au?userId=${param.userId}&cls=3">
                      <i class="fa fa-plus"></i> 添加</button>
                  <button class="jqOpenViewBtn btn btn-primary btn-sm"
                          data-url="${ctx}/party/partyPunish_au?userId=${param.userId}&cls=3"
                          data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                      修改</button>
              </c:if>
                    <button data-url="${ctx}/party/partyPunish_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/partyPunish_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
</shiro:hasPermission>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        ondblClickRow: function () {
        },
        pager: "jqGridPager2",
        url: '${ctx}/party/partyPunish_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cls==OW_PARTY_REPU_PARTY}">
            { label: '分党委',name: 'partyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
                    var party = _cMap.partyMap[cellvalue];
                    var _partyView = null;
                    if (party != undefined) {
                        _partyView = party.name;
                        if ($.inArray("party:list", _permissions) >= 0 || $.inArray("party:*", _permissions) >= 0)
                            _partyView = $.trim('{0}'.format(party.name));
                    }
                    if (_partyView != null) {
                        return '<span class="{0}">{1}</span>'.format(party.isDeleted ? "delete" : "", _partyView);
                    }
                    return '--';
                },frozen:true},
            </c:if>
            <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
            { label: '党支部',name: 'branchId',width:400,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
                    var branch = (cellvalue == undefined) ? undefined : _cMap.branchMap[cellvalue];
                    var _branchView = null;
                    if (branch != undefined) {
                        var _branchView = branch.name;
                        if ($.inArray("branch:list", _permissions) >= 0 || $.inArray("branch:*", _permissions) >= 0)
                            _branchView = $.trim('{0}'.format(branch.name))
                            ;
                    }
                    if (_branchView != null) { // 仅显示党支部
                        return '<span class="{0}">{1}</span>'
                            .format(branch.isDeleted ? "delete" : "", _branchView);
                    }
                    return '--';
                },frozen:true},
            </c:if>
            <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
            { label: '党员',name: 'user.realname',formatter:function (cellvalue, options, rowObject) {
                    return $.trim(cellvalue);
                },frozen:true},
            </c:if>
                { label: '处分日期',name: 'punishTime',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
                { label: '处分截止日期',name: 'endTime',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
                { label: '受何种处分',name: 'name',width:180},
                { label: '处分单位',name: 'unit',width: 180, width: 280, align:'left', cellattr: function (rowId, val, rowObject, cm, rdata) {
                        if($.trim(val)=='')
                            return "class='danger'";
                    }},
                { label: '备注',name: 'remark',width:200}, {hidden: true, key: true, name: 'id'}, {hidden: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.fancybox();
    function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").loadPage("${ctx}/party/partyPunish?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>