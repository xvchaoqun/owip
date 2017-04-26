<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv">
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/modify/modifyTableApply/menu.jsp"/>
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
                        <a class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/cadreCourse_au?toApply=1&cadreId=${cadre.id}"><i class="fa fa-plus"></i>
                            添加</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadreCourse_au"
                           data-grid-id="#jqGrid_records"
                           data-querystr="&toApply=1&cadreId=${cadre.id}"><i class="fa fa-edit"></i>
                            修改</a>
                        <button data-url="${ctx}/user/modifyTableApply_del"
                                data-title="删除"
                                data-msg="申请删除这条记录？"
                                data-grid-id="#jqGrid_records"
                                data-querystr="module=${module}"
                                data-callback="_delCallback"
                                class="jqItemBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid_records" class="jqGrid"></table>
                <div id="jqGridPager_cadreCourse"></div>
            </div>
        </div>
        <div id="item-content">
        </div>
    </div>
</div>
<%--<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-grid-id="#jqGrid_cadreCourse"
       data-url="${ctx}/cadreCourse_changeOrder?cadreId=${param.cadreId}" data-id="{{=id}}"
       data-direction="-1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
<a href="#" class="jqOrderBtn" data-grid-id="#jqGrid_cadreCourse"
       data-url="${ctx}/cadreCourse_changeOrder?cadreId=${param.cadreId}"
       data-id="{{=id}}" data-direction="1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>--%>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp?_sort=no"/>
<script>
    function _delCallback(type) {
        $("#modal").modal("hide");
        location.href='?cls=1&module=${module}';
    }
    $("#jqGrid_records").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreCourse",
        url: '${ctx}/cadreCourse_data?cadreId=${cadre.id}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreCourse
    }).jqGrid("setFrozenColumns");
    register_fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'));
    });
    $(window).triggerHandler('resize.jqGrid');
</script>