package com.juniordamacena.bankuishtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juniordamacena.bankuishtest.R
import com.juniordamacena.bankuishtest.databinding.ItemRepositoryBinding
import com.juniordamacena.bankuishtest.models.Repository

/*Created by junio on 28/05/2022.*/
class RepositoriesAdapter(
    private val onClick: (Repository) -> Unit,
    diffCallback: DiffUtil.ItemCallback<Repository>,
) : PagingDataAdapter<Repository, RepositoriesAdapter.RepositoryViewHolder>(diffCallback) {

    inner class RepositoryViewHolder(val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.binding.apply {
            val repository = getItem(position)

            if (repository == null) return

            lblNameRepo.text = repository.full_name
            lblDescription.text = repository.description

            holder.itemView.setOnClickListener {
                onClick(repository)
            }

            Glide.with(holder.itemView.context)
                .load(repository.owner.avatar_url)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(imgAuthor)
        }
    }
}