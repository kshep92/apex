import io.vertx.ext.unit.TestSuite

class InterceptorsTest extends AppTestSuite {

    static main(args) {
        TestSuite.create("Interceptors Test")
        .before({context -> startServer(context) })
        .test("Ensure context data is written", { context ->
            promise(context)
            client.get("/sub/user_data").handler({ res ->
                context.assertEquals(200, res.statusCode())
                res.bodyHandler({ body ->
                    context.assertEquals(body.toString(), "kevin@mail.com")
                    resolve()
                })
            }).end()
        })
        .test("Chained interceptors", {context ->
            promise(context)
            client.get("/sub/allowed", { res ->
                context.assertEquals(200, res.statusCode())
                resolve()
            }).end()
        })
        .test("Chained interceptor blocks unauthorized users", { context ->
            promise(context)
            client.get("/sub/forbidden", {res ->
                context.assertEquals(403, res.statusCode())
                resolve()
            }).end()
        })
        .after({ context -> app.stop(context.asyncAssertSuccess()) })
        .run(defaultTestOptions)
    }
}
