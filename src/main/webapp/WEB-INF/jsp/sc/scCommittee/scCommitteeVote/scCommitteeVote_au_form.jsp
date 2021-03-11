<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title">
            添加干部选拔任用表决情况
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/sc/scCommitteeVote_au" id="voteForm" method="post">
                <input type="hidden" name="id" value="${scCommitteeVote.id}">
                <input type="hidden" name="topicId" value="${scCommitteeTopic.id}">

                <table class="table table-striped table-bordered
                    table-condensed table-center table-unhover2" style="border: dashed 1px">
                    <colgroup>
                        <col width="100">
                        <col width="130">
                        <col width="100">
                        <col width="50">
                        <col width="100">
                        <col width="130">
                        <col width="160">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td class="bg-right"><span class="star">*</span>类别</td>
                        <td class="bg-left">
                            <c:forEach var="entity" items="${DISPATCH_CADRE_TYPE_MAP}">
                                <label class="label-text">
                                    <input required name="type" type="radio" class="ace"
                                           value="${entity.key}"
                                           <c:if test="${scCommitteeVote.type==entity.key}">checked</c:if>/>
                                    <span class="lbl"> ${entity.value}</span>
                                </label>
                            </c:forEach>
                        </td>
                        <td class="bg-right"><span class="star">*</span>选择干部</td>
                        <td class="bg-left" colspan="4">
                            <select required data-ajax-url="${ctx}/cadre_selects?type=0&lpWorkTime=1" data-width="360"
                                    name="cadreId" data-placeholder="请输入姓名或工作证号">
                                <option value="${scCommitteeVote.cadre.id}">
                                    ${scCommitteeVote.user.realname}-${scCommitteeVote.user.code}-${scCommitteeVote.cadre.unit.name}</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="7" class="bg-grey" style="text-align: left">
                            <span class="red bolder">干部原任职务信息</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="bg-right">原任职务</td>
                        <td class="bg-left" colspan="4">
                            <textarea class="form-control noEnter" rows="2" style="width: 100%;"
                                      name="originalPost">${scCommitteeVote.originalPost}</textarea>
                            <%--<input class="form-control" type="text" name="originalPost"
                                   value="${scCommitteeVote.originalPost}">--%>
                        </td>
                        <td class="bg-right">原任职务任职时间</td>
                        <td class="bg-left" class="original">
                            <div class="input-group">
                                <input class="form-control date-picker" name="originalPostTime"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(scCommitteeVote.originalPostTime,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <table class="table table-striped table-bordered
                    table-condensed table-center table-unhover2" style="margin: 10px 0 0;">
                    <tbody>
                    <tr>
                        <td rowspan="4" style="width: 30px;" class="red bolder">任免信息</td>
                        <td class="bg-right">所属岗位</td>
                        <td class="bg-left" colspan="5">
                            <select data-ajax-url="${ctx}/unitPost_selects" data-width="590"
                                    name="unitPostId" data-placeholder="请选择">
                                <option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.name}-${unitPost.job}-${unitPost.unitName}</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="bg-right" id="typeNameTd" nowrap><span class="star">*</span>任免职务</td>
                        <td class="bg-left">
                            <textarea required class="form-control noEnter" rows="2" style="width: 150px"
                                       name="post">${scCommitteeVote.post}</textarea>
                        </td>
                        <td class="bg-right" nowrap><span class="star">*</span>职务属性</td>
                        <td class="bg-left">
                            <select required name="postType" data-rel="select2" data-width="130"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_post"/>
                            </select>
                            <script>
                                $("#voteForm select[name=postType]").val('${scCommitteeVote.postType}');
                            </script>
                        </td>
                        <td class="bg-right" nowrap><span class="star">*</span>行政级别</td>
                        <td class="bg-left">
                            <select required class="form-control" data-rel="select2" data-width="140"
                                    name="adminLevel"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_admin_level"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=adminLevel]").val('${scCommitteeVote.adminLevel}');
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="bg-right" nowrap><span class="star">*</span>单位类别</td>
                        <td class="bg-left">
                            <select required class="form-control" name="_unitStatus" data-width="150"
                                    data-rel="select2"
                                    data-placeholder="请选择">
                                <option></option>
                                <option value="1" ${scCommitteeVote.unit.status==1?"selected":""}>正在运转单位</option>
                                <option value="2" ${scCommitteeVote.unit.status==2?"selected":""}>历史单位</option>
                            </select>
                        </td>
                        <td class="bg-right" nowrap><span class="star">*</span>所属单位</td>
                        <td class="bg-left">
                            <select required data-rel="select2-ajax" data-width="130"
                                    data-ajax-url="${ctx}/unit_selects"
                                    name="unitId" data-placeholder="请选择">
                                <option value="${scCommitteeVote.unit.id}">${scCommitteeVote.unit.name}</option>
                            </select>
                        </td>
                        <td class="bg-right">单位类型</td>
                        <td class="bg-left">
                            <input class="form-control" name="_unitType" type="text" disabled
                                   style="width: 140px"
                                   value="${scCommitteeVote.unit.unitType.name}">
                        </td>
                    </tr>
                    <tr>
                        <td class="bg-right" nowrap><span class="star">*</span>干部类型</td>
                        <td class="bg-left">
                            <select required data-rel="select2" name="cadreTypeId" data-width="150"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_dispatch_cadre_type"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=cadreTypeId]").val('${scCommitteeVote.cadreTypeId}');
                            </script>
                        </td>
                        <td class="bg-right">任免方式</td>
                        <td class="bg-left">
                            <select data-rel="select2" name="wayId" data-width="130" data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_dispatch_cadre_way"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=wayId]").val('${scCommitteeVote.wayId}');
                            </script>
                        </td>
                        <td class="bg-right">任免程序</td>
                        <td class="bg-left">
                            <select class="form-control" data-rel="select2" data-width="140" name="procedureId"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_dispatch_cadre_procedure"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=procedureId]").val('${scCommitteeVote.procedureId}');
                            </script>
                        </td>

                    </tr>
                    </tbody>
                </table>
                <div class="row" style="margin: 10px 0 0;">
                    <table class="table table-striped table-bordered
                    table-condensed table-center table-unhover2">
                        <tbody>
                        <tr>
                            <td rowspan="3" style="width: 30px;" class="red bolder">常委会表决情况</td>
                            <td width="90">党委常委会<br/>讨论日期</td>
                            <td width="50">常委<br/>总数</td>
                            <td width="70">应参会<br/>常委数</td>
                            <td width="80">实际参会<br/>常委数</td>
                            <td width="60">请假<br/>常委数</td>
                            <td width="70">表决<br/>同意票数</td>
                            <td width="70">表决<br/>弃权票数</td>
                            <td width="70">表决<br/>反对票数</td>
                            <td width="60">常委会<br/>表决票</td>
                        </tr>
                        <tr>
                            <td class="bg-center">
                                ${cm:formatDate(scCommittee.holdDate, "yyyy-MM-dd")}
                            </td>
                            <td class="bg-center">${scCommittee.committeeMemberCount}</td>
                            <td class="bg-center">${scCommittee.count+scCommittee.absentCount}</td>
                            <td class="bg-center">${scCommittee.count}</td>
                            <td class="bg-center">${scCommittee.absentCount}</td>
                            <td class="bg-center"><input class="form-control digits" type="text"
                                       style="width: 100%" name="agreeCount"
                                       data-at="bottom center" data-my="top center"
                                       value="${scCommitteeVote.agreeCount}"></td>
                            <td class="bg-center">
                                <input class="form-control digits" type="text"
                                       style="width: 100%" name="abstainCount"
                                       data-at="bottom center" data-my="top center"
                                       value="${scCommitteeVote.abstainCount}"></td>
                            </td>
                            <td class="bg-center">
                                <input class="form-control digits" type="text"
                                       style="width: 100%" name="disagreeCount"
                                       data-at="bottom center" data-my="top center"
                                       value="${scCommitteeVote.disagreeCount}"></td>
                            </td>
                            <td class="bg-center">

                                <t:preview filePath="${scCommitteeTopic.voteFilePath}" fileName="表决票" label="<i class='fa fa-search'></i> 预览"/>
                            </td>
                        </tr>
                        <tr>
                            <td>备注</td>
                            <td colspan="9">
                                 <textarea class="form-control limited" name="remark"
                                           rows="2">${scCommitteeVote.remark}</textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <table class="table table-striped table-bordered
                    table-condensed table-center table-unhover2"  style="margin: 10px 0;">
                    <tr>
                        <td class="bg-grey" style="width: 150px">
                            <span class="red bolder">对应的选任纪实</span>
                        </td>
                        <td class="bg-left">
                            <input type="hidden" name="userId" value="${scCommitteeVote.cadre.userId}">
                            <input type="hidden" name="recordId" value="${scCommitteeVote.scRecord.id}">
                            <span id="scRecordCode">${scCommitteeVote.scRecord.code}</span>
                            <button type="button"
                                    onclick="_selectScRecordBtn()"
                                    class="btn btn-primary btn-xs"><i class="fa fa-edit"></i></button>
                        </td>
                    </tr>
                </table>
                <div class="clearfix form-actions center">
                    <button class="btn ${empty scCommitteeVote?'btn-success':'btn-info'} btn-sm" type="submit">
                        <i class="ace-icon fa ${empty scCommitteeVote?"fa-plus":"fa-edit"} "></i>
                        ${empty scCommitteeVote?"添加":"修改"}
                    </button>
                    <c:if test="${not empty scCommitteeVote}">
                        &nbsp; &nbsp; &nbsp;
                        <button type="button" class="btn btn-default btn-sm" onclick="_reload2()">
                            <i class="ace-icon fa fa-undo"></i>
                            返回添加
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>

