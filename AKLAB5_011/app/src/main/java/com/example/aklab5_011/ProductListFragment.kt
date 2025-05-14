package com.example.aklab5_011

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.aklab5_011.R
import com.example.aklab5_011.Product
import com.example.aklab5_011.ProductAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListFragment : Fragment() {
    interface OnProductClickListener {
        fun onProductClicked(product: Product)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private var listener: OnProductClickListener? = null
    private lateinit var productDao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database= ProductDatabase.getDatabase(requireContext())
        productDao = database.productDao()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter { product ->
            listener?.onProductClicked(product)
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            if (NetworkUtil.isConnected(requireContext())){
                val response = withContext(Dispatchers.IO){
                    RetrofitClient.api.getProducts()
                }
                val products =response.products
                withContext(Dispatchers.IO){
                    productDao.deleteAllProducts()
                    productDao.insertProducts(products)
                }
                adapter.submitList(products)
            } else {
                val products = withContext(Dispatchers.IO) {
                    productDao.getAllProducts()
                }
                adapter.submitList(products)
            }
        }

    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is OnProductClickListener) {
            listener = context
        }
    }



    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}