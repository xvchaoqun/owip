<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OA_TASK_STATUS_ABOLISH" value="<%=OaConstants.OA_TASK_STATUS_ABOLISH%>"/>
<c:set var="OA_TASK_STATUS_FINISH" value="<%=OaConstants.OA_TASK_STATUS_FINISH%>"/>
<c:set var="taskCanEdit" value="${oaTask.status!=OA_TASK_STATUS_ABOLISH&&oaTask.status!=OA_TASK_STATUS_FINISH}"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <span class="bolder" style="cursor: auto;padding-left: 20px;font-size:larger">
            选择任务对象（${oaTask.name}）
        </span>
    </div>
    <div class="widget-body" style="max-width: 1225px">
        <div class="widget-main padding-4">
            <div id="selectDiv" class="tab-content padding-8 row">

                <div style="width: 450px;float: left;margin-right: 25px">
                    <div class="widget-box" style="height: 530px;">
                        <div class="widget-header">
                            <h4 class="widget-title">
                                选择方式
                            </h4>
                        </div>
                        <div class="widget-body">
                            <div class="widget-main" style="padding-bottom: 0px">
                                <div class="row" style="padding: 20px;">
                                    <h4>方式一：从现任干部库中选择

                                        <button data-url="${ctx}/oa/oaTask_selectCadres" type="button"
                                                style="margin-left: 20px;"
                                                class="popupBtn btn btn-success"><i class="fa fa-plus-square-o"></i> 选择
                                        </button>
                                    </h4>
                                    <hr>

                                    <h4>方式二：从Excel中导入</h4>
                                    <hr>
                                    <form class="form-horizontal" action="${ctx}/oa/oaTask_importUsers"
                                          autocomplete="off"
                                          disableautocomplete id="importForm" method="post"
                                          enctype="multipart/form-data">
                                        <input type="file" name="xlsx"
                                               data-at="bottom center" data-my="top center"
                                               required extension="xlsx"/>
                                        <button id="importBtn" type="button" class="btn btn-success"
                                                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 导入中">
                                            <i class="fa fa-upload"></i> 导入
                                        </button>
                                        <div style="clear: both">
                                            <span class="help-inline">（点击下载 <a
                                                    href="${ctx}/attach?code=sample_oaTaskUser">导入样表.xlsx</a>）</span>
                                        </div>
                                    </form>
                                    <hr>
                                    <h4>方式三：从人事库中选择</h4>
                                    <hr>
                                    <select data-rel="select2-ajax" data-width="200"
                                            data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                            name="userId" data-placeholder="请输入账号或姓名或工号">
                                        <option></option>
                                    </select>
                                    <input type="text" name="mobile" placeholder="手机号码" style="width: 130px">
                                    <button id="selectBtn" type="button" class="btn btn-success"><i
                                            class="fa fa-plus"></i> 添加
                                    </button>
                                    <hr>
                                    <div class="col-xs-12" style="padding: 0 5px">
                                        <div style="font-weight: bolder">使用说明：</div>
                                        <ol style="padding-left: 5px;">
                                            <li>选择后的任务对象可进行拖动排序，排序不影响任务报送</li>
                                            <li class="text-danger bolder">任务对象选择后，请务必点击“保存”进行保存</li>
                                        </ol>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="width: 750px;float: left;">
                    <div class="widget-box">
                        <div class="widget-header">
                            <h4 class="widget-title">
                                任务对象列表
                                <span class="tip">已选<span
                                        class="count">${fn:length(selectUsers)}</span>人，请确认准确无误。</span>
                            </h4>
                        </div>
                        <div class="widget-body">
                            <div class="widget-main" style="padding:5px">
                                <table id="jqGrid2" data-width-reduce="20"
                                       class="table-striped"></table>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="clear: both"></div>
                <div class="clearfix form-actions center" style="margin-top: 5px">
                    <button id="submitBtn" class="btn btn-primary" type="button"
                            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 保存中，请不要关闭此窗口"
                            data-publish="0">
                        <i class="ace-icon fa fa-edit bigger-110"></i>
                        保存
                    </button>
                </div>
            </div>
            <!-- /.widget-main -->
        </div>
        <!-- /.widget-body -->
    </div>
    <!-- /.widget-box -->
</div>
<style>
    .widget-box .tip {
        margin-left: 30px;
        font-size: 16px;
        color: darkgreen;
    }

    .widget-box .tip .count {
        color: darkred;
        font-size: 24px;
        font-weight: bolder;
    }

    .ace-file-input {
        width: 280px;
        float: left;
        margin-right: 20px;
    }
