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
            <form class="form-horizontal" action="${ctx}/pmMeeting_au?type=${param.type}&reedit=${param.reedit}" id="modalForm" method="post">
            <table class="table table-bordered table-unhover">
                <input class="form-control" type="hidden" name="id"
                       value="${param.id}">
              <c:if test="${not empty pmMeeting.partyId &&not empty pmMeeting.branchId}">
                <input class="form-control" type="hidden" name="partyId"
                       value="${pmMeeting.partyId}">
                <input class="form-control" type="hidden" name="branchId"
                       value="${pmMeeting.branchId}">
              </c:if>
                <c:if test="${empty pmMeeting.id&&empty pmMeeting.partyId}">
                <tr>
                    <div class="form-group">
                        <td><span class="star">*</span>所属分党委</td>
                        <td>
                            <div class="col-xs-6">
                                <select required class="form-control" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/party_selects?auth=1"
                                            name="partyId" data-placeholder="请选择" data-width="270">
                                    <option value="${party.id}">${party.name}</option>
                                </select>
                            </div>
                        </td>
                    </div>

                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                    <td><span class="star">*</span>所属党支部</td>
                    <td>
                        <div class="col-xs-6">
                            <select class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
                                    name="branchId" data-placeholder="请选择" data-width="270">
                                <option value="${branch.id}">${branch.name}</option>
                            </select>
                        </div>
                    </td>
                </div>
                    <script>
                        $.register.party_branch_select($("#modalForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                    </script>
                </tr>
                </c:if>
                <tr>
                    <td width="100">会议名称</td>
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
                    <td>会议时间</td>
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

                    <td>主持人</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.presenterName.realname}
                        </c:if>
                        <c:if test="${edit}">
                        <select  required  data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?branchId=${pmMeeting.branchId}" data-width="350"
                                 id="presenter" name="presenter" data-placeholder="请选择">
                            <option value="${pmMeeting.presenter}">${pmMeeting.presenterName.realname}-${pmMeeting.presenterName.code}</option>
                        </select>
                        </c:if>
                    </td>

                </tr>
                <tr>
                    <td>会议地点</td>
                    <td>
                        <c:if test="${!edit}">
                           ${pmMeeting.address}
                        </c:if>
                        <c:if test="${edit}">
                            <input   required class="form-control" type="text" name="address"
                                   value="${pmMeeting.address}">
                        </c:if>
                    </td>
                    <td>记录人</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.recorderName.realname}
                        </c:if>
                        <c:if test="${edit}">
                        <select  required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?branchId=${pmMeeting.branchId}" data-width="350"
                                 id="recorder" name="recorder" data-placeholder="请选择">
                            <option value="${pmMeeting.recorder}">${pmMeeting.recorderName.realname}-${pmMeeting.recorderName.code}</option>
                        </select>
                        </c:if>
                        <script type="text/javascript">
                            $('#modalForm select[name="recorder"]').val("${pmMeeting.recorder}").trigger('change');
                        </script>
                    </td>
                </tr>
                <tr>
                    <td>列席人员</td>
                    <td colspan="3">
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
                    <td>参会人员
                        <a href="javascript:;"
                           class="popupBtn btn btn-info btn-xs"
                           data-width="900"
                           data-url="${ctx}/pmMeeting_member?partyId=${pmMeeting.partyId}&branchId=${pmMeeting.branchId}">
                            <i class="fa fa-plus-circle"></i> 选择</a></td>
                    <td colspan="3">
                        <div style="height:200px; overflow:auto;">
                        <table id="attendTable"  class="table table-bordered table-condensed"
                        data-pagination="true" data-side-pagination="client" data-page-size="5">
                            <thead>
                            <tr>
                                <th>工作证号</th>
                                <th>党员姓名</th>
                                <th>党内职务</th>
                                <th>手机号</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        </div>
                        <%--<c:if test="${!edit&&not empty pmMeeting.users}">--%>
                            <%--<c:forEach items="${pmMeeting.users}" var="user">--%>
                                <%--${user.realname}、--%>
                            <%--</c:forEach>--%>
                        <%--</c:if>--%>
                        <%--<c:if test="${edit}">--%>
                   <%----%>
                        <%--</c:if>--%>
                    </td>
                </tr>
                <tr>
                    <td>请假人员</td>
                    <td>
                    <c:if test="${!edit}">
                        DFJ
                    </c:if>
                    <%--<c:if test="${edit}">--%>
                        <%--<select class="multiselect" name="absents" multiple="">--%>
                            <%--&lt;%&ndash;<c:forEach items="${membersViews}" var="memberView">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<option value="${memberView.userId}">${memberView.realname}</option>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                        <%--</select>--%>
                    <%--</c:if>--%>
                    </td>
                    <td>请假人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.absentNum}
                        </c:if>
                        <c:if test="${edit}">
                        <input  required class="form-control" type="text" name="absentNum"
                               value="${pmMeeting.absentNum}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>应到人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${empty pmMeeting.dueNum?allMembersNum:pmMeeting.dueNum}
                        </c:if>
                        <c:if test="${edit}">
                        <input required  class="form-control" type="text" name="dueNum"
                               value="${pmMeeting.dueNum}">
                        </c:if>
                    </td>
                    <td>实到人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting.attendNum}
                        </c:if>
                        <c:if test="${edit}">
                        <input required class="form-control" type="text" name="attendNum"
                               value="${pmMeeting.attendNum}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>会议议题</td>
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
                    <td style="padding: 80px">会议内容</td>
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
                                    <a href="${ctx}/attach_download?path=${cm:encodeURI(file.filePath)}&filename=${file.fileName}">${vs.count}、附件</a>
                                </div>
                                <c:if test="${edit}">
                                    <div style="width:60px;float: left"><a href="javascript:;" onclick="_delFile(${file.id}, '附件')">删除</a>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                        <c:if test="${edit}">
                            <div class="files">
                                <input class="form-control" type="file" name="_files[]"/>
                            </div>
                            <div style="padding-left: 50px">
                            <button type="button" onclick="addFile()"
                                    class="addFileBtn btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
                            </div>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${edit}">
                <tr>
                    <td colspan="4">

                        <div class="modal-footer center">
                            <button type="button" class="hideView btn btn-default btn-xlg"><i
                                    class="fa fa-reply"></i> 取消
                            </button>

                            <button id="submitBtn"
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
        <td>
            <button ${!allowModify?"":"disabled"} class="delRowBtn btn btn-danger btn-xs"><i class="fa fa-minus-circle"></i> 移除</button>
        </td>
    </tr>
    {{});}}
