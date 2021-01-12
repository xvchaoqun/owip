<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp"%>
<c:set var="CET_UPPER_TRAIN_TYPE_OW" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_OW%>"/>
<c:set var="CET_UPPER_TRAIN_TYPE_UNIT" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_UNIT%>"/>
<c:set var="CET_UPPER_TRAIN_TYPE_ABROAD" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD%>"/>
<c:set var="CET_UPPER_TRAIN_TYPE_SCHOOL" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL%>"/>

<c:set var="CET_UPPER_TRAIN_ADD_TYPE_SELF" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_OW" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_UNIT" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT%>"/>
<c:set var="CET_UPPER_TRAIN_STATUS_INIT" value="<%=CetConstants.CET_UPPER_TRAIN_STATUS_INIT%>"/>
<c:set var="CET_UPPER_TRAIN_STATUS_PASS" value="<%=CetConstants.CET_UPPER_TRAIN_STATUS_PASS%>"/>
<c:set var="CET_UPPER_TRAIN_STATUS_UNPASS" value="<%=CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS%>"/>

<c:set var="CET_UPPERTRAIN_AU_TYPE_SINGLE" value="<%=CetConstants.CET_UPPERTRAIN_AU_TYPE_SINGLE%>"/>
<c:set var="CET_UPPERTRAIN_AU_TYPE_BATCH" value="<%=CetConstants.CET_UPPERTRAIN_AU_TYPE_BATCH%>"/>

<c:set var="CET_PROJECT_TYPE_SPECIAL" value="<%=CetConstants.CET_PROJECT_TYPE_SPECIAL%>"/>
<c:set var="CET_PROJECT_TYPE_DAILY" value="<%=CetConstants.CET_PROJECT_TYPE_DAILY%>"/>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        <c:if test="${param.check==1}">审批</c:if>
        <c:if test="${param.check!=1}">
            <c:if test="${cetUpperTrain!=null}">编辑</c:if><c:if test="${cetUpperTrain==null}">添加</c:if>
        </c:if>
    </h3>
