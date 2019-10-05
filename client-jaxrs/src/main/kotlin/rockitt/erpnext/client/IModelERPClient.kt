package rockitt.erpnext.client

import kotlin.reflect.KClass

interface IModelERPClient: AutoCloseable {
    /***
     * Create a record defined by @item
     */
    fun <T: Any> create(item: T): T?

    /***
     * Read the record of type @klass with rest id of @id
     */
    fun <T: Any> read(klass: KClass<T>, id: Any): T?

    /***
     * Read all record ids of the model type @klass
     */
    fun readAll(klass: KClass<*>, filters: List<Filter> = emptyList(), page: Int = 0, quantity: Int = 20): List<String>

    /***
     * Update the record @item
     */
    fun <T: Any> update(item: T): T?

    /***
     * Delete the record @item
     */
    fun <T: Any> delete(item: T): T?
}