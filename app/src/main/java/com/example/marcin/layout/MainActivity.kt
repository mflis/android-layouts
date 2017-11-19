package com.example.marcin.layout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), TextWatcher {

    private lateinit var listView : ListView
    private lateinit var editText : EditText
    private lateinit var buttonAdd : Button
    private lateinit var buttonDelete: Button
    private lateinit var toDoList : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toDoList = ArrayList()
        initComponents()
    }

    private fun initComponents() {
        listView = findViewById(R.id.listView)
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listView.onItemClickListener = AdapterView.OnItemClickListener {
            _: AdapterView<*>, _: View, _: Int, _: Long -> onListViewItemClickAction()
        }
        updateListView()
        editText = findViewById(R.id.editText)
        editText.addTextChangedListener(this)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonAdd.isEnabled = false
        buttonAdd.setOnClickListener { buttonAddClickAction() }
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.isEnabled = false
        buttonDelete.setOnClickListener { buttonDeleteClickAction() }
    }

    private fun onListViewItemClickAction() {
        val checkedPositions = listView.checkedItemPositions
        buttonDelete.isEnabled = checkedPositions.size() != 0
    }

    private fun buttonAddClickAction() {
        val toDoThing : String = editText.text.toString()
        toDoList.add(toDoThing)
        updateListView()
        buttonAdd.isEnabled = false
        editText.text.clear()
    }

    private fun buttonDeleteClickAction() {
        val selectedToDos = listView.checkedItemPositions
        val toDelete : MutableList<String> = ArrayList()
        (0 until listView.count)
                .filter { selectedToDos.get(it) }
                .forEach { toDelete.add(toDoList[it]) }
        toDoList.removeAll(toDelete)
        updateListView()
        buttonDelete.isEnabled = false
    }

    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        buttonAdd.isEnabled = p0?.isNotEmpty()!!
    }

    private fun updateListView() {
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, toDoList)
    }

}
