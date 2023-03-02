package uz.khan.myapplication

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import uz.khan.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Type {
        NOUGHT, CROSS
    }

    private var firstType = Type.CROSS
    private var currentType = Type.CROSS
    private var boardList = mutableListOf<Button>()
    private var cross = 0
    private var nought = 0

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBoard()


    }

    private fun initBoard() {

        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTrapped(view: View) {
        if (view !is Button)
            return
        addToBoar(view)

        if (checkForVictory(NOUGHT)) {
            nought++

            result("0 :")
        } else if (checkForVictory(CROSS)) {
            cross++
            result("X :")
        }


        if (fullBoard()) {
            result("Result")
        }


    }

    private fun checkForVictory(s: String): Boolean {
        //h
        if (match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s)) {
            return true
        }
        if (match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s)) {
            return true
        }
        if (match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s)) {
            return true
        }
        //v

        if (match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s)) {
            return true
        }
        if (match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s)) {
            return true
        }
        if (match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s)) {
            return true
        }
        // d
        if (match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s)) {
            return true
        }
        if (match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s)) {
            return true
        }





        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title: String) {
        val message = "\n0: $nought\n\nX: $cross"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Restart")
            { _, _ ->

                resetBoard()

            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {

        for (button in boardList) {
            button.text = ""
        }

        if (firstType == Type.NOUGHT)
            firstType = Type.CROSS
        else if (firstType == Type.CROSS)
            firstType = Type.NOUGHT

        currentType = firstType
        setTurnLabel()


    }

    private fun fullBoard(): Boolean {


        for (button in boardList) {
            if (button.text == "") {
                return false
            }
        }
        return true

    }

    private fun addToBoar(button: Button) {

        if (button.text != "")
            return
        if (currentType == Type.NOUGHT) {


            button.setTextColor(Color.parseColor("#FF3700B3"))
            button.text = NOUGHT
            currentType = Type.CROSS

        } else if (currentType == Type.CROSS) {

            button.text = CROSS
            currentType = Type.NOUGHT

        }


        setTurnLabel()

    }

    private fun setTurnLabel() {

        var gameText: String = ""
        if (currentType == Type.CROSS) {
            gameText = "Game: $CROSS"//x
        } else if (currentType == Type.NOUGHT) {
            gameText = "Game: $NOUGHT"//0
        }
        binding.txtInfo.text = gameText


    }

    companion object {

        const val NOUGHT = "0"
        const val CROSS = "X"

    }


}