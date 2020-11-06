<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
            <c:if test="${param.admin==1}">
                <h4 class="widget-title lighter smaller"
            style="position:absolute; top: -50px; right: 400px;">
                <a id="backToBranchListBtn" href="javascript:;" style="color: red;font-weight: bolder;line-height: 30px"
                   data-load-el="#step-body-content-view"
                   data-url="${ctx}/pcs/pcsOw_party_branch_page?stage=${param.stage}&partyId=${param.partyId}"
                   class="loadPage">
                    <i class="ace-icon fa fa-reply"></i>
                    返回支部列表</a>
                </h4>
            </c:if>
            <c:if test="${param.admin!=1}">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
            </c:if>

        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4 multi-row-head-table">

                        <form class="form-inline" action="${ctx}/pcs/pcsRecommend_au" id="recommendForm" method="post">
                            <input type="hidden" name="stage" value="${param.stage}">
                            <input type="hidden" name="partyId" value="${param.partyId}">
                            <input type="hidden" name="branchId" value="${param.branchId}">
                            <input type="hidden" name="_isFinish">
                            <table class="form-table">
                                <tr>
                                    <td class="">党支部名称：</td>
                                    <td width="200">${pcsBranchBean.name}</td>
                                    <td>党员数：</td>
                                    <td width="60">${pcsBranchBean.memberCount}</td>
                                    <td>应参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="expectMemberCount"
                                               value="${pcsBranchBean.expectMemberCount}"></td>
                                    <td>实参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="actualMemberCount"
                                               value="${pcsBranchBean.actualMemberCount}">
                                        <a href="javascript:;" onclick="_tipPopup()" class="text-success">应到会人数如何计算？</a>
                                    </td>
                                </tr>
                            </table>
                            <div id="accordion">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><span class="title"><i
                                                class="fa fa-users"></i>   党委委员</span>
                            <span style="margin-left: 20px">
                            <select id="dwUserId" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
                                    data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                                &nbsp;
                            <a href="javascript:;" onclick="_addDwUser()"
                               class="btn btn-primary btn-sm ${!allowModify?"disabled":""}">
                                <i class="fa fa-plus-circle"></i> 添加</a>
                                <c:if test="${param.stage==PCS_STAGE_SECOND || param.stage==PCS_STAGE_THIRD}">
                                    <a href="javascript:;"
                                       class="popupBtn btn btn-info btn-sm ${!allowModify?"disabled":""}"
                                       data-width="900"
                                       data-url="${ctx}/pcs/pcsRecommend_candidates?stage=${param.stage==PCS_STAGE_SECOND
                                        ?PCS_STAGE_FIRST:PCS_STAGE_SECOND}&type=${PCS_USER_TYPE_DW}">
                                        <i class="fa fa-plus-circle"></i> 从“${param.stage==PCS_STAGE_SECOND?"二下":"三下"}”名单中添加</a>
                                </c:if>
                                </span>
                                            <span class="tip">已选<span class="count">${fn:length(dwCandidates)}</span>人，可拖拽行进行排序</span>

                                            <div class="panel-toolbar">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                                    <i class="ace-icon fa fa-chevron-up"></i>
                                                </a>
                                            </div>
                                        </h3>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <table id="jqGrid1" data-width-reduce="30"
                                                   class="jqGrid4 table-striped"></table>
                                        </div>
                                    </div>
                                </div>

                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><span class="title"><i
                                                class="fa fa-users"></i>   纪委委员</span>
                            <span style="margin-left: 20px">
                            <select id="jwUserId" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
                                    data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                                &nbsp;
                            <a href="javascript:;" onclick="_addJwUser()"
                               class="btn btn-primary btn-sm ${!allowModify?"disabled":""}">
                                <i class="fa fa-plus-circle"></i> 添加</a>

                                <c:if test="${param.stage==PCS_STAGE_SECOND || param.stage==PCS_STAGE_THIRD}">
                                    <a href="javascript:;"
                                       class="popupBtn btn btn-info btn-sm ${!allowModify?"disabled":""}"
                                       data-width="900"
                                       data-url="${ctx}/pcs/pcsRecommend_candidates?stage=${param.stage==PCS_STAGE_SECOND
                                        ?PCS_STAGE_FIRST:PCS_STAGE_SECOND}&type=${PCS_USER_TYPE_JW}">
                                        <i class="fa fa-plus-circle"></i> 从“${param.stage==PCS_STAGE_SECOND?"二下":"三下"}”名单中添加</a>
                                </c:if>
                                </span>
                                            <span class="tip">已选<span class="count">${fn:length(jwCandidates)}</span>人，可拖拽行进行排序</span>

                                            <div class="panel-toolbar">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                    <i class="ace-icon fa fa-chevron-down"></i>
                                                </a>
                                            </div>
                                        </h3>

                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <table id="jqGrid2" data-width-reduce="30"
                                                   class="jqGrid4 table-striped"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer center" style="margin-top: 20px">
                            <button ${!allowModify?"disabled":""} onclick="_clearUsers()"
                                 class="btn btn-danger btn-lg">
                                <i class="fa fa-times"></i> 清空</button>

                            <button class="popupBtn btn btn-info btn-lg tooltip-success"
                                    data-url="${ctx}/pcs/pcsRecommend_candidate_import?partyId=${param.partyId}&stage=${param.stage}"
                                    data-rel="tooltip" data-placement="top" ${!allowModify?"disabled":""}
                                    title="从Excel中导入名单"><i class="fa fa-upload"></i> 导入名单</button>
                            <c:if test="${param.admin!=1}">
                                    <button id="saveBtn" data-loading-text="保存中..." data-success-text="已保存成功"
                                            autocomplete="off" ${!allowModify?"disabled":""}
                                            class="btn btn-primary btn-lg"><i class="fa fa-save"></i> 暂存
                                    </button>
                            </c:if>
                            <c:if test="${param.admin==1}">
                                    <button id="updateBtn" data-loading-text="保存中..." data-success-text="已保存成功"
                                            autocomplete="off" ${!allowModify?"disabled":""}
                                            class="btn btn-info btn-lg"><i class="fa fa-edit"></i> 修改
                                    </button>
                            </c:if>
                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                            autocomplete="off"  ${!allowModify?"disabled":""}
                                            class="btn btn-success btn-lg"><i class="fa fa-random"></i> 提交推荐票
                                    </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .form-table {
        margin: 0 10px 10px 0px;
        border: 1px solid #e5e5e5;
    }

    .form-table tr td {
        padding: 5px;
        border: 1px solid #e5e5e5;
        white-space: nowrap;
    }

    .form-table input, .form-table input:focus {
        width: 80px;
        background-color: #f2dede;
        border: solid 1px darkred;
        font-size: 20px;
        font-weight: bolder;
        color: #000 !important;
        text-align: center !important;
    }

    .form-table tr td:nth-child(odd) {
        font-weight: bolder;
        background-color: #f9f9f9 !important;
        text-align: right !important;
        vertical-align: middle !important;
    }

    .panel .tip {
        margin-left: 30px
    }

    .panel .tip .count, .modal .tip .count {
        color: darkred;
        font-size: 24px;
        font-weight: bolder;
    }

    .panel input.vote, .panel input.positiveVote{
        width: 60px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        font-size: 18px;
        color: red;
    }

    .panel span.title {
        font-weight: bolder;
        color: #669fc7
    }

    .modal .tip ul {
        margin-left: 150px;
    }

    .modal .tip ul li {
        font-size: 25px;
        text-align: left;
    }

    .modal .tip div {
        margin: 20px 0;

        font-size: 25px;
        color: darkred;
        font-weight: bolder;
        text-align: center;
    }

    .confirm-modal .modal-dialog {
        width: 800px;
    }
