<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <h4 class="widget-title lighter smaller"
            style="position:absolute; top: -50px; left: 550px;">
            <c:if test="${param.admin==1}">
                <a href="javascript:;" style="color: red;font-weight: bolder;line-height: 30px"
                   data-load-el="#step-body-content-view"
                   data-url="${ctx}/pcsOw_party_branch_page?stage=${param.stage}&partyId=${param.partyId}"
                   class="loadPage">
                    <i class="ace-icon fa fa-reply"></i>
                    返回支部列表</a>
            </c:if>
            <c:if test="${param.admin!=1}">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-reply"></i>
                    返回</a>
            </c:if>
        </h4>

        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4">

                        <form class="form-inline" action="${ctx}/pcsRecommend_au" id="recommendForm" method="post">
                            <input type="hidden" name="stage" value="${param.stage}">
                            <input type="hidden" name="partyId" value="${param.partyId}">
                            <input type="hidden" name="branchId" value="${param.branchId}">
                            <input type="hidden" name="_isFinish">
                            <table class="form-table">
                                <tr>
                                    <td class="">党支部名称：</td>
                                    <td width="200">${pcsRecommend.name}</td>
                                    <td>党员数：</td>
                                    <td width="60">${pcsRecommend.memberCount}</td>
                                    <td>应参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="expectMemberCount"
                                               value="${pcsRecommend.expectMemberCount}"></td>
                                    <td>实参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="actualMemberCount"
                                               value="${pcsRecommend.actualMemberCount}">
                                        <a href="javascript:;" onclick="_tipPopup()" class="text-success">应到会人数如何计算？</a>
                                    </td>
                                </tr>
                            </table>
                            <div id="accordion">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                                                class="fa fa-users"></i>   党委委员</span>
                            <span style="margin-left: 20px">
                            <select id="dwUserId" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&politicalStatus=<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
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
                                       data-url="${ctx}/pcsRecommend_candidates?stage=${param.stage==PCS_STAGE_SECOND
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
                                        <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                                                class="fa fa-users"></i>   纪委委员</span>
                            <span style="margin-left: 20px">
                            <select id="jwUserId" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&politicalStatus=<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
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
                                       data-url="${ctx}/pcsRecommend_candidates?stage=${param.stage==PCS_STAGE_SECOND
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
                        <c:if test="${param.admin!=1}">
                            <div class="modal-footer center" style="margin-top: 20px">
                                <button id="saveBtn" data-loading-text="保存中..." data-success-text="已保存成功"
                                        autocomplete="off" ${!allowModify?"disabled":""}
                                        class="btn btn-primary btn-lg"><i class="fa fa-save"></i> 暂存
                                </button>

                                <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                        autocomplete="off"  ${!allowModify?"disabled":""}
                                        class="btn btn-success btn-lg"><i class="fa fa-random"></i> 提交推荐票
                                </button>
                            </div>
                        </c:if>
                        <c:if test="${param.admin==1 && allowModify}">
                            <div class="modal-footer center" style="margin-top: 20px">
                                <button id="updateBtn" data-loading-text="保存中..." data-success-text="已保存成功"
                                        autocomplete="off" class="btn btn-info btn-lg"><i class="fa fa-edit"></i> 修改
                                </button>
                            </div>
                        </c:if>

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
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <a name="OLE_LINK4"></a><a name="OLE_LINK3"></a><b><span style="font-size:14.0pt;font-family:宋体;">党员大会进行选举时，有选举权的到会人数超过应到会人数的<span>4/5</span>，会议有效。为了保证选举工作能够顺利进行，党员因下列情况不能参加选举的，经报上级党组织同意，并经支部党员大会通过，可以不计算在应到会人数之内：</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>1</span>）患有精神病或因其他疾病导致不能表达本人意志的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>2</span>）出国半年以上的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>3</span>）虽未受到留党察看以上党纪处分，但正在服刑的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>4</span>）年老体弱卧床不起和长期生病、生活不能自理的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span
                style="font-size:14.0pt;font-family:宋体;">（<span>5</span>）工作调动，下派锻炼，外出学习或工作半年以上等，按规定应转走正式组织关系而没有转走的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>6</span>）已经回原籍长期居住的离退休人员中的党员，因特殊情况，没有从原单位转出党员组织关系、确实不能参加选举的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <b><span style="font-size:14.0pt;font-family:宋体;">凡上述情况之外的党员不能参加党员大会进行选举，仍应计算在应到会人数之列。</span></b><br/>
</script>
<script>

    function _tipPopup() {

        var msg = _.template($("#alertTpl").html());
        bootbox.alert({
            className: "confirm-modal",
            message: msg,
            title: '应到会人数如何计算？'
        });
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
            label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
        },
        {label: '民族', name: 'nation', width: 60},
        {label: '职称', name: 'proPost', width: 200},
        {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {
            label: '入党时间',
            name: 'growTime',
            width: 120,
            sortable: true,
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y-m-d'}
        }/*,{
         label: '参加工作时间',
         name: 'workTime',
         width: 120,
         sortable: true,
         formatter: $.jgrid.formatter.date,
         formatoptions: {newformat: 'Y.m'}
         }*/, {
            label: '所在单位及职务',
            name: '_title',
            width: 350,
            align: 'left',
            formatter: function (cellvalue, options, rowObject) {
                return ($.trim(rowObject.title) == '') ? $.trim(rowObject.extUnit) : $.trim(rowObject.title);
            }
        }, {hidden: true, key: true, name: 'userId'}
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

        var dwUserIds = $("#jqGrid1").jqGrid("getDataIDs")
        var jwUserIds = $("#jqGrid2").jqGrid("getDataIDs")
        var _isFinish = $("#recommendForm input[name=_isFinish]").val();
        $(form).ajaxSubmit({
            data: {isFinish:(_isFinish==-1)?"":_isFinish, dwCandidateIds: dwUserIds, jwCandidateIds: jwUserIds},
            success: function (ret) {

                if (ret.success) {
                    if (_isFinish == 1) {
                        $.hideView();
                        $("#submitBtn").button("reset");
                    } else if(_isFinish == 0){
                        $.tip({
                            $target: $("#saveBtn"),
                            at: 'top center', my: 'bottom center', type: 'success',
                            msg: "填写内容已暂存，请及时填写完整并提交。"
                        });
                        $("#saveBtn").button("reset");
                    }else{
                        $.tip({
                            $target: $("#updateBtn"),
                            at: 'top center', my: 'bottom center', type: 'success',
                            msg: "修改成功。"
                        });
                        $("#updateBtn").button("reset");
                    }
                }
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

        $.post("${ctx}/pcsRecommend_selectUser", {"userIds[]": userId}, function (ret) {
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