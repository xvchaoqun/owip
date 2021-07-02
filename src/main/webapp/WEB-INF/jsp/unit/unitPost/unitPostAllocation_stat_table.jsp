<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table border=0 cellpadding=0 cellspacing=0  class=xl632001
       style='border-collapse:collapse;table-layout:fixed;'>
  <col class=xl632001 width=42 style='mso-width-source:userset;mso-width-alt:
 1344;width:32pt'>
  <col class=xl692001 width=201 style='mso-width-source:userset;mso-width-alt:
 6432;width:151pt'>
  <col class=xl692001 width=54 span=4 style='mso-width-source:userset;
 mso-width-alt:1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=54 style='mso-width-source:userset;mso-width-alt:
 1728;width:41pt'>
  <col class=xl632001 width=71 style='width:53pt'>
  <col class=xl632001 width=71 span=3 style='width:53pt'>
  <tr height=75 style='mso-height-source:userset;height:56.25pt'>
    <td colspan=${cadreCategory==CADRE_CATEGORY_CJ?18:14} height=75 class=xl822001 width=1107 style='height:56.25pt;
  width:839pt'>${_school}内设机构${CADRE_CATEGORY_MAP.get(cadreCategory)}配备统计表</td>

  </tr>
  <tr height=45 style='mso-height-source:userset;height:33.75pt'>
    <td colspan=${cadreCategory==CADRE_CATEGORY_CJ?18:14} height=45 class=xl832001 width=1107 style='height:33.75pt;
  width:839pt'>统计日期：${cm:formatDate(now,'yyyy年MM月dd日')}</td>

  </tr>
  <tr class=xl652001 height=37 style='mso-height-source:userset;height:27.75pt'>
    <td rowspan=3 height=111 class=xl762001 width=42 style='border-bottom:1pt solid black;
  height:83.25pt;border-top:none;width:32pt'>序号</td>
    <td rowspan=3 class=xl762001 width=201 style='border-bottom:1pt solid black;
  border-top:none;width:151pt'>单位类型</td>
    <td colspan=4 class=xl792001 width=216 style='border-right:2.0pt double black;
  border-left:none;width:164pt'>所有岗位</td>
    <td colspan=4 class=xl802001 width=216 style='border-right:2.0pt double black;
  width:164pt'>${cadreCategory==CADRE_CATEGORY_CJ?"正处":"正科"}级岗位</td>
    <td colspan=4 class=xl892001 width=216 style='border-right:2.0pt double black;
  border-left:none;width:164pt'>${cadreCategory==CADRE_CATEGORY_CJ?"副处":"副科"}级岗位</td>
    <c:if test="${cadreCategory==CADRE_CATEGORY_CJ}">
    <td colspan=4 class=xl882001 width=216 style='border-right:1pt solid black;
  border-left:none;width:164pt'>${_p_label_adminLevelNone}岗位</td>
    </c:if>
  </tr>
  <tr class=xl652001 height=37 style='mso-height-source:userset;height:27.75pt'>
    <td rowspan=2 height=74 class=xl762001 width=54 style='border-bottom:1pt solid black;
  height:55.5pt;border-top:none;width:41pt'>干部<br>
      职数</td>
    <td colspan=2 class=xl792001 width=108 style='
  border-left:none;width:82pt'>在职岗位数</td>
    <td rowspan=2 class=xl742001 width=54 style='border-bottom:1pt solid black;
  border-top:none;width:41pt'>空缺<br>
      岗位数</td>
    <td rowspan=2 class=xl762001 width=54 style='border-bottom:1pt solid black;
  border-top:none;width:41pt'>干部<br>
      职数</td>
    <td colspan=2 class=xl792001 width=108 style='
  border-left:none;width:82pt'>在职岗位数</td>
    <td rowspan=2 class=xl742001 width=54 style='border-bottom:1pt solid black;
  border-top:none;width:41pt'>空缺<br>
      岗位数</td>
    <td rowspan=2 class=xl762001 width=54 style='border-bottom:1pt solid black;
  border-top:none;width:41pt'>干部<br>
      职数</td>
    <td colspan=2 class=xl792001 width=108 style='
  border-left:none;width:82pt'>在职岗位数</td>
    <td rowspan=2 class=xl742001 width=54 style='border-bottom:1pt solid black;
  border-top:none;width:41pt'>空缺<br>
      岗位数</td>
    <c:if test="${cadreCategory==CADRE_CATEGORY_CJ}">
    <td rowspan=2 class=xl762001 width=54 style='border-bottom:1pt solid black;
  border-top:none;width:41pt'>干部<br>
      职数</td>
    <td colspan=2 class=xl792001 width=108 style='
  border-left:none;width:82pt'>在职岗位数</td>
    <td rowspan=2 class=xl752001 width=54 style='border-bottom:1pt solid black;
  border-top:none;width:41pt'>空缺<br>
      岗位数</td>
    </c:if>
  </tr>
  <tr class=xl652001 height=37 style='mso-height-source:userset;height:27.75pt'>
    <td height=37 class=xl712001 width=54 style='height:27.75pt;border-top:none;
  border-left:none;width:41pt'>全职</td>
    <td class=xl712001 width=54 style='border-top:none;border-left:none;
  width:41pt'>兼职</td>
    <td class=xl712001 width=54 style='border-top:none;border-left:none;
  width:41pt'>全职</td>
    <td class=xl712001 width=54 style='border-top:none;border-left:none;
  width:41pt'>兼职</td>
    <td class=xl712001 width=54 style='border-top:none;border-left:none;
  width:41pt'>全职</td>
    <td class=xl712001 width=54 style='border-top:none;border-left:none;
  width:41pt'>兼职</td>
    <c:if test="${cadreCategory==CADRE_CATEGORY_CJ}">
    <td class=xl712001 width=54 style='border-top:none;border-left:none;
  width:41pt'>全职</td>
    <td class=xl712001 width=54 style='border-top:none;border-left:none;
  width:41pt'>兼职</td>
    </c:if>
  </tr>
  <c:forEach items="${cpcStatDataMap}" var="entity" varStatus="gvs">
    <c:if test="${entity.key!='total'}">
    <tr height=64 style='mso-height-source:userset;height:48.0pt'>
      <td height=64 class=xl642001 width=42 style='height:48.0pt;border-top:none;
        width:32pt'>${gvs.count}</td>
      <td class=xl682001 width=201 style='border-top:none;border-left:none;
        width:151pt'>${unitTypeGroupMap.get(entity.key).detail}</td>
      <t:cpc_stat dataList="${entity.value}" unitType="${entity.key}"/>
    </tr>
    </c:if>
    <c:if test="${entity.key=='total'}">
    <tr height=64 style='mso-height-source:userset;height:48.0pt'>
      <td colspan=2 height=64 class=xl842001 width=243 style='border-right:1pt solid black;
        height:48.0pt;width:183pt'>合计</td>
      <c:forEach items="${entity.value}" var="data" varStatus="vs">
        <td class=${((vs.index+1)%4==0 && !vs.first && !vs.last)?'xl672001':'xl662001'} width=54>${data}</td>
      </c:forEach>
    </tr>
    </c:if>
  </c:forEach>
  <tr height=38 style='mso-height-source:userset;height:28.5pt'>
    <td colspan=14 height=38 class=xl852001 width=1107 style='height:28.5pt;
  width:839pt'>注：“在职岗位数”中的“兼职”是指占干部职数的兼职干部。</td>

  </tr>
