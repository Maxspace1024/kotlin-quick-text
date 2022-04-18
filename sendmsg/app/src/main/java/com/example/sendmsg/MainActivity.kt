package com.example.sendmsg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.lang.Exception
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class MainActivity : AppCompatActivity() {

    private lateinit var ipaddr : String
    private lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ipaddr = getString(R.string.ipaddr)
        mainHandler = Handler(Looper.getMainLooper(),Handler.Callback {
            when(it.what){
                1 -> Log.d("infoo","asdfasdf")
                2 -> Log.d("infoo","asdfasdfx")
                else -> Log.d("infoo",it.obj as String)
            }
            true
        })

        btnSet.setOnClickListener {
            ipaddr = etIP.text.toString()
            Toast.makeText(this,"set ip=${ipaddr}",Toast.LENGTH_SHORT).show()
        }
        btnSend.setOnClickListener {
            var msg : String = "test"
            runOnUiThread {
                msg = etMSG.text.toString()
            }

            Thread{
                try{
                    val sock = Socket(ipaddr,9999)
                    val output = PrintWriter(sock.getOutputStream(),true)
                    val input = BufferedReader(InputStreamReader(sock.getInputStream()))

                    output.println(msg)
                    println("reply: ${input.readLine()}")

                    output.close()
                    input.close()
                    sock.close()
                }catch(e:Exception){
                    Message().also {
                        it.what=3
                        it.obj = e.toString()
                    }
                }

            }.start()

        }
    }
}