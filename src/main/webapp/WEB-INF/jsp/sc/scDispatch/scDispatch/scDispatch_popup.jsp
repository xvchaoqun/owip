<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>文件起草签发</h3>
</div>
<div class="modal-body">
    <form class="form-inline search-form" id="searchForm_popup">
        <input type="hidden" name="cls" value="${param.cls}">

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
        <div class="form-group">
            <label>发文类型</label>
            <c:set var="dispatchType"
                   value="${dispatchTypeMap.get(dispatchTypeId)}"/>
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}/dispatchType_selects"
                    name="dispatchTypeId" data-placeholder="请选择发文类型">
                <option value="${dispatchType.id}">${dispatchType.name}</option>
            </select>
        </div>
        <c:set var="_query" value="${not empty param.year ||not empty param.dispatchTypeId}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/sc/scDispatch_popup"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/sc/scDispatch_popup"
                        data-querystr="cls=${param.cls}"
                        data-target="#modal .modal-content"
                        class="resetBtn btn btn-warning btn-sm">
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

<jsp:include page="scDispatch_colModel.jsp"/>
<script>

    function _reload2() {
        $("#jqGrid_popup").trigger("reloadGrid");
    }
    $.register.date($('#searchForm_popup .date-picker'));
    $.register.dispatchType_select($('#searchForm_popup select[name=dispatchTypeId]'), $("#searchForm_popup input[name=year]"));

    clearJqgridSelected();

    var $jqGrid = $("#jqGrid_popup");
    $jqGrid.jqGrid({
        height: 390,
        width: 1160,
        rowNum: 10,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/sc/scDispatch_data?callback=?&hideDispatched=1&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: colModel
    });
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    $("#modal #selectBtn").click(function () {

        var $selectBtn = $(this);
        var ids = $jqGrid.getGridParam("selarrrow");
        if (ids.length == 0 || ids.length > 1) {
            $.tip({
                $target: $selectBtn, $container: $("#modal"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选一行记录。"
            });
            return;
        }

        $.post("${ctx}/sc/scDispatch_select", {id:ids[0]}, function(data){

            if(data.success) {
                var sd = data.scDispatch;
                //console.log(sd)
                $("#scDispatchDiv").html(_.template($("#scDispatchTpl").html())({sd: sd}));
                $("#scCommitteesDiv .label-text").html(_.template($("#scCommitteesTpl").html())({scCommittees: data.scDispatch.scCommittees}));
                $("#scCommitteesDiv").show();

                $("#modalForm input[name=scDispatchId]").val(sd.id);

                $("#modalForm input[name=year]").val(sd.year).prop("disabled", true);
                var $dispatchTypeId = $("#modalForm select[name=dispatchTypeId]");
                $dispatchTypeId.html('<option value="{0}">{1}</option>'.format(sd.dispatchType.id, sd.dispatchType.name));
                $dispatchTypeId.val(sd.dispatchType.id);
                $dispatchTypeId.select2({theme: "default"}).trigger("change").prop("disabled", true);

                if(sd.code>0) {
                    $("#modalForm input[name=code]").val(sd.code).prop("disabled", true);
                }
                $("#modalForm input[name=_meetingTime]").val($.date(sd.meetingTime, 'yyyy-MM-dd')).prop("disabled", true);
                $("#modalForm input[name=appointCount]").val(sd.appointCount).prop("disabled", true);
                $("#modalForm input[name=dismissCount]").val(sd.dismissCount).prop("disabled", true);

                $("#modalForm input[name=_pubTime]").val('');
                $("#modalForm input[name=_workTime]").val('');
                $("#modalForm input[name=remark]").val('');


                $("#modal").modal('hide');
            }
        })
    });
</script>