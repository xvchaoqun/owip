<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:;" class="hideView btn btn-xs btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
    </h4>

    <div class="widget-toolbar no-border">
      <ul class="nav nav-tabs">
        <li class="active">
          <a href="javascript:;">干部考察报告预览</a>
        </li>
      </ul>
    </div>
  </div>
  <div class="widget-body">
    <div class="widget-main">
      <style>
        <!--
        /* Font Definitions */
        @font-face
        {font-family:宋体;
          panose-1:2 1 6 0 3 1 1 1 1 1;
          mso-font-alt:SimSun;
          mso-font-charset:134;
          mso-generic-font-family:auto;
          mso-font-pitch:variable;
          mso-font-signature:3 680460288 22 0 262145 0;}
        @font-face
        {font-family:"Cambria Math";
          panose-1:2 4 5 3 5 4 6 3 2 4;
          mso-font-charset:1;
          mso-generic-font-family:roman;
          mso-font-pitch:variable;
          mso-font-signature:0 0 0 0 0 0;}
        @font-face
        {font-family:Calibri;
          panose-1:2 15 5 2 2 2 4 3 2 4;
          mso-font-charset:0;
          mso-generic-font-family:swiss;
          mso-font-pitch:variable;
          mso-font-signature:-536859905 -1073732485 9 0 511 0;}
        @font-face
        {font-family:"\@宋体";
          panose-1:2 1 6 0 3 1 1 1 1 1;
          mso-font-charset:134;
          mso-generic-font-family:auto;
          mso-font-pitch:variable;
          mso-font-signature:3 680460288 22 0 262145 0;}
        /* Style Definitions */
        p.MsoNormal, li.MsoNormal, div.MsoNormal
        {mso-style-unhide:no;
          mso-style-qformat:yes;
          mso-style-parent:"";
          margin:0cm;
          margin-bottom:.0001pt;
          text-align:justify;
          text-justify:inter-ideograph;
          mso-pagination:none;
          font-size:10.5pt;
          mso-bidi-font-size:11.0pt;
          font-family:"Calibri",sans-serif;
          mso-fareast-font-family:宋体;
          mso-bidi-font-family:"Times New Roman";
          mso-font-kerning:1.0pt;}
        p.MsoHeader, li.MsoHeader, div.MsoHeader
        {mso-style-unhide:no;
          mso-style-link:"页眉 字符";
          margin:0cm;
          margin-bottom:.0001pt;
          text-align:center;
          mso-pagination:none;
          tab-stops:center 207.65pt right 415.3pt;
          layout-grid-mode:char;
          border:none;
          mso-border-bottom-alt:solid windowtext .75pt;
          padding:0cm;
          mso-padding-alt:0cm 0cm 1.0pt 0cm;
          font-size:9.0pt;
          font-family:"Calibri",sans-serif;
          mso-fareast-font-family:宋体;
          mso-bidi-font-family:"Times New Roman";
          mso-font-kerning:1.0pt;}
        p.MsoFooter, li.MsoFooter, div.MsoFooter
        {mso-style-unhide:no;
          mso-style-link:"页脚 字符";
          margin:0cm;
          margin-bottom:.0001pt;
          mso-pagination:none;
          tab-stops:center 207.65pt right 415.3pt;
          layout-grid-mode:char;
          font-size:9.0pt;
          font-family:"Calibri",sans-serif;
          mso-fareast-font-family:宋体;
          mso-bidi-font-family:"Times New Roman";
          mso-font-kerning:1.0pt;}
        p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
        {mso-style-unhide:no;
          mso-style-qformat:yes;
          margin:0cm;
          margin-bottom:.0001pt;
          text-align:justify;
          text-justify:inter-ideograph;
          text-indent:21.0pt;
          mso-char-indent-count:2.0;
          mso-pagination:none;
          font-size:10.5pt;
          mso-bidi-font-size:11.0pt;
          font-family:"Calibri",sans-serif;
          mso-fareast-font-family:宋体;
          mso-bidi-font-family:"Times New Roman";
          mso-font-kerning:1.0pt;}
        span.a
        {mso-style-name:"页眉 字符";
          mso-style-unhide:no;
          mso-style-locked:yes;
          mso-style-parent:"";
          mso-style-link:页眉;
          mso-ansi-font-size:9.0pt;
          mso-bidi-font-size:9.0pt;}
        span.a0
        {mso-style-name:"页脚 字符";
          mso-style-unhide:no;
          mso-style-locked:yes;
          mso-style-parent:"";
          mso-style-link:页脚;
          mso-ansi-font-size:9.0pt;
          mso-bidi-font-size:9.0pt;}
        .MsoChpDefault
        {mso-style-type:export-only;
          mso-default-props:yes;
          mso-ascii-font-family:Calibri;
          mso-fareast-font-family:宋体;
          mso-hansi-font-family:Calibri;}
        /* Page Definitions */
        @page
        {mso-page-border-surround-header:no;
          mso-page-border-surround-footer:no;
          mso-footnote-separator:url("考察材料.files/header.htm") fs;
          mso-footnote-continuation-separator:url("考察材料.files/header.htm") fcs;
          mso-endnote-separator:url("考察材料.files/header.htm") es;
          mso-endnote-continuation-separator:url("考察材料.files/header.htm") ecs;}
        @page WordSection1
        {size:595.3pt 841.9pt;
          margin:2.0cm 2.0cm 2.0cm 2.0cm;
          mso-header-margin:42.55pt;
          mso-footer-margin:25.5pt;
          mso-footer:url("考察材料.files/header.htm") f1;
          mso-paper-source:0;
          layout-grid:15.6pt;}
        div.WordSection1
        {page:WordSection1;}
        /* List Definitions */
        @list l0
        {mso-list-id:63457669;
          mso-list-type:hybrid;
          mso-list-template-ids:1541555328 1664284524 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}
        @list l0:level1
        {mso-level-number-format:japanese-counting;
          mso-level-text:%1、;
          mso-level-tab-stop:none;
          mso-level-number-position:left;
          text-indent:-36.0pt;
          mso-ansi-language:EN-US;}
        @list l0:level2
        {mso-level-number-format:alpha-lower;
          mso-level-text:"%2\)";
          mso-level-tab-stop:none;
          mso-level-number-position:left;
          margin-left:42.0pt;
          text-indent:-21.0pt;}
        @list l0:level3
        {mso-level-number-format:roman-lower;
          mso-level-tab-stop:none;
          mso-level-number-position:right;
          margin-left:63.0pt;
          text-indent:-21.0pt;}
        @list l0:level4
        {mso-level-tab-stop:none;
          mso-level-number-position:left;
          margin-left:84.0pt;
          text-indent:-21.0pt;}
        @list l0:level5
        {mso-level-number-format:alpha-lower;
          mso-level-text:"%5\)";
          mso-level-tab-stop:none;
          mso-level-number-position:left;
          margin-left:105.0pt;
          text-indent:-21.0pt;}
        @list l0:level6
        {mso-level-number-format:roman-lower;
          mso-level-tab-stop:none;
          mso-level-number-position:right;
          margin-left:126.0pt;
          text-indent:-21.0pt;}
        @list l0:level7
        {mso-level-tab-stop:none;
          mso-level-number-position:left;
          margin-left:147.0pt;
          text-indent:-21.0pt;}
        @list l0:level8
        {mso-level-number-format:alpha-lower;
          mso-level-text:"%8\)";
          mso-level-tab-stop:none;
          mso-level-number-position:left;
          margin-left:168.0pt;
          text-indent:-21.0pt;}
        @list l0:level9
        {mso-level-number-format:roman-lower;
          mso-level-tab-stop:none;
          mso-level-number-position:right;
          margin-left:189.0pt;
          text-indent:-21.0pt;}
        ol
        {margin-bottom:0cm;}
        ul
        {margin-bottom:0cm;}
        -->

        .content p{
          font-size: 14pt;
          font-family: 宋体;
        }
      </style>
      <!--[if gte mso 10]>
      <style>
        /* Style Definitions */
        table.MsoNormalTable
        {mso-style-name:普通表格;
          mso-tstyle-rowband-size:0;
          mso-tstyle-colband-size:0;
          mso-style-noshow:yes;
          mso-style-priority:99;
          mso-style-parent:"";
          mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
          mso-para-margin:0cm;
          mso-para-margin-bottom:.0001pt;
          mso-pagination:widow-orphan;
          font-size:10.0pt;
          font-family:"Calibri",sans-serif;
          mso-bidi-font-family:"Times New Roman";}
      </style>
      <![endif]-->
      <div class="tab-content" style="padding-bottom: 0;padding-top: 0">
        <div class=WordSection1 style='layout-grid:15.6pt'>

          <p class=MsoNormal align=center style='margin-top:7.8pt;margin-right:0cm;
