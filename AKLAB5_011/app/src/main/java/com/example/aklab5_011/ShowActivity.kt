package com.example.aklab5_011

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class ShowActivity : AppCompatActivity() {

    private var product: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        product = intent.getSerializableExtra("product") as? Product



        setContentView(R.layout.activity_show)


        val listFragment = ProductListFragment()
        val detailsFragment = ProductDetailsFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerA, listFragment)
            .add(R.id.fragmentContainerB, detailsFragment)
            .commit()





//        detailsFragment0?.let {
//            it.updateProduct(product)
//        }
    }

    override fun onStart() {
        super.onStart()
        // Display product information if available
        val detailsFragmenta= supportFragmentManager.findFragmentById(R.id.fragmentContainerB) as ProductDetailsFragment
        detailsFragmenta.updateProduct(product)

        val orientation: Int = getResources().getConfiguration().orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val resultIntent = Intent()
            resultIntent.putExtra("product", product)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val resultIntent = Intent()
            resultIntent.putExtra("product", product)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}