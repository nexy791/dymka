package com.ribsky.settings.adapter.settings

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.settings.R
import com.ribsky.settings.adapter.diff.DiffCallbackSettings
import com.ribsky.settings.adapter.settings.holder.LabelViewHolder
import com.ribsky.settings.adapter.settings.holder.SettingsViewHolder
import com.ribsky.settings.model.Settings

class SettingsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Settings, RecyclerView.ViewHolder>(DiffCallbackSettings) {

    fun interface OnClickListener {
        fun onClick(item: Settings.Type)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is Settings.LabelModel -> R.layout.item_label
        is Settings.SettingsModel -> R.layout.item_settings
        else -> error("error with viewType")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_label -> LabelViewHolder.create(parent)
            R.layout.item_settings -> SettingsViewHolder.create(parent)
            else -> error("bad viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Settings.LabelModel -> (holder as LabelViewHolder).bind(item)
            is Settings.SettingsModel -> (holder as SettingsViewHolder).bind(
                item,
                onClickListener
            )
        }
    }
}
