<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="smaller">
            添加干部选拔任用表决情况
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/sc/scCommitteeVote_au" id="voteForm" method="post">
                <div class="row">
                    <div class="col-xs-6">
                        <input type="hidden" name="id" value="${scCommitteeVote.id}">
                        <input type="hidden" name="topicId" value="${scCommitteeTopic.id}">

                        <div class="form-group">
                            <label class="col-xs-3 control-label">类别</label>

                            <div class="col-xs-6">
                                <c:forEach var="DISPATCH_CADRE_TYPE" items="${DISPATCH_CADRE_TYPE_MAP}">
                                    <label class="label-text">
                                        <input required name="type" type="radio" class="ace"
                                               value="${DISPATCH_CADRE_TYPE.key}"
                                               <c:if test="${scCommitteeVote.type==DISPATCH_CADRE_TYPE.key}">checked</c:if>/>
                                        <span class="lbl"> ${DISPATCH_CADRE_TYPE.value}</span>
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">工作证号</label>

                            <div class="col-xs-8">
                                <select required data-ajax-url="${ctx}/cadre_selects?type=0" data-width="220"
                                        name="cadreId" data-placeholder="请选择干部">
                                    <option value="${scCommitteeVote.cadre.id}">${scCommitteeVote.user.code}</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">姓名</label>

                            <div class="col-xs-8">
                                <input disabled class="form-control" type="text" name="_name"
                                       value="${scCommitteeVote.user.realname}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">干部类型</label>

                            <div class="col-xs-6">
                                <select required data-rel="select2" name="cadreTypeId" data-width="220"
                                        data-placeholder="请选择干部类型">
                                    <option></option>
                                    <c:forEach var="cadreType" items="${cadreTypeMap}">
                                        <option value="${cadreType.value.id}">${cadreType.value.name}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#voteForm select[name=cadreTypeId]").val('${scCommitteeVote.cadreTypeId}');
                                </script>
                            </div>
                        </div>
                        <div class="form-group original">
                            <label class="col-xs-3 control-label">原任职务</label>

                            <div class="col-xs-8">
                                <input required class="form-control" type="text" name="originalPost"
                                       value="${scCommitteeVote.originalPost}">
                            </div>
                        </div>
                        <div class="form-group original">
                            <label class="col-xs-4 control-label" style="white-space: nowrap">原任职务任职时间</label>

                            <div class="col-xs-5">
                                <div class="input-group" style="width: 150px;">
                                    <input required class="form-control date-picker" name="originalPostTime"
                                           data-date-format="yyyy-mm-dd"
                                           value="${cm:formatDate(scCommitteeVote.originalPostTime,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">任免方式</label>

                            <div class="col-xs-6">
                                <select data-rel="select2" name="wayId" data-width="220" data-placeholder="请选择任免方式">
                                    <option></option>
                                    <c:forEach var="way" items="${wayMap}">
                                        <option value="${way.value.id}">${way.value.name}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#voteForm select[name=wayId]").val('${scCommitteeVote.wayId}');
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">任免程序</label>

                            <div class="col-xs-6">
                                <select class="form-control" data-rel="select2" data-width="220" name="procedureId"
                                        data-placeholder="请选择任免程序">
                                    <option></option>
                                    <c:forEach var="procedure" items="${procedureMap}">
                                        <option value="${procedure.value.id}">${procedure.value.name}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#voteForm select[name=procedureId]").val('${scCommitteeVote.procedureId}');
                                </script>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">职务</label>

                            <div class="col-xs-8">
                                <input required class="form-control" type="text" name="post"
                                       value="${scCommitteeVote.post}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">备注</label>

                            <div class="col-xs-8">
                                <textarea class="form-control limited" name="remark"
                                          rows="2">${scCommitteeVote.remark}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6">

                        <div class="form-group">
                            <label class="col-xs-3 control-label">职务属性</label>

                            <div class="col-xs-7">
                                <select required name="postId" data-rel="select2" data-placeholder="请选择职务属性">
                                    <option></option>
                                    <c:forEach items="${postMap}" var="post">
                                        <option value="${post.key}">${post.value.name}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#voteForm select[name=postId]").val('${scCommitteeVote.postId}');
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">行政级别</label>

                            <div class="col-xs-7">
                                <select required class="form-control" data-rel="select2" name="adminLevelId"
                                        data-placeholder="请选择行政级别">
                                    <option></option>
                                    <c:forEach var="adminLevel" items="${adminLevelMap}">
                                        <option value="${adminLevel.value.id}">${adminLevel.value.name}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#voteForm select[name=adminLevelId]").val('${scCommitteeVote.adminLevelId}');
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">单位类别</label>

                            <div class="col-xs-7">
                                <select required class="form-control" name="_unitStatus" data-rel="select2"
                                        data-placeholder="请选择单位类别">
                                    <option></option>
                                    <option value="1" ${scCommitteeVote.unit.status==1?"selected":""}>正在运转单位</option>
                                    <option value="2" ${scCommitteeVote.unit.status==2?"selected":""}>历史单位</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">所属单位</label>

                            <div class="col-xs-7">
                                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                        name="unitId" data-placeholder="请选择单位">
                                    <option value="${scCommitteeVote.unit.id}">${scCommitteeVote.unit.name}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">单位类型</label>

                            <div class="col-xs-7">
                                <input class="form-control" name="_unitType" type="text" disabled
                                       value="${scCommitteeVote.unit.unitType.name}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">常委会讨论日期</label>

                            <div class="col-xs-7 label-text">
                                ${cm:formatDate(scCommittee.holdDate, "yyyy-MM-dd")}
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">常委总数</label>

                            <div class="col-xs-7 label-text">
                                ${committeeMemberCount}
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">应参会常委数</label>

                            <div class="col-xs-7 label-text">
                                ${scCommittee.count+scCommittee.absentCount}
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">实际参会常委数</label>

                            <div class="col-xs-7 label-text">
                                ${scCommittee.count}
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">参会同意人数</label>

                            <div class="col-xs-5">
                                <input required class="form-control num" type="text" name="aggreeCount"
                                       value="${scCommitteeVote.aggreeCount}">
                            </div>
                        </div>


                    </div>
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
    <table class="table table-actived table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th style="width: 50px">类别</th>
            <th style="width: 80px">任免方式</th>
            <th style="width: 80px">任免程序</th>
            <th style="width: 80px">姓名</th>
            <th>所属单位</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scCommitteeVotes}" var="scCommitteeVote" varStatus="st">
            <c:set value="${cm:getUserById(cm:getCadreById(scCommitteeVote.cadreId).userId)}" var="user"/>
            <tr>
                <td nowrap>${DISPATCH_CADRE_TYPE_MAP.get(scCommitteeVote.type)}</td>
                <td nowrap>${wayMap.get(scCommitteeVote.wayId).name}</td>
                <td nowrap>${procedureMap.get(scCommitteeVote.procedureId).name}</td>
                <td nowrap>${user.realname}</td>
                <td nowrap>${unitMap.get(scCommitteeVote.unitId).name}</td>
                <td>
                    <a href="javascript:void(0)" onclick="_update(${scCommitteeVote.id})">修改</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    <c:if test="${empty scCommitteeTopic}">
    $("select, input, button, textarea", "#voteForm").prop("disabled", true);
    </c:if>

    $("#voteForm input[name=type]").change(function () {

        if ($(this).val() == 1) {
            $("#voteForm input[name=originalPost]").attr("required", "required");
            $("#voteForm input[name=originalPostTime]").attr("required", "required");
            $("#voteForm .original").show();
        } else {
            $("#voteForm input[name=originalPost]").removeAttr("required");
            $("#voteForm input[name=originalPostTime]").removeAttr("required");
            $("#voteForm .original").hide();
        }
    })

    register_date($('.date-picker'));
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
    function _update(id) {
        $("#dispatch-cadres-view").load("${ctx}/sc/scCommitteeVote_au_form?topicId=${param.topicId}&id=" + $.trim(id));
    }

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    var $selectCadre = register_user_select($('#voteForm select[name=cadreId]'), function (state) {
        var $state = state.text;
        if (state.code != undefined && state.code.length > 0)
            $state = state.code;
        return $state;
    });
    $selectCadre.on("change", function () {
        //console.log($(this).select2("data")[0])
        var name = $(this).select2("data")[0]['text'] || '';
        $('#voteForm input[name=_name]').val(name);
    });
    //register_cadre_select($('#voteForm select[name=cadreId]'), $('#voteForm input[name=_name]'));
    register_unit_select($('#voteForm select[name=_unitStatus]'), $('#voteForm select[name=unitId]'), $('#voteForm input[name=_unitType]'));
</script>