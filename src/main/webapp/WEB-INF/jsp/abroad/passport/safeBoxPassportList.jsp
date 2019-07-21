<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">保险柜编号：${safeBoxMap.get(cm:toInt(param.safeBoxId)).code}</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <table id="jqGrid2" class="jqGrid2 table-striped"> </table>
                <div id="jqGridPager2"> </div>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager:"#jqGridPager2",
        url: '${ctx}/abroad/safeBoxPassportList_data?callback=?&safeBoxId=${param.safeBoxId}&type=${param.type}&cancelConfirm=${param.cancelConfirm}',
        colModel: [
            { label: '工作证号', name: 'user.code', width: 110,frozen:true },
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.cadre(rowObject.cadre.id, cellvalue);
            },frozen:true  },
            { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
            { label: '职位属性', name: 'cadre.postType', width: 200, formatter:$.jgrid.formatter.MetaType},
            { label: '证件名称', name: 'passportClass.name', width: 200 },
            { label: '证件号码', name: 'code', width: 110 },
            { label:'发证日期', name: 'issueDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} },
            { label:'有效期', name: 'expiryDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} },
            { label:'集中保管日期', name: 'keepDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} },
            { label:'证件状态', name: 'passportType', width: 130 },
            { label:'是否借出', name: 'isLent', formatter:function(cellvalue){
                return cellvalue?"借出":"-";
            } }
        ]
    })
    $(window).triggerHandler('resize.jqGrid2');
</script>