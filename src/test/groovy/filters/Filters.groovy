package filters

import com.reviselabs.apex.web.RequestHandler

/**
 * Created by Kevin on 12/24/2016.
 */
class Filters {

    static RequestHandler checkPermissions(String[] permissions) {
        assert permissions.length > 0
        return { context ->
            String[] userPermissions = context.get("permissions");
            if(userPermissions.find({ permissions.contains(it) })) context.next()
            else context.forbidden().end("You do not have permission to view this area.")
        }
    }
}