</style>
<script type="text/template" id="confirmTpl">
    <div class="tip">
        <ul>
            <li>
                应参会党员数<span class="count">{{=expectMemberCount}}</span>人
            </li>
            <li>
                实参会党员数<span class="count">{{=actualMemberCount}}</span>人
            </li>
            <li>
                已推荐党委委员<span class="count">{{=dwCount}}</span>人
            </li>
            <li>
                已推荐纪委委员<span class="count">{{=jwCount}}</span>人
            </li>
        </ul>
        <div>请确认以上信息准确无误后提交</div>
    </div>
</script>
<script type="text/template" id="alertTpl">
    ${cm:getHtmlFragment('hf_pcs_expect_count_info').content}
</script>
<script>

    function _clearUsers(){
        SysMsg.confirm("确定清空全部两委委员？", "操作确认", function () {
            $("#jqGrid1").jqGrid("clearGridData");
            _showCount(1);
            $("#jqGrid2").jqGrid("clearGridData");
            _showCount(2);

        });
    }

    function _showCount(type){
        var $count = $("#jqGrid"+type).closest(".panel").find(".tip .count");
        $count.html($("#jqGrid"+type).jqGrid("getDataIDs").length);
    }
    function _tipPopup() {

         var msg = _.template($("#alertTpl").html())();
        bootbox.alert({
            className: "confirm-modal",
            message: msg,
            title: '应到会人数如何计算？'
        });
    }

    function _container(gid) {
        var panelId = $("#" + gid).closest(".panel").prop("id");
        return '#' + panelId + ' .panel-collapse';
    }

    var dwCandidates = ${cm:toJSONArray(dwCandidates)};
    var jwCandidates = ${cm:toJSONArray(jwCandidates)};
    var colModel = [
        {
            label: '移除', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
            //console.log(options)
            return '<button ${!allowModify?"disabled":""} class="delRowBtn btn btn-danger btn-xs" data-id="{0}" data-gid="{1}"><i class="fa fa-minus-circle"></i> 移除</button>'
                    .format(rowObject.userId, options.gid)
        }
        },
        {label: '工作证号', name: 'code', width: 110},
        {label: '被推荐提名人姓名', name: 'realname', width: 150},
        {
            label: '推荐提名<br/>的党员数', name: 'vote', width: 150, formatter: function (cellvalue, options, rowObject) {

                return ('<input type="text" name="vote{0}" data-container="{1}" value="{2}" class="vote num" maxlength="4">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
         }},
        {
            label: '推荐提名<br/>的正式党员数', name: 'positiveVote', width: 150, formatter: function (cellvalue, options, rowObject) {

            return ('<input type="text" name="positiveVote{0}" data-container="{1}" value="{2}" class="positiveVote num" maxlength="4">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
        }},
        {
            label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
        },
        {label: '民族', name: 'nation', width: 60},
        {label: '职称', name: 'proPost', width: 200},
        {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m',baseDate: '${_ageBaseDate}'}},
        {
            label: '入党时间',
            name: 'growTime',
            width: 120,
            sortable: true,
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m.d'}
        }/*,{
         label: '参加工作时间',
         name: 'workTime',
         width: 120,
         sortable: true,
         formatter: $.jgrid.formatter.date,
         formatoptions: {newformat: 'Y.m'}
         }*/,{label: '所在单位及职务', name: 'title', width: 350, align: 'left'},
        {hidden: true, key: true, name: 'userId'}
    ];
    $("#jqGrid1").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 400,
        datatype: "local",
        rowNum: dwCandidates.length,
        data: dwCandidates,
        colModel: colModel,
        gridComplete: function () {
            <c:if test="${!allowModify}">
            $("#recommendForm input, .panel input, .panel select").prop("disabled", true);
            </c:if>
        }
    });
    <c:if test="${allowModify}">
    $("#jqGrid1").jqGrid('sortableRows')
    </c:if>
    $("#jqGrid2").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 400,
        datatype: "local",
        rowNum: jwCandidates.length,
        data: jwCandidates,
        colModel: colModel,
        gridComplete: function () {
            <c:if test="${!allowModify}">
            $("#recommendForm input, .panel input, .panel select").prop("disabled", true);
            </c:if>
        }
    });
    <c:if test="${allowModify}">
    $("#jqGrid2").jqGrid('sortableRows')
    </c:if>
    $(window).triggerHandler('resize.jqGrid4');

    $("#saveBtn").click(function () {
        $("#recommendForm input[name=_isFinish]").val(0);
        $("#recommendForm").submit();
        return false;
    })
    $("#submitBtn").click(function () {

        var $voteNull = null;
        var $positiveVoteNull = null;
        $(".vote", ".panel").each(function () {
            var $this = $(this);
            if ($.trim($this.val()) == '') {
                $voteNull = $this;
                return false;
            }
        });
        if ($voteNull != null) {

            var $panel = $voteNull.closest('.panel');
            var $title = $panel.find('span.title');

            SysMsg.warning("请填写完整所有推荐人的信息（{0}）".format($.trim($title.text())));
            return;
        }
        $(".positiveVote", ".panel").each(function () {
            var $this = $(this);
            if ($.trim($this.val()) == '') {
                $positiveVoteNull = $this;
                return false;
            }
        });
        if ($positiveVoteNull != null) {

            var $panel = $positiveVoteNull.closest('.panel');
            var $title = $panel.find('span.title');

            SysMsg.warning("请填写完整所有推荐人的信息（{0}）".format($.trim($title.text())));
            return;
        }

        $("#recommendForm input[name=_isFinish]").val(1);
        $("#recommendForm").submit();
        return false;
    })
    // 管理员修改
    $("#updateBtn").click(function () {
        $("#recommendForm input[name=_isFinish]").val(-1);
        $("#recommendForm").submit();
        return false;
    })

    $("#recommendForm").validate({

        submitHandler: function (form) {

            var _isFinish = $("#recommendForm input[name=_isFinish]").val();
            if (_isFinish == 1) {
                _confirmSubmit(form);
            } else if(_isFinish == 0){
                $("#saveBtn").button('loading');
                _ajaxSubmit(form);
            }else{
                $("#updateBtn").button('loading');
                _ajaxSubmit(form);
            }
        }
    });

    function _ajaxSubmit(form) {

        var items = [];
        $.each($("#jqGrid1").jqGrid("getDataIDs"), function (i, userId) {
            var $row = $("[role='row'][id=" + userId + "]", "#jqGrid1");
            var item = {};
            item.type = ${PCS_USER_TYPE_DW}; // 党委委员
            item.userId = userId;
            item.vote = $.trim($("input.vote", $row).val());
            item.positiveVote = $.trim($("input.positiveVote", $row).val());
            items.push(item);
        });
        $.each($("#jqGrid2").jqGrid("getDataIDs"), function (i, userId) {
            var $row = $("[role='row'][id=" + userId + "]", "#jqGrid2");
            var item = {};
            item.type = ${PCS_USER_TYPE_JW} // 纪委委员
            item.userId = userId;
            item.vote = $.trim($("input.vote", $row).val());
            item.positiveVote = $.trim($("input.positiveVote", $row).val());
            items.push(item);
        });

        var _isFinish = $("#recommendForm input[name=_isFinish]").val();
        $(form).ajaxSubmit({
            data: {isFinish:(_isFinish==-1)?"":_isFinish, items: $.base64.encode(JSON.stringify(items))},
            success: function (ret) {

                if (ret.success) {
                    if (_isFinish == 1) {
                        <c:if test="${param.admin==1}">
                        $("#backToBranchListBtn").click();
                        </c:if>
                        <c:if test="${param.admin!=1}">
                        $.hideView();
                        </c:if>
                        $("#submitBtn").button("reset");
                    } else if(_isFinish == 0){
                        /*$.tip({
                            $target: $("#saveBtn"),
                            at: 'top center', my: 'bottom center', type: 'success',
                            msg: "填写内容已暂存，请及时填写完整并提交。"
                        });*/
                        SysMsg.info("填写内容已暂存，请及时填写完整并提交。")
                    }else{
                        /*$.tip({
                            $target: $("#updateBtn"),
                            at: 'top center', my: 'bottom center', type: 'success',
                            msg: "修改成功。"
                        });*/
                        SysMsg.info("修改成功。")
                    }
                }
                $("#saveBtn").button("reset");
                $("#updateBtn").button("reset");
            }
        });
    }

    function _confirmSubmit(form) {

        var msg = _.template($("#confirmTpl").html())({
            expectMemberCount: $("#recommendForm input[name=expectMemberCount]").val(),
            actualMemberCount: $("#recommendForm input[name=actualMemberCount]").val(),
            dwCount: $("#jqGrid1").jqGrid("getDataIDs").length,
            jwCount: $("#jqGrid2").jqGrid("getDataIDs").length
        })

        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> 确认无误',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<i class="fa fa-reply"></i> 返回修改',
                    className: 'btn-default btn-show'
                }
            },
            message: msg,
            callback: function (result) {
                if (result) {
                    $("#submitBtn").button('loading');
                    _ajaxSubmit(form);
                }
            },
            title: '提交确认'
        });
    }

    function _addDwUser() {
        _addUser("dwUserId", "jqGrid1");
    }
    function _addJwUser() {
        _addUser("jwUserId", "jqGrid2");
    }

    function _addUser(selectId, jqGridId) {

        var $select = $("#" + selectId);
        var $jqGrid = $("#" + jqGridId);
        var userId = $select.val();
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                my: 'bottom center', at: 'top center', msg: "请选择教职工党员"
            })
            return;
        }
        var rowData = $jqGrid.getRowData(userId);
        if (rowData.userId != undefined) {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                my: 'bottom center', at: 'top center', msg: "您已经添加了该教职工"
            })
            return;
        }

        $.post("${ctx}/pcs/pcsRecommend_selectUser", {"userIds": userId}, function (ret) {
            if (ret.success) {
                // console.log(ret.candidate)
                $jqGrid.jqGrid("addRowData", ret.candidates[0].userId, ret.candidates[0], "last");
                $select.val(null).trigger("change");
                $select.closest(".panel").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
            }
        })
    }

    $(document).on("click", ".delRowBtn", function () {

        var $jqGrid = $("#" + $(this).data("gid"));
        var $count = $(this).closest(".panel").find(".tip .count");
        // console.log($(this).data("gid"))
        $jqGrid.delRowData($(this).data("id"));
        $count.html($jqGrid.jqGrid("getDataIDs").length);
    })

    $.register.user_select($('#dwUserId, #jwUserId'));
</script>