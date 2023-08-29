package com.ribsky.loader.ui

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFade
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.logInWith
import com.ribsky.billing.wrapper.BillingClientWrapper
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.core.Resource
import com.ribsky.domain.exceptions.Exceptions
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.loader.databinding.ActivityLoaderBinding
import com.ribsky.navigation.features.AuthNavigation
import com.ribsky.navigation.features.MainNavigation
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoaderActivity :
    BaseActivity<LoaderViewModel, ActivityLoaderBinding>(ActivityLoaderBinding::inflate) {

    override val viewModel: LoaderViewModel by viewModel()

    private val mainNavigation: MainNavigation by inject()
    private val authNavigation: AuthNavigation by inject()

    private val billingClientWrapper: BillingClientWrapper by inject()

    private var texts = listOf(
        "Нації вмирають не від інфаркту. Спочатку їм відбирає мову\n\n- Ліна Костенко",
        "Мова росте елементарно разом з душею народу\n\n- Іван Франко",
        "Рідна мова на чужині ще милішою стає\n\n- Павло Грабовський",
        "Чужу мову можна вивчити за шість років, а свою треба вчити все життя\n\n- Франсуа Вольтер",
        "Мова - душа кожної національності, її святості, її найцінніший скарб\n\n- Іван Огієнко",
        "Щоб мова тобі повністю відкрилася, маєш бути залюбленим в неї\n\n- Олесь Гончар",
        "Мова вмирає, коли наступне покоління втрачає розуміння значення слів\n\n- Василь Голобородько",
        "Мова – це наша національна ознака, в мові – наша культура, сутність нашої свідомості\n\n- Іван Огієнко",
        "І возвеличимо на диво і розум наш, і наш язик\n\n- Тарас Шевченко",
        "Нове життя нового прагне слова\n\n- Максим Рильський",
        "Мова - духовне багатство народу\n\n- Василь Сухомлинський",
        "Мова вдосконалює серце і розум народу, розвиває їх\n\n- Олесь Гончар",
        "Здається – кращого немає нічого в Бога, як Дніпро та наша славная країна\n\n- Тарас Шевченко",
        "Мова зникає не тому, що її не вчать інші, а тому, що нею не говорять ті, хто її знає\n\n- Хосе-Марія Арце",
        "Яке прекрасне рідне слово! Воно — не світ, а всі світи\n\n- Володимир Сосюра",
        "Без усякої іншої науки ще можна обійтися, без знання рідної мови обійтися не можна\n\n- Олександр Олесь",
        "Кожен із нас має гордитися своєю чудовою мовою, адже вона того варта\n\n- Олесь Гончар",
        "Бринить-співає наша мова, чарує, тішить і п’янить\n\n- Олександр Олесь",
        "Коли забудеш рідну мову, забудеш душу ти свою\n\n- Володимир Сосюра",
        "А мова – це душа народу, народ без мови – не народ\n\n- Володимир Сосюра",
        "Слова – це кольорові камінці. Мало їх назбирати – треба ще з них навчитися узори викладати\n\n- Iрина Вiльде"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initBilling()
    }

    private fun initBilling() {
        billingClientWrapper.setup(this)
    }

    private fun getSubInfo(user: BaseUserModel) {
        val id = Firebase.auth.currentUser?.uid
        if (id != null) {
            Purchases.sharedInstance.logInWith(id,
                onSuccess = { _, _ ->
                    billingClientWrapper.getInventory { r ->
                        r.fold(
                            onSuccess = {
                                Purchases.sharedInstance.apply {
                                    setEmail(user.email)
                                    setDisplayName(user.name)
                                    setCampaign(user.bioFrom.toString())
                                }
                                viewModel.loadData()
                            },
                            onFailure = {
                                showError(it.message.orEmpty().ifEmpty { "Помилка!" }) {
                                    getSubInfo(user)
                                }
                            }
                        )
                    }
                },
                onError = {
                    showError(it.message.ifEmpty { "Помилка!" }) {
                        viewModel.loadUser()
                    }
                }
            )
        } else {
            showError("Помилка!") {
                authNavigation.navigate(this@LoaderActivity)
            }
        }
    }

    override fun initView() {}

    override fun initObs() = with(viewModel) {
        loadUser()
        statusUser.observe(this@LoaderActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> getSubInfo(it.data!!)
                Resource.Status.ERROR -> {
                    when (val ex = it.exception) {
                        is Exceptions.AuthException -> authNavigation.navigate(this@LoaderActivity)
                        else -> showError(ex?.localizedMessage.orEmpty()) {
                            viewModel.loadUser()
                        }
                    }
                }
            }
        }
        statusData.observe(this@LoaderActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> navigateToMain()
                Resource.Status.ERROR -> {
                    showError(it.exception?.localizedMessage.orEmpty()) {
                        viewModel.loadUser()
                    }
                }
            }
        }
    }

    private fun navigateToMain() = with(binding) {
        TransitionManager.beginDelayedTransition(root, MaterialFade())
        group.isGone = true
        tvQuote.apply {
            isGone = false
            setCharacterDelay(30)
            animateText(texts.shuffled().first())
            setOnAnimationChangeListener {
                lifecycleScope.launchWhenResumed {
                    delay(1500)
                    mainNavigation.navigate(this@LoaderActivity)
                }
            }
        }
    }

    private fun showError(error: String, callback: () -> Unit) {
        showAlert(
            title = "Помилка",
            message = error.ifBlank { "Невідома помилка" },
            positiveButton = "Повторити",
            positiveAction = callback,
            negativeButton = "Вихід",
            negativeAction = { authNavigation.navigate(this) },
        )
    }

    override fun clear() {
        billingClientWrapper.destroy()
    }
}
