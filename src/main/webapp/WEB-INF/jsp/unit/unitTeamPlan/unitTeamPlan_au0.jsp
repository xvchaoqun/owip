<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${unitTeamPlan!=null?'编辑':'添加'}班子下的干部配置方案</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitTeamPlan_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitTeamPlan.id}">
        <input type="hidden" name="unitTeamId" value="${param.unitTeamId}">

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>起始时间</label>
				<div class="col-xs-6">
					<div class="input-group">
                    <input required class="form-control date-picker" name="startDate" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(unitTeamPlan.startDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政正职</label>
				<div class="col-xs-8 selectUsers">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
							name="postId0" data-placeholder="请选择">
						<option></option>
					</select>
					<button type="button" class="btn btn-primary btn-sm" onclick="_selectPost(0)"><i
							class="fa fa-plus"></i> 确定
					</button>
				</div>
			</div>
			<div style="margin:0 5px 10px">
				<div id="itemList0" data-type="0" class="itemList">

				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政副职</label>
				<div class="col-xs-8 selectUsers">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
							name="postId1" data-placeholder="请选择">
						<option></option>
					</select>
					<button type="button" class="btn btn-primary btn-sm" onclick="_selectPost(1)"><i
							class="fa fa-plus"></i> 确定
					</button>
				</div>
			</div>
			<div style="margin:0 5px 10px">
				<div id="itemList1" data-type="1" class="itemList">

				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${unitTeamPlan.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${unitTeamPlan!=null}">确定</c:if><c:if test="${unitTeamPlan==null}">添加</c:if></button>
</div>
<script type="text/template" id="itemListTpl">
    <table class="table table-striped table-bordered table-condensed table-center table-unhover2">
        <thead>
        <tr>
            <th>岗位编号</th>
            <th>岗位名称</th>
            <th>岗位级别</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        {{_.each(unitPosts, function(up, idx){ }}
        <tr data-id="{{=up.id}}"
            data-name="{{=up.name}}"
            data-code="{{=up.adminLevel}}">
            <td>{{=up.code}}</td>
            <td style="text-align: left">{{=up.name}}</td>
            <td>{{=_cMap.metaTypeMap[up.adminLevel].name}}</td>
            <td>
                <a href="javasciprt:;" class="del">移除</a>
            </td>
        </tr>
        {{});}}
        </tbody>
    </table>
</script>
<script>

	var selectedMainPosts = ${cm:toJSONArrayWithFilter(mainPosts, "id,code,name,adminLevel")};
	var selectedVicePosts = ${cm:toJSONArrayWithFilter(vicePosts, "id,code,name,adminLevel")};

    $("#itemList0").append(_.template($("#itemListTpl").html())({unitPosts: selectedMainPosts}));
    $("#itemList1").append(_.template($("#itemListTpl").html())({unitPosts: selectedVicePosts}));
    function _selectPost(type) {

        var $select = $("#modalForm select[name=postId"+type+"]");
        var postId = $.trim($select.val());
        if (postId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择。"
            });
            return;
        }

        var selectedPosts = type==0?selectedMainPosts:selectedVicePosts;
        var hasSelected = false;
        $.each(selectedPosts, function (i, post) {
            if (post.id == postId) {
                hasSelected = true;
                return false;
            }
        })
        if (hasSelected) {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "您已经选择了该岗位。"
            });
            return;
        }
        //console.log(user)
		var name = $select.select2("data")[0]['text'] || '';
        var code = $select.select2("data")[0]['code'] || '';
        var adminLevel = $select.select2("data")[0]['adminLevel'] || '';
        var post = {id: postId, name:name, code: code, adminLevel: adminLevel};

		if(type==0){
		    selectedMainPosts.push(post);
		    $("#itemList0").empty().append(_.template($("#itemListTpl").html())({unitPosts: selectedMainPosts}));
		}else{
		    selectedVicePosts.push(post);
		    $("#itemList1").empty().append(_.template($("#itemListTpl").html())({unitPosts: selectedVicePosts}));
		}
    }
    $(document).off("click",".itemList .del")
            .on('click', ".itemList .del", function(){
        var $tr = $(this).closest("tr");
        var postId = $tr.data("id");
        //console.log("userId=" + userId)
		var type = $(this).closest(".itemList").data("type");
		if(type==0) {
            $.each(selectedMainPosts, function (i, val) {
                if (val.id == postId) {
                    selectedMainPosts.splice(i, 1);
                    return false;
                }
            })
        }else{
		   $.each(selectedVicePosts, function (i, val) {
                if (val.id == postId) {
                    selectedVicePosts.splice(i, 1);
                    return false;
                }
            })
		}
        $(this).closest("tr").remove();
    })

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
				data: {mainPosts: $.map(selectedMainPosts,function(val){return val.id;}).join(","),
					vicePosts: $.map(selectedVicePosts,function(val){return val.id;}).join(",")},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid_plan").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.del_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>