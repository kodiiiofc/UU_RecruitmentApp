package com.kodiiiofc.urbanuniversity.recruitment

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var firstNameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var ageET: EditText
    private lateinit var occupationSp: Spinner
    private lateinit var submitBtn: Button
    private lateinit var personsLV: ListView

    private val persons = mutableListOf<Person>()
    private var personsFiltered = listOf<Person>()
    private var filterListAdapter: ListAdapter? = null
    private var currentFilter = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        firstNameET = findViewById(R.id.et_first_name)
        lastNameET = findViewById(R.id.et_last_name)
        ageET = findViewById(R.id.et_age)
        occupationSp = findViewById(R.id.sp_occupation)
        submitBtn = findViewById(R.id.btn_submit)
        personsLV = findViewById(R.id.lv_persons)

        filterListAdapter = ListAdapter(this, personsFiltered)
        personsLV.adapter = filterListAdapter

        submitBtn.setOnClickListener {
            if (firstNameET.text.isNotEmpty() && lastNameET.text.isNotEmpty() && ageET.text.isNotEmpty()) {
                val person = Person(
                    firstNameET.text.toString(),
                    lastNameET.text.toString(),
                    ageET.text.toString().toInt(),
                    occupationSp.selectedItem.toString()
                )
                persons.add(person)
                personsFiltered = persons.filter { person -> person.occupation == currentFilter }
                filterListAdapter = ListAdapter(this, personsFiltered)
                personsLV.adapter = filterListAdapter
            }
        }

        personsLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val person = personsFiltered[position]
                AlertDialog.Builder(this)
                    .setTitle("Внимание!")
                    .setMessage("Вы действительно хотите удалить ${person.firstName} ${person.lastName}?")
                    .setPositiveButton("Да") { _, _ ->
                        filterListAdapter?.remove(person)
                        persons.remove(person)
                    }
                    .setNegativeButton("Нет", null)
                    .create()
                    .show()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val spinner = menu?.findItem(R.id.menu_spinner)?.actionView as Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.occupations,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val itemSelector = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentFilter = spinner.selectedItem.toString()
                Toast.makeText(applicationContext, currentFilter, Toast.LENGTH_SHORT).show()
                personsFiltered = persons.filter { person -> person.occupation == currentFilter }
                filterListAdapter = ListAdapter(this@MainActivity, personsFiltered)
                personsLV.adapter = filterListAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        spinner.onItemSelectedListener = itemSelector

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> finish()

        }

        return super.onOptionsItemSelected(item)
    }


}