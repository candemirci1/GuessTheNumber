package com.candem.guessthenumber.ui.scoreboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.candem.guessthenumber.R
import com.candem.guessthenumber.databinding.FragmentGameBinding
import com.candem.guessthenumber.databinding.FragmentScoreBinding
import com.candem.guessthenumber.ui.game.GameFragmentArgs
import com.candem.guessthenumber.ui.game.GameFragmentDirections


class ScoreFragment : Fragment() {
    private var binding: FragmentScoreBinding? = null
    private val args: ScoreFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvPoint?.text = args.score.toString()
        binding?.btnRestart?.setOnClickListener {
            val action = ScoreFragmentDirections.actionScoreFragmentToStartGameFragment()
            findNavController().navigate(action)
        }

    }


}