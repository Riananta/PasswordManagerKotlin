package com.example.passwordmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.R
import com.example.passwordmanager.data.entity.Password
import com.example.passwordmanager.utils.EncryptionUtil

class PasswordAdapter(var list: List<Password>) : RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {
    private lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog){
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var username: TextView
        var password: TextView

        init {
            title = view.findViewById(R.id.title)
            username = view.findViewById(R.id.username)
            password = view.findViewById(R.id.password)
            view.setOnClickListener{
                dialog.onClick(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_password, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val decryptedPassword = EncryptionUtil.decrypt(list[position].password)
        val decryptedPassword = list[position].password
        holder.title.text = list[position].title
        holder.username.text = list[position].username
        holder.password.text = decryptedPassword
    }
}