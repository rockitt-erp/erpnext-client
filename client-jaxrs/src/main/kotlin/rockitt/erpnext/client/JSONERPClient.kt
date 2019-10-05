package rockitt.erpnext.client

import javax.json.JsonObject
import javax.ws.rs.client.*
import javax.ws.rs.core.Cookie
import javax.ws.rs.core.MediaType

class JSONERPClient (
        private val client: Client,
        private val webTarget: WebTarget,
        private val authCookie: Cookie
): IJSONERPClient {

    //
    //  Resource Accessors
    //

    override fun create(model: String, payload: JsonObject): JsonObject {
        return webTarget.path("model/$model")
                .setup()
                .post(Entity.json(payload), JsonObject::class.java)
    }

    override fun read(model: String, id: String): JsonObject {
        return webTarget.path("resource/$model")
                .path("/$id")
                .setup()
                .get(JsonObject::class.java)
                .getJsonObject("data")
    }

    override fun readAll(model: String, filters: List<Filter>, page: Int, quantity: Int): List<String> {
        var builder = webTarget.path("model/$model")
                .queryParam("limit_start", page*quantity)
                .queryParam("limit_page_length", quantity)

        if (filters.isNotEmpty()) {
            val filterParam = "[" + filters.map { "[\"$model\",\"${it.field}\",\"${it.operator}\",\"${it.value}\"]" }.joinToString(",") + "]"
            builder = builder.queryParam("filters", filterParam)
        }

        return builder.setup()
                .get(JsonObject::class.java)
                .getJsonArray("data")
                .map { it.asJsonObject().getString("name") as String }
    }

    override fun update(model: String, id: String, data: JsonObject): JsonObject {
        return webTarget.path("resource/$model")
                .path("/$id")
                .setup()
                .put(Entity.json(data), JsonObject::class.java)
    }

    override fun delete(model: String, id: String): JsonObject {
        return webTarget.path("resource/$model")
                .path("/$id")
                .setup()
                .delete(JsonObject::class.java)
    }

    //
    //  Helpers
    //

    private fun WebTarget.setup(): Invocation.Builder {
        return this.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(authCookie)
    }

    //
    //  AutoCloseable
    //

    override fun close() {
        client.close()
    }
}