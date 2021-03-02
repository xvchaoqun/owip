<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="contentDiv" style="width: 1148px" width=1283>

            <jsp:include page="menu.jsp"/>
            <div class="tab-content" style="padding: 2px 2px 0px">
                <table border=0 cellpadding=0 cellspacing=0 width=1583 >
                    <tr height=52 style='mso-height-source:userset;height:39.0pt'>
                        <td colspan=13 height=52 width=1483 class=xl99 width=1583 style='height:39.0pt;width:966pt'>
                            <a name="Print_Area">${_school}<c:if test="${param.cls==1}">${checkParty.name}</c:if>全校基层党组织基本情况一览表</a>
                        </td>
                    </tr>
                    <tr height=28 style='mso-height-source:userset;height:21.0pt'>
                        <td colspan=13 height=28 width=1583 class=xl100 style='height:21.0pt'>（数据源自${cm:formatDate(now,'yyyy年MM月dd日')}年统数据）</td>
                    </tr>
                    <tr height=33 width=1583 style='mso-height-source:userset;height:24.95pt'>
                        <td height=33 width="130" class=xl73 style=' border-right:.5pt solid black;height:24.95pt;' colspan="2">二级党组织</td>
                        <td class=xl73 width="150" style='border-left:none;'>党支部</td>
                        <td class=xl73 width="130" style='border-left:none'>党支部类型</td>
                        <td class=xl73 width="80" style='border-left:none'>支部书记</td>
                        <td class=xl73 width="60">性别</td>
                        <td class=xl73 width="60">民族</td>
                        <td class=xl73 width="100" style='border-left:none;'>入党时间</td>
                        <td class=xl73 width="100" style='border-left:none;'>党龄</td>
                        <td class=xl73 width="100" style='border-left:none;'>出生年月</td>
                        <td class=xl73 width="100" style='border-left:none;'>年龄</td>
                        <td class=xl73 width="100" style='border-left:none;'>职称/职务/身份</td>
                        <td class=xl73 style='border-left:none;width: 170px'>职务</td>
                        <td class=xl73 style='border-left:none;width: 60px'>正式党员</td>
                        <td class=xl73 style='border-left:none;width: 60px'>预备党员</td>
                        <td class=xl73 style='border-left:none;width: 70px'>入党申请人</td>
                        <td class=xl73 style='border-left:none;width: 80px'>入党积极分子</td>
                        <td class=xl73 style='border-left:none;width: 60px'>发展对象</td>
                        <td class=xl73 style='border-left:none;width: 60px'>总计</td>
                    </tr>
                    <c:forEach items="${dataList}" var="data">
                        <tr height=33 width=1583 style='mso-height-source:userset;height:24.95pt'>
                            <td height=33 class=xl73 style=' border-right:.5pt solid black;height:24.95pt;'>${data.rowNum}</td>
                            <td class=xl73>${data.partyName}</td>
                            <td class=xl73>${data.branchName}</td>
                            <td class=xl73>${data.branchTypeStr}</td>
                            <td class=xl73>${data.cadreName}</td>
                            <td class=xl73>${data.gender}</td>
                            <td class=xl73>${data.cadreNation}</td>
                            <td class=xl73>${data.growTime}</td>
                            <td class=xl73>${data.partyAge}</td>
                            <td class=xl73>${data.cadreBirth}</td>
                            <td class=xl73>${data.age}</td>
                            <td class=xl73>${data.cadreStatus}</td>
                            <td class=xl73>${data.cadreDuty}</td>
                            <td class=xl73>${data.formalNum}</td>
                            <td class=xl73>${data.preparedNum}</td>
                            <td class=xl73>${data.applyNum}</td>
                            <td class=xl73>${data.activistsNum}</td>
                            <td class=xl73>${data.developmentNum}</td>
                            <td class=xl73>${data.rowSum}</td>
                        </tr>
                    </c:forEach>
                    <tr height=33 style='mso-height-source:userset;height:24.95pt'>
                        <td height=33 class=xl73 style=' border-right:.5pt solid black;height:24.95pt'>总计</td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'></td>
                        <td class=xl73 style='border-left:none'>${totalFormal}</td>
                        <td class=xl73 style='border-left:none'>${totalPreparedNum}</td>
                        <td class=xl73 style='border-left:none'>${totalApply}</td>
                        <td class=xl73 style='border-left:none'>${totalActivists}</td>
                        <td class=xl73 style='border-left:none'>${totalDevelopment}</td>
                        <td class=xl73 style='border-left:none'>${allNum}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<style>
    col
    {mso-width-source:auto;
        mso-ruby-visibility:none;}
    br
    {mso-data-placement:same-cell;}
    ruby
    {ruby-align:left;}

    td
    {mso-style-parent:style0;
        padding-top:1px;
        padding-right:1px;
        padding-left:1px;
        mso-ignore:padding;
        color:black;
        font-size:8.0pt;
        font-weight:200;
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

    .xl73
    {mso-style-parent:style0;
        font-size:8.0pt;
        font-weight:300;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}

    .xl99
    {mso-style-parent:style0;
        font-size:20.0pt;
        font-weight:700;
        font-family:华文中宋;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        text-align:center;
        vertical-align:middle;}
    .xl100
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border-top:none;
        border-right:none;
        border-bottom:.5pt solid windowtext;
        border-left:none;}
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

    td
    {mso-style-parent:style0;
        padding-top:1px;
        padding-right:1px;
        padding-left:1px;
        mso-ignore:padding;
        color:black;
        font-size:10.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:"Times New Roman", serif;
        mso-font-charset:0;
        mso-number-format:General;
        text-align:left;
        vertical-align:top;
        border:none;
        mso-background-source:auto;
        mso-pattern:auto;
        mso-protection:locked visible;
        white-space:nowrap;
        mso-rotate:0;}

    .xl73
    {mso-style-parent:style0;
        vertical-align:middle;
        border:.5pt solid black;
        white-space:normal;}

</style>
<script>
    $('[data-rel="select2"]').change(function () {
        $.post("${ctx}/stat/partySum?cls=1", {partyId: this.value} , function (html) {
            $("#cartogram").replaceWith(html);
        });
    });
    $('[data-rel="select2"]').select2();

    $(".unit-group").click(function () {
        $("#searchForm input[name=cls]").val($(this).data('type'));
        $(".jqSearchBtn").click();
    })
</script>