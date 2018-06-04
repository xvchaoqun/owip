<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="UNIT_TYPE_ATTR_MAP" value="<%=SystemConstants.UNIT_TYPE_ATTR_MAP%>"/>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${empty param.type}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-mask-el="#statTable" data-url="${ctx}/stat_cadre?type="><i class="fa fa-signal"></i> 所有中层干部</a>
                </li>
                <li class="<c:if test="${param.type=='jg'}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-mask-el="#statTable" data-url="${ctx}/stat_cadre?type=<%=SystemConstants.UNIT_TYPE_ATTR_JG%>"><i class="fa fa-signal"></i> 机关及直属单位</a>
                </li>
                <li class="<c:if test="${param.type=='xy'}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-mask-el="#statTable" data-url="${ctx}/stat_cadre?type=<%=SystemConstants.UNIT_TYPE_ATTR_XY%>"><i class="fa fa-signal"></i> 学部、院、系所</a>
                </li>
                <li class="<c:if test="${param.type=='fs'}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-mask-el="#statTable" data-url="${ctx}/stat_cadre?type=<%=SystemConstants.UNIT_TYPE_ATTR_FS%>"><i class="fa fa-signal"></i> 附属单位</a>
                </li>

                <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                    <a class="btn btn-success btn-sm"
                       href="${ctx}/stat_cadre?export=1"><i class="fa fa-download"></i> 导出</a>
                </div>
            </ul>

            <div class="tab-content" style="padding: 5px 4px 0px">
                <table id="statTable" border=0 cellpadding=0 cellspacing=0
                       style='border-collapse:collapse;table-layout:fixed;width:860pt'>
                    <tr height=41 style='mso-height-source:userset;height:31.15pt'>
                        <td colspan=14 height=41 class=xl97>${_school}中层领导干部情况统计表
                            <c:if test="${empty param.type}">（所有中层干部）</c:if>
                            <c:if test="${not empty param.type}">（${UNIT_TYPE_ATTR_MAP.get(param.type)}）</c:if>
                            </td>
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
                        <td class=xl72>正处</td>
                        <td class=xl69>比率</td>
                        <td class=xl70>副处</td>
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
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>正处</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="2"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>副处</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="3"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>聘任制（无级别）</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="4"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=2 height=46 class=xl94>民族</td>
                        <td class=xl70>汉族</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="5"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>少数民族</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="6"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=2 height=46 class=xl94>党派
                        </td>
                        <td class=xl70>中共党员</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="7"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>民主党派
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="8"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=7 height=161 class=xl94>年龄分布
                        </td>
                        <td class=xl70  style="white-space: nowrap">30岁及以下</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="9"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>31-35岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="10"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>36-40岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="11"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>41-45岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="12"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>46-50岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="13"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>51-55岁
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="14"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>55岁以上
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="15"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=6 height=138 class=xl94>职称分布
                        </td>
                        <td class=xl70>正高(总)</td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="16"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70  style="white-space: nowrap">正高(二级)
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="17"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70 style="white-space: nowrap">正高(三级)
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="18"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70 style="white-space: nowrap">正高(四级)
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="19"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>副高
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="20"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70  style="white-space: nowrap">中级及以下
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="21"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td rowspan=4 height=92 class=xl94>学历分布
                        </td>
                        <td height=23 class=xl70>博士
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="22"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>硕士
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="23"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>学士
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="24"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td height=23 class=xl70>大专
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="25"/>
                        </jsp:include>
                    </tr>
                    <tr>
                        <td colspan=2 height=23 class=xl70>专职干部
                        </td>
                        <jsp:include page="row.jsp">
                            <jsp:param name="row" value="26"/>
                        </jsp:include>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<style>
    tr
    {mso-height-source:auto;
        mso-ruby-visibility:none;}
    col
    {mso-width-source:auto;
        mso-ruby-visibility:none;}
    br
    {mso-data-placement:same-cell;}
    ruby
    {ruby-align:left;}
    .style0
    {mso-number-format:General;
        text-align:general;
        vertical-align:bottom;
        white-space:nowrap;
        mso-rotate:0;
        mso-background-source:auto;
        mso-pattern:auto;
        color:black;
        font-size:11.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        border:none;
        mso-protection:locked visible;
        mso-style-name:常规;
        mso-style-id:0;}
    td
    {mso-style-parent:style0;
        padding-top:1px;
        padding-right:1px;
        padding-left:1px;
        mso-ignore:padding;
        color:black;
        font-size:11.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:宋体;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        mso-number-format:General;
        text-align:general;
        vertical-align:bottom;
        border:none;
        mso-background-source:auto;
        mso-pattern:auto;
        mso-protection:locked visible;
        white-space:nowrap;
        mso-rotate:0;}
    .xl65
    {mso-style-parent:style0;
        text-align:center;
        vertical-align:middle;
        white-space:normal;}
    .xl66
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        white-space:normal;}
    .xl67
    {mso-style-parent:style0;
        text-align:center;
        vertical-align:middle;
        white-space:normal;
        layout-flow:vertical-ideographic;}
    .xl68
    {mso-style-parent:style0;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        white-space:normal;}
    .xl69
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl70
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl71
    {mso-style-parent:style0;
        color:windowtext;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl72
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl73
    {mso-style-parent:style0;
        color:windowtext;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl74
    {mso-style-parent:style0;
        color:windowtext;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl75
    {mso-style-parent:style0;
        color:windowtext;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        mso-protection:unlocked visible;
        white-space:normal;}
    .xl76
    {mso-style-parent:style0;
        color:windowtext;
        mso-number-format:0%;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        mso-protection:unlocked visible;
        white-space:normal;}
    .xl77
    {mso-style-parent:style0;
        color:windowtext;
        mso-number-format:0%;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl78
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:none;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl79
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl80
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        white-space:normal;
        layout-flow:vertical-ideographic;}
    .xl81
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        white-space:normal;}
    .xl82
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        /*border-bottom:1pt solid windowtext;*/
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl83
    {mso-style-parent:style0;
        color:windowtext;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        border-bottom:1pt solid windowtext;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl84
    {mso-style-parent:style0;
        text-align:left;
        vertical-align:top;
        border-top:none;
        border-right:none;
        border-bottom:none;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl85
    {mso-style-parent:style0;
        text-align:left;
        vertical-align:top;
        white-space:normal;}
    .xl86
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:none;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl87
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:none;
        border-bottom:1pt solid windowtext;
        border-left:2.0pt double windowtext;
        white-space:normal;}
    .xl88
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl89
    {mso-style-parent:style0;
        color:windowtext;
        font-size:18.0pt;
        font-family:华文中宋;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        text-align:center;
        vertical-align:middle;
        border-top:none;
        border-right:none;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl90
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;
        layout-flow:vertical-ideographic;}
    .xl91
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:none;
        border-bottom:1pt solid windowtext;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl92
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl93
    {mso-style-parent:style0;
        color:windowtext;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        border-bottom:1pt solid windowtext;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl94
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;
        /*layout-flow:vertical-ideographic;*/}
    .xl95
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:1pt solid windowtext;
        border-left:2.0pt double windowtext;
        white-space:normal;}
    .xl96
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        border-bottom:1pt solid windowtext;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl97
    {mso-style-parent:style0;
        color:windowtext;
        font-size:18.0pt;
        font-family:华文中宋;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        text-align:center;
        vertical-align:middle;
        white-space:normal;}
    .xl98
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl99
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        border-bottom:1pt solid windowtext;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl100
    {mso-style-parent:style0;
        color:windowtext;
        text-align:left;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:none;
        border-bottom:none;
        border-left:none;
        white-space:normal;}
    .xl101
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:none;
        border-bottom:none;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl102
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:none;
        border-left:none;
        white-space:normal;}
    .xl103
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:none;
        border-right:none;
        border-bottom:1pt solid windowtext;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl104
    {mso-style-parent:style0;
        color:windowtext;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:none;
        border-right:1pt solid windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl105
    {mso-style-parent:style0;
        color:red;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl106
    {mso-style-parent:style0;
        color:red;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl107
    {mso-style-parent:style0;
        color:red;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        mso-protection:unlocked visible;
        white-space:normal;}
    .xl108
    {mso-style-parent:style0;
        color:red;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl109
    {mso-style-parent:style0;
        color:red;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:2.0pt double windowtext;
        border-bottom:1pt solid windowtext;
        border-left:1pt solid windowtext;
        white-space:normal;}
    .xl110
    {mso-style-parent:style0;
        color:#4F81BD;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl111
    {mso-style-parent:style0;
        color:#4F81BD;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        mso-protection:unlocked visible;
        white-space:normal;}
    .xl112
    {mso-style-parent:style0;
        color:#4F81BD;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl113
    {mso-style-parent:style0;
        color:#4F81BD;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}
    .xl114
    {mso-style-parent:style0;
        color:#C00000;
        text-align:center;
        vertical-align:middle;
        border-top:1pt solid windowtext;
        border-right:1pt solid windowtext;
        border-bottom:1pt solid windowtext;
        border-left:none;
        white-space:normal;}
    .xl115
    {mso-style-parent:style0;
        color:#C00000;
        mso-number-format:"0\.0%";
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        mso-protection:unlocked visible;
        white-space:normal;}
    .xl116
    {mso-style-parent:style0;
        color:#C00000;
        text-align:center;
        vertical-align:middle;
        border:1pt solid windowtext;
        white-space:normal;}

</style>
