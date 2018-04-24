<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><span style="font-size:25px;font-weight: bolder">${cadre.realname}</span>离任
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_leave" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">
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
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
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
                        if(ret.status=='${CADRE_STATUS_MIDDLE_LEAVE}')
                            $.hashchange('status='+ret.status, '${ctx}/cadre');
                        if(ret.status=='${CADRE_STATUS_LEADER_LEAVE}')
                            $.hashchange('status='+ret.status, '${ctx}/cadreLeaderInfo');
                        //});
                    }
                }
            });
        }
    });
</script>