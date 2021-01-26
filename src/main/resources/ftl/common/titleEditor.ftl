<#if title??>
<w:p>
    <w:pPr>
        <w:rPr>
            <w:b/>
        </w:rPr>
    </w:pPr>
    <w:r>
        <w:t>${title}：</w:t>
    </w:r>
</w:p>
</#if>
<#list dataList as row>
<w:p>
    <w:pPr>
        <w:ind w:left="2200" w:hanging="${row[1]?starts_with("（")?string("136","2000")}"/>
    </w:pPr>
    <#list row as col>
    <#if col_index!=0>
    <w:r>
        <w:t xml:space="preserve"><#if needWhiteSpace?? && needWhiteSpace>    </#if>${col}<#if col_has_next>  </#if></w:t>
    </w:r>
    </#if>
    </#list>
</w:p>
</#list>
