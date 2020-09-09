<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="PARTY_MEETING2_MAP" value="<%=sys.constants.PmConstants.PARTY_MEETING2_MAP%>"/>
<c:set var="PARTY_MEETING2_BRANCH_GROUP" value="<%=sys.constants.PmConstants.PARTY_MEETING2_BRANCH_GROUP%>"/>
<c:set var="PARTY_MEETING2_BRANCH_ACTIVITY" value="<%=sys.constants.PmConstants.PARTY_MEETING2_BRANCH_ACTIVITY%>"/>
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
            <form class="form-horizontal" action="${ctx}/pmMeeting2_au?reedit=${param.reedit}" id="modalForm" method="post">
            <table class="table table-bordered table-unhover">
                <input class="form-control" type="hidden" name="id"
                       value="${param.id}">
              <c:if test="${adminOnePartyOrBranch==true}">
                <input class="form-control" type="hidden" name="partyId"
                       value="${pmMeeting2.partyId}">
                <input class="form-control" type="hidden" name="branchId"
                       value="${pmMeeting2.branchId}">
                <tr>
                      <td><span class="star">*</span>所在党组织</td>
                      <td colspan="3">
                              ${cm:displayParty(pmMeeting2.partyId,pmMeeting2.branchId)}
                              <%-- ${cm:displayParty(pmMeeting.partyId,pmMeeting.branchId)}--%>
                      </td>
                </tr>
              </c:if>
              <c:if test="${adminOnePartyOrBranch!=true}">
                <tr>
                    <div class="form-group">
                        <td><span class="star">*</span>所在党组织</td>
                        <td colspan="3">
                            <c:if test="${!edit}">
                                ${cm:displayParty(pmMeeting2.partyId,pmMeeting2.branchId)}
                            </c:if>

                            <c:if test="${edit}">
                                    <select required class="form-control" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/party_selects?auth=1"
                                                name="partyId" data-placeholder="请选择${_p_partyName}" data-width="250">
                                        <option value="${pmMeeting2.partyId}">${pmMeeting2.party.name}</option>
                                    </select>
                                    <span style="${(empty branch)?'display: none':''}" id="branchDiv" style="padding-left: 25px;">
                                        <select class="form-control"  data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/branch_selects?del=0&auth=1"
                                                name="branchId" data-placeholder="请选择党支部" data-width="250">
                                            <option value="${pmMeeting2.branchId}">${pmMeeting2.branch.name}</option>
                                        </select>
                                    </span>
                            </c:if>
                        </td>
                    </div>

                    <script>
                        $.register.party_branch_select($("#modalForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${pmMeeting2.partyId}",
                            "${pmMeeting2.party.classId}", "partyId", "branchId", true);
                    </script>
                </tr>
                </c:if>

                <tr>
                    <td><span class="star">*</span>实际时间</td>
                    <td>
                        <c:if test="${!edit}">
                            ${cm:formatDate(pmMeeting2.date, "yyyy-MM-dd HH:mm")}
                        </c:if>
                        <c:if test="${edit}">
                            <div class="input-group" style="width: 200px">
                                <input  required  class="form-control datetime-picker " type="text"  name="date"
                                        value="${cm:formatDate(pmMeeting2.date, "yyyy-MM-dd HH:mm")}">
                                <span class="input-group-addon">
                                         <i class="fa fa-calendar bigger-110"></i>
                                </span>
                            </div>
                        </c:if>
                    </td>
                    <td><span class="star">*</span>地点</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting2.address}
                        </c:if>
                        <c:if test="${edit}">
                            <input   required class="form-control" type="text" name="address"
                                     value="${pmMeeting2.address}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><span class="star">*</span>活动名称</td>
                    <td colspan="3">
                        <div class="input-group" style="width: 100%">
                           <table class="table table-bordered table-unhover2" name="typeTable">
                               <c:forEach items="${PARTY_MEETING2_MAP}" var="entity">
                                  <tr>
                                      <td>
                                       <div class="checkbox checkbox-inline checkbox-sm">
                                           <input ${edit?'':'disabled'} required type="checkbox" name="type" id="identity${entity.key}"
                                                                        value="${entity.key}">
                                           <label for="identity${entity.key}">${entity.value}</label>
                                       </div>
                                      </td>
                                      <td>
                                          <span style="margin-right:5px;">第</span>
                                          <input disabled class="input${entity.key}" style="width:100px;display:inline;text-align:center;"type="text" name="number_${entity.key}" value="">
                                          <span style="margin-left:5px;">次</span>
                                      </td>
                                      <td>时长</td>
                                      <td>
                                          <input disabled class="input${entity.key}" style="width:100px;display:inline;text-align:center;" type="text" name="time_${entity.key}" value="">
                                          <span style="margin-left:5px;">分钟</span>
                                      </td>
                                  </tr>
                               </c:forEach>
                           </table>
                            </div>
                        <script>
                               $('#modalForm input[name="type"][value="'+${pmMeeting2.type1} +'"]').prop("checked", true);
                               $(".input${pmMeeting2.type1}").attr("required","required")
                               $(".input${pmMeeting2.type1}:first").val(${pmMeeting2.number1});
                               $(".input${pmMeeting2.type1}:last").val(${pmMeeting2.time1});

                               $('#modalForm input[name="type"][value="'+${pmMeeting2.type2} +'"]').prop("checked", true);
                               $(".input${pmMeeting2.type2}").attr("required","required")
                               $(".input${pmMeeting2.type2}:first").val(${pmMeeting2.number2});
                               $(".input${pmMeeting2.type2}:last").val(${pmMeeting2.time2});
                                if(${edit}){
                                   if($("#identity${pmMeeting2.type1}").prop("checked")){
                                       $(".input${pmMeeting2.type1}").attr("disabled" ,false);
                                   }

                                   if($("#identity${pmMeeting2.type2}").prop("checked")){
                                       $(".input${pmMeeting2.type2}").attr("disabled" ,false);
                                   }
                               }
                        </script>
                    </td>

                </tr>

                <tr>
                    <td width="100"><span class="star">*</span>主要内容</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting2.shortContent}
                        </c:if>
                        <c:if test="${edit}">
                        <input  required  class="form-control" type="text" name="shortContent"
                                           value="${pmMeeting2.shortContent}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>应到人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting2.dueNum}
                        </c:if>
                        <c:if test="${edit}">
                            <input class="form-control" type="text" name="dueNum"
                                   value="${pmMeeting2.dueNum}">
                        </c:if>
                    </td>

                    <td>实到人数</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting2.attendNum}
                        </c:if>
                        <c:if test="${edit}">
                            <input class="form-control" type="text" name="attendNum"
                                   value="${pmMeeting2.attendNum}">
                        </c:if>
                    </td>
                </tr>

                <tr>
                <td>缺席人员及原因</td>
                <td colspan="3">
                    <c:if test="${!edit}">
                        ${pmMeeting2.absents}
                    </c:if>
                    <c:if test="${edit}">
                        <input class="form-control" type="text" name="absents"
                               value="${pmMeeting2.absents}">
                    </c:if>
                </td>
                </tr>

                <tr>

                        <td>主持人</td>
                        <td>
                            <c:if test="${!edit}">
                                ${pmMeeting2.presenterName.realname}
                            </c:if>
                            <c:if test="${edit}">
                            <select  ${empty pmMeeting2.partyId&&empty pmMeeting2.branchId?'disabled="disabled"':''}
                                     data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?partyId=${pmMeeting2.partyId}&branchId=${pmMeeting2.branchId}&status=${MEMBER_STATUS_NORMAL}"
                                    data-width="270" id="presenter" name="presenter" data-placeholder="请选择">
                                <option value="${pmMeeting2.presenter}">${pmMeeting2.presenterName.realname}-${pmMeeting2.presenterName.code}</option>
                            </select>
                            </c:if>
                        </td>
                    <td>记录人</td>
                    <td>
                        <c:if test="${!edit}">
                            ${pmMeeting2.recorderName.realname}
                        </c:if>
                        <c:if test="${edit}">
                            <select ${empty pmMeeting2.partyId&&empty pmMeeting2.branchId?'disabled="disabled"':''}
                                    data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?partyId=${pmMeeting2.partyId}&branchId=${pmMeeting2.branchId}&status=${MEMBER_STATUS_NORMAL}"
                                    data-width="270" id="recorder" name="recorder" data-placeholder="请选择">
                                <option value="${pmMeeting2.recorder}">${pmMeeting2.recorderName.realname}-${pmMeeting2.recorderName.code}</option>
                            </select>
                        </c:if>
                        <script type="text/javascript">
                            $('#modalForm select[name="recorder"]').val("${pmMeeting2.recorder}").trigger('change');
                        </script>
                    </td>
                </tr>

                <tr>
                    <td style="padding: 80px">会议内容</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting2.content}
                        </c:if>
                        <c:if test="${edit}">
                            <textarea name="content" rows="10"
                                  style="width: 100%">${pmMeeting2.content}</textarea>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td><span class="star">*</span>附件
                      <%-- <c:if test="${not empty pmMeeting2.filePath}">

                            <c:if test="${fn:endsWith(fn:toLowerCase(pmMeeting2.filePath), '.pdf')}">
                                <a href="${ctx}/pdf?path=${cm:sign(pmMeeting2.filePath)}" target="_blank" style="font-size: 14px;font-weight: normal">(预览</a>
                            </c:if>

                            <c:if test="${!fn:endsWith(fn:toLowerCase(pmMeeting2.filePath), '.pdf')}">
                                <a href="${ctx}/pic?path=${cm:sign(pmMeeting2.filePath)}" target="_blank" style="font-size: 14px;font-weight: normal">(预览</a>
                            </c:if>

                            <a href="javascript:;" data-type="download" style="font-size: 14px;font-weight: normal"
                               data-url="${ctx}/attach_download?path=${cm:sign(pmMeeting2.filePath)}&filename=${pmMeeting2.fileName}"
                               class="downloadBtn">下载)</a>
                       </c:if>--%>
                    </td>
                    <td colspan="3">
                        <div class="col-xs-12" style="margin-bottom: 10px">
                            <c:if test="${not empty pmMeeting2Files}">
                            <table class="table table-bordered table-condensed"
                                   data-pagination="true" data-side-pagination="client" data-page-size="5">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>附件</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${pmMeeting2Files}" var="file" varStatus="vs">
                                <tr>
                                    <th>${vs.count}</th>
                                    <th>${pmMeeting2FileNames.get(vs.index)}</th>
                                    <th>
                                        <c:if test="${fn:endsWith(fn:toLowerCase(file), '.pdf')}">
                                        <a href="${ctx}/pdf?path=${cm:sign(file)}" target="_blank" style="font-size: 14px;font-weight: normal">预览</a>
                                        </c:if>
                                        <c:if test="${!fn:endsWith(fn:toLowerCase(file), '.pdf')}">
                                            <a href="${ctx}/pic?path=${cm:sign(file)}" target="_blank" style="font-size: 14px;font-weight: normal">预览</a>
                                        </c:if>
                                        <a href="javascript:;" data-type="download" style="font-size: 14px;font-weight: normal"
                                           data-url="${ctx}/attach_download?path=${cm:sign(file)}"
                                           class="downloadBtn">下载</a>
                                        <c:if test="${edit}">
                                            <a href="javascript:;" style="font-size: 14px;font-weight: normal"
                                               onclick="delFile(${pmMeeting2.id},${vs.index},'${pmMeeting2FileNames.get(vs.index)}')">删除</a>
                                        </c:if>
                                    </th>
                                </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            </c:if>
                        </div>
                        <c:if test="${edit}">
                         <%--   <div class="col-xs-6">
                                <input ${empty pmMeeting2.filePath?'required':''} class="form-control" type="file" name="_file" />
                            </div>--%>

                                <div class="col-xs-6">
                                    <div class="files">
                                        <input ${empty pmMeeting2.filePath?'required':''} class="form-control" multiple="multiple" type="file" name="_file"/>
                                    </div>
                                </div>

                                <div id="fileButton" style="padding-left: 50px;padding-top: 15px">
                                     <button type="button" onclick="addFile()"
                                                class="addFileBtn btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
                                </div>

                        </c:if>

                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                        <c:if test="${!edit}">
                            ${pmMeeting2.remark}
                        </c:if>
                        <c:if test="${edit}">
                            <textarea class="form-control" type="text" name="remark">${pmMeeting2.remark}</textarea>
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

</style>
<link href="${ctx}/extend/css/bootstrap-tagsinput.css" rel="stylesheet"/>
<script src="${ctx}/extend/js/bootstrap-tagsinput.min.js"></script>

<script>

    var presenterSelect = $('#modalForm select[name="presenter"]');
    var recorderSelect = $('#modalForm select[name="recorder"]');

    $.register.datetime($('.datetime-picker'));
    $.register.user_select(presenterSelect);
    $.register.user_select(recorderSelect);

    var partySelect = $('#modalForm select[name="partyId"]');
    partySelect.on('change',function(){
        var partyId=partySelect.val();
        if ($.isBlank(partyId)){
            $("#modalForm input[name=dueNum]").val('');
            presenterSelect.attr("disabled",true);
            recorderSelect.attr("disabled",true);
            return;
        }
        var data = partySelect.select2("data")[0];
        if(data.class==${cm:getMetaTypeByCode("mt_direct_branch").id}){


            presenterSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&status="+${MEMBER_STATUS_NORMAL});
            recorderSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&status="+${MEMBER_STATUS_NORMAL});

            $.register.user_select(presenterSelect);
            $.register.user_select(recorderSelect);

            presenterSelect.removeAttr("disabled");
            recorderSelect.removeAttr("disabled");

        }

     });

    var branchSelect = $('#modalForm select[name="branchId"]');
    branchSelect.on('change',function(){

        var partyId=partySelect.val();
        var branchId=branchSelect.val();

        if ($.isBlank(branchId)){
            $("#modalForm input[name=dueNum]").val('');
            presenterSelect.attr("disabled",true);
            recorderSelect.attr("disabled",true);
            return;
        }

        presenterSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&branchId="+branchId+"&status="+${MEMBER_STATUS_NORMAL});
        recorderSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&branchId="+branchId+"&status="+${MEMBER_STATUS_NORMAL});

        $.register.user_select(presenterSelect);
        $.register.user_select(recorderSelect);

        presenterSelect.removeAttr("disabled");
        recorderSelect.removeAttr("disabled");

       });

    $("#pmSubmitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var number=new Array();
            var time=new Array();
            $("input[name=type]:checked").each(function (i) {
                var type=$(this).val();

                number[i]= $(".input"+type+":first").val();
                time[i]= $(".input"+type+":last").val();
            })

            $(form).ajaxSubmit({
                data: {number1:number[0],number2:number[1],time1: time[0],time2:time[1]},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success("保存成功。", function () {
                            $.hideView();
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                }
            });
        }
    });
    $.fileInput($('input[type=file]'), {
        no_file: '请上传pdf或图片...',
        allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
         allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });

    var i = 1;
    function addFile() {
        i++;
        var _file = $('<div id="file'+i+'"><input  class="form-control" type="file" name="_file" /></div>');
        $(".files").append(_file);
        var _fileButton = $('<div id="btn'+i+'" style="padding-top: 13px"><button type="button" data-i="'+i+'" onclick="delfileInput(this)"class="addFileBtn btn btn-default btn-xs"><i class="fa fa-trash"></i></button></div>');
        $("#fileButton").append(_fileButton);
        $.fileInput($('input[type=file]', $(_file)), {
            no_file: '请上传pdf或图片...',
            allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
            allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
        });
        return false;
    }

    function delfileInput(btn) {
        var i = $(btn).data("i");
        $("#file"+i).remove();
        $("#btn"+i).remove();
    }

    function delFile(id, indexId,name){
        bootbox.confirm("确定删除'"+name+"'吗？", function (result) {
            if (result) {
                $.post("${ctx}/pmMeeting2_delFile",{id:id,indexId:indexId},function(ret){
                    if(ret.success){
                       /* page_reload();*/
                        $.openView("${ctx}/pmMeeting2_au?edit=${edit}&reedit=${reedit}&id="+id);
                    }
                });
            }
        });
    }

    $("input[type=checkbox]").click(function(){

        var type =$(this).val();
        var branchActivity=${PARTY_MEETING2_BRANCH_ACTIVITY};//主题党日活动
        var branchGroup=${PARTY_MEETING2_BRANCH_GROUP};//党小组会

        $("table[name=typeTable] input[type=text]").prop("disabled" ,true);

        if($(this).val()==branchActivity||$(this).val()==branchGroup) {
            if($("#identity"+branchGroup).prop("checked")){
                $(".input"+branchGroup).attr("disabled" ,false);
                $("table[name=typeTable] input[type=text]").not(".input"+branchGroup).val('');
            }
            if($("#identity"+branchActivity).prop("checked")){
                $(".input"+branchActivity).attr("disabled" ,false);
                $("table[name=typeTable] input[type=text]").not(".input"+branchActivity).val('');
            }
            if(!$("#identity"+branchActivity).prop("checked")&&!$("#identity"+branchGroup).prop("checked")){
                $("table[name=typeTable] input[type=text]").val('');
            }
        }else{
            $("table[name=typeTable] input[type=text]").val('');
        }

        if($(this).prop("checked")){
            //党小组和主题党日可同时选择

            if($(this).val()==branchActivity) {
                $("input[type=checkbox]").not("#identity"+branchGroup).prop("checked", false);
                $(this).prop("checked", true);

            }else if($(this).val()==branchGroup){
                $("input[type=checkbox]").not("#identity"+branchActivity).prop("checked", false);
                $(this).prop("checked", true);

            }else{
                $("input[type=checkbox]").not(this).prop("checked", false);
            }

            $(".input"+type).attr("disabled" ,false);
            $(".input"+type).attr("required","required");

        }
    });
</script>