<#if title??>
<w:p>
    <w:pPr>
        <w:spacing w:line="22pt" w:lineRule="exact"/>
        <w:ind w:start="102.05pt" w:hanging="102.05pt"/>
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
        <w:spacing w:line="22pt" w:lineRule="exact"/>
        <w:ind w:start="102.05pt" w:hanging="102.05pt"/>
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
