<#list dataList as row>
<w:p w14:paraId="51924773" w14:textId="77777777" w:rsidR="00C5539E" w:rsidRDefault="009907B5">
    <w:pPr>
        <w:spacing w:line="18pt" w:lineRule="exact"/>
        <w:ind w:start="100pt" w:hanging="100pt"/>
    </w:pPr>
    <#list row as col>
        <#if col_index!=0>
            <w:r>
                <w:rPr>
                    <w:sz w:val="24"/>
            <w:szCs w:val="24"/>
        </w:rPr>
        <w:t xml:space="preserve"><#if needWhiteSpace?? && needWhiteSpace>    </#if>${col}<#if col_has_next>  </#if></w:t>
    </w:r>
    </#if>
    </#list>
</w:p>
</#list>