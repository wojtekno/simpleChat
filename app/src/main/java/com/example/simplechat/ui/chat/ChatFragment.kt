package com.example.simplechat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplechat.databinding.FragmentChatBinding
import com.example.simplechat.ui.chat.recycler.ChatItemCompositeAdapter
import com.example.simplechat.ui.chat.recycler.delegates.MessageReceivedDelegateAdapter
import com.example.simplechat.ui.chat.recycler.delegates.MessageSentDelegateAdapter
import com.example.simplechat.util.DummyDataProvider

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val chatItemAdapter: ChatItemCompositeAdapter by lazy {
        ChatItemCompositeAdapter.Builder()
            .add(MessageSentDelegateAdapter())
            .add(MessageReceivedDelegateAdapter())
            .build()
    }

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
//        return inflater.inflate(R.layout.fragment_chat, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvChatItems.layoutManager = layoutManager

        binding.rvChatItems.adapter = chatItemAdapter
        chatItemAdapter.submitList(DummyDataProvider().createDummy())
        chatItemAdapter.notifyDataSetChanged()
    }

}