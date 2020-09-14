package com.example.muzmatch.faketexts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muzmatch.faketexts.R
import com.example.muzmatch.faketexts.adaptor.MessagesAdaptor
import com.example.muzmatch.faketexts.databinding.FragmentMainBinding
import com.example.muzmatch.faketexts.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        viewModel = MainViewModel()
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        messages_recycler_view.layoutManager = LinearLayoutManager(context)
        messages_recycler_view.adapter =
            MessagesAdaptor(viewModel.messages)

        viewModel.sendMessage.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                viewModel.sendMessage(message_text.text.toString())
                message_text.text.clear()
                (messages_recycler_view.adapter as MessagesAdaptor).notifyDataSetChanged()
            }
        })

    }

}