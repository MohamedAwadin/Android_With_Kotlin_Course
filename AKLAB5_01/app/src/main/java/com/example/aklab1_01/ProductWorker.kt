package com.example.aklab1_01

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import org.json.JSONObject
import java.net.URL

class ProductWorker(context: Context, params: WorkerParameters) : Worker(context , params){
    companion object {
        var fetchedProducts: List<Product> = emptyList()
    }
    override fun doWork(): Result {
        return try {
            val jsonText = URL("https://dummyjson.com/products").readText()
            val jsonObj = JSONObject(jsonText)
            val jsonArray = jsonObj.getJSONArray("products")
            val products = mutableListOf<Product>()
            for (i in 0 until jsonArray.length()){
                val item = jsonArray.getJSONObject(i)
                products.add(
                    Product(
                        id = item.getInt("id"),
                        title = item.getString("title"),
                        description = item.getString("description"),
                        category = item.getString("category"),
                        price = item.getDouble("price"),
                        rating = item.getDouble("rating"),
                        thumbnail = item.getString("thumbnail")
                    )
                )
            }
            fetchedProducts = products
            Result.success(workDataOf("success" to true))
        } catch (e: Exception) {
            Result.retry()

        }
    }
}