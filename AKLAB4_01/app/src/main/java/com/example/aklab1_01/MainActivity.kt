package com.example.aklab1_01
import android.content.Intent
import android.content.res.Configuration
import com.example.aklab1_01.ProductDetailsFragment
import com.example.aklab1_01.ProductListFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aklab1_01.R
import com.example.aklab1_01.Product


class MainActivity : AppCompatActivity(), ProductListFragment.OnProductClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerA , ProductListFragment())
                .commit()
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerB , ProductDetailsFragment())
                    .commit()
            }
        }


//        if (savedInstanceState == null) {
//            val listFragment = ProductListFragment()
//            val detailsFragment = ProductDetailsFragment()
//
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragmentContainerA, listFragment)
//                .add(R.id.fragmentContainerB, detailsFragment)
//                .commit()
//            product = savedInstanceState?.getSerializable("product") as? Product
//        } else {
//            // Get the product from Intent if available
//            product = intent.getSerializableExtra("product") as? Product
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the product object if it exists
        if (product != null) {
            outState.putSerializable("product", product)
        }
    }
    override fun onStart() {
        super.onStart()
        val orientation = resources.configuration.orientation


        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if(product!=null)
            {
            val intent = Intent(this, ShowActivity::class.java)
            intent.putExtra("product", product)
            startActivityForResult(intent, REQUEST_CODE_DETAILS)
}

            // Landscape mode
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val detailsFragmenta= supportFragmentManager.findFragmentById(R.id.fragmentContainerB) as ProductDetailsFragment

            if(product!=null)
            detailsFragmenta.updateProduct( product)

            // Portrait mode
        }

    }
    val REQUEST_CODE_DETAILS = 1
    override fun onProductClicked(product: Product) {

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val detailsFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerB) as? ProductDetailsFragment
            detailsFragment?.updateProduct(product)
        } else {
            val intent = Intent(this, ShowActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }




    }
    private var product: Product? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            val detailsFragmenta= supportFragmentManager.findFragmentById(R.id.fragmentContainerB) as ProductDetailsFragment

              product=  data?.getSerializableExtra("product") as? Product

            detailsFragmenta.updateProduct( product)

    }
}