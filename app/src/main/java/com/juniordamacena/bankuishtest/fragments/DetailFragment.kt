package com.juniordamacena.bankuishtest.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.juniordamacena.bankuishtest.R
import com.juniordamacena.bankuishtest.models.Repository
import com.juniordamacena.bankuishtest.databinding.FragmentDetailBinding
import com.juniordamacena.bankuishtest.viewmodels.RepositoriesViewModel
import java.text.ParseException
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: RepositoriesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repository: Repository?

        viewModel.loadRepositories()

        viewModel.repositories.observe(viewLifecycleOwner) { repositoryList ->
            repository = repositoryList.first { it.id == viewModel.selectedId }

            repository?.apply {
                (activity as AppCompatActivity).supportActionBar?.apply {
                    title = name
                    subtitle = full_name
                }

                binding.lblDescription.text = description

                try {
                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    val date = format.parse(updated_at)
                    binding.lblUpdatedAt.text =
                        getString(R.string.last_updated_text, date?.toLocaleString() ?: updated_at)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                binding.lblHomepage.text = homepage
                binding.lblHomepage.isVisible = !homepage.isNullOrBlank()

                binding.lblNumStars.text = getString(R.string.num_stars_text, stargazers_count)
                binding.lblNumWatchers.text = getString(R.string.num_watchers_text, watchers_count)
                binding.lblNumForks.text = getString(R.string.num_forks_text, forks_count)

                binding.btnOpenGithub.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(html_url))
                    startActivity(intent)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        (activity as AppCompatActivity).supportActionBar?.apply {
            subtitle = ""
        }
    }
}