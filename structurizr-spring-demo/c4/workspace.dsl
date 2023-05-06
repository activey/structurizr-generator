workspace {

    model {
        properties {
            "structurizr.groupSeparator" "/"
        }

        user = person "User"

        softwareSystem "Spring Demo" {
            !include infrastructure

            postgresql = container "Database" "PostgreSQL" {
                tags Database
            }

            !include model/container.dsl
        }

        user -> nginx "Calls"
    }

    views {
        styles {
            !include styles
        }

        component test {
            include *
            autoLayout lr
        }
    }

}