package com.example.pos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.PosApplication
import com.example.pos.R
import com.example.pos.databinding.ActivityMainBinding
import com.example.pos.ui.adapter.NavAdapter
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navAdapter: NavAdapter

    private val navItems =
            listOf(
                    NavItem(R.id.nav_dashboard, "Dashboard", R.drawable.ic_dashboard),
                    NavItem(R.id.nav_categories, "Categories", R.drawable.ic_category),
                    NavItem(R.id.nav_products, "Products", R.drawable.ic_product),
                    NavItem(R.id.nav_orders, "Orders", R.drawable.ic_orders),
                    NavItem(R.id.nav_history, "History", R.drawable.ic_history),
                    NavItem(R.id.nav_expense, "Expenses", R.drawable.ic_money),
                    NavItem(R.id.nav_sync, "Backup & Sync", R.drawable.ic_backup),
                    NavItem(R.id.nav_settings, "Settings", R.drawable.ic_settings),
                    NavItem(-1, "Logout", R.drawable.ic_logout)
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupSidebar()
        setupUserProfile()
    }

    private fun setupNavigation() {
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val isLoggedIn = intent.getBooleanExtra("LOGGED_IN", false)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        
        if (isLoggedIn) {
            graph.setStartDestination(R.id.nav_orders)
        } else {
            graph.setStartDestination(R.id.nav_login)
        }
        navController.graph = graph

        // Clear the extra so it doesn't persist on rotation if not handled by savedInstanceState, 
        // though typically MainActivity handles rotation differently. 
        // Better yet, just rely on it for initial launch.
    }

    private fun setupSidebar() {
        navAdapter =
                NavAdapter(navItems) { navItem ->
                    if (navItem.destinationId == -1) {
                        performLogout()
                    } else {
                        navController.navigate(navItem.destinationId)
                        updateSelectedNav(navItem.destinationId)
                    }
                }

        binding.navRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = navAdapter
        }

        // Set initial selection
        updateSelectedNav(R.id.nav_orders)

        // Update selection when destination changes
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateSelectedNav(destination.id)
        }
    }

    private fun updateSelectedNav(destinationId: Int) {
        navAdapter.setSelectedId(destinationId)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun performLogout() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                val app = application as PosApplication
                // Sign out in background
                kotlinx.coroutines.GlobalScope.launch(kotlinx.coroutines.Dispatchers.IO) {
                    try {
                        app.supabase.auth.signOut()
                        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                            navController.navigate(R.id.nav_login)
                            binding.drawerLayout.closeDrawer(GravityCompat.START)
                        }
                    } catch (e: Exception) {}
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun setupUserProfile() {
        val app = application as PosApplication
        kotlinx.coroutines.GlobalScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            val session = try {
                app.supabase.auth.currentSessionOrNull()
            } catch (e: Exception) {
                null
            }
            if (session != null) {
                val user = session.user
                val email = user?.email ?: "No Email"
                // Try to get display name from user metadata, fallback to email username or "User"
                // Note: enable creating user metadata in Supabase to support "full_name"
                // For now, let's see if metadata is available.
                // Since `user` is from gotrue-kt, need to check its properties.
                // Assuming standard Supabase user structure.
                
                // Using a safe approach:
                val metadata = user?.userMetadata
                val fullName = metadata?.get("full_name")?.toString()?.replace("\"", "") ?: "User"

                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                    binding.tvUserName.text = fullName
                    binding.tvUserRole.text = email
                }
            }
        }
    }

    data class NavItem(val destinationId: Int, val title: String, val iconRes: Int)
}
