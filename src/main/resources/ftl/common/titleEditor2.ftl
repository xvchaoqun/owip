<#if title??>
<w:p>
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
        <w:spacing w:line="18pt" w:lineRule="exact"/>
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
