package com.candem.guessthenumber.ui.game

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.candem.guessthenumber.databinding.ItemGuessBinding
import com.candem.guessthenumber.domain.model.Guess

class GuessAdapter(
    private val onCorrectGuess: (Boolean) -> Unit
) : RecyclerView.Adapter<GuessAdapter.GuessViewHolder>() {

    private val guesses: MutableList<Guess> = mutableListOf()

    class GuessViewHolder(val binding: ItemGuessBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuessViewHolder {
        val binding = ItemGuessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuessViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GuessViewHolder, position: Int) {
        val currentGuess = guesses[position]
        holder.binding.apply {
            guessedNumber.text = getPrettyGuess(position, currentGuess.guessedNumber.toList())
            positiveCount.text = "+${currentGuess.result.first}"
            negativeCount.text = "-${currentGuess.result.second}"
            if (currentGuess.result.first == 0) {
                positiveCount.isVisible = false
            }
            if (currentGuess.result.second == 0) {
                negativeCount.isVisible = false
            }
            if (currentGuess.result.first == 0 && currentGuess.result.second == 0) {
                positiveCount.text = "0"
                positiveCount.isVisible = true
                negativeCount.isVisible = false
            }
            onCorrectGuess.invoke(currentGuess.result.first == 4)

        }
    }

    private fun getPrettyGuess(position: Int, guess: List<Int>): String {
        val number = guess[0] * 1000 +
                guess[1] * 100 +
                guess[2] * 10 +
                guess[3]
        return "${position + 1}) $number"
    }

    override fun getItemCount(): Int {
        return guesses.size
    }

    fun insertGuess(guess: Guess) {
        guesses.add(guess)
        notifyItemInserted(guesses.size)
    }

}