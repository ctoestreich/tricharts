<div class="row-fluid">
  <div class="span4"><h2>${sport}</h2></div>

  <div class="span8">
    <div class="btn-group-wrap-right">
      <div class="btn-group">
        <a class="btn dropdown-toggle btn-small" data-toggle="dropdown" href="#">
          Sort
          <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
          <li><a
                  href="${createLink(controller: 'dashboard', action: 'index', params: [srt: "date${sport}", 'user.id': params?.user?.id])}"
                  id="view-progression"><i class="icon-calendar"></i>&nbsp;Date</a></li>
          <li><a
                  href="${createLink(controller: 'dashboard', action: 'index', params: [srt: "type${sport}", 'user.id': params?.user?.id])}"
                  id="view-progression"><i class="icon-film"></i>&nbsp;Type</a></li>
        </ul>
      </div>

      <div class="btn-group">
        <a class="btn btn-small"
           href="javascript:void(0);"
           id="toggle${sport}"><i id="icon${sport}" class="icon-arrow-down"></i>&nbsp;Toggle All</a>
        <a class="btn btn-small"
           href="${createLink(controller: 'dashboard', action: 'progression', params: [raceType: sport, 'user.id': params?.user?.id])}"
           id="view-progression"><i class="icon-camera"></i>&nbsp;${sport} Charts</a>
        <a class="btn btn-small"
           href="${createLink(controller: 'dashboard', action: 'createResult', params: [raceType: sport, 'user.id': params?.user?.id])}"
           id="add-race"><i class="icon-tag"></i>&nbsp;Add Result</a>
      </div>

    </div>
  </div>
</div>

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