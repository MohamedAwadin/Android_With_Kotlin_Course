package com.example.aklab1_01
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.impl.model.WorkSpec
import com.example.aklab1_01.R
import com.example.aklab1_01.Product
import com.example.aklab1_01.ProductAdapter

class ProductListFragment : Fragment() {
    interface OnProductClickListener {
        fun onProductClicked(product: Product)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private var listener: OnProductClickListener? = null

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
        //adapter.submitList(products)

        val  workManager = WorkManager.getInstance(requireContext())
        val workRequest = OneTimeWorkRequestBuilder<ProductWorker>().setConstraints(Constraints.Builder().setRequiredNetworkType(
            NetworkType.CONNECTED).build()).build()

        workManager.enqueue(workRequest)

        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->
                if (workInfo?.state == WorkInfo.State.SUCCEEDED)
                    adapter.submitList(ProductWorker.fetchedProducts)
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