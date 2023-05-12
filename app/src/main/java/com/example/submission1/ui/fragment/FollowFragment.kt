package com.example.submission1.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.ui.viewmodels.FollowViewModel
import com.example.submission1.adapter.UserAdapter
import com.example.submission1.databinding.FragmentFollowBinding
import com.example.submission1.model.ItemsItem

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private var position: Int? = null
    private var username: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listFollow.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listFollow.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.listFollow.addItemDecoration(itemDecoration)

        var followViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            followViewModel.setFollowers(username.toString())
            followViewModel.followers.observe(requireActivity()) {
                setFollowData(it)
            }
        } else {
            followViewModel.setFollowing(username.toString())
            followViewModel.following.observe(requireActivity()) {
                setFollowData(it)
            }
        }
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowData(followList: List<ItemsItem>) {
        val adapter = UserAdapter(followList)
        binding.listFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}