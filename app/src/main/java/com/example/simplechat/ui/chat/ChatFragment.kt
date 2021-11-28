package com.example.simplechat.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplechat.R
import com.example.simplechat.databinding.FragmentChatBinding
import com.example.simplechat.ui.chat.recycler.ChatItemCompositeAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    @Inject
    lateinit var chatItemAdapter: ChatItemCompositeAdapter

    private val viewModel: ChatViewModel by viewModels()

    companion object {
        private const val USER_ID = "USER_ID"
        private const val CHAT_ID = "CHAT_ID"
        fun newInstance(userId: Int, chatId: Int) = ChatFragment().apply {
            arguments = bundleOf(
                USER_ID to userId,
                CHAT_ID to chatId
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("chatFragment", "onAct created")

        setupChatItemsRv()
        setChatInput()

        viewModel.isChatInputActive.observe(viewLifecycleOwner) { makeInputSectionActive(it) }

        with(arguments) {
            val userId: Int = this!!.getInt(USER_ID)
            val chatId: Int = this!!.getInt(CHAT_ID)
            setupViewModel(userId, chatId)
        }

        viewModel.chatItems().observe(viewLifecycleOwner) {
            chatItemAdapter.submitList(it)
            binding.rvChatItems.smoothScrollToPosition(it.size)
        }
    }

    private fun setupViewModel(userId: Int, chatId: Int) {
        viewModel.setUpUserId(userId)
        viewModel.setUpChatId(chatId)
        viewModel.initMessages()
    }

    private fun setChatInput() {
        binding.etChatInput.setOnFocusChangeListener { view, hasFocus ->
            Log.d("chatFragment", "has etfocus $hasFocus")
            if (!hasFocus) hideKeyboard(view)
        }
        binding.etChatInput.doAfterTextChanged { editable -> viewModel.textChanged(editable.toString()) }

        binding.btSend.setOnClickListener {
            binding.etChatInput.clearFocus()
            viewModel.sendClicked(binding.etChatInput.text.toString())
        }
    }

    private fun setupChatItemsRv() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvChatItems.layoutManager = layoutManager
        binding.rvChatItems.adapter = chatItemAdapter
        binding.rvChatItems.addOnLayoutChangeListener { v, i, i2, i3, i4, i5, i6, i7, i8 ->
            binding.rvChatItems.smoothScrollToPosition(
                chatItemAdapter.itemCount
            )
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager? = getSystemService(requireContext(), InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun makeInputSectionActive(active: Boolean) {
        with(binding.btSend) {
            isEnabled = active
            val alpha = if (active) 1F
            else 0.6F
            setAlpha(alpha)
        }

        val inputBorder = if (active) R.drawable.chat_input_border_active
        else R.drawable.chat_input_border_inactive

        with(binding.etChatInput) {
            setBackgroundResource(inputBorder)
            if (!active) text.clear()
        }
    }
}
