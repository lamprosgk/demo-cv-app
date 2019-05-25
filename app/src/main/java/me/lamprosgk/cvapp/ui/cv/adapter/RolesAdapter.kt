package me.lamprosgk.cvapp.ui.cv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.role_item.*
import me.lamprosgk.cvapp.R
import me.lamprosgk.cvapp.model.WorkExperience

class RolesAdapter : RecyclerView.Adapter<RolesAdapter.ViewHolder>() {

    private var roles: List<WorkExperience>? = null

    fun setItems(roles: List<WorkExperience>?) {
        this.roles = roles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.role_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        roles?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = roles?.size ?: 0

    /**
     * Using experimental LayoutContainer
     * https://kotlinlang.org/docs/tutorials/android-plugin.html#layoutcontainer-support
     */
    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val context: Context = containerView.context

        fun bind(experience: WorkExperience) {
            with(experience) {
                roleCompany.text = context.getString(R.string.role_item_company, company.name, company.location)
                roleTitle.text = role
                roleDate.text = context.getString(R.string.role_item_date, period.from, period.to)
            }
        }
    }
}
