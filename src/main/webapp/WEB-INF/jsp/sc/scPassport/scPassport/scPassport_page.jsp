<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=ScConstants.SC_PASSPORTHAND_STATUS_UNHAND%>" var="SC_PASSPORTHAND_STATUS_UNHAND"/>
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
                    <a href="javascript:;">交证件录入信息</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${scPassportHand.status==SC_PASSPORTHAND_STATUS_UNHAND}">
                    <shiro:hasPermission name="scPassport:edit">
                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/sc/scPassport_au?handId=${param.handId}">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/sc/scPassport_au?handId=${param.handId}"
                                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                            修改
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="scPassport:del">
                        <button data-url="${ctx}/sc/scPassport_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                    </c:if>
                   <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                            data-url="${ctx}/sc/scPassport_data"
                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出
                    </button>--%>
                    <span style="padding-left: 10px;" class="text-danger">提示：三类证件全部添加之后，才可以进行入库操作。</span>
                </div>

                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers: true,
        url: '${ctx}/sc/scPassport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '集中保管日期', name: 'keepDate', width: 120, formatter:function(cellvalue, options, rowObject){
                if(!rowObject.isExist) return '--'
                if($.trim(cellvalue)=='') return '--'
                return $.date(cellvalue, "yyyy-MM-dd")
            }},
            { label: '证件名称', name: 'classId', width: 200, formatter:$.jgrid.formatter.MetaType },
            {label: '证件号码', name: 'code', formatter:function(cellvalue, options, rowObject){
                if(!rowObject.isExist) return '--'
                return $.trim(cellvalue)
            }},
            { label: '证件首页', name: '_pic', width: 80, formatter:function(cellvalue, options, rowObject){
                if(!rowObject.isExist) return '--'
                if($.trim(rowObject.pic)=='') return '--'
                return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">查看</a>'
                        .format(encodeURI(rowObject.pic), rowObject.code + ".jpg");
            } },
            {label: '发证机关', name: 'authority', width: 180, formatter:function(cellvalue, options, rowObject){
                if(!rowObject.isExist) return '--'
                return $.trim(cellvalue)
            }},
            { label:'发证日期', name: 'issueDate', formatter:function(cellvalue, options, rowObject){
                if(!rowObject.isExist) return '--'
                if($.trim(cellvalue)=='') return '--'
                return $.date(cellvalue, "yyyy-MM-dd")
            }},
            { label:'有效期', name: 'expiryDate', formatter:function(cellvalue, options, rowObject){
                if(!rowObject.isExist) return '--'
                if($.trim(cellvalue)=='') return '--'
                return $.date(cellvalue, "yyyy-MM-dd")
            } },
            { label:'存放保险柜', name: 'safeBox.code', width: 130, formatter:function(cellvalue, options, rowObject){
                if(!rowObject.isExist) return '--'
                return $.trim(cellvalue)
            }},
            {label: '备注', name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach_download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });
</script>