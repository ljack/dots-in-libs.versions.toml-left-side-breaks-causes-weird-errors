import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun main() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    println("Hello, world! It is $now")
}
