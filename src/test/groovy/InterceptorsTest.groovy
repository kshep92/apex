import io.vertx.ext.unit.TestSuite

class InterceptorsTest extends AppTestSuite {

    static main(args) {
        TestSuite.create("Interceptors Test")
        .before({context -> startServer(context) })
        .test("Ensure context data is written", { context ->
            promise(context)
            client.get("/sub/user_data").handler({ res ->
                context.assertEquals(200, res.statusCode())
                resolve()
                res.bodyHandler({ body ->
                    context.assertEquals(body.toString(), "kevin@mail.com")
                    resolve()
                })
            }).end()
        })
        .test("Chained interceptors", {context ->
            promise(context)
            client.get("/sub/someroute", {res ->
                resolve()
            }).end()
        })
        .after({ context -> app.stop(context.asyncAssertSuccess()) })
        .run(defaultTestOptions)
    }
}
