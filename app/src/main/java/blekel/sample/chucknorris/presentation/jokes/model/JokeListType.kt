package blekel.sample.chucknorris.presentation.jokes.model

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

enum class JokeListType {
    MAIN,
    MY_JOKES;

    companion object {
        fun lookup(value: String): JokeListType {
            values().forEach {
                if (it.name == value) {
                    return it
                }
            }
            throw IllegalArgumentException("Invalid type: $value")
        }
    }
}
