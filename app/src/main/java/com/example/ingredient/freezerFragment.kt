package com.example.ingredient

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ingredient.adapter.todoAdapter2
import com.example.ingredient.dto.Todo
import com.example.ingredient.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [freezerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class freezerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var todoViewModel : TodoViewModel
    lateinit var todoAdapter: todoAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_freezer, container, false)

        val rv_board : RecyclerView = view.findViewById(R.id.rvTodoList1)

        todoAdapter = todoAdapter2(this)
        rv_board.layoutManager = LinearLayoutManager(context)
        rv_board.adapter = todoAdapter


        val showDialogButton = view.findViewById<Button>(R.id.inputInfo)
        showDialogButton.setOnClickListener {
            showDialog(requireContext())
        }
        todoViewModel.todoList.observe(viewLifecycleOwner){
            todoAdapter.update(it)
        }


        //삭제 기능
        todoAdapter.setItemDeleteListener(object: todoAdapter2.ItemDeleteListener {
            override fun onClick(view: View, position: Int, itemId: Long, state: Boolean) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)
                    todoViewModel.delete(todo)
                    todoViewModel.update(todo)
                }
            }
        })


        todoAdapter.setItemCheckListener(object : todoAdapter2.ItemCheckListener{
            override fun onClick(view: View, position:Int, itemId: Long, state:Boolean){
                CoroutineScope(Dispatchers.IO).launch{
                    if(state == true){
                        val todo = todoViewModel.getOne(itemId)
                        val intent = Intent(context,processedfood_input::class.java).apply {
                            putExtra("type","EDIT")
                            putExtra("item",todo)
                        }
                        requestActivity.launch(intent)
                        todoViewModel.update(todo)
                    }
                    else{
                        Log.d("temp", "false")
                        val todo = todoViewModel.getOne(itemId)
                        val intent = Intent(context, freshfood_input::class.java).apply{
                            putExtra("type","EDIT")
                            putExtra("item",todo)
                        }
                        requestActivity.launch(intent)
                        todoViewModel.update(todo)
                    }



                }
            }
        })

        return view
    }

    private fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_option)

        // 다이얼로그 내부 버튼 등의 요소에 대한 처리 코드 작성
        val processedfoodInput = dialog.findViewById<Button>(R.id.ProcessedFood)
        processedfoodInput.setOnClickListener {
            val intent = Intent(context, processedfood_input::class.java).apply{
                putExtra("type","ADD")
            }
            requestActivity.launch(intent)
            dialog.dismiss()
        }

        val freshfoodInput = dialog.findViewById<Button>(R.id.FreshFood)
        freshfoodInput.setOnClickListener {
            val intent = Intent(context, freshfood_input::class.java).apply{
                putExtra("type","ADD")
            }
            requestActivity.launch(intent)
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment freezerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            freezerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val todo = it.data?.getSerializableExtra("todo")as Todo

            when(it.data?.getIntExtra("flag",-1)){
                0->{
                    CoroutineScope(Dispatchers.IO).launch{

                        todoViewModel.insert(todo)


                    }
                }
                1 ->{
                    CoroutineScope(Dispatchers.IO).launch{
                        todoViewModel.update(todo)
                    }
                }
            }
        }
    }

}