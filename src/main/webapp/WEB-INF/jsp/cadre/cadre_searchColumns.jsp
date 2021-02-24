<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${empty _pMap['label_staffType']?'个人身份':_pMap['label_staffType']}" var="_p_label_staffType"/>
<div class="column">
    <label>性别</label>
    <div class="input">
        <select name="gender" data-width="100" data-rel="select2"
                data-placeholder="请选择">
            <option></option>
            <option value="<%=SystemConstants.GENDER_MALE%>">男</option>
            <option value="<%=SystemConstants.GENDER_FEMALE%>">女
            </option>
        </select>
        <script>
            $("#searchForm select[name=gender]").val('${param.gender}');
        </script>
    </div>
</div>
<div class="column">
    <label>政治面貌</label>
    <div class="input">
        <select class="multiselect" multiple="" name="dpTypes"
                style="width: 250px;">
            <option value="-1">非中共党员</option>
            <option value="0">中共党员</option>
            <c:import url="/metaTypes?__code=mc_democratic_party"/>
            <shiro:hasRole name="${ROLE_SUPER}">
                <option value="-2">空</option>
            </shiro:hasRole>
        </select>
    </div>
</div>
<c:if test="${fn:length(staffTypes)>0}">
    <div class="column">
        <label>${_p_label_staffType}</label>
        <div class="input">
            <select class="multiselect" multiple="" name="staffTypes">
                <c:forEach items="${staffTypes}" var="staffType">
                    <option value="${staffType}">${staffType}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</c:if>
<div class="column">
    <label>民族</label>
    <div class="input">
        <select class="multiselect" multiple="" name="nation">
            <option value="-1">少数民族</option>
            <c:forEach var="nation" items="${cm:getMetaTypes('mc_nation').values()}">
                <option value="${nation.name}">${nation.name}</option>
            </c:forEach>
        </select>
    </div>
</div>
<div class="column">
    <label>所在单位及职务</label>
    <div class="input">
        <input type="text" name="title"
               value="${param.title}">
    </div>
</div>
<c:if test="${cm:getMetaTypes('mc_cadre_label').size()>0}">
    <div class="column">
        <label>干部标签</label>
        <div class="input">
            <select class="multiselect" multiple="" name="labels"
                    data-placeholder="请选择">
                <c:import url="/metaTypes?__code=mc_cadre_label"/>
            </select>
        </div>
    </div>
</c:if>
<c:if test="${_p_useCadreState}">
    <div class="column">
        <label>${_pMap['cadreStateName']}</label>
        <div class="input">
            <select data-rel="select2" data-width="100" name="state"
                    data-placeholder="请选择">
                <option></option>
                <c:import url="/metaTypes?__code=mc_cadre_state"/>
            </select>
            <script type="text/javascript">
                $("#searchForm select[name=state]").val(${param.state});
            </script>
        </div>
    </div>
</c:if>
<c:if test="${fn:length(authorizedTypes)>0}">
    <div class="column">
        <label>编制类别</label>
        <div class="input">
            <select class="multiselect" multiple="" name="authorizedTypes">
                <c:forEach items="${authorizedTypes}" var="authorizedType">
                    <option value="${authorizedType}">${authorizedType}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</c:if>
<div class="column">
    <label>部门属性</label>
    <div class="input">
        <select class="multiselect" multiple="" name="unitTypes">
            <c:import url="/metaTypes?__code=mc_unit_type"/>
        </select>
    </div>
</div>
<div class="column">
    <label>出生日期</label>
    <div class="input">
        <input placeholder="请选择出生日期范围" data-rel="date-range-picker"
               class="form-control date-range-picker"
               type="text" name="_birth" value="${param._birth}"/>
    </div>
</div>
<div class="column">
    <label>党派加入时间</label>
    <div class="input">
        <input placeholder="请选择党派加入时间范围"
               data-rel="date-range-picker"
               class="form-control date-range-picker"
               type="text" name="_cadreGrowTime"
               value="${param._cadreGrowTime}"/>
    </div>
</div>
<div class="column">
    <label>所在单位</label>
    <div class="input">
        <select class="multiselect" multiple="" name="unitIds">
            <c:forEach var="unitType"
                       items="${cm:getMetaTypes('mc_unit_type')}">
                <optgroup label="${unitType.value.name}">
                    <c:forEach
                            items="${unitListMap.get(unitType.value.id)}"
                            var="unitId">
                        <c:set var="unit"
                               value="${unitMap.get(unitId)}"></c:set>
                        <option value="${unit.id}">${unit.name}</option>
                    </c:forEach>
                </optgroup>
            </c:forEach>
        </select>
    </div>
