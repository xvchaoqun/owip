<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['proPostTimeToDay']=='true'?'yyyy.MM.dd':'yyyy.MM'}" var="_p_proPostTimeFormat"/>
<c:set value="${_pMap['birthToDay']=='true'?'yyyy.MM.dd':'yyyy.MM'}" var="_p_birthFormat"/>
<c:set value="<%=CadreConstants.CADRE_SCHOOL_TYPE_MAP%>" var="CADRE_SCHOOL_TYPE_MAP"/>
<c:if test="${(cm:isPermitted(PERMISSION_CADREADMIN)&&!cm:isPermitted(PERMISSION_CADREONLYVIEW))
	|| hasDirectModifyCadreAuth}">
    <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
        <li class="${empty param.type?"active":""}">
            <a href="javascript:;" onclick="_innerPage()"><i class="fa fa-table"></i> 查看基本信息</a>
        </li>
        <li class="${param.type==1?"active":""}">
            <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-edit"></i> 修改基本信息</a>
        </li>

    </ul>
</c:if>
<c:if test="${empty param.type}">
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-paw blue "></i>
                基本信息
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">

                <table class="table table-unhover table-bordered table-striped">
                    <tbody>
                    <tr>
                        <td rowspan="6" style="text-align: center;vertical-align: middle;
				 width: 50px;background-color: #fff;">
                            <img src="${ctx}/avatar?path=${cm:sign(uv.avatar)}&t=<%=new Date().getTime()%>"
                                 class="avatar">
                        </td>

                        <td class="bg-right">
                            姓名
                        </td>
                        <td class="bg-left" style="min-width: 150px;">
                                ${uv.realname}
                        </td>

                        <td class="bg-right">
                            工作证号
                        </td>
                        <td class="bg-left" style="min-width: 150px;">
                                ${uv.code}
                        </td>
                        <td class="bg-right">性别</td>
                        <td class="bg-left" style="min-width: 150px;">
                                ${GENDER_MAP.get(uv.gender)}
                        </td>

                    </tr>
                    <tr>
                        <td>
                            民族
                        </td>
                        <td>
                                ${uv.nation}
                        </td>
                        <td>出生日期</td>
                        <td>
                                ${cm:formatDate(cadre.birth, _p_birthFormat)}
                        </td>
                        <td>
                            年龄
                        </td>
                        <td>
                                ${uv.birth==null?'':(cm:intervalYearsUntilNow(_pMap['birthToDay']==false?cm:getFirstDayOfMonth(uv.birth):uv.birth))}
                        </td>
                    </tr>
                    <tr>
                        <td>政治面貌</td>
                        <td>
                                ${cm:cadreParty(cadre.isOw, cadre.owGrowTime, cadre.owPositiveTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}
                        </td>
                        <td>
                            党派加入时间
                        </td>
                        <td>
                                ${cm:cadreParty(cadre.isOw, cadre.owGrowTime, cadre.owPositiveTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('growTime')}
                        </td>

                        <td>国家/地区</td>
                        <td>
                                ${uv.country}
                        </td>
                    </tr>
                    <tr>
                        <c:if test="${_p_hasPartyModule}">
                            <td>
                                所在党组织
                            </td>
                            <td>
                                    ${cm:displayParty(member.partyId, member.branchId)}
                            </td>
                        </c:if>
                        <td>证件类型</td>
                        <td>
                                ${uv.idcardType}
                        </td>
                        <td>
                            证件号码
                        </td>
                        <td colspan="${_p_hasPartyModule?'':3}">
                            <t:mask src="${uv.idcard}" type="idCard"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            籍贯
                        </td>
                        <td style="min-width: 100px">
                                ${uv.nativePlace}
                        </td>
                        <td>出生地</td>
                        <td>
                                ${uv.homeplace}
                        </td>
                        <td>
                            户籍地
                        </td>
                        <td>
                                ${uv.household}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            健康状况
                        </td>
                        <td>
                                ${cm:getMetaType(uv.health).name}
                        </td>
                        <td>熟悉专业有何专长</td>
                        <td colspan="3">
                                ${uv.specialty}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-phone-square blue"></i>
                联系方式
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <table class="table table-unhover table-bordered table-striped">
                    <tbody>
                    <tr>
                        <td>
                            手机号
                        </td>
                        <td style="min-width: 80px">
                            <t:mask src="${uv.mobile}" type="mobile"/>
                        </td>
                        <td>
                            办公电话
                        </td>
                        <td style="min-width: 80px">
                            <t:mask src="${uv.phone}" type="fixedPhone"/>
                        </td>

                        <td>
                            电子邮箱
                        </td>
                        <td style="min-width: 80px">
                            <t:mask src="${uv.email}" type="email"/>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-info-circle blue"></i>
                人事信息
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <jsp:include page="/ext/cadre_info_table.jsp"/>
            </div>
        </div>
    </div>

    <c:if test="${_user.id==cadre.userId || cm:isPermitted('cadre:archive')}">
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-pencil-square-o blue"></i>
                任职信息
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <table class="table table-unhover table-bordered table-striped">
                    <tbody>
                    <tr>
                        <td>
                            任职单位
                        </td>
                        <td style="min-width: 80px">
                                ${unitMap.get(cadre.unitId).name}
                        </td>
                        <td>
                            单位属性
                        </td>
                        <td style="min-width: 80px">
                                ${cm:getMetaType(unitMap.get(cadre.unitId).typeId).name}
                        </td>

                        <td>
                            是否双肩挑
                        </td>
                        <td style="min-width: 80px">
                            <c:if test="${cadre.isDouble}">
                                <c:if test="${not empty cadre.doubleUnitIds}">
                                    <c:forEach var="unitId" items="${fn:split(cadre.doubleUnitIds, ',')}" varStatus="vs">
                                        <c:set var="unit" value="${cm:getUnitById(cm:toInt(unitId))}"/>
                                        <span class="${unit.status==UNIT_STATUS_HISTORY?'delete':''}">${unit.name}</span>
                                        ${vs.last?'':','}
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty cadre.doubleUnitIds}">是</c:if>
                            </c:if>
                            <c:if test="${!cadre.isDouble}">否</c:if>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            现任职务
                        </td>
                        <td style="min-width: 120px">
                                ${cadre.post}
                        </td>
                        <td>
                            任现职时间
                        </td>
                        <td>${cm:formatDate(cadre.lpWorkTime,_p_proPostTimeFormat)}
                        </td>
                        <td>
                            现职务始任时间
                        </td>
                        <td>${cm:formatDate(cadre.npWorkTime,_p_proPostTimeFormat)}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            行政级别
                        </td>
                        <td>
                                ${cm:getMetaType(cadre.adminLevel).name}
                        </td>
                        <td>任现职级时间</td>
                        <td>
                                ${cm:formatDate(cadre.sWorkTime,_p_proPostTimeFormat)}
                        </td>
                        <td>
                            任现职级年限
                        </td>
                        <td>
                            <c:if test="${not empty cadre.adminLevelYear}">
                                ${cadre.adminLevelYear==0?"未满一年":cadre.adminLevelYear}
                            </c:if>
                        </td>
                    </tr>
                    <tr>

                        <td>兼职单位1</td>
                        <td>
                                ${unitMap.get(subCadrePost1.unitId).name}
                        </td>
                        <td>
                            兼任职务1
                        </td>
                        <td>
                                ${subCadrePost1.post}
                        </td>
                        <td>
                            任兼职时间1
                        </td>
                        <td>${cm:formatDate(subCadrePost1.lpWorkTime,_p_proPostTimeFormat)}
                        </td>
                    </tr>
                    <tr>

                        <td>兼职单位2</td>
                        <td>
                                ${unitMap.get(subCadrePost2.unitId).name}
                        </td>
                        <td>
                            兼任职务2
                        </td>
                        <td>
                                ${subCadrePost2.post}
                        </td>
                        <td>
                            任兼职时间2
                        </td>
                        <td>${cm:formatDate(subCadrePost2.lpWorkTime,_p_proPostTimeFormat)}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </c:if>
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-star blue"></i>
                职称信息
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <jsp:include page="/ext/cadre_post_table.jsp"/>
            </div>
        </div>
    </div>

    <c:if test="${_user.id==cadre.userId || cm:isPermitted('cadre:archive')}">
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title lighter">
                <i class="ace-icon fa fa-graduation-cap blue"></i>
                学历学位信息
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body">
            <div class="widget-main no-padding">
                <table class="table table-unhover table-bordered table-striped">
                    <tbody>
                    <tr>
                        <td>
                            最高学历
                        </td>
                        <td style="min-width: 80px">
                                ${cm:getMetaType(highEdu.eduId).name}
                        </td>
                        <td>
                            学习方式
                        </td>
                        <td style="min-width: 80px">
                                ${cm:getMetaType(highEdu.learnStyle).name}
                        </td>

                        <td>
                            毕业学校
                        </td>
                        <td style="min-width: 80px">
                                ${highEdu.school}
                        </td>
                    </tr>
                    <tr>
                        <td>毕业学院</td>
                        <td>
                                ${highEdu.dep}
                        </td>
                        <td>
                            所学专业
                        </td>
                        <td>
                                ${highEdu.major}
                        </td>
                        <td>
                            学校类型
                        </td>
                        <td>${CADRE_SCHOOL_TYPE_MAP.get(highEdu.schoolType)}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            最高学位
                        </td>
                        <td>
                            <c:forEach items="${highDegrees}" var="highDegree" varStatus="vs">
                                ${highDegree.degree}${vs.last?'':'，'}
                            </c:forEach>
                        </td>
                        <td>学习方式</td>
                        <td>
                            <c:forEach items="${highDegrees}" var="highDegree" varStatus="vs">
                                ${cm:getMetaType(highDegree.learnStyle).name}${vs.last?'':'，'}
                            </c:forEach>
                        </td>
                        <td>
                            毕业学校
                        </td>
                        <td>
                            <c:forEach items="${highDegrees}" var="highDegree" varStatus="vs">
                                ${highDegree.school}${vs.last?'':'，'}
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>

                        <td>毕业学院</td>
                        <td>
                            <c:forEach items="${highDegrees}" var="highDegree" varStatus="vs">
                                ${highDegree.dep}${vs.last?'':'，'}
                            </c:forEach>
                        </td>
                        <td>
                            所学专业
                        </td>
                        <td>
                            <c:forEach items="${highDegrees}" var="highDegree" varStatus="vs">
                                ${highDegree.major}${vs.last?'':'，'}
                            </c:forEach>
                        </td>
                        <td>
                            学校类型
                        </td>
                        <td>
                            <c:forEach items="${highDegrees}" var="highDegree" varStatus="vs">
                                ${CADRE_SCHOOL_TYPE_MAP.get(highDegree.schoolType)}${vs.last?'':'，'}
                            </c:forEach>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </c:if>
</c:if>
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <c:if test="${param.type==1}">
        <form class="form-horizontal" action="${ctx}/cadreBaseInfo?cadreId=${cadre.id}" autocomplete="off"
              disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-paw blue "></i>
                        基本信息
                    </h4>
                </div>

                <div class="widget-body">
                    <div class="widget-main no-padding">

                        <table class="table table-unhover table-bordered table-striped">
                            <tbody>
                            <tr>
                                <td id="_avatarTitle" class="bg-right" style="text-align: left!important;">头像：</td>

                                <td class="bg-right">
                                    姓名
                                </td>
                                <td class="bg-left" style="min-width: 150px;">
                                        ${uv.realname}
                                </td>

                                <td class="bg-right">
                                    工作证号
                                </td>
                                <td class="bg-left" style="min-width: 150px;">
                                        ${uv.code}
                                </td>
                                <td class="bg-right">性别</td>
                                <td class="bg-left" style="min-width: 150px;">
                                        ${GENDER_MAP.get(uv.gender)}
                                </td>

                            </tr>
                            <tr>
                                <td rowspan="5" style="text-align: center;
				                         width: 50px;background-color: #fff;">
                                    <div style="width:145px">
                                        <input type="file" name="_avatar" id="_avatar"/>
                                    </div>
                                    <div>
                                        <button type="button" class="btn btn-xs btn-primary"
                                                onclick='$("#_avatar").click()'>
                                            <i class="fa fa-upload"></i> 重传
                                        </button>
                                    </div>
                                </td>
                                <td class="bg-right">
                                    民族
                                </td>
                                <td class="bg-left">
                                        ${uv.nation}
                                </td>
                                <td class="bg-right">出生日期</td>
                                <td class="bg-left">
                                        ${cm:formatDate(uv.birth, _p_birthFormat)}
                                </td>
                                <td class="bg-right">
                                    年龄
                                </td>
                                <td class="bg-left">
                                        ${empty uv.birth?'':cm:intervalYearsUntilNow(uv.birth)}
                                </td>
                            </tr>
                            <tr>
                                <td>政治面貌</td>
                                <td>
                                    <c:set var="cadreParty"
                                           value="${cm:cadreParty(cadre.isOw, cadre.owGrowTime, cadre.owPositiveTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false)}"/>
                                    <c:set var="original" value="${cadreParty.get('partyName')}"/>
                                    <c:set var="hasMultiParty" value="${fn:contains(original, ',')}"/>
                                    <c:if test="${hasMultiParty}">${original}</c:if><!--有多个党派不允许在此修改-->
                                    <c:if test="${!hasMultiParty}">
                                        <c:if test="${member!=null}">${original}</c:if>
                                        <c:if test="${member==null}">
                                            <select data-rel="select2" name="dpTypeId" data-width="150"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <option value="0">中共党员</option>
                                                <jsp:include page="/metaTypes?__code=mc_democratic_party"/>
                                            </select>
                                        </c:if>
                                    </c:if>
                                </td>
                                <td>
                                    党派加入时间
                                </td>
                                <td>
                                    <c:set var="original" value="${cadreParty.get('growTime')}"/>
                                    <c:if test="${member!=null || hasMultiParty}">${original}</c:if>
                                    <c:if test="${member==null && !hasMultiParty}">

                                         <c:if test="${_p_hasPartyModule}">
                                             <c:set var="growTimeFormat" value="yyyy.MM.dd"/>
                                             <div class="input-group date" data-date-format="yyyy.mm.dd"
                                             style="width: 130px; float: left;">
                                                <input class="form-control" type="text" name="_dpAddTime"
                                                       placeholder="yyyy.mm.dd"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </c:if>
                                        <c:if test="${!_p_hasPartyModule}">
                                            <c:set var="growTimeFormat" value="yyyy.MM"/>
                                             <div class="input-group date" data-date-min-view-mode="1" data-date-format="yyyy.mm"
                                             style="width: 110px; float: left;">
                                                <input class="form-control" type="text" name="_dpAddTime"
                                                       placeholder="yyyy.mm"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </c:if>
                                    </c:if>
                                    <script type="text/javascript">
                                        <c:choose>
                                        <c:when test="${cadre.dpTypeId>0}">
                                        $("#modalForm select[name=dpTypeId]").val(${cadre.dpTypeId});
                                        $("#modalForm input[name=_dpAddTime]").val('${cm:formatDate(cadre.dpGrowTime, growTimeFormat)}');
                                        </c:when>
                                        <c:when test="${cadre.isOw}">
                                        $("#modalForm select[name=dpTypeId]").val(0);
                                        $("#modalForm input[name=_dpAddTime]").val('${cm:formatDate(cadre.owGrowTime, growTimeFormat)}');
                                        </c:when>
                                        </c:choose>
                                        $("#modalForm select[name=dpTypeId]").on("change",function(){
                                            var val = $.trim($(this).val());
                                            if(val>0 && _cMap.metaTypeMap[val].boolAttr){
                                                $("#modalForm input[name=_dpAddTime]").val('').prop("disabled", true);
                                            }else{
                                                $("#modalForm input[name=_dpAddTime]").prop("disabled", false);
                                            }
                                        }).change();
                                    </script>
                                </td>

                                <td>国家/地区</td>
                                <td>
                                        ${uv.country}
                                </td>
                            </tr>
                            <tr>
                                <c:if test="${_p_hasPartyModule}">
                                    <td>
                                        所在党组织
                                    </td>
                                    <td>
                                            ${cm:displayParty(member.partyId, member.branchId)}
                                    </td>
                                </c:if>
                                <td>证件类型</td>
                                <td>
                                        ${uv.idcardType}
                                </td>
                                <td>
                                    证件号码
                                </td>
                                <td colspan="${_p_hasPartyModule?'':3}">
                                        <t:mask src="${uv.idcard}" type="idCard"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="star">*</span>籍贯
                                </td>
                                <td style="min-width: 100px">
                                    <input required type="text" name="nativePlace" value="${uv.nativePlace}">
                                    <div class="help-block">
                                            ${_pMap['nativePlaceHelpBlock']}
                                    </div>
                                </td>
                                <td><span class="star">*</span>出生地</td>
                                <td>
                                    <input required type="text" name="homeplace" value="${uv.homeplace}">
                                    <div class="help-block">
                                            ${_pMap['nativePlaceHelpBlock']}
                                    </div>
                                </td>
                                <td>
                                    <span class="star">*</span>户籍地
                                </td>
                                <td>
                                    <input required type="text" name="household" value="${uv.household}">
                                    <div class="help-block">
                                            ${_pMap['nativePlaceHelpBlock']}
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="star">*</span>健康状况</td>
                                <td>
                                    <select required data-rel="select2" name="health"
                                            data-placeholder="请选择" data-width="162">
                                        <option></option>
                                        <c:import url="/metaTypes?__code=mc_health"/>
                                    </select>
                                    <script type="text/javascript">
                                        $("select[name=health]").val('${uv.health}');
                                    </script>
                                </td>
                                <td>
                                    <span class="star">*</span>熟悉专业有何专长
                                </td>
                                <td colspan="3">
                                    <input required type="text" name="specialty" value="${uv.specialty}"
                                           style="width: 500px">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-info-circle blue"></i>
                        人事信息
                    </h4>
                </div>

                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <table class="table table-unhover table-bordered table-striped">
                            <tbody>
                            <tr>
                                <td style="width: 300px;">
                                    参加工作时间
                                </td>
                                <td title="${hasVerifyWorkTime?'已根据您的档案记载对参加工作时间进行了组织认定':''}">
                                    <c:set var="original" value="${cm:formatDate(cadre.workTime,'yyyy.MM')}"/>
                                    <c:if test="${hasVerifyWorkTime}">${original}</c:if>
                                    <c:if test="${!hasVerifyWorkTime}">
                                        <div class="input-group date" data-date-min-view-mode="1"
                                             data-date-format="yyyy.mm"
                                             style="width: 130px">
                                            <input class="form-control" type="text" name="_workTime"
                                                   placeholder="yyyy.mm" value="${original}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </c:if>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <shiro:hasAnyRoles name="${ROLE_CADRERECRUIT},${ROLE_CADRERESERVE},${ROLE_CADREADMIN}">
                <div class="widget-box transparent">
                    <div class="widget-header widget-header-flat">
                        <h4 class="widget-title lighter">
                            <i class="ace-icon fa fa-info-circle blue"></i>
                            干部信息
                        </h4>
                    </div>

                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <table class="table table-unhover table-bordered table-striped">
                                <tbody>
                                <tr>
                                    <td style="width: 300px;">
                                        <span class="star">*</span>所在单位及职务
                                    </td>
                                    <td>
                                        <textarea required type="text" name="title"
                                                  style="width: 500px">${cadre.title}</textarea>
                                    </td>
                                </tr>
                                <c:if test="${not empty cadreReserve}">
                                    <tr>
                                        <td style="width: 300px;">
                                            <span class="star">*</span>任职时间
                                        </td>
                                        <td>
                                            <c:set var="original"
                                                   value="${cm:formatDate(cadreReserve.postTime,'yyyy.MM')}"/>
                                            <div class="input-group date" data-date-min-view-mode="1"
                                                 data-date-format="yyyy.mm"
                                                 style="width: 130px">
                                                <input class="form-control" type="text" name="_postTime"
                                                       placeholder="yyyy.mm" value="${original}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </shiro:hasAnyRoles>
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-phone-square blue"></i>
                        联系方式
                    </h4>
                </div>

                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <table class="table table-unhover table-bordered table-striped">
                            <tbody>
                            <tr>
                                <td>
                                    <span class="star">*</span>手机号
                                </td>
                                <td style="min-width: 80px">
                                    <input required type="text" class="mobile" name="mobile" value="${uv.mobile}">
                                </td>
                                <td>
                                    办公电话
                                </td>
                                <td style="min-width: 80px">
                                    <input type="text" name="phone" value="${uv.phone}">
                                </td>

                                <td>
                                    电子邮箱
                                </td>
                                <td>
                                    <input style="width: 380px" class="email" type="text" name="email" value="${uv.email}">
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="clearfix form-actions center">
                <a class="popupBtn btn btn-warning"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_cadre_base_info">
                    <i class="fa fa-info-circle"></i> 填写说明</a>
                &nbsp; &nbsp; &nbsp;
                <button id="submitBtn" class="btn btn-info"
                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                        type="button">
                    <i class="ace-icon fa fa-save bigger-110"></i>
                    保存
                </button>
                    <%--
                                &nbsp; &nbsp; &nbsp;
                                <button class="hideView btn btn-default" type="button">
                                    <i class="ace-icon fa fa-undo bigger-110"></i>
                                    取消
                                </button>--%>
            </div>
        </form>
        <style>
            .ace-file-container {
                height: 200px !important;
            }

            .ace-file-multiple .ace-file-container .ace-file-name .ace-icon {
                line-height: 120px !important;
            }
        </style>
    </c:if>
    <script>
        <shiro:hasPermission name="cadre:updateWithoutRequired">
        $('span.star').css("color", "gray");
        $('input, textarea').prop("required", false);
        </shiro:hasPermission>

        function _innerPage(type) {
            if (type == undefined) {
                $("#tab-content").loadPage("${ctx}/cadre_base?cadreId=${param.cadreId}&_auth=${param._auth}")
            } else {
                $("#tab-content").loadPage("${ctx}/cadre_base?cadreId=${param.cadreId}&_auth=${param._auth}&type=" + type)
            }
        }

        <c:if test="${param.type==1}">
        $.fileInput($("#_avatar"), {
            style: 'well',
            btn_choose: '更换头像',
            btn_change: null,
            no_icon: 'ace-icon fa fa-picture-o',
            thumbnail: 'large',
            maxSize:${_uploadMaxSize},
            droppable: true,
            previewWidth: 143,
            previewHeight: 198,
            allowExt: ['jpg', 'jpeg', 'png', 'gif'],
            allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
        })
        $('#modalForm button[type=reset]').on(ace.click_event, function () {
            //$('#user-profile input[type=file]').ace_file_input('reset_input');
            $("#_avatar").ace_file_input('show_file_list', [{
                type: 'image',
                name: '${ctx}/avatar?path=${cm:sign(uv.avatar)}&t=<%=new Date().getTime()%>'
            }]);
        });
        $("#_avatar").ace_file_input('show_file_list', [{
            type: 'image',
            name: '${ctx}/avatar?path=${cm:sign(uv.avatar)}&t=<%=new Date().getTime()%>'
        }]);

        $("#submitBtn").click(function () {
            $("#modalForm").submit();
            return false;
        });
        $("#modalForm").validate({
            submitHandler: function (form) {
                /*if($("select[name=dpTypeId]", "#modalForm").val()>0 && $("input[name=_dpAddTime]").val()==""){
                    SysMsg.info("请填写党派加入时间");
                    return ;
                }*/
                if ($("select[name=dpTypeId]", "#modalForm").val() == '' && $("input[name=_dpAddTime]").val() != "") {
                    SysMsg.info("请选择政治面貌");
                    return;
                }
                var $btn = $("#submitBtn").button('loading');
                $(form).ajaxSubmit({
                    success: function (ret) {
                        if (ret.success) {
                            _innerPage();
                        }
                        $btn.button('reset');
                    }
                });
            }
        });
        $('[data-rel="select2"]').select2();
        $.register.date($('.input-group.date'));
        </c:if>
    </script>
</c:if>