</script>
<script>

    $.register.datetime($('.datetime-picker'));
    //$.register.multiselect($('#modalForm select[class="multiselect"]'));
   // $('#modalForm [data-rel="select2"]').select2();

    $.register.user_select($('#modalForm select[name=presenter]'));
    $.register.user_select($('#modalForm select[name=recorder]'));
    $("#modalForm input[name=isPublic]").bootstrapSwitch();
    $("#attendTable tbody").append(_.template($("#seconder_tpl").html())({users: ${cm:toJSONArray(pmMeeting.attendList)}}));

    $('#modalForm select[name="branchId"]').on('change',function(){
        var partyId=$('#modalForm select[name=partyId]').val();
        var branchId=$('#modalForm select[name=branchId]').val();
        $('#modalForm select[name="presenter"]').data('ajax-url', "${ctx}/member_selects?branchId="+branchId);
        $('#modalForm select[name="recorder"]').data('ajax-url', "${ctx}/member_selects?branchId="+branchId);
        $.register.user_select($('#modalForm select[name=presenter]'));
        $.register.user_select($('#modalForm select[name=recorder]'));
       $("a").data('url', "pmMeeting_member?partyId="+partyId+"&branchId="+branchId);
       });

    // $('#modalForm select[name="attends"]').on('change',function(){
    //     if($('#modalForm select[name="attends"]').val()!=null){
    //         $('input[name="attendNum"]').val($('#modalForm select[name="attends"]').val().length);
    //         }else{
    //             $('input[name="attendNum"]').val(undefined);
    //         }
    // });
    // $('#modalForm select[name="absents"]').on('change',function(){
    //     if($('#modalForm select[name="absents"]').val()!=null){
    //         $('input[name="absentNum"]').val($('#modalForm select[name="absents"]').val().length);
    //     }else{
    //         $('input[name="absentNum"]').val(undefined);
    //     }
    // });
    $(document).on("click", ".delRowBtn", function () {

        $(this).closest("tr").remove();
    })
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var attendIds = [];
            $("#attendTable tbody tr").each(function(){
                attendIds.push($(this).data("user-id"));
            });
            //alert(inviteIds)
            var data = {attendIds:attendIds};

            $(form).ajaxSubmit({
                data: data,
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success("保存成功。", function () {
                            $.hideView();
                        });
                    }
                }
            });
        }
    });
    $.fileInput($('input[type=file]'), {
        no_file: '请上传附件...',
        // allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif','doc', 'docx'],
        // allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif','application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
    function addFile() {
        var _file = $('<input class="form-control" type="file" name="_files[]" />');
        $(".files").append(_file);
        $.fileInput(_file, {
            no_file: '请上传附件...',
          //  allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
           // allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
        });
        return false;
    }
    function _delFile(id, name) {
        bootbox.confirm("确定删除该材料？", function (result) {
            if (result) {
                $.post("${ctx}/pmMeetingFile_del", {id: id}, function (ret) {
                    if (ret.success) {
                        //SysMsg.success("删除成功",'',function(){
                        $("#file" + id).remove();
                        if ($("#fileGroup").find(".file").length == 0) {
                            $("#fileGroup").remove();
                        }
                        //});
                    }
                });
            }
        });
    }
</script>