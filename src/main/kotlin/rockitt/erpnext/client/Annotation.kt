package rockitt.erpnext.client

import kotlin.reflect.KClass

annotation class Resource(val name: String)

annotation class Link(val name: Resource, val type: KClass<*>)

annotation class Id