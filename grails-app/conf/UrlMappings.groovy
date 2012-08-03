class UrlMappings {

	static mappings = {

        "/$controller/backbone/$id?" {
            action = [GET:"backboneList", POST: "backboneSave", DELETE: "backboneDelete", PUT: "backboneEdit"]
        }

		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
