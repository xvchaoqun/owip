<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <h4 class="widget-title lighter smaller"
            style="position:absolute; top: -50px; left: 460px; ">

            <a href="javascript:" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-reply"></i>
                返回</a>
        </h4>

        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4">

                        <form class="form-inline" action="${ctx}/pcsRecommend_au" id="recommendForm" method="post">
                            <input type="hidden" name="stage" value="${param.stage}">
                            <input type="hidden" name="partyId" value="${param.partyId}">
                            <input type="hidden" name="branchId" value="${param.branchId}">
                            <input type="hidden" name="isFinish">
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
                                               value="${pcsRecommend.actualMemberCount}"></td>
                                </tr>
                            </table>
                            <div id="accordion">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                                            class="fa fa-users"></i>   党委委员</span>
                            <span style="margin-left: 20px">
                            <select id="dwUserId" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                                    data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.username}-${sysUser.code}</option>
                            </select>
                                &nbsp;
                            <a href="javascript:;" onclick="_addDwUser()" class="btn btn-primary btn-sm ${hasReport?"disabled":""}">
                                <i class="fa fa-plus-circle"></i> 添加</a>
                                <c:if test="${param.stage==PCS_STAGE_SECOND || param.stage==PCS_STAGE_THIRD}">
                                <a href="javascript:;" class="popupBtn btn btn-info btn-sm ${hasReport?"disabled":""}"
                                        data-width="900"
                                        data-url="${ctx}/pcsRecommend_candidates?stage=${param.stage==PCS_STAGE_SECOND
                                        ?PCS_STAGE_FIRST:PCS_STAGE_SECOND}&type=${PCS_USER_TYPE_DW}">
                                    <i class="fa fa-plus-circle"></i> 从“${param.stage==PCS_STAGE_SECOND?"二下":"三下"}”名单中添加</a>
                                </c:if>
                                </span>
                                        <a style="margin-left: 30px" data-toggle="collapse" data-parent="#accordion"
                                           href="#collapseOne">
                                            点击这里进行展开/折叠
                                        </a>
                                        <span class="tip">已选<span class="count">${fn:length(dwCandidates)}</span>人，可拖拽行进行排序</span>
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
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                                    data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.username}-${sysUser.code}</option>
                            </select>
                                &nbsp;
                            <a href="javascript:;" onclick="_addJwUser()" class="btn btn-primary btn-sm ${hasReport?"disabled":""}">
                                <i class="fa fa-plus-circle"></i> 添加</a>

                                <c:if test="${param.stage==PCS_STAGE_SECOND || param.stage==PCS_STAGE_THIRD}">
                                    <a href="javascript:;" class="popupBtn btn btn-info btn-sm ${hasReport?"disabled":""}"
                                       data-width="900"
                                       data-url="${ctx}/pcsRecommend_candidates?stage=${param.stage==PCS_STAGE_SECOND
                                        ?PCS_STAGE_FIRST:PCS_STAGE_SECOND}&type=${PCS_USER_TYPE_JW}">
                                        <i class="fa fa-plus-circle"></i> 从“${param.stage==PCS_STAGE_SECOND?"二下":"三下"}”名单中添加</a>
                                </c:if>
                                </span>

                                        <a style="margin-left: 30px" data-toggle="collapse" data-parent="#accordion"
                                           href="#collapseTwo">
                                            点击这里进行展开/折叠
                                        </a>
                                        <span class="tip">已选<span class="count">${fn:length(jwCandidates)}</span>人，可拖拽行进行排序</span>
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
                            <button id="saveBtn" data-loading-text="保存中..." data-success-text="已保存成功"
                                    autocomplete="off" ${hasReport?"disabled":""}
                                    class="btn btn-primary btn-lg"><i class="fa fa-save"></i> 保存
                            </button>

                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                    autocomplete="off"  ${hasReport?"disabled":""}
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
    .form-table{
        margin: 0 10px 10px 0px;
        border: 1px solid #e5e5e5;
    }
    .form-table tr td{
        padding: 5px;
        border: 1px solid #e5e5e5;
        white-space: nowrap;
    }
    .form-table input{
        width: 50px;
    }
    .form-table tr td:nth-child(odd){
        font-weight: bolder;
        background-color: #f9f9f9 !important;
        text-align: right !important;
        vertical-align: middle !important;
    }
    .panel .tip{
        margin-left: 30px
    }
    .panel .tip .count, .modal .tip .count{
        color:darkred;
        font-size: 24px;
        font-weight: bolder;
    }
    .modal .tip ul{
        margin-left: 150px;
    }
    .modal .tip ul li{
        font-size: 25px;
        text-align: left;
    }
    .modal .tip div{
        margin: 20px 0;

        font-size: 25px;
        color: darkred;
        font-weight: bolder;
        text-align: center;
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
<script>
    var dwCandidates = ${cm:toJSONArray(dwCandidates)};
    var jwCandidates = ${cm:toJSONArray(jwCandidates)};
    var colModel = [
        {
            label: '移除', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
            //console.log(options)
            return '<button ${hasReport?"disabled":""} class="delRowBtn btn btn-danger btn-xs" data-id="{0}" data-gid="{1}"><i class="fa fa-minus-circle"></i> 移除</button>'
                    .format(rowObject.userId, options.gid)
        }
        },
        {label: '工作证号', name: 'code', width: 100},
        {label: '被推荐提名人姓名', name: 'realname', width: 150},
        {
            label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
        },
        {label: '民族', name: 'nation', width: 60},
        {label: '职称', name: 'proPost', width: 200},
        {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
        {
            label: '入党时间',
            name: 'growTime',
            width: 120,
            sortable: true,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        },{
            label: '参加工作时间',
            name: 'workTime',
            width: 120,
            sortable: true,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
        }, {
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
        rownumbers: true,
        multiselect: false,
        height: 400,
        datatype: "local",
        rowNum: dwCandidates.length,
        data: dwCandidates,
        colModel: colModel
    }).jqGrid('sortableRows');
    $("#jqGrid2").jqGrid({
        pager: null,
        rownumbers: true,
        multiselect: false,
        height: 400,
        datatype: "local",
        rowNum: jwCandidates.length,
        data: jwCandidates,
        colModel: colModel
    }).jqGrid('sortableRows');
    $(window).triggerHandler('resize.jqGrid4');

    $("#saveBtn").click(function () {
        $("#recommendForm input[name=isFinish]").val(0);
        $("#recommendForm").submit();
        return false;
    })
    $("#submitBtn").click(function () {
        $("#recommendForm input[name=isFinish]").val(1);
        $("#recommendForm").submit();
        return false;
    })

    $("#recommendForm").validate({
        submitHandler: function (form) {

            var isFinish = $("#recommendForm input[name=isFinish]").val();
            if(isFinish==1){
                _confirmSubmit(form);
            }else{
                _ajaxSubmit(form);
            }
        }
    });

    function _ajaxSubmit(form){

        var dwUserIds = $("#jqGrid1").jqGrid("getDataIDs")
        var jwUserIds = $("#jqGrid2").jqGrid("getDataIDs")
        $(form).ajaxSubmit({
            data:{dwCandidateIds:dwUserIds, jwCandidateIds: jwUserIds},
            success: function (ret) {
                if (ret.success) {
                    if($("#recommendForm input[name=isFinish]").val()==1){
                        $.hideView();
                    }else{
                        SysMsg.info("保存成功。");
                    }
                }
            }
        });
    }

    function _confirmSubmit(form){

        var msg = _.template($("#confirmTpl").html())({expectMemberCount: $("#recommendForm input[name=expectMemberCount]").val(),
            actualMemberCount: $("#recommendForm input[name=actualMemberCount]").val(),
            dwCount:$("#jqGrid1").jqGrid("getDataIDs").length,
            jwCount:$("#jqGrid2").jqGrid("getDataIDs").length})

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

    function _addDwUser(){
        _addUser("dwUserId", "jqGrid1");
    }
    function _addJwUser(){
        _addUser("jwUserId", "jqGrid2");
    }

    function _addUser(selectId, jqGridId) {

        var $select = $("#" + selectId);
        var $jqGrid =  $("#"+jqGridId);
        var userId = $select.val();
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                my: 'bottom center', at: 'top center', msg: "请选择教职工党员"
            })
            return;
        }
        var rowData =$jqGrid.getRowData(userId);
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

    register_user_select($('#dwUserId, #jwUserId'));
</script>