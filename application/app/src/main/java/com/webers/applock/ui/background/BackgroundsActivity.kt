package com.webers.applock.ui.background

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.webers.applock.databinding.ActivityBackgroundsBinding
import com.webers.applock.R
import com.webers.applock.ui.BaseActivity

class BackgroundsActivity : BaseActivity<BackgroundsActivityViewModel>() {

    private lateinit var binding: ActivityBackgroundsBinding

    override fun getViewModel(): Class<BackgroundsActivityViewModel> =
        BackgroundsActivityViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_backgrounds)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.containerBackgrounds, BackgroundsFragment.newInstance())
                .commitAllowingStateLoss()
        }

        binding.imageViewBack.setOnClickListener { finish() }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BackgroundsActivity::class.java)
        }
    }
}