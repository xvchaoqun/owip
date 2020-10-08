<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1148px">
            <c:set var="_query" value="${not empty param.startNowPostAge||not empty param.endNowPostAge||not empty param.adminLevels
            ||not empty param.isKeepSalary
            ||not empty param.isPrincipal ||not empty param.isDouble || not empty param.labels}"/>
            <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                <div class="widget-header ${_query?'search':''}">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="cadreType" value="${cadreType}">
                            <input type="hidden" name="unitTypeGroup" value="${unitTypeGroup}">
                            <div class="columns">
                                <div class="column">
                                    <label>行政级别</label>
                                    <div class="input">
                                        <select class="multiselect" multiple="" name="adminLevels">
                                            <c:import url="/metaTypes?__code=mc_admin_level"/>
                                        </select>
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
                                <div class="column">
                                    <label>是否保留待遇</label>
                                    <div class="input">
                                        <select name="isKeepSalary" data-width="100"
                                                data-rel="select2" data-placeholder="请选择">
                                            <option></option>
                                            <option value="1">是</option>
                                            <option value="0">否</option>

                                        </select>
                                        <script>
                                            $("#searchForm select[name=isKeepSalary]").val('${param.isKeepSalary}');
                                        </script>
                                    </div>
                                </div>
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
                                    <label>现职务始任年限</label>
                                    <div class="input">
                                        <input class="num" type="text" name="startNowPostAge"
                                               value="${param.startNowPostAge}"> 至 <input class="num"
                                                                                          type="text"
                                                                                          name="endNowPostAge"
                                                                                          value="${param.endNowPostAge}">
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="clearfix form-actions center">
                                <button type="button" class="jqSearchBtn btn btn-default btn-sm"
                                   data-mask-el="#contentDiv"
                                   data-url="${ctx}/stat_cadre"><i class="fa fa-search"></i> 查找</button>
                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                    <button type="button" data-url="${ctx}/stat_cadre"
                                            class="reloadBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${empty param.unitTypeGroup}">active</c:if>">
                    <a href="javascript:;" class="unit-group" data-type="">
                        <i class="fa fa-signal"></i> 全部</a>
                </li>
                <c:forEach items="${unitTypeGroupMap}" var="entity" varStatus="vs">
                    <li class="<c:if test="${param.unitTypeGroup==entity.key}">active</c:if>">
                        <a href="javascript:;" class="unit-group" data-type="${entity.key}">
                            <i class="fa fa-signal"></i> ${entity.value.name}</a>
                    </li>
                </c:forEach>
                <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
                    <shiro:hasPermission name="cadre:export">
                    <button class="downloadBtn pull-left btn btn-success btn-sm"
                            data-url="${ctx}/stat_cadre?export=1&${cm:encodeQueryString(pageContext.request.queryString)}"><i class="fa fa-download"></i>
                        导出
                    </button>
                    </shiro:hasPermission>
                </div>
                <c:if test="${_p_hasKjCadre}">
                    <div class="input-group pull-left" style="left: 60px;padding-top: 6px">
                        <c:forEach items="${CADRE_TYPE_MAP}" var="entity">
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-success checkbox-circle">
                                <input required type="radio" name="cadreType" id="cadreType${entity.key}"
                                    ${cadreType==entity.key?"checked":""} value="${entity.key}">
                                <label for="cadreType${entity.key}">
                                        ${entity.value}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </ul>

            <div class="tab-content" style="padding: 5px 4px 0px">
                <table border=0 cellpadding=0 cellspacing=0
                       style='border-collapse:collapse;table-layout:fixed;'>
                    <tr height=41 style='mso-height-source:userset;height:31.15pt'>
                        <td colspan=14 height=41 class=xl97>${_school}${CADRE_TYPE_MAP.get(cadreType)}情况统计表
                            <c:if test="${not empty param.unitTypeGroup}">（${unitTypeGroupMap.get(param.unitTypeGroup).name}）</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14" align="right">注：百分比按四舍五入取值<%--${(_query|| not empty param.unitTypeGroup)?'':'  数据刷新频率：30s'}--%></td>
                    </tr>
                    <tr class=xl66>
                        <td colspan=2 rowspan=2 height=46 class=xl70 width=170 style='height:34.5pt;width:128pt'>类别</td>
                        <td colspan=2 class=xl91 width=162
                            style='border-right:2.0pt double black;border-left:none;width:122pt'>
                            总<span style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span>体
                        </td>
                        <td colspan=6 class=xl95 width=486
                            style='border-right:2.0pt double black;border-left:none;width:366pt'>行政级别
                        </td>
                        <td colspan=4 class=xl86 width=324 style='border-right:1pt solid black;width:244pt'>
                            性<span style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span>别
                        </td>
                    </tr>
                    <tr class=xl66>
                        <td height=23 class=xl78>人数</td>
                        <td class=xl82>比率</td>
                        <td class=xl72>${cadreType==CADRE_TYPE_CJ?"正处":"正科"}</td>
                        <td class=xl69>比率</td>
                        <td class=xl70>${cadreType==CADRE_TYPE_CJ?"副处":"副科"}</td>
                        <td class=xl69>比率</td>
                        <td class=xl70>无级别</td>
                        <td class=xl82>比率</td>
                        <td class=xl72>男</td>
                        <td class=xl69>比率</td>
                        <td class=xl70>女</td>
                        <td class=xl69>比率</td>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>总数</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="1"/>
                            <jsp:param name="firstTypeCode" value="all" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>${cadreType==CADRE_TYPE_CJ?"正处":"正科"}</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="2"/>
                            <jsp:param name="firstTypeCode" value="adminLevel"/>
                            <jsp:param name="firstTypeNum" value="1"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>${cadreType==CADRE_TYPE_CJ?"副处":"副科"}</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="3"/>
                            <jsp:param name="firstTypeCode" value="adminLevel"/>
                            <jsp:param name="firstTypeNum" value="2"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>聘任制（无级别）
                        <span class="prompt" data-title="聘任制（无级别）说明" data-width="400"
							  data-prompt="<ul>
							  <li>统计“无行政级别”的干部</li>
							  </ul>"><i class="fa fa-question-circle-o"></i></span>
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="4"/>
                            <jsp:param name="firstTypeCode" value="adminLevel"/>
                            <jsp:param name="firstTypeNum" value="3"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=2 height=46 class=xl94>民族</td>
                        <td class=xl70>汉族</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="5"/>
                            <jsp:param name="firstTypeCode" value="nation" />
                            <jsp:param name="firstTypeNum" value="1" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>少数民族</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="6"/>
                            <jsp:param name="firstTypeCode" value="nation" />
                            <jsp:param name="firstTypeNum" value="2" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=3 height=46 class=xl94>政治面貌
                            <span class="prompt" data-title="政治面貌说明" data-width="450"
							  data-prompt="<ul>
							  <li>同时是中共党员和民主党派成员的，分别计入统计数据</li>
							  <li>“群众”是指既不是中共党员，也不是民主党派成员</li>
							  </ul>"><i class="fa fa-question-circle-o"></i></span>
                        </td>
                        <td class=xl70>中共党员</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="7"/>
                            <jsp:param name="firstTypeCode" value="politicsStatus" />
                            <jsp:param name="firstTypeNum" value="1" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>民主党派
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="8"/>
                            <jsp:param name="firstTypeCode" value="politicsStatus" />
                            <jsp:param name="firstTypeNum" value="2" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>群众
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="Qz"/>
                            <jsp:param name="firstTypeCode" value="politicsStatus" />
                            <jsp:param name="firstTypeNum" value="3" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=7 height=161 class=xl94>年龄分布
                        </td>
                        <td class=xl70 style="white-space: nowrap">30岁及以下</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="9"/>
                            <jsp:param name="firstTypeCode" value="age" />
                            <jsp:param name="firstTypeNum" value="1" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>31-35岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="10"/>
                            <jsp:param name="firstTypeCode" value="age" />
                            <jsp:param name="firstTypeNum" value="2" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>36-40岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="11"/>
                            <jsp:param name="firstTypeCode" value="age" />
                            <jsp:param name="firstTypeNum" value="3" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>41-45岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="12"/>
                            <jsp:param name="firstTypeCode" value="age" />
                            <jsp:param name="firstTypeNum" value="4" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>46-50岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="13"/>
                            <jsp:param name="firstTypeCode" value="age" />
                            <jsp:param name="firstTypeNum" value="5" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>51-55岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="14"/>
                            <jsp:param name="firstTypeCode" value="age" />
                            <jsp:param name="firstTypeNum" value="6" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>55岁以上
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="15"/>
                            <jsp:param name="firstTypeCode" value="age" />
                            <jsp:param name="firstTypeNum" value="7" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>平均年龄
                        </td>
                        <jsp:include page="row2.jsp">
                            <jsp:param name="row" value="_avgAge"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=4 height=69 class=xl94>职称分布
                            <span class="prompt" data-title="职称分布说明" data-width="400"
							  data-prompt="<ul>
							  <li>此处是针对职级（职称级别）的分布进行统计</li>
							  <li>“其他”是指初级以下或无职称或职称信息暂缺的人员</li>
							  </ul>"><i class="fa fa-question-circle-o"></i></span>
                        </td>
                        <td height=23 class=xl70>正高</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="16"/>
                            <jsp:param name="firstTypeCode" value="postLevel" />
                            <jsp:param name="firstTypeNum" value="1" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>副高
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="17"/>
                            <jsp:param name="firstTypeCode" value="postLevel" />
                            <jsp:param name="firstTypeNum" value="2" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70 style="white-space: nowrap">中（初）级
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="18"/>
                            <jsp:param name="firstTypeCode" value="postLevel" />
                            <jsp:param name="firstTypeNum" value="3" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70 style="white-space: nowrap">其他
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="_otherZc"/>
                            <jsp:param name="firstTypeCode" value="postLevel" />
                            <jsp:param name="firstTypeNum" value="4" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=4 height=69 class=xl94>学位分布
                            <span class="prompt" data-title="学位分布说明" data-width="400"
							  data-prompt="<ul>
							  <li>“其他”是指没有取得学位或学位信息暂缺的人员</li>
							  </ul>"><i class="fa fa-question-circle-o"></i></span>
                        </td>
                        <td height=23 class=xl70>博士
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="19"/>
                            <jsp:param name="firstTypeCode" value="degree" />
                            <jsp:param name="firstTypeNum" value="<%=SystemConstants.DEGREE_TYPE_BS%>" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>硕士
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="20"/>
                            <jsp:param name="firstTypeCode" value="degree" />
                            <jsp:param name="firstTypeNum" value="<%=SystemConstants.DEGREE_TYPE_SS%>" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>学士
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="21"/>
                            <jsp:param name="firstTypeCode" value="degree" />
                            <jsp:param name="firstTypeNum" value="<%=SystemConstants.DEGREE_TYPE_XS%>" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>其他
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="_otherDegree"/>
                            <jsp:param name="firstTypeCode" value="degree" />
                            <jsp:param name="firstTypeNum" value="4" />
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>专职干部
                            <span class="prompt" data-title="专职干部说明" data-width="400"
							  data-prompt="<ul>
							  <li>统计非“双肩挑”的干部</li>
							  </ul>"><i class="fa fa-question-circle-o"></i></span>
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="22"/>
                            <jsp:param name="firstTypeCode" value="isNotDouble"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>双肩挑干部
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="23"/>
                            <jsp:param name="firstTypeCode" value="isDouble"/>
                        </jsp:include>
                    </tr>
                    <c:set var="eduCount" value="${fn:length(eduRowMap)}"/>
                    <c:forEach items="${eduRowMap}" var="entity" varStatus="vs">
                    <tr>
                        <c:if test="${vs.first}">
                        <td rowspan=${eduCount} height=92 class=xl94>学历分布</td>
                        </c:if>
                        <td height=23 class=xl70 style="white-space: nowrap">${cm:getMetaType(entity.key).name}</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="${23+vs.index+5}"/>
                            <jsp:param name="firstTypeCode" value="education"/>
                            <jsp:param name="firstTypeNum" value="${entity.key}"/>
                        </jsp:include>
                        <%--<c:forEach items="${entity.value}" var="col">
                            <td>${col}</td>
                        </c:forEach>--%>
                    </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<style>
    tr {
        mso-height-source: auto;
        mso-ruby-visibility: none;
    }

    col {
        mso-width-source: auto;
        mso-ruby-visibility: none;
    }

    br {
        mso-data-placement: same-cell;
    }

    ruby {
        ruby-align: left;
    }

    .style0 {
        mso-number-format: General;
        text-align: general;
        vertical-align: bottom;
        white-space: nowrap;
        mso-rotate: 0;
        mso-background-source: auto;
        mso-pattern: auto;
        color: black;
        font-size: 11.0pt;
        font-weight: 400;
        font-style: normal;
        text-decoration: none;
        font-family: 宋体;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        border: none;
        mso-protection: locked visible;
        mso-style-name: 常规;
        mso-style-id: 0;
    }

    td {
        mso-style-parent: style0;
        padding-top: 1px;
        padding-right: 1px;
        padding-left: 1px;
        mso-ignore: padding;
        color: black;
        font-size: 11.0pt;
        font-weight: 400;
        font-style: normal;
        text-decoration: none;
        font-family: 宋体;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        mso-number-format: General;
        text-align: general;
        vertical-align: bottom;
        border: none;
        mso-background-source: auto;
        mso-pattern: auto;
        mso-protection: locked visible;
        white-space: nowrap;
        mso-rotate: 0;
    }

    .xl65 {
        mso-style-parent: style0;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl66 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl67 {
        mso-style-parent: style0;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
        layout-flow: vertical-ideographic;
    }

    .xl68 {
        mso-style-parent: style0;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl69 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl70 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl71 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl72 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl73 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl74 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl75 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl76 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: 0%;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl77 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: 0%;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl78 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: none;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl79 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl80 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
        layout-flow: vertical-ideographic;
    }

    .xl81 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl82 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        /*border-bottom:1pt solid windowtext;*/
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl83 {
        mso-style-parent: style0;
        color: windowtext;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl84 {
        mso-style-parent: style0;
        text-align: left;
        vertical-align: top;
        border-top: none;
        border-right: none;
        border-bottom: none;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl85 {
        mso-style-parent: style0;
        text-align: left;
        vertical-align: top;
        white-space: normal;
    }

    .xl86 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl87 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: 2.0pt double windowtext;
        white-space: normal;
    }

    .xl88 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl89 {
        mso-style-parent: style0;
        color: windowtext;
        font-size: 18.0pt;
        font-family: 华文中宋;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        text-align: center;
        vertical-align: middle;
        border-top: none;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl90 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
        layout-flow: vertical-ideographic;
    }

    .xl91 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl92 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl93 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl94 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
        /*layout-flow:vertical-ideographic;*/
    }

    .xl95 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 2.0pt double windowtext;
        white-space: normal;
    }

    .xl96 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl97 {
        mso-style-parent: style0;
        color: windowtext;
        font-size: 18.0pt;
        font-family: 华文中宋;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl98 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl99 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl100 {
        mso-style-parent: style0;
        color: windowtext;
        text-align: left;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: none;
        border-left: none;
        white-space: normal;
    }

    .xl101 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: none;
        border-bottom: none;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl102 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: none;
        border-left: none;
        white-space: normal;
    }

    .xl103 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: none;
        border-right: none;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl104 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border-top: none;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl105 {
        mso-style-parent: style0;
        color: red;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl106 {
        mso-style-parent: style0;
        color: red;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl107 {
        mso-style-parent: style0;
        color: red;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl108 {
        mso-style-parent: style0;
        color: red;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl109 {
        mso-style-parent: style0;
        color: red;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 2.0pt double windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: 1pt solid windowtext;
        white-space: normal;
    }

    .xl110 {
        mso-style-parent: style0;
        color: #4F81BD;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl111 {
        mso-style-parent: style0;
        color: #4F81BD;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl112 {
        mso-style-parent: style0;
        color: #4F81BD;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl113 {
        mso-style-parent: style0;
        color: #4F81BD;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl114 {
        mso-style-parent: style0;
        color: #C00000;
        text-align: center;
        vertical-align: middle;
        border-top: 1pt solid windowtext;
        border-right: 1pt solid windowtext;
        border-bottom: 1pt solid windowtext;
        border-left: none;
        white-space: normal;
    }

    .xl115 {
        mso-style-parent: style0;
        color: #C00000;
        mso-number-format: "0\.0%";
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        mso-protection: unlocked visible;
        white-space: normal;
    }

    .xl116 {
        mso-style-parent: style0;
        color: #C00000;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

</style>
<script>
    $.each(${cm:toJSONObject(param)}, function(name, val){
        if($.trim(val)!=''){
            $("[name='"+name+"']", ".search-form .columns .column .input").closest(".column").find("label").addClass("bolder red");
        }
    })
    $.register.multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm select[name=labels]'), ${cm:toJSONArray(selectLabels)});
    $('[data-rel="select2"]').select2();
    $("input[name=cadreType]").click(function () {
        var cadreType = $(this).val();
        //console.log("cadreType="+cadreType)
        $("#searchForm input[name=cadreType]").val(cadreType);
        $(".jqSearchBtn").click();
    })
    $(".unit-group").click(function () {
        $("#searchForm input[name=unitTypeGroup]").val($(this).data('type'));
        $(".jqSearchBtn").click();
    })
</script>
