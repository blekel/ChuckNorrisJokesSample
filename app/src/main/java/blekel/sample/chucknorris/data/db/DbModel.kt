package blekel.sample.chucknorris.data.db

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

open class JokeDb : RealmObject() {
    companion object {
        const val FIELD_LIKED = "isLiked"
        const val FIELD_MY = "isMy"
    }

    @PrimaryKey
    var id = ""
    var text = ""

    @Index
    var isLiked = false
    @Index
    var isMy = false
}
