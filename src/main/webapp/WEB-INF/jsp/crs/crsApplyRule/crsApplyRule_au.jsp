<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsApplyRule!=null}">编辑</c:if><c:if test="${crsApplyRule==null}">添加</c:if>报名规则</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsApplyRule_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsApplyRule.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所含招聘岗位</label>
				<div class="col-xs-6">
                    <input type="hidden" name="postIds">
                    <div id="postIdsDiv">
                        <c:forEach items="${crsApplyRule.crsPosts}" var="crsPost">
                        <div class="post" data-id="${crsPost.id}">${crsPost.name}
                            <a href="javascript:;" class="del btn btn-xs"><i class="fa fa-times"></i></a>
                        </div>
                        </c:forEach>
                    </div>
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/crsPost_selects" data-width="200"
                            name="postId" data-placeholder="请输入岗位名称">
                        <option></option>
                    </select>
                    <a href="javascript:;" class="btn btn-xs btn-success" onclick="_addPost()"><i class="fa fa-plus"></i> 添加</a>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">最多同时报名个数</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="num" value="${crsApplyRule.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">有效期起始时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control datetime-picker" required type="text" name="startTime"
                               value="${cm:formatDate(crsApplyRule.startTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">有效期截止时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control datetime-picker" required type="text" name="endTime"
                               value="${cm:formatDate(crsApplyRule.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${crsApplyRule!=null}">确定</c:if><c:if test="${crsApplyRule==null}">添加</c:if>"/>
</div>
<style>
    #postIdsDiv .post{
        padding: 3px 0px;
    }
</style>
<script type="text/template" id="post_tpl">
    <div class="post" data-id="{{=postId}}">{{=postName}}
        <a href="javascript:;" class="del btn btn-xs"><i class="fa fa-times"></i></a>
    </div>
</script>
<script>
    register_datetime($('.datetime-picker'));

    register_ajax_select($("select[name=postId]", "#modalForm"))
    var $selectPost =  $("select[name=postId]", "#modalForm");
    var $target = $selectPost.closest("div").find(".select2-container");
    function _addPost(){
        var postId = $selectPost.val();
        if(postId==''){
            $.tip({$target:$target, msg:"请选择招聘岗位", my:"bottom center", at:"top center"});
            return;
        }
        postId = parseInt(postId);
        var postName = $selectPost.find("option:selected").text();
        var selectedPostIds = [];
        $("#postIdsDiv").find(".post").each(function(){
            selectedPostIds.push(parseInt($(this).data("id")));
        })
        //console.log("+++++++++" + selectedPostIds + "++" + postId + "++" + $.inArray(postId, selectedPostIds))
        if($.inArray(postId, selectedPostIds)>-1){
            $.tip({$target:$target, msg:"已经选择了该招聘岗位", my:"bottom center", at:"top center"});
            return;
        }
        var postDiv = _.template($("#post_tpl").html().NoMultiSpace())({postId: postId, postName:postName});
        $("#postIdsDiv").append(postDiv);

        $selectPost.val(null).trigger("change");
    }

    $(document).on('click', ".del", "#postIdsDiv", function(){
        //console.log("-----------")
        $(this).closest(".post").remove();
    });

    $("#modalForm").validate({
        submitHandler: function (form) {

            var selectedPostIds = [];
            $("#postIdsDiv").find(".post").each(function(){
                selectedPostIds.push($(this).data("id"));
            });
            if(selectedPostIds.length==0){
                $.tip({$target:$target, msg:"请选择招聘岗位", my:"bottom center", at:"top center"});
                return;
            }
            $("input[name=postIds]", "#modalForm").val(selectedPostIds.join(","));

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>