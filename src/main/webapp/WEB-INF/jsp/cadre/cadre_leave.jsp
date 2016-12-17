<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><span style="font-size:25px;font-weight: bolder">${sysUser.realname}</span>离任
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_leave" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">
        <input type="hidden" name="status">
        <div class="form-group">
            <label class="col-xs-3 control-label">类别</label>
            <div class="col-xs-8 label-text"  style="font-size: 15px;">
                <input type="checkbox" class="big" value="1"/> 中层干部离任
                <input type="checkbox"  class="big" value="2"/> 校领导离任
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">离任文件</label>
            <div class="col-xs-9 label-text">
                <div id="tree3"></div>
                <input type="hidden" name="dispatchCadreId">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">离任后所在单位及职务</label>
            <div class="col-xs-8">
                <input  class="form-control" type="text" name="title" value="${cadre.title}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>

    var treeNode = ${tree};
    if(treeNode.children.length==0){
        $("#tree3").html("没有发文");
    }else{
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 2,
            children: treeNode,
            onSelect: function(select, node) {
                //node.expand(node.data.isFolder && node.isSelected());
            },
            onCustomRender: function(node) {
                if(!node.data.isFolder)
                return "<span class='dynatree-title'>"+node.data.title
                        +"</span>&nbsp;&nbsp;<button class='openUrl btn btn-xs btn-default' data-url='${ctx}/swf/preview?type=url&path="+node.data.tooltip+"'>查看</button>"
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    }

    $("input[type=checkbox]").click(function(){
        if($(this).prop("checked")){
            $("input[type=checkbox]").not(this).prop("checked", false);
        }
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            var type = $('#modal input[type=checkbox]:checked').val();
            if(type==1){
                $("#modal input[name=status]").val('${CADRE_STATUS_LEAVE}');
            }else if(type==2){
                $("#modal input[name=status]").val('${CADRE_STATUS_LEADER_LEAVE}');
            }else {
                SysMsg.warning("请选择离任类别");
                return;
            }

            var dispatchCadreId = -1;
            if(treeNode.children.length>0) {
                var selectIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                    return node.data.key;
                });
                if (selectIds.length > 1) {
                    SysMsg.warning("只能选择一个发文");
                    return;
                }
                if (selectIds.length==1)
                    $("#modal input[name=dispatchCadreId]").val(selectIds[0]);
            }

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //SysMsg.success('操作成功。', '成功',function(){
                                location.href='${ctx}/cadre?status='+$("#modal input[name=status]").val()
                        //});
                    }
                }
            });
        }
    });
</script>