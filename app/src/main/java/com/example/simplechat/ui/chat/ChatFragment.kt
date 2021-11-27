package com.example.simplechat.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplechat.R
import com.example.simplechat.databinding.FragmentChatBinding
import com.example.simplechat.ui.chat.recycler.ChatItemCompositeAdapter
import com.example.simplechat.util.DummyDataProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    @Inject lateinit var chatItemAdapter: ChatItemCompositeAdapter

    private val viewModel: ChatViewModel by viewModels()

    companion object {
        fun newInstance() = ChatFragment()
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

        setupChatItemsRv()
        setChatInput()

        viewModel.isChatInputActive.observe(viewLifecycleOwner) { makeInputSectionActive(it) }
        Log.d("chatFragment", "onAct created")
    }

    private fun setChatInput() {
        binding.etChatInput.setOnFocusChangeListener { view, hasFocus ->
            Log.d("chatFragment", "has etfocus $hasFocus")
            if (!hasFocus) hideKeyboard(view)
        }
        binding.etChatInput.doAfterTextChanged { editable -> viewModel.textChanged(editable.toString()) }

        binding.btSend.setOnClickListener {
            binding.etChatInput.clearFocus()
            viewModel.sendClicked()
        }
    }

    private fun setupChatItemsRv() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvChatItems.layoutManager = layoutManager
        binding.rvChatItems.adapter = chatItemAdapter
        chatItemAdapter.submitList(DummyDataProvider().createDummy())
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
