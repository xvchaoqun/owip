<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv">
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/modify/modifyTableApply/menu.jsp"/>
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
                        <button class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/cadrePostPro_au?module=${param.module}&toApply=1&cadreId=${cadre.id}"><i class="fa fa-plus"></i>
                            添加</button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadrePostPro_au"
                           data-grid-id="#jqGrid_records"
                           data-querystr="module=${param.module}&toApply=1&cadreId=${cadre.id}"><i class="fa fa-edit"></i>
                            修改</button>
                        <button data-url="${ctx}/user/modifyTableApply_del"
                                data-grid-id="#jqGrid_records"
                                data-querystr="module=${module}"
                                class="jqOpenViewBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    <input type="checkbox" data-name="post_pro" name="check" class="cadre-info-check"> 无此类情况
                    </shiro:lacksPermission>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid_records" class="jqGrid"></table>
                <div id="jqGridPager_cadrePostPro"></div>
            </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<script>
    $("#jqGrid_records").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostPro",
        url: '${ctx}/cadrePostPro_data?cadreId=${cadre.id}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadrePostPro
    }).jqGrid("setFrozenColumns");
    $.register.fancybox();
    $(window).triggerHandler('resize.jqGrid');
</script>