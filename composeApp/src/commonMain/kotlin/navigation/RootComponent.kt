package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import navigation.RootComponent.*

interface RootComponent {
    fun navigateToOnboarding()
    fun navigateToLogin()
    fun navigateToHome()

    @Serializable
    sealed class Config {
        @Serializable
        data object Onboarding : Config()

        @Serializable
        data object Login : Config()

        @Serializable
        data class Home(val info: String) : Config()
    }

    sealed class Child {
        data class Onboarding(val component: OnboardingComponent) : Child()
        data class Login(val component: LoginComponent) : Child()
        data class Home(val component: HomeComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    val childStack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Onboarding,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(config: Config, context: ComponentContext): Child {
        return when (config) {
            is Config.Onboarding -> Child.Onboarding(
                OnboardingComponent(
                    context,
                    { navigation.pushNew(Config.Login) },
                    { navigation.pop() }
                )
            )
            is Config.Login -> Child.Login(
                LoginComponent(
                    context,
                    { navigation.pushNew(Config.Home("Yessir")) },
                    { navigation.pop() }
                )
            )
            is Config.Home -> Child.Home(
                HomeComponent(
                    config.info,
                    context,
                    { navigation.pushNew(Config.Onboarding) },
                    { navigation.pop() }
                )
            )
        }
    }

    override fun navigateToOnboarding() {
        TODO("Not yet implemented")
    }

    override fun navigateToLogin() {
        TODO("Not yet implemented")
    }

    override fun navigateToHome() {
        TODO("Not yet implemented")
    }
}