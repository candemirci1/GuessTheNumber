package com.candem.guessthenumber.ui.game

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.candem.guessthenumber.R
import com.candem.guessthenumber.databinding.FragmentGameBinding
import com.candem.guessthenumber.domain.model.Guess
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        adapter = GuessAdapter { isCorrect,score ->
                            if (isCorrect) {
                                val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score)
                                findNavController().navigate(action)
                                showOnSnackBar(
                                    "WINNER",
                                    R.drawable.bg_round_winner,
                                    SnackbarGravity.BOTTOM
                                )
                            } else {
                                showOnSnackBar(
                                    "TRY AGAIN",
                                    R.drawable.bg_round_again
                                )

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
        if (guess.length != 4) {
            showOnSnackBar(
                "Enter Number With 4 digit",
                R.drawable.bg_round_error
            )
            return false
        }
        if (guess[0] == '0') {
            showOnSnackBar(
                "Cannot start with zero",
                R.drawable.bg_round_error
            )
            return false
        }
        var valid = true
        guess.forEachIndexed { index, digit ->
            if (index != 3) {
                for (i in index + 1..3) {
                    if (digit == guess[i]) {
                        valid = false
                        showOnSnackBar(
                            "Cannot include same number",
                            R.drawable.bg_round_error
                        )
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

    private fun showOnSnackBar(
        message: String,
        background: Int,
        gravity: SnackbarGravity = SnackbarGravity.CENTER
    ) {
        activity?.let {
            val snackBar = Snackbar.make(
                it.findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_SHORT
            )


            val params = snackBar.view.layoutParams as (FrameLayout.LayoutParams)
            params.setMargins(16, 0, 16, 32)
            snackBar.view.layoutParams = params
            when (gravity) {
                SnackbarGravity.TOP -> params.gravity = Gravity.TOP
                SnackbarGravity.CENTER -> params.gravity = Gravity.CENTER
                SnackbarGravity.BOTTOM -> params.gravity = Gravity.BOTTOM
            }
            params.width = FrameLayout.LayoutParams.WRAP_CONTENT
            snackBar.view.background = ContextCompat.getDrawable(requireContext(), background)

            snackBar.show()
        }
    }

    enum class SnackbarGravity {
        TOP, BOTTOM, CENTER
    }
}