SELECT *
FROM produto_servico ps
WHERE 1=1
<#if nome??>
and ps.nome LIKE '${nome}'
</#if>
<#if descricao??>
and ps.descricao LIKE '${descricao}'
</#if>
<#if valor??>
and ps.valor in (${valor})'
</#if>
<#if tipo??>
and ps.tipo in (${tipo})'
</#if>
<#if status??>
and ps.status in (${status})'
</#if>