</div>
<c:if test="${status==CADRE_STATUS_CJ_LEAVE || status==CADRE_STATUS_KJ_LEAVE}">
    <div class="column">
        <label>历史单位</label>
        <div class="input">
            <select class="multiselect" multiple="" name="unitIds">
                <c:forEach var="unitType"
                           items="${cm:getMetaTypes('mc_unit_type')}">
                    <optgroup label="${unitType.value.name}">
                        <c:forEach
                                items="${historyUnitListMap.get(unitType.value.id)}"
                                var="unitId">
                            <c:set var="unit"
                                   value="${unitMap.get(unitId)}"></c:set>
                            <option value="${unit.id}">${unit.name}</option>
                        </c:forEach>
                    </optgroup>
                </c:forEach>
            </select>
        </div>
    </div>
</c:if>
<div class="column">
    <label>年龄</label>
    <div class="input">
        <input class="num" type="text" name="startAge"
               value="${param.startAge}"> 至 <input class="num"
                                                   type="text"
                                                   name="endAge"
                                                   value="${param.endAge}">
    </div>
</div>
<div class="column">
    <label>党龄</label>
    <div class="input">
        <input class="num" type="text" name="startDpAge"
               value="${param.startDpAge}"> 至 <input class="num"
                                                     type="text"
                                                     name="endDpAge"
                                                     value="${param.endDpAge}">
    </div>
</div>
<div class="column">
    <label>行政级别</label>
    <div class="input">
        <select class="multiselect" multiple="" name="adminLevels">
            <c:import url="/metaTypes?__code=mc_admin_level"/>
        </select>
    </div>
</div>
<div class="column">
    <label>最高学历</label>
    <div class="input">
        <select class="multiselect" multiple="" name="maxEdus">
            <c:import url="/metaTypes?__code=mc_edu"/>
            <option value="-1">无(仅查询无最高学历的干部)</option>
        </select>
    </div>
</div>
<div class="column">
    <label>最高学位</label>
    <div class="input">
        <select data-rel="select2" data-placeholder="请选择"
                name="degreeType">
            <option></option>
            <c:forEach items="<%=SystemConstants.DEGREE_TYPE_MAP%>"
                       var="_type">
                <option value="${_type.key}">${_type.value}</option>
            </c:forEach>
            <option value="-1">无</option>
        </select>
        <script>
            $("#searchForm select[name=degreeType]").val('${param.degreeType}');
        </script>
    </div>
</div>
<div class="column">
    <label>所学专业 <span class="prompt" data-title="查询说明"
							  data-prompt="支持多条件查询，分割符为,，;；、|"><i class="fa fa-question-circle-o"></i></span></label>
    <div class="input">
        <input type="text" name="major"
               value="${param.major}">
    </div>
</div>
<div class="column">
    <label>职务属性</label>
    <div class="input">
        <select class="multiselect" multiple="" name="postTypes">
            <c:import url="/metaTypes?__code=mc_post"/>
        </select>
    </div>
</div>
<div class="column">
    <label>现职务始任年限</label>
    <div class="input">
        <input class="num" type="text" name="startNowPostAge"
               value="${param.startNowPostAge}"> 至 <input class="num"
                                                          type="text"
                                                          name="endNowPostAge"
                                                          value="${param.endNowPostAge}">
    </div>
</div>
<div class="column">
    <label>专业技术职务</label>
    <div class="input">
        <select class="multiselect" multiple="" name="proPosts">
            <option value="0">无（没有职称）</option>
            <c:forEach items="${proPosts}" var="proPost">
                <option value="${proPost}">${proPost}</option>
            </c:forEach>
        </select>
    </div>
</div>
<div class="column">
    <label>职称级别</label>
    <div class="input">
        <select class="multiselect" multiple="" name="proPostLevels">
            <c:forEach items="${proPostLevels}" var="proPostLevel">
                <option value="${proPostLevel}">${proPostLevel}</option>
            </c:forEach>
        </select>
    </div>
</div>
<shiro:hasRole name="${ROLE_ADMIN}">
    <div class="column">
        <label>是否在岗 <span class="prompt" data-title="查询说明"
							  data-prompt="“是”：存在第一主职，并且第一主职关联了某个岗位；<br/>“否”：存在第一主职，但没有关联岗位；
<br/>“缺第一主职”：还未录入第一主职；"><i class="fa fa-question-circle-o"></i></span></label>
        <div class="input">
            <select name="firstUnitPost" data-width="130"
                    data-rel="select2" data-placeholder="请选择">
                <option></option>
                <option value="1">是</option>
                <option value="0">否</option>
                <shiro:hasRole name="${ROLE_SUPER}">
                    <option value="-1">缺第一主职</option>
                </shiro:hasRole>
            </select>
            <script>
                $("#searchForm select[name=firstUnitPost]").val('${param.firstUnitPost}');
            </script>
        </div>
    </div>
</shiro:hasRole>
<div class="column">
    <label>是否正职</label>
    <div class="input">
        <select name="isPrincipal" data-width="100"
                data-rel="select2" data-placeholder="请选择">
            <option></option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
        <script>
            $("#searchForm select[name=isPrincipal]").val('${param.isPrincipal}');
        </script>
    </div>
