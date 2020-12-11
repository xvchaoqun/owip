<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>以下${param.isOdAdmin==1?"分党委存在未报送的支部":"党支部未报送"}</h3>
</div>
<div class="modal-body rownumbers">
    <div class="space-4"></div>
    <table id="jqGridPopup" class="table-striped"></table>
    <div class="space-4"></div>
    <div class="form-group">
        <label class="col-xs-2 control-label" style="padding-left: 20px!important;padding-top: 12px!important;"> 提醒例句</label>
        <div class="col-xs-9">
            <textarea class="form-control" type="text" name="notice">${tpl.content}</textarea>
        </div>
    </div>
</div>

<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" class="btn btn-primary" value="发送短信提醒"/>
</div>
<script>

   var partyList = ${cm:toJSONArray(partyList)};
   var branchList = ${cm:toJSONArray(branchList)};

    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        height: 350,
       datatype: "local",
        <c:if test="${param.isOdAdmin==1}">
        rowNum: partyList.length,
        data: partyList,
        </c:if>
        <c:if test="${param.isOdAdmin==0}">
        rowNum: branchList.length,
        data: branchList,
        </c:if>
        colModel: [
            <c:if test="${param.isOdAdmin==1}">
            {label: '分党委名称', name: 'name', width: 450, frozen: true},
            {hidden: true, key: true, name: 'id'}
            </c:if>
            <c:if test="${param.isOdAdmin==0}">
            {label: '党支部名称', name: 'name', width: 450, frozen: true},
            {hidden: true, key: true, name: 'id'}
            </c:if>
        ]
    });



    $("#modal #selectBtn").click(function(){

        var ids = $("#jqGridPopup").getGridParam("selarrrow");

        if (ids.length == 0) {
            SysMsg.warning("请选择行", "提示");
            return;
        }

        var title = "发送短信提醒";
        var msg = "确定给这{0}个{1}发送短信提醒吗？";
        var isOdAdmin = "${param.isOdAdmin==1?_p_partyName:"党支部"}";
        SysMsg.confirm(msg.format(ids.length,isOdAdmin), title, function () {

        var tplCode = $('.rownumbers input[name=tplCode]').val();
        //console.log(notice);
        if(ids.length==0) return;
        $.post("${ctx}/pm/pm3Guide_notice",
            {
                isOdAdmin:${param.isOdAdmin},
                ids:ids,
            },function(ret){
                if(ret.success){
                    SysMsg.success('发送提醒成功！', '提示', function(){
                        $("#modal").modal('hide');
                        $.hashchange();
                    });
                }
            })

        $("#modal").modal('hide');

        });
    });
</script>