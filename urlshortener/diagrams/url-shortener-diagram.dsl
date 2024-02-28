workspace {

    model {
        user = person "User"
        external-system = softwareSystem "External Web Application" " " "Existing System"
        url-shortener = softwareSystem "Url Shortener System" {
            webapp = container "URL Shortener Service" {
                user -> this "1 - Submits a long URL"
                this -> user "3 - Returns a short URL"
                user -> this "4 - Access the short URL"
                this -> external-system "6 - Redirects to external link"
            }
            database = container "Database" " " " " "Database" {
                webapp -> this "2 - Writes short URI"
                webapp -> this "5 - Finds long URL by short URI"
            }
        }
    }

    views {
        systemContext url-shortener {
            include *
            autolayout lr
        }

        container url-shortener "Containers" {
            include *
            autoLayout lr
            description "The container diagram for the URL Shortener Service."
        }

        styles {
            element "Existing System" {
                background #999999
                color #ffffff
            }
            element "Database" {
                shape Cylinder
            }
        }

        theme default
    }
}