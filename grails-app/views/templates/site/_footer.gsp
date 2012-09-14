<footer class="footer">
  <div class="container">
    <div class="row-fluid">
      <div class="span6">
        %{--<p><div class="fb-like" data-href="http://www.tricharts.com/" data-send="false" data-layout="button_count" data-width="450" data-show-faces="true" data-action="recommend"></div></p>--}%
        <p><iframe src="//www.facebook.com/plugins/like.php?href=http%3A%2F%2Fwww.tricharts.com%2F&amp;send=false&amp;layout=button_count&amp;width=450&amp;show_faces=true&amp;action=recommend&amp;colorscheme=light&amp;font&amp;height=21&amp;appId=423509571032868" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:450px; height:21px;" allowTransparency="true"></iframe></p>
        <p><small>&copy; Two Guys In Design, LLC 2012&nbsp;|&nbsp;version: <g:meta name="app.version"/></small></p>
      </div>
      <div class="span6">
        <ul class="nav nav-pills">
          <li><a <%=request.forwardURI == "${createLink(uri: '/')}" ? 'class="btn disabled"' : ''%> href="${createLink(uri: '/')}">Home</a></li>
          <li><a <%=request.forwardURI == "${createLink(controller: 'site', action: 'aboutus')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'site', action: 'aboutus', params: paramMap)}">About Us</a></li>
          <li><a <%=request.forwardURI == "${createLink(controller: 'site', action: 'contact')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'site', action: 'contact', params: paramMap)}">Contact</a></li>
          <li><a <%=request.forwardURI == "${createLink(controller: 'site', action: 'faq')}" ? ' class="btn disabled"' : ''%> href="${createLink(controller: 'site', action: 'faq', params: paramMap)}">FAQ</a></li>
        </ul>
      </div>
    </div>
  </div>
</footer>