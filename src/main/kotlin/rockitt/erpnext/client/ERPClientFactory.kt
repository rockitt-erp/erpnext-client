package rockitt.erpnext.client

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Cookie
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

object ERPClientFactory {
    private class LoginEntity(val usr: String, val pwd: String)

    fun buildJSONClient(uri: String, username: String, password: String): IJSONERPClient {
        val client = ClientBuilder.newClient()
        val webTarget = client.target(uri)

        val cookie = getResponseCookie(webTarget, username, password)
        val erpClient = JSONERPClient(client, webTarget, cookie)
        return erpClient
    }

    fun buildModelClient(uri: String, username: String, password: String): IModelERPClient {
        val client = ClientBuilder.newClient()
        val webTarget = client.target(uri)

        val cookie = getResponseCookie(webTarget, username, password)
        val erpClient = ModelERPClient(client, webTarget, cookie)
        return erpClient
    }

    private fun getResponseCookie(webTarget: WebTarget, username: String, password: String): Cookie {
        val response = webTarget.path("method/login")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(LoginEntity(username, password), MediaType.APPLICATION_JSON))

        if (response.status != Response.Status.OK.statusCode)
            throw IllegalStateException("Received response code ${response.status} when authenticating with ERP")

        if (!response.cookies.containsKey("sid"))
            throw IllegalStateException("No cookie with key 'sid' found in authentication response")

        val cookie = response.cookies["sid"]!!
        return cookie
    }
}