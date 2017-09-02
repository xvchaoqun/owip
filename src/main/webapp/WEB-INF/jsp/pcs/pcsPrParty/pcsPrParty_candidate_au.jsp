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
                        <form class="form-inline" action="${ctx}/pcsPrParty_candidate_au" id="recommendForm"
                              method="post">
                            <input type="hidden" name="stage" value="${param.stage}">
                            <table class="form-table">
                                <tr>
                                    <td class="">所有党员总数：</td>
                                    <td width="60">${partyView.memberCount}</td>
                                    <td>其中正式党员：</td>
                                    <td width="60">${partyView.positiveCount}</td>
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
                                    </c:if>
                                    <c:if test="${_type.key==PCS_PR_TYPE_STU}">
                                        <c:set var="_memberType" value="${MEMBER_TYPE_STUDENT}"/>
                                        <c:set var="_isRetire" value=""/>
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
                                                       data-url="${ctx}/pcsPrParty_candidates?stage=${param.stage==PCS_STAGE_SECOND
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
    .panel input.vote {
        width: 60px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        font-size: 18px;
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
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <a name="OLE_LINK4"></a><a name="OLE_LINK3"></a><b><span style="font-size:14.0pt;font-family:宋体;">党员大会进行选举时，有选举权的到会人数超过应到会人数的<span>4/5</span>，会议有效。为了保证选举工作能够顺利进行，党员因下列情况不能参加选举的，经报上级党组织同意，并经支部党员大会通过，可以不计算在应到会人数之内：</span></b><span style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>1</span>）患有精神病或因其他疾病导致不能表达本人意志的。</span></b><span style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>2</span>）出国半年以上的。</span></b><span style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>3</span>）虽未受到留党察看以上党纪处分，但正在服刑的。</span></b><span style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>4</span>）年老体弱卧床不起和长期生病、生活不能自理的。</span></b><span style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>5</span>）工作调动，下派锻炼，外出学习或工作半年以上等，按规定应转走正式组织关系而没有转走的。</span></b><span style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>6</span>）已经回原籍长期居住的离退休人员中的党员，因特殊情况，没有从原单位转出党员组织关系、确实不能参加选举的。</span></b><span style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <b><span style="font-size:14.0pt;font-family:宋体;">凡上述情况之外的党员不能参加党员大会进行选举，仍应计算在应到会人数之列。</span></b><br />
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
    function _tipPopup(){

        var msg = _.template($("#alertTpl").html());
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
            label: '票数', name: 'vote', formatter: function (cellvalue, options, rowObject) {

            return ('<input required type="text" name="vote{0}" data-container="{1}" value="{2}" class="vote num" maxlength="4">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
        }
        },
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

         return ('<input required type="text" name="birth{0}" data-container="{1}" data-date-format="yyyy-mm-dd" value="{2}" class="birth date-picker">')
         .format(rowObject.userId, _container(options.gid), ($.trim(cellvalue) != '') ? cellvalue.substr(0, 10) : "");
         }
         },
        /* {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},*/
        {
            label: '民族', name: 'nation', width: 150, formatter: function (cellvalue, options, rowObject) {

            return ('<input required type="text" name="nation{0}" value="{2}" data-container="{1}"  class="nation" maxlength="6">')
                    .format(rowObject.userId, _container(options.gid), $.trim(cellvalue))
        }
        },
        {
            label: '学历学位', name: '_learn', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                return $.jgrid.formatter.MetaType(rowObject.eduId);
            } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                return $.trim(rowObject.education);
            }
            return "-"
        }
        },/*
        {
            label: '参加工作时间',
            name: 'workTime',
            width: 120,
            sortable: true,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },*/
        {
            label: '入党时间',
            name: 'growTime',
            width: 120,
            sortable: true,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },
        {
            label: '职别', name: 'proPost', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                return '干部';
            } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                return (rowObject.isRetire) ? "离退休" : $.trim(cellvalue);
            }
            return $.trim(rowObject.eduLevel);
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
            register_date($('.date-picker'));
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
        $(".gender, .vote, .birth, .nation", ".panel").each(function () {
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
            $.tip({
                $target: $title,
                //$container:$panel,
                at: "top center",
                my: "bottom left",
                msg: '请填写完整{0}中每个人的信息'
                        .format($.trim($title.text()))
            });
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
            item.vote = $.trim($("input.vote", $row).val());
            item.gender = $.trim($("select.gender", $row).val());
            item.birth = $.trim($("input.birth", $row).val());
            item.nation = $.trim($("input.nation", $row).val());
            items.push(item);
        });
        </c:forEach>

        //console.log(items)
        $(form).ajaxSubmit({
            data: {items: new Base64().encode(JSON.stringify(items))},
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

        $.post("${ctx}/pcsPrParty_selectUser", {"userIds[]": userId, stage:${param.stage}}, function (ret) {
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