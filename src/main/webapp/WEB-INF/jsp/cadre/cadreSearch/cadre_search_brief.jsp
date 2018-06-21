<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>

                </h4>

                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;">提取简介</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
                    <div class="tab-content padding-4" id="step-content">
                        <div style="width: 450px;float: left">
                            <form class="form-horizontal" id="modalForm">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">选择干部</label>

                                    <div class="col-xs-8">
                                        <select name="userId" data-rel="select2-ajax" data-width="220"
                                                data-ajax-url="${ctx}/cadre_selects?key=1&type=2"
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
                                </div>
                            </form>
                        </div>
                        <div style="float: left;font-size: 14pt" id="resultDiv">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer center">
    <button id="copyBtn" class="btn btn-success"><i class="fa fa-copy"></i> 复制到剪贴板</button>
</div>
<script type="text/template" id="itemListTpl">
    <table id="itemTable" class="table table-striped table-bordered table-condensed table-unhover2 table-center">
        <thead class="multi">
        <tr>
            <th width="80">姓名</th>
            <th width="120">工号</th>
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
<script src="${ctx}/extend/js/ZeroClipboard.min.js"></script>
<script>
    ZeroClipboard.config({swfPath: '${ctx}/extend/js/ZeroClipboard.swf'});
    var clip = new ZeroClipboard($("#copyBtn"));
    clip.on('ready', function () {
        this.on( 'copy', function(event) {
            event.clipboardData.setData('text/plain', $("#resultDiv").text().replaceAll("\\s+", "\n"));
        } );
        this.on('aftercopy', function (event) {
            SysMsg.success('内容已经复制剪贴板');
        });
    });
    clip.on('error', function (event) {
        ZeroClipboard.destroy();
    });

    $.register.user_select($('[data-rel="select2-ajax"]'));
    var selectedItems = [];
    $("#itemList").append(_.template($("#itemListTpl").html())({users: selectedItems}));
    function _selectUser() {

        var $select = $("#modalForm select[name=userId]");
        var userId = $.trim($select.val());
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择干部。"
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
                msg: "您已经选择了该干部。"
            });
            return;
        }

        var realname = $select.select2("data")[0]['text'] || '';
        var code = $select.select2("data")[0]['code'] || '';
        var user = {userId: userId, realname: realname, code: code};

        //console.log(user)
        selectedItems.push(user);
        $("#itemList").empty().append(_.template($("#itemListTpl").html())({users: selectedItems}));

        getCopyStr();
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
                getCopyStr();
            });

    function getCopyStr() {

        var userIds = $.map(selectedItems, function (user) {
            return user.userId;
        });
        $.post("${ctx}/cadre_search_brief", {userIds: userIds}, function (ret) {

            $("#resultDiv").html(ret);
            clip.setData("设置用于复制的文本内容");

        })

    }
</script>