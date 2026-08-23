package com.example.localassistant

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var messages: LinearLayout
    private lateinit var input: EditText
    private lateinit var mode: Button

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun textView(
        value: String,
        size: Float = 16f
    ) = TextView(this).apply {
        text = value
        textSize = size
        setTextColor(Color.WHITE)
        setPadding(
            dp(16),
            dp(10),
            dp(16),
            dp(10)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showChat()
    }

    private fun showChat() {

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(18, 18, 18))
        }

        val top = LinearLayout(this).apply {
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.rgb(28, 28, 28))
        }

        top.addView(
            Button(this).apply {
                text = "☰"
                setOnClickListener {
                    showInfo()
                }
            },
            LinearLayout.LayoutParams(
                dp(56),
                dp(56)
            )
        )

        top.addView(
            textView("Новый чат", 18f),
            LinearLayout.LayoutParams(
                0,
                dp(56),
                1f
            )
        )

        top.addView(
            Button(this).apply {
                text = "⋮"
                setOnClickListener {
                    showInfo()
                }
            },
            LinearLayout.LayoutParams(
                dp(56),
                dp(56)
            )
        )

        page.addView(top)

        messages = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(
                dp(10),
                dp(10),
                dp(10),
                dp(10)
            )
        }

        page.addView(
            ScrollView(this).apply {
                addView(messages)
            },
            LinearLayout.LayoutParams(
                -1,
                0,
                1f
            )
        )

        mode = Button(this).apply {
            text = "🌐  Авто"

            setOnClickListener {
                chooseMode()
            }
        }

        page.addView(
            mode,
            LinearLayout.LayoutParams(
                -1,
                dp(52)
            )
        )

        val row = LinearLayout(this).apply {
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.rgb(28, 28, 28))
        }

        input = EditText(this).apply {
            hint = "Напишите сообщение..."
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)

            setBackgroundColor(
                Color.rgb(36, 36, 36)
            )

            setPadding(
                dp(12),
                dp(8),
                dp(12),
                dp(8)
            )
        }

        row.addView(
            input,
            LinearLayout.LayoutParams(
                0,
                -2,
                1f
            )
        )

        row.addView(
            Button(this).apply {
                text = "➤"

                setOnClickListener {
                    send()
                }
            },
            LinearLayout.LayoutParams(
                dp(60),
                dp(56)
            )
        )

        page.addView(row)

        setContentView(page)
    }

    private fun send() {

        val query =
            input.text.toString().trim()

        if (query.isEmpty()) {
            return
        }

        messages.addView(
            textView("Вы: $query")
        )

        input.text.clear()

        val imageRequest =
            Regex(
                "(?i)(нарисуй|сгенерируй|изображен|картин)"
            ).containsMatchIn(query)

        val response =
            if (imageRequest) {

                "🎨 Запрос на изображение принят. " +
                "Локальный генератор будет подключён " +
                "на следующем этапе."

            } else {

                "🤖 Тестовая оболочка работает. " +
                "Настоящая локальная модель будет " +
                "подключена на следующем этапе."
            }

        messages.addView(
            textView(response)
        )
    }

    private fun chooseMode() {

        val options =
            arrayOf(
                "Выкл.",
                "Авто",
                "Вкл."
            )

        AlertDialog.Builder(this)
            .setTitle("Интернет-поиск")
            .setSingleChoiceItems(
                options,
                -1
            ) { dialog, which ->

                mode.text =
                    "🌐  ${options[which]}"

                dialog.dismiss()
            }
            .setNegativeButton(
                "Отмена",
                null
            )
            .show()
    }

    private fun showInfo() {

        AlertDialog.Builder(this)
            .setTitle(
                "LocalAIAssistant v0.1"
            )
            .setMessage(
                "Проверочная сборка интерфейса.\n\n" +
                "Локальная LLM, настоящий " +
                "интернет-поиск и генератор " +
                "изображений будут подключены " +
                "следующим этапом."
            )
            .setPositiveButton(
                "OK",
                null
            )
            .show()
    }
}
