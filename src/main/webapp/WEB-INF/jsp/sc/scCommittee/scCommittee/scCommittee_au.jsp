<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    常委会文件预览
                    <div style="position: absolute; left:180px;top:8px;">
                        <form action="${ctx}/sc/scCommittee_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传常委会文件
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
                        <c:import url="${ctx}/pdf_preview?type=html&path=${scCommittee.filePath}"/>
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
                        ${scCommittee!=null?"修改":"添加"}信息
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scCommittee_au" id="committeeForm" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <input type="hidden" name="id" value="${scCommittee.id}">
                                <input type="hidden" name="filePath" value="${scCommittee.filePath}">

                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>年份</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" placeholder="请选择年份"
                                                   name="year"
                                                   type="text"
                                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                                   value="${empty scCommittee.year?_thisYear:scCommittee.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>党委常委会日期</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" name="holdDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(scCommittee.holdDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>常委总数</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control digits"
                                               autocomplete="off" disableautocomplete
                                               type="text" name="committeeMemberCount" value="${committeeMemberCount}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>实际参会常委</label>

                                    <div class="col-xs-8 selectUsers">
                                        <select class="multiselect" name="userId0" multiple="">
                                            <c:forEach items="${committeeMembers}" var="m">
                                                <option value="${m.userId}">${m.realname}-${m.title}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="button" class="btn btn-primary btn-sm" onclick="_selectUser(0)"><i
                                                class="fa fa-plus"></i> 确定
                                        </button>
                                    </div>
                                </div>
                                <div style="margin:0 5px 10px">
                                    <div id="itemList0" class="itemList">

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">请假常委</label>

                                    <div class="col-xs-8 selectUsers">

                                        <select class="multiselect" name="userId1" multiple="">
                                            <c:forEach items="${committeeMembers}" var="m">
                                                <option value="${m.userId}">${m.realname}-${m.title}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="button" class="btn btn-primary btn-sm" onclick="_selectUser(1)"><i
                                                class="fa fa-plus"></i> 确定
                                        </button>
                                    </div>

                                </div>
                                <div style="margin:0 5px 10px">
                                    <div id="itemList1" class="itemList">

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">列席人</label>

                                    <div class="col-xs-6">
                                        <input class="form-control" type="text"
                                               autocomplete="off" disableautocomplete
                                               name="attendUsers" value="${scCommittee.attendUsers}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">上会PPT</label>

                                    <div class="col-xs-6">
                                        <input class="form-control" type="file" name="_pptFile"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>议题数量</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control digits"
                                               autocomplete="off" disableautocomplete
                                               type="text" name="topicNum" value="${scCommittee.topicNum}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">会议记录</label>

                                    <div class="col-xs-6">
                                        <input class="form-control" type="file" name="_logFile"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">备注</label>

                                    <div class="col-xs-6">
                                        <textarea class="form-control limited"
                                                  name="remark">${scCommittee.remark}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <button id="committeeBtn"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                                        class="btn btn-info btn-sm" type="button">
                                    <i class="ace-icon fa fa-check "></i>
                                    ${scCommittee!=null?"修改":"添加"}
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
    <table class="table table-striped table-bordered table-condensed table-center table-unhover2">
        <thead>
        {{if(!isAbsent){}}
        <tr>
            <th colspan="4" style="text-align: left">
                实际参会常委数：<span id="realCount"></span>人 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实际参会比例：<span id="realRate"></span>
            </th>
        </tr>
        {{}}}
        {{if(isAbsent){}}
        <tr>
            <th colspan="4" style="text-align: left">
                请假常委数：<span id="absentCount"></span>人
            </th>
        </tr>
        {{}}}
        <tr>
            <th>姓名</th>
            <th>工号</th>
            <th>所在单位及职务</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        {{_.each(users, function(u, idx){ }}
        <tr data-user-id="{{=u.userId}}"
            data-realname="{{=u.realname}}"
            data-code="{{=u.code}}"
            data-title="{{=u.title}}"
            data-is-absent="{{=u.isAbsent}}">
            <td>{{=u.realname}}</td>
            <td>{{=u.code}}</td>
            <td style="text-align: left">{{=u.title}}</td>
            <td>
                <a href="javasciprt:;" class="del">移除</a>
                {{if(idx>0){}}
                <a href="javasciprt:;" class="up">上移</a>
                {{}}}
            </td>
        </tr>
        {{});}}
        </tbody>
    </table>
