package com.tgid.tri

class SitemapController {

    def sitemap = {
        render(contentType: 'text/xml', encoding: 'UTF-8') {
            mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
            urlset(xmlns: "http://www.sitemaps.org/schemas/sitemap/0.9",
                   'xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance",
                   'xsi:schemaLocation': "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd") {
                url {
                    loc(g.createLink(absolute: true, uri: '/'))
                    changefreq('weekly')
                    priority(1.0)
                }
                url {
                    loc(g.createLink(absolute: true, controller:'site', action:'aboutus'))
                    changefreq('monthly')
                    priority(0.5)
                }
                url {
                    loc(g.createLink(absolute: true, controller:'site', action:'faq'))
                    changefreq('monthly')
                    priority(0.5)
                }
            }
        }
    }
}
