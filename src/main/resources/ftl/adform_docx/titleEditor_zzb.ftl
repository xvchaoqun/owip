<#list dataList as row>
<w:p>
    <w:pPr>
        <#if needHanging?? && needHanging>
            <w:ind w:left="2189" w:hanging="${row[1]?starts_with("（")?string("136","2189")}"/>
        </#if>
    </w:pPr>
    <#list row as col>
    <#if col_index!=0>
    <w:r>
        <w:rPr>
            <w:sz w:val="24"/>
            <w:sz-cs w:val="24"/>
        </w:rPr>
        <w:t xml:space="preserve">${col}<#if col_has_next && !col?ends_with(" ")>  </#if></w:t>
    </w:r>
    </#if>
    </#list>
</w:p>
</#list>