<div style="padding-top: 20px">
    <table class="table table-actived table-striped table-bordered table-hover table-center">
        <thead>
        <tr>
            <th style="width: 50px">类别</th>
            <th style="width: 80px">任免方式</th>
            <th style="width: 80px">任免程序</th>
            <th style="width: 80px">姓名</th>
            <th>所属单位</th>
            <th width="150"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scCommitteeVotes}" var="scCommitteeVote" varStatus="st">
            <c:set value="${cm:getUserById(cm:getCadreById(scCommitteeVote.cadreId).userId)}" var="user"/>
            <tr>
                <td nowrap>${DISPATCH_CADRE_TYPE_MAP.get(scCommitteeVote.type)}</td>
                <td nowrap>${cm:getMetaType(scCommitteeVote.wayId).name}</td>
                <td nowrap>${cm:getMetaType(scCommitteeVote.procedureId).name}</td>
                <td nowrap class="${empty scCommitteeVote.unitPostId?'warning':''}">${user.realname}</td>
                <td style="text-align: left">${unitMap.get(scCommitteeVote.unitId).name}</td>
                <td>
                    <button type="button" class="btn btn-xs btn-primary"
                            onclick="_update(${scCommitteeVote.id})"><i class="fa fa-edit"></i>
                        修改</button>
                    <button type="button" class="confirm btn btn-xs btn-danger"
                            data-callback="_reload2"
                            data-title="删除"
                            data-msg="确定删除该条记录(${user.realname})"
                            data-url="${ctx}/sc/scCommitteeVote_batchDel?ids=${scCommitteeVote.id}">
                        <i class="fa fa-times"></i>
                        删除</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<style>
    #voteForm .select2-container{
        text-align: left;
    }
