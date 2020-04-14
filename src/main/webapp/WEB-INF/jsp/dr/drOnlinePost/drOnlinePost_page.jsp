<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
        <div>
            <shiro:hasPermission name="drOnlinePost:edit">
                <button class="popupBtn btn btn-info btn-sm"
                        data-url="${ctx}/dr/drOnlinePost_au?onlineId=${onlineId}">
                    <i class="fa fa-plus"></i> 添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/dr/drOnlinePost_au?onlineId=${onlineId}"
                   data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                    修改</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="drOnlinePost:del">
                <button data-url="${ctx}/dr/drOnlinePost_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid2"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 删除
                </button>
            </shiro:hasPermission>
           <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
               data-url="${ctx}/dr/drOnlinePost_data?onlineId=${onlineId}"
               data-grid-id="#jqGrid2"
               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                <i class="fa fa-download"></i> 导出</button>--%>
        </div>
        <div class="space-4"></div>
        <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="50"></table>
        <div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/dr/drOnlinePost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '推荐类型',name: 'onlineType', frozen: true, width: 105, formatter: $.jgrid.formatter.MetaType},
                { label: '推荐职务',name: 'name', width: 200, frozen: true},
                { label: '最多推荐人数',name: 'competitiveNum'},
                { label: '候选人',name: 'users', align:'left', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == undefined || cellvalue.length == 0) return '--';
                        var names = new Array();
                        cellvalue.forEach(function(user, i){
                            //console.log(user)//list中的值
                            //console.log(i)//下标
                            if (user.realname)
                                names.push(user.realname)
                        })

                        return names.join("，")
                    }, width: 250},
                { label: '分管工作',name: 'job', width: 180},
                { label: '岗位级别',name: 'adminLevel', width: 90, formatter: $.jgrid.formatter.MetaType},
                { label: '职务属性',name: 'postType', width: 120, formatter: $.jgrid.formatter.MetaType},
                { label: '所属单位',name: 'unitId', width: 200, formatter: $.jgrid.formatter.unit},
                { label: '单位类型',name: 'typeId', width: 120, formatter: $.jgrid.formatter.MetaType},
                { label: '备注', name: 'remark', width: 350},{hidden: true, key: true, name: 'id'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>