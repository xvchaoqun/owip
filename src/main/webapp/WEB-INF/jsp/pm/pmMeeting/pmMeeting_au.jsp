<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" style="width: 900px">
            <form class="form-horizontal" action="${ctx}/pmMeeting_au?type=${param.type}&reedit=${param.reedit}" id="pmForm" method="post">
            <table class="table table-bordered table-unhover">
                <input class="form-control" type="hidden" name="id"
                       value="${param.id}">
              <c:if test="${adminOnePartyOrBranch==true}">
                <input class="form-control" type="hidden" name="partyId"
                       value="${pmMeeting.partyId}">
                <input class="form-control" type="hidden" name="branchId"
                       value="${pmMeeting.branchId}">
              </c:if>
                <c:if test="${adminOnePartyOrBranch!=true}">
                <tr>
                    <div class="form-group">
                        <td><span class="star">*</span>所在党组织</td>
                        <td colspan="3">
                            <c:if test="${!edit}">
                                ${cm:displayParty(pmMeeting.partyId,pmMeeting.branchId)}
                            </c:if>

                            <c:if test="${edit}">
                                <div class="col-xs-5">
                                    <select required class="form-control" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/party_selects?auth=1"
                                                name="partyId" data-placeholder="请选择${_p_partyName}" data-width="250">
                                        <option value="${pmMeeting.partyId}">${pmMeeting.party.name}</option>
                                    </select>
                                </div>
                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                    <%--<label class="col-xs-3 control-label">选择党支部</label>--%>
                                    <div class="col-xs-5" style="padding-left: 25px;">
                                        <select class="form-control"  data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/branch_selects?del=0&auth=1"
                                                name="branchId" data-placeholder="请选择党支部" data-width="250">
                                            <option value="${pmMeeting.branchId}">${pmMeeting.branch.name}</option>
                                        </select>
                                    </div>
                                </div>
                            </c:if>
                        </td>
                    </div>

                    <script>
                        $.register.party_branch_select($("#pmForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${pmMeeting.partyId}",
                            "${pmMeeting.party.classId}", "partyId", "branchId", true);
                    </script>
                </tr>
                </c:if>
                <c:if test="${adminOnePartyOrBranch==true}">
                <td><span class="star">*</span>所在党组织</td>
                <td colspan="3">
                   ${cm:displayParty(pmMeeting.partyId,pmMeeting.branchId)}
                   <%-- ${cm:displayParty(pmMeeting.partyId,pmMeeting.branchId)}--%>
                </td>
                </c:if>
                <tr>
                    <td width="100"><span class="star">*</span>${param.type!=5?"会议":"主题党日活动"}名称</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.name}
                        </c:if>
                        <c:if test="${edit}">
                        <input  required  class="form-control" type="text" name="name"
                                           value="${pmMeeting.name}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><span class="star">*</span>计划时间</td>
                    <td ${param.type!=5?'':'colspan="3"'}>
                        <c:if test="${!edit}">
                            ${cm:formatDate(pmMeeting.planDate, "yyyy-MM-dd HH:mm")}
                        </c:if>
                        <c:if test="${edit}">
                            <div class="input-group" style="width: 200px">
                                <input  required  class="form-control datetime-picker " type="text"  name="planDate"
                                       value="${cm:formatDate(pmMeeting.planDate, "yyyy-MM-dd HH:mm")}">
                                <span class="input-group-addon">
                                         <i class="fa fa-calendar bigger-110"></i>
                                </span>
                            </div>
                        </c:if>
                    </td>
                    <c:if test="${param.type!=5}">
                        <td><span class="star">*</span>主持人</td>
                        <td>
                            <c:if test="${!edit}">
                                ${pmMeeting.presenterName.realname}
                            </c:if>
                            <c:if test="${edit}">
                            <select  ${empty pmMeeting.partyId&&empty pmMeeting.branchId?'disabled="disabled"':''}
                                    required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?partyId=${pmMeeting.partyId}&branchId=${pmMeeting.branchId}&status=${MEMBER_STATUS_NORMAL}"
                                    data-width="270" id="presenter" name="presenter" data-placeholder="请选择">
                                <option value="${pmMeeting.presenter}">${pmMeeting.presenterName.realname}-${pmMeeting.presenterName.code}</option>
                            </select>
                            </c:if>
                        </td>
                    </c:if>
                </tr>
                <tr>
                    <td><span class="star">*</span>实际时间</td>
                    <td>
                        <c:if test="${!edit}">
                            ${cm:formatDate(pmMeeting.date, "yyyy-MM-dd HH:mm")}
                        </c:if>
                        <c:if test="${edit}">
                            <div class="input-group" style="width: 200px">
                                <input  required  class="form-control datetime-picker " type="text"  name="date"
                                        value="${cm:formatDate(pmMeeting.date, "yyyy-MM-dd HH:mm")}">
                                <span class="input-group-addon">
                                         <i class="fa fa-calendar bigger-110"></i>
                                </span>
                            </div>
                        </c:if>
                    </td>

                    <td><span class="star">*</span>记录人</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.recorderName.realname}
                        </c:if>
                        <c:if test="${edit}">
                        <select ${empty pmMeeting.partyId&&empty pmMeeting.branchId?'disabled="disabled"':''}
                                required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?partyId=${pmMeeting.partyId}&branchId=${pmMeeting.branchId}&status=${MEMBER_STATUS_NORMAL}"
                                data-width="270" id="recorder" name="recorder" data-placeholder="请选择">
                            <option value="${pmMeeting.recorder}">${pmMeeting.recorderName.realname}-${pmMeeting.recorderName.code}</option>
                        </select>
                        </c:if>
                        <script type="text/javascript">
                            $('#pmForm select[name="recorder"]').val("${pmMeeting.recorder}").trigger('change');
                        </script>
                    </td>
                </tr>

                <tr>
                    <td><span class="star">*</span>${param.type!=5?"会议":"活动"}地点</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.address}
                        </c:if>
                        <c:if test="${edit}">
                            <input   required class="form-control" type="text" name="address"
                                     value="${pmMeeting.address}">
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td>参会人员
                        <c:if test="${edit}">

                            <button type="button" id ="attend" ${empty pmMeeting.partyId&&empty pmMeeting.branchId?'disabled="disabled"':''}
                            class="popupBtn btn btn-info btn-xs"
                            data-width="900"
                            data-url="${ctx}/pmMeeting_member?type=1&partyId=${pmMeeting.partyId}&branchId=${pmMeeting.branchId}">
                            <i class="fa fa-plus-circle"></i> 选择</button>
                        </c:if>
                    </td>
                    <td colspan="3">
                        <div style="max-height:200px; overflow:auto;">
                        <table id="attendTable"  class="table table-bordered table-condensed"
                        data-pagination="true" data-side-pagination="client" data-page-size="5">
                            <thead>
                            <tr>
                                <th width="30%">工作证号</th>
                                <th>党员姓名</th>
                                <th>党内职务</th>
                                <th>手机号</th>
                                <c:if test="${edit}">
                                    <th></th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        </div>

                    </td>
                </tr>
                <tr>
                    <td>请假人员
                        <c:if test="${edit}">
                            <button type="button" id ="absent" ${empty pmMeeting.partyId&&empty pmMeeting.branchId?'disabled="disabled"':''}
                               class="popupBtn btn btn-info btn-xs"
                               data-width="900"
                               data-url="${ctx}/pmMeeting_member?type=2&partyId=${pmMeeting.partyId}&branchId=${pmMeeting.branchId}">
                                <i class="fa fa-plus-circle"></i> 选择</button>
                        </c:if>
                    </td>
                    <td colspan="3">
                        <div style="max-height:135px; overflow:auto;">
                            <table id="absentTable"  class="table table-bordered table-condensed"
                                   data-pagination="true" data-side-pagination="client" data-page-size="5">
                                <thead>
                                <tr>
                                    <th width="30%">工作证号</th>
                                    <th>党员姓名</th>
                                    <th>党内职务</th>
                                    <th>手机号</th>
                                    <c:if test="${edit}">
                                         <th></th>
                                    </c:if>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </td>

                </tr>
                <tr>
                    <td><span class="star">*</span>实到人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.attendNum}
                        </c:if>
                        <c:if test="${edit}">
                            <input required class="form-control" type="text" name="attendNum"
                                   value="${pmMeeting.attendNum}">
                        </c:if>
                    </td>
                    <td>请假人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.absentNum}
                        </c:if>
                        <c:if test="${edit}">
                            <input class="form-control" type="text" name="absentNum"
                                    value="${pmMeeting.absentNum}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>请假原因</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.absentReason}
                        </c:if>
                        <c:if test="${edit}">
                            <input class="form-control" type="text" name="absentReason"
                                    value="${pmMeeting.absentReason}">
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td><span class="star">*</span>应到人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${empty pmMeeting.dueNum?allMembersNum:pmMeeting.dueNum}
                        </c:if>
                        <c:if test="${edit}">
                        <input required  class="form-control" type="text" name="dueNum"
                               value="${pmMeeting.dueNum}">
                        </c:if>
                    </td>

                    <td>列席人员</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.invitee}
                        </c:if>
                        <c:if test="${edit}">
                            <input class="form-control" type="text" name="invitee"
                                   value="${pmMeeting.invitee}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><span class="star">*</span>${param.type!=5?"会议议题":"活动主题"}</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.issue}
                        </c:if>
                        <c:if test="${edit}">
                        <input  required class="form-control" type="text" name="issue"
                               value="${pmMeeting.issue}">
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td style="padding: 80px"><span class="star">*</span>${param.type!=5?"会议内容":"主要内容及特色"}</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.content}
                        </c:if>
                        <c:if test="${edit}">
                            <textarea  required name="content" rows="10"
                                  style="width: 100%">${pmMeeting.content}</textarea>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${param.type!=5}">
                    <tr>
                        <td style="padding: 80px">会议决议</td>
                        <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.decision}
                        </c:if>
                        <c:if test="${edit}">
                            <textarea  name="decision" rows="10"
                                      style="width: 100%">${pmMeeting.decision}</textarea>
                        </c:if>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>是否公开</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.isPublic?"是":"否"}
                        </c:if>
                        <c:if test="${edit}">
                            <input type="checkbox" class="big" name="isPublic" ${pmMeeting.isPublic?"checked":""}/>

                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td>附件</td>
                    <td colspan="3">
                        <c:forEach items="${pmMeetingFiles}" var="file" varStatus="vs">
                            <div id="file${file.id}" class="file row well well-sm"
                                 style="padding: 0;margin-bottom: 8px;width: 150px;margin-left: 1px;">
                                <div style="padding-left:10px;width: 80px;float: left;">
                                    <a href="${ctx}/attach_download?path=${cm:sign(file.filePath)}&filename=${file.fileName}">${vs.count}、附件</a>
                                </div>
                                <c:if test="${edit}">
                                    <div style="width:60px;float: left"><a href="javascript:;" onclick="_delFile(${file.id}, '附件')">删除</a>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                        <c:if test="${edit}">
                        <div id="fileDiv">
                            <div class="files">
                                <input class="form-control" type="file" name="_files"/>
                            </div>
                            <div id="fileButton"style="padding-left: 50px">
                                <div style="padding-left: 50px">
                                     <button type="button" onclick="addFile()"
                                    class="addFileBtn btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
                            </div>
                            </div>
                        </div>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting.remark}
                        </c:if>
                        <c:if test="${edit}">
                            <textarea class="form-control" type="text" name="remark">${pmMeeting.remark}</textarea>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${edit}">
                <tr>
                    <td colspan="4">

                        <div class="modal-footer center">

                            <button id="pmSubmitBtn"
                                    class="btn btn-success btn-xlg"><i
                                    class="fa fa-check"></i> 确定
                            </button>
                        </div>

                    </td>
                </tr>
                </c:if>
            </table>
            </form>
        </div>
    </div>
