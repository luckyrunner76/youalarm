package com.example.ingredient.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ingredient.R
import com.example.ingredient.*
import com.example.ingredient.dto.Todo

class todoAdapter3(val context: fridgeFragment) : RecyclerView.Adapter<todoAdapter3.TodoViewHolder>(){
    private var list = mutableListOf<Todo>()
    private var list2 = mutableListOf<Todo>()
    private var position2 = 0
    inner class TodoViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun onBind(data:Todo){
            var state = itemView.findViewById<TextView>(R.id.tv_item_state2)
            var title = itemView.findViewById<TextView>(R.id.tv_item_name2)
            var date = itemView.findViewById<TextView>(R.id.tv_item_date2)
            var count = itemView.findViewById<TextView>(R.id.tv_item_counts2)
            var delete = itemView.findViewById<TextView>(R.id.tv_delete2)

            if(data.state){
                state.text = "가공"
            }
            else{
                state.text = "신선"
            }
            title.text = data.title
            date.text = data.timestamp.toString()
            count.text = data.count

            title.setOnClickListener {
                itemCheckListener.onClick(it, layoutPosition, list[layoutPosition].id,list[layoutPosition].state)
            }
            delete.setOnClickListener{
                itemDeleteListener.onClick(it, layoutPosition, list[layoutPosition].id,list[layoutPosition].state)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list2, parent, false)
        return TodoViewHolder(v)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        if(list[position].place.equals("냉장실")){
            list2.add(list[position])
            holder.onBind(list2[position2])
            position2 += 1
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //이름,등록일,개수 만든거 구성
    fun update(newList:MutableList<Todo>){
        val new2 = mutableListOf<Todo>()
        if(newList.size >= 2){
            for(i : Int in 0 until newList.size){
                if(newList[i].place.equals("냉장실")){
                    new2.add(newList[i])
                }
            }
            this.list =  new2
            notifyDataSetChanged()
        }
        else if(newList.size == 1){
            if(newList[0].place.equals("냉장실")){
                new2.add(newList[0])
            }
            this.list = new2
            notifyDataSetChanged()
        }
        else if(newList.size == 0){
            this.list = newList
            notifyDataSetChanged()
        }
    }
    interface ItemCheckListener{
        fun onClick(view: View, position: Int, itemId:Long, state:Boolean)
    }

    interface ItemDeleteListener{
        fun onClick(view: View, position: Int, itemId:Long, state:Boolean)
    }

    private lateinit var itemDeleteListener : ItemDeleteListener

    private lateinit var itemCheckListener : ItemCheckListener


    fun setItemDeleteListener(itemDeleteListener: ItemDeleteListener){
        this.itemDeleteListener = itemDeleteListener
    }

    fun setItemCheckListener(itemCheckListener : ItemCheckListener){
        this.itemCheckListener = itemCheckListener
    }
}