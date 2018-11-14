package blekel.sample.chucknorris.data.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

class ListResponseDto {
    var type = ""
    @SerializedName("value")
    var jokes = emptyList<JokeDto>()
}

class JokeDto {
    var id = 0L
    @SerializedName("joke")
    var text = ""
    var categories = emptyList<String>()
}
