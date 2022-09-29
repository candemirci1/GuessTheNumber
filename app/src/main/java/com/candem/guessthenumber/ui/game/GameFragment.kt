package com.candem.guessthenumber.ui.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.candem.guessthenumber.databinding.FragmentGameBinding
import com.candem.guessthenumber.domain.model.Guess
import com.candem.guessthenumber.extensions.random
import com.candem.guessthenumber.extensions.showMessage


class GameFragment : Fragment() {
    private var binding: FragmentGameBinding? = null
    private val args: GameFragmentArgs by navArgs()
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



        binding?.apply {
            btnOk.setOnClickListener {
                guessSet.clear()
                val guess = etGuess.text.toString()
                if (isValidGuess(guess)) {
                    guess.forEach { digit ->
                        guessSet.add(digit.toString().toInt())
                    }
                    etGuess.text.clear()
                    val result = checkForResult(args.gameArg.number, guessSet)
                    if (adapter == null) {
                        adapter = GuessAdapter { isCorrect ->
                            if (isCorrect){
                                requireContext().showMessage("Winner")
                            } else {
                                requireContext().showMessage("TRY AGAIN")
                            }
                        }
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
        if(guess.length != 4){
            requireContext().showMessage("Enter Number With 4 digit")
            return false
        }
        if (guess[0] == '0') {
            requireContext().showMessage("Cannot start with zero")
            return false
        }

        var valid = true
        guess.forEachIndexed { index, digit ->
            if (index != 3) {
                for (i in index+1..3) {
                    if (digit == guess[i]) {
                        valid = false
                        requireContext().showMessage("Cannot include same number")
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