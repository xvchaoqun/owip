<#if title??>
<w:p wsp:rsidR="00E759EF" wsp:rsidRPr="00B62C14" wsp:rsidRDefault="00E759EF" wsp:rsidP="00B943F3">
    <w:pPr>
        <w:spacing w:line="400" w:line-rule="exact"/>
        <w:ind w:left="2041" w:hanging="2041"/>
        <w:rPr>
            <w:b/>
            <w:sz w:val="22"/>
        </w:rPr>
    </w:pPr>
    <w:r wsp:rsidRPr="00B62C14">
        <w:rPr>
            <w:rFonts w:hint="fareast"/>
            <wx:font wx:val="宋体"/>
            <w:b/>
            <w:sz w:val="22"/>
        </w:rPr>
        <w:t>${title}：</w:t>
    </w:r>
</w:p>
</#if>
<#list dataList as row>
<w:p wsp:rsidR="00E759EF" wsp:rsidRPr="00B62C14" wsp:rsidRDefault="004C07F8" wsp:rsidP="00B943F3">
    <#if row_index==0>
    <w:pPr>
        <w:spacing w:line="400" w:line-rule="exact"/>
        <w:ind w:left="2041" w:hanging="2041"/>
        <w:rPr>
            <w:sz w:val="22"/>
        </w:rPr>
    </w:pPr>
    </#if>
    <#list row as col>
    <w:r>
        <w:rPr>
            <w:sz w:val="22"/>
        </w:rPr>
        <w:t>${col}</w:t>
    </w:r>
    <#if col_has_next>
    <w:r wsp:rsidR="00E759EF" wsp:rsidRPr="00B62C14">
        <w:rPr>
            <w:rFonts w:hint="fareast"/>
            <w:sz w:val="22"/>
        </w:rPr>
        <w:t>  </w:t>
    </w:r>
    </#if>
    </#list>
</w:p>
</#list>
<w:p wsp:rsidR="00E759EF" wsp:rsidRPr="00B62C14" wsp:rsidRDefault="00E759EF" wsp:rsidP="00B943F3">
    <w:pPr>
        <w:spacing w:line="400" w:line-rule="exact"/>
        <w:rPr>
            <w:sz w:val="22"/>
        </w:rPr>
    </w:pPr>
</w:p>