<#if title??>
<w:p>
    <w:pPr>
        <w:spacing w:line="${line}" w:line-rule="exact"/>
        <w:ind w:left="2041" w:hanging="2041"/>
        <w:rPr>
            <w:b/>
        </w:rPr>
    </w:pPr>
    <w:r>
        <w:rPr>
            <w:b/>
        </w:rPr>
        <w:t>${title}：</w:t>
    </w:r>
</w:p>
</#if>
<#list dataList as row>
<w:p>
    <w:pPr>
        <w:spacing w:line="${line}" w:line-rule="exact"/>
        <#if needHanging?? && needHanging>
            <w:ind w:left="2200" w:hanging="${row[1]?starts_with("（")?string("136","2200")}"/>
        </#if>
    </w:pPr>
    <#list row as col>
    <#if col_index!=0>
    <w:r>
        <w:rPr>
            <w:sz w:val="24"/>
            <w:sz-cs w:val="24"/>
        </w:rPr>
        <w:t xml:space="preserve">${col}<#if col_has_next>  </#if></w:t>
    </w:r>
    </#if>
    </#list>
</w:p>
</#list>
