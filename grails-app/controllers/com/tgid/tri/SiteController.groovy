package com.tgid.tri

import com.tgid.tri.auth.User

class SiteController extends BaseController {

    def thanks() { }

    def aboutus(){
        User user = requestedUser

        render view: 'aboutus', model: [user: user]
    }

    def contact(){
        User user = requestedUser

        render view: 'contact', model: [user: user]
    }
}
