<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="mt_admin_level_main" value="mt_admin_level_main"/>
<c:set var="mt_admin_level_vice" value="mt_admin_level_vice"/>
<table border=0 cellpadding=0 cellspacing=0 width=1125 class=xl6324425
       style='border-collapse:collapse;table-layout:fixed;width:847pt'>
  <col class=xl6324425 width=32>
  <col class=xl7124425 width=151>
  <col class=xl6324425 width=42>
  <col class=xl6324425 width=57>
  <col class=xl7124425 width=116>
  <col class=xl6324425 width=57>
  <col class=xl6324425 width=42>
  <col class=xl6324425 width=57>
  <col class=xl7124425 width=242>
  <col class=xl6324425 width=57>
  <col class=xl6324425 width=57>
  <col class=xl6324425 width=116>
  <col class=xl7124425 width=57>
  <col class=xl6324425 width=116>
  <tr height=52>
    <td colspan=14 height=52 class=xl8224425 width=1068>${_school}内设机构${CADRE_TYPE_MAP.get(cadreType)}配备情况
    </td>
  </tr>
  <tr height=30>
    <td colspan=7 height=30 class=xl8324426 width=1068>
      统计日期：${cm:formatDate(now,'yyyy年MM月dd日')}
    </td>

        <td colspan=7 height=30 class=xl8324425 width=1068>
          注：<span class="notCpc">(兼职)</span>
        </td>

  </tr>
  <tr class=xl6524425 height=30>
    <td rowspan=3 height=90 class=xl6624425 width=32>序号
    </td>
    <td rowspan=3 class=xl6624425 width=200>单<span
            style='mso-spacerun:yes'>&nbsp; </span>位
    </td>
    <td colspan=4 class=xl6624425 width=272>正处级干部
    </td>
    <td colspan=4 class=xl8524425 width=398>副处级干部
    </td>
    <td colspan=4 class=xl8024425 width=317 style='border-right: 2.0pt double black;'>
      其他领导干部（不占职数）
    </td>
  </tr>
  <tr class=xl6524425 height=30 style='mso-height-source:userset;height:22.9pt'>
    <td rowspan=2 height=60 class=xl6624425 width=42 style='height:45.8pt;
  border-top:none;width:32pt'>职数
    </td>
    <td colspan=2 class=xl6624425 width=173>现任干部情况</td>

    <td rowspan=2 class=xl7924425 width=57>空缺数</td>

    <td rowspan=2 class=xl7824425 width=42>职数</td>
    <td colspan=2 class=xl6624425 width=299 style="border-right: none;">现任干部情况</td>

    <td rowspan=2 class=xl7924425 width=57>空缺数</td>

    <td colspan=2 class=xl6624425 width=173>正处级</td>
    <td colspan=2 class=xl6624425 width=173 style="border-right: 2.0pt double windowtext;">副处级</td>


  </tr>
  <tr class=xl6524425 height=30>
    <td height=30 class=xl6624425 width=57>现任数
    </td>
    <td class=xl7024425 width=116>现任干部
    </td>

    <td class=xl6624425 width=57>现任数
    </td>
    <td class=xl7024425 width=242>现任干部
    </td>

    <td class=xl6624425 width=57>现任数</td>
    <td class=xl7724425 width=116>现任干部</td>
    <td class=xl6624425 width=57>现任数</td>
    <td class=xl7724425 width=116 style="border-right: 2.0pt double windowtext;">现任干部</td>

  </tr>

  <c:forEach items="${beans}" var="bean" varStatus="vs">
    <c:if test="${!vs.last}">
      <tr height=40 style='mso-height-source:userset;height:30.0pt'>
        <td height=40 class=xl6424425 width=32>
        ${vs.index+1}</td>
        <td class=xl6924425 width=200>
        ${bean.unit.name}
        </td>
        <td class=xl6424425 width=42>
          <c:if test="${bean.mainNum==0}">0</c:if>
          <c:if test="${bean.mainNum>0}">
          <a href="javascript:;" class="popupBtn"
             data-url="${ctx}/unitPosts?unitId=${bean.unit.id}&adminLevel=${cm:getMetaTypeByCode(mt_admin_level_main).id}">
              ${bean.mainNum}
          </a>
          </c:if>
        </td>
        <td class=xl6424425 width=57>${bean.mainCount}</td>
        <td class=xl6924425 width=116>
          <t:cpc_cadres2 cadrePosts="${bean.mains}"/>
        </td>
        <td width=57 class="xl6824425" >
          <c:if test="${bean.mainLack==0}">0</c:if>
          <c:if test="${bean.mainLack<0}">
            <span class="badge badge-danger">${bean.mainLack}</span>
          </c:if>
          <c:if test="${bean.mainLack>0}">
            <a href="javascript:;" class="popupBtn"
               data-url="${ctx}/unitPosts?unitId=${bean.unit.id}&adminLevel=${cm:getMetaTypeByCode(mt_admin_level_main).id}&displayEmpty=1">
            <span class="badge badge-success">${bean.mainLack}</span>
            </a>
          </c:if>
        </td>

        <td class=xl6724425 width=42>
          <c:if test="${bean.viceNum==0}">0</c:if>
          <c:if test="${bean.viceNum>0}">
            <a href="javascript:;" class="popupBtn"
               data-url="${ctx}/unitPosts?unitId=${bean.unit.id}&adminLevel=${cm:getMetaTypeByCode(mt_admin_level_vice).id}">
                ${bean.viceNum}
            </a>
          </c:if>
            </td>
        <td class=xl6424425 width=57>${bean.viceCount}</td>
        <td class=xl6924425 width=242>
          <t:cpc_cadres2 cadrePosts="${bean.vices}"/>
        </td>
        <td width=57 class="xl6824425">
          <c:if test="${bean.viceLack==0}">0</c:if>
          <c:if test="${bean.viceLack<0}">
            <span class="badge badge-danger">${bean.viceLack}</span>
          </c:if>
          <c:if test="${bean.viceLack>0}">
            <a href="javascript:;" class="popupBtn"
               data-url="${ctx}/unitPosts?unitId=${bean.unit.id}&adminLevel=${cm:getMetaTypeByCode(mt_admin_level_vice).id}&displayEmpty=1">
              <span class="badge badge-success">${bean.viceLack}</span>
            </a>
          </c:if>
        </td>

        <td class=xl6424425 width=57>${bean.noCpcMainCount}</td>
        <td class=xl6924425 width=116>
          <t:cpc_cadres2 cadrePosts="${bean.noCpcMains}"/>
        </td>
        <td class=xl6424425 width=57>${bean.noCpcViceCount}</td>
        <td class=xl6924425 width=116 style="border-right: 2.0pt double windowtext;">
          <t:cpc_cadres2 cadrePosts="${bean.noCpcVices}"/>
        </td>

      </tr>
    </c:if>
    <c:if test="${vs.last}">
      <tr height=40>
        <td colspan='2' height=40 class=xl8424425 width=183>合<span
                style='mso-spacerun:yes'>&nbsp; </span>计
        </td>
        <td class=xl6424425 width=42>${bean.mainNum}</td>
        <td class=xl6424425 width=57>${bean.mainCount}</td>
        <td class=xl6924425 width=116>　</td>
        <td width=57 class="xl6824425">${bean.mainLack}</td>

        <td class=xl6724425 width=42>${bean.viceNum}</td>
        <td class=xl6424425 width=57>${bean.viceCount}</td>
        <td class=xl6924425 width=242>　</td>
        <td width=57 class="xl6824425">${bean.viceLack}</td>

        <td class=xl6424425 width=57>${bean.noCpcMainCount}</td>
        <td class=xl6424425 width=116></td>
        <td class=xl6424425 width=57>${bean.noCpcViceCount}</td>
        <td class=xl6824425 width=116></td>

      </tr>
    </c:if>
  </c:forEach>
