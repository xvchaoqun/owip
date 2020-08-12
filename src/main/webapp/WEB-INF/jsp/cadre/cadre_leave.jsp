<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><span style="font-size:25px;font-weight: bolder">${cadre.realname}</span>离任
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadre_leave" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>原职务</label>
            <div class="col-xs-7">
                <textarea required class="form-control noEnter" name="originalPost">${cadre.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>任职日期</label>
            <div class="col-xs-7">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" placeholder="请选择任职日期" type="text"
                           name="_appointDate" data-date-format="yyyy.mm.dd"
                           value="${empty cadre?_today:cm:formatDate(cadre.lpWorkTime,'yyyy.MM.dd')}"/>
                    <span class="input-group-addon">
                        <i class="fa fa-calendar bigger-110"></i></span>
                </div>
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
            <label class="col-xs-3 control-label"><span class="star">*</span>免职日期</label>
            <div class="col-xs-8">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" placeholder="请选择免职日期" type="text"
                           name="_deposeDate" data-date-format="yyyy.mm.dd"/>
                    <span class="input-group-addon">
                        <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">离任后所在单位及职务</label>
            <div class="col-xs-7">
                <textarea class="form-control noEnter" name="title">${cadre.title}</textarea>
            </div>
        </div>
        <c:if test="${fn:length(cadrePosts)>0}">
        <div class="form-group">
            <label class="col-xs-3 control-label">卸任岗位</label>
            <div class="col-xs-8 label-text">
                <c:forEach items="${cadrePosts}" var="cadrePost">
                    <div><input type="checkbox" value="${cadrePost.id}" class="big" name="postIds">
                            ${cadrePost.post}(${cadrePost.isMainPost?"主职":"兼职"})</div>
                </c:forEach>
                <span class="red bolder padding-4">注：勾选后，将取消相关岗位的关联（即成为空岗）。</span>
            </div>
        </div>
        </c:if>

    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>

    var treeNode = ${tree};
    if(treeNode.children.length==0){
        $("#tree3").html("未找到相关发文，可在“组织部发文管理模块”添加发文后再操作");
    }else{
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 1,
            children: treeNode,
            onSelect: function(select, node) {
                //node.expand(node.data.isFolder && node.isSelected());
                $.getJSON("${ctx}/cadre_leave_dispatch",{id:node.data.key},function(data){
                    var workTime = new Date(data.workTime).format("yyyy.MM.dd");
                    $("input[name=_deposeDate]").val(workTime);
                })
            },
            onCustomRender: function(node) {
                if(!node.data.isFolder)
                return "<span class='dynatree-title'>"+node.data.title
                        +"</span>&nbsp;&nbsp;<button class='openUrl btn btn-xs btn-default' data-url='${ctx}/pdf_preview?type=url&path="+node.data.tooltip+"'>查看</button>"
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
                        if(ret.status=='${CADRE_STATUS_CJ_LEAVE}'||ret.status=='${CADRE_STATUS_KJ_LEAVE}')
                            $.hashchange('status='+ret.status, '${ctx}/cadre');
                        if(ret.status=='${CADRE_STATUS_LEADER_LEAVE}')
                            $.hashchange('status='+ret.status, '${ctx}/leaderInfo');
                        //});
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'));
</script>