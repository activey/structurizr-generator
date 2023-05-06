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
        rabbitMQ -> Service2 "Notifies about published message" "" "Asynchronous"
        Service1 -> rabbitMq "Publishes new message" "" "Asynchronous"
    }

    views {
        styles {
            !include styles
        }
    }

}