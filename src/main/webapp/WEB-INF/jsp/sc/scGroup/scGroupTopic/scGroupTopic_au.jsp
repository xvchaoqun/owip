<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<c:set value="<%=ScConstants.SC_RECORD_STATUS_INIT%>" var="SC_RECORD_STATUS_INIT"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">讨论议题</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8 row">
                <form class="form-horizontal" action="${ctx}/sc/scGroupTopic_au" autocomplete="off" disableautocomplete id="modalForm" method="post"
                      enctype="multipart/form-data">
                    <div style="width: 500px;float: left;margin-right: 25px">
                        <div class="widget-box" style="height: 530px;">
                            <div class="widget-header">
                                <h4 class="widget-title">
                                    ${scGroupTopic!=null?"修改":"添加"}讨论议题
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="row">
                                        <input type="hidden" name="id" value="${scGroupTopic.id}">
                                        <input type="hidden" name="filePath" value="${scGroupTopic.filePath}">

                                        <div class="form-group">
                                            <label class="col-xs-4 control-label"><span class="star">*</span>所属干部小组会</label>

                                            <div class="col-xs-6">
                                                <select required name="groupId" data-rel="select2"
                                                        data-width="240"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach var="scGroup" items="${scGroups}">
                                                        <option value="${scGroup.id}">
                                                            干部小组会[${cm:formatDate(scGroup.holdDate, "yyyyMMdd")}]号
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#modalForm select[name=groupId]").val("${groupId}");
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label"><span class="star">*</span>议题名称</label>

                                            <div class="col-xs-6">
                                                <textarea required class="form-control noEnter" rows="3"
                                                          name="name">${scGroupTopic.name}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label"><span class="star">*</span>议题类型</label>
                                            <div class="col-xs-6">
                                                <select required data-rel="select2" data-width="240"
                                                        name="type" data-placeholder="请选择">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__id=${cm:getMetaClassByCode('mc_sc_group_topic_type').id}"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#modalForm select[name=type]").val(${scGroupTopic.type});
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label">涉及单位</label>

                                            <div class="col-xs-6">
                                                <div class="input-group">
                                                    <select class="multiselect" multiple="" name="unitIds">
                                                        <c:forEach var="unitType"
                                                                   items="${cm:getMetaTypes('mc_unit_type')}">
                                                            <c:set var="unitList"
                                                                   value="${unitListMap.get(unitType.value.id)}"/>
                                                            <c:if test="${fn:length(unitList)>0}">
                                                                <optgroup label="${unitType.value.name}">
                                                                    <c:forEach
                                                                            items="${unitList}"
                                                                            var="unitId">
                                                                        <c:set var="unit"
                                                                               value="${unitMap.get(unitId)}"></c:set>
                                                                        <option value="${unit.id}">${unit.name}</option>
                                                                    </c:forEach>
                                                                </optgroup>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="help-block">（正在运转单位）</span>

                                                    <div class="space-4"></div>
                                                    <select class="multiselect" multiple="" name="historyUnitIds">
                                                        <c:forEach var="unitType"
                                                                   items="${cm:getMetaTypes('mc_unit_type')}">
                                                            <c:set var="unitList"
                                                                   value="${historyUnitListMap.get(unitType.value.id)}"/>
                                                            <c:if test="${fn:length(unitList)>0}">
                                                                <optgroup label="${unitType.value.name}">
                                                                    <c:forEach
                                                                            items="${unitList}"
                                                                            var="unitId">
                                                                        <c:set var="unit"
                                                                               value="${unitMap.get(unitId)}"></c:set>
                                                                        <option value="${unit.id}">${unit.name}</option>
                                                                    </c:forEach>
                                                                </optgroup>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="help-block">（历史单位）</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label">附件（批量）</label>
                                            <div class="col-xs-6">
                                                <input class="form-control" type="file" name="files"
                                                       multiple="multiple"/>
                                                <c:if test="${not empty scGroupTopic.filePath}">
                                                    已上传附件：
                                                <c:forEach var="file" items="${fn:split(scGroupTopic.filePath,',')}" varStatus="vs">
                                                    <a href="${ctx}/attach_download?path=${cm:sign(file)}&filename=附件${vs.count}">附件${vs.count}</a>
                                                    ${vs.last?"":"、"}
                                                </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label">备注</label>

                                            <div class="col-xs-6">
                                        <textarea class="form-control limited" rows="4"
                                                  name="remark">${scGroupTopic.remark}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="width: 750px;float: left;margin-right: 25px">
                        <div class="widget-box">
                            <div class="widget-header">
                                <h4 class="widget-title">
                                    议题内容
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="form-group typeSelect recordId">
                                        <label class="col-xs-3 control-label"><span class="star">*</span>对应的干部选任纪实</label>
                                        <div class="col-xs-6">
                                            <select required name="recordId" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/sc/scRecord_selects" data-width="400"
                                                    data-placeholder="请选择">
                                                <option value="${scRecord.id}"
                                                        delete="${scRecord.status!=SC_RECORD_STATUS_INIT}">
                                                    ${scRecord.code}-${scRecord.postName}-${scRecord.job}</option>
                                            </select>
                                            <script>
                                                $.register.del_select($("#modalForm select[name=recordId]"))
                                            </script>
                                        </div>
                                    </div>
                                    <div class="form-group typeSelect unitPostId">
                                        <label class="col-xs-3 control-label"><span class="star">*</span>拟调整的岗位</label>
                                        <div class="col-xs-6">
                                            <select required name="unitPostId" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/unitPost_selects" data-width="400"
                                                    data-placeholder="请选择">
                                                <option value="${unitPost.id}"
                                                        delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
                                            </select>
                                            <script>
                                                $.register.del_select($("#modalForm select[name=unitPostId]"))
                                            </script>
                                        </div>
                                    </div>
                                    <div class="form-group typeSelect scType">
                                        <label class="col-xs-3 control-label"><span class="star">*</span>确定选任方式</label>
                                        <div class="col-xs-6">
                                            <select required data-rel="select2" data-width="273"
                                                    name="scType" data-placeholder="请选择">
                                                <option></option>
                                                <jsp:include page="/metaTypes?__id=${cm:getMetaClassByCode('mc_sc_type').id}"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#modalForm select[name=scType]").val(${scGroupTopic.scType});
                                            </script>
                                        </div>
                                    </div>
                                    <div class="form-group typeSelect candidateUserId">
                                        <label class="col-xs-3 control-label"><span class="star">*</span>推荐拟任人选</label>
                                        <div class="col-xs-6">

                                            <select required name="candidateUserId" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/sc/scGroupTopic_users" data-width="400"
                                                    data-placeholder="请选择">
                                                <option value="${candidateUser.id}">${candidateUser.code}-${candidateUser.realname}</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="typeSelect motion"
                                         style="position: absolute;text-align: center;width: 320px;margin:0 200px;
                                         font-size: 16px;font-weight: bolder;">
                                        确定工作方案
                                    </div>
                                    <div  class="typeSelect content">
                                    <textarea id="contentId">${scGroupTopic.content}</textarea>
                                    </div>
                                    <div class="widget-box typeSelect candidateUserIds">
                                        <div class="widget-header">
                                            <h4 class="smaller">
                                                确定考察对象
                                                <select data-rel="select2-ajax" data-width="200"
                                                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                                        name="userId" data-placeholder="请输入账号或姓名或工号">
                                                    <option></option>
                                                </select>
                                                <button id="selectBtn" type="button" class="btn btn-success"><i
                                                        class="fa fa-plus"></i> 添加
                                                </button>
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
                            </div>
                        </div>
                        <div class="widget-box memo">
                            <div class="widget-header">
                                <h4 class="widget-title">
                                    议题讨论备忘
                                </h4>
                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <textarea id="memoId">${scGroupTopic.memo}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="clear: both"></div>
                    <div class="clearfix form-actions center">
                        <button class="btn btn-info btn-sm" type="button"
                                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 保存中，请不要关闭此窗口"
                                id="submitBtn">
                            <i class="ace-icon fa fa-check "></i>
                            ${scGroupTopic!=null?"修改":"添加"}
                        </button>
                    </div>
                </form>
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
</style>
<script>
    var contentKe = KindEditor.create('#contentId', {
        //cssPath:"${ctx}/css/ke.css",
        items: ["wordpaste", "source", "clearstyle", "|", "fullscreen"],
        height: '330px',
        width: '720px',
        minWidth: 720,
        filterMode:false,
        pasteType:0
    });

    var memoKe = KindEditor.create('#memoId', {
        //cssPath:"${ctx}/css/ke.css",
        items: ["wordpaste", "source", "|", "fullscreen"],
        height: '255px',
        width: '720px',
        minWidth: 720,
        filterMode:false,
        pasteType:0
    });

    $("#modalForm select[name=type]").on("change", function(){
        var topicType = $(this).val();

        $(".typeSelect").hide().find("input,select, textarea").prop("required", false);
         $(".typeSelect.content").show();
        $(".widget-box.memo").addClass("collapsed");
        if(topicType>0){
            var mt = _cMap.metaTypeMap[topicType];
            //console.log(mt)
            if(mt!=undefined){
                 $(".typeSelect.content").hide();
                if(mt.code=='mt_sgt_motion'){
                    $(".typeSelect.unitPostId, .typeSelect.scType, .typeSelect.motion, .typeSelect.content").show()
                    .find("input,select").prop("required", true);
                }else if(mt.code=='mt_sgt_candidate'){
                     $(".typeSelect.recordId, .typeSelect.unitPostId").show()
                     .find("input,select").prop("required", true);
                     $(".typeSelect.candidateUserIds").show();
                }else if(mt.code=='mt_sgt_recommend'){
                     $(".typeSelect.recordId, .typeSelect.unitPostId, .typeSelect.candidateUserId").show()
                     .find("input,select").prop("required", true);
                     $(".widget-box.memo").removeClass("collapsed");
                }else if(mt.code=='mt_sgt_other'){
                    $(".typeSelect.content").show();
                }
            }
        }
    }).change();

    $('#modalForm select[name="recordId"]').on('change',function(){

        var recordId=$(this).val();
        $('#modalForm select[name="candidateUserId"]').data('ajax-url', "${ctx}/sc/scGroupTopic_users?recordId="+recordId);
        $.register.ajax_select($("#modalForm select[name=candidateUserId]"))
    }).change();

    var $userSelect = $("#modalForm select[name=userId]");
    $.register.user_select($userSelect);
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

        $.post("${ctx}/sc/scGroupTopic_selectUser", {userId: userId}, function (ret) {

            if (ret.success) {

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

    var selectUsers = ${cm:toJSONArray(selectUsers)};
    var lastsel;
    $("#jqGrid2").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 200,
        width: 730,
        datatype: "local",
        rowNum: selectUsers.length,
        data: selectUsers,
        //工作证号、姓名、所在单位及职务
        colModel: [
            {
                label: '移除', name: '_remove', width: 90, formatter: function (cellvalue, options, rowObject) {
                    //console.log(options)
                    return '<button class="delRowBtn btn btn-danger btn-xs" type="button" data-id="{0}"><i class="fa fa-minus-circle"></i> 移除</button>'
                        .format(rowObject.userId)
                }
            },
            {label: '工作证号', name: 'code', width: 120},
            {label: '姓名', name: 'realname'},
            {
                label: '所在单位及职务',
                name: 'title',
                width: 320,
                align: 'left',
                editable:true,
                formatter: function (cellvalue, options, rowObject) {
                    return $.trim(cellvalue)
                }
            },
            {hidden: true, key: true, name: 'userId'}
        ], onSelectRow: function(id) {
             if (id && id !== lastsel) {
                 $(this).saveRow(lastsel);
                 $(this).editRow(id, true, null, null);
                 lastsel = id;
             }else{
                 if(lastsel>0){
                    $(this).saveRow(lastsel);
                 }
                 lastsel=undefined;
             }
         }
    }).jqGrid('sortableRows')

    $.fileInput($('#modalForm input[type=file]'));

    var selectUnitIds = ${selectUnitIds};
    $.register.multiselect($('#modalForm select[name=unitIds]'), selectUnitIds, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false, buttonWidth: '240px'
    });

    $.register.multiselect($('#modalForm select[name=historyUnitIds]'), selectUnitIds, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false, buttonWidth: '240px'
    });

    $.register.date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();

    var selectedUnitIds = [];
    $("#submitBtn").click(function () {
        selectedUnitIds = $.map($('#modalForm select[name=unitIds] option:selected, ' +
                '#modalForm select[name=historyUnitIds] option:selected'), function (option) {
            return $(option).val();
        });

        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            var $btn = $("#submitBtn").button('loading');

            if(lastsel>0){
                $("#jqGrid2").saveRow(lastsel);
            }

            var userIds = $("#jqGrid2").jqGrid("getDataIDs");
            var users = [];
            $.each(userIds, function (i, userId) {
                var rowData = $jqGrid.getRowData(userId);
                users.push({userId: userId, realname: rowData.realname, title: rowData.title})
            })

            $(form).ajaxSubmit({
                data: {unitIds: selectedUnitIds,
                    users: $.base64.encode(JSON.stringify(users)),
                    content: contentKe.html(),
                    memo: memoKe.html()},
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