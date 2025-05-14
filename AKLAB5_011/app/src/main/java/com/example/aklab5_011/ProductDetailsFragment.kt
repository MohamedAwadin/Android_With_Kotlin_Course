package com.example.aklab5_011

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aklab5_011.R
import com.example.aklab5_011.Product
import kotlin.text.category
import kotlin.toString

class ProductDetailsFragment : Fragment() {
    private var product: Product? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateProduct(product)
    }

    fun updateProduct(newProduct: Product?) {
        product = newProduct
        val titleText = view?.findViewById<TextView>(R.id.titleText)
        val descriptionText = view?.findViewById<TextView>(R.id.descriptionText)
        val categoryText = view?.findViewById<TextView>(R.id.categoryText)
        val priceText = view?.findViewById<TextView>(R.id.priceText)
        val ratingText = view?.findViewById<TextView>(R.id.ratingText)

        product?.let {
            titleText?.text = it.title
            descriptionText?.text = it.description
            categoryText?.text = it.category
            priceText?.text = "$${it.price}"
            ratingText?.text = it.rating.toString()
        } ?: run {
            titleText?.text = "No product selected"
            descriptionText?.text = ""
            categoryText?.text = ""
            priceText?.text = ""
            ratingText?.text = ""
        }
    }
}