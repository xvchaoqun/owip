<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择党委常委会</h3>
</div>
<div class="modal-body">
    <form class="form-inline search-form" id="searchForm_popup">
        <input type="hidden" name="cls" value="${param.cls}">

        <div class="form-group">
            <label>年份</label>

            <div class="input-group" style="width: 100px">
                <input class="form-control date-picker" placeholder="请选择"
                       name="year"
                       type="text"
                       data-date-format="yyyy" data-date-min-view-mode="2"
                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
        <div class="form-group">
            <label>党委常委会日期</label>
            <input class="form-control date-picker" name="holdDate" style="width: 100px"
                   type="text"
                   data-date-format="yyyymmdd"
                   value="${param.holdDate}"/>
        </div>
        <c:set var="_query" value="${not empty param.year ||not empty param.holdDate}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/sc/scCommittee_popup"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/sc/scCommittee_popup"
                        data-querystr="cls=${param.cls}"
                        data-target="#modal .modal-content"
                        class="reloadBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>
        </div>
    </form>
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>

</div>
<div class="modal-footer">
    <button class="btn btn-info" type="button" id="selectBtn">
        <i class="ace-icon fa fa-check "></i>
        确定
    </button>
</div>

<jsp:include page="scCommittee_colModel.jsp"/>
<script>

    function _reload2() {
        $("#jqGrid_popup").trigger("reloadGrid");
    }
    $.register.date($('#searchForm_popup .date-picker'));

    clearJqgridSelected();

    var $jqGrid = $("#jqGrid_popup");
    $jqGrid.jqGrid({
        height: 390,
        width: 1160,
        rowNum: 10,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/sc/scCommittee_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: colModel
    });
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    $("#modal #selectBtn").click(function () {

        var $selectBtn = $(this);
        var ids = $jqGrid.getGridParam("selarrrow");
        if (ids.length == 0 ) {
            $.tip({
                $target: $selectBtn, $container: $("#modal"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择党委常委会。"
            });
            return;
        }

        $.post("${ctx}/sc/scCommittee_select", {ids:ids}, function(data){

            if(data.success) {
                $.each(data.scCommittees, function(i, scCommittee){
                    _selectCommittee(scCommittee);
                })
                $("#modal").modal('hide');
            }
        })
    });
</script>