</table>
<style>
  <!--table
  {mso-displayed-decimal-separator:"\.";
    mso-displayed-thousand-separator:"\,";}
  .font52001
  {color:windowtext;
    font-size:9.0pt;
    font-weight:400;
    font-style:normal;
    text-decoration:none;
    font-family:等线;
    mso-generic-font-family:auto;
    mso-font-charset:134;}
  .xl632001
  {padding-top:1px;
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
    text-align:center;
    vertical-align:middle;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl642001
  {padding-top:1px;
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
    text-align:center;
    vertical-align:middle;
    border:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl652001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl662001
  {padding-top:1px;
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
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:1pt solid windowtext;
    border-bottom:1pt solid windowtext;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl672001
  {padding-top:1px;
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
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:2.0pt double windowtext;
    border-bottom:1pt solid windowtext;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl682001
  {padding-top:1px;
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
    text-align:left;
    vertical-align:middle;
    border:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl692001
  {padding-top:1px;
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
    text-align:left;
    vertical-align:middle;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl702001
  {padding-top:1px;
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
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:none;
    border-bottom:1pt solid windowtext;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl712001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl722001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:1pt solid windowtext;
    border-bottom:none;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl732001
  {padding-top:1px;
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
    text-align:left;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:2.0pt double windowtext;
    border-bottom:1pt solid windowtext;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl742001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:2.0pt double windowtext;
    border-bottom:none;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl752001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:none;
    border-right:1pt solid windowtext;
    border-bottom:1pt solid windowtext;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl762001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:1pt solid windowtext;
    border-bottom:none;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl772001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:none;
    border-right:1pt solid windowtext;
    border-bottom:none;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl782001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:none;
    border-right:1pt solid windowtext;
    border-bottom:1pt solid windowtext;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl792001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:none;
    border-bottom:1pt solid windowtext;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl802001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:1pt solid windowtext;
    border-bottom:1pt solid windowtext;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl812001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:2.0pt double windowtext;
    border-bottom:1pt solid windowtext;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl822001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size: 24.0pt;
    font-weight: bolder;
    font-style:normal;
    text-decoration:none;
    font-family:方正小标宋简体, monospace;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl832001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:12.0pt;
    font-weight:400;
    font-style:normal;
    text-decoration:none;
    font-family:方正小标宋简体, monospace;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:right;
    vertical-align:middle;
    border-top:none;
    border-right:none;
    border-bottom:1pt solid windowtext;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl842001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:none;
    border-bottom:none;
    border-left:1pt solid windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl852001
  {padding-top:1px;
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
    text-align:left;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:none;
    border-bottom:none;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl862001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:none;
    border-bottom:1pt solid windowtext;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl872001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:2.0pt double windowtext;
    border-bottom:1pt solid windowtext;
    border-left:none;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl882001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:none;
    border-bottom:1pt solid windowtext;
    border-left:2.0pt double windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  .xl892001
  {padding-top:1px;
    padding-right:1px;
    padding-left:1px;
    mso-ignore:padding;
    color:black;
    font-size:11.0pt;
    font-weight:700;
    font-style:normal;
    text-decoration:none;
    font-family:宋体;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-number-format:General;
    text-align:center;
    vertical-align:middle;
    border-top:1pt solid windowtext;
    border-right:1pt solid windowtext;
    border-bottom:1pt solid windowtext;
    border-left:2.0pt double windowtext;
    mso-background-source:auto;
    mso-pattern:auto;
    white-space:normal;}
  ruby
  {ruby-align:left;}
  rt
  {color:windowtext;
    font-size:9.0pt;
    font-weight:400;
    font-style:normal;
    text-decoration:none;
    font-family:等线;
    mso-generic-font-family:auto;
    mso-font-charset:134;
    mso-char-type:none;}
  -->
</style>