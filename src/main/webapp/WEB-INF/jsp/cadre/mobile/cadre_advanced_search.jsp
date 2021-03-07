<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${empty _pMap['label_staffType']?'个人身份':_pMap['label_staffType']}" var="_p_label_staffType"/>
<div class="back-btn">
    <a href="javascript:;" class="hideView"><i class="fa fa-reply"></i> 返回</a>
</div>
<div class="center">
    <form id="searchForm">
        <input type="hidden" name="status" value="${param.status}" />
        <div class="select">
            <select data-rel="select2-aj1ax"
                    data-ajax-url="${ctx}/m/cadre_selects?types=${CADRE_STATUS_CJ},${CADRE_STATUS_CJ_LEAVE},${CADRE_STATUS_KJ},${CADRE_STATUS_KJ_LEAVE}"
                    data-width="300"
                    name="cadreId" data-placeholder="账号或姓名或学工号">
                <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
            </select>
        </div>
        <div class="select">
            <select name="gender" data-width="300" data-rel="select2" data-placeholder="请选择性别">
                <option></option>
                <option value="<%=SystemConstants.GENDER_MALE%>">男</option>
                <option value="<%=SystemConstants.GENDER_FEMALE%>">女
                </option>
            </select>
            <script>
                $("#searchForm select[name=gender]").val('${param.gender}');
            </script>
        </div>
        <div class="select">
            <select name="dpTypes" id="multiselect" multiple="" class="multiselect" data-placeholder="请选择政治面貌">
                <option value="-1">非中共党员</option>
                <option value="0">中共党员</option>
                <c:import url="/metaTypes?__code=mc_democratic_party"/>
                <shiro:hasRole name="${ROLE_SUPER}">
                    <option value="-2">空</option>
                </shiro:hasRole>
            </select>
        </div>

        <c:if test="${fn:length(staffTypes)>0}">
            <div class="select">
                <select class="multiselect" multiple="" name="staffTypes" data-placeholder="请选择${_p_label_staffType}">
                    <c:forEach items="${staffTypes}" var="staffType">
                        <option value="${staffType}">${staffType}</option>
                    </c:forEach>
                </select>
            </div>
        </c:if>

        <div class="select">
            <select class="multiselect" multiple="" name="nation" data-placeholder="请选择民族">
                <option value="-1">少数民族</option>
                <c:forEach var="nation" items="${cm:getMetaTypes('mc_nation').values()}">
                    <option value="${nation.name}">${nation.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="select">
            <input style="width: 300px" type="text" name="title" placeholder="所在单位及职务" value="${param.title}">
        </div>

        <c:if test="${cm:getMetaTypes('mc_cadre_label').size()>0}">
            <div class="select">
                <select class="multiselect" multiple="" name="labels"
                        data-placeholder="请选择干部标签">
                    <c:import url="/metaTypes?__code=mc_cadre_label"/>
                </select>
            </div>
        </c:if>

        <c:if test="${_p_useCadreState}">
            <div class="select">
                <select data-rel="select2" data-width="300" name="state"
                        data-placeholder="请选择${_pMap['cadreStateName']}">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_cadre_state"/>
                </select>
                <script type="text/javascript">
                    $("#searchForm select[name=state]").val(${param.state});
                </script>
            </div>
        </c:if>

        <c:if test="${fn:length(authorizedTypes)>0}">
            <div class="select">
                <select class="multiselect" multiple="" name="authorizedTypes" data-placeholder="请选择编制类别">
                    <c:forEach items="${authorizedTypes}" var="authorizedType">
                        <option value="${authorizedType}">${authorizedType}</option>
                    </c:forEach>
                </select>
            </div>
        </c:if>

        <div class="select">
            <select class="multiselect" multiple="" name="unitTypes" data-placeholder="请选择部门属性">
                <c:import url="/metaTypes?__code=mc_unit_type"/>
            </select>
        </div>

        <label>请选择出生日期范围</label>
        <div class="select">
            <input style="width: 140px" type="date" name="startBirth" value="${param.startBirth}"/> 至
            <input style="width: 140px" type="date" name="endBirth" value="${param.endBirth}"/>
        </div>

        <label>请选择党派加入时间范围</label>
        <div class="select">
            <input style="width: 140px;" type="date" name="startCadreGrowTime" value="${param.startCadreGrowTime}"/> 至
            <input style="width: 140px;" type="date" name="endCadreGrowTime" value="${param.endCadreGrowTime}"/>
        </div>

        <div class="select">
            <select class="multiselect" multiple="" name="unitIds" data-placeholder="请选择所在单位">
                <c:forEach var="unitType" items="${cm:getMetaTypes('mc_unit_type')}">
                    <optgroup label="${unitType.value.name}">
                        <c:forEach items="${unitListMap.get(unitType.value.id)}" var="unitId">
                            <c:set var="unit" value="${unitMap.get(unitId)}"></c:set>
                            <option value="${unit.id}">${unit.name}</option>
                        </c:forEach>
                    </optgroup>
                </c:forEach>
            </select>
        </div>

        <div class="select">
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="startAge" placeholder="最小年龄" value="${param.startAge}"> 至
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="endAge" placeholder="最大年龄" value="${param.endAge}">
        </div>

        <div class="select">
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="startDpAge" placeholder="最小党龄" value="${param.startDpAge}"> 至
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="endDpAge" placeholder="最大党龄" value="${param.endDpAge}">
        </div>

        <div class="select">
            <select class="multiselect" multiple="" name="adminLevels" data-placeholder="请选择行政级别">
                <c:import url="/metaTypes?__code=mc_admin_level"/>
            </select>
        </div>

        <div class="select">
            <select class="multiselect" multiple="" name="maxEdus" data-placeholder="请选择最高学历">
                <c:import url="/metaTypes?__code=mc_edu"/>
                <option value="-1">无(仅查询无最高学历的干部)</option>
            </select>
        </div>

        <div class="select">
            <select data-width="300" data-rel="select2" data-placeholder="请选择最高学位" name="degreeType">
                <option></option>
                <c:forEach items="<%=SystemConstants.DEGREE_TYPE_MAP%>" var="_type">
                    <option value="${_type.key}">${_type.value}</option>
                </c:forEach>
                <option value="-1">无</option>
            </select>
            <script type="text/javascript">
                $("#searchForm select[name=degreeType]").val(${param.degreeType});
            </script>
        </div>

        <div class="select">
            <input style="width: 300px;" type="text" name="major" value="${param.major}" placeholder="所学专业">
        </div>

        <div class="select">
            <select data-width="300" class="multiselect" multiple="" name="postTypes" data-placeholder="请选择职务属性">
                <c:import url="/metaTypes?__code=mc_post"/>
            </select>
        </div>

        <div class="select">
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="startNowPostAge" value="${param.startNowPostAge}" placeholder="现职务始任年限"> 至
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="endNowPostAge" value="${param.endNowPostAge}"placeholder="现职务始任年限">
        </div>

        <div class="select">
            <select class="multiselect" multiple="" name="proPosts"
                    data-width="300" data-placeholder="请选择专业技术职务">
                <option value="0">无（没有职称）</option>
                <c:forEach items="${proPosts}" var="proPost">
                    <option value="${proPost}">${proPost}</option>
                </c:forEach>
            </select>
        </div>

        <div class="select">
            <select class="multiselect" multiple="" name="proPostLevels"
                    data-width="300" data-placeholder="请选择职称级别">
                <c:forEach items="${proPostLevels}" var="proPostLevel">
                    <option value="${proPostLevel}">${proPostLevel}</option>
                </c:forEach>
            </select>
        </div>

        <shiro:hasRole name="${ROLE_ADMIN}">
            <div class="select">
                <select name="firstUnitPost" data-width="300"
                        data-rel="select2" data-placeholder="第一主职是否已关联岗位">
                    <option></option>
                    <option value="1">是</option>
                    <option value="0">否</option>
                    <shiro:hasRole name="${ROLE_SUPER}">
                        <option value="-1">缺第一主职</option>
                    </shiro:hasRole>
                </select>
                <script type="text/javascript">
                    $("#searchForm select[name=firstUnitPost]").val(${param.firstUnitPost});
                </script>
            </div>
        </shiro:hasRole>

        <div class="select">
            <select name="isPrincipal" data-width="300"
                    data-rel="select2" data-placeholder="是否正职">
                <option></option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
            <script type="text/javascript">
                $("#searchForm select[name=isPrincipal]").val(${param.isPrincipal});
            </script>
        </div>

        <div class="select">
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="startNowLevelAge" placeholder="现职级始任年限" value="${param.startNowLevelAge}"> 至
            <input style="width: 140px;" type="tel" oninput="value=value.replace(/[^\d]/g,'')"
                   name="endNowLevelAge" placeholder="现职级始任年限" value="${param.endNowLevelAge}">
        </div>

        <div class="select">
            <select name="isDouble" data-width="300" data-rel="select2"
                    data-placeholder="是否双肩挑">
                <option></option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
            <script type="text/javascript">
                $("#searchForm select[name=isDouble]").val(${param.isDouble});
            </script>
        </div>

        <label>
            <input ${param.andWorkTypes==1?'checked':''} type="radio" name="andWorkTypes" value="1">交集
            <input ${param.andWorkTypes!=1?'checked':''} type="radio" name="andWorkTypes" value="0">并集
        </label>
        <div class="select">
            <select data-width="300" class="multiselect" multiple="" name="workTypes" data-placeholder="请选择工作经历">
                <c:import url="/metaTypes?__code=mc_cadre_work_type"/>
            </select>
        </div>

        <div class="select">
            <select data-width="300" class="multiselect" multiple="" name="leaderTypes" data-placeholder="是否班子负责人">
                <c:forEach items="<%=SystemConstants.UNIT_POST_LEADER_TYPE_MAP%>" var="leaderType">
                    <option value="${leaderType.key}">${leaderType.value}</option>
                </c:forEach>
            </select>
        </div>

        <div class="select">
             <select data-width="300" class="select2" name="type" data-placeholder="请选择干部类别">
                <c:import url="/metaTypes?__code=mc_cadre_type"/>
            </select>
            <script type="text/javascript">
                $("#searchForm select[name=type]").val('${param.type}');
            </script>
        </div>

        <div class="select">
            <select name="hasCrp" data-width="300" data-rel="select2"
                    data-placeholder="是否有挂职经历">
                <option></option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
            <script type="text/javascript">
                $("#searchForm select[name=hasCrp]").val(${param.hasCrp});
            </script>
        </div>

        <div class="select">
            <select name="hasAbroadEdu" data-width="300" data-rel="select2"
                    data-placeholder="有无国外学习经历">
                <option></option>
                <option value="1">有</option>
                <option value="0">无</option>
            </select>
            <script type="text/javascript">
                $("#searchForm select[name=hasAbroadEdu]").val(${param.hasAbroadEdu});
            </script>
        </div>
    </form>
    <button id="compare" class="btn btn-success btn-sm">
        <i class="ace-icon fa fa-search"></i> 查询
    </button>
    <button class="openView btn btn-warning btn-sm"
            data-url="${ctx}/m/cadre_advanced_search?status=${param.status}" data-open-by="page">
        <i class="ace-icon fa fa-reply"></i> 重置
    </button>
</div>

<style>
    .select {
        padding-bottom: 20px;
    }

    .multiselect-container .checkbox input[type="checkbox"] {
        opacity: 1;
    }

    .btn.multiselect-clear-filter {
        height: 34px;
    }
</style>
<script>
    $.register.user_select($('select[name=cadreId]'));
    $('#searchForm [data-rel="select2"]').select2();

    multiselect($('#searchForm select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNation)});
    multiselect($('#searchForm select[name=staffTypes]'), ${cm:toJSONArray(selectStaffTypes)});
    multiselect($('#searchForm select[name=labels]'), ${cm:toJSONArray(selectLabels)});
    multiselect($('#searchForm select[name=authorizedTypes]'), ${cm:toJSONArray(selectAuthorizedTypes)});
    multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    multiselect($('#searchForm select[name=unitIds]'), ${cm:toJSONArray(selectUnitIds)}, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true,
        collapsed: true,
        selectAllJustVisible: false
    });
    multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    multiselect($('#searchForm select[name=maxEdus]'), ${cm:toJSONArray(selectMaxEdus)});
    multiselect($('#searchForm select[name=postTypes]'), ${cm:toJSONArray(selectPostTypes)});
    multiselect($('#searchForm select[name=proPosts]'), ${cm:toJSONArray(selectProPosts)});
    multiselect($('#searchForm select[name=proPostLevels]'), ${cm:toJSONArray(selectProPostLevels)});
    multiselect($('#searchForm select[name=workTypes]'), ${cm:toJSONArray(selectWorkTypes)});
    multiselect($('#searchForm select[name=leaderTypes]'), ${cm:toJSONArray(selectLeaderTypes)});



    function multiselect($select, selected, params) {
        var $select = $select.multiselect($.extend({
            enableFiltering: true,
            buttonClass: 'btn btn-primary',
            filterPlaceholder: '查找',
            nonSelectedText: '请选择',
            nSelectedText: '已选择',
            includeSelectAllOption: true,
            collapseOptGroupsByDefault: true,
            selectAllText: '全选/取消全选',
            allSelectedText: '全部已选择',
            maxHeight: 300,
            buttonWidth:300,}, params));

        if (selected != undefined && selected instanceof Array && selected.length > 0) {
            $select.multiselect('select', selected);
        }
        return $select;
    }

    $("#compare").click(function () {
        $.openView("${ctx}/m/cadre_advanced_search_result?" + $("#searchForm").serialize());
    });
</script>