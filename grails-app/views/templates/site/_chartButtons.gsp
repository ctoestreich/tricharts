<g:each in="${raceTypes}" var="raceType">
  <li class="${request.forwardURI == createLink(controller: controller, action: action) && params?.raceType == raceType.raceType ? 'active' : ''}"><a href="${createLink(controller: controller, action: action, params: [raceType: raceType, 'user.id': params?.user?.id])}">${raceType.raceType} ${title}</a></li>&nbsp;
</g:each>