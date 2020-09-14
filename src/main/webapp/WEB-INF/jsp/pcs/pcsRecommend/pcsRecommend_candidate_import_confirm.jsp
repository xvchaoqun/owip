<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>信息确认</h3>
</div>
<div class="modal-body">
     <form class="form-horizontal" action="${ctx}/pcs/pcsRecommend_candidate_import_confirm" autocomplete="off"
           disableautocomplete id="modalForm" method="post"
                              enctype="multipart/form-data">
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>学工号</th>
            <th>姓名</th>
            <th>类型</th>
            <th>提名党员数</th>
            <th>提名正式党员数</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${candidates}" var="can" varStatus="vs">
        <tr data-user-id="${can.userId}" data-type="${can.type}"
            data-vote="${can.vote}" data-positive-vote="${can.positiveVote}">
            <td>
                <c:if test="${not empty can.code}">${can.code}</c:if>
                <c:if test="${empty can.code}">
                <select required name="userId${vs.index}" data-rel="select2-ajax"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&politicalStatus=<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
                        data-placeholder="请输入账号或姓名或工作证号">
                    <option></option>
                </select>
                </c:if>
            </td>
            <td>${can.realname}</td>
            <td>${PCS_USER_TYPE_MAP.get(can.type)}</td>
            <td>${can.vote}</td>
            <td>${can.positiveVote}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
$.register.user_select($('#modalForm select'));

$("#submitBtn").click(function () {
    $("#modalForm").submit();
    return false;
});
$("#modalForm").validate({
    submitHandler: function (form) {
        var hasEmptyUserId = false;
        var items = [];
        $.each($("#modalForm tbody tr"), function (i, tr) {
            var userId = $.trim($(tr).data("user-id"));
            if(userId==""){
                userId = $.trim($("select", tr).val());
            }
            if(userId == ""){
                hasEmptyUserId = true;
                return true;
            }
            var item ={userId:userId, type:$(tr).data("type"),
                vote:$(tr).data("vote"), positiveVote:$(tr).data("positive-vote")};
            items.push(item);
        });
        if(hasEmptyUserId){
            alert("请选择账号")
            return;
        }

        //console.log(JSON.stringify(items))
        $(form).ajaxSubmit({
            data: {items: $.base64.encode(JSON.stringify(items))},
            success: function (ret) {

                if(ret.success){

                    $.each(ret.candidates, function(i, candidate){

                       _importUser(candidate);

                    })
                    $("#modal").modal('hide');
                }
            }
        });
    }
});

function _importUser(candidate) {
  var $select;
  if(candidate.type==${PCS_USER_TYPE_DW}){
      $select = $("#dwUserId");
  }else{
      $select = $("#jwUserId");
  }

  var $jqGrid = $("#jqGrid" + candidate.type);

  var rowData = $jqGrid.getRowData(candidate.userId);

  if (rowData.userId == undefined) {

        $jqGrid.jqGrid("addRowData", candidate.userId, candidate, "last");
        $select.val(null).trigger("change");
        $select.closest(".panel").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
  }
}

</script>