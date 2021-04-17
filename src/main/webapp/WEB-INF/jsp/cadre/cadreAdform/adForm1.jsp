<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<style type="text/css">.b1{white-space-collapsing:preserve;}
.b2{margin: 0.4722222in 0.5395833in 0.39375in 0.39375in;}
.s1{font-weight:bold;}
.s2{color:black;}
.p1{text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:22pt;}
.p2{text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p3{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p4{margin-left:0.07847222in;margin-right:0.07847222in;text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p5{text-indent:-1.4166666in;margin-left:1.4166666in;text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p6{text-indent:-1.2583333in;margin-left:1.2583333in;text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p7{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
.p8{margin-left:-0.07083333in;text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p9{margin-left:-0.06944445in;text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p10{margin-left:-0.06944445in;text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:9pt;}
.p11{margin-left:-0.06944445in;text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
.p12{margin-left:-0.07083333in;text-align:center;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
.p13{margin-right:0.24930556in;text-align:end;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p14{margin-right:0.12361111in;margin-top:0.108333334in;text-align:end;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p15{margin-right:0.27430555in;text-align:end;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p16{margin-right:0.14861111in;margin-top:0.108333334in;text-align:end;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p17{margin-right:0.2986111in;text-align:end;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p18{margin-right:0.175in;margin-top:0.108333334in;text-align:end;hyphenate:auto;font-family:Times New Roman;font-size:12pt;}
.p19{text-indent:0.072916664in;margin-top:0.108333334in;text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}
.td0{padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td1{width:0.89513886in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td2{width:0.875in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td3{width:1.125in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td4{width:1.8498611in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td5{width:1.25in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td6{width:1.75in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td7{width:2.354861in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td8{width:1.0in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td9{width:2.604861in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td10{width:1.5in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td11{width:5.625in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td12{width:6.2298613in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td13{width:0.85486114in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td14{width:0.75in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td15{/*width:3.0in;*/max-width: 100px;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td16{width:2.25in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td17{width:2.5in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td18{/*width:2.375in;*/padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td19{width:3.5in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.td20{width:3.395in;padding-start:0.075in;padding-end:0.075in;border-bottom:thin solid black;border-left:thin solid black;border-right:thin solid black;border-top:thin solid black;}
.r1{keep-together:always;}
.r2{height:0.35416666in;}
.t1{table-layout:fixed;border-collapse:collapse;border-spacing:0;min-width: 706px}

td.center {
  text-align: center
}
td.padding10{
  padding-left: 10px;
}
td.bolder{font-weight: bolder}

  .resume p {
 text-indent: -9em;
 margin: 0 0 0 9em;
}
  .reward p {
 margin: 0;
}
</style>
<c:if test="${param.mobile=='1'}">
    <button type="button"
            style="padding: 15px; position: absolute;right: 0;top: -10px;z-index: 2222"
            data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
</c:if>
<div id="preview">
<div style="float: left; margin-right: 20px; padding-bottom: 20px;">
  <table class="t1">
    <tbody>
    <tr>
      <td colspan="9">
        <p class="p1">
          <span class="s1">干部任免审批表</span>
        </p>
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder">
        <span>姓  名</span>
      </td>
      <td class="td0 center" colspan="2">
        ${bean.realname}
      </td>
      <td class="td2 center bolder">
        <span class="s2">性  别</span>
      </td>
      <td class="td3 center">
        ${GENDER_MAP.get(bean.gender)}
      </td>
      <td class="td3 center bolder" colspan="2" style="font-size: smaller">
        <span>出生年月</span>
        <div><span>（  岁）</span></div>
      </td>
      <td class="td3 center" style="font-size: smaller">
        ${cm:formatDate(bean.birth, "yyyy.MM")}
        <div><span>（${bean.age}岁）</span></div>
      </td>
      <td class="td5 center" rowspan="4">
        <img src="data:image/jpeg;base64,${bean.avatar}" width="100%"/>
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder" style="height: 38px">
        <span>民  族</span>
      </td>
      <td class="td0 center" colspan="2">
        ${bean.nation}
      </td>
      <td class="td2 center bolder">
        <span class="s2">籍  贯</span>
      </td>
      <td class="td2 center">
        ${bean.nativePlace}
      </td>
      <td class="td3 center bolder" colspan="2">
        <span>出生地</span>
      </td>
      <td class="td3 center">
        ${bean.homeplace}
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder" style="font-size: smaller">
        <span>入  党</span>
        <div>
          <span>时  间</span>
        </div>
      </td>
      <td class="td0 center" colspan="2">
        <c:if test="${bean.dpTypeId>0}">
          <c:if test="${not empty bean.owGrowTime}">${cm:formatDate(bean.owGrowTime, "yyyy.MM")}；</c:if>${cm:getMetaType(bean.dpTypeId).extraAttr}<c:forEach items="${bean.dpParties}" var="dpParty">；${cm:getMetaType(dpParty.classId).extraAttr}</c:forEach>
        </c:if>
        <c:if test="${empty bean.dpTypeId}">
          <c:if test="${not empty bean.owGrowTime}">
            ${cm:formatDate(bean.owGrowTime, "yyyy.MM")}
          </c:if>
        </c:if>
      </td>
      <td class="td2 center bolder" style="font-size: smaller">
        <span class="s2">参加工作</span>
        <div>时间</div>
      </td>
      <td class="td2 center">
        ${cm:formatDate(bean.workTime, "yyyy.MM")}
      </td>
      <td class="td3 center bolder" colspan="2">
        <span>健康状况</span>
      </td>
      <td class="td3 center">
        ${bean.health}
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder" style="font-size: smaller">
        <span>专业技</span>
        <div>
          <span>术职务</span>
        </div>
      </td>
      <td class="td5 center" colspan="3">
        ${bean.proPost}
      </td>
      <td class="td2 center bolder" style="font-size: smaller">
        <span>熟悉专业</span>
        <div>
          <span>有何专长</span>
        </div>
      </td>
      <td class="td7 center" colspan="3">
        ${bean.specialty}
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder" rowspan="2">
        <span>学  历</span>
        <div>
          <span>学  位</span>
        </div>
      </td>
      <td class="td0 center bolder" colspan="2">
        <span>全日制</span>
        <div>
          <span>教    育</span>
        </div>
      </td>
      <td class="td6 padding10" colspan="2" style="text-align: center">
        ${bean.edu}
        <br/>
        ${bean.degree}
      </td>
      <td class="td8 center bolder">

        <span>毕业院校</span>
        <div>
          <span>系及专业</span>
        </div>
      </td>
      <td class="td9 padding10" colspan="3" style="text-align: center">
          ${bean.schoolDepMajor1}${bean.sameSchool?'':'<br/>'}${bean.schoolDepMajor2}
      </td>
    </tr>
    <tr>
      <td class="td0 center bolder" colspan="2">
        <span>在  职</span>
        <div>
          <span>教  育</span>
        </div>
      </td>
      <td class="td6 padding10" colspan="2" style="text-align: center">
        ${bean.inEdu}
        <br/>
        ${bean.inDegree}
      </td>
      <td class="td8 center bolder">
        <span>毕业院校</span>
        <div>
          <span>系及专业</span>
        </div>
      </td>
      <td class="td9 padding10" colspan="3" style="text-align: center">
        ${bean.inSchoolDepMajor1}${bean.sameInSchool?'':'<br/>'}${bean.inSchoolDepMajor2}
      </td>
    </tr>
    <tr class="r1">
      <td class="td10 center bolder" colspan="2" style="height: 50px">
        <span>现 任 职 务</span>
      </td>
      <td class="td11 padding10" colspan="7">
        ${bean.post}
      </td>
    </tr>
    <tr class="r1">
      <td class="td10 center bolder" colspan="2" style="height: 50px">
        <span>拟 任 职 务</span>
      </td>
      <td class="td11 padding10" colspan="7">
        ${bean.inPost}
      </td>
    </tr>
    <tr class="r1">
      <td class="td10 center bolder" colspan="2" style="height: 50px">
        <span>拟 免 职 务</span>
      </td>
      <td class="td11 padding10" colspan="7">
        ${bean.prePost}
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder"  style="height: 460px">
        <span>简</span>
        <div style="margin-top: 100px">
          历
        </div>
      </td>
      <td class="td12 padding10 resume" colspan="8" style="vertical-align: top;line-height: 25px">
        <%--<c:if test="${empty bean.resumeDesc}">
        <c:if test="${not empty bean.learnDesc}">
          <p style="font-weight: bolder;">学习经历：</p>
          <p>
              ${bean.learnDesc}
          </p>
        </c:if>
        <c:if test="${not empty bean.workDesc}">
          <p style="font-weight: bolder;">工作经历：</p>
          <p>
              ${bean.workDesc}
          </p>
        </c:if>
        </c:if>--%>
          ${bean.resumeDesc}
      </td>
    </tr>
    </tbody>
  </table>
</div>

<div style="padding-top: 53px">
  <table class="t1">
    <tbody>
    <tr>
      <td class="td1 center bolder" style="height: 100px;">
        <span>奖&nbsp;&nbsp;情</span>
        <div>
          <span>惩&nbsp;&nbsp;况</span>
        </div>
      </td>
      <td class="td12 padding10 reward" colspan="8">
        ${bean.reward}
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder" style="height: 80px;">
        <span>年&nbsp;&nbsp;核</span>
        <div>
          <span>度&nbsp;&nbsp;结</span>
        </div>
        <div>
          <span>考&nbsp;&nbsp;果</span>
        </div>
      </td>
      <td class="td12 padding10" colspan="8">
        ${bean.ces}
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder" style="height: 150px;">
        <span>培&nbsp;&nbsp;情</span>
        <div>
          <span>训&nbsp;&nbsp;况</span>
        </div>
      </td>
      <td class="td12 padding10" colspan="8">
        ${bean.trainDesc}
      </td>
    </tr>
    <tr>
      <td class="td1 center bolder"  style="height: 50px;">
        <span>任&nbsp;&nbsp;理</span>
        <div>
          <span>免&nbsp;&nbsp;由</span>
        </div>
      </td>
      <td class="td12 padding10" colspan="8">
        ${bean.reason}
      </td>
    </tr>
    <tr class="r2">
      <c:set var="familyCount" value="${fn:length(bean.cadreFamilys)}"/>
      <td class="td1 center bolder" rowspan="${familyCount<6?7:(familyCount+1)}">
        <span>主&nbsp;&nbsp;及</span>
        <div>
          <span>要&nbsp;&nbsp;社</span>
        </div>
        <div>
          <span>家&nbsp;&nbsp;会</span>
        </div>
        <div>
          <span>庭&nbsp;&nbsp;关</span>
        </div>
        <div>
          <span>成&nbsp;&nbsp;系</span>
        </div>
        <div>
          <span>员&nbsp;&nbsp;&nbsp;&nbsp;</span>
        </div>
      </td>
      <td class="td2 center bolder">

        <span>称  谓</span>
      </td>

      <td class="td2 center bolder" colspan="2">
        <span>姓  名</span>
      </td>
      <td class="td2 center bolder">
        <span>年龄</span>
      </td>
      <td class="td2 center bolder" colspan="2">
        <span>政  治</span>

        <div>
          <span>面  貌</span>
        </div>
      </td>
      <td class="td15 center bolder" colspan="2">
        <span>工 作 单 位 及 职 务</span>
      </td>
    </tr>
    <c:forEach items="${bean.cadreFamilys}" var="f">
      <tr class="r2">
        <td class="td13 center">
            ${cm:getMetaType(f.title).name}
        </td>
        <td class="td14 center" colspan="2">
            ${f.realname}
        </td>
        <td class="td14 center">
          <c:if test="${f.birthday!=null}">
            ${cm:intervalYearsUntilNow(cm:getFirstDayOfMonth(f.birthday))}
          </c:if>
        </td>
        <td class="td2 center" colspan="2">
            ${cm:getMetaType(f.getPoliticalStatus()).name}
        </td>
        <td class="td15 padding10" colspan="2">
            ${f.unit}
        </td>
      </tr>
    </c:forEach>
    <c:if test="${familyCount<6}">
    <c:forEach begin="0" end="${5-familyCount}">
      <tr class="r2">
        <td class="td13">
          <p class="p8"></p>
        </td>
        <td class="td14" colspan="2">
          <p class="p8"></p>
        </td>
        <td class="td14">
          <p class="p8"></p>
        </td>
        <td class="td2" colspan="2">
          <p class="p8"></p>
        </td>
        <td class="td15" colspan="2">
          <p class="p9"></p>
        </td>
      </tr>
    </c:forEach>
    </c:if>

    <tr>
      <td class="td16 center bolder" colspan="3" style="height: 30px">
        <span>呈报或提议单位意见</span>
      </td>
      <td class="td17 center bolder" colspan="4">
        <span>试任职审批机关意见</span>
      </td>
      <td class="td18 center bolder" colspan="2">
        <span>试任职行政任免机关意见</span>
      </td>
    </tr>
    <tr>
      <td class="td16" colspan="3">
        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p13">
          <span>盖  章</span>
        </p>

        <p class="p14">
          <%--<span>${cm:formatDate(bean.reportDate, "yyyy年MM月dd日")}</span>--%>
          <span>年  月  日</span>
        </p>
      </td>
      <td class="td17" colspan="4">
        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p15">
          <span>盖  章</span>
        </p>

        <p class="p16">
          <span>年  月  日</span>
        </p>
      </td>
      <td class="td18" colspan="2">
        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p17">
          <span>盖  章</span>
        </p>

        <p class="p18">
          <span>年  月  日</span>
        </p>
      </td>
    </tr>
    <tr>
      <td class="td19 center bolder" colspan="6"  style="height: 30px">
        <span>正式任职审批机关意见</span>
      </td>
      <td class="td20 center bolder" colspan="3">
        <span>正式任职行政任免机关意见</span>
      </td>
    </tr>
    <tr>
      <td class="td19" colspan="6">
        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p17"></p>

        <p class="p17">
          <span>盖  章</span>
        </p>

        <p class="p18">
          <span>年  月  日</span>
        </p>
      </td>
      <td class="td20" colspan="3">
        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p3"></p>

        <p class="p17"></p>

        <p class="p17">
          <span>盖  章</span>
        </p>

        <p class="p18">
          <span>年  月  日</span>
        </p>
      </td>
    </tr>
    <tr>
      <td colspan="9" style="height: 30px">
        <span>填表人_____________</span>
      </td>
    </tr>
    </tbody>
  </table>
</div>
</div>
<c:if test="${param.mobile=='1'}">
  <script>
    window.setTimeout(function() {
      //console.log($("#preview table").width())
      //console.log(window.screen.availWidth)
      var r = (window.screen.availWidth-20) / $("#preview table").width();
      $("#preview").css("-webkit-transform","scale(" + r + ")")
              .css("transform-origin","left top").css("padding", "20px");
      //console.log($("#body-content-view").height())
      //console.log($("#preview").height())
      $("#preview").css("margin-bottom", -1*$("#preview").height()*(1-r) + "px");
      //$("#btn-scroll-up").click();
      $(".modal-dialog").css("margin", "0")
    }, 400);

  </script>
</c:if>