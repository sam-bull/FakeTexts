package com.example.muzmatch.faketexts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muzmatch.faketexts.FakeTextsApplication
import com.example.muzmatch.faketexts.R
import com.example.muzmatch.faketexts.adaptor.MessagesAdaptor
import com.example.muzmatch.faketexts.databinding.FragmentMainBinding
import com.example.muzmatch.faketexts.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as FakeTextsApplication).component.inject(this)
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main, container, false
            )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        messages_recycler_view.layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
        messages_recycler_view.adapter = MessagesAdaptor(viewModel.messages)

        viewModel.sendMessage.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                val lastIndex = viewModel.messages.lastIndex
                viewModel.sendMessage(message_text.text.toString())
                message_text.text.clear()
                (messages_recycler_view.adapter as MessagesAdaptor).notifyItemInserted(viewModel.messages.lastIndex)
                (messages_recycler_view.adapter as MessagesAdaptor).notifyItemChanged(lastIndex)
                messages_recycler_view.scrollToPosition(viewModel.messages.lastIndex)
            }
        })
    }

}