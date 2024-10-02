package com.kodiiiofc.urbanuniversity.recruitment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context, list: List<Person>) : ArrayAdapter<Person>(context,R.layout.list_item,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val firstNameTV = view?.findViewById<TextView>(R.id.tv_first_name)
        val lastNameTV = view?.findViewById<TextView>(R.id.tv_last_name)
        val occupationTV = view?.findViewById<TextView>(R.id.tv_occupation)
        val ageTV = view?.findViewById<TextView>(R.id.tv_age)

        firstNameTV?.text = person?.firstName
        lastNameTV?.text = person?.lastName
        occupationTV?.text = person?.occupation
        ageTV?.text = person?.age.toString()

        return view!!
    }

}