<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<style type="text/css">.b1 {
    white-space-collapsing: preserve;
}

.b2 {
    margin: 0.4722222in 0.59097224in 0.4722222in 0.59097224in;
}

.s1 {
    font-weight: bold;
}

.s2 {
    text-decoration: underline;
}

.s3 {
    font-family: 宋体;
}

.p1 {
    margin-top: 0.11319444in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 22pt;
}

.p2 {
    margin-bottom: 0.027777778in;
    text-align: start;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p3 {
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p4 {
    text-align: center;
    hyphenate: auto;
    font-family: 宋体;
    font-size: 12pt;
}

.p5 {
    margin-left: 0.0034722222in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p6 {
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 10pt;
}

.p7 {
    margin-left: 0.0034722222in;
    text-align: center;
    hyphenate: auto;
    font-family: 宋体;
    font-size: 12pt;
}

.p8 {
    text-align: center;
    hyphenate: auto;
    font-family: 宋体;
    font-size: 10pt;
}

.p9 {
    margin-left: 0.0048611113in;
    text-align: center;
    hyphenate: auto;
    font-family: 宋体;
    font-size: 12pt;
}

.p10 {
    margin-left: -0.07083333in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p11 {
    margin-left: -0.07083333in;
    text-align: center;
    hyphenate: auto;
    font-family: 宋体;
    font-size: 12pt;
}

.p12 {
    margin-left: 0.021527778in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p13 {
    margin-left: 0.023611112in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p14 {
    margin-left: 0.021527778in;
    text-align: start;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p15 {
    text-align: center;
    hyphenate: auto;
    font-family: Verdana;
    font-size: 12pt;
}

.p16 {
    text-align: justify;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p17 {
    text-align: justify;
    hyphenate: auto;
    font-family: 宋体;
    font-size: 12pt;
}

.p18 {
    margin-left: 0.016666668in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p19 {
    margin-left: -0.06944445in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p20 {
    margin-left: 0.07847222in;
    margin-right: 0.07847222in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p21 {
    margin-left: 0.016666668in;
    margin-right: 0.07847222in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p22 {
    margin-left: 0.025in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p23 {
    margin-left: 0.020833334in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p24 {
    margin-left: 0.017361112in;
    text-align: center;
    hyphenate: auto;
    font-family: Times New Roman;
    font-size: 12pt;
}

.p25 {
    text-align: justify;
    hyphenate: auto;
    font-family: 楷体_GB2312;
    font-size: 10pt;
}

.p26 {
    margin-left: -0.06944445in;
    text-align: center;
    hyphenate: auto;
    font-family: 楷体_GB2312;
    font-size: 10pt;
}

.td1 {
    width: 0.91041666in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td2 {
    width: 0.9826389in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td3 { /*width:0.98194444in;*/
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td4 {
    width: 0.9847222in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td5 { /*width:0.9840278in;*/
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td6 {
    width: 1.2972223in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td7 {
    width: 1.9645833in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td8 {
    width: 1.96875in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td9 {
    width: 1.9666667in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td10 {
    width: 2.2819445in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td11 {
    width: 2.5493055in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td12 {
    width: 6.2152777in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td13 {
    width: 2.770139in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td14 {
    width: 2.4625in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td15 {
    width: 0.6875in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td16 {
    width: 0.6861111in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td17 {
    width: 0.7083333in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td18 {
    width: 0.68819445in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td19 {
    width: 3.445139in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td20 {
    width: 1.3965278in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td21 {
    width: 1.2715278in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td22 {
    width: 1.0881945in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.td23 {
    width: 1.0854167in;
    padding-start: 0.075in;
    padding-end: 0.075in;
    border-bottom: thin solid black;
    border-left: thin solid black;
    border-right: thin solid black;
    border-top: thin solid black;
}

.r1 {
    height: 0.43333334in;
}

.r2 {
    height: 0.7083333in;
}

.r3 {
    keep-together: always;
}

.r4 {
    height: 0.35416666in;
}

.t1 {
    table-layout: fixed;
    border-collapse: collapse;
    border-spacing: 0;
    width: 760px
}

td.center {
    text-align: center
}

td.padding10 {
    padding-left: 10px;
}

.bolder {
    font-weight: bolder
}

.resume p {
    text-indent: -9em;
    margin: 0 0 0 9em;
}
</style>
<div style="position: absolute; top:25px; left:10px;">
    <a href="javascript:;" class="downloadBtn btn btn-primary"
       data-url="${ctx}/cadreInfoFormSimple_download?cadreId=${param.cadreId}">
        <i class="ace-icon fa fa-download "></i>
        下载(WORD)
    </a>
</div>
<div>
    <div style="float: left; margin-right: 20px; padding-bottom: 20px">
        <table class="t1">
            <tbody>
            <tr>
                <td colspan="14">
                    <p class="p1">
                        <span class="s1">${_school}干部信息表(简版)</span>
                    </p>
                </td>
            </tr>

            <tr class="r1">
                <td class="td1 center bolder">
                    <span>姓  名</span>
                </td>
                <td class="td2 center" colspan="2">
                    ${bean.realname}</td>
                <td class="td3 center bolder" colspan="2">
                    <span>性  别</span>
                </td>
                <td class="td4 center" colspan="3">
                    ${GENDER_MAP.get(bean.gender)}
                </td>
                <td class="td5 center bolder" colspan="2">
                    <span>出生年月</span>
                </td>
                <td class="td4 center" colspan="2">
                    <c:if test="${not empty bean.birth}">
                        ${cm:formatDate(bean.birth, "yyyy.MM")}
                        <div>
                            <span>（${bean.age}岁）</span>
                        </div>
                    </c:if>
                </td>
                <td class="td6 center bolder" rowspan="3" colspan="2">
                    <img src="data:image/jpeg;base64,${bean.avatar}" width="100%"/>
                </td>
            </tr>
            <tr class="r1">
                <td class="td1 center bolder">
                    <span>民  族</span>
                </td>
                <td class="td2 center" colspan="2">
                    ${bean.nation}
                </td>
                <td class="td3 center bolder" colspan="2">
                    <span>籍  贯</span>
                </td>
                <td class="td4 center" colspan="3">
                    ${bean.nativePlace}
                </td>
                <td class="td5 center bolder" colspan="2">
                    <span>专业技<br/>术职务</span>
                </td>
                <td class="td4 center" colspan="2">
                    ${bean.proPost}
                </td>
            </tr>
            <tr class="r1">
                <td class="td1 center bolder">
                    <span>政治<br/>面貌</span>
                </td>
                <td class="td2 center" colspan="2">
                    ${cm:cadreParty(bean.isOw, bean.owGrowTime, bean.owPositiveTime, '中共党员', bean.dpTypeId, bean.dpGrowTime, false).get('partyName')}
                </td>
                <td class="td3 center bolder" colspan="2">
                    <span>参加工<br/>作时间</span>
                </td>
                <td class="td4 center" colspan="3">
                    ${cm:formatDate(bean.workTime, "yyyy.MM")}
                </td>
                <td class="td5 center bolder" colspan="2">
                    <span>熟悉专业<br/>有何特长</span>
                </td>
                <td class="td4 center" colspan="2">
                    ${bean.specialty}
                </td>
            </tr>
            <tr class="r1">
                <td class="td1 center bolder" rowspan="2">
                    <span>学  历<br/>学  位</span>
                </td>
                <td class="td2 center bolder" colspan="2">
                    <span>全日制</span>
                    <div><span>教   育</span></div>
                </td>
                <td class="td9 padding10" colspan="5">
                    ${bean.edu}<br/>${bean.degree}
                </td>
                <td class="td5 center bolder" colspan="2">
                    <span>毕业院校系</span>
                    <div>
                        <span>及专业</span>
                    </div>
                </td>
                <td class="td10 padding10" colspan="4">
                    ${bean.schoolDepMajor1}${bean.sameSchool?'':'<br/>'}${bean.schoolDepMajor2}
                </td>
            </tr>
            <tr class="r1">
                <td class="td2 center bolder" colspan="2">
                    <span>在  职</span>
                    <div><span>教  育</span></div>
                </td>
                <td class="td9 padding10" colspan="5">
                    ${bean.inEdu}
                    <br/>
                    ${bean.inDegree}
                </td>
                <td class="td5 center bolder" colspan="2">
                    <span>毕业院校系</span>
                    <div><span>及专业</span>
                    </div>
                </td>
                <td class="td10 padding10" colspan="4">
                    ${bean.inSchoolDepMajor1}${bean.sameInSchool?'':'<br/>'}${bean.inSchoolDepMajor2}
                </td>
            </tr>
            <tr class="r1">
                <td class="td1 center bolder" colspan="3">
                    现任职务
                </td>
                <td class="td11 padding10" colspan="11">
                    ${bean.title}
                </td>
            </tr>
            <tr class="r3"  style="height: 200px">
                <td class="td1 center bolder">
                    <span>简</span>
                    <div style="margin-top: 100px">
                        历
                    </div>
                </td>
                <td class="td12 padding10 resume" colspan="13" style="vertical-align: top">
                    <div style="white-space: nowrap;height:190px;text-overflow: ellipsis;overflow:hidden;display:block;">
                        <c:if test="${not empty bean.learnDesc}">
                            ${bean.learnDesc}
                        </c:if>
                        <c:if test="${not empty bean.workDesc}">
                            ${bean.workDesc}
                        </c:if>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div style="padding-top: 64px">
        <table class="t1">
            <tbody>
            <tr class="r4">
                <c:set var="familyCount" value="${fn:length(bean.cadreFamilys)}"/>
                <td class="td1 center bolder" rowspan="${familyCount<6?7:(familyCount+1)}">
                    <span>家<br/>庭<br/>主<br/>要<br/>成<br/>员<br/>及<br/>重<br/>要<br/>社<br/>会<br/>关<br/>系</span>

                </td>
                <td class="td15 center bolder">
                    <span>称  谓</span>
                </td>
                <td class="td16 center bolder" colspan="2">
                    <span>姓  名</span>
                </td>
                <td class="td17 center bolder" colspan="2">
                    <span>出生</span>
                    <div>
                        <span>年月</span>
                    </div>
                </td>
                <td class="td18 center bolder">
                    <span>政治</span>
                    <div>
                        <span>面貌</span>
                    </div>
                </td>
                <td class="td19 center bolder" colspan="6">
                    <span>工作单位及职务</span>
                </td>
            </tr>
            <c:forEach items="${bean.cadreFamilys}" var="f">
                <tr class="r4">
                    <td class="td15 center">
                            ${cm:getMetaType(f.title).name}
                    </td>
                    <td class="td16 center" colspan="2">
                            ${f.realname}
                    </td>
                    <td class="td17 center" colspan="2">
                            ${cm:formatDate(f.birthday, "yyyy.MM")}
                    </td>
                    <td class="td18 center">
                            ${cm:getMetaType(f.getPoliticalStatus()).name}
                    </td>
                    <td class="td19 padding10" colspan="6">
                            ${f.unit}
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${familyCount<=5}">
                <c:forEach begin="0" end="${5-familyCount}">
                    <tr class="r4">
                        <td class="td15">
                            <p class="p18"></p>
                        </td>
                        <td class="td16" colspan="2">
                            <p class="p18"></p>
                        </td>
                        <td class="td17" colspan="2">
                            <p class="p18"></p>
                        </td>
                        <td class="td18">
                            <p class="p18"></p>
                        </td>
                        <td class="td19" colspan="6">
                            <p class="p18"></p>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

