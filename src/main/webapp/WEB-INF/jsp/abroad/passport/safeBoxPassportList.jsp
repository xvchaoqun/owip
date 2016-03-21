<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">保险柜编号：${safeBoxMap.get(cm:parseInt(param.safeBoxId)).code}</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/safeBoxPassportList_data?callback=?&safeBoxId=${param.safeBoxId}',
        colModel: [
            { label: '工作证号', align:'center', name: 'user.code', width: 100 ,frozen:true},
            { label: '姓名',align:'center', name: 'user.realname',resizable:false, width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id={0}">{1}</a>'
                        .format(rowObject.cadre.id, cellvalue);
            } ,frozen:true },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
            { label: '职位属性', align:'center', name: 'cadre.postType.name', width: 200 },
            { label: '证件名称', align:'center', name: 'passportClass.name', width: 200 },
            { label: '证件号码', align:'center', name: 'code', width: 100 },
            { label:'发证日期', align:'center', name: 'issueDate', width: 100 },
            { label:'有效期', align:'center', name: 'expiryDate', width: 100 },
            { label:'集中保管日期', align:'center', name: 'keepDate', width: 120 },
            { label:'证件状态', align:'center', name: 'passportType', width: 130 },
            { label:'是否借出', align:'center', name: 'isLent', width: 100, formatter:function(cellvalue){
                return cellvalue?"借出":"-";
            } }
        ]
    })/*.jqGrid("setFrozenColumns")*/;
    $(window).triggerHandler('resize.jqGrid');

</script>