margin-bottom:7.8pt;margin-left:0cm;mso-para-margin-top:7.8pt;mso-para-margin-right:
0cm;mso-para-margin-bottom:.5gd;mso-para-margin-left:0cm;text-align:center;
text-indent:5.4pt'><span style='font-size:18.0pt;font-family:宋体'>${dataMap.realname}同志考察材料<span
                  lang=EN-US><o:p></o:p></span></span></p>

          <div align=center>

            <table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=651
                   style='width:488.25pt;border-collapse:collapse;border:none;mso-border-alt:
 solid windowtext .5pt;mso-yfti-tbllook:1184;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
 mso-border-insideh:.5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>
              <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:31.2pt'>
                <td width=651 colspan=5 style='width:488.25pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;background:#F2F2F2;padding:0cm 5.4pt 0cm 5.4pt;
  height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center'><b
                          style='mso-bidi-font-weight:normal'><span style='font-size:14.0pt;font-family:
  宋体'>考察对象基本情况<span lang=EN-US><o:p></o:p></span></span></b></p>
                </td>
              </tr>
              <tr style='mso-yfti-irow:1;height:31.2pt'>
                <td width=150 colspan=2 style='width:112.6pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt;
  mso-line-height-rule:exactly'><span style='font-size:14.0pt;font-family:宋体'>工作证号<span
                          lang=EN-US><o:p></o:p></span></span></p>
                </td>
                <td width=175 style='width:131.6pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US
                                                                                  style='font-size:14.0pt;font-family:宋体'>${dataMap.code}<o:p></o:p></span></p>
                </td>
                <td width=151 style='width:4.0cm;border-top:none;border-left:none;border-bottom:
  solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-top-alt:
  solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;mso-border-alt:
  solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt;
  mso-line-height-rule:exactly'><span style='font-size:14.0pt;font-family:宋体'>姓<span
                          lang=EN-US><span style='mso-spacerun:yes'>&nbsp;&nbsp; </span><span
                          style='mso-spacerun:yes'>&nbsp;</span></span>名<span lang=EN-US><o:p></o:p></span></span></p>
                </td>
                <td width=174 style='width:130.65pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>${dataMap.realname}<span lang=EN-US><o:p></o:p></span></span></p>
                </td>
              </tr>
              <tr style='mso-yfti-irow:2;height:31.2pt'>
                <td width=150 colspan=2 style='width:112.6pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>所在单位及职务<span lang=EN-US><o:p></o:p></span></span></p>
                </td>
                <td width=501 colspan=3 style='width:375.65pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  14.0pt;font-family:宋体'>${dataMap.post}<span lang=EN-US><o:p></o:p></span></span></p>
                </td>
              </tr>
              <tr style='mso-yfti-irow:3;height:31.2pt'>
                <td width=651 colspan=5 style='width:488.25pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  background:#F2F2F2;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center'><b
                          style='mso-bidi-font-weight:normal'><span style='font-size:14.0pt;font-family:
  宋体'>考 察 情 况<span lang=EN-US><o:p></o:p></span></span></b></p>
                </td>
              </tr>
              <tr style='mso-yfti-irow:4;height:31.2pt'>
                <td width=150 colspan=2 style='width:112.6pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>考察组成员<span lang=EN-US><o:p></o:p></span></span></p>
                </td>
                <td width=501 colspan=3 style='width:375.65pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:31.2pt'>
                  <p class=MsoNormal align=left style='text-align:left'><span style='font-size:
  14.0pt;font-family:宋体'>${dataMap.inspectors}<span lang=EN-US><o:p></o:p></span></span></p>
                </td>
              </tr>
              <tr style='mso-yfti-irow:5;height:362.25pt'>
                <td width=52 rowspan=2 style='width:38.65pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:362.25pt'>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>考<span lang=EN-US><o:p></o:p></span></span></p>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>察<span lang=EN-US><o:p></o:p></span></span></p>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>情<span lang=EN-US><o:p></o:p></span></span></p>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>况<span lang=EN-US><o:p></o:p></span></span></p>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>汇<span lang=EN-US><o:p></o:p></span></span></p>
                  <p class=MsoNormal align=center style='text-align:center'><span
                          style='font-size:14.0pt;font-family:宋体'>总<b style='mso-bidi-font-weight:normal'><span
                          lang=EN-US><o:p></o:p></span></b></span></p>
                </td>
                <td width=599 colspan=4 valign=top style='width:449.6pt;border:none;
  border-right:solid windowtext 1.0pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-left-alt:solid windowtext .5pt;mso-border-top-alt:solid windowtext .5pt;
  mso-border-left-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:362.25pt;font-size:14.0pt;font-family:宋体' class="content">
                  ${dataMap.content}
                </td>
              </tr>
              <tr style='mso-yfti-irow:6;height:76.5pt'>
                <td width=599 colspan=4 valign=bottom style='width:449.6pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-left-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:76.5pt'>
                  <p class=MsoNormal align=right style='margin-left:28.15pt;mso-para-margin-left:
  2.68gd;text-align:right;line-height:25.0pt;mso-line-height-rule:exactly;
  word-break:break-all'><span style='font-size:14.0pt;font-family:宋体'>考察组负责人：${dataMap.chief}<span
                          lang=EN-US><span
                          style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></span>${dataMap.inspectorType}<span lang=EN-US><span
                          style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><o:p></o:p></span></span></p>
                  <p class=MsoNormal align=right style='margin-left:28.15pt;mso-para-margin-left:
  2.68gd;text-align:right;line-height:25.0pt;mso-line-height-rule:exactly;
  word-break:break-all;font-size:14.0pt;font-family:宋体'>${dataMap.inspectDate}<span lang=EN-US><span
                          style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span><o:p></o:p></span></span></p>
                </td>
              </tr>
              <tr style='mso-yfti-irow:7;mso-yfti-lastrow:yes;height:2.0cm'>
                <td width=52 style='width:38.65pt;border:solid windowtext 1.0pt;border-top:
  none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:2.0cm'>
                  <p class=MsoNormal align=center style='text-align:center;line-height:18.0pt;
  mso-line-height-rule:exactly'><span style='font-size:14.0pt;font-family:宋体'>备注<span
                          lang=EN-US><o:p></o:p></span></span></p>
                </td>
                <td width=599 colspan=4 style='width:449.6pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;
  mso-border-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:2.0cm'>
                  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US
                                                                              style='font-size:14.0pt;font-family:宋体'>
                    ${dataMap.remark}
                  </span></p>
                </td>
              </tr>
             <tr>
               <td colspan="5" style="text-align: right; border: 0;">
                 <p class=MsoNormal align=right style='text-align:right;line-height:16.0pt;
mso-line-height-rule:exactly'><span style='font-size:14.0pt;font-family:宋体'>${dataMap.schoolName}党委组织部制表<span
                         lang=EN-US><o:p></o:p></span></span></p>
               </td>
             </tr>
            </table>

          </div>


        </div>
      </div>
    </div>
  </div>
  <c:if test="${param.view!=1}">
  <div class="clearfix form-actions center">
    <button class="btn btn-success" type="button" id="saveBtn">
      <i class="ace-icon fa fa-save bigger-110"></i>
      归档保存
    </button>
  </div>
  </c:if>
</div>
<c:if test="${param.view!=1}">
<script>
  $("#saveBtn").click(function(){

    $.post("${ctx}/sc/scAdArchive_cisSave", {archiveId:'${param.archiveId}', objId: "${param.objId}"}, function (ret) {
      if (ret.success) {
        $.hideView();
      }
    })
  });
</script>
  </c:if>