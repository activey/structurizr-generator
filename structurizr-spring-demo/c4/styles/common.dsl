element "Database" {
    shape Cylinder
    background #00bfff
}

element "Message Bus" {
    shape Pipe
    background yellow
}

element "Person" {
    shape Person
}

element "Service" {
    shape Hexagon
}

Element "ExternalSystem" {
    shape Circle
    border Dashed
}

relationship "Relationship" {
    dashed false
    #routing Orthogonal
}

relationship "Asynchronous" {
    dashed true
    routing Orthogonal
}