package com.candem.guessthenumber.ui.startscreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.candem.guessthenumber.databinding.FragmentStartGameBinding
import com.candem.guessthenumber.domain.model.GameArg


class StartGameFragment : Fragment() {

    private val viewModel: StartGameViewModel by viewModels()
    private var binding: FragmentStartGameBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartGameBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnStart?.setOnClickListener {
            val numberList = viewModel.createRandomNumber()
            val gameArg = GameArg(numberList)

            val action = StartGameFragmentDirections.actionStartGameFragmentToGameFragment(gameArg)
            findNavController().navigate(action)

            Log.d("StartGameFragment", "computer number is: $numberList")


        }
    }

}