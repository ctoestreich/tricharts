<g:each in="${raceTypes}" var="raceType">
  <a href="${createLink(controller: controller, action: action, params: [raceType: raceType, 'user.id': params?.user?.id])}" class="btn btn-small ${request.forwardURI == createLink(controller: controller, action: action) && params?.raceType == raceType.raceType ? 'btn-success' : ''}">${raceType.raceType} ${title}</a>&nbsp;
</g:each>