</div>
<div class="modal-body overflow-visible">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrain_au" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUpperTrain.id}">
        <input type="hidden" name="addType" value="${addType}">

        <div class="col-xs-12">
            <div class="col-xs-6">
                <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_SELF}">
                    <input type="hidden" name="userId" value="${_user.id}">
                </c:if>
                 <c:if test="${param.addType!=CET_UPPER_TRAIN_ADD_TYPE_SELF}">
                     <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW&&cetUpperTrain==null}">
                         <div class="form-group">
                             <label class="col-xs-4 control-label"><span class="star">*</span> 添加方式</label>
                             <div class="col-xs-8">
                                 <div class="input-group">
                                     <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                         <input required type="radio" name="auType" id="auType0"
                                                value="${CET_UPPERTRAIN_AU_TYPE_SINGLE}">
                                         <label for="auType0">
                                            个别添加
                                         </label>
                                     </div>
                                     <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                         <input required type="radio" name="auType" id="auType1"
                                                value="${CET_UPPERTRAIN_AU_TYPE_BATCH}">
                                         <label for="auType1">
                                             导入名单
                                         </label>
                                     </div>
                                 </div>
                             </div>
                         </div>
                         <div class="form-group" id="owAuType_file">
                             <label class="col-xs-4 control-label"><span class="star">*</span> 参训人员名单</label>
                             <div class="col-xs-7 label-text">
                                 <input class="form-control" type="file" name="xlsx" required extension="xlsx"/>
                                 <span class="help-inline">导入的文件请严格按照
                                <a href="javascript:;" class="downloadBtn" data-type="download"
                                   data-url="${ctx}/attach?code=sample_cetUpperTrain_addType3">
                                    参训人员名单.xlsx</a>（点击下载）的数据格式</span>
                             </div>
                         </div>
                     </c:if>
                <div class="form-group owAuType">
                    <label class="col-xs-4 control-label"><span class="star">*</span> 参训人</label>
                    <div class="col-xs-7 label-text">
                        <select required data-rel="select2-ajax"
                                data-width="252"
                                data-ajax-url="${ctx}/sysUser_selects?types=<%=SystemConstants.USER_TYPE_JZG%>"
                                name="userId" data-placeholder="请选择">
                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                        </select>
                    </div>
                </div>
                 </c:if>
                <div class="form-group owAuType">
                    <label class="col-xs-4 control-label">时任单位及职务</label>
                    <div class="col-xs-7">
                        <textarea class="form-control" name="title">${cetUpperTrain.title}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">时任职务属性</label>
                    <div class="col-xs-7">
                        <select  data-rel="select2" name="postType" data-placeholder="请选择时任职务属性" data-width="252">
                            <option></option>
                            <jsp:include page="/metaTypes?__code=mc_post"/>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=postType]").val(${cetUpperTrain.postType});
                        </script>
                    </div>
                </div>
                <c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
                    <div class="form-group owAuType">
                        <label class="col-xs-4 control-label"> 参训人身份</label>
                        <div class="col-xs-8">
                            <div class="input-group">
                                <c:forEach items="${cm:getMetaTypes('mc_cet_identity')}" var="entity">
                                    <div class="checkbox checkbox-inline checkbox-sm">
                                        <input type="checkbox" name="identities" id="identity${entity.key}" value="${entity.key}">
                                        <label for="identity${entity.key}">${entity.value.name}</label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <script>
                        <c:if test="${not empty cetUpperTrain}">
                            var identity = '${cetUpperTrain.identity}';
                            var identities = identity.split(',');
                            $.each(identities, function (i, item) {
                                $('#modalForm input[name="identities"][value="'+ item +'"]').prop("checked", true);
                            })
                            //console.log(identities);
                        </c:if>
                    </script>
                </c:if>

                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span> 参训人员类型</label>
                    <div class="col-xs-7">
                        <select required data-rel="select2" name="traineeTypeId" data-width="252" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="${traineeTypeMap}" var="entity">
                                <option value="${entity.value.id}">${entity.value.name}</option>
                            </c:forEach>
                            <option value="0">其他</option>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=traineeTypeId]").val(${cetUpperTrain.traineeTypeId});
                        </script>
                    </div>
                </div>

                <div class="form-group hidden" id="otherTraineeType">
                    <div class="col-xs-offset-4 col-xs-6">
                        <input class="form-control" name="otherTraineeType" type="text"
                               value="${cetUpperTrain.otherTraineeType}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训年度</label>
                    <div class="col-xs-7">
                        <div class="input-group" style="width: 100px">
                            <input required class="form-control date-picker"
                                   name="year"
                                   type="text"
                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                   value="${empty cetUpperTrain.year?_thisYear:cetUpperTrain.year}"/>
                            <span class="input-group-addon"> <i
                                    class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_ABROAD}">
                <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_SCHOOL}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训班主办方</label>
                    <div class="col-xs-7">
                        <select required data-rel="select2" name="organizer" data-width="252" data-placeholder="请选择">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_cet_upper_train_organizer"/>
                            <option value="0">其他</option>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=organizer]").val(${cetUpperTrain.organizer});
                        </script>
                    </div>
                </div>
                <div class="form-group" id="otherOrganizerDiv" style="display: none">
                    <div class="col-xs-offset-4 col-xs-6">
                        <input class="form-control" type="text" name="otherOrganizer"
                               value="${cetUpperTrain.otherOrganizer}">
                    </div>
                </div>
                </c:if>
                 <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_SCHOOL}">
                    <div class="form-group">
                     <label class="col-xs-4 control-label"><span class="star">*</span>培训类别</label>
                     <div class="col-xs-8">
                         <div class="input-group">
                             <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                 <input type="radio" name="specialType" id="specialType0"
                                        value="${CET_PROJECT_TYPE_SPECIAL}">
                                 <label for="specialType0">
                                     专题培训
                                 </label>
                             </div>
                             <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                 <input type="radio" name="specialType" id="specialType1"
                                        value="${CET_PROJECT_TYPE_DAILY}">
                                 <label for="specialType1">
                                     日常培训
                                 </label>
                             </div>
                         </div>
                     </div>
                 </div>

                     <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训班类型</label>
                    <div class="col-xs-7">
                        <select required name="projectTypeId" data-rel="select2"
                                            data-width="223"
                                            data-placeholder="请选择">
                            <option></option>
                        </select>
                        <script>
                            var projectTypes = [];
                            $("#modalForm input[name=specialType]").change(function(){
                                //console.log("---------" + $(this).val())
                                var $projectTypeId = $("#modalForm select[name=projectTypeId]")
                                            .empty().prepend("<option></option>").select2();

                                if($(this).val()!='${CET_PROJECT_TYPE_SPECIAL}'){
                                    projectTypes = ${cm:toJSONArray(dailyProjectTypes)}
                                }else{
                                    projectTypes = ${cm:toJSONArray(specialProjectTypes)}
                                }
                                //console.dir(projectTypes)
                                $.each(projectTypes, function (i, projectType) {
                                    var selected = (projectType.id == '${cetUpperTrain.projectTypeId}');
                                    $projectTypeId.append(new Option(projectType.name, projectType.id, selected, selected))
                                })
                                $projectTypeId.trigger('change');
                            })
                            $("#modalForm input[name=specialType][value='${empty cetUpperTrain.specialType?CET_PROJECT_TYPE_SPECIAL:cetUpperTrain.specialType}']")
                                .click();
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">培训内容分类</label>
                    <div class="col-xs-8">
                        <div class="input-group">
                            <select class="multiselect" multiple="" name="category" data-width="223" data-placeholder="请选择">
                                <c:import url="/metaTypes?__code=mc_cet_project_category"/>
                            </select>
                            <script type="text/javascript">
                                $.register.multiselect($('#modalForm select[name=category]'), '${cetUpperTrain.category}'.split(","));
                            </script>
                        </div>
                    </div>
                </div>
                </c:if>
                <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_SCHOOL}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训班类型</label>
                    <div class="col-xs-7">
                        <select required data-rel="select2" name="trainType" data-width="252" data-placeholder="请选择">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_cet_upper_train_type"/>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=trainType]").val(${cetUpperTrain.trainType});
                        </script>
                    </div>
                </div>
                </c:if>
                </c:if>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span> 培训班名称</label>
                    <div class="col-xs-7">
                                <textarea required class="form-control noEnter" rows="2"
                                          name="trainName">${cetUpperTrain.trainName}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训形式</label>
                    <div class="col-xs-7">
                        <div class="input-group">
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="isOnline" id="isOnline0"
                                       ${(empty cetUpperTrain || !cetUpperTrain.isOnline)?"checked":""} value="0">
                                <label for="isOnline0">
                                    线下培训
                                </label>
                            </div>
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="isOnline" id="isOnline1"
                                       ${cetUpperTrain.isOnline?"checked":""} value="1">
                                <label for="isOnline1">
                                    线上培训
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">

                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训时间</label>
                    <div class="col-xs-7">
                        <div class="input-group">
                            <input required class="form-control date-picker" name="startDate"
                                   type="text" autocomplete="off" disableautocomplete
                                   data-date-format="yyyy.mm.dd" style="width: 90px;float: left"
                                   value="${cm:formatDate(cetUpperTrain.startDate,'yyyy.MM.dd')}"/>
                            <div style="float: left;margin: 5px 5px 0 5px;"> 至 </div>
                            <input required class="form-control date-picker" name="endDate"
                                   type="text" autocomplete="off" disableautocomplete
                                   data-date-format="yyyy.mm.dd" style="width: 90px"
                                   value="${cm:formatDate(cetUpperTrain.endDate,'yyyy.MM.dd')}"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训学时</label>
                    <div class="col-xs-7">
                        <input required class="form-control period" type="text" name="period" style="width: 90px"
                               value="${cetUpperTrain.period}">
                    </div>
                </div>
                <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_ABROAD}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>前往国家</label>
                    <div class="col-xs-7">
                        <input required class="form-control" type="text" name="country"
                               value="${cetUpperTrain.country}">
                    </div>
                </div>
                    </c:if>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训地点</label>
                    <div class="col-xs-7">
                        <textarea required class="form-control" rows="2"
                                          name="address">${cetUpperTrain.address}</textarea>
                    </div>
                </div>
                <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_ABROAD}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>组织培训机构</label>
                    <div class="col-xs-7">
                        <input required class="form-control" type="text" name="agency"
                               value="${cetUpperTrain.agency}">
                    </div>
                </div>
                </c:if>
                <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_SELF}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label">培训总结</label>
                        <div class="col-xs-7">
                            <input class="form-control" type="file" name="_word"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-4 col-xs-7">
                            <input class="form-control" type="file" name="_pdf"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_ABROAD}">
                    <input type="hidden" name="type" value="${CET_UPPER_TRAIN_TYPE_ABROAD}">
                </c:if>
                <c:if test="${param.type==CET_UPPER_TRAIN_TYPE_SCHOOL}">
                    <input type="hidden" name="type" value="${CET_UPPER_TRAIN_TYPE_SCHOOL}">
                </c:if>
                <div class="form-group">
                    <label class="col-xs-4 control-label"> 培训成绩</label>
                    <div class="col-xs-7">
                        <input class="form-control" type="text" name="score"
                               value="${cetUpperTrain.score}" maxlength="20">
                    </div>
                </div>
                <c:if test="${_p_cetSupportCert}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>是否结业</label>
                    <div class="col-xs-7">
                        <div class="input-group">
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="isGraduate" id="isGraduate1"
                                       ${(empty cetUpperTrain || cetUpperTrain.isGraduate)?"checked":""} value="1">
                                <label for="isGraduate1">
                                    是
                                </label>
                            </div>
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="isGraduate" id="isGraduate0"
                                       ${not empty cetUpperTrain && !cetUpperTrain.isGraduate?"checked":""} value="0">
                                <label for="isGraduate0">
                                    否
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                </c:if>
                <c:if test="${param.type!=CET_UPPER_TRAIN_TYPE_SCHOOL}">
                <c:if test="${param.addType!=CET_UPPER_TRAIN_ADD_TYPE_UNIT}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>派出单位</label>
                        <div class="col-xs-8">
                            <div class="input-group">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type" id="type0" ${(empty cetUpperTrain || cetUpperTrain.type == CET_UPPER_TRAIN_TYPE_OW) ? "checked" : ""}
                                           value="${CET_UPPER_TRAIN_TYPE_OW}">
                                    <label for="type0">
                                        党委组织部
                                    </label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type" id="type1" ${(cetUpperTrain.type == CET_UPPER_TRAIN_TYPE_UNIT || cetUpperTrain.type == CET_UPPER_TRAIN_TYPE_ABROAD) ? "checked" : ""}
                                           value="${CET_UPPER_TRAIN_TYPE_UNIT}">
                                    <label for="type1">
                                        其他部门派出
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" id="unitDiv"
                         style="display: ${cetUpperTrain.type==CET_UPPER_TRAIN_TYPE_UNIT?'block':'none'}">
                        <div class="col-xs-offset-4 col-xs-7">
                            <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_SELF}">
                                <c:set var="unitSelectsUrl" value="${ctx}/unit_selects?status=${UNIT_STATUS_RUN}"/>
                            </c:if>
                            <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                                <c:set var="unitSelectsUrl" value="${ctx}/unit_selects"/>
                            </c:if>
                            <select name="unitId"
                                    ${cetUpperTrain.type==CET_UPPER_TRAIN_TYPE_UNIT?'required':''}
                                    data-rel="select2-ajax" data-width="252" data-ajax-url="${unitSelectsUrl}"
                                    data-placeholder="请选择单位">
                                <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                            </select>
                            <script>
                                $.register.del_select($("#modalForm select[name=unitId]"))
                            </script>
                        </div>
                    </div>
                </c:if>
                <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_UNIT}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>派出单位</label>
                        <div class="col-xs-8">
                            <input type="hidden" name="type" value="${CET_UPPER_TRAIN_TYPE_UNIT}">

                            <select required data-rel="select2" name="unitId"
                                    data-placeholder="请选择派出单位">
                                <option></option>
                                <c:forEach var="unit" items="${upperUnits}">
                                    <option value="${unit.id}"
                                            delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                </c:forEach>
                            </select>
                            <script>
                                $("#modalForm select[name=unitId]").val('${cetUpperTrain.unitId}')
                            </script>
                        </div>
                    </div>
                </c:if>
                </c:if>
                <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
                    <div class="form-group red bolder">
                        <label class="col-xs-4 control-label"
                               style="margin-top: -10px;"><span class="star">*</span>是否计入<br/>年度学习任务</label>
                        <div class="col-xs-7">
                            <div class="input-group">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="isValid" id="isValid1"
                                           ${cetUpperTrain.isValid?"checked":""} value="1">
                                    <label for="isValid1">
                                        计入
                                    </label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="isValid" id="isValid0"
                                           ${((param.check==1&& cetUpperTrain.status==CET_UPPER_TRAIN_STATUS_INIT)||cetUpperTrain.isValid!=null) && !cetUpperTrain.isValid?"checked":""} value="0">
                                    <label for="isValid0">
                                        不计入
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="col-xs-4 control-label">备注</label>
                    <div class="col-xs-7">
					 <textarea class="form-control limited" rows="2"
                               name="remark">${cetUpperTrain.remark}</textarea>
                    </div>
                </div>
                <c:if test="${param.check==1}">
                    <input type="hidden" name="check" value="1">
                </c:if>
                <c:if test="${param.check==1 && cetUpperTrain.status==CET_UPPER_TRAIN_STATUS_INIT}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>审批结果</label>
                        <div class="col-xs-7">
                            <div class="input-group">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="status" id="status1"
                                           value="${CET_UPPER_TRAIN_STATUS_PASS}">
                                    <label for="status1">
                                        通过
                                    </label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="status" id="status2"
                                           value="${CET_UPPER_TRAIN_STATUS_UNPASS}">
                                    <label for="status2">
                                        不通过
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" id="backReasonDiv" style="display: none">
                        <label class="col-xs-4 control-label"><span class="star">*</span>不通过原因</label>
                        <div class="col-xs-7">
                            <textarea class="form-control limited" rows="2" name="backReason"></textarea>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer" style="clear: both">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <i class="fa fa-check"></i>
        <c:if test="${cetUpperTrain!=null}">${cetUpperTrain.status==CET_UPPER_TRAIN_STATUS_UNPASS?'重新提交':'确定'}</c:if>
        <c:if test="${cetUpperTrain==null}">添加</c:if></button>
