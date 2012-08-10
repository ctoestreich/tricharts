<%
	def attrs = [name: property, value: value, prefix: prefix]
	if (required) attrs.required = ''
	out << tri.periodPicker(attrs)
%>
