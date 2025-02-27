package com.webers.applock.ui.callblocker.blacklist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.webers.applock.R
import com.webers.applock.data.database.callblocker.blacklist.BlackListItemEntity
import com.webers.applock.databinding.ItemCallBlockerBlacklistBinding
import com.webers.applock.util.extensions.inflateAdapterItem

class BlackListAdapter : RecyclerView.Adapter<BlackListAdapter.BlackListItemViewHolder>() {

    private val blackListItems = arrayListOf<BlackListItemViewState>()

    var onItemClicked: ((BlackListItemEntity) -> Unit)? = null

    fun setBlackListItems(blackListItems: List<BlackListItemViewState>) {
        this.blackListItems.clear()
        this.blackListItems.addAll(blackListItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = blackListItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlackListItemViewHolder =
        BlackListItemViewHolder.create(parent, onItemClicked)


    override fun onBindViewHolder(holder: BlackListItemViewHolder, position: Int) =
        holder.bind(blackListItems[position])

    class BlackListItemViewHolder(
        private val binding: ItemCallBlockerBlacklistBinding,
        private val onItemClicked: ((BlackListItemEntity) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked?.invoke(binding.viewState!!.blackListItemEntity)
            }
        }

        fun bind(blackListItemViewState: BlackListItemViewState) {
            binding.viewState = blackListItemViewState
            binding.executePendingBindings()
        }

        companion object {

            fun create(
                parent: ViewGroup,
                onItemClicked: ((BlackListItemEntity) -> Unit)?
            ): BlackListItemViewHolder {
                return BlackListItemViewHolder(
                    parent.inflateAdapterItem(R.layout.item_call_blocker_blacklist),
                    onItemClicked
                )
            }
        }

    }
}