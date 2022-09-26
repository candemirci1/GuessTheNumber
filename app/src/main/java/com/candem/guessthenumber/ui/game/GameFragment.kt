package com.candem.guessthenumber.ui.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.candem.guessthenumber.R
import com.candem.guessthenumber.databinding.FragmentGameBinding
import com.candem.guessthenumber.domain.model.Guess


class GameFragment : Fragment() {
    private var binding: FragmentGameBinding? = null
    private val numberSet: MutableSet<Int> = mutableSetOf()
    private val guessSet: MutableSet<Int> = mutableSetOf()
    private var adapter: GuessAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        while (numberSet.size < 4) {
            val digit = (0..9).random()
            if (!(digit == 0 && numberSet.isEmpty())) {
                numberSet.add(digit)
            }
        }

        Log.d("GameFragment", "computer number is: $numberSet")

        binding?.apply {
            btnOk.setOnClickListener {
                guessSet.clear()
                val guess = etGuess.text.toString()
                if (isValidGuess(guess)) {
                    guess.forEach { digit ->
                        guessSet.add(digit.toString().toInt())
                    }
                    etGuess.text.clear()
                    val result = checkForResult(numberSet, guessSet)
                    if (adapter == null) {
                        adapter = GuessAdapter()
                        rvGuesses.adapter = adapter
                        adapter?.insertGuess(Guess(guessSet, result))
                    } else {
                        adapter?.insertGuess(Guess(guessSet, result))
                    }

                }
            }
        }

    }

    private fun isValidGuess(guess: String): Boolean {
        if (guess[0] == '0') {
            Toast.makeText(requireContext(), "Cannot start with zero", Toast.LENGTH_SHORT).show()
            return false
        }

        var valid = true
        guess.forEachIndexed { index, digit ->
            if (index != 3) {
                for (i in index+1..3) {
                    if (digit == guess[i]) {
                        valid = false
                        Toast.makeText(requireContext(), "Sayilar ayni olamaz", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
            }
        }
        return valid
    }

    private fun checkForResult(numberSet: Set<Int>, guessSet: Set<Int>): Pair<Int, Int> {
        var correctPositionedNumberCount = 0
        var wrongPositionedNumberCount = 0
        numberSet.forEachIndexed { numberIndex, numberDigit ->
            guessSet.forEachIndexed { guessIndex, guessDigit ->
                if (numberDigit == guessDigit) {
                    if (numberIndex == guessIndex) {
                        correctPositionedNumberCount++
                    } else {
                        wrongPositionedNumberCount++
                    }
                }
            }
        }
        return Pair(correctPositionedNumberCount, wrongPositionedNumberCount)
    }


}