</table>
<style>
  table {
    mso-displayed-decimal-separator: "\.";
    mso-displayed-thousand-separator: "\,";
  }

  .font524425 {
    color: windowtext;
    font-size: 9.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 等线;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
  }

  .font624425 {
    color: black;
    font-size: 8.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
  }

  .xl6324425 {
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
    text-align: center;
    vertical-align: middle;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl6424425 {
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
    text-align: center;
    vertical-align: middle;
    border: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl6524425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl6624425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl6724425 {
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
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: 1pt solid windowtext;
    border-bottom: 1pt solid windowtext;
    border-left: none;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl6824425 {
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
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: 2.0pt double windowtext;
    border-bottom: 1pt solid windowtext;
    border-left: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl6924425 {
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
    text-align: left;
    vertical-align: middle;
    border: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7024425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7124425 {
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
    text-align: left;
    vertical-align: middle;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7224425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 9.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: left;
    vertical-align: middle;
    border: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7324425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 7.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: left;
    vertical-align: middle;
    border: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7424425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 10.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: left;
    vertical-align: middle;
    border: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7524425 {
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
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: none;
    border-bottom: 1pt solid windowtext;
    border-left: none;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7624425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: 1pt solid windowtext;
    border-bottom: none;
    border-left: none;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7724425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: 1pt solid windowtext;
    border-bottom: none;
    border-left: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7824425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: 1pt solid windowtext;
    border-bottom: 1pt solid windowtext;
    border-left: none;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl7924425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: 2.0pt double windowtext;
    border-bottom: 1pt solid windowtext;
    border-left: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl8024425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: none;
    border-bottom: 1pt solid windowtext;
    border-left: 2.0pt double windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl8124425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: none;
    border-bottom: 1pt solid windowtext;
    border-left: none;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl8224425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 24.0pt;
    font-weight: bolder;
    font-style: normal;
    text-decoration: none;
    font-family: 方正小标宋简体, monospace;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl8324425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 12.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 方正小标宋简体, monospace;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: right;
    vertical-align: middle;
    border-top: none;
    border-right: none;
    border-bottom: 1pt solid windowtext;
    border-left: none;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }
  .xl8324426 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 12.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 方正小标宋简体, monospace;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: left;
    vertical-align: middle;
    border-top: none;
    border-right: none;
    border-bottom: 1pt solid windowtext;
    border-left: none;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl8424425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: none;
    border-bottom: 1pt solid windowtext;
    border-left: 1pt solid windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  .xl8524425 {
    padding-top: 1px;
    padding-right: 1px;
    padding-left: 1px;
    mso-ignore: padding;
    color: black;
    font-size: 11.0pt;
    font-weight: 700;
    font-style: normal;
    text-decoration: none;
    font-family: 宋体;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-number-format: General;
    text-align: center;
    vertical-align: middle;
    border-top: 1pt solid windowtext;
    border-right: 1pt solid windowtext;
    border-bottom: 1pt solid windowtext;
    border-left: 2.0pt double windowtext;
    mso-background-source: auto;
    mso-pattern: auto;
    white-space: normal;
  }

  ruby {
    ruby-align: left;
  }

  rt {
    color: windowtext;
    font-size: 9.0pt;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    font-family: 等线;
    mso-generic-font-family: auto;
    mso-font-charset: 134;
    mso-char-type: none;
  }
</style>