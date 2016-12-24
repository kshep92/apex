import io.vertx.ext.unit.TestSuite

class RouteGroupTest extends AppTestSuite {

    static main(args) {
        TestSuite.create("RouteGroup Tests")
        .before({context ->
            startServer(context)
        })
        .test("Can get to sub router", {context ->
            promise(context)
            client.get("/sub").handler({response ->
                context.assertEquals(200, response.statusCode())
                response.bodyHandler({body ->
                    context.assertEquals("/sub", body.toString())
                    resolve()
                })
            }).end()
        })
        .after({context -> app.stop(context.asyncAssertSuccess())})
        .run(defaultTestOptions)
    }
}
