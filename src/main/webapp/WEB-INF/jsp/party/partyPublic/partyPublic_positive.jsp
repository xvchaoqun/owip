<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<STYLE type=text/css>
    .con {
        FONT-SIZE: 14px;
        COLOR: #003333;
        padding: 5px 10px
    }

    .context, .context td {
        FONT-SIZE: 20pt;
        COLOR: black
    }

    .title {
        FONT-SIZE: 24pt;
        FONT-FAMILY: "FZXiaoBiaoSong-B05S";
        FONT-WEIGHT: bold;
        COLOR: #ff0000;
        PADDING-BOTTOM: 10px;
        TEXT-ALIGN: center;
        PADDING-TOP: 20px
    }

    .unpublish {
        font-weight: bolder;
    }

    table {
        table-layout: fixed;
        padding-bottom: 20px;
        margin-top: 20px;
        margin-bottom: 20px;
    }

    BODY {
        font-family: "仿宋_GB2312";
    }
</STYLE>
<div class="row" style="padding: 0 100px 0 100px">
    <TABLE cellSpacing=0 cellPadding=0 width=840 align=center border=0>
        <TBODY>
        <%--<TR>
            <TD height=5 width=7 background="/img/bt1-f.gif"></TD>
            <TD height=7 background="/img/bt1-a.gif"></TD>
            <TD height=5 width=7 background="/img/bt1-g.gif"></TD>
        </TR>--%>
        <TR>
            <%--<TD background="/img/bt1-b.gif" width=7>　</TD>--%>
            <TD height=800 vAlign=top>
                <DIV class=title>公　　示</DIV>
                <c:if test="${!partyPublic.isPublish}">
                    <DIV class="title unpublish">（！！！该公示还未发布！！！）</DIV>
                </c:if>
                <DIV class=con>
                    <TABLE width=800 align=center>
                        <TBODY>
                        <TR>
                            <c:set var="num" value="${fn:length(partyPublic.publicUsers)}"/>
                            <c:set var="realname"
                                   value="${cm:getUserById(partyPublic.publicUsers[0].userId).realname}"/>
                            <TD><SPAN id=context
                                      class=context>&nbsp;&nbsp;&nbsp;经党组织培养考察，${realname}<c:if
                                    test="${num>1}">等${num}名</c:if>同志预备期已满。根据校党委组织部《关于实行发展党员公示制度的意见》精神，党员和群众对以下同志按期转为中共正式党员如有意见或建议，可采用口头或书面形式于${cm:formatDate(deadline, 'yyyy年MM月dd日')}前向院党委或校党委组织部反映。
                        <BR>具体公示名单如下：
                        <BR>
                        <c:forEach items="${partyPublic.publicUsers}" var="pu" varStatus="vs">
                            <c:set var="u" value="${cm:getUserById(pu.userId)}"/>
                            <BR>${pu.branchName}${empty pu.branchName?'':':'}${u.realname}(${u.code})
                        </c:forEach>
                        <BR>
                        <BR>院党委电话：${partyPublic.phone}
                        <BR>信箱号码：${partyPublic.mailbox}
                        <BR>电子邮箱：${partyPublic.email}
                        <BR>校党委组织部电话：${_pMap['zzb_phone']}
                        <BR>信箱号码：${_pMap['zzb_mailbox']}
                        <BR>电子邮箱：${_pMap['zzb_email']}
                        <BR>
                    </SPAN>
                            </TD>
                        </TR>
                        <TR>
                            <TD style="HEIGHT: 150px" vAlign=bottom align=right>
                                <TABLE>
                                    <TBODY>
                                    <TR>
                                        <TD align=center>
                                            <SPAN id="writer" class="context">${partyPublic.partyName}</SPAN>
                                        </TD>
                                    </TR>
                                    <TR>
                                        <TD align=center>
                                            <SPAN id="cur_date"
                                                  class="context">${cm:formatDate(partyPublic.pubDate, 'yyyy年MM月dd日')}</SPAN>
                                        </TD>
                                    </TR>
                                    </TBODY>
                                </TABLE>
                            </TD>
                        </TR>
                        </TBODY>
                    </TABLE>
                </DIV>
            </TD>
            <%-- <TD background="/img/bt1-b.gif" width=7></TD>--%>
        </TR>
        <%-- <TR>
             <TD height=5 width=7 background="/img/bt1-f.gif"></TD>
             <TD height=7 background="/img/bt1-a.gif"></TD>
             <TD height=5 width=7 background="/img/bt1-g.gif"></TD>
         </TR>--%>
        <TR></TR>
        </TBODY>
    </TABLE>
</div>
