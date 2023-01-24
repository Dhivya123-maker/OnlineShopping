package com.example.onlineshopping.itemsList

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshopping.R
import com.example.onlineshopping.RvAdapter
import com.example.onlineshopping.database.Item
import com.example.onlineshopping.database.ItemsDatabase
import com.example.onlineshopping.databinding.FragmentItemsListBinding
import com.google.android.material.snackbar.Snackbar


class ItemsListFragment : Fragment() {
    private var _binding: FragmentItemsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ItemsListViewModel

    lateinit var id : String
    lateinit var name : String
    lateinit var price : String

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = ItemsDatabase.getInstance(application).itemsDao

        val viewModelFactory = ItemsListViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemsListViewModel::class.java]
        binding.itemsListViewModel = viewModel // data binding
        binding.lifecycleOwner = viewLifecycleOwner // live data for data binding



        val itemPlusButtonClickListener: (item: Item) -> Unit = { item ->
            viewModel.incrementQuantityForItem(item)
        }

        val itemMinusButtonClickListener: (item: Item) -> Unit = { item ->
            viewModel.decrementQuantityForItem(item)
        }

        val adapter = ItemsAdapter(
            itemPlusButtonClickListener,
            itemMinusButtonClickListener
        )
        binding.itemsList.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        this.context?.let {
            enableSwipe(it, view, binding.itemsList, adapter)
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun enableSwipe(context: Context, view: View, recyclerView: RecyclerView, adapter: ItemsAdapter) {

        class SwipeCallback: ItemTouchHelperCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val item = adapter.currentList[itemPosition]
                viewModel.removeItem(item)
                Snackbar.make(
                    view,
                    getString(R.string.removed_item, item.name),
                    Snackbar.LENGTH_LONG
                )
                    .setAnchorView(binding.totalCount)
                    .setAction(getString(R.string.undo_link)) {
                        viewModel.addItem(item)
                        Toast.makeText(
                            activity,
                            getString(R.string.undo_remove, item.name),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            }
        }

        val swipeCallback = SwipeCallback()
        ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView)
    }
}