</style>
<script>

    <c:if test="${!taskCanEdit}">
        $("#selectDiv button").prop("disabled", true);
    </c:if>

    var $userSelect = $("#selectDiv select[name=userId]");
    $.register.user_select($userSelect);
    $userSelect.on("change", function () {
        var ret = $(this).select2("data")[0];
        $('#selectDiv input[name=mobile]').val(ret.msgMobile || ret.mobile);
    });

    var $jqGrid = $("#jqGrid2");
    $("#selectBtn").click(function () {

        var userId = $userSelect.val();
        if ($.trim(userId) == '') {
            $.tip({
                $target: $userSelect.closest("div").find(".select2-container"),
                at: 'bottom center', my: 'top center', type: 'info',
                msg: "请选择。"
            });
            return;
        }
        var $mobile = $('#selectDiv input[name=mobile]');
        if ($.trim($mobile.val()) == '') {
            $.tip({
                $target: $mobile,
                at: 'bottom center', my: 'top center', type: 'info',
                msg: "请输入手机号码。"
            });
            return;
        }
        $.post("${ctx}/oa/oaTask_selectUser", {userId: userId, mobile: $mobile.val()}, function (ret) {

            //console.log(ret)
            if (ret.success) {
                //console.log(ret.user)

                var user = ret.user;
                var rowData = $jqGrid.getRowData(user.userId);
                if (rowData.userId == undefined) {
                    $jqGrid.jqGrid("addRowData", user.userId, user, "last");
                } else {
                    $jqGrid.delRowData(user.userId);
                    $jqGrid.jqGrid("addRowData", user.userId, user, "last");
                }

                $userSelect.val(null).trigger("change");
                _showCount();
            }
        });
    });

    $.fileInput($("#importForm input[name=xlsx]"), {
        no_file: '请选择Excel文件 ...',
        allowExt: ['xlsx'],
        width: 200
    });

    $("#importBtn").click(function () {
        $("#importForm").submit();
        return false;
    });
    $("#importForm").validate({
        submitHandler: function (form) {
            var $btn = $("#importBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {

                        $.each(ret.users, function (i, user) {
                            var rowData = $jqGrid.getRowData(user.userId);
                            if (rowData.userId == undefined) {
                                $jqGrid.jqGrid("addRowData", user.userId, user, "last");
                            } else {
                                $jqGrid.delRowData(user.userId);
                                $jqGrid.jqGrid("addRowData", user.userId, user, "last");
                            }
                        })
                        _showCount();
                        $("#importForm input[name=xlsx]").ace_file_input('reset_input');
                    }
                    $btn.button('reset');
                }
            });
        }
    });

    var selectUsers = ${cm:toJSONArray(selectUsers)};
    $("#jqGrid2").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 438,
        width: 730,
        datatype: "local",
        rowNum: selectUsers.length,
        data: selectUsers,
        //工作证号、姓名、所在单位及职务
        colModel: [
            <c:if test="${taskCanEdit}">
            {
                label: '移除', name: '_remove', width: 90, formatter: function (cellvalue, options, rowObject) {
                    //console.log(options)
                    return '<button class="delRowBtn btn btn-danger btn-xs" type="button" data-id="{0}"><i class="fa fa-minus-circle"></i> 移除</button>'
                        .format(rowObject.userId)
                }
            },
            </c:if>
            {label: '工作证号', name: 'code', width: 120},
            {label: '姓名', name: 'realname'},
            {label: '手机号码', name: 'mobile', width: 150},
            {
                label: '所在单位及职务',
                name: 'title',
                width: 320,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.trim(cellvalue)
                }
            },
            {hidden: true, key: true, name: 'userId'}
        ]
    })
    <c:if test="${taskCanEdit}">
    $("#jqGrid2").jqGrid('sortableRows')
    </c:if>
    $(document).on("click", ".delRowBtn", function () {
        var $jqGrid = $("#jqGrid2");
        $jqGrid.delRowData($(this).data("id"));

        _showCount();
    })

    function _showCount(){
        var $count = $jqGrid.closest(".widget-box").find(".tip .count");
        //console.log($jqGrid.jqGrid("getDataIDs").length)
        $count.html($jqGrid.jqGrid("getDataIDs").length);
    }

    $("#submitBtn").click(function () {

        var $btn = $("#submitBtn").button('loading');
        var userIds = $("#jqGrid2").jqGrid("getDataIDs");
        var users = [];
        $.each(userIds, function (i, userId) {
            var rowData = $jqGrid.getRowData(userId);
            users.push({userId: userId, mobile: rowData.mobile, title: rowData.title})
        })

        $.post("${ctx}/oa/oaTask_users", {id:'${oaTask.id}', users: $.base64.encode(JSON.stringify(users))}, function (ret) {
            if (ret.success) {
                SysMsg.info("保存成功。");
            }
            $btn.button('reset');
        })

        //console.log(users)
    });
</script>