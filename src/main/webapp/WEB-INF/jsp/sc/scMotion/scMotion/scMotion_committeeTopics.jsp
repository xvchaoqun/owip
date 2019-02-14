<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>动议记录-干部小组会议题</h3>
</div>
<div class="modal-body popup-jqgrid">
    <form class="form-inline search-form" id="searchForm_popup">
        <div class="form-group">
            <label>年份</label>
            <div class="input-group" style="width: 150px">
                <input required class="form-control date-picker" placeholder="请选择年份"
                       name="year"
                       type="text"
                       data-date-format="yyyy" data-date-min-view-mode="2"
                       value="${param.year}"/>
                <span class="input-group-addon"> <i
                        class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
        <c:set var="_query" value="${not empty param.year}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/sc/scMotion_topics?id=${param.id}"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/sc/scMotion_topics?id=${param.id}"
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
    <button class="btn btn-primary" type="button" id="selectBtn">
        <i class="ace-icon fa fa-check "></i>
        确定
    </button>
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<jsp:include page="/WEB-INF/jsp/sc/scCommittee/scCommitteeTopic/scCommitteeTopic_colModel.jsp"/>
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
        rowNum: 1000, // 单页显示
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/sc/scCommitteeTopic_data?callback=?&unitIds=${unitPost.unitId}&popup=1&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: colModel,
        rowattr: function(rowData, currentObj, rowId)
        {
            if($.date(currentObj.holdDate, "yyyy.MM.dd")=='${cm:formatDate(scMotion.holdDate, "yyyy.MM.dd")}') {
                //console.log(rowData)
                return {'class':'danger'}
            }
        },
        gridComplete:function(){
            var topics = "${scMotion.topics}";
            if($.trim(topics)!='') {
                topics.split(",").forEach(function (item, i) {
                    $("#jqGrid_popup").jqGrid("setSelection", item);
                });
            }
        }
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    $("#modal #selectBtn").click(function () {

        var $selectBtn = $(this);
        var ids = $jqGrid.getGridParam("selarrrow");
        if (ids.length == 0) {
            $.tip({
                $target: $selectBtn, $container: $("#modal"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择议题。"
            });
            return;
        }

        $.post("${ctx}/sc/scMotion_topics", {id:"${param.id}", topics:ids.join(",")}, function(ret){

            if(ret.success) {
                $("#modal").modal('hide');
                $("#jqGrid").trigger("reloadGrid");
            }
        })
    });
</script>