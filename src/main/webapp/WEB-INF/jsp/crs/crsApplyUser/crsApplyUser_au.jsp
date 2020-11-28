<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsApplyUser!=null}">编辑</c:if><c:if test="${crsApplyUser==null}">添加</c:if>补报人员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsApplyUser_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
            <input type="hidden" name="id" value="${crsApplyUser.id}">
            <input type="hidden" name="postId" value="${crsPost.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">报名人员</label>
                <c:if test="${empty crsApplyUser}">
				<div class="col-xs-9">
                    <select name="userId" data-rel="select2-ajax" data-width="270"
                            data-ajax-url="${ctx}/sysUser_selects?type=${USER_TYPE_JZG}"
                            data-placeholder="请输入账号或姓名或学工号">
                        <option></option>
                    </select>
                    <button type="button" class="btn btn-success btn-sm" onclick="_selectUser()"><i
                            class="fa fa-plus"></i> 选择
                    </button>
                    <div style="padding-top: 10px;width:270px;">
                        <div id="itemList" style="max-height: 300px;overflow-y: auto;">

                        </div>
                    </div>
				</div>
                </c:if>
                <c:if test="${not empty crsApplyUser}">
                    <div class="col-xs-9 label-text">
                    ${crsApplyUser.user.realname}
                    </div>
                 </c:if>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>补报开始时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text"  name="startTime"
                           value="${cm:formatDate(crsApplyUser.startTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>补报结束时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text"  name="endTime"
                           value="${cm:formatDate(crsApplyUser.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited"
                              name="remark">${crsApplyUser.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${crsApplyUser!=null}">确定</c:if><c:if test="${crsApplyUser==null}">添加</c:if></button>
</div>
<script type="text/template" id="itemListTpl">
    <table id="itemTable" class="table table-striped table-bordered table-condensed table-unhover2 table-center">
        <thead class="multi">
        <tr>
            <th width="80">姓名</th>
            <th width="120">工作证号</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        {{_.each(users, function(u, idx){ }}
        <tr data-user-id="{{=u.userId}}">
            <td>{{=u.realname}}</td>
            <td>{{=u.code}}</td>
            <td>
                <a href="javasciprt:;" class="del">移除</a>
            </td>
        </tr>
        {{});}}
        </tbody>
    </table>
</script>
<script>
    $.register.user_select($('[data-rel="select2-ajax"]'));
    var selectedItems=[];
    $("#itemList").append(_.template($("#itemListTpl").html())({users: selectedItems}));
    function _selectUser() {

        var $select = $("#modalForm select[name=userId]");
        var userId = $.trim($select.val());
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择报名人员。"
            });
            return;
        }
        var hasSelected = false;
        $.each(selectedItems, function (i, user) {
            if (user.userId == userId) {
                hasSelected = true;
                return false;
            }
        })
        if (hasSelected) {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "您已经选择了该报名人员。"
            });
            return;
        }

        var realname = $select.select2("data")[0]['text'] || '';
        var code = $select.select2("data")[0]['code'] || '';
        var user = {userId: userId, realname: realname, code: code};

        //console.log(user)
        selectedItems.push(user);
        $("#itemList").empty().append(_.template($("#itemListTpl").html())({users: selectedItems}));
    }
    $(document).off("click", "#itemList .del")
            .on('click', "#itemList .del", function () {
                var $tr = $(this).closest("tr");
                var userId = $tr.data("user-id");
                //console.log("userId=" + userId)
                $.each(selectedItems, function (i, user) {
                    if (user.userId == userId) {
                        selectedItems.splice(i, 1);
                        return false;
                    }
                })
                $(this).closest("tr").remove();
            });

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var userIds = $.map(selectedItems, function (user) {
                return user.userId;
            });
            <c:if test="${empty crsApplyUser}">
            if(userIds.length==0) {
                var $select = $("#modalForm select[name=userId]");
                $.tip({
                    $target: $select.closest("div").find("button"),
                    at: 'top center', my: 'bottom center', type: 'success',
                    msg: "请选择补报人员。"
                });
                return false;
            }
            </c:if>
            $(form).ajaxSubmit({
                data: {userIds: userIds},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.datetime($('.datetime-picker'));
</script>