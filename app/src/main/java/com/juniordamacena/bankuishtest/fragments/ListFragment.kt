package com.juniordamacena.bankuishtest.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juniordamacena.bankuishtest.R
import com.juniordamacena.bankuishtest.Repository
import com.juniordamacena.bankuishtest.adapters.RepositoriesAdapter
import com.juniordamacena.bankuishtest.databinding.FragmentListBinding
import com.juniordamacena.bankuishtest.viewmodels.RepositoriesViewModel

const val TAG = "FirstFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var repositoriesAdapter: RepositoriesAdapter

    private val model: RepositoriesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupRecyclerView()

        loadData()

        binding.swipeRefresh.setOnRefreshListener {
            loadData(true)
        }
    }

    private fun loadData(refresh: Boolean = false) {
        binding.swipeRefresh.isRefreshing = true

        model.getRepositories(refresh).observe(viewLifecycleOwner) { repositoryList ->
            repositoriesAdapter.repositories = repositoryList
            binding.swipeRefresh.isRefreshing = false
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menu_refresh -> {
                loadData(true)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}