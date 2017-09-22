<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pcsProposal.name}-附议人信息</h3>
</div>
<div class="modal-body rownumbers">
    <table id="jqGridPopup" class="table-striped"></table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<script>
    var candidates = ${cm:toJSONArray(candidates)};
    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        multiselect:false,
        height: 300,
        width:567,
        datatype: "local",
        rowNum: candidates.length,
        data: candidates,
        colModel: [
            {label: '代表姓名', name: 'realname', width: 130, frozen: true},
            {label: '所在单位', name: 'unitName', width: 280, align: 'left'},
            {label: '联系电话', name: 'mobile', width: 120},
            {hidden: true, key: true, name: 'userId'}
        ]
    });
</script>