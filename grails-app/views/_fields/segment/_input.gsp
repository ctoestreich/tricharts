<%@ page import="com.tgid.tri.race.Segment" defaultCodec="html" %>
<g:if test="${bean.hasProperty('raceResults')}">
  <g:select name="segment.id" from="${bean?.raceResults?.race?.segments}" id="segment"/>
</g:if>
<g:else>
  <g:select value="${bean?.segment?.id}" name="segment.id" optionKey="id" from="${Segment.list().sort {a, b -> a.segmentOrder <=> b.segmentOrder}}" id="segment"/>
</g:else>