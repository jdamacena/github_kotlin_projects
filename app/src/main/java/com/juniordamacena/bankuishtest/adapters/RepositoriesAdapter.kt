package com.juniordamacena.bankuishtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juniordamacena.bankuishtest.R
import com.juniordamacena.bankuishtest.models.Repository
import com.juniordamacena.bankuishtest.databinding.ItemRepositoryBinding

/*Created by junio on 28/05/2022.*/
class RepositoriesAdapter(private val onClick: (Repository) -> Unit) :
    RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>() {

    inner class RepositoryViewHolder(val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var repositories: List<Repository>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.binding.apply {
            val repository = repositories[position]

            lblNameRepo.text = repository.name
            lblNameAuthor.text = repository.owner.login
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