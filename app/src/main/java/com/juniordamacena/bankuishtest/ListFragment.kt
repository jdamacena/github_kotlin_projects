package com.juniordamacena.bankuishtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juniordamacena.bankuishtest.databinding.FragmentListBinding

const val TAG = "FirstFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var repositoriesAdapter: RepositoriesAdapter

    private val model: RepositoriesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.progressBar.isVisible = true

        model.getRepositories().observe(viewLifecycleOwner) { repositoryList ->
            repositoriesAdapter.repositories = repositoryList
            binding.progressBar.isVisible = false
        }
    }

    /**
     * Setting up the recycler view
     */
    private fun setupRecyclerView() = binding.rvRepos.apply {
        repositoriesAdapter = RepositoriesAdapter { repository: Repository ->
            adapterOnClick(repository)
        }
        adapter = repositoriesAdapter
        layoutManager = LinearLayoutManager(context)
    }

    /* Opens the details screen when RecyclerView item is clicked. */
    private fun adapterOnClick(repository: Repository) {
        model.selectedId = repository.id
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        Snackbar.make(requireView(), "Repository ${repository.name}", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}