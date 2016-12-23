import io.vertx.ext.unit.TestSuite

public class RoutingComponentTest extends AppTestSuite {

    public static void main(String[] args) {
        TestSuite.create("ApexApplication Tests")
            .before({context ->
                // Start the application and configure routes
                startServer(context);
            })
            .test("GET request", { context ->
                promise(context)
                client.get('/', { res ->
                    context.assertEquals(200, res.statusCode())
                    res.bodyHandler({body ->
                        context.assertTrue(body.toString().equals("OK"))
                        resolve()
                    })
                }).end()
            })
            .test("POST request", {context ->
                promise(context)
                String params = [name: 'Kevin', age: 27].collect { it }.join('&')
                client.post('/', { res ->
                    context.assertEquals(200, res.statusCode())
                    res.bodyHandler({body ->
                        context.assertTrue(body.toString().equals(params))
                        println params
                        resolve()
                    })
                }).putHeader("content-length", "${params.size()}").write(params).end()
            })
            .test("PUT request", {context ->
                promise(context)
                String params = [id: 10002, name: 'Kevin', email: 'kevin@mail.com'].collect { it }.join('&')
                client.put('/', { res ->
                    context.assertEquals(200, res.statusCode())
                    res.bodyHandler({body ->
                        context.assertTrue(body.toString().equals(params))
                        println params
                        resolve()
                    })
                }).putHeader("content-length", "${params.size()}").write(params).end()

            })
            .test("DELETE request", { context ->
                promise(context)
                client.delete('/', { res ->
                    context.assertEquals(200, res.statusCode())
                    res.bodyHandler({body ->
                        context.assertTrue(body.toString().equals('DELETED'))
                        resolve()
                    })
                }).end()
            })
            .after({context ->
                // Shut down the application
                app.stop(context.asyncAssertSuccess());
            })
            .run(defaultTestOptions);
    }
}
