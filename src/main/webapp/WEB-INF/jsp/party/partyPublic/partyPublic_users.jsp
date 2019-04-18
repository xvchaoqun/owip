<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择公示对象<span class="red bolder">（如信息有误， 请到“党员发展”中维护）</span></h3>
</div>
<div class="modal-body rownumbers">
    <form class="form-inline search-form" id="searchForm_popup">
        <div class="form-group">
            <label>类型</label>
            <select data-rel="select2"  data-width="90"
                    name="applyType" data-placeholder="请选择">
                <option></option>
                <c:forEach items="<%=MemberConstants.MEMBER_TYPE_MAP%>" var="entity">
                    <option value="${entity.key}">${entity.value}</option>
                </c:forEach>
            </select>
             <script>
                $("#searchForm_popup select[name=applyType]").val("${param.applyType}")
            </script>
        </div>
        <div class="form-group">
            <label>姓名</label>
            <select data-rel="select2"
                    name="userId" data-placeholder="请选择">
                <option></option>
                <c:forEach items="${memberApplys}" var="memberApply">
                    <c:set var="user" value="${memberApply.user}"/>
                    <option value="${user.id}">${user.realname}-${user.code}</option>
                </c:forEach>
            </select>
            <script>
                $("#searchForm_popup select[name=userId]").val("${param.userId}")
            </script>
        </div>
        <div class="form-group">
            <label>所在支部</label>
            <select class="form-control" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/branch_selects?partyId=${param.partyId}"
                                    name="branchId" data-placeholder="请选择" data-width="280">
                <option value="${branch.id}">${branch.name}</option>
            </select>
             <script>
                $("#searchForm_popup select[name=branchId]").val("${param.branchId}")
            </script>
        </div>
        <c:set var="_query" value="${not empty param.userId ||not empty param.applyType ||not empty param.branchId}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/partyPublic_users?type=${param.type}&partyId=${param.partyId}"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/partyPublic_users"
                        data-querystr="type=${param.type}&partyId=${param.partyId}"
                        data-target="#modal .modal-content"
                        class="reloadBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>
        </div>
    </form>
    <table id="jqGridPopup" class="table-striped"></table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" class="btn btn-primary" value="选择"/>
</div>
<style>
    .rownumbers #jqGridPopup_cb {
        text-align: left !important;
    }
</style>
<script>
    $('#searchForm_popup [data-rel="select2"]').select2();
    $.register.ajax_select($('#searchForm_popup [data-rel="select2-ajax"]'));
    var memberApplys = ${cm:toJSONArray(memberApplys)};
    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        height: 400,
        width:865,
        datatype: "local",
        rowNum: memberApplys.length,
        data: memberApplys,
        colModel: [
            { label:'工作证号', name: 'user.code', width: 120,frozen:true},
            { label:'姓名', name: 'user.realname',frozen:true},
            { label:'类别', name: 'type', width: 80, formatter:function(cellvalue, options, rowObject){
                return _cMap.MEMBER_TYPE_MAP[cellvalue];
            },frozen:true },
            {
                label: '所在支部', name: '_branch', align:'left',  width: 450, formatter:function(cellvalue, options, rowObject){
                    if(rowObject.branchId>0){
                        var branch = _cMap.branchMap[rowObject.branchId];
                        return '<span class="{0}">{1}</span>'
                        .format(branch.isDeleted ? "delete" : "", branch.name);
                    }else{
                        var party = _cMap.partyMap[rowObject.partyId];
                        return '<span class="{0}">{1}</span>'
                        .format(party.isDeleted ? "delete" : "", party.name);
                    }
            }}, {hidden: true, key: true, name: 'userId'}
        ]
    });

    $("#modal #selectBtn").click(function(){

        var userIds = $("#jqGridPopup").getGridParam("selarrrow");
        if(userIds.length==0) return;

        var $jqGrid = $("#jqGrid2");
        $.post("${ctx}/partyPublic_selectUser", {userIds: userIds}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $.each(ret.votes, function(i, vote){
                    var rowData =$jqGrid.getRowData(vote.userId);
                    if (rowData.userId == undefined) {
                        //console.log(vote)
                        $jqGrid.jqGrid("addRowData", vote.userId, vote, "last");
                    }
                })
                $jqGrid.closest(".widget-box").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
                clearJqgridSelected();
                //$("#jqGridPopup").resetSelection();
            }
        })

        /*$.each(userIds, function(i, voteId){
            var rowData =$jqGrid.getRowData(voteId);
            if (rowData.id == undefined) {
                var data = $("#jqGridPopup").getRowData(voteId);
                //console.log(data)
                $jqGrid.jqGrid("addRowData", voteId, data, "last");
            }
        })
        $("#modal").modal('hide');
        clearJqgridSelected();*/
    });
</script>