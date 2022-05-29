package com.juniordamacena.bankuishtest

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
import com.juniordamacena.bankuishtest.databinding.FragmentDetailBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val model: RepositoriesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repository: Repository?

        model.getRepositories().observe(viewLifecycleOwner) { repositoryList ->
            repository = repositoryList.toMutableList().first { it.id == model.selectedId }

            repository?.apply {
                (activity as AppCompatActivity).supportActionBar?.apply {
                    title = name
                    subtitle = full_name
                }

                binding.lblDescription.text = description

                binding.lblHomepage.text = homepage
                binding.lblHomepage.isVisible = !homepage.isNullOrBlank()

                binding.lblNumStars.text = "$stargazers_count Stars"
                binding.lblNumWatchers.text = "$watchers_count Watchers"
                binding.lblNumForks.text = "$forks_count Forks"

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