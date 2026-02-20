package com.example.pos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.PosApplication
import com.example.pos.R
import com.example.pos.databinding.ActivityMainBinding
import com.example.pos.ui.adapter.NavAdapter
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.*
import androidx.appcompat.widget.PopupMenu
import android.view.View

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
                    NavItem(R.id.nav_report, "Report & Analytics", R.drawable.ic_dashboard),
                    NavItem(R.id.nav_customers, "Customers", R.drawable.ic_people),
                    NavItem(R.id.nav_expense, "Expenses", R.drawable.ic_money),
                    NavItem(R.id.nav_sync, "Backup & Sync", R.drawable.ic_backup),
                    NavItem(R.id.nav_settings, "Settings", R.drawable.ic_settings)
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Initialize NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 2. Initialize UI components (Sidebar/Adapter)
        setupSidebar()
        
        // 3. Setup Navigation graph and logic
        setupNavigationLogic()
        
        updateUserProfile()
        setupUserMenu()
        setupBackPress()
        setupToolbar()
    }

    private fun setupToolbar() {
        val isLandscape = resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
        
        if (isLandscape) {
            // In landscape, the sidebar is a direct child within a horizontal layout, 
            // not a drawer child. DrawerLayout will throw an exception if we try to treat it as a drawer.
            binding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            binding.toolbar.navigationIcon = null
        } else {
            binding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)
            binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
            binding.toolbar.setNavigationOnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun setupNavigationLogic() {
        val isLoggedIn = intent.getBooleanExtra("LOGGED_IN", false)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        
        if (isLoggedIn) {
            graph.setStartDestination(R.id.nav_orders)
        } else {
            graph.setStartDestination(R.id.nav_login)
        }
        navController.graph = graph

        // Add listener AFTER everything is initialized to avoid UninitializedPropertyAccessException
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateSelectedNav(destination.id)
            val isLogin = destination.id == R.id.nav_login
            // Toolbar should be hidden on login screen
            binding.appBar.visibility = if (isLogin) View.GONE else View.VISIBLE
            
            // Handle sidebar visibility based on orientation and login state
            val isLandscape = resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
            if (isLandscape) {
                binding.sidebar.visibility = if (isLogin) View.GONE else View.VISIBLE
            } else {
                // In portrait, the sidebar is always visible as a drawer child when opened, 
                // but the drawer itself should be hidden on login screen.
                // However, we already lock the drawer in setupToolbar if needed.
                closeDrawerIfOpen()
            }
        }
    }

    private fun setupSidebar() {
        navAdapter =
                NavAdapter(navItems) { navItem ->
                    if (navItem.destinationId == -1) {
                        performLogout()
                    } else if (navController.currentDestination?.id != navItem.destinationId) {
                        navController.navigate(navItem.destinationId)
                        updateSelectedNav(navItem.destinationId)
                        closeDrawerIfOpen()
                    } else {
                        closeDrawerIfOpen()
                    }
                }

        binding.navRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = navAdapter
        }

        // Set initial selection
        updateSelectedNav(R.id.nav_orders)
    }

    private fun setupUserMenu() {
        binding.layoutUserProfile.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_user_profile, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_logout -> {
                        performLogout()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    private fun updateSelectedNav(destinationId: Int) {
        navAdapter.setSelectedId(destinationId)
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isLandscape = resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
                if (!isLandscape && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })
    }

    private fun closeDrawerIfOpen() {
        val isLandscape = resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
        // Only try to close if it's NOT landscape AND drawer is actually open
        if (!isLandscape) {
            try {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            } catch (e: Exception) {
                // Ignore if it's not a drawer in the current layout
            }
        }
    }

    private fun performLogout() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                val app = application as PosApplication
                // Sign out in background
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        app.supabase.auth.signOut()
                        withContext(Dispatchers.Main) {
                            updateUserProfile()
                            navController.navigate(R.id.nav_login)
                            closeDrawerIfOpen()
                        }
                    } catch (e: Exception) {}
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    fun updateUserProfile() {
        val app = application as PosApplication
        lifecycleScope.launch(Dispatchers.IO) {
            val session = try {
                app.supabase.auth.awaitInitialization()
                app.supabase.auth.currentSessionOrNull()
            } catch (e: Exception) {
                null
            }
            if (session != null) {
                val user = session.user
                val email = user?.email ?: "No Email"
                
                // Try to get display name from user metadata, fallback to email username or "User"
                val metadata = user?.userMetadata
                val fullName = metadata?.get("full_name")?.toString()?.replace("\"", "") 
                    ?: email.substringBefore("@").replaceFirstChar { it.uppercase() }

                withContext(Dispatchers.Main) {
                    binding.tvUserName.text = fullName
                    binding.tvUserRole.text = email
                    app.onUserChanged()
                }
            } else {
                 withContext(Dispatchers.Main) {
                    binding.tvUserName.text = "Guest"
                    binding.tvUserRole.text = "Not logged in"
                    app.onUserChanged()
                }
            }
        }
    }

    data class NavItem(val destinationId: Int, val title: String, val iconRes: Int)
}
