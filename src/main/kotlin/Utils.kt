import java.io.File

object Utils {
    fun readFileInput(fileName: String): List<String> {
        return File(fileName).readLines()
    }
}