package rockitt.erpnext.client

import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Cookie
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType
import kotlin.reflect.KClass

class ModelERPClient (
        private val client: Client,
        private val webTarget: WebTarget,
        private val authCookie: Cookie
): IModelERPClient {

    //
    //  Resource Accessors
    //

    override fun <T : Any> create(item: T): T? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any> read(klass: KClass<T>, id: Any): T? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun readAll(klass: KClass<*>, filters: List<Filter>, page: Int, quantity: Int): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any> update(item: T): T? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any> delete(item: T): T? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //
    //  Helpers
    //

    private fun WebTarget.setup(): Invocation.Builder {
        return this.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(authCookie)
    }

    private class DataList<T> {
        var data: List<T> = emptyList()
    }

    class DataMap {
        var data: Map<String, Any> = mutableMapOf()
        var _server_messages: Any = Any()
    }

    private class Name {
        val name: String = ""
    }

    private object DataListName: GenericType<DataList<Name>>()

    //
    //  AutoCloseable
    //

    override fun close() {
        client.close()
    }
}