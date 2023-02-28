package com.ribsky.games.ui.games

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import coil.transform.CircleCropTransformation
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.games.adapter.games.GamesAdapter
import com.ribsky.games.databinding.FragmentGamesBinding
import com.ribsky.games.model.GameModel
import com.ribsky.games.utils.geo.GeolocationHelper.Companion.isGeolocationEnabled
import com.ribsky.games.utils.geo.GeolocationHelper.Companion.turnOnGeolocation
import com.ribsky.navigation.features.*
import com.ribsky.permission.manager.PermissionManager
import com.ribsky.permission.manager.PermissionManagerImpl
import com.ribsky.permission.permissions.GamePermissionChecker
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GamesFragment :
    BaseFragment<GamesNavigation, GamesViewModel, FragmentGamesBinding>(FragmentGamesBinding::inflate) {

    override val viewModel: GamesViewModel by viewModel()

    override val navigation: GamesNavigation by inject()
    private val dialogsNavigation: DialogsNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()
    private val gameNavigation: GameNavigation by inject()

    private var state: LoadingStateDelegate? = null
    private var adapter: GamesAdapter? = null

    private var _permissionManager: PermissionManager? = null
    private val permissionManager: PermissionManager get() = _permissionManager!!


    private val permissionCallback = object : PermissionManager.PermissionCallback {
        override fun onPermissionGranted() {
            updateUi()
        }

        override fun onPermissionDenied() {
            updateUi()
        }
    }

    private val internetManager: InternetManager by inject()

    override fun initView() {
        initPermissionManager()
        initState()
        initAdapter()
        initRecyclerView()
        initBtns()
    }

    private fun initPermissionManager() {
        _permissionManager = PermissionManagerImpl(
            requireActivity() as AppCompatActivity,
            GamePermissionChecker()
        )
    }

    private fun initState() = with(binding) {
        state =
            LoadingStateDelegate(container, circularProgressIndicator, containerPermission).apply {
                showLoading()
            }
    }

    private fun initBtns() = with(binding) {
        btnWaiting.setOnClickListener {
            startGameSearch(GamesNavigation.LobbyState.WAITING)
        }
        btnSearch.setOnClickListener {
            startGameSearch(GamesNavigation.LobbyState.SEARCHING)
        }
        btnPermission.setOnClickListener {
            processRequestPermission()
        }
    }

    private fun initAdapter() {
        adapter = GamesAdapter { game ->
            processGameClick(game)
        }
    }

    private fun processGameClick(game: GameModel) {
        if (internetManager.isOnline() || viewModel.isFileExists(game.content)) {
            if (!game.isInProgress()) {
                if (game.isActive) {
                    adapter?.setPicked(game)
                } else {
                    navigation.navigatePromptSub(shopNavigation)
                }
            } else {
                navigation.navigateProgress(dialogsNavigation)
            }
        } else {
            navigation.navigateProgress(dialogsNavigation)
        }
    }

    private fun startGameSearch(state: GamesNavigation.LobbyState) {
        navigation.navigateLobby(
            gameNavigation,
            state,
            viewModel.user!!.image,
            adapter?.getPicked()!!.id
        )
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        layoutManager = GridLayoutManager(activity, 2)
        adapter = this@GamesFragment.adapter
    }

    override fun initObs() = with(viewModel) {
        getProfile()
        userStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> {
                    updateProfile(result.data!!)
                    getTests()
                }
                Resource.Status.ERROR -> {}
            }
        }
        testStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> adapter?.submitList(result.data) {
                    updateUi()
                    adapter?.setPicked(result.data?.first()!!)
                }
                Resource.Status.ERROR -> {}
            }
        }
        setFragmentResultListener(DialogsNavigation.RESULT_KEY_PROGRESS) { _, _ ->
            navigation.navigateBeta(betaNavigation)
        }
        setFragmentResultListener(ShopNavigation.RESULT_KEY_PROMPT_SUB) { _, _ ->
            navigation.navigateShop(shopNavigation)
        }
    }

    private fun showLoading() {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        state?.showLoading()
    }

    private fun processRequestPermission() {
        if (!permissionManager.hasPermissions()) {
            if (permissionManager.hasBlockedPermissions()) {
                permissionManager.openAppSettings()
            } else {
                permissionManager.requestPermission(permissionCallback)
            }
        } else if (!isGeolocationEnabled()) {
            turnOnGeolocation()
        } else {
            updateUi()
        }
    }

    private fun updateUi() {
        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
        if (permissionManager!!.hasPermissions() && isGeolocationEnabled()) {
            state?.showContent()
        } else {
            state?.showStub()
        }
    }

    private fun updateProfile(data: BaseUserModel) = with(binding) {
        ivAccount.load(data.image) {
            transformations(CircleCropTransformation())
        }
        tvName.text = data.name
    }

    override fun clear() {
        state = null
        adapter = null
        _permissionManager = null
    }
}
