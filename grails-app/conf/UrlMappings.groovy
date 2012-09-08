class UrlMappings {

	static mappings = {

        "/sitemap" {
            controller = 'sitemap'
            action = 'sitemap'
        }

        "/$controller/backbone/$id?" {
            action = [GET:"backboneList", POST: "backboneSave", DELETE: "backboneDelete", PUT: "backboneEdit"]
        }

		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: "site", action: "index")
		"500"(view:'/error')
		"404"(view:'/error')
	}
}