</script>
<script>
    $.register.multiselect($('#committeeForm select[name=userId0]'));
    $.register.multiselect($('#committeeForm select[name=userId1]'));

    $.fileInput($("#committeeForm input[name=_pptFile]"));

    $.fileInput($("#committeeForm input[name=_logFile]"), {
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

    var committeeMembers = ${cm:toJSONArrayWithFilter(committeeMembers, "userId,code,realname,title")};
    var memberMap = [];
    var selectedUsers = ${cm:toJSONArrayWithFilter(memberUserList, "userId,code,realname,title,isAbsent")};
    $.each(committeeMembers, function (i, m) {
        memberMap[m.userId] = m;
    })
    //console.log(memberMap)
    function _displayItemList(isAbsent) {
        var users = $.map(selectedUsers, function (user) {
            if (user.isAbsent == (isAbsent == 1))
                return user;
        });
        if (users.length == 0) return;

        $("#itemList" + isAbsent).empty().append(_.template($("#itemListTpl").html())({
            users: users,
            isAbsent: isAbsent
        }));

        $.each(selectedUsers, function (i, u) {
            $(".selectUsers input[value='" + u.userId + "']").prop('disabled', true)
                    .parent('label').parent('a').parent('li').addClass('disabled');
            //$("#selectUsers option[value='"+ u.userId +"']").prop('disabled', true)
        })

        _updateCount(isAbsent == 1)
    }
    function _updateCount(isAbsent) {
        if (isAbsent) {
            var absentCount = 0;
            $.each(selectedUsers, function (i, user) {
                if (user.isAbsent) absentCount++;
            });
            $("#absentCount").html(absentCount);
        } else {
            var realCount = 0;
            $.each(selectedUsers, function (i, user) {
                if (!user.isAbsent) realCount++;
            });
            var totalCount = ${committeeMemberCount};
            $("#realCount").html(realCount);
            $("#realRate").html((totalCount == 0) ? '-' : (Math.formatFloat(100 * realCount / totalCount, 1)) + "%");
        }
    }
    _displayItemList(0)
    _displayItemList(1)

    function _selectUser(isAbsent) {

        var $select = $("#committeeForm select[name=userId" + isAbsent + "]");
        //console.log($select.val())
        var userIds = $select.val();
        if ($.trim(userIds) == '') {
            return;
        }
        //console.log(userIds)
        var hasSelectedUserIds = [];
        $.each(selectedUsers, function (i, user) {
            if ($.inArray(user.userId + "", userIds) >= 0) {
                hasSelectedUserIds.push(user.userId);
            }
        })
        //console.log("hasSelectedUserIds="+hasSelectedUserIds)
        $.each(userIds, function (i, userId) {
            userId = parseInt(userId);
            if (hasSelectedUserIds.length == 0 || $.inArray(userId, hasSelectedUserIds) == -1) {
                //console.log(memberMap[userId])
                selectedUsers.push($.extend(memberMap[userId], {isAbsent: (isAbsent == 1)}));
            }
        })

        //console.log(selectedUsers)
        $select.multiselect('deselectAll', false).multiselect('updateButtonText');
        _displayItemList(isAbsent);
    }

    $(document).off("click", ".del")
            .on('click', ".del", function () {
                var $tr = $(this).closest("tr");
                var $table = $(this).closest("table");
                var userId = $tr.data("user-id");
                //console.log("userId=" + userId)
                $.each(selectedUsers, function (i, user) {
                    if (user.userId == userId) {
                        selectedUsers.splice(i, 1);
                        return false;
                    }
                })

                _updateCount($tr.data("is-absent"));
                $(this).closest("tr").remove();
                if ($("tbody tr", $table).length == 0) {
                    $table.remove();
                }

                $(".selectUsers input[value='" + userId + "']").prop('disabled', false)
                        .parent('label').parent('a').parent('li').removeClass('disabled');
                //$("#selectUsers option[value='"+ userId +"']").prop('disabled', false)
            });
    $(document).off("click", ".up")
            .on('click', ".up", function () {
                var $tr = $(this).parents("tr");
                if ($tr.index() == 0) {
                    //alert("首行数据不可上移");
                } else {
                    //$tr.fadeOut().fadeIn();
                    $tr.prev().before($tr);
                }

                selectedUsers = $.map($(".itemList tbody tr"), function (tr) {
                    return {
                        userId: $(tr).data("user-id"), realname: $(tr).data("realname"),
                        code: $(tr).data("code"), title: $(tr).data("title"), isAbsent: $(tr).data("is-absent")
                    };
                });
                //console.log(selectedUsers)
                _displayItemList(0)
                _displayItemList(1)
            });
    $.register.user_select($('#committeeForm [data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
    $('#committeeForm [data-rel="select2"]').select2();
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

                        $("#committeeForm input[name=filePath]").val(ret.filePath);
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

    $("#committeeBtn").click(function () {

        console.log(selectedUsers)
        if (selectedUsers.length == 0) {
            var $select = $("#committeeForm select[name=userId0]");
            $.tip({
                $target: $select.closest("div").find(".btn-group"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择常委。"
            });
        }

        $("#committeeForm").submit();
        return false;
    });
    $("#committeeForm").validate({
        submitHandler: function (form) {
            if (selectedUsers.length == 0) {
                return;
            }
            var $btn = $("#committeeBtn").button('loading');
            $(form).ajaxSubmit({
                data: {items: $.base64.encode(JSON.stringify(selectedUsers))},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>