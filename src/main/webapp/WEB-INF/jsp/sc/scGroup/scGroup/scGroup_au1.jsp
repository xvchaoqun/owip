<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    干部小组会议题预览
                    <div style="position: absolute; left:200px;top:0px;">
                        <form action="${ctx}/sc/scGroup_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传干部小组会议题
                            </button>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right ">

                        <a href="javascript:;" class="hideView btn btn-xs btn-success"
                           style="margin-right: 10px; top: -5px;">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/pdf_preview?type=html&path=${scGroup.filePath}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        ${scGroup!=null?"修改":"添加"}干部小组会
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scGroup_au" autocomplete="off" disableautocomplete id="modalForm" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <input type="hidden" name="id" value="${scGroup.id}">
                                <input type="hidden" name="filePath" value="${scGroup.filePath}">

                                <div class="form-group">
                                    <label class="col-xs-3 control-label"><span class="star">*</span>年份</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" placeholder="请选择年份"
                                                   name="year"
                                                   type="text"
                                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                                   value="${scGroup.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label"><span class="star">*</span>干部小组会日期</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" name="holdDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(scGroup.holdDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label"><span class="star">*</span>议题数量</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control num" type="text" name="topicNum" value="${scGroup.topicNum}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">议题word版</label>
                                    <div class="col-xs-6">
                                        <input class="form-control" type="file" name="_wordFilePath"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">会议记录</label>
                                    <div class="col-xs-6">
                                        <input class="form-control" type="file" name="_logFile"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">参会人</label>

                                    <div class="col-xs-8">
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sc/scGroupMember_selects"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option></option>
                                        </select>
                                        <button type="button" class="btn btn-success btn-sm" onclick="_selectUser()"><i
                                                class="fa fa-plus"></i> 选择
                                        </button>
                                        <div style="padding-top: 10px;">
                                            <div id="itemList">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">列席人</label>

                                    <div class="col-xs-6">
                                        <input class="form-control" type="text" name="attendUsers" value="${scGroup.attendUsers}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-3 control-label">备注</label>

                                    <div class="col-xs-6">
                                        <textarea class="form-control limited"
                                                  name="remark">${scGroup.remark}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    ${scGroup!=null?"修改":"添加"}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/template" id="itemListTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td colspan="3">已选择的参会人</td>
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
    $.fileInput($("#modalForm input[name=_wordFilePath]"),{
        allowExt: ['doc', 'docx'],
        //allowMime: ['application/pdf']
    });
    $.fileInput($("#modalForm input[name=_logFile]"),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

    var selectedUsers = ${cm:toJSONArrayWithFilter(memberUserList, "userId,code,realname")};
    $("#itemList").append(_.template($("#itemListTpl").html())({users: selectedUsers}));
    function _selectUser() {

        var $select = $("#modalForm select[name=userId]");
        var userId = $.trim($select.val());
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择参会人。"
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
                msg: "您已经选择了该参会人。"
            });
            return;
        }

        var realname = $select.select2("data")[0]['text'] || '';
        var code = $select.select2("data")[0]['code'] || '';
        var user = {userId: userId, realname: realname, code: code};

        //console.log(user)
        selectedUsers.push(user);
        $("#itemList").empty().append(_.template($("#itemListTpl").html())({users: selectedUsers}));
    }
    $(document).off("click", "#itemList .del")
            .on('click', "#itemList .del", function () {
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
            });
    $.register.user_select($('#modalForm [data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
    $("#upload-file").change(function () {
        if ($("#upload-file").val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var viewHtml = $("#dispatch-file-view").html()
            $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $("#dispatch-file-view").load("${ctx}/pdf_preview?type=html&path=" + encodeURI(ret.filePath));

                        $("#modalForm input[name=filePath]").val(ret.filePath);
                    } else {
                        $("#dispatch-file-view").html(viewHtml)
                    }
                    $btn.button('reset');
                    $this.removeAttr("disabled");
                }
            });
            $this.attr("disabled", "disabled");
        }
    });

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var userIds = $.map(selectedUsers, function (user) {
                return user.userId;
            });
           /* console.log(userIds.length);
            return;*/
            if (userIds.length==0) {
                var $select = $("#modalForm select[name=userId]");
                $.tip({
                    $target: $select.closest("div").find(".select2-container"),
                    at: 'top center', my: 'bottom center', type: 'info',
                    msg: "请选择。"
                });
                return;
            }
            $(form).ajaxSubmit({
                data: {userIds: userIds},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>