</div>
<style>
    .table.table-unhover > tbody > tr > td:nth-of-type(odd) {
        text-align: center;
        font-size: 18px;
        font-weight: bolder;
        vertical-align: middle;
    }

    textarea {
        text-indent: 32px;
        line-height: 25px;
        /*font-family: "Arial";*/
        font-size: 16px;
        padding: 2px;
        margin: 2px;
        border: none;
        background: #FFFFFF url(/img/dot_25.gif);
        resize: none;
    }

    .files{
        width: 300px;
        float: left;
        margin-top: 20px;
    }
    .addFileBtn{
        position: absolute;
        margin: 22px 20px;
    }
    .file{
        height: 25px;
        line-height: 25px;
        background: #FFFFFF url(/img/dot_25.gif);
        width: 350px;
        /*border-bottom: dotted 1px;*/
    }
    .file a.confirm{
        float: right;
    }
    .file:hover{
        background-color:#eadda9;
    }
</style>
<link href="${ctx}/extend/css/bootstrap-tagsinput.css" rel="stylesheet"/>
<script src="${ctx}/extend/js/bootstrap-tagsinput.min.js"></script>
<script type="text/template" id="seconder_tpl">
    {{_.each(users, function(u, idx){ }}

    <tr data-user-id="{{=u.userId}}">
        <td>{{=u.code}}</td>
        <td>{{=u.realname}}</td>
        <td>{{=u.partyPost}}</td>
        <td>{{=u.mobile}}</td>
        <c:if test="${edit}">
            <td>
               <button ${!allowModify?"":"disabled"} class="delRowBtn btn btn-danger btn-xs"><i class="fa fa-minus-circle"></i> 移除</button>
            </td>
        </c:if>
    </tr>
    {{});}}
