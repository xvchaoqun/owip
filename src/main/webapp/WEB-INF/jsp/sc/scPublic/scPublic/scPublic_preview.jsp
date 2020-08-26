<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>预览</h3>
</div>
<div class="modal-body">
    <div class=WordSection1 style='layout-grid:15.6pt'>

        <p class=MsoNormal align=center style='mso-margin-top-alt:auto;mso-margin-bottom-alt:
auto;text-align:center;line-height:22.0pt;mso-line-height-rule:exactly;
mso-pagination:widow-orphan;layout-grid-mode:char'><a name="OLE_LINK2"></a><a
                name="OLE_LINK1"><span style='mso-bookmark:OLE_LINK2'><b><span
                style='font-size:22.0pt;font-family:宋体;mso-bidi-font-family:宋体;letter-spacing:
2.6pt;mso-font-kerning:0pt'>干部任前公示</span></b></span></a><span style='mso-bookmark:
OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span lang=EN-US
                                                      style='font-size:12.0pt;font-family:宋体;mso-bidi-font-family:宋体;letter-spacing:
2.6pt;mso-font-kerning:0pt'><o:p></o:p></span></span></span></p>

        <p class=MsoNormal align=left style='mso-margin-top-alt:auto;mso-margin-bottom-alt:
auto;text-align:left;line-height:22.0pt;mso-line-height-rule:exactly;
mso-pagination:widow-orphan;layout-grid-mode:char'><span style='mso-bookmark:
OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><b><span style='font-size:14.0pt;
font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'>校内各单位：</span></b></span></span><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                lang=EN-US style='font-size:12.0pt;font-family:宋体;mso-bidi-font-family:宋体;
mso-font-kerning:0pt'><o:p></o:p></span></span></span></p>

        <p class=MsoNormal align=left style='mso-margin-top-alt:auto;mso-margin-bottom-alt:
auto;text-align:left;text-indent:28.0pt;line-height:22.0pt;mso-line-height-rule:
exactly;mso-pagination:widow-orphan;layout-grid-mode:char'><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                style='font-size:14.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
0pt'>为切实加强干部选拔任用工作的民主监督，根据中共中央《党政领导干部选拔任用工作条例》和《${dataMap.schoolName}处级干部选拔任用工作实施办法》的有关要求，现将<span
                lang=EN-US>${dataMap.holdDate}</span>党委常委会讨论通过的拟任职干部公示如下：</span></span></span><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                lang=EN-US style='font-size:12.0pt;font-family:宋体;mso-bidi-font-family:宋体;
mso-font-kerning:0pt'><o:p></o:p></span></span></span></p>

        <c:forEach items="${dataMap.votes}" var="vote">
        <p class=MsoNormal style='margin-top:7.8pt;margin-right:0cm;margin-bottom:7.8pt;
margin-left:0cm;mso-para-margin-top:.5gd;mso-para-margin-right:0cm;mso-para-margin-bottom:
.5gd;mso-para-margin-left:0cm;text-indent:27.5pt;line-height:22.0pt;mso-line-height-rule:
exactly;layout-grid-mode:char'><span style='mso-bookmark:OLE_LINK1'><span
                style='mso-bookmark:OLE_LINK2'><a name="OLE_LINK3"><b><span lang=EN-US
                                                                            style='font-size:14.0pt;font-family:宋体;mso-bidi-font-family:宋体;color:blue;
mso-font-kerning:0pt'>${vote.realname} <span style='mso-spacerun:yes'>&nbsp;</span></span></b></a></span></span><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                style='mso-bookmark:OLE_LINK3'><b><span style='font-size:14.0pt;font-family:
宋体;mso-bidi-font-family:宋体;color:blue;mso-font-kerning:0pt'>拟任<span lang=EN-US>${vote.post}
        <o:p></o:p></span></span></b></span></span></span></p>

        <p class=MsoNormal align=left style='margin-top:7.8pt;margin-right:0cm;
margin-bottom:7.8pt;margin-left:0cm;mso-para-margin-top:.5gd;mso-para-margin-right:
0cm;mso-para-margin-bottom:.5gd;mso-para-margin-left:0cm;text-align:left;
text-indent:27.5pt;line-height:22.0pt;mso-line-height-rule:exactly;mso-pagination:
widow-orphan;layout-grid-mode:char'><span style='mso-bookmark:OLE_LINK1'><span
                style='mso-bookmark:OLE_LINK2'><span style='mso-bookmark:OLE_LINK3'><span
                lang=EN-US style='font-size:14.0pt;font-family:宋体;mso-bidi-font-weight:bold'>${vote.detail}<o:p></o:p></span></span></span></span>
        </p>
        </c:forEach>
        <span style='mso-bookmark:OLE_LINK3'></span>

        <p class=MsoNormal align=left style='margin-top:7.8pt;margin-right:0cm;
