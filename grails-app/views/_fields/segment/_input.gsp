<%@ page import="com.tgid.tri.race.Segment" defaultCodec="html" %>
<g:if test="${bean.hasProperty('raceResult')}">
  <g:select name="segment.id" from="${bean?.raceResult?.race?.segments}" id="segment"/>
</g:if>
<g:else>
  <g:select value="${bean?.segment?.id}" name="segment.id" optionKey="id" from="${Segment.list().sort {it.segmentOrder}}" id="segment"/>
</g:else>