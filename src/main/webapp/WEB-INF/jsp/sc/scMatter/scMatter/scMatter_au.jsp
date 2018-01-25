<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scMatter!=null}">编辑</c:if><c:if test="${scMatter==null}">添加</c:if>个人有关事项</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatter_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMatter.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">年度</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                           type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2" value="${scMatter.year}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">填报类型</label>

            <div class="col-xs-6 label-text">
                ${type?'个别填报':'年度集中填报'}
                <input type="hidden" name="type" value="${type}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">领表时间</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text" name="drawTime"
                           value="${cm:formatDate(scMatter.drawTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">应交回时间</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text" name="handTime"
                           value="${cm:formatDate(scMatter.handTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">填报对象</label>

            <div class="col-xs-8">
                <c:if test="${type}">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?type=${USER_TYPE_JZG}"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option></option>
                    </select>
                    <button type="button" class="btn btn-success btn-sm" onclick="_selectUser()"><i class="fa fa-plus"></i> 选择
                    </button>
                    <div style="min-height: 100px; padding-top: 10px;">
                        <div id="itemList">

                        </div>
                    </div>
                </c:if>
                <c:if test="${!type}">
                    <div id="tree3" style="height: 300px;">
                        <div style="height: 300px;line-height: 300px;font-size: 20px">
                            <i class="fa fa-spinner fa-spin"></i> 加载中，请稍后...
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark">${scMatter.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if
            test="${scMatter!=null}">确定</c:if><c:if test="${scMatter==null}">添加</c:if></button>
</div>
<style>
    #itemList table td {
        text-align: center;
    }
</style>
<script type="text/template" id="itemListTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td colspan="3">已选择的填报对象</td>
        </tr>
        <tr>
            <td>姓名</td>
            <td>工号</td>
            <td></td>
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
    <c:if test="${!type}">
    $.getJSON("${ctx}/sc/scMatter_selectCadres_tree", {id: "${param.id}"}, function (data) {
        var treeData = data.tree.children;
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function (select, node) {

                node.expand(node.data.isFolder && node.isSelected());
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });
    </c:if>
    <c:if test="${type}">
    var selectedUsers = ${cm:toJSONArrayWithFilter(itemUserList, "userId,code,realname")};
    $("#itemList").append(_.template($("#itemListTpl").html())({users: selectedUsers}));
    function _selectUser() {

        var $select = $("#modalForm select[name=userId]");
        var userId = $.trim($select.val());
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择填报对象。"
            });
            return;
        }
        var hasSelected = false;
        $.each(selectedUsers, function (i, user) {
            if (user.userId == userId) {
                hasSelected = true;
                return false;
            }
        })
        if (hasSelected) {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "您已经选择了该填报对象。"
            });
            return;
        }

        var realname = $select.select2("data")[0]['text'] || '';
        var code = $select.select2("data")[0]['code'] || '';
        var user = {userId: userId, realname:realname, code: code};

        //console.log(user)
        selectedUsers.push(user);
        $("#itemList").empty().append(_.template($("#itemListTpl").html())({users: selectedUsers}));
    }
    $(document).off("click","#itemList .del")
            .on('click', "#itemList .del", function(){
        var $tr = $(this).closest("tr");
        var userId = $tr.data("user-id");
        //console.log("userId=" + userId)
        $.each(selectedUsers, function (i, user) {
            if (user.userId == userId) {
                selectedUsers.splice(i, 1);
                return false;
            }
        })
        $(this).closest("tr").remove();
    })
    </c:if>

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            <c:if test="${!type}">
            var userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                if (!node.data.isFolder && !node.data.hideCheckbox)
                    return node.data.key;
            });
            </c:if>
            <c:if test="${type}">
            var userIds = $.map(selectedUsers, function (user) {
                return user.userId;
            })
            </c:if>
            $(form).ajaxSubmit({
                data: {userIds: userIds},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    register_user_select($('#modal [data-rel="select2-ajax"]'));
    register_date($('.date-picker'));
    register_datetime($('.datetime-picker'));
    $('textarea.limited').inputlimiter();
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>