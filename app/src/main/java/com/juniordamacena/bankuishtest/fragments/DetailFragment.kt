package com.juniordamacena.bankuishtest.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.juniordamacena.bankuishtest.R
import com.juniordamacena.bankuishtest.databinding.FragmentDetailBinding
import com.juniordamacena.bankuishtest.viewmodels.RepositoriesViewModel
import io.noties.markwon.Markwon
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import java.text.ParseException
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: RepositoriesViewModel by activityViewModels()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>

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
        setHasOptionsMenu(true)

        val peekDistance = resources.getDimension(R.dimen.bottom_sheet_peek_distance)

        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.bottomSheet.standardBottomSheet).apply {
                peekHeight = peekDistance.roundToInt()
                isFitToContents = true
                state = BottomSheetBehavior.STATE_COLLAPSED
            }

        viewModel.readMeText.observe(viewLifecycleOwner) { readMeText ->
            // obtain an instance of Markwon
            val markwon: Markwon = Markwon
                .builder(requireContext())
                .usePlugin(GlideImagesPlugin.create(Glide.with(requireContext())))
                .usePlugin(LinkifyPlugin.create())
                .build()

            // set markdown
            markwon.setMarkdown(binding.lblReadme, readMeText)
        }

        viewModel.selectedItem.observe(viewLifecycleOwner) { repository ->
            repository?.apply {

                viewModel.loadReadMeForRepository(this)

                (activity as AppCompatActivity).supportActionBar?.apply {
                    title = name
                    subtitle = full_name
                }

                binding.bottomSheet.lblDescription.text = description

                try {
                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    val date = format.parse(updated_at)
                    binding.bottomSheet.lblUpdatedAt.text =
                        getString(R.string.last_updated_text, date?.toLocaleString() ?: updated_at)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                binding.bottomSheet.lblHomepage.text = homepage
                binding.bottomSheet.lblHomepage.isVisible = !homepage.isNullOrBlank()

                binding.bottomSheet.lblNumStars.text =
                    getString(R.string.num_stars_text, stargazers_count)
                binding.bottomSheet.lblNumWatchers.text =
                    getString(R.string.num_watchers_text, watchers_count)
                binding.bottomSheet.lblNumForks.text =
                    getString(R.string.num_forks_text, forks_count)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val repository = viewModel.selectedItem.value

        return when (item.itemId) {
            R.id.menu_info -> {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                } else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }

                return true
            }
            R.id.menu_open_github -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repository?.html_url))
                startActivity(intent)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}