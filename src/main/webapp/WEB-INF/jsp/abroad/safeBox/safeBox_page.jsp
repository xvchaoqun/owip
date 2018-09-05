<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/abroad/safeBox_au"
             data-url-del="${ctx}/abroad/safeBox_del"
             data-url-bd="${ctx}/abroad/safeBox_batchDel"
             data-url-co="${ctx}/abroad/safeBox_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <jsp:include page="/WEB-INF/jsp/abroad/passport/menu.jsp"/>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <a class="editBtn btn btn-success btn-sm"><i class="fa fa-plus"></i> 添加保险柜</a>
                            <button class="jqEditBtn btn btn-primary btn-sm">
                                <i class="fa fa-edit"></i> 修改信息
                            </button>
                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-url="${ctx}/abroad/safeBoxPassportList"
                                    data-id-name="safeBoxId" data-open-by="page">
                                <i class="fa fa-info-circle"></i> 详情
                            </button>
                            <shiro:hasPermission name="safeBox:del">
                                <a class="jqExportBtn btn btn-primary btn-sm tooltip-success"
                                   data-url="${ctx}/abroad/safeBox_data"
                                   data-rel="tooltip" data-placement="top" title="导出所有证件"><i class="fa fa-download"></i> 导出</a>
                                <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                            </shiro:hasPermission>
                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"> </table>
                        <div id="jqGridPager"> </div>
                    </div>
                </div></div></div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/abroad/safeBox_data?callback=?&',
        colModel: [
            { label: '保险柜编号', name: 'code', width: 100,frozen:true },
            { label: '证件总数量', name: 'totalCount', width: 100 , formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/abroad/safeBoxPassportList?safeBoxId={0}">{1}</a>'
                        .format(rowObject.id, rowObject.totalCount)
            },frozen:true},
            { label: '有效证件数量',align:'center',  name: 'keepCount', width: 120 , formatter:function(cellvalue, options, rowObject){
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/abroad/safeBoxPassportList?safeBoxId={0}&type=1">{1}</a>'
                        .format(rowObject.id, rowObject.keepCount)
            },frozen:true},
            { label: '取消集中管理证件数量（未确认）', name: 'cancelCount', width: 250 ,
                formatter:function(cellvalue, options, rowObject){
                    var count = rowObject.totalCount - rowObject.keepCount;
                    if(count<=0) return '0';
                return '<a href="javascript:;" class="openView" data-url="${ctx}/abroad/safeBoxPassportList?safeBoxId={0}&type=2&cancelConfirm=0">{1}</a>'
                        .format(rowObject.id, count)
            },frozen:true},
            { label:'排序',align:'center', width: 80, formatter: $.jgrid.formatter.sortOrder,frozen:true },
            { label: '证件所属单位', name: 'units', width: 500, align:'left' },
            { label: '备注', name: 'remark', width: 250 }
        ]}).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
</script>