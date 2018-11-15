package blekel.sample.chucknorris.util.common;

import java.util.UUID;

/**
 * Created by Vitaliy Levonyuk on 15.06.17
 */

public class IdUtils {

    public static String createNewId() {
        return UUID.randomUUID().toString();
    }
}
