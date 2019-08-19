<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv multi-row-head-table">
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/modify/modifyTableApply/menu.jsp"/>
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
                        <button class="popupBtn btn btn-success btn-sm" data-width="800"
                           data-url="${ctx}/cadreCompany_au?module=${param.module}&toApply=1&cadreId=${cadre.id}"><i class="fa fa-plus"></i>
                            添加</button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadreCompany_au"
                           data-grid-id="#jqGrid_records" data-width="800"
                           data-querystr="module=${param.module}&toApply=1&cadreId=${cadre.id}"><i class="fa fa-edit"></i>
                            修改</button>
                        <button data-url="${ctx}/user/modifyTableApply_del"
                                data-grid-id="#jqGrid_records"
                                data-querystr="module=${module}"
                                class="jqOpenViewBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    <input type="checkbox" data-name="company" name="check" class="cadre-info-check"> 无此类情况
                    </shiro:lacksPermission>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid_records" class="jqGrid"></table>
                <div id="jqGridPager_cadreCompany"></div>
            </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<style>
    .multi-row-head-table #jqGrid_records_cb{
        padding: 10px!important;
        padding-right: 2px!important;
    }
</style>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<jsp:include page="/WEB-INF/jsp/cadre/cadreCompany/colModels.jsp"/>
<script>
    $("#jqGrid_records").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreCompany",
        url: '${ctx}/cadreCompany_data?cadreId=${cadre.id}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels_cadreCompany
    }).jqGrid("setFrozenColumns");
    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach_download?path={0}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'));
    });
    $(window).triggerHandler('resize.jqGrid');
</script>