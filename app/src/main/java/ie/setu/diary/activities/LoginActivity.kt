package ie.setu.diary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ie.setu.diary.R

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private fun isValid(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.length >= 6
    }

    private fun authenticate(username: String, password: String): Boolean {
        // Replace the following with your actual authentication logic
        return username == "myusername" && password == "mypassword"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isValid(username, password)) {
                if (authenticate(username, password)) {
                    val intent = Intent(this, EntriesListActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