margin-bottom:7.8pt;margin-left:0cm;mso-para-margin-top:.5gd;mso-para-margin-right:
0cm;mso-para-margin-bottom:.5gd;mso-para-margin-left:0cm;text-align:left;
text-indent:27.5pt;line-height:22.0pt;mso-line-height-rule:exactly;mso-pagination:
widow-orphan;layout-grid-mode:char'><span style='mso-bookmark:OLE_LINK1'><span
                style='mso-bookmark:OLE_LINK2'><span style='font-size:14.0pt;font-family:宋体;
mso-bidi-font-family:宋体;mso-font-kerning:0pt'>以上干部公示时间为<span lang=EN-US>${dataMap.publicDate}</span>。组织部设干部监督信箱<span
                lang=EN-US>${_pMap['zzb_email']}</span>，并接待来信来访（组织部办公地点为${_pMap['zzb_address']}, 联系电话<span
                lang=EN-US>${_pMap['zzb_phone']}</span>）。反映情况和问题应实事求是，客观公正。为便于核实、反馈有关情况，请以单位名义反映问题的加盖单位公章，以个人名义反映问题的署真实姓名、单位和联系电话。</span></span></span><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                lang=EN-US style='font-size:12.0pt;font-family:宋体;mso-bidi-font-family:宋体;
mso-font-kerning:0pt'><o:p></o:p></span></span></span></p>

        <p class=MsoNormal align=left style='mso-margin-top-alt:auto;mso-margin-bottom-alt:
auto;text-align:left;text-indent:28.0pt;line-height:22.0pt;mso-line-height-rule:
exactly;mso-pagination:widow-orphan;layout-grid-mode:char'><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                style='font-size:14.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
0pt'>组织部将按照有关程序对群众反映的情况进行调查核实，并上报校党委。<span lang=EN-US><o:p></o:p></span></span></span></span></p>

        <p class=MsoNormal align=left style='mso-margin-top-alt:auto;mso-margin-bottom-alt:
auto;text-align:left;text-indent:28.0pt;line-height:22.0pt;mso-line-height-rule:
exactly;mso-pagination:widow-orphan;layout-grid-mode:char'><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                lang=EN-US style='font-size:12.0pt;font-family:宋体;mso-bidi-font-family:宋体;
mso-font-kerning:0pt'><o:p>&nbsp;</o:p></span></span></span></p>

        <p class=MsoNormal align=center style='mso-margin-top-alt:auto;mso-margin-bottom-alt:
auto;text-align:center;text-indent:148.85pt;line-height:22.0pt;mso-line-height-rule:
exactly;mso-pagination:widow-orphan;layout-grid-mode:char'><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                lang=EN-US style='font-size:14.0pt;font-family:宋体;mso-bidi-font-family:宋体;
mso-font-kerning:0pt'><span style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span><span
                style='mso-spacerun:yes'>&nbsp;</span><span
                style='mso-spacerun:yes'>&nbsp;</span></span></span></span><span
                style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><span
                style='font-size:14.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
0pt'>党委组织部</span></span></span><span style='mso-bookmark:OLE_LINK1'><span
                style='mso-bookmark:OLE_LINK2'><span lang=EN-US style='font-size:12.0pt;
font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></span></span></p>

        <span style='mso-bookmark:OLE_LINK2'></span><span style='mso-bookmark:OLE_LINK1'></span>

        <p class=MsoNormal align=center style='mso-margin-top-alt:auto;mso-margin-bottom-alt:
auto;text-align:center;text-indent:197.15pt;line-height:22.0pt;mso-line-height-rule:
exactly;mso-pagination:widow-orphan;layout-grid-mode:char'><span lang=EN-US
                                                                 style='font-size:14.0pt;font-family:宋体;mso-bidi-font-family:宋体;mso-font-kerning:
0pt'>${dataMap.publishDate}</span><span lang=EN-US style='font-size:12.0pt;font-family:
宋体;mso-bidi-font-family:宋体;mso-font-kerning:0pt'><o:p></o:p></span></p>

    </div>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>