</script>
<script>
    var presenterSelect = $('#pmForm select[name="presenter"]');
    var recorderSelect = $('#pmForm select[name="recorder"]');

    $.register.datetime($('.datetime-picker'));

    $.register.user_select(presenterSelect);
    $.register.user_select(recorderSelect);
    $("#pmForm input[name=isPublic]").bootstrapSwitch();

    $("#attendTable tbody").append(_.template($("#seconder_tpl").html())({users: ${cm:toJSONArray(pmMeeting.attendList)}}));
    $("#absentTable tbody").append(_.template($("#seconder_tpl").html())({users: ${cm:toJSONArray(pmMeeting.absentList)}}));

    if(${adminOnePartyOrBranch==true}){
        $("#pmForm input[name=dueNum]").val(${memberCount});
    }

    var partySelect = $('#pmForm select[name="partyId"]');
    partySelect.on('change',function(){
        var partyId=partySelect.val();
        if ($.isBlank(partyId)){
            $("#pmForm input[name=dueNum]").val('');
            presenterSelect.attr("disabled",true);
            recorderSelect.attr("disabled",true);
            $('#attend').attr("disabled",true);
            $('#absent').attr("disabled",true);
            return;
        }
        var data = partySelect.select2("data")[0];
        if(data.class==${cm:getMetaTypeByCode("mt_direct_branch").id}){


            presenterSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&status="+${MEMBER_STATUS_NORMAL});
            recorderSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&status="+${MEMBER_STATUS_NORMAL});

            $.register.user_select(presenterSelect);
            $.register.user_select(recorderSelect);

            $('#attend').data('url', "pmMeeting_member?type=1&partyId="+partyId);
            $('#absent').data('url', "pmMeeting_member?type=2&partyId="+partyId);

            presenterSelect.removeAttr("disabled");
            recorderSelect.removeAttr("disabled");
            $('#attend').removeAttr("disabled");
            $('#absent').removeAttr("disabled");

            var data = partySelect.select2("data")[0];
            $("#pmForm input[name=dueNum]").val(data['party'].memberCount);
        }

     });

    var branchSelect = $('#pmForm select[name="branchId"]');
    branchSelect.on('change',function(){

        var partyId=partySelect.val();
        var branchId=branchSelect.val();

        if ($.isBlank(branchId)){
            $("#pmForm input[name=dueNum]").val('');
            presenterSelect.attr("disabled",true);
            recorderSelect.attr("disabled",true);
            $('#attend').attr("disabled",true);
            $('#absent').attr("disabled",true);
            return;
        }

        presenterSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&branchId="+branchId+"&status="+${MEMBER_STATUS_NORMAL});
        recorderSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&branchId="+branchId+"&status="+${MEMBER_STATUS_NORMAL});

        $.register.user_select(presenterSelect);
        $.register.user_select(recorderSelect);

        $('#attend').data('url', "pmMeeting_member?type=1&partyId="+partyId+"&branchId="+branchId);
        $('#absent').data('url', "pmMeeting_member?type=2&partyId="+partyId+"&branchId="+branchId);

        presenterSelect.removeAttr("disabled");
        recorderSelect.removeAttr("disabled");
        $('#attend').removeAttr("disabled");
        $('#absent').removeAttr("disabled");

        var data = branchSelect.select2("data")[0];
        if(data['branch']) {
            console.log('--------')
            $("#pmForm input[name=dueNum]").val(data['branch'].memberCount);
        }else{
            console.log(data)
        }
       });

    $(document).on("click", "#attendTable button", function () {

        $(this).closest("tr").remove();
        $("#pmForm input[name=attendNum]").val($("#attendTable tbody tr").length);
    });
    $(document).on("click", "#absentTable button", function () {
        $(this).closest("tr").remove();
        $("#pmForm input[name=absentNum]").val($("#absentTable tbody tr").length);

    });

    $("#pmSubmitBtn").click(function(){$("#pmForm").submit();return false;});
    $("#pmForm").validate({
        submitHandler: function (form) {
            var attendIds = [];
            var absentIds = [];
            $("#attendTable tbody tr").each(function(){
                attendIds.push($(this).data("user-id"));
            });
            $("#absentTable tbody tr").each(function(){
                absentIds.push($(this).data("user-id"));
            });
            var data = {attendIds:attendIds,absentIds:absentIds};
            var $btn = $("#pmSubmitBtn").button('loading');
            $(form).ajaxSubmit({
                data: data,
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success("保存成功。", function () {
                            $.hideView();
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $.fileInput($('input[type=file]'), {
        no_file: '请上传附件...',
        // allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif','doc', 'docx'],
        // allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif','application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
    var i = 1;
    function addFile() {
        i++;
       var _file = $('<div id="file'+i+'"><input  class="form-control" type="file" name="_files" /></div>');
      $(".files").append(_file);
       var _fileButton = $('<div id="btn'+i+'" style="padding-top: 35px"><button type="button" data-i="'+i+'" onclick="delfileInput(this)"class="addFileBtn btn btn-default btn-xs"><i class="fa fa-trash"></i></button></div>');
      $("#fileButton").append(_fileButton);
        $.fileInput($('input[type=file]', $(_file)), {
            no_file: '请上传附件...',
          //  allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
           // allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
        });
        return false;
    }
    function delfileInput(btn) {
        var i = $(btn).data("i");
        $("#file"+i).remove();
        $("#btn"+i).remove();
    }
    function _delFile(id, name) {
        bootbox.confirm("确定删除该材料？", function (result) {
            if (result) {
                $.post("${ctx}/pmMeetingFile_del", {id: id}, function (ret) {
                    if (ret.success) {

                        $("#file" + id).remove();
                        if ($("#fileGroup").find(".file").length == 0) {
                            $("#fileGroup").remove();
                        }
                    }
                });
            }
        });
    }
</script>