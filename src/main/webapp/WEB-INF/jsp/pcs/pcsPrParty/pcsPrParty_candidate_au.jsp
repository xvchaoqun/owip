<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<c:set var="GENDER_MALE" value="<%=SystemConstants.GENDER_MALE%>"/>
<c:set var="GENDER_FEMALE" value="<%=SystemConstants.GENDER_FEMALE%>"/>
<div class="row">
    <div class="col-xs-12">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-reply"></i>
                返回</a>
        </h4>

        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4 multi-row-head-table">
                        <form class="form-inline" action="${ctx}/pcs/pcsPrParty_candidate_au" id="recommendForm"
                              method="post">
                            <input type="hidden" name="stage" value="${param.stage}">
                            <input type="hidden" name="meetingType" value="0">
                            <table class="form-table">
                                <tr>
                                    <td class="">所有党员总数：</td>
                                    <td width="60">${pcsParty.memberCount}</td>
                                    <td>其中正式党员：</td>
                                    <td width="60">${pcsParty.positiveCount}</td>
                                    <td>应参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="expectMemberCount"
                                               value="${pcsPrRecommend.expectMemberCount}"></td>
                                    <td>其中正式党员：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="expectPositiveMemberCount"
                                               value="${pcsPrRecommend.expectPositiveMemberCount}"></td>
                                    <td>实参会党员数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="actualMemberCount"
                                               value="${pcsPrRecommend.actualMemberCount}"></td>
                                    <td>其中正式党员：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="actualPositiveMemberCount"
                                               value="${pcsPrRecommend.actualPositiveMemberCount}">
                                        <a href="javascript:;" onclick="_tipPopup()" class="text-success">应到会人数如何计算？</a>
                                    </td>
                                </tr>
                            </table>
                            <div id="accordion">

                                <c:forEach items="${PCS_PR_TYPE_MAP}" var="_type">
                                    <c:if test="${_type.key==PCS_PR_TYPE_PRO}">
                                        <c:set var="_memberType" value="${MEMBER_TYPE_TEACHER}"/>
                                        <c:set var="_isRetire" value="0"/>
                                        <c:set var="_status" value="${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_OUT}"/>
                                    </c:if>
                                    <c:if test="${_type.key==PCS_PR_TYPE_STU}">
                                        <c:set var="_memberType" value="${MEMBER_TYPE_STUDENT}"/>
                                        <c:set var="_isRetire" value=""/>
                                        <c:set var="_status" value="${MEMBER_STATUS_NORMAL}"/>
                                    </c:if>
                                    <c:if test="${_type.key==PCS_PR_TYPE_RETIRE}">
                                        <c:set var="_memberType" value="${MEMBER_TYPE_TEACHER}"/>
                                        <c:set var="_isRetire" value="1"/>
                                        <c:set var="_status" value="${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_OUT}"/>
                                    </c:if>
                                    <div class="panel panel-default" id="panel${_type.key}">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><span class="title">
                                                <i class="fa fa-users"></i> ${_type.value}</span>
                                                <span style="margin-left: 20px">
                                                <select id="select${_type.key}" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/member_selects?noAuth=1&type=${_memberType}&isRetire=${_isRetire}&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${_status}"
                                                        data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                                &nbsp;
                                            <a href="javascript:;" onclick="_addUser(${_type.key})"
                                               class="btn btn-primary btn-sm ${!allowModify?"disabled":""}">
                                                <i class="fa fa-plus-circle"></i> 添加</a>
                                                <c:if test="${param.stage==PCS_STAGE_SECOND || param.stage==PCS_STAGE_THIRD}">
                                                    <a href="javascript:;"
                                                       class="popupBtn btn btn-info btn-sm ${!allowModify?"disabled":""}"
                                                       data-width="900"
                                                       data-url="${ctx}/pcs/pcsPrParty_candidates?stage=${param.stage==PCS_STAGE_SECOND
                                                        ?PCS_STAGE_FIRST:PCS_STAGE_SECOND}&type=${_type.key}">
                                                        <i class="fa fa-plus-circle"></i>
                                                        从“${param.stage==PCS_STAGE_SECOND?"二下":"三下"}”名单中添加</a>
                                                </c:if>
                                                </span>
                                                <span class="tip">已选<span
                                                        class="count">${fn:length(candidatesMap.get(_type.key))}</span>人，可拖拽行进行排序</span>
                                                <div class="panel-toolbar">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${_type.key}">
                                                        <i class="ace-icon fa fa-chevron-${_type.key==PCS_PR_TYPE_PRO?"up":"down"}"></i>
                                                    </a>
                                                </div>
                                            </h3>

                                        </div>
                                        <div id="collapse${_type.key}"
                                             class="panel-collapse collapse ${_type.key==PCS_PR_TYPE_PRO?"in":""}">
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

                            <button ${!allowModify?"disabled":""} onclick="_clearUsers()"
                                   <%-- data-title="清空"
                                    data-msg="确定清空全部党代表？"--%>
                                    class="btn btn-danger btn-lg">
                                <i class="fa fa-times"></i> 清空</button>

                            <button class="popupBtn btn btn-info btn-lg tooltip-success"
                                    data-url="${ctx}/pcs/pcsPrParty_candidate_import?stage=${param.stage}"
                                    data-rel="tooltip" data-placement="top" ${!allowModify?"disabled":""} title="从Excel中导入名单"><i class="fa fa-upload"></i> 导入名单</button>

                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                    autocomplete="off"  ${!allowModify?"disabled":""}
                                    class="btn btn-success btn-lg"><i class="fa fa-random"></i> 保存名单
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

    .form-table input, .form-table input:focus{
        width: 60px;
        background-color: #f2dede;
        border: solid 1px darkred;
        font-size: 20px;
        font-weight: bolder;
        color: #000!important;
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
        margin-left: 50px;
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
    .panel{
        margin-bottom: 10px;
    }
    .panel input.branchVote, .panel input.vote, .panel input.positiveVote {
        width: 60px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        font-size: 18px;
        height: 27px;
        color: red;
    }

    .panel input.nation {
        width: 120px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        height: 27px;
        color: darkgreen;
    }

    .panel input.birth {
        width: 100px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        height: 27px;
        color: darkgreen;
    }

    .panel select.gender {
        width: 50px !important;
        padding: 0px !important;
        height: 27px;
        font-weight: bolder;
        color: darkgreen;
    }

    .panel span.title {
        font-weight: bolder;
        color: #669fc7
    }
    .panel select[disabled]{
        color: #848484!important;
        background-color: #eee!important;
    }
    .confirm-modal .modal-dialog{
        width: 800px;
    }
</style>
<script type="text/template" id="alertTpl">
    ${cm:getHtmlFragment('hf_pcs_expect_count_info').content}
</script>
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
<script type="text/template" id="genderTpl">
    <select required name="gender{{=userId}}" class="gender"
            data-container="{{=container}}">
        <option></option>
        <option {{=(gender==${GENDER_MALE})?'selected':''}} value="${GENDER_MALE}">男</option>
        <option {{=(gender==${GENDER_FEMALE})?'selected':''}} value="${GENDER_FEMALE}">女</option>
    </select>
</script>
<script>

    function _clearUsers(){
        SysMsg.confirm("确定清空全部党代表？", "操作确认", function () {
             $("#jqGrid"+${PCS_PR_TYPE_STU}).jqGrid("clearGridData");
              _showCount(${PCS_PR_TYPE_STU});
              $("#jqGrid"+${PCS_PR_TYPE_PRO}).jqGrid("clearGridData");
              _showCount(${PCS_PR_TYPE_PRO});
               $("#jqGrid"+${PCS_PR_TYPE_RETIRE}).jqGrid("clearGridData");
              _showCount(${PCS_PR_TYPE_RETIRE});
        });
    }
    function _showCount(type){
        var $count = $("#jqGrid"+type).closest(".panel").find(".tip .count");
        $count.html($("#jqGrid"+type).jqGrid("getDataIDs").length);
    }

    function _tipPopup(){

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
    var colModel = [
        {
            label: '移除', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
            //console.log(options)
            return '<button ${!allowModify?"disabled":""} class="delRowBtn btn btn-danger btn-xs" data-id="{0}" data-gid="{1}"><i class="fa fa-minus-circle"></i> 移除</button>'
                    .format(rowObject.userId, options.gid)
        }
        },
        {label: '工作证号', name: 'code', width: 120, frozen: true},
        {label: '被推荐人姓名', name: 'realname', width: 150, frozen: true},
        {
            label: '提名支部数量', name: 'branchVote', formatter: function (cellvalue, options, rowObject) {

            return ('<input type="text" name="branchVote{0}" data-container="{1}" value="{2}" class="branchVote num" maxlength="4">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
        }},
        {
            label: '推荐提名<br/>的党员数', name: 'vote', width: 110, formatter: function (cellvalue, options, rowObject) {

                return ('<input type="text" name="vote{0}" data-container="{1}" value="{2}" class="vote num" maxlength="4">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
            }},
        {
            label: '推荐提名<br/>的正式党员数', name: 'positiveVote', width: 110, formatter: function (cellvalue, options, rowObject) {

                return ('<input type="text" name="positiveVote{0}" data-container="{1}" value="{2}" class="positiveVote num" maxlength="4">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
            }},
        {
            label: '性别', name: 'gender', width: 120, formatter: function (cellvalue, options, rowObject) {

            return _.template($("#genderTpl").html().NoMultiSpace())({
                gender: cellvalue,
                userId: rowObject.userId,
                container: _container(options.gid)
            })
        }
        },
         {
         label: '出生年月', width: 150, name: 'birth', formatter: function (cellvalue, options, rowObject) {

         return ('<input type="text" name="birth{0}" data-container="{1}" data-date-format="yyyy-mm-dd" value="{2}" class="birth date-picker">')
         .format(rowObject.userId, _container(options.gid), ($.trim(cellvalue) != '') ? cellvalue.substr(0, 10) : "");
         }
         },
        /* {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},*/
        {
            label: '民族', name: 'nation', width: 150, formatter: function (cellvalue, options, rowObject) {

            return ('<input type="text" name="nation{0}" value="{2}" data-container="{1}"  class="nation" maxlength="6">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
        }
        },
        {label: '学历', name: 'education'},
        {label: '学位', name: 'degree'},
        /*
        {
            label: '参加工作时间',
            name: 'workTime',
            width: 120,
            sortable: true,
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m'}
        },*/
        {
            label: '入党时间',
            name: 'growTime',
            width: 120,
            sortable: true,
            formatter: $.jgrid.formatter.date,
            formatoptions: {newformat: 'Y.m.d'}
        },
        {
            label: '职称', name: 'proPost', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_STU}') {
                    return $.trim(rowObject.eduLevel);
                }
                return (rowObject.isRetire) ? "离退休" : $.trim(cellvalue);
            }
        },
        {
            label: '职务',
            name: 'post',
            width: 150,
            align: 'left', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                return $.trim(cellvalue);
            }
            return "-"
        }
        }, {hidden: true, key: true, name: 'userId'}
    ];

    <c:forEach items="${PCS_PR_TYPE_MAP}" var="_type">
    var candidates_${_type.key} = ${cm:toJSONArray(candidatesMap.get(_type.key))};
    var jqGrid = $("#jqGrid${_type.key}").jqGrid({
        pager: null,
        responsive:false,
        rownumbers: true,
        multiselect: false,
        height: 350,
        datatype: "local",
        rowNum: candidates_${_type.key}.length,
        data: candidates_${_type.key},
        colModel: colModel,
        gridComplete:function(){
            <c:if test="${allowModify}">
            $.register.date($('.date-picker'));
            </c:if>
            <c:if test="${!allowModify}">
            $("#recommendForm input, .panel input, .panel select").prop("disabled", true);
            </c:if>
        }
    });
    <c:if test="${allowModify}">
    jqGrid.jqGrid('sortableRows');
    </c:if>
    </c:forEach>
    $(window).triggerHandler('resize.jqGrid4');

    $("#submitBtn").click(function () {
        var $null = null;
        $(".gender, .branchVote, .vote, .positiveVote, .birth, .nation", ".panel").each(function () {
            var $this = $(this);
            if ($.trim($this.val()) == '') {
                $null = $this;
                return false;
            }
        });
        //console.log($null)
        if ($null != null) {

            var $panel = $null.closest('.panel');
            var $title = $panel.find('span.title');
            /*
            $.tip({
                $target: $title,
                //$container:$panel,
                at: "top center",
                my: "bottom left",
                msg: '请填写完整{0}中每个人的信息'
                        .format($.trim($title.text()))
            });*/

            SysMsg.warning("请填写完整所有推荐人的信息（{0}）".format($.trim($title.text())));
            return;
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

        var items = [];
        <c:forEach items="${PCS_PR_TYPE_MAP}" var="_type">
        $.each($("#jqGrid${_type.key}").jqGrid("getDataIDs"), function (i, userId) {
            var $row = $("[role='row'][id=" + userId + "]", "#jqGrid${_type.key}");
            var item = {};
            item.type = ${_type.key};
            item.userId = userId;
            item.branchVote = $.trim($("input.branchVote", $row).val());
            item.vote = $.trim($("input.vote", $row).val());
            item.positiveVote = $.trim($("input.positiveVote", $row).val());
            item.gender = $.trim($("select.gender", $row).val());
            item.birth = $.trim($("input.birth", $row).val());
            item.nation = $.trim($("input.nation", $row).val());
            items.push(item);
        });
        </c:forEach>

        //console.log(items)
        $(form).ajaxSubmit({
            data: {items: $.base64.encode(JSON.stringify(items))},
            success: function (ret) {
                if (ret.success) {
                    /*SysMsg.success("提交成功。", function () {
                        $.hideView();
                    });*/
                    $.hideView();
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

        $.post("${ctx}/pcs/pcsPrParty_selectUser", {"userIds": userId, stage:${param.stage}}, function (ret) {
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

    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>