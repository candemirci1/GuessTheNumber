package com.candem.guessthenumber.ui.profilescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.candem.guessthenumber.R
import com.candem.guessthenumber.databinding.FragmentGameBinding
import com.candem.guessthenumber.databinding.FragmentProfileBinding
import com.candem.guessthenumber.databinding.FragmentScoreBinding
import com.candem.guessthenumber.ui.scoreboard.ScoreViewModel
import com.candem.guessthenumber.util.Constants.KEY_GAME_PLAYED_COUNT
import com.candem.guessthenumber.util.Constants.KEY_SCORE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStatistic(KEY_SCORE, KEY_GAME_PLAYED_COUNT)

        lifecycleScope.launch {
            viewModel.state.collect {
                binding?.tvTotalPointValue?.text = it.score.toString()
                binding?.tvTotalGameValue?.text = it.count.toString()
                val average = if(it.count == 0){
                    0
                } else {
                    it.score / it.count
                }
                binding?.tvAverageValue?.text = average.toString()
            }
        }



    }


}