</div>
<div class="column">
    <label>现职级始任年限</label>
    <div class="input">
        <input class="num" type="text" name="startNowLevelAge"
               value="${param.startNowLevelAge}"> 至 <input class="num"
                                                           type="text"
                                                           name="endNowLevelAge"
                                                           value="${param.endNowLevelAge}">
    </div>
</div>
<div class="column">
    <label>是否双肩挑</label>
    <div class="input">
        <select name="isDouble" data-width="100" data-rel="select2"
                data-placeholder="请选择">
            <option></option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
        <script>
            $("#searchForm select[name=isDouble]").val('${param.isDouble}');
        </script>
    </div>
</div>
<div class="column">
    <label class="rows">工作经历类别<br/>
        (<input ${param.andWorkTypes==1?'checked':''} style="vertical-align: -2px" type="checkbox" name="andWorkTypes" value="1">交集
        <input ${param.andWorkTypes!=1?'checked':''} style="vertical-align: -2px" type="checkbox" name="andWorkTypes" value="0">并集)</label>
    <div class="input">
        <select class="multiselect" multiple="" name="workTypes" data-placeholder="请选择">
            <c:import url="/metaTypes?__code=mc_cadre_work_type"/>
        </select>
    </div>
</div>
<div class="column">
    <label>工作经历 <span class="prompt" data-title="查询说明"
							  data-prompt="支持多条件查询，分割符为,，;；、|"><i class="fa fa-question-circle-o"></i></span></label>
    <div class="input">
        <input type="text" name="workDetail"
               value="${param.workDetail}">
    </div>
</div>
<div class="column">
    <label>是否班子负责人</label>
    <div class="input">
        <select class="multiselect" multiple="" name="leaderTypes">
            <c:forEach
                    items="<%=SystemConstants.UNIT_POST_LEADER_TYPE_MAP%>"
                    var="leaderType">
                <option value="${leaderType.key}">${leaderType.value}</option>
            </c:forEach>
        </select>
    </div>
</div>
<div class="column">
    <label>干部类别</label>
    <div class="input">
        <select name="isDep" data-width="150" data-rel="select2"
                data-placeholder="请选择">
            <option></option>
            <option value="1">院系干部</option>
            <option value="0">机关干部</option>
        </select>
        <script>
            $("#searchForm select[name=isDep]").val('${param.isDep}');
        </script>
    </div>
</div>
<div class="column">
    <label>是否有挂职经历</label>
    <div class="input">
        <select name="hasCrp" data-width="100" data-rel="select2"
                data-placeholder="请选择">
            <option></option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
        <script>
            $("#searchForm select[name=hasCrp]").val('${param.hasCrp}');
        </script>
    </div>
</div>
<div class="column">
    <label>国外学习经历</label>
    <div class="input">
        <select name="hasAbroadEdu" data-width="100" data-rel="select2"
                data-placeholder="请选择">
            <option></option>
            <option value="1">有</option>
            <option value="0">无</option>
        </select>
        <script>
            $("#searchForm select[name=hasAbroadEdu]").val('${param.hasAbroadEdu}');
        </script>
    </div>
</div>
<div class="column">
    <label>备注</label>
    <div class="input">
        <input type="text" name="remark"
               value="${param.remark}">
    </div>
</div>
<script>
    $.each(${cm:toJSONObject(param)}, function(name, val){
        if($.trim(val)!=''){
            $("[name='"+name+"']", ".search-form .columns .column .input").closest(".column").find("label").addClass("bolder red");
        }
    })
    $.register.multiselect($('#searchForm select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    $.register.multiselect($('#searchForm select[name=staffTypes]'), ${cm:toJSONArray(selectStaffTypes)});
    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=labels]'), ${cm:toJSONArray(selectLabels)});
    $.register.multiselect($('#searchForm select[name=authorizedTypes]'), ${cm:toJSONArray(selectAuthorizedTypes)});
    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    $.register.multiselect($('#searchForm select[name=unitIds]'), ${cm:toJSONArray(selectUnitIds)}, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
    });
    $.register.multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm select[name=maxEdus]'), ${cm:toJSONArray(selectMaxEdus)});
    $.register.multiselect($('#searchForm select[name=proPosts]'), ${cm:toJSONArray(selectProPosts)});
    $.register.multiselect($('#searchForm select[name=postTypes]'), ${cm:toJSONArray(selectPostTypes)});
    $.register.multiselect($('#searchForm select[name=workTypes]'), ${cm:toJSONArray(selectWorkTypes)});
    $.register.multiselect($('#searchForm select[name=leaderTypes]'), ${cm:toJSONArray(selectLeaderTypes)});
    $.register.multiselect($('#searchForm select[name=proPostLevels]'), ${cm:toJSONArray(selectProPostLevels)});

    $("input[name=andWorkTypes]").click(function () {
        if($(this).is(":checked")){
            $("input[name=andWorkTypes]").not(this).prop("checked", false);
        }else{
            $(this).prop("checked", true);
        }
    });
</script>
