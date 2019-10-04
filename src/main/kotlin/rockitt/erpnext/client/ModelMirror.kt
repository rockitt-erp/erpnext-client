package rockitt.erpnext.client

internal class ModelMirror(val instance: Any) {
    @Suppress("NAME_SHADOWING")
    operator fun set(key: String, value: Any) {
        val field = instance.javaClass.getDeclaredField(key)
        field.isAccessible = true

        field.set(instance, value)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T: Any> get(key: String): T {
        val field = instance.javaClass.getDeclaredField(key)
        field.isAccessible = true
        return field.get(instance) as T
    }

    fun setId(value: Any) {
        val id = instance.javaClass
                .declaredFields
                .first { it.getDeclaredAnnotation(Id::class.java) != null }

        id.isAccessible = true
        id.set(instance, value)
    }
}

internal fun Any.mirrored(): ModelMirror = ModelMirror(this)