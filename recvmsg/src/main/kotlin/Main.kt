import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*

fun main(args: Array<String>) {
    val serv = ServerSocket(9999)
    val fp = File("msg.txt")
    while (true){
        val cli = serv.accept()
        val output = PrintWriter(cli.getOutputStream(),true)
        val input = BufferedReader(InputStreamReader(cli.getInputStream()))

        var text = input.readLine()
        println("MSG: ${text}")
        output.println("get the data")

        fp.appendBytes((text+"\n").toByteArray())

        cli.close()
        output.close()
        input.close()
        if(text=="^^^"){
            break
        }
    }
    serv.close()
}