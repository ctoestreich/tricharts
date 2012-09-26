<div class="row-fluid">
  <div class="<g:if test="${showButtons != "no"}">span6</g:if><g:else>span12</g:else>"><h4>${sport}</h4></div>

  <g:if test="${showButtons != "no"}">
    <div class="span6">
      <div class="btn-group-wrap-right">
        <div class="btn-group">
          <a class="btn dropdown-toggle btn-small" data-toggle="dropdown" href="#">
            Sort
            <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li><a
                    href="${createLink(controller: 'results', action: 'index', params: [srt: "date", 'user.id': params?.user?.id])}"
                    id="view-progression"><i class="icon-calendar"></i>&nbsp;Date</a></li>
            <li><a
                    href="${createLink(controller: 'results', action: 'index', params: [srt: "type", 'user.id': params?.user?.id])}"
                    id="view-progression"><i class="icon-film"></i>&nbsp;Type</a></li>
          </ul>
        </div>

        <div class="btn-group">
          <a class="btn btn-small"
             href="javascript:void(0);"
             id="toggle${sport}"><i id="icon${sport}" class="icon-arrow-down"></i>&nbsp;Toggle All</a>
          <a class="btn btn-small"
             href="${createLink(controller: 'visualization', action: 'progression', params: [raceType: sport, 'user.id': params?.user?.id])}"
             id="view-progression"><i class="icon-camera"></i>&nbsp;${sport} Charts</a>
          <a class="btn btn-small"
             href="${createLink(controller: 'results', action: 'createResult', params: [raceType: sport, 'user.id': params?.user?.id])}"
             id="add-race"><i class="icon-tag"></i>&nbsp;Add Result</a>
        </div>

      </div>
    </div>
  </g:if>
</div>

<g:if test="${showButtons != "no"}">
  <script type="text/javascript">
    $(function () {
      $('#toggle${sport}').on('click', function () {
        var addClass = ($('#icon${sport}').hasClass('icon-arrow-down')) ? 'icon-arrow-up' : 'icon-arrow-down';
        var removeClass = ((addClass === 'icon-arrow-up') ? 'icon-arrow-down' : 'icon-arrow-up');
        var collapse = ((addClass === 'icon-arrow-up') ? 'show' : 'hide');
        $('.collapse-${sport.toLowerCase()}').collapse(collapse);
        $('#icon${sport}').removeClass(removeClass).addClass(addClass);
      });
    });
  </script>
</g:if>