<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    函询文件预览
                    <div style="position: absolute; left:125px;top:8px;">
                        <form action="${ctx}/sc/scLetter_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传函询文件
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
                        <c:import url="${ctx}/swf/preview?type=html&path=${scLetter.filePath}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        ${scLetter!=null?"修改":"添加"}函询文件
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scLetter_au" id="modalForm" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <input type="hidden" name="id" value="${scLetter.id}">
                                <input type="hidden" name="filePath" value="${scLetter.filePath}">
                                <input type="hidden" name="fileName" value="${scLetter.fileName}">

                                <div class="form-group">
                                    <label class="col-xs-3 control-label">年份</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" placeholder="请选择年份"
                                                   name="year"
                                                   type="text"
                                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                                   value="${scLetter.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">类型</label>

                                    <div class="col-xs-6">
                                        <select data-rel="select2" name="type" data-placeholder="请选择" data-width="240">
                                            <option></option>
                                            <c:import url="/metaTypes?__code=mc_sc_letter_type"/>
                                        </select>
                                        <script type="text/javascript">
                                            $("#modalForm select[name=type]").val(${scLetter.type});
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">函询编号</label>

                                    <div class="col-xs-6">
                                        <input class="form-control digits" type="text" name="num"
                                               value="${scLetter.num}">
                                        <span class="label-inline"> * 留空自动生成</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">函询日期</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" name="queryDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(scLetter.queryDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-3 control-label">函询对象</label>

                                    <div class="col-xs-8">
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option></option>
                                        </select>
                                        <button type="button" class="btn btn-success btn-sm" onclick="_selectUser()"><i
                                                class="fa fa-plus"></i> 选择
                                        </button>
                                        <div style="padding-top: 10px;">
                                            <div id="itemList" style="height: 382px;overflow-y: auto;">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">备注</label>

                                    <div class="col-xs-6">
                                        <textarea class="form-control limited"
                                                  name="remark">${scLetter.remark}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    ${scLetter!=null?"修改":"添加"}
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
    <table id="itemTable" class="table table-striped table-bordered table-condensed table-unhover2 table-center">
        <thead class="multi">
        <tr>
            <th colspan="3">已选择的函询对象</th>
        </tr>
        <tr>
            <th>姓名</th>
            <th>工号</th>
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

    var selectedItems = ${cm:toJSONArrayWithFilter(itemList, "userId,code,realname")};
    $("#itemList").append(_.template($("#itemListTpl").html())({users: selectedItems}));
    stickheader($("#itemTable"));

    function _selectUser() {

        var $select = $("#modalForm select[name=userId]");
        var userId = $.trim($select.val());
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择函询对象。"
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
                msg: "您已经选择了该函询对象。"
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
                        $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.filePath));

                        $("#modalForm input[name=filePath]").val(ret.filePath);
                        $("#modalForm input[name=fileName]").val(ret.fileName);
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
            var userIds = $.map(selectedItems, function (user) {
                return user.userId;
            });
            if ($.trim($("#modalForm input[name=filePath]").val()) == "") {
                SysMsg.warning("请上传函询文件");
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