</style>

<script>
    <c:if test="${empty scCommitteeTopic}">
    $("select, input, button, textarea", "#voteForm").prop("disabled", true);
    </c:if>

    function _selectScRecordBtn(){

        var userId = $('#voteForm input[name=userId]').val();
        if($.trim(userId)==''){
            SysMsg.info("请选择干部");
            return;
        };
        var recordId = $('#voteForm input[name=recordId]').val();

        $.loadModal("${ctx}/sc/scCommitteeVote_selectScRecord?userId="+userId
            +"&year=${scCommittee.year}&recordId="+recordId, 1050)
    }

    $.register.date($('.date-picker'));
    $('textarea.limited').inputlimiter();
    $("#voteForm button[type=submit]").click(function () {
        $("#voteForm").submit();
        return false;
    });
    $("#voteForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#dispatch-cadres-view").load("${ctx}/sc/scCommitteeVote_au_form?topicId=${param.topicId}");
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    function _reload2(){
        $("#dispatch-cadres-view").load("${ctx}/sc/scCommitteeVote_au_form?topicId=${param.topicId}");
    }
    function _update(id) {
        $("#dispatch-cadres-view").load("${ctx}/sc/scCommitteeVote_au_form?topicId=${param.topicId}&id=" + $.trim(id));
    }

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.del_select($('#voteForm select[name=unitPostId]'), function (state) {
        var $state = state.text;
        if(state.up!=undefined){
            if ($.trim(state.up.job)!='')
                $state += "-" + state.up.job;
            if ($.trim(state.up.unitName)!='')
                $state += "-" + state.up.unitName;
        }
        return $state;
    }).on("change", function () {
        //console.log($(this).select2("data")[0])
        if($(this).select2("data")[0]!=undefined) {
          var up = $(this).select2("data")[0]['up'];
        }
         //console.log(up)
        if(up!=undefined){
            if(up.postClass=='${cm:getMetaTypeByCode("mt_post_dw").id}'){
                $("#voteForm select[name=cadreTypeId]").val('${cm:getMetaTypeByCode("mt_dispatch_cadre_dw").id}').trigger("change")
            }else if(up.postClass=='${cm:getMetaTypeByCode("mt_post_xz").id}'){
                $("#voteForm select[name=cadreTypeId]").val('${cm:getMetaTypeByCode("mt_dispatch_cadre_xz").id}').trigger("change")
            }

            $('#voteForm textarea[name=post]').val(up.name)
            $("#voteForm select[name=postType]").val(up.postType).trigger("change");
            $("#voteForm select[name=adminLevel]").val(up.adminLevel).trigger("change");
            $("#voteForm select[name=_unitStatus]").val(up.unitStatus).trigger("change");

            var option = new Option(up.unitName, up.unitId, true, true);
            $("#voteForm select[name=unitId]").append(option).trigger('change');


            $('#voteForm input[name=_unitType]').val(_cMap.metaTypeMap[up.unitTypeId].name)
        }
    });
    var $selectCadre = $.register.user_select($('#voteForm select[name=cadreId]'), function (state) {
        var $state = state.text;
        if ($.trim(state.code)!='')
            $state += "-" + state.code;
        if ($.trim(state.unit)!='')
            $state += "-" + state.unit;
        return $state;
    });
    $selectCadre.on("change", function () {
        //console.log($(this).select2("data")[0])
        var userId = $(this).select2("data")[0]['userId'] || '';
        var status = $(this).select2("data")[0]['status'] || '';

        $('#voteForm input[name=userId]').val(userId);

        if(status==${CADRE_STATUS_CJ} || status==${CADRE_STATUS_LEADER}){

            var title = '';
            var lpWorkTime = '';
            $.getJSON("${ctx}/sc/scCommitteeTopic_cadre",{topicId:'${scCommitteeTopic.id}',
                cadreId:$(this).val()},function(ret){
                //console.log(ret)
                if(ret.id>0){
                    title = ret.originalPost;
                    lpWorkTime = $.date(ret.originalPostTime, "yyyy-MM-dd");
                }

                if($.trim(title)=='')
                    title = $(this).select2("data")[0]['title'] || '';
                if($.trim(lpWorkTime)=='')
                    lpWorkTime = $(this).select2("data")[0]['lpWorkTime'] || '';

                $('#voteForm textarea[name=originalPost]').val(title);
                $('#voteForm input[name=originalPostTime]').val(lpWorkTime);
            })
        }else{
            $('#voteForm textarea[name=originalPost]').val('');
            $('#voteForm input[name=originalPostTime]').val('');
        }
    });
    $.register.unit_select($('#voteForm select[name=_unitStatus]'), $('#voteForm select[name=unitId]'), $('#voteForm input[name=_unitType]'));
</script>