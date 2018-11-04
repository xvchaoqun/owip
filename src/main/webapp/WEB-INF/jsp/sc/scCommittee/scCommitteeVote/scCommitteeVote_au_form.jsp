<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
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
                    <tbody>
                    <tr>
                        <td width="50">类别</td>
                        <td>
                            <c:forEach var="DISPATCH_CADRE_TYPE" items="${DISPATCH_CADRE_TYPE_MAP}">
                                <label class="label-text">
                                    <input required name="type" type="radio" class="ace"
                                           value="${DISPATCH_CADRE_TYPE.key}"
                                           <c:if test="${scCommitteeVote.type==DISPATCH_CADRE_TYPE.key}">checked</c:if>/>
                                    <span class="lbl"> ${DISPATCH_CADRE_TYPE.value}</span>
                                </label>
                            </c:forEach>
                        </td>
                        <td>工作证号</td>
                        <td>
                            <select required data-ajax-url="${ctx}/cadre_selects?type=0&lpWorkTime=1" data-width="160"
                                    name="cadreId" data-placeholder="请选择干部">
                                <option value="${scCommitteeVote.cadre.id}">${scCommitteeVote.user.code}</option>
                            </select>
                        </td>
                        <td width="50">姓名</td>
                        <td><input disabled class="form-control" type="text" name="_name"
                                   value="${scCommitteeVote.user.realname}"></td>
                    </tr>
                    <tr>
                        <td>原任职务</td>
                        <td colspan="2">
                            <textarea class="form-control noEnter" rows="2" style="width: 300px;"
                                      name="originalPost">${scCommitteeVote.originalPost}</textarea>
                            <%--<input class="form-control" type="text" name="originalPost"
                                   style="width: 300px;"
                                   value="${scCommitteeVote.originalPost}">--%>
                        </td>
                        <td >原任职务任职时间</td>
                        <td colspan="2" class="original">
                            <div class="input-group" style="width: 150px;">
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
                        <td>干部类型</td>
                        <td>
                            <select required data-rel="select2" name="cadreTypeId" data-width="180"
                                    data-placeholder="请选择干部类型">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_dispatch_cadre_type"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=cadreTypeId]").val('${scCommitteeVote.cadreTypeId}');
                            </script>
                        </td>
                        <td>任免方式</td>
                        <td>
                            <select data-rel="select2" name="wayId" data-width="140" data-placeholder="请选择任免方式">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_dispatch_cadre_way"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=wayId]").val('${scCommitteeVote.wayId}');
                            </script>
                        </td>
                        <td>任免程序</td>
                        <td>
                            <select class="form-control" data-rel="select2" data-width="140" name="procedureId"
                                    data-placeholder="请选择任免程序">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_dispatch_cadre_procedure"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=procedureId]").val('${scCommitteeVote.procedureId}');
                            </script>
                        </td>

                    </tr>
                    <tr>
                        <td>职务</td>
                        <td>
                             <textarea class="form-control noEnter" rows="2"
                                       name="post">${scCommitteeVote.post}</textarea>
                            <%--<input required class="form-control" type="text" name="post"
                                   value="${scCommitteeVote.post}">--%>
                        </td>
                        <td>职务属性</td>
                        <td>
                            <select required name="postId" data-rel="select2" data-width="140"
                                    data-placeholder="请选择职务属性">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_post"/>
                            </select>
                            <script>
                                $("#voteForm select[name=postId]").val('${scCommitteeVote.postId}');
                            </script>
                        </td>
                        <td>行政级别</td>
                        <td>
                            <select required class="form-control" data-rel="select2" data-width="140"
                                    name="adminLevelId"
                                    data-placeholder="请选择行政级别">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_admin_level"/>
                            </select>
                            <script type="text/javascript">
                                $("#voteForm select[name=adminLevelId]").val('${scCommitteeVote.adminLevelId}');
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td>单位类别</td>
                        <td>
                            <select required class="form-control" name="_unitStatus" data-width="180"
                                    data-rel="select2"
                                    data-placeholder="请选择单位类别">
                                <option></option>
                                <option value="1" ${scCommitteeVote.unit.status==1?"selected":""}>正在运转单位</option>
                                <option value="2" ${scCommitteeVote.unit.status==2?"selected":""}>历史单位</option>
                            </select>
                        </td>
                        <td>所属单位</td>
                        <td>
                            <select required data-rel="select2-ajax" data-width="140"
                                    data-ajax-url="${ctx}/unit_selects"
                                    name="unitId" data-placeholder="请选择单位">
                                <option value="${scCommitteeVote.unit.id}">${scCommitteeVote.unit.name}</option>
                            </select>
                        </td>
                        <td>单位类型</td>
                        <td>
                            <input class="form-control" name="_unitType" type="text" disabled
                                   style="width: 140px"
                                   value="${scCommitteeVote.unit.unitType.name}">
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="row" style="margin: 10px 0;">
                    <table class="table table-striped table-bordered
                    table-condensed table-center table-unhover2">
                        <thead>
                        <tr>
                            <th width="90">党委常委会<br/>讨论日期</th>
                            <th width="50">常委<br/>总数</th>
                            <th width="70">应参会<br/>常委数</th>
                            <th width="80">实际参会<br/>常委数</th>
                            <th width="60">请假<br/>常委数</th>
                            <th width="70">表决<br/>同意票数</th>
                            <th width="70">表决<br/>弃权票数</th>
                            <th width="70">表决<br/>反对票数</th>
                            <th width="60">常委会<br/>表决票</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                ${cm:formatDate(scCommittee.holdDate, "yyyy-MM-dd")}
                            </td>
                            <td>${scCommittee.committeeMemberCount}</td>
                            <td>${scCommittee.count+scCommittee.absentCount}</td>
                            <td>${scCommittee.count}</td>
                            <td>${scCommittee.absentCount}</td>
                            <td><input required class="form-control digits" type="text"
                                       style="width: 100%" name="agreeCount"
                                       data-at="bottom center" data-my="top center"
                                       value="${scCommitteeVote.agreeCount}"></td>
                            <td>
                                <input required class="form-control digits" type="text"
                                       style="width: 100%" name="abstainCount"
                                       data-at="bottom center" data-my="top center"
                                       value="${scCommitteeVote.abstainCount}"></td>
                            </td>
                            <td>
                                <input required class="form-control digits" type="text"
                                       style="width: 100%" name="disagreeCount"
                                       data-at="bottom center" data-my="top center"
                                       value="${scCommitteeVote.disagreeCount}"></td>
                            </td>
                            <td>

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
                <div class="clearfix form-actions center">
                    <button class="btn ${empty scCommitteeVote?'btn-success':'btn-info'} btn-sm" type="submit">
                        <i class="ace-icon fa ${empty scCommitteeVote?"fa-plus":"fa-edit"} "></i>
                        ${empty scCommitteeVote?"添加":"修改"}
                    </button>
                    <c:if test="${not empty scCommitteeVote}">
                        &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-default btn-sm" onclick="_update()">
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
                <td nowrap>${user.realname}</td>
                <td style="text-align: left">${unitMap.get(scCommitteeVote.unitId).name}</td>
                <td>
                    <button type="button" class="btn btn-xs btn-primary"
                            onclick="_update(${scCommitteeVote.id})"><i class="fa fa-edit"></i>
                        修改</button>
                    <button type="button" class="confirm btn btn-xs btn-danger"
                            data-callback="_reload2"
                            data-title="删除"
                            data-msg="确定删除该条记录(${user.realname})"
                            data-url="${ctx}/sc/scCommitteeVote_batchDel?ids[]=${scCommitteeVote.id}">
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

    /*$("#voteForm input[name=type]").change(function () {

     if ($(this).val() == 1) {
     $("#voteForm input[name=originalPost]").attr("required", "required");
     $("#voteForm input[name=originalPostTime]").attr("required", "required");
     $("#voteForm .original").show();
     } else {
     $("#voteForm input[name=originalPost]").removeAttr("required");
     $("#voteForm input[name=originalPostTime]").removeAttr("required");
     $("#voteForm .original").hide();
     }
     })*/

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

    var $selectCadre = $.register.user_select($('#voteForm select[name=cadreId]'), function (state) {
        var $state = state.text;
        if (state.code != undefined && state.code.length > 0)
            $state = state.code;
        return $state;
    });
    $selectCadre.on("change", function () {
        //console.log($(this).select2("data")[0])
        var name = $(this).select2("data")[0]['text'] || '';
        var status = $(this).select2("data")[0]['status'] || '';
        var title = $(this).select2("data")[0]['title'] || '';
        var lpWorkTime = $(this).select2("data")[0]['lpWorkTime'] || '';
        $('#voteForm input[name=_name]').val(name);
        if(status==${CADRE_STATUS_MIDDLE} || status==${CADRE_STATUS_LEADER}){
            $('#voteForm textarea[name=originalPost]').val(title);
            $('#voteForm input[name=originalPostTime]').val(lpWorkTime);
        }else{
            $('#voteForm textarea[name=originalPost]').val('');
            $('#voteForm input[name=originalPostTime]').val('');
        }
    });
    $.register.unit_select($('#voteForm select[name=_unitStatus]'), $('#voteForm select[name=unitId]'), $('#voteForm input[name=_unitType]'));
</script>