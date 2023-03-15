import org.apache.commons.csv.CSVFormat
import smile.classification.knn
import smile.validation.CrossValidation
import java.io.FileReader

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    val x1 = mutableListOf<DoubleArray>()
    val reader = FileReader("../matrix.csv")
    val records = CSVFormat.Builder.create(CSVFormat.EXCEL).setDelimiter(' ').setTrim(false).build().parse(reader)
    for (record in records) {
        val list = record.toList()
        list.remove("")
        x1.add(list.map { it.toDouble() }.toDoubleArray())
//        val rec : List<Double> = record.toList().map { it -> it.toDouble() }
//        println(rec)
    }
    val arr = IntArray(x1.size) { 0 }
    // 1, 18, 30, 84, 99
    // 43, 25, 88
    arr[0] = 1
    arr[17] = 1
    arr[29] = 1
    arr[83] = 1
    arr[98] = 1
    arr[42] = 2
    arr[24] = 2
    arr[87] = 2
    val x = x1.toTypedArray()
    val classifier = knn(x, arr, 3)
    for (i in 0..arr.size - 1) {
        println("$i : ${classifier.predict(x[i])}")
    }
    println(CrossValidation.classification(15, x, arr, { indata, y -> knn(indata, y, 3) }))
}
