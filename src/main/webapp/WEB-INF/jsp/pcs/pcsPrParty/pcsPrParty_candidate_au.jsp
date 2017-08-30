<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <h4 class="widget-title lighter smaller"
            style="position:absolute; top: -50px; left: 550px; ">
            <a href="javascript:" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-reply"></i>
                返回</a>
        </h4>

        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4">
                        <form class="form-inline" action="${ctx}/pcsPrParty_candidate_au" id="recommendForm" method="post">
                            <input type="hidden" name="stage" value="${param.stage}">
                            <table class="form-table">
                                <tr>
                                    <td class="">所有党员总数：</td>
                                    <td width="200">${partyView.memberCount}</td>
                                    <td>其中正式党员：</td>
                                    <td width="60">${partyView.positiveCount} 名</td>
                                    <td>应参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="expectMemberCount"
                                               value="${pcsRecommend.expectMemberCount}"></td>
                                    <td>其中正式党员：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="expectPositiveMemberCount"
                                               value="${pcsRecommend.expectPositiveMemberCount}"></td>
                                    <td>实参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="actualMemberCount"
                                               value="${pcsRecommend.actualMemberCount}"></td>
                                    <td>其中正式党员：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="actualPositiveMemberCount"
                                               value="${pcsRecommend.actualPositiveMemberCount}"></td>
                                </tr>
                            </table>
                            <div id="accordion">

                                <c:forEach items="${PCS_PR_TYPE_MAP}" var="_type">
                                    <c:if test="${_type.key==PCS_PR_TYPE_PRO}">
                                        <c:set var="_memberType" value="${MEMBER_TYPE_TEACHER}"/>
                                        <c:set var="_isRetire" value="0"/>
                                    </c:if>
                                    <c:if test="${_type.key==PCS_PR_TYPE_STU}">
                                        <c:set var="_memberType" value="${MEMBER_TYPE_STUDENT}"/>
                                    </c:if>
                                    <c:if test="${_type.key==PCS_PR_TYPE_RETIRE}">
                                        <c:set var="_memberType" value="${MEMBER_TYPE_TEACHER}"/>
                                        <c:set var="_isRetire" value="1"/>
                                    </c:if>
                                    <div class="panel panel-default" id="panel${_type.key}">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><span class="title">
                                                <i class="fa fa-users"></i> ${_type.value}</span>
                                                <span style="margin-left: 20px">
                                                <select id="select${_type.key}" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${partyView.id}&type=${_memberType}&isRetire=${_isRetire}&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL}"
                                                        data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.username}-${sysUser.code}</option>
                                                </select>
                                                &nbsp;
                                            <a href="javascript:;" onclick="_addUser(${_type.key})"
                                               class="btn btn-primary btn-sm ${hasReport?"disabled":""}">
                                                <i class="fa fa-plus-circle"></i> 添加</a>
                                                <c:if test="${param.stage==PCS_STAGE_SECOND || param.stage==PCS_STAGE_THIRD}">
                                                    <a href="javascript:;"
                                                       class="popupBtn btn btn-info btn-sm ${hasReport?"disabled":""}"
                                                       data-width="900"
                                                       data-url="${ctx}/pcsPrParty_candidates?stage=${param.stage==PCS_STAGE_SECOND
                                                        ?PCS_STAGE_FIRST:PCS_STAGE_SECOND}&type=${PCS_PR_TYPE_PRO}">
                                                        <i class="fa fa-plus-circle"></i> 从“${param.stage==PCS_STAGE_SECOND?"二下":"三下"}”名单中添加</a>
                                                </c:if>
                                                </span>
                                                <a style="margin-left: 30px" data-toggle="collapse"
                                                   data-parent="#accordion"
                                                   href="#collapse${_type.key}">
                                                    点击这里进行展开/折叠
                                                </a>
                                                <span class="tip">已选<span
                                                        class="count">${fn:length(candidatesMap.get(_type.key))}</span>人，可拖拽行进行排序</span>
                                            </h3>
                                        </div>
                                        <div id="collapse${_type.key}" class="panel-collapse collapse ${_type.key==PCS_PR_TYPE_PRO?"in":""}">
                                            <div class="panel-body">
                                                <table id="jqGrid${_type.key}" data-width-reduce="30"
                                                       class="jqGrid4 table-striped"></table>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </form>
                        <div class="modal-footer center" style="margin-top: 20px">
                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                    autocomplete="off"  ${hasReport?"disabled":""}
                                    class="btn btn-success btn-lg"><i class="fa fa-random"></i> 提交名单
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

    .form-table input {
        width: 50px;
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

    .panel input.vote{
        width:60px!important;
        padding: 0px!important;
        text-align: center;
        font-weight: bolder;
        font-size: 18px;
        color: red;
    }
    .panel span.title{
        font-weight: bolder;
        color: #669fc7
    }
</style>
<script type="text/template" id="confirmTpl">
    <div class="tip">
        <ul>
            <li>
                应参会党员数<span class="count">{{=expectMemberCount}}</span>人，
                其中正式党员<span class="count">{{=expectPositiveMemberCount}}</span>人
            </li>
            <li>
                实参会党员数<span class="count">{{=actualMemberCount}}</span>人，
                其中正式党员<span class="count">{{=actualPositiveMemberCount}}</span>人
            </li>
            <li>
                已填报专业技术人员和干部<span class="count">{{=proCount}}</span>人
            </li>
            <li>
                已填报学生代表<span class="count">{{=stuCount}}</span>人
            </li>
            <li>
                已填报离退休代表代表<span class="count">{{=retireCount}}</span>人
            </li>
        </ul>
        <div>请确认以上信息准确无误后提交</div>
    </div>
</script>
<script>
    var colModel = [
        {
            label: '移除', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
            //console.log(options)
            return '<button ${hasReport?"disabled":""} class="delRowBtn btn btn-danger btn-xs" data-id="{0}" data-gid="{1}"><i class="fa fa-minus-circle"></i> 移除</button>'
                    .format(rowObject.userId, options.gid)
        }
        },
        {label: '工作证号', name: 'code', width: 120, frozen: true},
        {label: '被推荐人姓名', name: 'realname', width: 150, frozen: true},
        {
            label: '票数', name: 'vote', formatter: function (cellvalue, options, rowObject) {

            var panelId = $("#"+options.gid).closest(".panel").prop("id");
            return ('<input required type="text" name="vote{0}" data-container="#'
            + panelId +' .panel-collapse"  class="vote num" maxlength="4">')
                    .format(rowObject.userId)
        }
        },
        {
            label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
        },
        {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {label: '民族', name: 'nation', width: 60},
        {
            label: '学历', name: '_learn', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                return $.jgrid.formatter.MetaType(rowObject.eduId);
            } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                return $.trim(rowObject.education);
            }
            return "-"
        }
        },
        {
            label: '参加工作时间',
            name: 'workTime',
            width: 120,
            sortable: true,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '入党时间',
            name: 'growTime',
            width: 120,
            sortable: true,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '职别', name: 'proPost', width: 200, formatter: function (cellvalue, options, rowObject) {
            if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                return '干部';
            } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                return (rowObject.isRetire) ? "离退休" : cellvalue;
            }
            return $.trim(rowObject.eduLevel);
        }
        },
        {
            label: '职务',
            name: 'post',
            width: 350,
            align: 'left', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                return $.trim(cellvalue);
            }
            return "-"
        }
        }, {hidden: true, key: true, name: 'userId'}
    ];

    var proCandidates = ${cm:toJSONArray(candidatesMap.get(PCS_PR_TYPE_PRO))};
    var stuCandidates = ${cm:toJSONArray(candidatesMap.get(PCS_PR_TYPE_STU))};
    var retireCandidates = ${cm:toJSONArray(candidatesMap.get(PCS_PR_TYPE_RETIRE))};
    $("#jqGrid1").jqGrid({
        pager: null,
        rownumbers: true,
        multiselect: false,
        height: 350,
        datatype: "local",
        rowNum: proCandidates.length,
        data: proCandidates,
        colModel: colModel
    }).jqGrid('sortableRows');
    $("#jqGrid2").jqGrid({
        pager: null,
        rownumbers: true,
        multiselect: false,
        height: 350,
        datatype: "local",
        rowNum: stuCandidates.length,
        data: stuCandidates,
        colModel: colModel
    }).jqGrid('sortableRows');

    $("#jqGrid3").jqGrid({
        pager: null,
        rownumbers: true,
        multiselect: false,
        height: 350,
        datatype: "local",
        rowNum: retireCandidates.length,
        data: retireCandidates,
        colModel: colModel
    }).jqGrid('sortableRows');

    $(window).triggerHandler('resize.jqGrid4');

    $("#submitBtn").click(function () {
        var $null_vote = null;
        $("input.vote").each(function(){
            var $this = $(this);
            var vote = $.trim($this.val());
            if(vote=='' || vote<=0){
                $null_vote = $this;
                $this.val('');
                return false;
            }
        });
        if($null_vote!=null){
            var $panel = $null_vote.closest('.panel');
            var $title = $panel.find('span.title');
            $.tip({$target:$title,
                //$container:$panel,
                at:"top center",
                my:"bottom left",
                msg:'请填写完整{0}中每个人的票数'
                        .format($.trim($title.text()))});

          //  return;
        }

        $("#recommendForm").submit();
        return false;
    })

    $("#recommendForm").validate({
        submitHandler: function (form) {
            _confirmSubmit(form);
        }
    });

    function _ajaxSubmit(form) {

        var proUserIds = $("#jqGrid1").jqGrid("getDataIDs");
        var stuUserIds = $("#jqGrid2").jqGrid("getDataIDs");
        var retireUserIds = $("#jqGrid3").jqGrid("getDataIDs");
        var items = [];
        $.each(proUserIds, function (i, userId) {
            var $vote = $("[role='row'][id=" + userId + "] input.vote", "#jqGrid1");
            var item = {};
            item.type = ${PCS_PR_TYPE_PRO};
            item.userId = userId;
            item.vote = $.trim($vote.val());
            items.push(item);
        });
        $.each(stuUserIds, function (i, userId) {
            var $vote = $("[role='row'][id=" + userId + "] input.vote", "#jqGrid2");
            var item = {};
            item.type = ${PCS_PR_TYPE_STU};
            item.userId = userId;
            item.vote = $.trim($vote.val());
            items.push(item);
        });
        $.each(retireUserIds, function (i, userId) {
            var $vote = $("[role='row'][id=" + userId + "] input.vote", "#jqGrid3");
            var item = {};
            item.type = ${PCS_PR_TYPE_RETIRE};
            item.userId = userId;
            item.vote = $.trim($vote.val());
            items.push(item);
        });

        $(form).ajaxSubmit({
            data: {items: new Base64().encode(JSON.stringify(items))},
            success: function (ret) {
                if (ret.success) {
                    SysMsg.success("提交成功。", function () {
                        $.hideView();
                    });
                }
            }
        });
    }

    function _confirmSubmit(form) {

        var msg = _.template($("#confirmTpl").html())({
            expectMemberCount: $("#recommendForm input[name=expectMemberCount]").val(),
            expectPositiveMemberCount: $("#recommendForm input[name=expectPositiveMemberCount]").val(),
            actualMemberCount: $("#recommendForm input[name=actualMemberCount]").val(),
            actualPositiveMemberCount: $("#recommendForm input[name=actualPositiveMemberCount]").val(),
            proCount: $("#jqGrid1").jqGrid("getDataIDs").length,
            stuCount: $("#jqGrid2").jqGrid("getDataIDs").length,
            retireCount: $("#jqGrid3").jqGrid("getDataIDs").length
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
                    _ajaxSubmit(form);
                }
            },
            title: '提交确认'
        });
    }

    function _addUser(prType) {

        var $select = $("#select" + prType);
        var $jqGrid = $("#jqGrid" + prType);
        var userId = $select.val();
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                my: 'bottom center', at: 'top center', msg: "请选择人员"
            })
            return;
        }
        var rowData = $jqGrid.getRowData(userId);
        if (rowData.userId != undefined) {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                my: 'bottom center', at: 'top center', msg: "您已经添加了该人员"
            })
            return;
        }

        $.post("${ctx}/pcsPrParty_selectUser", {"userIds[]": userId}, function (ret) {
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

    register_user_select($('[data-rel="select2-ajax"]'));
</script>