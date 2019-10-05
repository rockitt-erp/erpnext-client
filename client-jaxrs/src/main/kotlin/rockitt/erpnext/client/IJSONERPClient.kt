package rockitt.erpnext.client

import javax.json.JsonObject

interface IJSONERPClient: AutoCloseable {
    /***
     * Create a record of type @model with attributes defined in @payload
     */
    fun create(model: String, payload: JsonObject): JsonObject

    /***
     * Read the record of type @model with rest id of @id
     */
    fun read(model: String, id: String): JsonObject

    /***
     * Read all record ids of the model type @model
     */
    fun readAll(model: String, filters: List<Filter> = emptyList(), page: Int = 0, quantity: Int = 20): List<String>

    /***
     * Update a record of type @model with id @id and attributes defined in @data
     */
    fun update(model: String, id: String, data: JsonObject): JsonObject

    /***
     * Delete a record of type @model with id @id
     */
    fun delete(model: String, id: String): JsonObject
}