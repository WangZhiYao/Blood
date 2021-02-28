package me.zhiyao.blood.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import me.zhiyao.blood.R
import me.zhiyao.blood.databinding.ActivityMainBinding
import me.zhiyao.blood.ext.startActivity
import me.zhiyao.blood.ext.viewBinding
import me.zhiyao.blood.ui.base.BaseActivity
import me.zhiyao.blood.ui.record.NewRecordActivity
import java.util.concurrent.TimeUnit

/**
 *
 * @author WangZhiYao
 * @date 2021/2/24
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    override fun canGoBack() = false

    private fun initView() {
        setSupportActionBar(binding.appBarMain.toolbar)
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_record, R.id.nav_statistic
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.appBarMain.fab.visibility =
                if (destination.id == R.id.nav_record) View.VISIBLE else View.GONE
        }

        binding.appBarMain.fab.clicks()
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe { startActivity(NewRecordActivity::class.java) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}