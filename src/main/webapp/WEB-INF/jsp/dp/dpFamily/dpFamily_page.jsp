<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-home"></i> 家庭成员信息
                    <div class="buttons">
                        <%--<a class="popupBtn btn btn-warning btn-sm"
                           data-width="800"
                           data-url="${ctx}/hf_content?code=hf_cadre_family">
                            <i class="fa fa-info-circle"></i> 填写说明</a>--%>
                        <shiro:hasPermission name="dpFamily:edit">
                            <a class="popupBtn btn btn-success btn-sm" data-width="800"
                               data-url="${ctx}/dp/dpFamily_au?userId=${param.userId}"><i class="fa fa-plus"></i>
                                添加</a>
                            <a class="jqOpenViewBtn btn btn-primary btn-sm"
                               data-url="${ctx}/dp/dpFamily_au"  data-width="800"
                               data-grid-id="#jqGrid2"
                               data-querystr="&userId=${param.userId}"><i class="fa fa-edit"></i>
                                修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="dpFamily:del">
                            <a data-url="${ctx}/dp/dpFamily_batchDel"
                               data-title="删除"
                               data-msg="确定删除这{0}条数据？<div class='bolder text-danger'></div>"
                               data-grid-id="#jqGrid2"
                               data-querystr="userId=${param.userId}"
                               class="jqBatchBtn btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> 删除
                            </a>
                        </shiro:hasPermission>
                    </div>
        </h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table id="jqGrid2" class="jqGrid2"  data-height-reduce="70" data-width-reduce="70"></table>
            <div id="jqGridPager_dpFamily"></div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager_dpFamily",
        url: '${ctx}/dp/dpFamily_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '称谓', name: 'title', frozen: true, formatter: $.jgrid.formatter.MetaType},
            {
                label: '排序',formatter: $.jgrid.formatter.sortOrder, width:90,
                formatoptions: {url: "${ctx}/dp/dpFamily_changeOrder",grid:'#jqGrid2'}
            },
            {label: '姓名', width: 120, name: 'realname'},
            {label: '出生年月', name: 'birthday', formatter: function (cellvalue, options, rowObject) {
                    if(rowObject.withGod) return '--'
                    return $.date(cellvalue, "yyyy.MM");
                },
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if(!rowObject.withGod && $.trimHtml(val)=='')
                        return "class='danger'";
                }},
            {label: '政治面貌', name: 'politicalStatus', formatter: $.jgrid.formatter.MetaType},
            {label: '工作单位及职务', name: 'unit', width: 650, align:"left"},
            {label: '备注', name: 'remark', width: 300},{hidden: true, key: true, name: 'id'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
</script>