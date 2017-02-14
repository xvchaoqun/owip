<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>预览</h3>
</div>
<div class="modal-body">
<style type="text/css">
    .b1 {
        white-space-collapsing: preserve;
    }

    .b2 {
        margin: 0.7875in 0.7875in 0.7875in 0.7875in;
    }

    .s1 {
        font-weight: bold;
    }

    .s2 {
        font-weight: bold;
        text-decoration: underline;
    }

    .p1 {
        text-align: center;
        hyphenate: auto;
        font-family: 华文中宋;
        font-size: 18pt;
    }

    .p2 {
        text-align: center;
        hyphenate: auto;
        font-family: 华文中宋;
        font-size: 10pt;
    }

    .p3 {
        text-indent: 2.6409721in;
        margin-top: 0.25in;
        margin-bottom: 0.108333334in;
        text-align: end;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 14pt;
    }

    .p4 {
        text-align: center;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 12pt;
    }

    .p5 {
        text-align: start;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 12pt;
    }

    .p6 {
        text-align: justify;
        hyphenate: auto;
        font-family: 华文仿宋;
        font-size: 12pt;
    }

    .p7 {
        text-align: start;
        hyphenate: auto;
        font-family: 宋体;
        font-size: 14pt;
    }

    .td1 {
        width: 0.64375in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td2 {
        width: 1.7034723in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td3 {
        width: 4.195833in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td4 {
        width: 0.7875in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td5 {
        width: 1.0458333in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .td6 {
        width: 5.8993053in;
        padding-start: 0.075in;
        padding-end: 0.075in;
        border-bottom: thin solid black;
        border-left: thin solid black;
        border-right: thin solid black;
        border-top: thin solid black;
    }

    .r1 {
        keep-together: always;
    }

    .t1 {
        table-layout: fixed;
        border-collapse: collapse;
        border-spacing: 0;
    }
</style>

<table class="t1">
    <tbody>
    <tr>
        <td colspan="11">
            <p class="p1">
                <span class="s1">专题班评估表</span>
            </p>
        </td>
    </tr>
    <tr>
        <td colspan="11">
            <p class="p3">
                <span class="s1">评估总分 : </span><span class="s2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </p>
        </td>
    </tr>
    <tr>
        <td class="td1" rowspan="3">
            <p class="p4">
                <span>评估</span>
            </p>

            <p class="p4">
                <span>内容</span>
            </p>
        </td>
        <td class="td2" rowspan="3">
            <p class="p4">
                <span>评估指标</span>
            </p>
        </td>
        <td class="td3" colspan="5">
            <p class="p4">
                <span>评估等级</span>
            </p>
        </td>
    </tr>
    <tr>
        <td class="td4">
            <p class="p4">
                <span>很满意</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4">
                <span>满意</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4">
                <span>较满意</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4">
                <span>一般</span>
            </p>
        </td>
        <td class="td5">
            <p class="p4">
                <span>不满意</span>
            </p>
        </td>
    </tr>
    <tr>
        <td class="td4">
            <p class="p4">
                <span>10分</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4">
                <span>9分</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4">
                <span>8分</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4">
                <span>7分</span>
            </p>
        </td>
        <td class="td5">
            <p class="p4">
                <span>6分及以下</span>
            </p>
        </td>
    </tr>
    <tr>
        <td class="td1" rowspan="3">
            <p class="p4">
                <span>培训</span>
            </p>

            <p class="p4">
                <span>设计</span>
            </p>
        </td>
        <td class="td2">
            <p class="p5">
                <span>目标设定</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td2">
            <p class="p5">
                <span>课程设置</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td2">
            <p class="p5">
                <span>师资配备</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td1" rowspan="3">
            <p class="p4">
                <span>培训</span>
            </p>

            <p class="p4">
                <span>实施</span>
            </p>
        </td>
        <td class="td2">
            <p class="p5">
                <span>教学内容</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td2">
            <p class="p5">
                <span>教学方法</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td2">
            <p class="p5">
                <span>教学水平</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td1" rowspan="2">
            <p class="p4">
                <span>培训</span>
            </p>

            <p class="p4">
                <span>管理</span>
            </p>
        </td>
        <td class="td2">
            <p class="p5">
                <span>学员管理</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td2">
            <p class="p5">
                <span>服务质量</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td1" rowspan="2">
            <p class="p4">
                <span>培训</span>
            </p>

            <p class="p4">
                <span>效果</span>
            </p>
        </td>
        <td class="td2">
            <p class="p4">
                <span>对推动工作帮助程度</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr>
        <td class="td2">
            <p class="p4">
                <span>对个人成长帮助程度</span>
            </p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td4">
            <p class="p4"></p>
        </td>
        <td class="td5">
            <p class="p4"></p>
        </td>
    </tr>
    <tr class="r1">
        <td class="td1">
            <p class="p4">
                <span>意见</span>
            </p>

            <p class="p4">
                <span>建议</span>
            </p>
        </td>
        <td class="td6" colspan="6">
            <p class="p6"></p>
        </td>
    </tr>
    </tbody>
</table>
<p class="p7"></p>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
