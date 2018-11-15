package blekel.sample.chucknorris.domain

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

data class Joke(
    val id: String,
    val text: String,
    var isLiked: Boolean = false,
    var isMy: Boolean = false
)