</div>
<style>
    .modal-body {
        max-height: inherit !important;
    }
</style>
<script>
    function traineeTypeChange(){
        if ($("select[name=traineeTypeId]").val() == "0"){
            $("#otherTraineeType").removeClass("hidden");
            $("input[name=otherTraineeType]", "#otherTraineeType").prop("disabled", false).attr("required", "required");
        }else {
            $("#otherTraineeType").addClass("hidden");
            $("input[name=otherTraineeType]", "#otherTraineeType").val('').prop("disabled", true).removeAttr("required");
        }
    }
    $("select[name=traineeTypeId]").on("change", function () {
        traineeTypeChange();
    })
    traineeTypeChange();

    <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_OW&&cetUpperTrain==null}">
        owAuType_hide();
        $("input[name=auType]", "#modalForm").on("click", function () {
            owAuType_show($(this).val());
            //console.log($("#auType").val());
        })
        $("input[name=auType][value='${CET_UPPERTRAIN_AU_TYPE_SINGLE}']", "#modalForm").click();
    </c:if>

    function owAuType_hide() {
        $(".owAuType").addClass("hidden");
        $(".owAuType select[name=userId]").removeAttr("required");
        $("#owAuType_file").addClass("hidden");
        $("#owAuType_file input[name=xlsx]").removeAttr("required");
    }
    function owAuType_show(auType) {
        //console.log(auType)
        if (auType == ${CET_UPPERTRAIN_AU_TYPE_SINGLE}) {
            $(".owAuType").removeClass("hidden");
            $(".owAuType select[name=userId]").attr("required", "required");
            $("#owAuType_file").addClass("hidden");
            $("#owAuType_file input[name=xlsx]").removeAttr("required");
        }else if (auType == ${CET_UPPERTRAIN_AU_TYPE_BATCH}) {
            $(".owAuType").addClass("hidden");
            $(".owAuType select[name=userId]").removeAttr("required");
            $("#owAuType_file").removeClass("hidden");
            $("#owAuType_file input[name=xlsx]").attr("required", "required");
            $.fileInput($("#owAuType_file input[name=xlsx]"), {
                no_file: '请上传xlsx文件...',
                allowExt: ['xlsx']
            });
        }else {
            owAuType_hide();
        }
    }

    <c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_SELF}">
    $.fileInput($("#modalForm input[name=_word]"), {
        no_file: '请上传word文件...',
        allowExt: ['doc', 'docx'],
        allowMime: ['application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
    $.fileInput($("#modalForm input[name=_pdf]"), {
        no_file: '请上传pdf文件...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    </c:if>
    <c:if test="${param.addType!=CET_UPPER_TRAIN_ADD_TYPE_UNIT}">
    if ($("#type1").attr("checked")){
        $("#unitDiv").show();
        $("#modalForm select[name=unitId]").prop("disabled", false).attr("required", "required");
    }else {
        $("#unitDiv").hide();
        $("#modalForm select[name=unitId]").val(null).trigger("change").prop("disabled", true).removeAttr("required");
    }
    </c:if>
    $("#modalForm input[name=type]").click(function () {
        if ($(this).val() == ${CET_UPPER_TRAIN_TYPE_UNIT}) {
            $("#unitDiv").show();
            $("#modalForm select[name=unitId]").prop("disabled", false).attr("required", "required");
        } else {
            $("#unitDiv").hide();
            $("#modalForm select[name=unitId]").val(null).trigger("change").prop("disabled", true).removeAttr("required");
        }
    });
    <c:if test="${not empty cetUpperTrain.id}">
    $("#modalForm input[name=type][value=${cetUpperTrain.type}]").prop("checked", true);
    </c:if>
    <c:if test="${empty cetUpperTrain.id && not empty param.type}">
    $("#modalForm input[name=type][value=${param.type}]").click();
    </c:if>
    $("#modalForm input[name=status]").click(function () {

        if ($(this).val() ==${CET_UPPER_TRAIN_STATUS_UNPASS}) {
            $("#backReasonDiv").show();
            $("#modalForm textarea[name=backReason]").prop("disabled", false).attr("required", "required");
        } else {
            $("#backReasonDiv").hide();
            $("#modalForm textarea[name=backReason]").val('').prop("disabled", true).removeAttr("required");
        }
    });

    function organizerChange() {
        var $organizer = $("#modalForm select[name=organizer]");
        if ($organizer.val() == '0') { // 其他主办方
            $("#otherOrganizerDiv").show();
            $("#modalForm input[name=otherOrganizer]").prop("disabled", false).attr("required", "required");
        } else {
            $("#otherOrganizerDiv").hide();
            $("#modalForm input[name=otherOrganizer]").val('').prop("disabled", true).removeAttr("required");
        }
    }

    $("#modalForm select[name=organizer]").change(function () {
        organizerChange();
    });
    organizerChange();

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({

        submitHandler: function (form) {

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        <c:choose>
                        <c:when test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_SELF
                        && (empty cetUpperTrain.id||cetUpperTrain.status==CET_UPPER_TRAIN_STATUS_UNPASS)}">
                        $.loadPage({url: '${ctx}/user/cet/cetUpperTrain?type=${param.type}&cls=2'})
                        </c:when>
                        <c:otherwise>
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                        </c:otherwise>
                        </c:choose>
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $.register.user_select($('#modalForm select[name=userId]'));
    $('#modalForm [data-rel="select2"]').customSelect2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>