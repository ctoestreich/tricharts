<%@ page import="com.tgid.tri.auth.User" %>

<sec:ifAnyGranted roles="ROLE_ADMIN">
  <g:form controller="dashboard" action="index" id="userForm" name="userForm">
    <div class="row">
      <button class="close" data-dismiss="alert">×</button>
      %{--<g:select onchange="app.changeUser()" name="user.id" from="${User.listOrderByFirstName()}" id="userId" optionKey="id" value="${user?.id}"/>--}%
      %{--&nbsp;<a href="javascript:app.changeUser()" class="btn btn-primary">switch</a>--}%
      <input id="userSelect" class="span4" name="userSelect">
      <input type="hidden" id="userId" value="${user?.id}" name="user.id" />
    </div>
  </g:form>

%{--<script>--}%
%{--$(function () {--}%
%{--$("#userId").combobox();--}%
%{--});--}%
%{--</script>--}%

  <script>
    $(function () {
      var cache = {},
              lastXhr;
      $("#userSelect").autocomplete({
                                      minLength:2,
                                      source:function (request, response) {
                                        var term = request.term;
                                        if(term in cache) {
                                          response(cache[ term ]);
                                          return;
                                        }

                                        lastXhr = $.getJSON("${createLink(controller:'user',action:'search')}", request, function (data, status, xhr) {
                                          cache[ term ] = data;
                                          if(xhr === lastXhr) {
                                            response(data);
                                          }
                                        });
                                      },
                                      select:function (event, ui) {
                                        $("#userId").val(ui.item.value);
                                        $("#userSelect").val(ui.item.label);
                                        app.changeUser();
                                        return false;
                                      }
                                    });
    });
  </script>

</sec:ifAnyGranted>