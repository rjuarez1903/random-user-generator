package app.rodrigojuarez.dev.retrofitpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import app.rodrigojuarez.dev.retrofitpractice.models.User
import app.rodrigojuarez.dev.retrofitpractice.models.UserViewModel
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        observeViewModel()
        viewModel.getUser()

        findViewById<Button>(R.id.buttonGetNewUser).setOnClickListener {
            viewModel.getUser()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->

            if (state.isLoading) {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                findViewById<ConstraintLayout>(R.id.userDataContainer).visibility = View.GONE
            } else {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.userDataContainer).visibility = View.VISIBLE
            }

            state.user?.let {
                displayUserData(it.results.first())
                findViewById<ConstraintLayout>(R.id.userDataContainer).visibility = View.VISIBLE
            }

            if (state.errorMessage.isNotEmpty()) {
                findViewById<TextView>(R.id.textViewError).apply {
                    text = state.errorMessage
                    visibility = View.VISIBLE
                }
                findViewById<ConstraintLayout>(R.id.userDataContainer).visibility = View.GONE
            } else {
                findViewById<TextView>(R.id.textViewError).visibility = View.GONE
            }
        }
    }


    private fun displayUserData(user: User) {
        findViewById<TextView>(R.id.textViewName).text =
            "${user.name.title} ${user.name.first} ${user.name.last}"
        findViewById<TextView>(R.id.textViewEmail).text = user.email
        findViewById<TextView>(R.id.textViewLocation).text =
            "${user.location.city}, ${user.location.country}"

        Glide.with(this)
            .load(user.picture.large)
            .into(findViewById(R.id.imageViewProfile))

        findViewById<ConstraintLayout>(R.id.userDataContainer).visibility = View.VISIBLE

        Log.d("User data", "Data loaded and views updated.")
    }
}
